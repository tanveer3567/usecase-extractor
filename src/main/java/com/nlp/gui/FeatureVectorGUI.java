package com.nlp.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import com.nlp.core_nlp.Feature_merged;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class FeatureVectorGUI {
	private static JTextField textField;

	public static void main(String[] args) {
		JFrame f = new JFrame();

		JButton b = new JButton("generate");
		
		f.getContentPane().add(b);

		f.setSize(400, 500);
		f.getContentPane().setLayout(null);
		textField = new JTextField();
		textField.setBounds(55, 127, 187, 33);
		f.getContentPane().add(textField);
		textField.setColumns(10);
		JLabel lblNewLabel = new JLabel("Generate feature Vectors");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(55, 38, 293, 40);
		f.getContentPane().add(lblNewLabel);
		
		JButton btnSelectFile = new JButton("select file");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					textField.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnSelectFile.setBounds(263, 127, 100, 29);
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(55, 267, 293, 20);
		f.getContentPane().add(lblNewLabel_1);
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText("Your feature vectors are being generated please wait....");
				Feature_merged.addVerbCategories();
				Feature_merged.readFileFromGUI(textField.getText());
				lblNewLabel_1.setText("Feature vectors are generated");
			}
		});
		b.setBounds(142, 190, 100, 40);
		
		f.getContentPane().add(btnSelectFile);
		f.setVisible(true);
	}
}