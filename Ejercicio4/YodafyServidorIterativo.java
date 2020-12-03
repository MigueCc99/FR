import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;	
import java.net.InetAddress;
import java.util.Random;

import javax.xml.crypto.Data;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []bufferEnvio = new byte[256];
		byte []bufferRecepcion = new byte[256];
		
		// Datagramas
		DatagramSocket socketServidor = null;
		DatagramPacket paquete = null;
		DatagramPacket paqueteModificado = null;
		InetAddress direccion;

		String frase;

		// Se crea el socket para la comunicación mediante datagramas		
		try {
			socketServidor = new DatagramSocket(port);			
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}

		do{
			paquete = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);

			// Se recibe el paquete con el mensaje a yodificar.
			try {
				socketServidor.receive(paquete);
			} catch (IOException e) {
				System.err.println("Error de entrada/salida al abrir el socket.");
			}

			// Se extraen todos los datos del paquete.
			frase = new String(paquete.getData());
			direccion = paquete.getAddress();
			port = paquete.getPort();

			// Incluimos la función de yodificar
			////////////////////////////////////
			String[] s = frase.split(" ");
			String resultado = "";

			Random random = new Random();

			for(int i=0; i<s.length; i++){
				int j = random.nextInt(s.length);
				int k = random.nextInt(s.length);
				String tmp = s[j];

				s[j] = s[k];
				s[k] = tmp;
			}

			resultado = s[0];

			for(int i=1; i<s.length; i++){
				resultado += " " + s[i];
			}
			////////////////////////////////////

			bufferEnvio = resultado.getBytes();

			// Se crea un nuevo paquete con el mensaje yodificado
			paqueteModificado = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccion, port);

			// Se envía el nuevo paquete con el mensaje yodificado incluido
			try {
				socketServidor.send(paqueteModificado);
			} catch (IOException e) {
				System.out.println("Error de entrada/salida al abrir el socket");
			}
		} while(true);

	}

}
