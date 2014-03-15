package com.mcbadgercraft.installer.monitor;

import io.github.thefishlive.installer.event.PhaseChangeEvent;
import io.github.thefishlive.installer.event.TaskCompleteEvent;
import io.github.thefishlive.installer.event.Event.Result;
import io.github.thefishlive.installer.exception.InstallerException;

import com.google.common.eventbus.Subscribe;
import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.log.LogPanel;

public class ProgressMonitor {

	@Subscribe
	public void onPhaseChange(PhaseChangeEvent event) {
		LogPanel panel = Bootstrap.getLogPanel();
		
		panel.nextPhase();
		
		int tasks = event.getInstaller().getAmountOfTasks(event.getNewPhase());
		
		if (tasks >= 0) {
			panel.setMaximum(tasks);
			panel.setProgress(0);
		} else {
			panel.setProgress(1);
			panel.setMaximum(1);
		}
		
	}
	
	@Subscribe
	public void onTaskComplete(TaskCompleteEvent event) throws InstallerException {
		if (event.getResult() == Result.FAIL) {
			throw new InstallerException("Task " + event.getTask().getName() + " failed to execute correctly");
		} else {
			Bootstrap.getLogPanel().nextTask();
		}
	}
	
}
