/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package ui;

import helper.CwqHelper;

import java.awt.event.ItemEvent;
import java.io.IOException;

import javax.crypto.spec.IvParameterSpec;

import utils.FrameUtil;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

import data.Constant;

/**
 *
 * @author  __USER__
 */
public class MainFrame extends javax.swing.JFrame {

	/** Creates new form MainFrame */
	public MainFrame() {
		initComponents();
		initType();
		FrameUtil.initWindows(this);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jp_main = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		cb_type = new javax.swing.JComboBox();
		et_page = new javax.swing.JTextField();
		btn_start = new javax.swing.JButton();
		cb_area = new javax.swing.JComboBox();
		cb_push = new javax.swing.JComboBox();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		item_cwq = new javax.swing.JMenuItem();
		item_wx = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(500, 300));
		setResizable(false);

		jp_main.setMaximumSize(new java.awt.Dimension(500, 300));
		jp_main.setMinimumSize(new java.awt.Dimension(500, 300));
		jp_main.setLayout(new java.awt.GridLayout(1, 0));

		cb_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));
		cb_type.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cb_typeItemStateChanged(evt);
			}
		});

		et_page.setColumns(5);

		btn_start.setText("\u5f00\u59cb");
		btn_start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_startActionPerformed(evt);
			}
		});

		cb_area.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));
		cb_area.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cb_areaItemStateChanged(evt);
			}
		});

		cb_push.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"硬广", "软广" }));
		cb_push.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cb_pushItemStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addComponent(
												cb_type,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(43, 43, 43)
										.addComponent(
												cb_area,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												45, Short.MAX_VALUE)
										.addComponent(
												cb_push,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(42, 42, 42)
										.addComponent(
												et_page,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(32, 32, 32)
										.addComponent(btn_start)
										.addGap(33, 33, 33)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(26, 26, 26)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																cb_type,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cb_area,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(btn_start)
														.addComponent(
																et_page,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cb_push,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(353, Short.MAX_VALUE)));

		jp_main.add(jPanel1);

		jMenu1.setText("\u529f\u80fd");

		item_cwq.setText("\u57ce\u5916\u5708");
		item_cwq.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				item_cwqActionPerformed(evt);
			}
		});
		jMenu1.add(item_cwq);

		item_wx.setText("\u5fae\u4fe1");
		item_wx.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				item_wxActionPerformed(evt);
			}
		});
		jMenu1.add(item_wx);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jp_main,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(78, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jp_main, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void cb_pushItemStateChanged(java.awt.event.ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			initPush();
		}
	}

	private void cb_areaItemStateChanged(java.awt.event.ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			initArea();
		}
	}

	private void initPush() {
		type_push = cb_push.getSelectedIndex() + 1 + "";
	}

	private void initArea() {
		int index = cb_area.getSelectedIndex();
		if (index == 0) {
			area_id = "3412";
		} else {
			area_id = index + 1 + "";
		}
	}

	private void item_wxActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {
		start();
	}

	private void cb_typeItemStateChanged(java.awt.event.ItemEvent evt) {
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			initMyType();
		}
	}

	private void initMyType() {
		int index = cb_type.getSelectedIndex();
		type_id = Constant.LIST_TYPE_ID[index] + "";
		currentType = index + 1 + "";
	}

	void item_cwqActionPerformed(java.awt.event.ActionEvent evt) {
		CwqJpanel jp_cwq = new CwqJpanel();
		jp_cwq.setVisible(true);
		jp_main.removeAll();
		jp_main.add(jp_cwq);
		this.invalidate();
		this.repaint();
	}

	private void start() {
		initArea();
		initMyType();
		initPush();
		try {
			SFileUtil.readFileLine(SFileUtil.createDataFile("config_area"),
					new ReadListener() {

						@Override
						public void onRead(int index, String text) {
							int positon = text.indexOf("|");
							String id = text.substring(0, positon);
							if (id.equals(area_id)) {
								type_area = text.substring(text.indexOf("|") + 1);
								System.out.println("type_area" + type_area);
							}
						}

						@Override
						public void onFinish() {
							System.out.println("loading finish");
							CwqHelper helper = new CwqHelper();
							helper.setId(currentType);
							type_id = Constant.LIST_TYPE_ID[cb_type
									.getSelectedIndex()] + "";
							helper.setTypeId(type_id);
							helper.setArea(type_area);
							helper.setType(type_push);
							helper.setPage(Integer.valueOf(et_page.getText()));
							if (helper.login()) {
								helper.getWxData();
							}
						}

						@Override
						public void onFail() {
							System.out.println("fail to loading config file");
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initType() {
		cb_type.setModel(new javax.swing.DefaultComboBoxModel(
				Constant.LIST_TYPE));

		cb_area.setModel(new javax.swing.DefaultComboBoxModel(
				Constant.LIST_AREA));
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_start;
	private javax.swing.JComboBox cb_area;
	private javax.swing.JComboBox cb_push;
	private javax.swing.JComboBox cb_type;
	private javax.swing.JTextField et_page;
	private javax.swing.JMenuItem item_cwq;
	private javax.swing.JMenuItem item_wx;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jp_main;
	// End of variables declaration//GEN-END:variables

	private String currentType;
	private String type_id;
	private String type_area;
	private String type_push;
	private String area_id;
}