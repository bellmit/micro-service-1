package com.frame.auto.code.main;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;

import com.frame.auto.code.constants.Config;

/**
 * <p>
 * Title:Java 代码自动生成工具
 * </p>
 * <p>
 * Description: 主要应用于oracle,sqlserver数据库数据基本操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author yuejing
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ConfigFrame extends JDialog implements ActionListener {
	GridBagLayout layOut = new GridBagLayout();
	JLabel jLabel1 = new JLabel();
	JTextField userName = new JTextField();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JTextField IP = new JTextField();
	JPasswordField passWd = new JPasswordField();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel9 = new JLabel();
	JTextField sequece = new JTextField();
	JTextField packagePath = new JTextField();
	JTextField path = new JTextField();
	JButton jButton3 = new JButton();
	JButton cancel = new JButton();
	JLabel jLabel8 = new JLabel();
	JRadioButton jRadioOracle = new JRadioButton();
	JRadioButton jRadioSql = new JRadioButton();
	ButtonGroup buttonGroup1 = new ButtonGroup();
	JLabel jLabel10 = new JLabel();
	JLabel jLabel11 = new JLabel();
	JTextField port = new JTextField();
	JTextField sid = new JTextField();
	JRadioButton jRadioMysql = new JRadioButton();

	JLabel jLabel12 = new JLabel();
	JLabel jLabel13 = new JLabel();
	JLabel jLabel14 = new JLabel();
	JTextField projectName = new JTextField();
	JTextField tablePrefix = new JTextField();
	JTextField excColumn = new JTextField();

	@SuppressWarnings("static-access")
	public ConfigFrame(JFrame own) {
		super(own, "配置信息");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		sequece.setBackground(Color.lightGray);
		sequece.setCaretColor(Color.black);
		Font font = new Font("Dialog", 0, 14);
		jLabel1.setFont(font);
		jLabel1.setText("数据库连接信息：");
		this.getContentPane().setLayout(layOut);
		jLabel2.setFont(font);
		jLabel2.setText("用户名：");
		jLabel3.setFont(font);
		jLabel3.setText("密    码：");
		userName.setText("");
		jLabel4.setFont(font);
		jLabel4.setText("数据库IP：");
		passWd.setText("");
		jButton1.setFont(font);
		jButton1.setText("确  定");
		jButton1
		.addActionListener(new ConfigFrame_jButton1_actionAdapter(this));
		jButton2.setFont(font);
		jButton2.setText("重  置");
		jButton2
		.addActionListener(new ConfigFrame_jButton2_actionAdapter(this));
		jLabel5.setFont(font);
		jLabel5.setText("代码相关信息:");
		jLabel6.setFont(font);
		jLabel6.setText("包路径：");
		jLabel7.setFont(font);
		jLabel7.setForeground(Color.black);
		jLabel7.setText("存放路径：");
		jLabel9.setFont(font);
		jLabel9.setText("默认序列名：");
		sequece.setText("");
		packagePath.setText("");
		jButton3.setFont(font);
		jButton3.setText("浏  览");
		jButton3
		.addActionListener(new ConfigFrame_jButton3_actionAdapter(this));
		path.setText("");
		IP.setColumns(20);
		this.setFont(font);
		this.setForeground(Color.black);
		this.setModal(true);
		this.setResizable(false);
		cancel.setFont(font);
		cancel.setText("取  消");
		cancel.addActionListener(new ConfigFrame_cancel_actionAdapter(this));
		jLabel8.setText(" ");
		jRadioOracle.setFont(font);
		jRadioOracle.setText("Oracle数据库");
		jRadioOracle
		.addActionListener(new ConfigFrame_jRadioOracle_actionAdapter(
				this));
		jRadioSql.setFont(font);
		jRadioSql.setToolTipText("");
		jRadioSql.setText("SqlServer数据库");
		jRadioSql.addActionListener(new ConfigFrame_jRadioSql_actionAdapter(
				this));
		jLabel10.setText("数据库端口：");
		jLabel11.setText("数据库名/SID:");
		jLabel10.setFont(font);
		jLabel11.setFont(font);
		sid.setText("");
		port.setText("");
		sid.setFont(font);
		port.setFont(font);
		jRadioMysql.setText("MySql数据库");
		jRadioMysql
		.addActionListener(new ConfigFrame_jRadioMysql_actionAdapter(
				this));
		jRadioMysql.setFont(font);

		jLabel12.setText("项目名称：");
		jLabel13.setText("项目表前缀:");
		jLabel12.setFont(font);
		jLabel13.setFont(font);
		jLabel14.setText("基类关联的列:");
		jLabel14.setFont(font);

		this.projectName.setText("");
		this.tablePrefix.setText("");
		this.excColumn.setText("");
		this.excColumn.setColumns(10);

		this.getContentPane().add(
				jLabel1,
				new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jLabel2,
				new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 6));
		this.getContentPane().add(
				jLabel3,
				new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 12));
		this.getContentPane().add(
				jLabel4,
				new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 11));
		this.getContentPane().add(
				jLabel5,
				new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 11));
		this.getContentPane().add(
				jLabel6,
				new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 2));
		this.getContentPane().add(
				jLabel7,
				new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 4));
		this.getContentPane().add(
				jLabel9,
				new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				packagePath,
				new GridBagConstraints(2, 8, 2, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				path,
				new GridBagConstraints(2, 9, 2, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				passWd,
				new GridBagConstraints(2, 3, 3, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				sequece,
				new GridBagConstraints(2, 4, 3, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				IP,
				new GridBagConstraints(2, 5, 3, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				userName,
				new GridBagConstraints(2, 2, 3, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jButton3,
				new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, -5));
		this.getContentPane().add(
				cancel,
				new GridBagConstraints(2, 13, 1, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 3), 0, -5));
		this.getContentPane().add(
				jLabel8,
				new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 20));
		this.getContentPane().add(
				jButton2,
				new GridBagConstraints(3, 13, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, -5));
		this.getContentPane().add(
				jButton1,
				new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, -5));
		this.getContentPane().add(
				jRadioOracle,
				new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jRadioSql,
				new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jLabel10,
				new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jLabel11,
				new GridBagConstraints(3, 6, 1, 2, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				port,
				new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				sid,
				new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 40, 0));
		this.getContentPane().add(
				jRadioMysql,
				new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));

		//项目信息
		this.getContentPane().add(
				jLabel12,
				new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				jLabel13,
				new GridBagConstraints(3, 11, 1, 2, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));

		this.getContentPane().add(
				this.projectName,
				new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				this.tablePrefix,
				new GridBagConstraints(4, 11, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 40, 0));

		this.getContentPane().add(
				jLabel14,
				new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(
				this.excColumn,
				new GridBagConstraints(2, 12, 3, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));


		userName.setColumns(10);
		passWd.setColumns(10);
		sequece.setColumns(10);
		packagePath.setColumns(10);
		path.setColumns(10);
		buttonGroup1.add(this.jRadioOracle);
		buttonGroup1.add(this.jRadioSql);
		buttonGroup1.add(this.jRadioMysql);
		this.initParam();
	}

	/**
	 * 初始化参数
	 */
	private void initParam() {
		Config config = Config.getInstance();
		this.packagePath.setText(config.getPackagePath());
		this.passWd.setText(config.getDbPasswd());
		this.userName.setText(config.getDbUsername());
		this.sequece.setText(config.getSequence());
		this.IP.setText(config.getDbIP());
		this.port.setText(config.getDbPort());
		this.sid.setText(config.getDbSID());
		this.path.setText(config.getPath());
		if (config.getDataBaseType() == 1) {
			this.jRadioSql.setSelected(true);
			this.sequece.disable();
		} else if (config.getDataBaseType() == 2) {
			this.jRadioMysql.setSelected(true);
			this.sequece.disable();
		} else {
			this.jRadioOracle.setSelected(true);
			this.sequece.enable(true);
			this.sequece.setBackground(Color.white);
		}
		this.projectName.setText(config.getProjectName());
		this.tablePrefix.setText(config.getTablePrefix());
		this.excColumn.setText(config.getExcColumn());
	}

	public void actionPerformed(ActionEvent event) {

	}

	/**
	 * 确定事件
	 * 
	 * @param e
	 */
	void jButton1_actionPerformed(ActionEvent e) {
		Config config = Config.getInstance();
		config.setDbIP(this.IP.getText());
		config.setDbPort(this.port.getText());
		config.setDbSID(this.sid.getText());
		config.setDbPasswd(new String(this.passWd.getPassword()));
		config.setDbUsername(this.userName.getText());
		config.setPackagePath(this.packagePath.getText());
		config.setPath(this.path.getText());
		config.setSequence(this.sequece.getText());

		config.setProjectName(this.projectName.getText());
		config.setTablePrefix(this.tablePrefix.getText());
		config.setExcColumn(this.excColumn.getText());
		if (this.jRadioSql.isSelected())
			config.setDataBaseType(1);
		else if (this.jRadioMysql.isSelected())
			config.setDataBaseType(2);
		else
			config.setDataBaseType(0);
		this.dispose();
		this.setVisible(false);
	}

	/**
	 * 重置事件
	 * 
	 * @param e
	 */
	void jButton2_actionPerformed(ActionEvent e) {
		this.initParam();
	}

	/**
	 * 浏览文件事件
	 * 
	 * @param e
	 */
	void jButton3_actionPerformed(ActionEvent e) {
		JFileChooser jFileChooser1 = new JFileChooser();
		jFileChooser1.setFont(new java.awt.Font("Dialog", 0, 12));
		jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser1.setCurrentDirectory(new File(this.path.getText()));

		int state = jFileChooser1.showOpenDialog(this.getContentPane());
		if (state == JFileChooser.CANCEL_OPTION) {
			// 撤消操作
		} else {
			File file = jFileChooser1.getSelectedFile();
			if (file != null && file.isDirectory()) {
				this.path.setText(file.getAbsolutePath());
			}
		}
	}

	void cancel_actionPerformed(ActionEvent e) {
		this.dispose();
		this.setVisible(false);
	}

	void jRadioOracle_actionPerformed(ActionEvent e) {
		this.sequece.enable(true);
		this.sequece.setBackground(Color.white);
	}

	void jRadioSql_actionPerformed(ActionEvent e) {
		this.sequece.enable(false);
		this.sequece.setBackground(Color.lightGray);
	}

	void jRadioMysql_actionPerformed(ActionEvent e) {
		this.sequece.enable(false);
		this.sequece.setBackground(Color.lightGray);

	}

}

class ConfigFrame_jButton1_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee = null;

	ConfigFrame_jButton1_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {

		adaptee.jButton1_actionPerformed(e);
	}
}

class ConfigFrame_jButton2_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_jButton2_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}

}

class ConfigFrame_jButton3_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_jButton3_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton3_actionPerformed(e);
	}
}

class ConfigFrame_cancel_actionAdapter implements java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_cancel_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.cancel_actionPerformed(e);
	}
}

class ConfigFrame_jRadioOracle_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_jRadioOracle_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jRadioOracle_actionPerformed(e);
	}
}

class ConfigFrame_jRadioSql_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_jRadioSql_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jRadioSql_actionPerformed(e);
	}
}

class ConfigFrame_jRadioMysql_actionAdapter implements
java.awt.event.ActionListener {
	ConfigFrame adaptee;

	ConfigFrame_jRadioMysql_actionAdapter(ConfigFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jRadioMysql_actionPerformed(e);
	}
}