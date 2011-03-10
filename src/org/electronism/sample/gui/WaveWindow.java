package org.electronism.sample.gui;
import java.awt.BorderLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.electronism.sample.Sample;


public class WaveWindow extends JFrame 
{

	WaveEditor editor;
	
	public WaveWindow()
	{
		JPanel panel = new JPanel(new BorderLayout());
		
		JButton button = new JButton("Open Wav");
		button.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("./");
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					try {
						
						Sample wav;
							wav = new Sample(file);
						editor.setWave(wav);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
		
		});
		
		
		editor = new WaveEditor();

		
		panel.add(button, BorderLayout.NORTH);
		panel.add(editor, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(panel);
		
		
	}
}
