package sockets;
import java.net.*;
import java.io.*;

import org.omg.CORBA.Environment;
public class SocketCliente {

	private static Socket socket;

	String usuario;
	String contrasenyaJuego;

	public SocketCliente(String usuario) throws UnknownHostException, IOException {
		logearseUsuario(usuario);
		this.usuario=usuario;
	}

	public String envioInfo(String datoEnvio) throws UnknownHostException, IOException{
		final int PUERTO=60507;
		final String HOST = "10.164.53.38";
		InetAddress address = InetAddress.getByName(HOST);
		socket = new Socket(address, PUERTO);

		//Send the message to the server
		OutputStream os = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(datoEnvio+"\n ");
		bw.flush();
		//Get the return message from the server
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String message = br.readLine();
		socket.close();
		return message;
	}

	private void logearseUsuario(String usuario) throws UnknownHostException, IOException{		
		envioInfo("BD"+usuario);
	}

	public String pedirRanking() throws UnknownHostException, IOException{
		return envioInfo("RANK");		
	}

	public boolean introducirEnCola(){

		try {
			if (envioInfo(0+usuario).equals("200")) return true;;				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean yaTieneContrincantes(){
		try {
			this.contrasenyaJuego=envioInfo(2+usuario);
			if(!(this.contrasenyaJuego).equals("300")&&!(this.contrasenyaJuego).equals("404"));
			return true;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}	
		return false;
	}

	public String movsJuego(String movPropio) throws UnknownHostException, IOException{
		return envioInfo(this.contrasenyaJuego+",,,"+this.usuario+",,,"+movPropio);
	
	}

	public static void main(String[] args) {
		try {
			SocketCliente b=new SocketCliente("gg");
//			System.out.println(b.introducirEnCola());
			System.out.println(b.envioInfo("fg"));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
