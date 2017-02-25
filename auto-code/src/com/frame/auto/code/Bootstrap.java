package com.frame.auto.code;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.javabean.Log;
import com.frame.auto.code.main.About;
import com.frame.auto.code.main.ConfigFrame;
import com.frame.auto.code.main.ConfigTable;
import com.frame.auto.code.main.MakeSource;

/**
 * <p>代码自动生成工具主程序</p>
 * <p>Description: 主要应用于oracle,mysql,sqlserver数据库数据基本操作</p>
 * @author yuejing
 * @version 1.0
 */

public class Bootstrap extends JFrame implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 4041410459161103142L;
	
	private JMenuBar menuBar = null;
	private JMenu menuFile = null;
	private JMenu menuEdit = null;
	private JMenu menuHelp = null;
	private JMenuItem miStart = null;
	private JMenuItem miExit = null;
	private JMenuItem miConfig = null;
	private JMenuItem miSelectTable = null;
	private JMenuItem miAbout = null;
	private BorderLayout borderLayout = new BorderLayout();
	JTextArea taInfo = new JTextArea();

	private JScrollPane jScrollPane1 = new JScrollPane(taInfo);
	protected ConfigFrame configFrame = null;
	protected ConfigTable configTable = null;
	protected About about = null;
	private MakeSource m = null;
	public Bootstrap() {
		Config.configName = FrameConfig.CONFIG_NAME;
		this.getContentPane().setLayout(borderLayout);
		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.pack();
		//设置显示大小和位置
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice();
		DisplayMode d = device.getDisplayMode();
		int width = 667, height = 500;
		this.setBounds( (int) (d.getWidth() - width) / 2,
				(int) (d.getHeight() - height) / 2, width, height);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Bootstrap();
	}

	private void jbInit() throws Exception {

		this.setTitle("Frame框架代码自动生成工具1.0");
		menuBar = new JMenuBar();
		menuFile = new JMenu("文件");
		menuEdit = new JMenu("编辑");
		menuHelp = new JMenu("帮助");

		taInfo.setText("欢迎使用yuejing制作的源代码生成工具!");
		Font font = new Font("Dialog", 0, 14);
		taInfo.setRows(20);
		taInfo.setColumns(50);
		taInfo.setAutoscrolls(true);
		taInfo.setEnabled(true);
		taInfo.setFont(new java.awt.Font("Dialog", 0, 12));

		miStart = new JMenuItem("开始");
		miExit = new JMenuItem("退出");
		miConfig = new JMenuItem("配置");
		miSelectTable = new JMenuItem("选取表");
		miAbout = new JMenuItem("关于我们");
		miAbout.setFont(font);
		menuFile.setFont(font);
		miExit.setFont(font);
		menuEdit.setFont(font);
		miConfig.setFont(font);
		menuHelp.setFont(font);
		miStart.setFont(font);
		miSelectTable.setFont(font);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuHelp);

		menuFile.add(miStart);
		menuFile.add(miExit);
		menuEdit.add(miConfig);
		menuEdit.add(miSelectTable);
		menuHelp.add(miAbout);

		miExit.addActionListener(this);
		miConfig.addActionListener(this);
		miAbout.addActionListener(this);
		this.miSelectTable.addActionListener(this);
		this.miStart.addActionListener(this);

		this.getContentPane().add(menuBar, BorderLayout.PAGE_START);
		this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		Config.getInstance().setLogArea(this.taInfo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.miExit) {
			Config.getInstance().writeToFile("./" + FrameConfig.CONFIG_NAME);
			Log.log("正在关闭...", Log.LEVEL_ERROR);
			if (m != null) {
				m.setFlag(1);
				m.interrupt();
			}
			this.dispose();
			this.setVisible(false);
			Log.log("关闭完成", Log.LEVEL_ERROR);
		}
		else if (event.getSource() == this.miConfig) {
			if (configFrame == null)
				configFrame = new ConfigFrame(this);
			this.configFrame.setBounds( (int) (this.getBounds().getX() + 50),
					(int) (this.getBounds().getY() + 50),
					(int) (this.getBounds().getWidth()),
					(int) (this.getBounds().getHeight())
					);
			this.configFrame.pack();
			this.configFrame.show(true);
		}
		else if (event.getSource() == this.miSelectTable) {
			if (configTable == null)
				configTable = new ConfigTable(this);
			this.configTable.setBounds( (int) (this.getBounds().getX() + 50),
					(int) (this.getBounds().getY() + 50),
					(int) (this.getBounds().getWidth()),
					(int) (this.getBounds().getHeight())
					);
			this.configTable.pack();
			this.configTable.show(true);
		}
		else if (event.getSource() == this.miStart) { //开始生成源代码
			this.run();
		}
		else if (event.getSource() == this.miAbout) {
			if (about == null)
				about = new About(this);
			this.about.setBounds( (int) (this.getBounds().getX() + 100),
					(int) (this.getBounds().getY() + 100),
					(int) (this.getBounds().getWidth()),
					(int) (this.getBounds().getHeight())
					);
			this.about.pack();
			this.about.show(true);
		}

	}
	/**
	 * 重载关闭的方法
	 * @param e
	 */
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			ActionEvent event = new ActionEvent(this.miExit,0,"");
			this.actionPerformed(event);
		}
		super.processWindowEvent(e);
	}

	public void run() {
		m = new MakeSource();
		m.start();
	}
}