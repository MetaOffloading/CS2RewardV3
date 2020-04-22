package com.sam.webtasks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.ProgressBar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebTasks implements EntryPoint {
	public static ProgressBar progressBar = new ProgressBar();
	public static HorizontalPanel progressBarPanel = new HorizontalPanel();
	
	public void onModuleLoad() {
		//Window.alert("Experiment: " + SessionInfo.experimentCode + ", Version: " + SessionInfo.experimentVersion);
		// set the sequence handler to the initialisation loop and start from the beginning
		SequenceHandler.SetLoop(1,true); 
		SequenceHandler.Next();
	}
}
