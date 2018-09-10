package FrameMain;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;  

public class ClientGUI extends JFrame {


	private static final long serialVersionUID = 1L;
	private JLabel ImgLabel;//图片容器
	private JLabel DoorLabel;//门容器
	private JLabel TextLabel[];//文本
	private JLabel ServerStatus[];//服务指示
	private JTextPane TextIP;//IP
	private JTextPane TextPort;//Port
	private JTextPane LocalNum;//Postion
	private JButton StartButton;//Start
	private Image img;
	private ImageIcon icon;
	
	private void ClientInit() {
		this.setSize(600,500);
		this.setResizable(false);
		this.setLocation(600, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		try {
			img=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("1.jpg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icon=new ImageIcon();
		icon.setImage(img);
		ImgLabel=new JLabel(icon);
		ImgLabel.setLocation(10, 10);
		ImgLabel.setSize(400, 400);
		this.add(ImgLabel);
		
		DoorLabel=new JLabel();
		DoorLabel.setOpaque(true);
		DoorLabel.setBackground(Color.orange);
		DoorLabel.setLocation(440, 40);
		DoorLabel.setSize(100, 50);
		this.add(DoorLabel);
		
		TextLabel=new JLabel[6];
		TextLabel[0]=new JLabel("Status");
		TextLabel[0].setLocation(420, 20);
		TextLabel[0].setSize(40, 20);
		TextLabel[1]=new JLabel("Cam");
		TextLabel[1].setLocation(420, 100);
		TextLabel[1].setSize(40, 20);
		TextLabel[2]=new JLabel("Net");
		TextLabel[2].setLocation(520, 100);
		TextLabel[2].setSize(40, 20);
		TextLabel[3]=new JLabel("I  P");
		TextLabel[3].setLocation(420, 250);
		TextLabel[3].setSize(40, 20);
		TextLabel[4]=new JLabel("PORT");
		TextLabel[4].setLocation(420, 300);
		TextLabel[4].setSize(40, 20);
		TextLabel[5]=new JLabel("位置");
		TextLabel[5].setLocation(50, 410);
		TextLabel[5].setSize(50, 50);
				for (int i = 0; i < TextLabel.length; i++) 
			this.add(TextLabel[i]);
				
		ServerStatus=new JLabel[2];
		ServerStatus[0]=new JLabel();
		ServerStatus[0].setOpaque(true);
		ServerStatus[0].setBackground(Color.RED);
		ServerStatus[0].setSize(10, 10);
		ServerStatus[0].setLocation(440, 120);
		ServerStatus[1]=new JLabel();
		ServerStatus[1].setOpaque(true);
		ServerStatus[1].setBackground(Color.RED);
		ServerStatus[1].setSize(10, 10);
		ServerStatus[1].setLocation(530, 120);
		this.add(ServerStatus[0]);
		this.add(ServerStatus[1]);

		TextIP=new JTextPane();
		TextIP.setText("190.168.32.100");
		TextIP.setLocation(440, 270);
		TextIP.setSize(100,25);
		this.add(TextIP);
		TextPort=new JTextPane();
		TextPort.setText("8081");
		TextPort.setLocation(440, 320);
		TextPort.setSize(50,25);
		this.add(TextPort);
		LocalNum=new JTextPane();
		LocalNum.setText("A001");
		LocalNum.setSize(100,25);
		LocalNum.setLocation(100, 422);
		this.add(LocalNum);
		StartButton =new JButton("Start");
		StartButton.setLocation(440, 400);
		StartButton.setSize(120, 50);
		StartButton.addMouseListener(new StartListen());
		this.add(StartButton);
		this.addWindowListener(new WindowChange());
	}
	public ClientGUI() throws HeadlessException {
		super("DoorGUI");
		// TODO Auto-generated constructor stub
		ClientInit();
	}
	/**
	 * 更新照片区域
	 * */
	public void ReFreshImg(InputStream ins) {
		try {
			img=ImageIO.read(ins);
			icon.setImage(img);
			ImgLabel.setIcon(icon);
			this.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void OpenDoor() {
		DoorLabel.setBackground(Color.GREEN);
	}
	public void CloseDoor() {
		DoorLabel.setBackground(Color.red);
	}
	public void OpenCam() {
		ServerStatus[0].setBackground(Color.green);
	}
	public void ReadyCam() {
		ServerStatus[0].setBackground(Color.yellow);
	}
	public void BusyCam() {
		ServerStatus[0].setBackground(Color.red);
	}
	public void OpenNet() {
		ServerStatus[1].setBackground(Color.green);
	}
	public void BusyNet() {
		ServerStatus[1].setBackground(Color.red);
	}
	public void CleanNet() {
		ServerStatus[1].setBackground(Color.WHITE);
	}
	public String getIP() {
		return TextIP.getText();
	}
	public String getPort() {
		return TextPort.getText();
	}
	public String getDoorNum() {
		return LocalNum.getText();
	}
	

}
