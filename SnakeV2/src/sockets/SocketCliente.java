package sockets;
import java.net.*;
import java.io.*;
public class SocketCliente {

	private static Socket socket;

	public String envioInfo(String datoEnvio) throws UnknownHostException, IOException{
		final int PUERTO=5000;
		final String HOST = "localhost";
		InetAddress address = InetAddress.getByName(HOST);
		socket = new Socket(address, PUERTO);

		//Send the message to the server
		OutputStream os = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(datoEnvio);
		bw.flush();
		//Get the return message from the server
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String message = br.readLine();
		socket.close();
		return message;

	}
}
