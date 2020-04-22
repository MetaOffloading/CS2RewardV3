package com.sam.webtasks.iotask2;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.client.Names;
import com.sam.webtasks.client.SequenceHandler;

public class IOtask2Block {
	// set defaults for the parameters

	// number of trials to run
	public int nTrials = 1;

	// run a standard 17-trial block?
	public boolean standard17block = false;
	
	// update the progress bar with progress through the block?
	public boolean updateProgress = false;

	// number of circles visible on screen
	public int nCircles = 6;

	// total number of circles in one trial
	public int totalCircles = 25;

	// allow any circle to be moved? NB this will be overridden by participants'
	// choice to use reminders or not, if this choice is presented
	public int offloadCondition = Names.REMINDERS_OPTIONAL;

	// what block number is this? useful to store alongside performance data
	public int blockNum = 1;

	// which trial number is this?
	public int currentTrial = 0;

	// should target feedback be shown, i.e. green for correct target response, red
	// for incorrect
	public boolean showTargetFeedback = true;

	// should post-trial feedback be shown?
	public boolean showPostTrialFeedback = true;

	// total number of targets to include
	public int nTargets = 10;

	// target locations for each circle
	int[] targetSide = new int[nTargets];

	// target values
	public ArrayList<Integer> targetValues = new ArrayList<Integer>();

	// maximum number of points per target (i.e. in the no reminder condition)
	public int maxPoints = 10;

	// how many points per target are actually scored?
	public int actualPoints = 0;

	// total number of points scored so far
	public int totalPoints = 0;

	// tell participants how many points they've scored at each PreTrial screen?
	public boolean showPoints = true;

	// check whether the first circle has been double-clicked, if so a double-click
	// on the final circle exits the task, to help speed up debugging
	public boolean doubleClickFlag = false;

	// if a circle has been dragged out of the box, which side has it been dragged
	// to?
	public int exitFlag = 0;

	// use these variables to check (when necessary) whether reminders have been set
	public int reminderFlag = -1;
	public int backupReminderFlag = -1;

	// task lockout as a penalty for reminders?
	public boolean reminderLockout = false;
	public int reminderLockoutTime = 2000; // duration of lockout in ms

	// this method actually runs the block
	public void Run() { 
		if (standard17block) {
			nTrials=17;
			
			// 17 trials is the standard number of trials for 4 of each forced condition
			// plus 9 choice trials

			// first set up and shuffle the 9 choice values
			ArrayList<Integer> choiceValues = new ArrayList<Integer>();

			// add numbers 1-9
			for (int i = 1; i < 10; i++) {
				choiceValues.add(i);
			}

			// now shuffle
			for (int i = 0; i < choiceValues.size(); i++) {
				Collections.swap(choiceValues, i, Random.nextInt(choiceValues.size()));
			}

			// now set up the values corresponding to the forced internal / forced external
			// conditions
			int forcedA = 0; // begin with forced internal
			int forcedB = 10; // then have forced external

			if (Counterbalance.getFactorLevel("forcedOrder") == 1) { // depending on counterbalancing version,
																		// switch these around
				forcedA = 10;
				forcedB = 0;
			}

			// now put the whole list of target values together
			targetValues.add(choiceValues.get(0));
			targetValues.add(forcedA);
			targetValues.add(choiceValues.get(1));
			targetValues.add(forcedB);
			targetValues.add(choiceValues.get(2));
			targetValues.add(forcedA);
			targetValues.add(choiceValues.get(3));
			targetValues.add(forcedB);
			targetValues.add(choiceValues.get(4));
			targetValues.add(forcedA);
			targetValues.add(choiceValues.get(5));
			targetValues.add(forcedB);
			targetValues.add(choiceValues.get(6));
			targetValues.add(forcedA);
			targetValues.add(choiceValues.get(7));
			targetValues.add(forcedB);
			targetValues.add(choiceValues.get(8));
		}
		
		while (targetValues.size() < nTrials) { //if not enough target values have been set up
			targetValues.add(-1);
		}

		/*
		 * now save all the context about this block to the IOtask1BlockContext class*
		 */
		IOtask2BlockContext.setContext(this);

		/*
		 * now start subloop 3 of the sequence handler, which runs a trial of the task
		 */
		SequenceHandler.SetLoop(3, true);
		SequenceHandler.Next();
	}

	// the variables below are used during trials of the task. they don't need to be
	// set up

	// which circle should next be clicked in the sequence?
	public int nextCircle = 0;

	// which circle has actually been clicked?
	public int clickedCircle = 0;

	// use this variable for the conversion between the number of the circle in the
	// sequence (can go infinitely high) and the number of circles on screen
	// (default 6)
	public int circleAdjust = 0;

	// how many hits (just considering the most recent trial)
	public int nHits = 0;

	// did the participant choose to set reminders (1) or not (0)?
	public int reminderChoice = -1;

	// how long should target circles appear in their instruction colour before
	// fading to yellow
	public int instructionTime = 2000;

	// flash flag. use this for timing the red flash if wrong circle is dragged to
	// the bottom
	public boolean flashFlag = false;

	// ???????
	public int completedCircles = 0;
	public int backupCompletedCircles = 0;
	public int reminderCompletedCircles = -999;

	public int checkExitFlag = 1;

	public int pracTargets;

	// TODO: forced offloading
	// TODO: drag-by-drag data?

}
