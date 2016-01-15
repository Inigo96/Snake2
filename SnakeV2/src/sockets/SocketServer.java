package sockets;
import java.math.BigInteger;
import java.net.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.Point;
import java.io.*;

public class SocketServer {

	private Socket socket;
	private Queue<String> cola;
	private HashMap<String, Juego> enPartida;
	private HashMap<String, String> codigoAccesoAJuego;
	//	private ArrayList<Juego> enPartida;
	private final int numJugadoresPorPartida=4;
	private SecureRandom random;

	/*3 valores int(formato String) Usuario ;
	 * 0 Introducir IP cola
	 * 1 Borrar usuario de cola por haberse salido
	 * 2 Pregunta acerca de si tiene ya contrincantes
	 * String (referencia) cuando 
	 * TODO BD nombre
	 * TODO RANK Pedir datos de servidor
	 */

	/*
	 * Respuesta del servidor (formato String)
	 * 	404 -> Error (Petición imposible)
	 * 	200 -> Recibido e introducido en cola  
	 * 	201 -> Recibido Borrar
	 *  300 -> Sigue esperando
	 */
	public void initServer(){
		try
		{
			random=new SecureRandom();
			cola=new LinkedList<>();
			enPartida=new HashMap<>();
			final int port = 5000;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 5000");

			//Server is running always. This is done using this while(true) loop
			while(true)
			{
				//Reading the message from the client
				socket = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String number = br.readLine();
				String respuesta = null;
				if(number.equals("0")){
					//Introduce al cliente en la lista si no se encuentra en ella
					String IP=socket.getRemoteSocketAddress().toString();
					if(!cola.contains(IP)){
						cola.add(IP);
						respuesta="200";
					}else{
						respuesta="404";
					}
				}else if(number.equals("1")){
					//Salir de la lista 
					if (cola.remove(socket.getInetAddress().toString())) respuesta="201"; else respuesta="404";					
				}else if(number.equals("2")){
					//Mirar si ya tiene contrincantes
					String IP=socket.getRemoteSocketAddress().toString();
					if(cola.contains(IP)){
						respuesta="300";
					}else{
						//Si los tiene darles el código del juego en el que están jugando
						respuesta=codigoAccesoAJuego.get(socket.getRemoteSocketAddress().toString());
						if(respuesta==null)respuesta="404";
					}
				}else if(number.equals("RANK")){
					//Peticion BD
				}else if(number.substring(0,1).equals("BD")){
					number.substring(2,number.length()-1);
					//Peticion BD
				}else{
					//Enviar mensaje a interpretar por el cliente
					Juego juego;
					if((juego=enPartida.get(number))!=null) respuesta=juego.getRespuesta() ;
					else respuesta="404";
				}
				//Sending response back to the client.
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(respuesta);
				bw.flush();
				if(cola.size()==4){
					String contrasenya=null;
					do{
						contrasenya=randomNumber();
					}while(enPartida.containsKey(contrasenya));
					String IP[]=new String[numJugadoresPorPartida];
					for(int a=0;a<numJugadoresPorPartida;a++){
						IP[a]=(cola.peek());
						codigoAccesoAJuego.put(cola.poll(),contrasenya );
					}
					enPartida.put(contrasenya, new Juego(IP));

				}
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

	private String randomNumber(){
		return new BigInteger(130,random).toString(32);
	}

	public static void main(String[] args) {
		new SocketServer().initServer();
	}

	private class Juego implements Runnable{

		Point[] objects= new Point[2];
		int[][] color=new int[800][600];
		int numPlayers;

		private String [] IP;
		private String getRespuesta(){
			return null;
		}

		public Juego(String[] IP) {
			this.IP=IP;
			new Thread(this).run();
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int a=0;a<4;a++){

				if(objects[a]!=null){
					if(objects[a].x < 0 || objects[a].x >800 || objects[a].y < 0 || objects[a].y>600){
						objects[a]=null;
					}

					if(objects[a].x>0 && objects[a].y>0 && objects[a].x<799 && objects[a].y<599){
						if(color[objects[a].x][objects[a].y]!=(a & 0) || color[objects[a].x+1][objects[a].y]!=(a & 0) ||
								color[objects[a].x-1][objects[a].y]!=(a & 0) || color[objects[a].x][objects[a].y+1]!=(a & 0)||
								color[objects[a].x][objects[a].y-1]!=(a & 0) ){
							objects[a]=null;
						}
					}
					if(objects[a].x>0 && objects[a].y>0 && objects[a].x<799 && objects[a].y<599){
						color[Math.round((float)(objects[a].x+objects[a].x))][Math.round((float)(objects[a].y+objects[a].y))]= a;
					}
				}
			}
			if(numPlayers<=1){
				// TODO terminar partida
			}
			// TODO Comenzar Juego -> A poder ser dejarme una variable que poder coger y praparado para enviar con get
			//TODO al terminar partida hay que guardarla en BD, borrarla de enPartida y codigoAccesoAJuego
		}		

	}

	private class BD{
		//Inicio, método para 
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
