package Grafos.src.Util.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {
    GraphBingo<String> bingo = new GraphBingo<>(false);
    String colorRonda="";

    //Constructor
    public Juego() {
        llenarGrafo();
        System.out.println(bingo.toString());
    }

    //llena el grafo
    private void llenarGrafo() {
        generateNumbers(bingo);
        generarTablas();
    }

    //contiene las bolas que van saliendo en el juego
    ArrayList<Integer> bolasJugadas=new ArrayList<>();

    //menú que se muestra en consola
    public  void menu() {

        System.out.println("Ingrese el número de la ronda: \n" +
                "1) Amarillo \n2) Azul \n3) Rojo\n");
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
            default:
                System.out.println("opcion no válida, elija de nuevo \n");
                menu();
                break;
        }

    }

    //simula a una ronda según el color indicado
    public void ronda(){
        //sigue el lazo mientras no se hayan cantado todas las bolas
        while(bolasJugadas.size()<20){

            //se obtiene una bola de este método
            int bola=generarBola();
            System.out.println("la bola jugada es: "+ bola);

            //método para tachar(eliminar el numero de la tabla) bolas de la tabla
            String salida=tachar(bola);

            //Si nos devuelve algo diferente a nulo, significa que esa tabla ganó
            if(salida!="null"){
                System.out.println("el ganador es : "+salida);

                //se limpia la lista de bolas jugadas
                bolasJugadas.clear();
                menu();
            }
        }
        System.out.println("No hubo ganador");
    }

    //método para tachar (eliminar numero de la tabla)
    private String tachar(int numero) {

        String bola= String.valueOf(numero);

        //se busca el número en la lista de vertices del grafo
        Vertex<String> v=bingo.searchVertexNumber(bola);

        /*se recorre para obtener el origen (el id) y luego comprobamos que la tabla es del color del juego
        retornamos el id en caso de  que exista un ganador
        */
        for (Edge e:v.getEdges()){
            Vertex id= e.getVDestino();
            if(id.getColor()==colorRonda){
                int salida=bingo.removeEdge((String)id.getData(),bola);
                if(salida==1){
                    return (String)e.getVDestino().getData();
                }
            }
        }
        return "null";
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
    public void  generarTablas(){
        int cont=1;
        while(cont<202){
            //crea aleatoriamente el id
            int a=(int)(Math.random()*10);
            int b=(int)(Math.random()*10);
            int c=(int)(Math.random()*10);
            int d=(int)(Math.random()*10);
            int e=(int)(Math.random()*10);
            int f=(int)(Math.random()*10);
            int g=(int)(Math.random()*10);

            String id= String.valueOf(a) + String.valueOf(b)+String.valueOf(c)+String.valueOf(d)+String.valueOf(e)+String.valueOf(f)+String.valueOf(g);

            //crea una lista de numeros de la tabla
            ArrayList<Integer> list=new ArrayList<>();
            while(list.size()<14){
                int n= (int)(Math.random()*20+1);
                if(!list.contains(n)){
                    list.add(n);
                }
            }

            //hace los movimientos necesarios para por aletoriedad asignarle un color a la tabla
            String color="amarillo";
            int co=(int)(Math.random()*3+1);
            if(co==2){
                color="azul";
            }else if(co==3){
                color="rojo";
            }

            //se añade al grafico  y recorremos los números para crear los arcos (aristas)
            bingo.addTabla(id, color);
            for(int h:list){
                bingo.addEdge(id, String.valueOf(h));
            }
            cont++;
        }

    }

}
