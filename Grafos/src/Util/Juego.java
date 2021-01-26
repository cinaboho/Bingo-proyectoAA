package util;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Juego {
    GraphBingo<String> bingo = new GraphBingo<>(false);
    String colorRonda="";

    //Constructor
    public Juego() throws Exception {
        llenarGrafo();
        menu();
    }

    //llena el grafo
    private void llenarGrafo() throws Exception {
        generateNumbers(bingo);
        //cargarTablas();
        generarTablas("src/Archivos/archivo.txt");
    }

    //contiene las bolas que van saliendo en el juego
    ArrayList<Integer> bolasJugadas=new ArrayList<>();

    //menú que se muestra en consola
    public  void menu() {

        System.out.println("Ingrese el número de la ronda: \n" +
                "1) Amarillo \n2) Azul \n3) Rojo\n4) Salir\n");
        String opcion=new Scanner(System.in).nextLine();

        switch (opcion){
            case "1":
                colorRonda="amarillo";
                ronda();
                break;
            case "2":
                colorRonda="azul";
                ronda();
                break;
            case "3":
                colorRonda="rojo";
                ronda();
                break;
            case "4":
                return;
            default:
                System.out.println("opcion no válida, elija de nuevo \n");
                menu();
                break;
        }

    }

    //simula a una ronda según el color indicado
    public void ronda(){
        int nBolas;
        if(colorRonda.equals("amarillo") || colorRonda.equals("azul"))
            nBolas = 14;
        else
            nBolas = 11;
        //sigue el lazo mientras no se hayan cantado todas las bolas
        while(bolasJugadas.size()< nBolas){

            //se obtiene una bola de este método
            int bola=generarBola();
            System.out.println("la bola jugada es: "+ bola);

            //método para tachar(eliminar el numero de la tabla) bolas de la tabla
            LinkedList<String> salida=tachar(bola);

            //Si nos devuelve algo diferente a nulo, significa que esa tabla ganó
            if(!salida.isEmpty()){
                System.out.println("el o los ganadores son : "+salida);

                //se limpia la lista de bolas jugadas
                bolasJugadas.clear();
                menu();
            }
        }
        System.out.println("Hubo un ganador? Seleccione una opcion:\n 1)Si\n 2)No\n");
        String opcion=new Scanner(System.in).nextLine();
        if(opcion.equals("2")){
            System.out.println("***Se juega una bola mas porque no hubo ganador***");
            int bola=generarBola();
            System.out.println("la bola jugada es: "+ bola);

            //método para tachar(eliminar el numero de la tabla) bolas de la tabla
            LinkedList<String> salida=tachar(bola);

            //Si nos devuelve algo diferente a nulo, significa que esa tabla ganó
            if(!salida.isEmpty()){
                System.out.println("el o los ganadores son : "+salida);

                //se limpia la lista de bolas jugadas
                bolasJugadas.clear();
                menu();
            }
        }
        System.out.println("No hubo tabla ganadora");
        bolasJugadas.clear();
        menu();
    }

    //método para tachar (eliminar numero de la tabla)
    private LinkedList<String> tachar(int numero) {
        
        LinkedList<String> ganadores = new LinkedList<>();
        String bola= String.valueOf(numero);

        //se busca el número en la lista de vertices del grafo
        Vertex<String> v=bingo.searchVertexNumber(bola);

        /*se recorre para obtener el origen (el id) y luego comprobamos que la tabla es del color del juego
        retornamos el id en caso de  que exista un ganador
        */
        for (Edge e:v.getEdges()){
            Vertex id= e.getVDestino();

            if(id.getColor().equals(colorRonda)){
                int salida=bingo.removeEdge((String)id.getData(),bola);
                if(salida==1){
                    ganadores.add((String)e.getVDestino().getData());
                }
            }
        }
        return ganadores;
    }


    //Genera la bola (el número) a jugarse en la ronda, retorna dicho número con la bola.
    public  int generarBola(){
        int bola=0;
        //pide por consola el número
        System.out.println("Ingrese la bola que sacó: ");
        Scanner in= new Scanner(System.in);
        int sacar=in.nextInt();

        //comprueba que aún no se haya sacado esa bola
        if(bolasJugadas.contains(sacar)){
            System.out.println("ya salió ese número, ingrese otro ");
            bola=generarBola();
        }else{
            bolasJugadas.add(sacar);
            bola=sacar;
        }
        return bola;
    }

    public static void generateNumbers(GraphBingo<String> grafo){
        for (int i=1; i <= 20; i++){
            grafo.addNumero(String.valueOf(i),"nocolor");
        }
    }

    /*añade las tablas al grafo, se creó para poder validad con muchos datos por ahora.
    ¡¡ATENCION!! Si se le queda la pc, cambie el número en el while(cont < 202)
    a uno mucho menor (este numero es la cantida de tablas generadas);
    */
    public void  generarTablas(String archivo) throws IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {

            String[] parts = cadena.split(";");
            String part1_id= parts[0];
            String part2_color = parts[1];

            String[] part3_numeros = parts[2].split("-");

            //se añade al grafo  y recorremos los números para crear los arcos (aristas)
            bingo.addTabla(part1_id, part2_color);
            for(String n:part3_numeros){
                bingo.addEdge(part1_id,n);

            }

        }
        b.close();



    }

    public void cargarTablas() throws IOException {
        int cont=1;
        File file= new File("src/Archivos/archivo.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        while(cont<1000){
            //crea aleatoriamente el id
            int a=(int)(Math.random()*10);
            int b=(int)(Math.random()*10);
            int c=(int)(Math.random()*10);
            int d=(int)(Math.random()*10);
            int e=(int)(Math.random()*10);
            int f=(int)(Math.random()*10);
            int g=(int)(Math.random()*10);

            String id= String.valueOf(a) + String.valueOf(b)+String.valueOf(c)+String.valueOf(d)+String.valueOf(e)+String.valueOf(f)+String.valueOf(g);

            //hace los movimientos necesarios para por aletoriedad asignarle un color a la tabla

            String color="amarillo";
            int numeros = 14;
            int co=(int)(Math.random()*3+1);
            if(co==2){
                color="azul";
            }else if(co==3){
                color="rojo";
                numeros = 11;
            }
            if(co==3){
                numeros=11;
            }

            bw.write(id +";" + color+";");

            //crea una lista de numeros de la tabla
            ArrayList<Integer> list=new ArrayList<>();
            while(list.size()<numeros){
                int n= (int)(Math.random()*20+1);
                if(list.isEmpty()){
                    list.add(n);
                    bw.write(""+n);
                } else if(!list.contains(n)){
                    list.add(n);
                    bw.write("-"+n);
                }
            }
            bw.write("\n");
            cont++;
        }

        bw.close();



    }

}
