/*
 * WXFragme.java
 *
 * Created on __DATE__, __TIME__
 */

package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;

import org.bson.Document;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.WebUtil;

import com.mongodb.BasicDBObject;

import db.BaseMonGoDB;
import helper.WXhelper;

/**
 *
 * @author  __USER__
 */
public class WXFragme extends javax.swing.JFrame {

	/** Creates new form WXFragme */
	public WXFragme() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		btn_start = new javax.swing.JButton();
		et_code = new javax.swing.JTextField();
		lb_img = new javax.swing.JLabel();
		tv_result = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		btn_start.setText("\u5f00");
		btn_start.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btn_startMouseClicked(evt);
			}
		});

		et_code.setColumns(6);

		tv_result.setText("  ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(66, 66, 66)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(tv_result)
												.addComponent(lb_img))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										130, Short.MAX_VALUE)
								.addComponent(et_code,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(36, 36, 36).addComponent(btn_start)
								.addGap(43, 43, 43)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(31, 31, 31)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btn_start)
												.addComponent(
														et_code,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(lb_img))
								.addGap(73, 73, 73).addComponent(tv_result)
								.addContainerGap(154, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btn_startMouseClicked(java.awt.event.MouseEvent evt) {
		FileInputStream fis;
		if(et_code.getText().length() > 0) {
			try {
				new WXhelper().soso(et_code.getText());
				Thread.sleep(5 * 1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fis = new FileInputStream(new File("d:/abc.txt"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
			  
			  int i = 0;
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						String line;
						try {
							while ((line = br.readLine()) != null) {   
								if(line.trim().equals(last_name)) {
									 isrun = true;
								}
								if(isrun) {
									try {
										currentname = line.trim();
										    tv_result.setText("���ڻ�ȡ:" + line.trim());
											JSONObject json = new WXhelper().getSearchList(line.trim());
											Document doc_main = new Document();
											doc_main.put("account", BasicDBObject.parse(json.toString()));
											BaseMonGoDB.getInstance().insertInfo(doc_main);
										} catch (FibdException e) {
											File file = FileUtil.createCodeFile();
											try {
												WebUtil.downImage(e.getUrl(), file.getName(), file.getParent());
											
												ImageIcon icon = new ImageIcon(file.getPath());
												lb_img.setIcon(icon);
												last_name = line.trim();
												break;
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										} catch (AccountErrorException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} finally {
											try {
												Thread.sleep(5 * 1000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
								}
								
						  }
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				
			  
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new WXFragme().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_start;
	private javax.swing.JTextField et_code;
	private javax.swing.JLabel lb_img;
	private javax.swing.JLabel tv_result;
	// End of variables declaration//GEN-END:variables
	
	public static String last_name = "chinanewsweekly";
	private boolean isrun = false;
	private String currentname = "";

}