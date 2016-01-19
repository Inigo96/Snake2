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
	private final int numJugadoresPorPartida=2;
	private SecureRandom random;

	/*3 valores int(formato String) Usuario ;
	 * 0 Introducir IP cola
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
			final int port = 64113;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 5000");

			//Server is running always. This is done using this while(true) loop
			while(true)
			{
				//Reading the message from the client
				socket = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String stringCliente = br.readLine();
				String respuesta = null;
				if(stringCliente.substring(0,1).equals("0")){
					//Introduce al cliente en la lista si no se encuentra en ella
					if(!cola.contains(stringCliente.substring(1,stringCliente.length()))){
						cola.add(stringCliente.substring(1,stringCliente.length()));
						respuesta="200";
					}else{
						respuesta="404";
					}				
				
				}else if(stringCliente.substring(0,1).equals("2")){
					//Mirar si ya tiene contrincantes
					if(cola.contains(stringCliente.substring(1,stringCliente.length()))){
						respuesta="300";
					}else{
						//Si los tiene darles el código del juego en el que están jugando
						respuesta=codigoAccesoAJuego.get(stringCliente.substring(1,stringCliente.length()));
						if(respuesta==null)respuesta="404";
					}
				}else if(stringCliente.equals("RANK")){
					//BD clasificacion
				}else if(stringCliente.substring(0,2).equals("BD")){
					//Esta en la BD el usuario y sino introducir
					stringCliente.substring(2,stringCliente.length());
				}else{
					//Enviar mensaje a interpretar por el cliente
					Juego juego;
					String mensajeCliente[]=stringCliente.split(",,,");
					if((juego=enPartida.get(mensajeCliente[0]))!=null){						
						respuesta=juego.conexion(new String[]{mensajeCliente[1],mensajeCliente[2]}) ;
					}
					else respuesta="404";
				}
				//Sending response back to the client.
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(respuesta);
				bw.flush();
				if(cola.size()==numJugadoresPorPartida){
					String contrasenya=null;
					do{
						contrasenya=randomNumber();
					}while(enPartida.containsKey(contrasenya));
					String usuarios[]=new String[numJugadoresPorPartida];
					for(int a=0;a<numJugadoresPorPartida;a++){
						usuarios[a]=(cola.peek());
						codigoAccesoAJuego.put(cola.poll(),contrasenya);
					}
					enPartida.put(contrasenya, new Juego(usuarios));

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
		return "9"+new BigInteger(130,random).toString(32);
	}

	public static void main(String[] args) {
		new SocketServer().initServer();
	}

	private class Juego implements Runnable{

		private Point[] objects= new Point[2];
		private int[][] color=new int[800][600];
		private int numPlayers;
		
		private String[] infoSalidaProcesado;
		
		private String[] entradaInfoUsuarioSinProcesar;

		private String [] usuarios;
		
		private String conexion(String[] infoUsuario){
			if(infoUsuario.equals(usuarios[0])){
				entradaInfoUsuarioSinProcesar[0]=infoUsuario[1];
			}else{
				entradaInfoUsuarioSinProcesar[1]=infoUsuario[1];
			}
			return infoSalidaProcesado[0]+",,,"+infoSalidaProcesado[1];
		}

		public Juego(String[] usuarios) {
			this.usuarios=usuarios;
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
			}
			new Thread(this).run();
		}

		@Override
		public void run() {
			do{
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int a=0;a<2;a++){
	
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
			}while(numPlayers<=1);
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
