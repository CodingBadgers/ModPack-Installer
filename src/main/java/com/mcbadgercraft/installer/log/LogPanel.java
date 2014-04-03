package com.mcbadgercraft.installer.log;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class LogPanel extends JFrame {
	
	private JProgressBar phaseProgress;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JProgressBar taskProgress;

	/**
	 * Create the panel.
	 */
	public LogPanel() {
		getContentPane().setLayout(null);
		setSize(600, 426);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		phaseProgress = new JProgressBar();
		phaseProgress.setBounds(10, 335, 564, 16);
		phaseProgress.setMaximum(5);
		getContentPane().add(phaseProgress);
		
		taskProgress = new JProgressBar();
		taskProgress.setBounds(10, 362, 564, 16);
		getContentPane().add(taskProgress);

		textPane = new JTextPane();
		textPane.setFont(new Font("consolas", 0, 12));
		JPanel noWrapPanel = new JPanel(new BorderLayout());
		noWrapPanel.add(textPane);
		
		scrollPane = new JScrollPane(noWrapPanel);
		scrollPane.setBounds(10, 11, 564, 313);
		getContentPane().add(scrollPane);
		
	}
	
	public void addLogLine(String line) {
	    try {
	    	Document doc = textPane.getDocument();
			doc.insertString(doc.getLength(), line, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void nextPhase() {
		phaseProgress.setValue(phaseProgress.getValue() + 1);
	}

	public void nextTask() {
		taskProgress.setValue(taskProgress.getValue() + 1);
	}
	
	public void setProgress(int progress) {
		taskProgress.setValue(progress);
	}
	
	public void setMaximum(int max) {
		taskProgress.setMaximum(max);
	}

}
