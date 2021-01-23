/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.src.Util.Util;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author josue
 */
public class Vertex <E> {
    E data;
    E color;
    LinkedList<Edge> edges;
    private boolean visited;
    private int distancia;
    private Vertex<E> antecesor;

    public Vertex(E data, E color) {
        this.data = data;
        this.color = color;
        edges = new LinkedList<>();
    }

    public Vertex(E data) {
        this.data = data;
        edges=new LinkedList<>();
    }

    public E getData() {
        return data;
    }
    
    public E getColor(){
        return color;
    }

    public void setData(E data) {
        this.data = data;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex<E> other = (Vertex<E>) obj;
        return Objects.equals(this.data, other.data); 
    }

    void setDistancia(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getDistancia() {
        return distancia;
    }

    public Vertex<E> getAntecesor() {
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        this.antecesor = antecesor;
    }
    
    
}
