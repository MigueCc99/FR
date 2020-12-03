//
// YodafyClienteTCP
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		byte []bufferEnvio = new byte[256];
		byte []bufferRecepcion = new byte[256];		
		
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Datagramas
		DatagramSocket socket = null;
		DatagramPacket paquete = null;
		DatagramPacket paqueteModificado = null;
		InetAddress direccion = null;

		String fraseYodificada;

		// Se crea el socket para la comunicación mediante diagramas		
		try {
			socket = new DatagramSocket();			
			
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		} 

		// Se obtiene la dirección con la que se quiere realizar la comunicación
		try {
			direccion = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			System.err.println("Error al recuperar la direccion.");
		}

		bufferEnvio = "Al monte del volcan debes ir sin demora".getBytes();

		// Se envía un DatagramPacket con el mensaje original y se recibe uno con el mensaje yodificado
		try {
			paquete = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccion, port);
			socket.send(paquete);

			paqueteModificado = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
			socket.receive(paqueteModificado);
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}

		fraseYodificada = new String(paqueteModificado.getData());

		System.out.println("\nRecibido: " + fraseYodificada);
		socket.close();
	}
}
