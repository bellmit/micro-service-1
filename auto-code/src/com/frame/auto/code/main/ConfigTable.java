package com.frame.auto.code.main;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.javabean.TableManager;
import com.frame.auto.code.javabean.TableManagerFactory;

/**
 * <p>Title:Java 代码自动生成工具</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public class ConfigTable
    extends JDialog  {
  GridBagLayout layOut = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
//  DefaultListModel dlm1=new DefaultListModel();
//  DefaultListModel dlm2=new DefaultListModel();
  JList jList1 = new JList( new DefaultListModel());
  JScrollPane jScrollPane1 = new JScrollPane(jList1);
  JList jList2 = new JList(new DefaultListModel());
  JScrollPane jScrollPane2 = new JScrollPane(jList2);
  JButton jbadd = new JButton();
  JButton jbmin = new JButton();
  JButton jBok = new JButton();
  JButton jBcan = new JButton();
  JButton jBreset = new JButton();

  public ConfigTable(Frame frame) {
    super(frame, "选取数据库表，以生成对应表的操作代码");
    this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    try {
      jbInit();
      this.init();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ConfigTable() {
    this(null);
  }

  private void jbInit() throws Exception {
    Font font = new Font("Dialog", 0, 14);
    Insets inserts=new Insets(0, 0, 0, 0);

    this.jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    this.jLabel2.setFont(font);
    this.jLabel3.setFont(font);
    this.jList1.setFont(font);
    this.jScrollPane1.setFont(font);
    this.jList2.setFont(font);
    this.jScrollPane2.setFont(font);
    this.jbadd.setFont(font);
    this.jbmin.setFont(font);
    this.jBok.setFont(font);
    this.jBcan.setFont(font);
    this.jBreset.setFont(font);
    jBreset.setToolTipText("");
    jBreset.setActionCommand("刷 新");

    jLabel1.setText("请选择需要操作的表");
    this.getContentPane().setLayout(layOut);
    jLabel2.setText("数据库表：");
    jLabel3.setText("已选择的表:");
    jList1.setFixedCellWidth(100);
    jList2.setFixedCellWidth(100);

    jbadd.setText("==>");
    jbadd.addActionListener(new ConfigTable_jbadd_actionAdapter(this));
    jbmin.setText("<==");
    jbmin.addActionListener(new ConfigTable_jbmin_actionAdapter(this));
    jBok.setText("确 定");
    jBok.addActionListener(new ConfigTable_jBok_actionAdapter(this));
    jBcan.setText(" 取 消");
    jBcan.addActionListener(new ConfigTable_jBcan_actionAdapter(this));
    jBreset.setText("刷 新");
    jBreset.addActionListener(new ConfigTable_jBreset_actionAdapter(this));
    this.getContentPane().add(jLabel1,
                              new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.getContentPane().add(jLabel3,
                              new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.getContentPane().add(jScrollPane1,
                               new GridBagConstraints(0, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 87, 142));
    this.getContentPane().add(jScrollPane2,
                               new GridBagConstraints(2, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 87, 142));
    this.getContentPane().add(jLabel2,
                              new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.getContentPane().add(jbmin,
                               new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(-1, 0, 1, 0), 0, 0));
    this.getContentPane().add(jbadd,
                               new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(49, 0, 0, 0), 0, 0));
    this.getContentPane().add(jBok, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.getContentPane().add(jBcan,
                              new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.getContentPane().add(jBreset,
                              new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        inserts, 0, 0));
    this.setModal(true);//前置
  }

  /**
   * 重置数据显示
   */
  private void init() {
    DefaultListModel  model1 = (DefaultListModel ) (jList1.getModel());
    DefaultListModel  model2 = (DefaultListModel ) (jList2.getModel());
    model1.clear();
    model2.clear();
    TableManager t = TableManagerFactory.getTableManager();
    String[] tables=t.getAllTableName();
    String[] stables=Config.getInstance().getTableList();
    for(int i=0;tables!=null&&i<tables.length;i++){
      model1.addElement(tables[i]);
    }
    for (int i = 0; stables != null && i < stables.length; i++) {
      model2.addElement(stables[i]);
    }
  }

  void jBcan_actionPerformed(ActionEvent e) {
    this.dispose();
    this.setVisible(false);
  }

  void jBreset_actionPerformed(ActionEvent e) {
    this.init();
  }

  void jBok_actionPerformed(ActionEvent e) {
    ListModel model =  jList2.getModel();
    String[] values = new String[model.getSize()];
    for (int i = 0; i < values.length; i++) {
      values[i] = (String) model.getElementAt(i);
    }
    Config.getInstance().setTableList(values);
    this.dispose();
    this.setVisible(false);
  }

  void jbadd_actionPerformed(ActionEvent e) {
    DefaultListModel  model1 = (DefaultListModel ) (jList1.getModel());
    DefaultListModel  model2 = (DefaultListModel ) (jList2.getModel());
    int[] indexs = jList1.getSelectedIndices();
    for (int i = 0; indexs != null && i < indexs.length; i++) {
      model2.addElement(model1.get(indexs[i]-i));
      model1.remove(indexs[i]-i);
    }
  }

  void jbmin_actionPerformed(ActionEvent e) {
    DefaultListModel model1 = (DefaultListModel) (jList1.getModel());
    DefaultListModel model2 = (DefaultListModel) (jList2.getModel());
    int[] indexs = jList2.getSelectedIndices();
    for (int i = 0; indexs != null && i < indexs.length; i++) {
      model1.addElement(model2.get(indexs[i]-i));
      model2.remove(indexs[i]-i);
    }
  }
}

class ConfigTable_jBcan_actionAdapter
    implements java.awt.event.ActionListener {
  ConfigTable adaptee;

  ConfigTable_jBcan_actionAdapter(ConfigTable adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBcan_actionPerformed(e);
  }
}

class ConfigTable_jBreset_actionAdapter
    implements java.awt.event.ActionListener {
  ConfigTable adaptee;

  ConfigTable_jBreset_actionAdapter(ConfigTable adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBreset_actionPerformed(e);
  }
}

class ConfigTable_jBok_actionAdapter
    implements java.awt.event.ActionListener {
  ConfigTable adaptee;

  ConfigTable_jBok_actionAdapter(ConfigTable adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBok_actionPerformed(e);
  }
}

class ConfigTable_jbadd_actionAdapter
    implements java.awt.event.ActionListener {
  ConfigTable adaptee;

  ConfigTable_jbadd_actionAdapter(ConfigTable adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jbadd_actionPerformed(e);
  }
}

class ConfigTable_jbmin_actionAdapter
    implements java.awt.event.ActionListener {
  ConfigTable adaptee;

  ConfigTable_jbmin_actionAdapter(ConfigTable adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jbmin_actionPerformed(e);
  }
}