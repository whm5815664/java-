package login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
//
import login.Login;
import util.Util;


public class Resign extends JFrame{

	
		public Resign() {                                           //ע��CatResign�����๹����
			//��������
			setTitle("���û�ע��");                    //���ñ���
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(500,500);
			setLocationRelativeTo(null); 
			
			//���뱳��
			JPanel contentPane = new JPanel() {
				private static final long serialVersionUID = 1L;
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(new ImageIcon("images\\Resign.jpg").getImage(), 0, 0,
							getWidth(), getHeight(), null);
				}

			};
			setContentPane(contentPane);
			contentPane.setLayout(null);

			//���ֹ�����GirdBagLayout
		    GridBagLayout gridBagLayout=new GridBagLayout();   //ʵ�������󲼾�
		    setLayout(gridBagLayout);                         //�����������ΪGridBagLayout����
		    GridBagConstraints gbc=new  GridBagConstraints();   //ʵ����gbc�������������������й���
		    gbc.fill=GridBagConstraints.NONE;
		    
		   //��������con
		    Container con=getContentPane();               //�������ڴ���f������
		    con.setLayout(gridBagLayout);                 //������������ΪGridBagLayout����
		    
		  //����
		    JPanel pan1=new JPanel();   
		    JLabel title=new JLabel("���û�ע��");    
			title.setFont(new Font("����",Font.BOLD, 28));
			gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=0;
			pan1.add(title);
			gridBagLayout.setConstraints(pan1, gbc);
			con.add(pan1);
			
			//�û���
		    JPanel pan2=new JPanel();
		    pan2.add(new Label("�û���"));
		    TextField newUserName=new TextField(20);
		    pan2.add(newUserName);
			gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=3;
		    gridBagLayout.setConstraints(pan2, gbc);
			con.add(pan2);
			
			//����1
			JPanel pan3=new JPanel();
			pan3.add(new Label("������   "));
			JPasswordField password1=new JPasswordField(15);
			password1.setEchoChar('*');
			pan3.add(password1);
			gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=5;
		    gridBagLayout.setConstraints(pan3,gbc);
		    con.add(pan3);
		    
		    //����2
			JPanel pan4=new JPanel();
			pan4.add(new Label("������   "));
			JPasswordField password2=new JPasswordField(15);
			password2.setEchoChar('*');
			pan4.add(password2);
			gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=6;
		    gridBagLayout.setConstraints(pan4,gbc);
		    con.add(pan4);
		    
		    //ע��
			JPanel pan5=new JPanel();
			JButton btnNewButton = new JButton("ע��");
			JButton btnNewButton_1 = new JButton("����");
			pan5.add(btnNewButton);
		    pan5.add(btnNewButton_1);
		    gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=7;
		    gridBagLayout.setConstraints(pan5, gbc);
		    con.add(pan5);
		    
		    //���ذ�ť����
		    btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnNewButton_1.setEnabled(false);
					//���ص�½����
					Login frame = new Login();
					frame.setVisible(true);
					setVisible(false);
				}
			});
		    
		    
		  //ע��ʱ��ʾ��Ϣ
		    JPanel pan6=new JPanel();
		    JLabel lblNewLabel = new JLabel();
		    pan6.add(lblNewLabel);
		    gbc.gridwidth=1;                                        //�����
		    gbc.gridheight=1;                                       //�����
		    gbc.gridx=0;                                            //�����Ժ�����
		    gbc.gridy=8;
		    gridBagLayout.setConstraints(pan6, gbc);
			con.add(pan6);
		    //ע�ᰴť����
		    btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Properties userPro = new Properties();
					File file = new File("Users.properties");
					Util.loadPro(userPro, file);
					
					String u_name = newUserName.getText();
					String u_pwd = new String(password1.getPassword());
					String u_pwd_ag = new String(password2.getPassword());

					// �ж��û����Ƿ�����ͨ�û����Ѵ���
					if (u_name.length() != 0) {
						
						if (userPro.containsKey(u_name)) {
							lblNewLabel.setText("�û����Ѵ���!");
						} else {
							isPassword(userPro, file, u_name, u_pwd, u_pwd_ag);
						}
					} else {
						lblNewLabel.setText("�û�������Ϊ�գ�");
					}
				}
				
				
				private void isPassword(Properties userPro,File file, String u_name, String u_pwd, String u_pwd_ag) 
				{
					if (u_pwd.equals(u_pwd_ag)) 
					{
						if (u_pwd.length() != 0) 
						{
							userPro.setProperty(u_name, u_pwd_ag);
							try {
								userPro.store(new FileOutputStream(file),
										"Copyright (c) Boxcode Studio");
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							btnNewButton_1.setEnabled(false);
							//���ص�½����
							Login frame = new Login();
							frame.setVisible(true);
							setVisible(false);
						} 
						else 
						{
							lblNewLabel.setText("����Ϊ�գ�");
						}
					} 
					else 
					{
						lblNewLabel.setText("���벻һ�£�");
					}
				}
			});
		

}
}