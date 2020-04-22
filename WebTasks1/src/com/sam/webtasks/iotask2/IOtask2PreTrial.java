package com.sam.webtasks.iotask2;

import java.text.DecimalFormat;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.client.Names;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask2PreTrial {
	public static void Run() {
		if (IOtask2BlockContext.getUpdateProgress()) {
			ProgressBar.SetProgress(IOtask2BlockContext.getTrialNum(), IOtask2BlockContext.getnTrials());
		}
		
		final Date instructionStart = new Date();
		
		IOtask2BlockContext.setReminderFlag(-1);
		IOtask2BlockContext.setBackupReminderFlag(-1);
		IOtask2BlockContext.setReminderCompletedCircles(-999);

		final HTML displayText = new HTML();
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		final VerticalPanel verticalPanel = new VerticalPanel();
		final HorizontalPanel buttonPanel = new HorizontalPanel();
		String displayString = "";
		
		if (Counterbalance.getFactorLevel("rewardGroup") == 0) {
			displayString = displayString + "You have scored a total of " + IOtask2BlockContext.getTotalPoints()
			+ " points so far <br><br>";	
		} else {
			if (IOtask2BlockContext.showPoints()) {
		}
			int nPoints = IOtask2BlockContext.getTotalPoints();
			
			double dollarTotal = (((double) nPoints) / 250) + 2.50;

			int nCents = (int) (dollarTotal * 100);
			
			int nWholeDollars = nCents / 100;
			int nCentsLeftOver = nCents % 100;
			
			String dollarString = "$" + nWholeDollars + ".";
			
			if (nCentsLeftOver<10) {
				dollarString = dollarString + "0" + nCentsLeftOver;
			} else {
				dollarString = dollarString + nCentsLeftOver;
			}
			
			displayString = displayString + "You have scored a total of " + IOtask2BlockContext.getTotalPoints()
					+ " points so far<br><br>" + "You have earned a total of " + IOtask2BlockContext.dollarTotal()
					+ "<br><br>";
		}

		int points = IOtask2BlockContext.currentTargetValue();

		if (points == 0) {
			displayString = displayString + "This time you must do the task without setting any reminders.<br><br>"
					+ "Please touch the button below to start.";
		} else if (points == IOtask2BlockContext.maxPoints()) {
			displayString = displayString + "This time you <b>must</b> set a reminder for every special circle.<br><br>"
					+ "Please touch the button below to start.";
		} else {
			displayString = displayString
					+ "This time you have a choice.<br><br>Please touch the option that you prefer.<br><br>";
		}

		displayText.setHTML(displayString);

		final Button reminderButton = new Button(
				"Special circles worth<br><b>" + points + " </b>points<br><br>" + "Reminders allowed");

		if (points == IOtask2BlockContext.maxPoints()) {
			reminderButton.setHTML(
					"Special circles worth<br><b>" + points + " </b>points<br><br>" + "You <b>must</b> set reminders");
		}

		final Button noReminderButton = new Button(
				"Special circles worth<br><b>" + IOtask2BlockContext.maxPoints() + " </b>points<br><br>" + "Reminders <b>not</b> allowed");

		if (Counterbalance.getFactorLevel("buttonColours") == 0) {
			reminderButton.setStyleName("pinkButton");
			noReminderButton.setStyleName("greenButton");
		} else {
			reminderButton.setStyleName("greenButton");
			noReminderButton.setStyleName("pinkButton");
		}

		if (Counterbalance.getFactorLevel("buttonPositions") == 0) {
			if (points > 0) {
				buttonPanel.add(reminderButton);
			}

			if (points < IOtask2BlockContext.maxPoints()) {
				buttonPanel.add(noReminderButton);
			}
		} else {
			if (points < IOtask2BlockContext.maxPoints()) {
				buttonPanel.add(noReminderButton);
			}

			if (points > 0) {
				buttonPanel.add(reminderButton);
			}
		}

		// set up vertical panel
		verticalPanel.setWidth("75%");
		// verticalPanel.setHeight(Window.getClientHeight() + "px");
		verticalPanel.setHeight("300px");

		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		// add elements to panel
		displayText.setStyleName("instructionText");
		verticalPanel.add(displayText);
		verticalPanel.add(buttonPanel);

		// place vertical panel inside horizontal panel, so it can be centred
		horizontalPanel.setWidth(Window.getClientWidth() + "px");
		horizontalPanel.setHeight(Window.getClientHeight() + "px");

		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		horizontalPanel.add(verticalPanel);

		// add panel to root
		RootPanel.get().add(horizontalPanel);

		// equalise the widths of the buttons
		if ((points > 0) & (points < IOtask2BlockContext.maxPoints())) {
			reminderButton.setWidth(noReminderButton.getOffsetWidth() + "px");
		}

		reminderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Date responseTime = new Date();
				
				IOtask2BlockContext.setReminderChoice(1);
				
				//,1, below indicates the output that reminders have been selected
				
				final String data = IOtask2BlockContext.getTrialNum() + "," + IOtask2BlockContext.currentTargetValue() + ",1," + (int) (responseTime.getTime() - instructionStart.getTime()); 

				RootPanel.get().remove(horizontalPanel);

				IOtask2BlockContext.setOffloadCondition(Names.REMINDERS_MANDATORY_TARGETONLY);
				
				IOtask2BlockContext.setActualPoints(IOtask2BlockContext.currentTargetValue());

				new Timer() {
					public void run() {
						PHP.logData("preTrial", data, true);
					}
				}.schedule(500);
			}
		});

		noReminderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Date responseTime = new Date();
				
				IOtask2BlockContext.setReminderChoice(0);
				
				//,0, below indicates that reminders have not been selected
				final String data = IOtask2BlockContext.getTrialNum() + "," + IOtask2BlockContext.currentTargetValue() + ",0," + (int) (responseTime.getTime() - instructionStart.getTime()); 

				RootPanel.get().remove(horizontalPanel);

				IOtask2BlockContext.setOffloadCondition(Names.REMINDERS_NOTALLOWED);
				
				IOtask2BlockContext.setActualPoints(IOtask2BlockContext.maxPoints());

				new Timer() {
					public void run() {
						PHP.logData("preTrial", data, true);
					}
				}.schedule(500);
			}
		});
	}
}


