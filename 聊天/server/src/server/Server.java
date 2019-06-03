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
			ss = new ServerSocket(8520);                       //���������
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
				// ��ͣ�Ĵӿͻ��˽�����Ϣ
				while (true) {
					// ��ȡ�ӿͻ��˽��յ���Transfer��Ϣ
					ois = new ObjectInputStream(client.getInputStream());
					bean = (Transfer)ois.readObject();
					
					// ����Transfer�У�type������һ������
					switch (bean.getType()) {
					// �����߸���
					case 0: { // ����
						// ��¼���߿ͻ����û����Ͷ˿���client��
						Client cbean = new Client();
						cbean.setName(bean.getName());
						cbean.setSocket(client);
						// ��������û�
						onlines.put(bean.getName(), cbean);
						// ������������Transfer�������͸��ͻ���
						Transfer serverBean = new Transfer();
						serverBean.setType(0);
						serverBean.setInfo(bean.getTimer() + "  "
								+ bean.getName() + "������");
						// ֪ͨ���пͻ���������
						HashSet<String> set = new HashSet<String>();
						// �ͻ��ǳ�
						set.addAll(onlines.keySet());
						serverBean.setClients(set);
						sendAll(serverBean);
						break;
					}
					case -1: { // ����
						// ������������Transfer�������͸��ͻ���
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

						// ��ʣ�µ������û����������뿪��֪ͨ
						Transfer serverBean2 = new Transfer();
						serverBean2.setInfo(bean.getTimer() + "  "
								+ bean.getName() + " " + " ������");
						serverBean2.setType(0);
						HashSet<String> set = new HashSet<String>();
						set.addAll(onlines.keySet());
						serverBean2.setClients(set);

						sendAll(serverBean2);
						return;
					}
					case 1: { // ����
						
                        // ������������Transfer�������͸��ͻ���
						Transfer serverBean = new Transfer();

						serverBean.setType(1);
						serverBean.setClients(bean.getClients());
						serverBean.setInfo(bean.getInfo());
						serverBean.setName(bean.getName());
						serverBean.setTimer(bean.getTimer());
						// ��ѡ�еĿͻ���������
						sendMessage(serverBean);
						break;
					}
					case 2: { // ��������ļ�
						// ������������Transfer�������͸��ͻ���
						Transfer serverBean = new Transfer();
						String info = bean.getTimer() + "  " + bean.getName()
								+ "���㴫���ļ�,�Ƿ���Ҫ����";

						serverBean.setType(2);
						serverBean.setClients(bean.getClients()); // ���Ƿ��͵�Ŀ�ĵ�
						serverBean.setFileName(bean.getFileName()); // �ļ�����
						serverBean.setSize(bean.getSize()); // �ļ���С
						serverBean.setInfo(info);
						serverBean.setName(bean.getName()); // ��Դ
						serverBean.setTimer(bean.getTimer());
						// ��ѡ�еĿͻ���������
						sendMessage(serverBean);

						break;
					}
					case 3: { // ȷ�������ļ�
						Transfer serverBean = new Transfer();

						serverBean.setType(3);
						serverBean.setClients(bean.getClients()); // �ļ���Դ
						serverBean.setTo(bean.getTo()); // �ļ�Ŀ�ĵ�
						serverBean.setFileName(bean.getFileName()); // �ļ�����
						serverBean.setIp(bean.getIp());
						serverBean.setPort(bean.getPort());
						serverBean.setName(bean.getName()); // ���յĿͻ�����
						serverBean.setTimer(bean.getTimer());
						// ֪ͨ�ļ���Դ�Ŀͻ����Է�ȷ�������ļ�
						sendMessage(serverBean);
						break;
					}
					case 4: {
						Transfer serverBean = new Transfer();

						serverBean.setType(4);
						serverBean.setClients(bean.getClients()); // �ļ���Դ
						serverBean.setTo(bean.getTo()); // �ļ�Ŀ�ĵ�
						serverBean.setFileName(bean.getFileName());
						serverBean.setInfo(bean.getInfo());
						serverBean.setName(bean.getName());// ���յĿͻ�����
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

		// ��ѡ�е��û���������
		private void sendMessage(Transfer serverBean) 
		{
			// ����ȡ�����е�values
			Set<String> cbs = onlines.keySet();
			Iterator<String> it = cbs.iterator();
			// ѡ�пͻ�
			HashSet<String> clients = serverBean.getClients();
			while (it.hasNext()) {
				// ���߿ͻ�
				String client = it.next();
				// ѡ�еĿͻ����������ߵģ��ͷ���server
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

		// �����е��û���������
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
       //�ر�
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
