//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		String buferEnvio;
		String buferRecepcion;
		int bytesLeidos;

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {
			// Creamos un socket que se conecte a "host" y "port":
			//////////////////////////////////////////////////////
			socketServicio = new Socket (host,port);
			//////////////////////////////////////////////////////			
			
			InputStream inputStream = socketServicio.getInputStream();
			OutputStream outputStream = socketServicio.getOutputStream();
			
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un String:
			buferEnvio = new String("Al monte del volcán debes ir sin demora");
			
			// Enviamos el buffer;
			//////////////////////////////////////////////////////
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
			outPrinter.println(buferEnvio);
			//////////////////////////////////////////////////////
			
			// Limpiamos el buffer
			//////////////////////////////////////////////////////
			outPrinter.flush();
			//////////////////////////////////////////////////////
			
			// Leemos la respuesta del servidor.
			//////////////////////////////////////////////////////
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
			buferRecepcion = inReader.readLine();
			//////////////////////////////////////////////////////
			
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("\nContenido recibido " + buferRecepcion + "\n");
			
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			socketServicio.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
