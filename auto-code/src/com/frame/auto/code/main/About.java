package com.frame.auto.code.main;

import java.awt.*;
import javax.swing.*;
@SuppressWarnings("serial")
public class About
extends JDialog {
	JPanel panel1 = new JPanel();
	JLabel jLabel1 = new JLabel();
	GridBagLayout layOut = new GridBagLayout();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JEditorPane jEditorPane=new JEditorPane();
	JLabel jLabel8 = new JLabel();
	public About(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public About(JFrame own) {
		super(own, "关于我们");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		try {
			jbInit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void jbInit() throws Exception {
		panel1.setLayout(layOut);
		Font font = new Font("Dialog", 0, 14);
		jLabel1.setText("Java代码自动生成器1.0;作者：yuejing");
		jLabel2.setText("Copyright \u00A92013  All Rights Reserved");
		jLabel3.setText("");
		jLabel1.setFont(font);
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel2.setFont(font);
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel3.setFont(font);
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel4.setPreferredSize(new Dimension(6, 16));
		jLabel4.setText("    ");
		jLabel5.setText("    ");
		jLabel6.setText("    ");
		jLabel7.setText("    ");
		jEditorPane.setFont(new java.awt.Font("Dialog", 0, 11));
		jLabel8.setText("");
		jLabel8.setFont(font);
		getContentPane().add(panel1);

		panel1.add(jLabel2,                  new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 56, 0));
		panel1.add(jLabel3,                  new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		panel1.add(jLabel1,                  new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		panel1.add(jLabel4,                    new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 22, 0));
		panel1.add(jLabel5,                  new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel1.add(jLabel6,                  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel1.add(jLabel7,                 new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		panel1.add(jLabel8,         new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	}
}