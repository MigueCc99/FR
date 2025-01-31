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
import java.util.Random;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafyConcurrente extends Thread {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	private int ID;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafyConcurrente(Socket socketServicio, int ID) {
		this.socketServicio=socketServicio;
		random=new Random();
		this.ID = ID;
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		String receiveData = new String();
		System.out.println("Receiving data");
		
		
		try {
			// Obtiene los flujos de escritura/lectura
			inputStream=socketServicio.getInputStream();
			outputStream=socketServicio.getOutputStream();
			
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
			receiveData = inReader.readLine();
			
			String respuesta = yodaDo(receiveData);
			
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
			Thread.sleep(5000);
			outPrinter.println(respuesta);
			System.out.println("Sending data, thread: " + this.ID);

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
