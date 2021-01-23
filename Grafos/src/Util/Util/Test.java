/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author josue
 */
public class Test{
        public static void main(String[] args){

        GraphBingo<String> bingo = new GraphBingo<>(false);
        generateNumbers(bingo);
        bingo.addTabla("111", "rojo");
        bingo.addTabla("112", "amarillo");
        bingo.addTabla("113","azul");
        bingo.addTabla("114","amarillo");
        bingo.addEdge("111", "1");
        bingo.addEdge("111", "2");
        bingo.addEdge("111", "3");
        System.out.println(bingo);
    }
    /*
    public static GraphLA<String> generarGrafoSintactico(String T){
        String[] palabras = T.toLowerCase().replaceAll(",","").split(" ");
        Map<String,Integer> dicc = new HashMap<>();
        for(int i=0;i<palabras.length-1;i++){
            String key = palabras[i]+" "+palabras[i+1];
            if(dicc.containsKey(key)){
                Integer peso = dicc.get(key);
                dicc.put(key, peso+1);
            }else{
                dicc.put(key,1);
            }
        }
        GraphLA<String> G = new GraphLA<>(true);
        for(String key : dicc.keySet()){
            String[] letras = key.split(" ");
            G.addVertex(letras[0]);
            G.addVertex(letras[1]);
            G.addEdge(letras[0],letras[1],dicc.get(key));
        }
        return G;
    }*/

    public static void generateNumbers(GraphBingo <String> grafBingo){
        for (int i=1; i <= 20; i++){
            grafBingo.addNumero(String.valueOf(i),"bgcolor");
        }
    }
}


