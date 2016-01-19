package sockets;

import java.math.BigInteger;
import java.net.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	// private ArrayList<Juego> enPartida;
	private final int numJugadoresPorPartida = 2;
	private SecureRandom random;

	/*
	 * 3 valores int(formato String) Usuario ; 0 Introducir IP cola 2 Pregunta
	 * acerca de si tiene ya contrincantes String (referencia) cuando TODO BD
	 * nombre TODO RANK Pedir datos de servidor
	 */

	/*
	 * Respuesta del servidor (formato String) 404 -> Error (Petición imposible)
	 * 200 -> Recibido e introducido en cola 201 -> Recibido Borrar 300 -> Sigue
	 * esperando
	 */
	public void initServer() {
		try {
			BaseDeDatos.initBD("usuarios");
			BaseDeDatos.crearTablaBD();
			random = new SecureRandom();
			cola = new LinkedList<>();
			enPartida = new HashMap<>();
			final int port = 50105;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 5000");

			// Server is running always. This is done using this while(true)
			// loop
			while (true) {
				// Reading the message from the client
				socket = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String stringCliente = br.readLine();
				String respuesta = null;
				if (stringCliente.substring(0, 1).equals("0")) {
					// Introduce al cliente en la lista si no se encuentra en
					// ella
					if (!cola.contains(stringCliente.substring(1,
							stringCliente.length()))) {
						cola.add(stringCliente.substring(1,
								stringCliente.length()));
						respuesta = "200";
					} else {
						respuesta = "404";
					}

				} else if (stringCliente.substring(0, 1).equals("2")) {
					// Mirar si ya tiene contrincantes
					if (cola.contains(stringCliente.substring(1,
							stringCliente.length()))) {
						respuesta = "300";
					} else {
						// Si los tiene darles el código del juego en el que
						// están jugando
						respuesta = codigoAccesoAJuego.get(stringCliente
								.substring(1, stringCliente.length()));
						if (respuesta == null)
							respuesta = "404";
					}
				} else if (stringCliente.equals("RANK")) {
					// BD clasificacion
				} else if (stringCliente.substring(0, 2).equals("BD")) {
					// Esta en la BD el usuario y sino introducir
					stringCliente.substring(2, stringCliente.length());
				} else {
					// Enviar mensaje a interpretar por el cliente
					Juego juego;
					String mensajeCliente[] = stringCliente.split(",,,");
					if ((juego = enPartida.get(mensajeCliente[0])) != null) {
						respuesta = juego.conexion(new String[] {
								mensajeCliente[1], mensajeCliente[2] });
					} else
						respuesta = "404";
				}
				// Sending response back to the client.
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(respuesta);
				bw.flush();
				if (cola.size() == numJugadoresPorPartida) {
					String contrasenya = null;
					do {
						contrasenya = randomNumber();
					} while (enPartida.containsKey(contrasenya));
					String usuarios[] = new String[numJugadoresPorPartida];
					for (int a = 0; a < numJugadoresPorPartida; a++) {
						usuarios[a] = (cola.peek());
						codigoAccesoAJuego.put(cola.poll(), contrasenya);
					}
					enPartida.put(contrasenya, new Juego(usuarios));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}

	private String randomNumber() {
		return "9" + new BigInteger(130, random).toString(32);
	}

	public static void main(String[] args) {
		new SocketServer().initServer();
	}

	private class Juego implements Runnable {

		private Point[] objects = new Point[2];
		private int[][] color = new int[800][600];
		private int numPlayers;

		private String[] infoSalidaProcesado;

		private String[] entradaInfoUsuarioSinProcesar;

		private String[] usuarios;

		public String conexion(String[] infoUsuario) {
			if (infoUsuario.equals(usuarios[0])) {
				entradaInfoUsuarioSinProcesar[0] = infoUsuario[1];
				return infoSalidaProcesado[1];
			} else {
				entradaInfoUsuarioSinProcesar[1] = infoUsuario[1];
				return infoSalidaProcesado[0];
			}

		}

		public Juego(String[] usuarios) {
			this.usuarios = usuarios;
			this.infoSalidaProcesado = new String[2];
			infoSalidaProcesado[0] = "100,,,100";
			infoSalidaProcesado[1] = "700,,,500";
			this.entradaInfoUsuarioSinProcesar = new String[2];
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
			}
			new Thread(this).run();
		}

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int a = 0; a < 2; a++) {
					
					for(int b=0;b<2;b++){
						String xy=entradaInfoUsuarioSinProcesar[b];
						objects[b].setLocation(Integer.parseInt(xy.split(",,,")[0]),Integer.parseInt(xy.split(",,,")[1]));
					}

					if (objects[a] != null) {
						if (objects[a].x < 0 || objects[a].x > 800
								|| objects[a].y < 0 || objects[a].y > 600) {
							objects[a] = null;
						}

						if (objects[a].x > 0 && objects[a].y > 0
								&& objects[a].x < 799 && objects[a].y < 599) {
							if (color[objects[a].x][objects[a].y] != (a & 0)
									|| color[objects[a].x + 1][objects[a].y] != (a & 0)
									|| color[objects[a].x - 1][objects[a].y] != (a & 0)
									|| color[objects[a].x][objects[a].y + 1] != (a & 0)
									|| color[objects[a].x][objects[a].y - 1] != (a & 0)) {
								objects[a] = null;
							}
						}
						if (objects[a].x > 0 && objects[a].y > 0
								&& objects[a].x < 799 && objects[a].y < 599) {
							color[Math
									.round((float) (objects[a].x + objects[a].x))][Math
									.round((float) (objects[a].y + objects[a].y))] = a;
						}
					}
				}
				// TODO Comenzar Juego -> A poder ser dejarme una variable que
				// poder coger y praparado para enviar con get
				// TODO al terminar partida hay que guardarla en BD, borrarla de
				// enPartida y codigoAccesoAJuego
			} while (numPlayers <= 1);
		}

	}

	/**
	 * Base de datos.
	 * 
	 * @author ander.areizagab
	 */
	public static class BaseDeDatos {

		private static Connection connection = null;
		private static Statement statement = null;

		/**
		 * Inicializa una BD SQLITE y devuelve una conexión con ella. Debe
		 * llamarse a este método antes que ningún otro, y debe devolver no null
		 * para poder seguir trabajando con la BD.
		 * 
		 * @param nombreBD
		 *            Nombre de fichero de la base de datos
		 * @return Conexión con la base de datos indicada. Si hay algún error,
		 *         se devuelve null
		 */
		public static Connection initBD(String nombreBD) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:"
						+ nombreBD);
				statement = connection.createStatement();
				statement.setQueryTimeout(30); // poner timeout 30 msg
				return connection;
			} catch (ClassNotFoundException | SQLException e) {
				return null;
			}
		}

		/**
		 * Cierra la conexión con la Base de Datos
		 */
		public static void close() {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Devuelve la conexión si ha sido establecida previamente (#initBD()).
		 * 
		 * @return Conexión con la BD, null si no se ha establecido
		 *         correctamente.
		 */
		public static Connection getConnection() {
			return connection;
		}

		/**
		 * Devuelve una sentencia para trabajar con la BD, si la conexión si ha
		 * sido establecida previamente (#initBD()).
		 * 
		 * @return Sentencia de trabajo con la BD, null si no se ha establecido
		 *         correctamente.
		 */
		public static Statement getStatement() {
			return statement;
		}

		/**
		 * Crea una tabla de puntuacion en una base de datos, si no existía ya.
		 * Debe haberse inicializado la conexión correctamente.
		 */
		public static void crearTablaBD() {
			if (statement == null)
				return;
			try {
				statement.executeUpdate("create table usuarios "
						+ "(usuario string, puntuacion int)");
			} catch (SQLException e) {
			}
		}

		public static void mostrarUsuario(String usuario) {
			String sentencia = ("select usuario, puntuacion from usuarios");
			try {
				boolean n = true;
				ResultSet rs = statement.executeQuery(sentencia);
				while (rs.next() && n == true) {
					if (usuario == rs.getString(1)) {
						System.out.println(rs.getString(1) + ": "
								+ rs.getInt("puntuacion"));
						n = false;
					}
				}
			} catch (SQLException e) {
			}
		}

		public static void crearUsuario(String usuario) {
			String sentencia = ("insert into usuarios values('" + usuario + "',0)");
			try {
				statement.executeUpdate(sentencia);
			} catch (SQLException e) {
			}
		}

		public static void modificarPuntuacion(String usuario) {
			String sentencia = ("select usuario from usuarios");
			try {
				boolean n = true;
				ResultSet rs = statement.executeQuery(sentencia);
				while (rs.next() && n == true) {
					if (usuario == rs.getString(1)) {
						statement
								.executeUpdate("update puntuacion set puntuacion= puntuacion+1 where usuario='"
										+ usuario + "'");
						n = false;
					}
				}
			} catch (SQLException e) {
			}
		}
	}
}

