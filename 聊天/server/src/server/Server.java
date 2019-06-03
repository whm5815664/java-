package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import function.Transfer;
import function.Client;

public class Server 
{
	private static ServerSocket ss;
	public static HashMap<String, Client> onlines;
	static {
		try {
			ss = new ServerSocket(8520);                       //创建服务端
			onlines = new HashMap<String, Client>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ClientThread extends Thread {
		private Socket client;
		private Transfer bean;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public ClientThread(Socket client) {
			this.client = client;
		}

		@Override
		public void run() 
		{
			try {
				// 不停的从客户端接收信息
				while (true) {
					// 读取从客户端接收到的Transfer信息
					ois = new ObjectInputStream(client.getInputStream());
					bean = (Transfer)ois.readObject();
					
					// 分析Transfer中，type是那样一种类型
					switch (bean.getType()) {
					// 上下线更新
					case 0: { // 上线
						// 记录上线客户的用户名和端口在client中
						Client cbean = new Client();
						cbean.setName(bean.getName());
						cbean.setSocket(client);
						// 添加在线用户
						onlines.put(bean.getName(), cbean);
						// 创建服务器的Transfer，并发送给客户端
						Transfer serverBean = new Transfer();
						serverBean.setType(0);
						serverBean.setInfo(bean.getTimer() + "  "
								+ bean.getName() + "上线了");
						// 通知所有客户有人上线
						HashSet<String> set = new HashSet<String>();
						// 客户昵称
						set.addAll(onlines.keySet());
						serverBean.setClients(set);
						sendAll(serverBean);
						break;
					}
					case -1: { // 下线
						// 创建服务器的Transfer，并发送给客户端
						Transfer serverBean = new Transfer();
						serverBean.setType(-1);

						try {
							oos = new ObjectOutputStream(
									client.getOutputStream());
							oos.writeObject(serverBean);
							oos.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						onlines.remove(bean.getName());

						// 向剩下的在线用户发送有人离开的通知
						Transfer serverBean2 = new Transfer();
						serverBean2.setInfo(bean.getTimer() + "  "
								+ bean.getName() + " " + " 下线了");
						serverBean2.setType(0);
						HashSet<String> set = new HashSet<String>();
						set.addAll(onlines.keySet());
						serverBean2.setClients(set);

						sendAll(serverBean2);
						return;
					}
					case 1: { // 聊天
						
                        // 创建服务器的Transfer，并发送给客户端
						Transfer serverBean = new Transfer();

						serverBean.setType(1);
						serverBean.setClients(bean.getClients());
						serverBean.setInfo(bean.getInfo());
						serverBean.setName(bean.getName());
						serverBean.setTimer(bean.getTimer());
						// 向选中的客户发送数据
						sendMessage(serverBean);
						break;
					}
					case 2: { // 请求接受文件
						// 创建服务器的Transfer，并发送给客户端
						Transfer serverBean = new Transfer();
						String info = bean.getTimer() + "  " + bean.getName()
								+ "向你传送文件,是否需要接受";

						serverBean.setType(2);
						serverBean.setClients(bean.getClients()); // 这是发送的目的地
						serverBean.setFileName(bean.getFileName()); // 文件名称
						serverBean.setSize(bean.getSize()); // 文件大小
						serverBean.setInfo(info);
						serverBean.setName(bean.getName()); // 来源
						serverBean.setTimer(bean.getTimer());
						// 向选中的客户发送数据
						sendMessage(serverBean);

						break;
					}
					case 3: { // 确定接收文件
						Transfer serverBean = new Transfer();

						serverBean.setType(3);
						serverBean.setClients(bean.getClients()); // 文件来源
						serverBean.setTo(bean.getTo()); // 文件目的地
						serverBean.setFileName(bean.getFileName()); // 文件名称
						serverBean.setIp(bean.getIp());
						serverBean.setPort(bean.getPort());
						serverBean.setName(bean.getName()); // 接收的客户名称
						serverBean.setTimer(bean.getTimer());
						// 通知文件来源的客户，对方确定接收文件
						sendMessage(serverBean);
						break;
					}
					case 4: {
						Transfer serverBean = new Transfer();

						serverBean.setType(4);
						serverBean.setClients(bean.getClients()); // 文件来源
						serverBean.setTo(bean.getTo()); // 文件目的地
						serverBean.setFileName(bean.getFileName());
						serverBean.setInfo(bean.getInfo());
						serverBean.setName(bean.getName());// 接收的客户名称
						serverBean.setTimer(bean.getTimer());
						sendMessage(serverBean);

						break;
					}
					default: {
						break;
					}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close();
			}
		}

		// 向选中的用户发送数据
		private void sendMessage(Transfer serverBean) 
		{
			// 首先取得所有的values
			Set<String> cbs = onlines.keySet();
			Iterator<String> it = cbs.iterator();
			// 选中客户
			HashSet<String> clients = serverBean.getClients();
			while (it.hasNext()) {
				// 在线客户
				String client = it.next();
				// 选中的客户中若是在线的，就发送server
				if (clients.contains(client)) {
					Socket c = onlines.get(client).getSocket();
					ObjectOutputStream oos;
					try {
						oos = new ObjectOutputStream(c.getOutputStream());
						oos.writeObject(serverBean);
						oos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		// 向所有的用户发送数据
		public void sendAll(Transfer serverBean) {
			Collection<Client> clients = onlines.values();
			Iterator<Client> it = clients.iterator();
			ObjectOutputStream oos;
			while (it.hasNext()) {
				Socket c = it.next().getSocket();
				try {
					oos = new ObjectOutputStream(c.getOutputStream());
					oos.writeObject(serverBean);
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
       //关闭
		private void close() {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void start() {
		try {
			while (true) {
				Socket client = ss.accept();
				new ClientThread(client).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}

}
