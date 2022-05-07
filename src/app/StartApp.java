package app;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import view.VotacionsGUI;

public class StartApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VotacionsGUI window = new VotacionsGUI();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString(), "Error:", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
