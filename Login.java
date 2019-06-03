package login;

//登陆界面
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
		//创建窗口
		setTitle("登陆");
		setSize(500,500);                 
		setLocationRelativeTo(null);      
		setVisible(true);   
	    addWindowListener(new shutdown());    //窗口关闭
	    
	    //布局管理器GirdBagLayout
	    GridBagLayout gridBagLayout=new GridBagLayout();   //实例化对象布局
	    setLayout(gridBagLayout);                         //窗体对象设置为GridBagLayout布局
	    GridBagConstraints gbc=new  GridBagConstraints();   //实例化gbc这个对象用来对组件进行管理
	    gbc.fill=GridBagConstraints.NONE;
	    
	  //设置容器con
	    Container con=getContentPane();               //创建属于窗口f的容器
	    con.setLayout(gridBagLayout);                   //容器对象设置为GridBagLayout布局
	    
		
		//主题
	    JPanel pan1=new JPanel();   
	    Label title=new Label("  欢迎登陆本系统  ");    
		title.setFont(new Font("宋体",Font.BOLD, 30));
		gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=1;                                       //组件高
	    gbc.gridx=2;                                            //组件相对横坐标
	    gbc.gridy=1;
		pan1.add(title);
		gridBagLayout.setConstraints(pan1, gbc);
		con.add(pan1);
		
		//用户名
	    JPanel pan2=new JPanel();
		pan2.add(new Label("用户名"));
		TextField userName=new TextField(20);
		pan2.add(userName);
		gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=1;                                       //组件高
	    gbc.gridx=3;                                            //组件相对横坐标
	    gbc.gridy=3;
	    gridBagLayout.setConstraints(pan2, gbc);
		con.add(pan2);
		
		//密码
		JPanel pan3=new JPanel();
		pan3.add(new Label("密码   "));
		JPasswordField password=new JPasswordField(15);
		password.setEchoChar('*');
		pan3.add(password);
		gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=1;                                       //组件高
	    gbc.gridx=3;                                            //组件相对横坐标
	    gbc.gridy=4;
	    gridBagLayout.setConstraints(pan3,gbc);
	    con.add(pan3);
		
		//登陆注册按钮
		JPanel pan4=new JPanel();
		JButton btnNewButton = new JButton("登陆");
		JButton btnNewButton_1 = new JButton("注册");
		pan4.add(btnNewButton);
	    pan4.add(btnNewButton_1);
	    gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=1;                                       //组件高
	    gbc.gridx=3;                                            //组件相对横坐标
	    gbc.gridy=5;
	    gridBagLayout.setConstraints(pan4, gbc);
	    con.add(pan4);
	    
	    //Logo
	    ImageIcon icon = new ImageIcon("images\\timg.jpg");             //插入图片到
	    JLabel labicon = new JLabel(icon);                      //用标签接收图片
	    gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=2;                                       //组件高
	    gbc.gridx=1;                                            //组件相对横坐标
	    gbc.gridy=3;
	    gridBagLayout.setConstraints(labicon, gbc);
	    con.add(labicon);
	
	   
	    //注册按钮监听
	    btnNewButton_1.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) {
	    		btnNewButton_1.setEnabled(false);
	    		Resign frame = new Resign();  //创建注册窗口界面
	    		frame.setVisible(true);// 显示注册界面
	    		setVisible(false);// 隐藏掉登陆界面
	    	}
	    });
	    
	 
	    //提示信息lblNewLabel	
	    final JLabel lblNewLabel = new JLabel();
	    lblNewLabel.setForeground(Color.red);
	    gbc.gridwidth=2;                                        //组件宽
	    gbc.gridheight=1;                                       //组件高
	    gbc.gridx=3;                                            //组件相对横坐标
	    gbc.gridy=6;
	    gridBagLayout.setConstraints(lblNewLabel, gbc);
	 	con.add(lblNewLabel);
	 	
	 	
	    // 登陆按钮btnNewButton监听
	 	btnNewButton.addActionListener(new ActionListener() 
	 	{
	 		public void actionPerformed(ActionEvent e) 
	 		{
	 				Properties userPro = new Properties();
	 				File file = new File("Users.properties");
	 				Util.loadPro(userPro, file);
	 				String u_name = userName.getText();
	 				if (file.length() != 0) {

	 					if (userPro.containsKey(u_name))    //用户名存在
	 					{
	 						String u_pwd = new String(password.getPassword());
	 						if (u_pwd.equals(userPro.getProperty(u_name)))    //密码正确
	 						{

	 							try {
	 								Socket client = new Socket("localhost", 8520); //创建客服端，接入端口8520 ip需改为服务器ipv4地址

	 								btnNewButton.setEnabled(false);
	 								Chatroom frame = new Chatroom(u_name, client);//创建聊天窗口界面
	 								frame.setVisible(true);// 显示聊天界面
	 								setVisible(false);// 隐藏掉登陆界面

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
	 							lblNewLabel.setText("您输入的密码有误！");
	 							userName.setText("");
	 							password.setText("");
	 							userName.requestFocus();
	 						}
	 					} 
	 					else 
	 					{
	 						lblNewLabel.setText("您输入昵称不存在！");
	 						userName.setText("");
	 						password.setText("");
	 						userName.requestFocus();
	 					}
	 				} 
	 				else 
	 				{
	 					lblNewLabel.setText("您输入昵称不存在！");
	 					userName.setText("");
	 					password.setText("");
	 					userName.requestFocus();
	 				}
	 			}
	 		});

	 
	
	}
}

//监听（关闭窗口）
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


