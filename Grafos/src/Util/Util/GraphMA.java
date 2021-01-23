/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.src.Util.Util;

import java.util.ArrayList;

/**
 *
 * @author josue
 */
public class GraphMA <E>{
    
    private ArrayList<E> vertexes;
    private int[][] matrix;
    private int capacity = 10;
    private boolean directed;
    
    public GraphMA(boolean directed){
        vertexes = new ArrayList();
        matrix = new int[capacity][capacity];
        this.directed = directed;
    }
    
    public boolean addVertex(E data){
        return (data == null || vertexes.contains(data))?false:vertexes.add(data);
    }
    
    public boolean removeVertex(E data){
        if(data == null) return false;
        int iv = vertexes.indexOf(data);
        if(iv== -1) return false;
        for(int i = iv;i<vertexes.size();i++){
            matrix[i] = matrix[i+1];
            for(int j=0;j<vertexes.size();j++){
                matrix[j][i] = matrix[j][i+1];
            }
        }
        vertexes.remove(iv);
        return true;
    }
    
    public boolean addEdge(E src,E dst,int peso){
        if(src == null || dst == null)return false;
        int is = vertexes.indexOf(src);
        int id= vertexes.indexOf(dst);
        if(is==-1 || id==-1) return false;
        matrix[is][id] = peso;
        if(!directed)
            matrix[id][is] = peso;
        return true;
    }
    
    public boolean removeEdge(E src,E dst){
        return addEdge(src,dst,0);
    }
    
    public int inDegree(E data){
        if(data == null) return -1;
        int iv = vertexes.indexOf(data);
        if(iv== -1) return -1;
        int cont = 0;
        for(int i = 0;i<vertexes.size();i++){
            if(matrix[i][iv] != 0)
                cont+=1;
        }
        return cont;
    }
    
    public int outDegree(E data){
        if(data == null) return -1;
        int iv = vertexes.indexOf(data);
        if(iv== -1) return -1;
        int cont = 0;
        for(int i = 0;i<vertexes.size();i++){
            if(matrix[iv][i] != 0)
                cont+=1;
        }
        return cont;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0;i<vertexes.size();i++){
            for(int j=0;j<vertexes.size();j++){
                if(matrix[i][j]!=0){
                    sb.append("(");
                    sb.append(vertexes.get(i)).append(",").append(vertexes.get(j))
                            .append(",").append(matrix[i][j]).append(")").append(",");
                }
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }
}
