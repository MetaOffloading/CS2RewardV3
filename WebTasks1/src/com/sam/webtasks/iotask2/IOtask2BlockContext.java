package com.sam.webtasks.iotask2;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.sam.webtasks.client.Names;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask2BlockContext {
	private static IOtask2Block blockContext;

	public static void setContext(IOtask2Block block) {
		blockContext = block;
	}

	public static IOtask2Block getContext() {
		return (blockContext);
	}

	// use these methods to set and find out the currently clicked circle
	public static void setClickedCircle(int clickedCircle) {
		blockContext.clickedCircle = clickedCircle;
	}

	public static int getClickedCircle() {
		return (blockContext.clickedCircle);
	}
	
	//update progress bar?
	public static boolean getUpdateProgress() {
		return (blockContext.updateProgress);
	}

	// find out the current total number of points
	public static int getTotalPoints() {
		return (blockContext.totalPoints);
	}

	// are we displaying points to participants?
	public static boolean showPoints() {
		return (blockContext.showPoints);
	}

	// what is the target value for the current trial?
	public static int currentTargetValue() {
		return (blockContext.targetValues.get(blockContext.currentTrial));
	}

	// what is the maximum number of points (i.e. for targets in the no reminder
	// condition)
	public static int maxPoints() {
		return (blockContext.maxPoints);
	}

	// what is the current trial number
	public static int getTrialNum() {
		return (blockContext.currentTrial);
	}
	
	public static int getnTrials() {
		return (blockContext.nTrials);
	}
	
	public static String dollarTotal() {
		int nPoints = blockContext.totalPoints;

		String dollars = "$0.00";

				if (IOtask2BlockContext.showPoints()) {
					
					double dollarTotal = (((double) nPoints) / 250) + 2.50;

					int nCents = (int) (dollarTotal * 100);
					
					int nWholeDollars = nCents / 100;
					int nCentsLeftOver = nCents % 100;
					
						dollars = "$" + nWholeDollars + ".";
					
					if (nCentsLeftOver<10) {
						dollars = dollars + "0" + nCentsLeftOver;
					} else {
						dollars = dollars + nCentsLeftOver;
					}
					return dollars; }
				return dollars;
					

				//return dollars; }
		}

	// use these methods to set and find out which is the next circle in the
	// sequence
	public static void setNextCircle(int nextCircle) {
		blockContext.nextCircle = nextCircle;
	}

	public static int getNextCircle() {
		return (blockContext.nextCircle);
	}

	public static boolean incrementNextCircle() { // returns true if there are more circles to go
		blockContext.nextCircle++;

		if (blockContext.nextCircle < blockContext.nCircles) {
			return (true);
		} else {
			return (false);
		}
	}

	// offloading condition
	public static int getOffloadCondition() {
		return(blockContext.offloadCondition);
	}
	
	//instruction time
	public static int getInstructionTime() {
		return(blockContext.instructionTime);
	}
	
	public static void setOffloadCondition(int newCondition) {
		blockContext.offloadCondition = newCondition;
	}
	
	//completed circles
	public static void setCompletedCircles(int completedCircles) {
		blockContext.completedCircles=completedCircles;
	}
	
	public static void incrementCompletedCircles() {
		blockContext.completedCircles++;
	}
	
	public static int getCompletedCircles() {
		return(blockContext.completedCircles);
	}
	
	public static void setReminderCompletedCircles(int completedCircles) {
		blockContext.reminderCompletedCircles=completedCircles;
	}
	
	public static int getReminderCompletedCircles() {
		return(blockContext.reminderCompletedCircles);
	}
	
	public static void setBackupCompletedCircles(int completedCircles) {
		blockContext.backupCompletedCircles=completedCircles;
	}
	
	public static int getBackupCompletedCircles() {
		return(blockContext.backupCompletedCircles);
	}
	
	//block number
	public static int getBlockNum() {
		return(blockContext.blockNum);
	}
	
	// hits
	public static int getnHits() {
		return(blockContext.nHits);
	}
	
	//reminder choice
	public static int getReminderChoice() {
		return(blockContext.reminderChoice);
	}
	
	public static void setReminderChoice(int choice) {
		blockContext.reminderChoice=choice;
	}
	
	//circleAdjust
	public static void setCircleAdjust(int circleAdjust) {
		blockContext.circleAdjust = circleAdjust;
	}
	
	public static int getCircleAdjust()  {
		return(blockContext.circleAdjust);
	}
	
	public static void doCircleAdjustment() {
		blockContext.nextCircle -= blockContext.nCircles;
		blockContext.circleAdjust += blockContext.nCircles;
	}
	
	//nHits
	public static void setnHits(int hits) {
		blockContext.nHits = hits;
	}
	
	public static void incrementHits() {
		blockContext.nHits++;
		blockContext.totalPoints += blockContext.actualPoints;
	}
	
	//get target side - use this for checking target status
	public static int getTargetSide(int side) {
		return(blockContext.targetSide[side]);
	}

	//checkExitFlag
	public static void setCheckExitFlag(int exitFlag) {
		blockContext.checkExitFlag=exitFlag;
	}
	
	public static int getCheckExitFlag() {
		return(blockContext.checkExitFlag);
	}

	// how many points per target will be scored?
	public static void setActualPoints(int newPoints) {
		blockContext.actualPoints = newPoints;
	}

	// post-trial feedback
	public static boolean showPostTrialFeedback() {
		return (blockContext.showPostTrialFeedback);
	}

	// set and check the doubleClickFlag
	public static void setDoubleClickFlag(boolean doubleClickFlag) {
		blockContext.doubleClickFlag = doubleClickFlag;
	}

	public static boolean getDoubleClickFlag() {
		return (blockContext.doubleClickFlag);
	}
	
	//increment current trial
	public static void incrementCurrentTrial() {
		blockContext.currentTrial++;
	}
	
	// flash flag
	public static void setFlashFlag(boolean flag) {
		blockContext.flashFlag = flag;
	}
	
	public static boolean getFlashFlag() {
		return(blockContext.flashFlag);
	}
	
	//reminder lockout
	public static boolean getReminderLockout() {
		return(blockContext.reminderLockout);
	}
	
	public static int getReminderLockoutTime() {
		return(blockContext.reminderLockoutTime);
	}
	
	//how many circles?
	public static int getnCircles() {
		return(blockContext.nCircles);
	}
	
	public static int getTotalCircles() {
		return(blockContext.totalCircles);
	}


	// use these methods to set and find which side of the box the circle has been
	// dragged to
	public static void setExitFlag(int exitFlag) {
		blockContext.exitFlag = exitFlag;
	}

	public static int getExitFlag() {
		return (blockContext.exitFlag);
	}

	// use these methods for enforcing reminders
	public static void setReminderFlag(int reminderFlag) {
		blockContext.reminderFlag = reminderFlag;
	}

	public static void setBackupReminderFlag(int reminderFlag) {
		blockContext.backupReminderFlag = reminderFlag;
	}

	public static int getReminderFlag() {
		return (blockContext.reminderFlag);
	}

	public static int getBackupReminderFlag() {
		return (blockContext.backupReminderFlag);
	}
}
