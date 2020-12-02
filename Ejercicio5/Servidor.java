import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    
    private Socket socketConexion;
    private ServerSocket socketServidor;
    private DataInputStream bufferEntrada = null;
    private DataOutputStream bufferSalida = null;

    Scanner escaner = new Scanner(System.in);
    final String COMANDO_TERMINAR = "salir()";
    int port = 8989;

    // Conexion UP desde el servidor
    public void upConexion(){
        try{
            socketServidor = new ServerSocket(port);
            socketConexion = socketServidor.accept();

            System.out.println("Conexion establecida con el cliente");
        } catch (IOException e){
            System.err.println("Error al escuchar en el puerto " + port);
        }
    }

    // Lectura de flujos (streams) y verificación
    public void flujos(){
        try{
            bufferEntrada = new DataInputStream(socketConexion.getInputStream());
            bufferSalida = new DataOutputStream(socketConexion.getOutputStream());
            bufferSalida.flush();
        } catch (IOException e){
            System.err.println("Error en la apertura de flujos");
        }        
    }

    // Recibimos datos de entrada hasta que introducimos "salir()"
    public void recibirDatos(){
        String st = "";
        try{
            do{
                st = (String) bufferEntrada.readUTF();
                System.out.println("\n[Cliente] => " + st);
                System.out.println("\n[Tú] => ");
            }while(!st.equals(COMANDO_TERMINAR));
        } catch (IOException e){
            cerrarConexion();
        }        
    }

    // Enviamos respuesta textual
    public void enviar(String s){
        try{
            bufferSalida.writeUTF(s);
            bufferSalida.flush();
        } catch (IOException e){
            System.err.println("Error al enviar");
        }
    }

    // Escribimos nuestro texto
    public void escribirDatos(){
        while(true){
            System.out.println("[Tú] => ");
            enviar(escaner.nextLine());
        }
    }

    // Cerramos la conexión
    public void cerrarConexion(){
        try{
            bufferEntrada.close();
            bufferSalida.close();
            socketConexion.close();
        } catch(IOException e){
            System.err.println("Error al intentar cerrar la conexión");
        }
    }

    // Ejecución de una nueva conexión mediante class Thread
    public void ejecutarConexion(){
        Thread nuevoHilo = new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        upConexion();
                        flujos();
                        recibirDatos();
                    } finally{
                        cerrarConexion();
                    }
                }
            }
        });
        nuevoHilo.start();
    }

    // Método main
    public static void main(String[] args){
        Servidor s = new Servidor();
        Scanner sc = new Scanner(System.in);
        
        s.ejecutarConexion();
        s.escribirDatos();
    }

}
