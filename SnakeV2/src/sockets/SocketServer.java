package sockets;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;


import java.io.*;

public class SocketServer {

	private Socket socket;
	private ArrayList<String> cola;
	private ArrayList<String> enPartida;

	/*3 valores int(formato String) Usuario ;
	 * 0 Introducir IP cola
	 * 1 Borrar usuario de cola por haberse salido
	 * 2 Pregunta acerca de si tiene ya contrincantes
	 */

	/*
	 * Respuesta del servidor (formato String)
	 * 	404 -> Error (Petici�n imposible)
	 * 	200 -> Recibido e introducido en cola  
	 * 	201 -> Recibido Borrar
	 *  300 -> Sigue esperando
	 */
	public void initServer(){
		try
		{
			cola=new ArrayList<>();
			int port = 5000;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 5000");

			//Server is running always. This is done using this while(true) loop
			while(true)
			{
				//Reading the message from the client
				socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String number = br.readLine();
				String respuesta = null;
				if(number.equals("0")){
					String IP=socket.getRemoteSocketAddress().toString();
					if(!cola.contains(IP)){
						cola.add(IP);
						respuesta="200";
					}else{
						respuesta="404";
					}
				}else if(number.equals("1")){
					if (cola.remove(socket.getInetAddress().toString())) respuesta="201"; else respuesta="404";					
				}else if(number.equals("2")){
					//Mirar si ya tiene contrincantes
					if(true){
						
					}else{
						
					}
				}
				//Sending the response back to the client.
              OutputStream os = socket.getOutputStream();
              OutputStreamWriter osw = new OutputStreamWriter(os);
              BufferedWriter bw = new BufferedWriter(osw);
              bw.write(respuesta);
//              System.out.println("Message sent to the client is "+returnMessage);
              bw.flush();
			}	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch(Exception e){}
		}
	}

	public static void main(String[] args) {
		new SocketServer().initServer();
	}
}
//	public void initServer(){
//		try
//        {
//            int port = 5000;
//            ServerSocket serverSocket = new ServerSocket(port);
//            System.out.println("Server Started and listening to the port 5000");
// 
//            //Server is running always. This is done using this while(true) loop
//            while(true)
//            {
//                //Reading the message from the client
//                socket = serverSocket.accept();
//                InputStream is = socket.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String number = br.readLine();
//
//                System.out.println("Message received from client is "+number);
// 
//                //Multiplying the number by 2 and forming the return message
//                String returnMessage;
//                try
//                {
//                    int numberInIntFormat = Integer.parseInt(number);
//                    int returnValue = numberInIntFormat*2;
//                    returnMessage = String.valueOf(returnValue) + "\n";
//                }
//                catch(NumberFormatException e)
//                {
//                    //Input was not a number. Sending proper message back to client.
//                    returnMessage = "Please send a proper number\n";
//                }
// 
//                //Sending the response back to the client.
//                OutputStream os = socket.getOutputStream();
//                OutputStreamWriter osw = new OutputStreamWriter(os);
//                BufferedWriter bw = new BufferedWriter(osw);
//                bw.write(returnMessage);
//                System.out.println("Message sent to the client is "+returnMessage);
//                bw.flush();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                socket.close();
//            }
//            catch(Exception e){}
//        }
//			
//	}




//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new SocketServer().initServer();
//	}
//	
//	
//
//}
