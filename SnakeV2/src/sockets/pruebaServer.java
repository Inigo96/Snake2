package sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class pruebaServer {
	private Socket socket;
//	public void initServer(){
//		try
//		{
//			int port = 64113;
//			ServerSocket listener = new ServerSocket(port);
//			try 
//			{
//				while (true) 
//				{
//					Socket socket = listener.accept();
//					try 
//					{
//						PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
//						out.println("dfdd");
//					}
//					catch(Exception e)
//					{
//
//					} 
//					finally 
//					{
//						socket.close();
//					}
//				}
//			}
//			finally 
//			{
//				listener.close();
//			}
//		}
//	}
		 public static void main(String[] args) throws IOException {
		        ServerSocket listener = new ServerSocket(1956);
		        while(true){
		        try {
		            while (true) {
		                Socket socket = listener.accept();
		                System.out.println("hecho65");
		                try {
		                	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		                	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		                	String line=in.readLine();
		                	System.out.println("jopu");
		                    System.out.println("hecho");
		                    out.println("dd");
		                } finally {
		                    socket.close();
		                }
		            }
		        }
		        finally {
		            listener.close();
		        }
		    }}
}

