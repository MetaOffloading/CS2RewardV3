//checks whether an ID has been passed to the code, and whether it starts with A (like all valid Mturk IDs)
package com.sam.webtasks.basictools;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.client.SequenceHandler;
import com.sam.webtasks.client.SessionInfo;

public class CheckIdExists {
	public static void Run() {
		if (SessionInfo.localTesting) {
			SequenceHandler.Next();
		} else {
			if (SessionInfo.participantID.startsWith("A")) {
				SequenceHandler.Next();
			} else {
				RootPanel.get().clear();

				final HTML error = new HTML("You must accept the HIT before you can continue. "
						+ "Please close this page, accept the HIT, and try again."
						+ "<br><br>If you have already accepted the HIT there has been a JavaScript error which "
						+ "means you will not be able to continue.");

				RootPanel.get().add(error);
			}
		}
	}
}
