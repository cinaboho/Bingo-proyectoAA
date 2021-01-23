/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author josue
 */
public class main {
    public static void main(String[] args){
        System.out.println(generarGrafoSintactico("the violence on the tv the article discussed the idea of the amount of violence on the news"));
    }
    
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
    }
}
