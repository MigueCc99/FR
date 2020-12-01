public static void main(String[] args) {
		
		String buferRecepcion;
		String buferEnvio;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión UDP
		DatagramSocket socketServicio=null;
		//Datagrama para enviar y recibir datos
		DatagramPacket paquete;
		//Dirección ip
		InetAdress direccion;
		
		try {
			// Creamos un socket que se conecte a "host" y "port":
			//////////////////////////////////////////////////////
			socketServicio = new Socket (host,port);
			//////////////////////////////////////////////////////			
			
			//Obtenemos la dirección ip
			direccion = InetAdress.getByName(host);
			
			// Creamos el buffer de envio
			String strEnvio = new String("Al monte del volcán debes ir sin demora");
			buferEnvio = strEnvio.getBytes();

			// Inicializamos el paquete de envío y lo enviamos
			paquete = new DatagramPacket(buferEnvio, buferEnvio.lenght, direccion, port);
			socket.send(paquete);
			
			//Recepcion de paquete
			buferRecepcion = new byte[256];			
			paquete = new DatagramPacket(buferRecepcion, buferRecepcion.lenght);
			socketServicio.receive(paquete);

			String strRecepcion = new String(buferRecepcion).trim();

			// MOstremos la cadena de caracteres recibidos:
			System.out.println("\nContenido recibido " + strRecepcion + "\n");
			
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