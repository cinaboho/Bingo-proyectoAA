/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author josue
 */
public class GraphBingo <E>{
    private LinkedList<Vertex<E>> tablas;
    private ArrayList<Vertex<E>> numerous;
    private boolean directed;

    public GraphBingo(boolean directed){
        this.directed = directed;
        tablas = new LinkedList<>();
        numerous = new ArrayList<>();
    }

    public boolean addTabla(E data, E color){
        Vertex<E> v =  new Vertex<>(data,color);
        return (data == null || tablas.contains(v))?false:tablas.add(v);
    }

    public boolean addNumero(E data, E color){
        Vertex<E> v =  new Vertex<>(data,color);
        return (data == null || numerous.contains(v))?false:numerous.add(v);
    }
    public boolean addEdge(E src,E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vo = searchVertex(src);
        Vertex<E> vd = numerous.get(Integer.parseInt((String) dst)-1);
        if(vo == null || vd == null) return false;
        Edge<E> e = new Edge<>(1,vo,vd);
        if(!vo.getEdges().contains(e))
            vo.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(1,vd,vo);
            if(!vd.getEdges().contains(ei)){
                vd.getEdges().add(ei);
            }
        }
        return true;
    }
    public boolean removeVertex(E data, E color){
        if(data == null || tablas.isEmpty()) return false;
        ListIterator<Vertex<E>> iv = tablas.listIterator();
        while(iv.hasNext()){
            Vertex<E> v = iv.next();
            ListIterator<Edge<E>> ie = v.getEdges().listIterator();
            while(ie.hasNext()){ 
                Edge<E> e = ie.next();
                if(e.getVDestino().getData().equals(data)){
                    ie.remove();
                }
            }
        }
        Vertex<E> vi = new Vertex<>(data, color);
        return tablas.remove(vi);
    }
    
    public int removeEdge(E src,E dst){
        if(src == null || dst == null) return -1;
        Vertex<E> vo = searchVertex(src);
        Vertex<E> vd = numerous.get(Integer.parseInt((String) dst)-1);
        if(vo == null || vd == null) return -1;
        Edge<E> e = new Edge<>(0,vo,vd);
        vo.getEdges().remove(e);
        if(!directed){
            Edge<E> ei = new Edge<>(0,vo,vd);
            vd.getEdges().remove(ei);
        }
        if(vo.getEdges().isEmpty()){
            return 1;
        }
        return 0;
    }
    public Vertex<E> searchVertex(E data){
        for(Vertex<E> v : tablas){
            if(v.getData().equals(data)) return v;
        }
        return null;
    }
    public Vertex<E> searchVertexNumber(E data){
        for(Vertex<E> v : numerous){
            if(v.getData().equals(data)) return v;
        }
        return null;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Vertex<E> v : tablas){
            for(Edge<E> e : v.getEdges()){
                sb.append(e);
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }
}
