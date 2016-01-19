package sockets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Prueba {
public static void main(String[] args) throws IOException {
	String a="00eee";
	System.out.println(a.substring(0,2));
	System.out.println(a.substring(2,a.length()));
	
	 InetAddress  ip = InetAddress.getLocalHost();
    String hostname = ip.getHostName();
    System.out.println("Your current IP address : " + ip);
    System.out.println("Your current Hostname : " + hostname);
	
	
	JOptionPane.showMessageDialog(new JFrame(), "1.-feo\n2.1rr");					
}
}
