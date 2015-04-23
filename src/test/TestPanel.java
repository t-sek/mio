package test;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		setLayout(null);
		
		JLabel title = new JLabel("教育実施状況の更新");
		title.setBounds(130, 6, 152, 16);
		add(title);
		
		JLabel label_1 = new JLabel("教育項目");
		label_1.setBounds(63, 45, 62, 16);
		add(label_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(63, 73, 204, 27);
		add(comboBox);
		
		JLabel label_2 = new JLabel("教育実施日");
		label_2.setBounds(63, 112, 85, 16);
		add(label_2);
		
		JLabel label_3 = new JLabel("2014年2月");
		label_3.setBounds(63, 140, 110, 16);
		add(label_3);
		
		JButton button = new JButton("変更");
		button.setBounds(130, 107, 75, 29);
		add(button);
		
		JLabel label_4 = new JLabel("教育対象者");
		label_4.setBounds(63, 168, 85, 16);
		add(label_4);
		
		JLabel label_5 = new JLabel("①");
		label_5.setBounds(73, 197, 20, 16);
		add(label_5);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(105, 196, 177, 27);
		add(comboBox_1);
		
		JLabel label_6 = new JLabel("②");
		label_6.setBounds(73, 240, 20, 16);
		add(label_6);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(105, 235, 177, 27);
		add(comboBox_2);
		
		JLabel label_7 = new JLabel("③");
		label_7.setBounds(73, 278, 20, 16);
		add(label_7);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(105, 274, 177, 27);
		add(comboBox_3);
		
		JLabel label_8 = new JLabel("④");
		label_8.setBounds(73, 318, 20, 16);
		add(label_8);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(105, 314, 177, 27);
		add(comboBox_4);
		
		JLabel label_9 = new JLabel("⑤");
		label_9.setBounds(73, 360, 20, 16);
		add(label_9);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(105, 356, 177, 27);
		add(comboBox_5);

	}
}
