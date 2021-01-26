import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {

    public static void muestraContenido(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            System.out.println("cadena: "+cadena);

            String[] parts = cadena.split(",");
            String part1_id= parts[0];
            String part2_color = parts[1];
            String part3_numeros = parts[2];
            System.out.println("id: "+part1_id+" |color: "+part2_color+" |numeros: "+part3_numeros);

        }
        b.close();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        muestraContenido("archivo.txt");
    }
}
