package com.sam.webtasks.basictools;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.sam.webtasks.client.Names;
import com.sam.webtasks.client.SequenceHandler;
import com.sam.webtasks.client.SessionInfo;

public class PHP {
	static int timeCounter; // use this var to measure time waiting for response from server
	static int checkInterval = 100; // interval between server checks, in ms
	static int timeOutDuration = 70; // how many checks before timing out

	static String phpOutput = null;

	public static void Post(String url, String postData, final boolean registerResponse) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

		try {
			builder.setHeader("Content-Type", "application/x-www-form-urlencoded");

			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("Could not connect to server");
				}

				public void onResponseReceived(Request request, Response response) {
					if (registerResponse) {
						phpOutput = response.getText();
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("no connection to server");
		}
	}

	public static String Call(String url, final boolean registerResponse) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("Could not connect to server");
				}

				public void onResponseReceived(Request request, Response response) {
					if (registerResponse) {
						phpOutput = response.getText();
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("no connection to server");
		}

		return (phpOutput);
	}

	public static void logData(final String dataType, final String data, final boolean checkSaved) {
		String postData = "dataType=" + dataType + "&participantCode=" + SessionInfo.participantID;
		postData = postData + "&experimentCode=" + SessionInfo.experimentCode + "&version="
				+ SessionInfo.experimentVersion;
		postData = postData + "&sessionKey=" + SessionInfo.sessionKey + "&data=" + data;

		phpOutput = "";

		// only register response from server if checkSaved is true,
		// i.e. set the third argument to checkSaved
		Post("log.php", postData, checkSaved);

		timeCounter = 1;

		if (checkSaved) { // wait for response from server before continuing the sequencehandler
			if (SessionInfo.localTesting) {
				SequenceHandler.Next();
			} else {
				new Timer() {
					public void run() {
						if (phpOutput.contains("OK")) {
							cancel();
							SequenceHandler.Next();
						} else if (++timeCounter == timeOutDuration) {
							cancel();
							Window.alert(
									"Database connection error. Please check your internet connection and try again.");
							logData(dataType, data, checkSaved);
						}
					}
				}.scheduleRepeating(checkInterval);
			}
		}
	}

	public static void CheckStatusPrevExp() {
		if (SessionInfo.newParticipantsOnly) {
			String postData = "participantCode=" + SessionInfo.participantID;

			phpOutput = null;
			Post("checkStatusPrevExp.php", postData, true);

			timeCounter = 1;

			if (SessionInfo.localTesting) {
				SequenceHandler.Next(); // don't check status if running in local mode
			} else {
				new Timer() {
					public void run() {
						if (phpOutput != null) { // response from database
							cancel();

							if (phpOutput.contains("exists")) {
								Window.alert("You have previously taken part in one of our earlier experiments. Unfortunately this means that you re not able to participate in this one and you should return the HIT.");
							}
							
							if (phpOutput.contains("unknown")) {
								SequenceHandler.Next();
							}
						} else if (++timeCounter == timeOutDuration) {
							cancel();
							Window.alert(
									"Database connection error. Please check your internet connection and try again.");
							CheckStatus();
						}
					}
				}.scheduleRepeating(checkInterval);
			}
		} else {
			SequenceHandler.Next();
		}
	}

	public static void CheckStatus() {
		String postData = "participantCode=" + SessionInfo.participantID;
		postData = postData + "&experimentCode=" + SessionInfo.experimentCode;
		postData = postData + "&version=" + SessionInfo.experimentVersion;

		phpOutput = null;
		Post("checkStatus.php", postData, true);

		timeCounter = 1;

		if (SessionInfo.localTesting) {
			SequenceHandler.Next(); // don't check status if running in local mode
		} else {
			new Timer() {
				public void run() {
					if (phpOutput != null) { // response from database
						cancel();

						switch (SessionInfo.eligibility) {
						case Names.ELIGIBILITY_ANYONE:
							// if anyone is eligible it doesn't matter what the response is, just continue
							SequenceHandler.Next();
							break;
						case Names.ELIGIBILITY_NEVERACCESSED:
							if (phpOutput.contains("unknown")) {
								// if it's an unknown ID, we can continue
								SequenceHandler.Next();
							} else {
								// otherwise throw an error
								Window.alert(
										"Your ID has previously been used to access this experiment. Unfortunately this means you will not be able to take part again and you should return the HIT.");
							}
							break;
						case Names.ELIGIBILITY_NEVERCOMPLETED:
							if (phpOutput.contains("finished")) {
								Window.alert(
										"Your ID has previously been used to access this experiment. Unfortunately this means you will not be able to take part again and you should return the HIT.");
							} else if (phpOutput.contains("unknown")) {
								// fine to continue if the ID hasn't been used before
								SequenceHandler.Next();
							} else {
								// it's also fine to continue if the ID has been used but the experiment was not
								// completed
								// in this case we need to make sure we use the some counterbalancing settings
								// as last time
								int cell = Integer.parseInt(phpOutput);
								Counterbalance.setCounterbalancingFactors(cell);
								SequenceHandler.Next();
							}
							break;
						}
					} else if (++timeCounter == timeOutDuration) {
						cancel();
						Window.alert("Database connection error. Please check your internet connection and try again.");
						CheckStatus();
					}
				}
			}.scheduleRepeating(checkInterval);
		}
	}

	public static void UpdateStatus(final String newStatus) {
		String postData = "participantCode=" + SessionInfo.participantID;
		postData = postData + "&experimentCode=" + SessionInfo.experimentCode;
		postData = postData + "&version=" + SessionInfo.experimentVersion;
		postData = postData + "&status=" + newStatus;

		phpOutput = null;
		Post("updateStatus.php", postData, true);

		timeCounter = 1;

		if (SessionInfo.localTesting) { // wait for response from server before continuing the sequencehandler
			SequenceHandler.Next();
		} else {
			new Timer() {
				public void run() {
					if (phpOutput.contains("OK")) {
						cancel();
						SequenceHandler.Next();
					} else if (++timeCounter == timeOutDuration) {
						cancel();
						Window.alert("Database connection error. Please check your internet connection and try again.");
						UpdateStatus(newStatus);
					}
				}
			}.scheduleRepeating(checkInterval);
		}
	}
}