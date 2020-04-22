package com.sam.webtasks.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sam.webtasks.basictools.ProgressBar;

public class Names {
	//reminder conditions
	public final static int REMINDERS_NOTALLOWED=0; //can't use reminders
	public final static int REMINDERS_OPTIONAL=1;   //optional to use reminders
	public final static int REMINDERS_MANDATORY_ANYCIRCLE=2; //mandatory to set reminders, but it works to move any circles, not just the specified targets
	public final static int REMINDERS_MANDATORY_TARGETONLY=3;//mandatory to set reminders for the specified targets
		
	//Gender
	public final static int GENDER_MALE=0;
	public final static int GENDER_FEMALE=1;
	public final static int GENDER_OTHER=2;
	
	//policy for dealing with participants who have already accessed the task
	//who can take part?
	public final static int ELIGIBILITY_ANYONE=0; //anyone can take part
	public final static int ELIGIBILITY_NEVERCOMPLETED=1; //can only take part if you haven't completed the experiment
	public final static int ELIGIBILITY_NEVERACCESSED=2;  //can only take part if you haven't accessed the task
	
	//feedback valence
	public final static int FEEDBACK_POSITIVE=0;
	public final static int FEEDBACK_NEGATIVE=1;
	
	//initial practice difficulty
	public final static int PRACTICE_EASY=0;
	public final static int PRACTICE_DIFFICULT=1;
	
	//reward value group
	public final static int REWARD_NO=0;
	public final static int REWARD_YES=1;
	
	//color association
	public final static int BUTTON_RED=0;
	public final static int BUTTON_GREEN=1;
}

