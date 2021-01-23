/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Objects;

/**
 *
 * @author josue
 * @param <E>
 */
public class Edge <E> {
    private int peso;
    private Vertex<E> vo;
    private Vertex<E> vd;

    public Edge(int peso, Vertex<E> vo, Vertex<E> vd){
        this.peso = peso;
        this.vo = vo;
        this.vd = vd;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Vertex<E> getVOrigen(){
        return vo;
    }

    public void setVOrigen(Vertex<E> vo) {
        this.vo = vo;
    }

    public Vertex<E> getVDestino() {
        return vd;
    }

    public void setVDestino(Vertex<E> vd) {
        this.vd = vd;
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
        final Edge<E> other = (Edge<E>) obj;
        return Objects.equals(this.vo, other.vo) && Objects.equals(this.vd, other.vd);
    }
    
    public String toString(){
        return "("+vo.getData()+","+vd.getData()+","+peso+")";
    }
}
