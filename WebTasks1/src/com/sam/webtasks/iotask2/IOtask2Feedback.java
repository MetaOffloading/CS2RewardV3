package com.sam.webtasks.iotask2;

import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.client.Names;

public class IOtask2Feedback {

	public static void Run() {
		String msg="";
		
		IOtask2Block block = IOtask2BlockContext.getContext(); //get context from most recent block
		
		if (Counterbalance.getFactorLevel("feedbackValence") == Names.FEEDBACK_POSITIVE) {
			if (block.nHits == 0) {
				msg = "You did not get any special circles correct this time.";
			} else if (block.nHits <= (block.nTargets/2)) {
				msg = "Well done - good work! You are responding well to the special circles.";
			} else if (block.nHits == block.nTargets) {
				msg = "Well done - perfect! You responded correctly to all of the special circles.";
			} else {
				msg = "Well done - excellent work! You responded correctly to most of the special circles.";
			}
		} else if (Counterbalance.getFactorLevel("feedbackValence") == Names.FEEDBACK_NEGATIVE) { 
			if (block.nHits == block.nTargets) {
				msg = "You did not get any special circles wrong this time.";
			} else if (block.nHits == 0) {
				msg = "Room for improvement. You got all of the special circles wrong.";
			} else if (block.nHits < (block.nTargets/2)) {
				msg = "Room for improvement. You got most of the special circles wrong.";
			} else {
				msg = "Room for improvement. You got some of the special circles wrong.";
			}
		}
		
		ClickPage.Run(msg, "Next");
	}

}
