import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    
    private Socket socketConexion;
    private ServerSocket socketServidor;
    private DataInputStream bufferEntrada = null;
    private DataOutputStream bufferSalida = null;
    
    Scanner teclado = new Scanner(System.in);
    final String COMANDO_TERMINAR = "salir()";
    int port = 8989;
    String ip = "localhost";

    // Conexion UP desde el cliente
    public void upConexion(){
        try{
            socketServidor = new Socket(ip, port);
            System.out.println("Conectado a: " + socketConexion.getInetAddress().getHostName());
        } catch (Exception e){
            System.out.println("Excepción al levantar la conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    // Abrimos los flujos del cliente (Istream/Ostream)
    public void abrirFlujos(){
        try{
            bufferEntrada = new DataInputStream(socketConexion.getInputStream());
            bufferSalida = new DataOutputStream(socketConexion.getOutputStream());
            bufferSalida.flush();
        } catch (IOException e){
            System.err.println("Error en la apertura de flujos");
        }
    }

    // Enviamos el contenido
    public void enviar(String s){
        try{
            bufferSalida.writeUTF(s);
            bufferSalida.flush();
        }catch(IOException e){
            System.err.println("Error al enviar");
        }
    }

    // Cerramos la conexión desde el cliente
    public void cerrarConexion(){
        try{
            bufferEntrada.close();
            bufferSalida.close();
            socketConexion.close();

            System.out.println("Conexión terminada");
        }catch(IOException e){
            System.err.println("IOException on cerrarConexion()");
        }
    }

    // Ejecutamos una nueva conexión con la clase Thread
    public void ejecutarConexion(){
        Thread hilo = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    upConexion();
                    abrirFlujos();
                    recibirDatos();
                } finally{
                    cerrarConexion();
                }
            }
        });
        hilo.start();
    }

    // Recibimos los datos en los flujos
    public void recibirDatos(){
        String st = "";
        try{
            do{
                st = (String) bufferEntrada.readUTF();
                System.out.println("\n[Servidor] => " + st);
                System.out.println("\n[Tú] => ");
            } while(!st.equals(COMANDO_TERMINAR));
        } catch (IOException e){
            cerrarConexion();
        }
    }

    // Escribimos nuestros datos y los enviamos
    public void escribirDatos(){
        String entrada = "";
        while(true){
            System.out.println("\n[Tú] => ");
            entrada = teclado.nextLine();
            if(entrada.length() > 0)
                enviar(entrada);
        }
    }

    // Método Main
    public static void main(String[] args){
        Cliente cliente = new Cliente();
        Scanner escaner = new Scanner(System.in);

        cliente.ejecutarConexion();
        cliente.escribirDatos();
    }
}
