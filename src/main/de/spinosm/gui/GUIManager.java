package de.spinosm.gui;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;

public class GUIManager {

	public static void main(String[] args) {
		JXMapViewer mapViewer = new JXMapViewer();

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example 2");
		frame.getContentPane().add(mapViewer);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
