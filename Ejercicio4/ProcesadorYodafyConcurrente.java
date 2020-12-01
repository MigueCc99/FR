//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafyConcurrente extends Thread {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socketServicio;
	private int port;
	private InetAdress direccion;
	private DatagramPacket paquete;
	int id;

	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramSocket socketServicio, int id, DatagramPacket paquete) {
		this.socketServicio=socketServicio;
		this.paquete = paquete;
		this.id = id;
		direccion = paquete.getAdrress();
		port = paquete.getPort();
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		Byte[] receiveData = new Byte[256];
		Byte[] sendData = new Byte[256];
		String receiveData_str;
		System.out.println("Receiving data");	

		try {

			receiveData = paquete.getData();
			receiveData_str = new String(receiveData).trim();			

			String respuesta = yodaDO(receiveData);
			sendData = respuesta.getBytes();
			// Enviar respuesta
			paquete = new DatagramPacket(sendData, sendData.length, direccion, port);
			socketServicio.send(paquete);

			Thread.sleep(5000);
			outPrinter.println(respuesta);
			System.out.println("Sending data, thread: ", + this.ID);

		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		} catch (InterruptedException excep){
			System.err.println("Error en el retardo.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
