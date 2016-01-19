package sockets;
import java.net.*;
import java.io.*;
public class SocketCliente {

	private static Socket socket;
	
	String usuario;
	String contrasenyaJuego;
	
	public SocketCliente(String usuario) throws UnknownHostException, IOException {
		logearseUsuario(usuario);
		this.usuario=usuario;
		
	}
	
	private String envioInfo(String datoEnvio) throws UnknownHostException, IOException{
		final int PUERTO=64113;
		final String HOST = "192.168.1.38";
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
		envioInfo(usuario);
	}
	
	public String pedirRanking() throws UnknownHostException, IOException{
		return envioInfo("RANK");		
	}
	
	public boolean introducirEnCola(){
		try {
			if (envioInfo(0+usuario).equals("200"));
			return true;
		} catch (UnknownHostException e) {			
		} catch (IOException e) {
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
		String movs= envioInfo(this.contrasenyaJuego+",,,"+this.usuario+",,,"+movPropio);
		if(movs.equals("404")){
			throw new IOException();
		}else{
			return movs;
		}
	}
}