// public void initServer(){
// try
// {
// int port = 5000;
// ServerSocket serverSocket = new ServerSocket(port);
// System.out.println("Server Started and listening to the port 5000");
//
// //Server is running always. This is done using this while(true) loop
// while(true)
// {
// //Reading the message from the client
// socket = serverSocket.accept();
// InputStream is = socket.getInputStream();
// InputStreamReader isr = new InputStreamReader(is);
// BufferedReader br = new BufferedReader(isr);
// String number = br.readLine();
//
// System.out.println("Message received from client is "+number);
//
// //Multiplying the number by 2 and forming the return message
// String returnMessage;
// try
// {
// int numberInIntFormat = Integer.parseInt(number);
// int returnValue = numberInIntFormat*2;
// returnMessage = String.valueOf(returnValue) + "\n";
// }
// catch(NumberFormatException e)
// {
// //Input was not a number. Sending proper message back to client.
// returnMessage = "Please send a proper number\n";
// }
//
// //Sending the response back to the client.
// OutputStream os = socket.getOutputStream();
// OutputStreamWriter osw = new OutputStreamWriter(os);
// BufferedWriter bw = new BufferedWriter(osw);
// bw.write(returnMessage);
// System.out.println("Message sent to the client is "+returnMessage);
// bw.flush();
// }
// }
// catch (Exception e)
// {
// e.printStackTrace();
// }
// finally
// {
// try
// {
// socket.close();
// }
// catch(Exception e){}
// }
//
// }

// public static void main(String[] args) {
// // TODO Auto-generated method stub
// new SocketServer().initServer();
// }
//
//
//
// }
