package login;

//��½����
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import client.Chatroom;
import util.Util;



public class Login extends JFrame {
	public static void main(String[] args) {
		Login frame = new Login();
		frame.setVisible(true);
	}
	
	public Login()
	{
		//��������
		setTitle("��½");
		setSize(500,500);                 
		setLocationRelativeTo(null);      
		setVisible(true);   
	    addWindowListener(new shutdown());    //���ڹر�
	    
	    //���ֹ�����GirdBagLayout
	    GridBagLayout gridBagLayout=new GridBagLayout();   //ʵ�������󲼾�
	    setLayout(gridBagLayout);                         //�����������ΪGridBagLayout����
	    GridBagConstraints gbc=new  GridBagConstraints();   //ʵ����gbc�������������������й���
	    gbc.fill=GridBagConstraints.NONE;
	    
	  //��������con
	    Container con=getContentPane();               //�������ڴ���f������
	    con.setLayout(gridBagLayout);                   //������������ΪGridBagLayout����
	    
		
		//����
	    JPanel pan1=new JPanel();   
	    Label title=new Label("  ��ӭ��½��ϵͳ  ");    
		title.setFont(new Font("����",Font.BOLD, 30));
		gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=1;                                       //�����
	    gbc.gridx=2;                                            //�����Ժ�����
	    gbc.gridy=1;
		pan1.add(title);
		gridBagLayout.setConstraints(pan1, gbc);
		con.add(pan1);
		
		//�û���
	    JPanel pan2=new JPanel();
		pan2.add(new Label("�û���"));
		TextField userName=new TextField(20);
		pan2.add(userName);
		gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=1;                                       //�����
	    gbc.gridx=3;                                            //�����Ժ�����
	    gbc.gridy=3;
	    gridBagLayout.setConstraints(pan2, gbc);
		con.add(pan2);
		
		//����
		JPanel pan3=new JPanel();
		pan3.add(new Label("����   "));
		JPasswordField password=new JPasswordField(15);
		password.setEchoChar('*');
		pan3.add(password);
		gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=1;                                       //�����
	    gbc.gridx=3;                                            //�����Ժ�����
	    gbc.gridy=4;
	    gridBagLayout.setConstraints(pan3,gbc);
	    con.add(pan3);
		
		//��½ע�ᰴť
		JPanel pan4=new JPanel();
		JButton btnNewButton = new JButton("��½");
		JButton btnNewButton_1 = new JButton("ע��");
		pan4.add(btnNewButton);
	    pan4.add(btnNewButton_1);
	    gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=1;                                       //�����
	    gbc.gridx=3;                                            //�����Ժ�����
	    gbc.gridy=5;
	    gridBagLayout.setConstraints(pan4, gbc);
	    con.add(pan4);
	    
	    //Logo
	    ImageIcon icon = new ImageIcon("images\\timg.jpg");             //����ͼƬ��
	    JLabel labicon = new JLabel(icon);                      //�ñ�ǩ����ͼƬ
	    gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=2;                                       //�����
	    gbc.gridx=1;                                            //�����Ժ�����
	    gbc.gridy=3;
	    gridBagLayout.setConstraints(labicon, gbc);
	    con.add(labicon);
	
	   
	    //ע�ᰴť����
	    btnNewButton_1.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) {
	    		btnNewButton_1.setEnabled(false);
	    		Resign frame = new Resign();  //����ע�ᴰ�ڽ���
	    		frame.setVisible(true);// ��ʾע�����
	    		setVisible(false);// ���ص���½����
	    	}
	    });
	    
	 
	    //��ʾ��ϢlblNewLabel	
	    final JLabel lblNewLabel = new JLabel();
	    lblNewLabel.setForeground(Color.red);
	    gbc.gridwidth=2;                                        //�����
	    gbc.gridheight=1;                                       //�����
	    gbc.gridx=3;                                            //�����Ժ�����
	    gbc.gridy=6;
	    gridBagLayout.setConstraints(lblNewLabel, gbc);
	 	con.add(lblNewLabel);
	 	
	 	
	    // ��½��ťbtnNewButton����
	 	btnNewButton.addActionListener(new ActionListener() 
	 	{
	 		public void actionPerformed(ActionEvent e) 
	 		{
	 				Properties userPro = new Properties();
	 				File file = new File("Users.properties");
	 				Util.loadPro(userPro, file);
	 				String u_name = userName.getText();
	 				if (file.length() != 0) {

	 					if (userPro.containsKey(u_name))    //�û�������
	 					{
	 						String u_pwd = new String(password.getPassword());
	 						if (u_pwd.equals(userPro.getProperty(u_name)))    //������ȷ
	 						{

	 							try {
	 								Socket client = new Socket("localhost", 8520); //�����ͷ��ˣ�����˿�8520 ip���Ϊ������ipv4��ַ

	 								btnNewButton.setEnabled(false);
	 								Chatroom frame = new Chatroom(u_name, client);//�������촰�ڽ���
	 								frame.setVisible(true);// ��ʾ�������
	 								setVisible(false);// ���ص���½����

	 							} catch (UnknownHostException e1) {
	 								// TODO Auto-generated catch block
	 								//errorTip("The connection with the server is interrupted, please login again");
	 							} catch (IOException e1) {
	 								// TODO Auto-generated catch block
	 								//errorTip("The connection with the server is interrupted, please login again");
	 							}

	 						} 
	 						else    
	 						{
	 							lblNewLabel.setText("���������������");
	 							userName.setText("");
	 							password.setText("");
	 							userName.requestFocus();
	 						}
	 					} 
	 					else 
	 					{
	 						lblNewLabel.setText("�������ǳƲ����ڣ�");
	 						userName.setText("");
	 						password.setText("");
	 						userName.requestFocus();
	 					}
	 				} 
	 				else 
	 				{
	 					lblNewLabel.setText("�������ǳƲ����ڣ�");
	 					userName.setText("");
	 					password.setText("");
	 					userName.requestFocus();
	 				}
	 			}
	 		});

	 
	
	}
}

//�������رմ��ڣ�
class shutdown implements WindowListener
{
	public void windowClosing(WindowEvent e)
	{
		((Window)e.getComponent()).dispose();
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}


