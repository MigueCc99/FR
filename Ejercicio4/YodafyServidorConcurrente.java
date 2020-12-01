import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.DatagramPaquet;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {

	public static void main(String[] args) {
	
		DatagramSocket socketServidor;
		int port=8989;
		int activeThreads=0;
		DatagramPaquet paquete;

		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			socketServidor = new DatagramSocketS(port);
			//////////////////////////////////////////////////
			
			// Mientras ... siempre!
			do { 
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como argumento
				// el socket y el paquete				
				activeThreads++;
				socket.receive(paquete);
				ProcesadorYodafyConcurrente procesador=new ProcesadorYodafyConcurrente(socketConexion, activeThreads, paquete);
				procesador.procesa();

			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}
	
		socketServidor.close();
	}

}