//The SequenceHandler is the piece of code that defines the sequence of events
//that constitute the experiment.
//
//SequenceHandler.Next() will run the next step in the sequence.
//
//We can also switch between the main sequence of events and a subsequence
//using the SequenceHandler.SetLoop command. This takes two inputs:
//The first sets which loop we are in. 0 is the main loop. 1 is the first
//subloop. 2 is the second subloop, and so on.
//
//The second input is a Boolean. If this is set to true we initialise the 
//position so that the sequence will start from the beginning. If it is
//set to false, we will continue from whichever position we were currently in.
//
//So SequenceHandler.SetLoop(1,true) will switch to the first subloop,
//starting from the beginning.
//
//SequenceHandler.SetLoop(0,false) will switch to the main loop,
//continuing from where we left off.

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.Finish;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1DisplayParams;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.iotask2.IOtask2Feedback;
import com.sam.webtasks.iotask2.IOtask2InitialiseTrial;
import com.sam.webtasks.iotask2.IOtask2PreTrial;

public class SequenceHandler {
	public static void Next() {	
		// move forward one step in whichever loop we are now in
		sequencePosition.set(whichLoop, sequencePosition.get(whichLoop) + 1);

		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 **********************************************************************/
			case 1:
				PHP.UpdateStatus("" + Counterbalance.getCounterbalancingCell());
				break;
			case 2:	
				if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_NO) {
					ClickPage.Run(Instructions.Get(0), "Next");
				} else if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_YES) {
					ClickPage.Run("Your payment has not yet been determined. For this experiment, you will earn a base payment of $5. "
							+ "You will also earn an additional bonus between 0 and $5.50 depending on the points you accumulate during this experiment. "
							+ "When you start earning money during the experiment, the amount will start from $2, but you will earn a minimum "
							+ "of $5 even if the total shows less than that.<br><br>"
							+ "In this experiment you will have a simple experiment to do.<br><br>"
							+ "You will see several yellow circles inside a box. "
							+ "Inside each circle will be a number.<br><br>"
							+ "You can move them around using your mouse. Your task is to drag them to the bottom "
							+ "of the box in sequence. "
							+ "Please start by dragging 1 all the way to the bottom. "
							+ "This will make it disappear. Then drag 2 to the bottom, then 3, "
							+ "and so on.<br><br> ", "Next");
				}
				break;
			case 3:
				IOtask2Block block0 = new IOtask2Block();
				block0.totalCircles = 8;
				block0.nTargets = 0;
				block0.blockNum = 0;
				block0.nTrials = 2;
				block0.showPostTrialFeedback = false;
				
				block0.Run();
				break;
			case 4:
				ClickPage.Run(Instructions.Get(1),  "Next");
				break;
			case 5:
				IOtask2Block block1 = new IOtask2Block();
				block1.totalCircles = 8;
				block1.nTargets = 1;
				block1.blockNum = 1;
				block1.nTrials = 2;
				block1.showPostTrialFeedback = false;
				block1.Run();
				break;
			case 6:
				int pracTargets = 0;
				
				if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_EASY) {
					pracTargets=4;
				} else if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_DIFFICULT) {
					pracTargets=16;
				}
				if (IOtask2BlockContext.getnHits() < 1) { //if there were fewer than 1 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					ClickPage.Run("You didn't drag the special circle to the correct location.", "Please try again");
				} else {
					ClickPage.Run("Well done, that was correct.<br><br>Now it will get more difficult. "
	                        + "There will be a total of 25 circles, and " + pracTargets + " of them will be special ones "
	                        + "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
	                        + "do not remember all of them. That's fine - just try to remember as many as you can.", "Next");
				}
				break;	
			case 7:
			IOtask2Block block2 = new IOtask2Block();
			
			if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_EASY) {
				block2.nTargets=4;
			} else if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_DIFFICULT) {
				block2.nTargets=16;
			}
			
			block2.showPoints = false;
			block2.blockNum = 2; 
			block2.nTrials = 5;
			
			block2.Run();
			break;
			case 8:
				if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_EASY) {
					ClickPage.Run("Now the task will get more difficult. It will stay like this for the rest of the experiment.<br><br>"  
							                        + "Please ignore the difficulty of the practice trials you have just done and remember that the task "  
							                        + "will be like this from now on.<br><br>Click below to continue", "Next");
				} else if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_DIFFICULT) {
				ClickPage.Run("Now the task will get easier. It will stay like this for the rest of the experiment.<br><br>"  
							                       + "Please ignore the difficulty of the practice trials you have just done and remember that the task "  
							                       + "will be like this from now on.<br><br>Click below to continue", "Next");
				}
				break;
			case 9:
				//this runs the task with default settings: no choice at the beginning, and just one trial
				IOtask2Block block3 = new IOtask2Block();
				block3.totalCircles = 25;
				block3.nTargets = 10;
				block3.blockNum = 3;
				block3.nTrials = 1;
				block3.Run();
				break;
				
			//case 8:
			//	IOtask2Block block3 = new IOtask2Block();
			//	block3.targetValues.add(0); //forced internal condition
			//	block3.showPoints=false;    //don't display the number of points so far at the beginning. The default is to show this
			//	block3.blockNum=2;          //we always set the block number so that data from each block is kept separate
			//	
			//	if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_EASY) {
			//		block3.nTargets=4;
			//	} else if (Counterbalance.getFactorLevel("practiceDifficulty") == Names.PRACTICE_DIFFICULT) {
			//		block3.nTargets=16;
			//	}
			//	
			//	block3.Run();
			//	break;
			
			case 10:
				Slider.Run(Instructions.Get(2), "None of them", "All of them");
				break;
			case 11:
				//save the selected slider value to the database
				PHP.logData("sliderValue",  "" + Slider.getSliderValue(), true);
				break;
			case 12:
				ClickPage.Run(Instructions.Get(3),  "Next");
				break;
			case 13:
				IOtask2Block block4 = new IOtask2Block();
				block4.showPoints=false;
				block4.showPostTrialFeedback = true;
				block4.blockNum=4;
				block4.Run();
				break;
			case 14:
				if (IOtask2BlockContext.getnHits() < 8) { //if there were fewer than 8 hits on the last trial
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
					String msg = "You got " + IOtask2BlockContext.getnHits() + " out of 10 correct that time. You need to get at least 8 out of "
							+ "10 correct to continue to the next part.<br><br>Please keep in mind that you can set reminders to help you remember. Each "
							+ "time a special circle appears, you can immediately drag it next to the part of the box where it eventually needs to go. "
							+ "This should help reminder you what to do when you get to that circle in the sequence.";
					ClickPage.Run(msg, "Try again");		
				} else {
					SequenceHandler.Next();
				}
				break;
			case 15:
				if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_NO) {
					ClickPage.Run(Instructions.Get(4), "Next");
				} else if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_YES) {
					ClickPage.Run("From now on, you will score points everytime you drag one of the special circles "
							+ "to the correct location.<br><br>You should try to score as many points as you can.<br><br>"
							+ "You will earn a bonus depending on how many points you score. "
							+ "The more points you score, the more money you will earn.<br><br>Click below to continue ", "Next");
				}	
				break;
			case 16:
				if (Counterbalance.getFactorLevel("buttonColours") == Names.BUTTON_GREEN) {
				ClickPage.Run(Instructions.Get(5), "Next");
				} else if (Counterbalance.getFactorLevel("buttonColours") == Names.BUTTON_RED) {
					ClickPage.Run("Sometimes when you do the task, you will have to do it without setting any reminders.<br><br>"
                    + "When this happens, you will score 10 points for every special circle you remember.<br><br>"
                    + "You will always be given clear instructions what you should do. In this case you will be "
                    + "told \"This time you must do the task without setting any reminders\" and you will see a green button. "
                    + "When this happens, "
                    + "the computer will not let you set any reminders.<br><br>Let's practise that now.", "Next");
				}
				break;
			case 17:
				IOtask2Block block5 = new IOtask2Block();
				block5.targetValues.add(0); //forced internal condition
				block5.showPoints=false;
				block5.showPostTrialFeedback = true;
				block5.blockNum=5;
				block5.Run();
				break;
			case 18:
				if (Counterbalance.getFactorLevel("buttonColours") == Names.BUTTON_GREEN) {
				ClickPage.Run(Instructions.Get(6), "Next");
				} else if (Counterbalance.getFactorLevel("buttonColours") == Names.BUTTON_RED) {
					ClickPage.Run("Other times, you will have to set reminders for all the special circles.<br><br>When "
                + "this happens, you will also score 10 points for every special circle you remember.<br><br>"
                + "In this case, you will be told \"This time you <b>must</b> set a reminder for every special circle\" "
                + "and you will see a red button.<br><br>"
                + "When this happens, the computer will make sure that you always set a reminder for every "
                + "circle and it will not let you continue if you do not.<br><br>Let's practice that now.", "Next");
				}
				break;
			case 19:
				IOtask2Block block6 = new IOtask2Block();
				block6.targetValues.add(10); //forced internal condition
				block6.showPoints=false;
				block6.showPostTrialFeedback = true;
				block6.blockNum=6;
				block6.Run();
				break;
			case 20:
				if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_NO) {
				ClickPage.Run(Instructions.Get(7), "Next");
				} else if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_YES) {
					ClickPage.Run("Sometimes, you will have a choice between two options when you do the task. One option will be to do the task "
							+ "without being able to set any reminders. If you choose this option, you will always score, "
							+ "10 points for every special circle your remember.<br><br>The other option will be to "
							+ "do the task with reminders, but in this case each special circle will be worth "
							+ "fewer points. For example, you might be told that if you want to use reminders, "
							+ "each special circle will be worth only 5 points.<br><br>You should choose whichever "
							+ "option you think will score you the most points.<br><br>So if, for example, you "
							+ "thought you would earn more points by setting reminders and scoring 5 points for "
							+ "each special circle, you should choose this option. But if you thought you would "
							+ "score more points by just using your own memory and earning 10 points for each special "
							+ "circle, you should choose this option instead.<br><br>"
							+ "Please bear in mind that the more points you score, the more you will get paid at the end of the experiment<br><br>", "Next");
				}
				break;
			case 21:
				if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_NO) {
					ClickPage.Run(Instructions.Get(8), "Next");
				} else if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_YES) {
					ClickPage.Run("When you are presented with a choice like this, it is completely up to you. "
							+ "You should do whatever you think will allow you to score the highest number of points and earn the most amount of money. "
							+ "Bear in mind that the more points you score, the more money you'll earn.<br><br>"
							+ "You are now in the final part of the experiment. When the progress bar above is complete the experiment will "
							+ "end.<br><br>Click to continue", "Next");
				}
				break;
			
				//if (IOtask2BlockContext.getnHits() < 8) { //if there were fewer than 8 hits on the last trial
				//	SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2); //this line means that instead of moving forward we will repeat the previous instructions
				//	ClickPage.Run("You only got " + nHits + " out of 10 correct. You need to get at least 8 out of 10 correct to continue to the next part.<br><br>"
				//			+"Please keep in mind that you can set reminders to help you remember. Each time a special circle appears, you can immediately drag it "
				//			+ "next to the part of the box where it eventually needs to go so. This should help remind you what to do when you get to that circle in the "
				//			+ "sequence.", "Try again");
				//} else {
				//	ClickPage.Run("Well done, that was correct.<br><br>Now it will get more difficult. "
	              //          + "There will be a total of 25 circles, and " + pracTargets + " of them will be special ones "
	                //        + "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
	                  //      + "do not remember all of them. That's fine - just try to remember as many as you can.", "Next");
				//	SequenceHandler.Next(); //move to the next instruction
				//}
				//break;	
			case 22: 
				//add progress bar to screen
				ProgressBar.Initialise();
				ProgressBar.Show();
				ProgressBar.SetProgress(0,  17);
				
				
				IOtask2Block block7 = new IOtask2Block();
				block7.standard17block = true; //run a standard block of 17 trials
				block7.updateProgress = true; //update the progress bar so that it represents the current trial number compared to the whole block
				block7.blockNum=7;
				block7.showPostTrialFeedback = true;
				block7.showPoints = true;
				block7.Run();
				break;
			case 23:
				Slider.Run(Instructions.Get(9), "None of them", "All of them");
				break;
			case 24:
				PHP.logData("sliderValue2", "" + Slider.getSliderValue(), true);
				break;
			case 25:
				if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_NO) {
					ClickPage.Run(Instructions.Get(10), "Finish");
				} else if (Counterbalance.getFactorLevel("rewardGroup") == Names.REWARD_YES) {
					ClickPage.Run("Thank you for participating in this experiment.<br><br>"
							+ "For your effort, you have recieved a total of " + IOtask2BlockContext.dollarTotal() + "out of which $2 is the "
							+ "base payment and the rest is the bonus that you recieved based on the points. <br><br>"
							+ "When your HIT is approved, you will receive the $5 this experiment was advertised with. You will then receive the rest of your bonus within 48 hours.", "Finish");
				}
				break;
			case 26:
				//hide the progress bar
				ProgressBar.Hide();
				
				//***** log data and check that it saves
				String data = SessionInfo.rewardCode + ",";
				data = data + Counterbalance.getFactorLevel("forcedOrder") + ",";
				data = data + Counterbalance.getFactorLevel("buttonPositions") + ",";
				data = data + Counterbalance.getFactorLevel("buttonColours") + ",";
				data = data + Counterbalance.getFactorLevel("practiceDifficulty") + ",";
				data = data + Counterbalance.getFactorLevel("feedbackValence") + ",";
				data = data + Counterbalance.getFactorLevel("rewardGroup") + ",";
				data = data + SessionInfo.gender + ",";
				data = data + SessionInfo.age + ",";
				data = data + Slider.getSliderValue() + ","; //Slider.getSliderValue() returns the most recent slider
				//value. Seeing as there is only one slider in this experiment this solution works fine, but if
				//there was more than one slider response we would have to implement a more complex solution.
				data = data + TimeStamp.Now() + ",";
				data = data + IOtask2BlockContext.dollarTotal();

				PHP.UpdateStatus("finished");
				PHP.logData("finish", data, true);
				break;
			case 27:
				Finish.Run();
				break;
			}
			break;

		/********************************************/
		/* no need to edit the code below this line */
		/********************************************/

		case 1: // initialisation loop
			switch (sequencePosition.get(1)) {
			case 1:
				// initialise experiment settings
				Initialise.Run();
				break;
			case 2:
				// make sure that a participant ID has been registered.
				// If not, the participant may not have accepted the HIT
				CheckIdExists.Run();
				break;
			case 3:
				// check the status of this participant ID.
				// have they already accessed or completed the experiment? if so,
				// we may want to block them, depending on the setting of
				// SessionInfo.eligibility
				PHP.CheckStatus();
				break;
			case 4:
				// check whether this participant ID has been used to access a previous experiment
				PHP.CheckStatusPrevExp();
						
				break;
			case 5:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 8:
				SequenceHandler.SetLoop(0, true); // switch to and initialise the main loop
				SequenceHandler.Next(); // start the loop
				break;
			}
			break;
		case 2: // IOtask1 loop
			switch (sequencePosition.get(2)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 2 *
			 * This runs a single trial of IOtask1                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask1Block block = IOtask1BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0, false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				// now initialise trial and present instructions
				IOtask1InitialiseTrial.Run();
				break;
			case 3:
				// now run the trial
				IOtask1RunTrial.Run();
				break;
			case 4:
				// we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(2, true);
				SequenceHandler.Next();
				break;
				// TODO: mechanism to give post-trial feedback?
			}
			break;
		case 3: //IOtask2 loop
			switch (sequencePosition.get(3)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 3 *
			 * This runs a single trial of IOtask2                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask2Block block = IOtask2BlockContext.getContext();
				
				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0,  false);
				}
				
				SequenceHandler.Next();
				break;
			case 2:
				IOtask2InitialiseTrial.Run();
				break;
			case 3:
				//present the pre-trial choice if appropriate
				if (IOtask2BlockContext.currentTargetValue() > -1) {
					IOtask2PreTrial.Run();
				} else { //otherwise just skip to the start of the block
					SequenceHandler.Next();
				}
				break;
			case 4:
				//now run the trial
				IOtask2RunTrial.Run();
				break;
			case 5:
				if (IOtask2BlockContext.showPostTrialFeedback()) {
					IOtask2Feedback.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 6:
				//we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(3,  true);
				SequenceHandler.Next();
				break;
			}
		}
	}
	
	private static ArrayList<Integer> sequencePosition = new ArrayList<Integer>();
	private static int whichLoop;

	public static void SetLoop(int loop, Boolean init) {
		whichLoop = loop;

		while (whichLoop + 1 > sequencePosition.size()) { // is this a new loop?
			// if so, initialise the position in this loop to zero
			sequencePosition.add(0);
		}

		if (init) { // go the beginning of the sequence if init is true
			sequencePosition.set(whichLoop, 0);
		}
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}
}
