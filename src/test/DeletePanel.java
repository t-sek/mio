package test;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class DeletePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DeletePanel() {
		setLayout(null);
		
		JLabel lblTitle = new JLabel("title");
		lblTitle.setBounds(183, 6, 61, 16);
		add(lblTitle);
		
		JLabel lblSubject = new JLabel("subject");
		lblSubject.setBounds(92, 95, 94, 16);
		add(lblSubject);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(102, 123, 182, 27);
		add(comboBox);
		
		JButton button = new JButton("更新");
		button.setBounds(92, 186, 117, 29);
		add(button);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setBounds(231, 186, 117, 29);
		add(btnCancel);

	}

}
