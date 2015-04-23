package test;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class addPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public addPanel() {
		setLayout(null);
		
		JLabel lblTitle = new JLabel("title");
		lblTitle.setBounds(176, 17, 61, 16);
		add(lblTitle);
		
		JLabel lblSubject = new JLabel("subject");
		lblSubject.setBounds(82, 82, 61, 16);
		add(lblSubject);
		
		textField = new JTextField();
		textField.setBounds(103, 110, 190, 28);
		add(textField);
		textField.setColumns(10);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.setBounds(103, 201, 117, 29);
		add(btnUpdate);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setBounds(247, 201, 117, 29);
		add(btnCancel);

	}
}
