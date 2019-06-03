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

	
		public Resign() {                                           //注册CatResign窗口类构造器
			//创建窗口
			setTitle("新用户注册");                    //设置标题
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(500,500);
			setLocationRelativeTo(null); 
			
			//插入背景
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

			//布局管理器GirdBagLayout
		    GridBagLayout gridBagLayout=new GridBagLayout();   //实例化对象布局
		    setLayout(gridBagLayout);                         //窗体对象设置为GridBagLayout布局
		    GridBagConstraints gbc=new  GridBagConstraints();   //实例化gbc这个对象用来对组件进行管理
		    gbc.fill=GridBagConstraints.NONE;
		    
		   //设置容器con
		    Container con=getContentPane();               //创建属于窗口f的容器
		    con.setLayout(gridBagLayout);                 //容器对象设置为GridBagLayout布局
		    
		  //主题
		    JPanel pan1=new JPanel();   
		    JLabel title=new JLabel("新用户注册");    
			title.setFont(new Font("宋体",Font.BOLD, 28));
			gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=0;
			pan1.add(title);
			gridBagLayout.setConstraints(pan1, gbc);
			con.add(pan1);
			
			//用户名
		    JPanel pan2=new JPanel();
		    pan2.add(new Label("用户名"));
		    TextField newUserName=new TextField(20);
		    pan2.add(newUserName);
			gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=3;
		    gridBagLayout.setConstraints(pan2, gbc);
			con.add(pan2);
			
			//密码1
			JPanel pan3=new JPanel();
			pan3.add(new Label("新密码   "));
			JPasswordField password1=new JPasswordField(15);
			password1.setEchoChar('*');
			pan3.add(password1);
			gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=5;
		    gridBagLayout.setConstraints(pan3,gbc);
		    con.add(pan3);
		    
		    //密码2
			JPanel pan4=new JPanel();
			pan4.add(new Label("新密码   "));
			JPasswordField password2=new JPasswordField(15);
			password2.setEchoChar('*');
			pan4.add(password2);
			gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=6;
		    gridBagLayout.setConstraints(pan4,gbc);
		    con.add(pan4);
		    
		    //注册
			JPanel pan5=new JPanel();
			JButton btnNewButton = new JButton("注册");
			JButton btnNewButton_1 = new JButton("返回");
			pan5.add(btnNewButton);
		    pan5.add(btnNewButton_1);
		    gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=7;
		    gridBagLayout.setConstraints(pan5, gbc);
		    con.add(pan5);
		    
		    //返回按钮监听
		    btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnNewButton_1.setEnabled(false);
					//返回登陆界面
					Login frame = new Login();
					frame.setVisible(true);
					setVisible(false);
				}
			});
		    
		    
		  //注册时提示信息
		    JPanel pan6=new JPanel();
		    JLabel lblNewLabel = new JLabel();
		    pan6.add(lblNewLabel);
		    gbc.gridwidth=1;                                        //组件宽
		    gbc.gridheight=1;                                       //组件高
		    gbc.gridx=0;                                            //组件相对横坐标
		    gbc.gridy=8;
		    gridBagLayout.setConstraints(pan6, gbc);
			con.add(pan6);
		    //注册按钮监听
		    btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Properties userPro = new Properties();
					File file = new File("Users.properties");
					Util.loadPro(userPro, file);
					
					String u_name = newUserName.getText();
					String u_pwd = new String(password1.getPassword());
					String u_pwd_ag = new String(password2.getPassword());

					// 判断用户名是否在普通用户中已存在
					if (u_name.length() != 0) {
						
						if (userPro.containsKey(u_name)) {
							lblNewLabel.setText("用户名已存在!");
						} else {
							isPassword(userPro, file, u_name, u_pwd, u_pwd_ag);
						}
					} else {
						lblNewLabel.setText("用户名不能为空！");
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
							//返回登陆界面
							Login frame = new Login();
							frame.setVisible(true);
							setVisible(false);
						} 
						else 
						{
							lblNewLabel.setText("密码为空！");
						}
					} 
					else 
					{
						lblNewLabel.setText("密码不一致！");
					}
				}
			});
		

}
}