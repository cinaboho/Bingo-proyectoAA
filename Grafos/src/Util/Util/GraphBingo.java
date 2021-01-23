/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos.src.Util.Util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

/**
 *
 * @author josue
 */
public class GraphBingo <E>{
    private LinkedList<Vertex<E>> tablas;
    private ArrayList<Vertex<E>> numeros;
    private boolean directed;
    
    public GraphBingo(boolean directed){
        this.directed = directed;
        tablas = new LinkedList<>();
        numeros = new ArrayList<>();
    }
    
    public boolean addTabla(E data, E color){
        Vertex<E> v =  new Vertex<>(data,color);
        return (data == null || tablas.contains(v))?false:tablas.add(v);
    }
    
    public boolean addNumero(E data, E color){
        Vertex<E> v =  new Vertex<>(data,color);
        return (data == null || numeros.contains(v))?false:numeros.add(v);
    }
    public boolean addEdge(E src,E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vo = searchVertex(src);
        Vertex<E> vd = numeros.get(Integer.parseInt((String) dst)-1);
        if(vo == null || vd == null) return false;
        Edge<E> e = new Edge<>(1,vo,vd);
        if(!vo.getEdges().contains(e))
            vo.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(1,vd,vo);
            if(!vd.getEdges().contains(ei))    
                vd.getEdges().add(ei);
        }
        return true;
    }
    /*
    public boolean addEdge(E src,E dst,int peso){
        if(src == null || dst == null) return false;
        Vertex<E> vo = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vo == null || vd == null) return false;
        Edge<E> e = new Edge<>(peso,vo,vd);
        if(!vo.getEdges().contains(e))
            vo.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(peso,vd,vo);
            if(!vd.getEdges().contains(ei))    
                vd.getEdges().add(ei);
        }
        return true;
    }*/
    
    public boolean removeVertex(E data, E color){
        if(data == null || tablas.isEmpty()) return false;
        ListIterator<Vertex<E>> iv = tablas.listIterator();
        while(iv.hasNext()){
            Vertex<E> v = iv.next();
            ListIterator<Edge> ie = v.getEdges().listIterator();
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
        Vertex<E> vd = numeros.get(Integer.parseInt((String) dst)-1);
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
    /*
    public boolean removeEdge(E src,E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vo = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vo == null || vd == null) return false;
        Edge<E> e = new Edge<>(0,vo,vd);
        vo.getEdges().remove(e);
        if(!directed){
            Edge<E> ei = new Edge<>(0,vo,vd);
            vd.getEdges().remove(ei);
        }
        return true;
    }*/
    
    public Vertex<E> searchVertex(E data){
        for(Vertex<E> v : tablas){
            if(v.getData().equals(data)) return v;
        }
        return null;
    }
    public Vertex<E> searchVertexNumber(E data){
        for(Vertex<E> v : numeros){
            if(v.getData().equals(data)) return v;
        }
        return null;
    }
    
    public int inDegree(E data){
        if(data == null || tablas.isEmpty()) return -1;
        Vertex<E> vd = searchVertex(data);
        if(vd == null) return -1;
        int cont = 0;
        for(Vertex<E> v : tablas){
            Edge<E> e = new Edge(0, v, vd);
            if(v.getEdges().contains(e))
                cont++;
        }
        return cont;
    }
    
    public int outDegree(E data){
        if(data == null || tablas.isEmpty()) return -1;
        Vertex<E> vo = searchVertex(data);
        if(vo == null) return -1;
        return vo.getEdges().size();
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
    
    public List<E> bfs(E data){
        List<E> result = new LinkedList<>();
        if(data == null ) return result;
        Vertex<E> v = searchVertex(data);
        if(v == null ) return result;
        v.setVisited(true);
        Queue<Vertex<E>> cola = new LinkedList<>();
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            result.add(v.getData());
            for(Edge<E> e : v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    cola.offer(e.getVDestino());
                }
            }
        }
        cleanVertexes();
        return result;
    }
    
    public List<E> dfs(E data){
        List<E> result = new LinkedList<>();
        if(data == null ) return result;
        Vertex<E> v = searchVertex(data);
        if(v == null ) return result;
        v.setVisited(true);
        Deque<Vertex<E>> pila = new LinkedList<>();
        pila.push(v);
        while(!pila.isEmpty()){
            v=pila.pop();
            result.add(v.getData());
            for(Edge<E> e : v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    pila.offer(e.getVDestino());
                }
            }
        }
        cleanVertexes();
        return result;
    }
    
    private void cleanVertexes(){
        for(Vertex<E> v : tablas){
            v.setVisited(false);
        }
    }
    
    public LinkedList<E> tacharNumero(E numero, E color){
        LinkedList<E> ganadores = new LinkedList<>();
        if(numero == null) return ganadores;
        for(Vertex<E> tabla: tablas){
            if(tabla.getColor().equals(color))
                if(removeEdge(tabla.getData(),color)>0){
                    ganadores.add(tabla.getData());
                }
        }
        return ganadores;
    }
    /*
    public boolean isConnected(){
        if(tablas.isEmpty()) return false;
        Vertex<E> v = tablas.getFirst();
        List<E> result = bfs(v.getData());
        if(result.size() != tablas.size()) return false;
        if(!directed) return true;
        GraphBingo<E> rg = this.reverse();
        List<E> result2 = rg.bfs(rg.tablas.getFirst().getData());
        if(result2.size()== tablas.size()) return true;
        return false;
    }
    
    public GraphBingo<E> reverse(){  
        if(tablas.isEmpty()) return null;
        if(!directed) return this;
        GraphBingo<E> rev = new GraphBingo<E>(true);
        Vertex<E> v = tablas.get(0);
        v.setVisited(true);
        Queue<Vertex<E>> cola = new LinkedList<>();
        cola.offer(v);
        while(!cola.isEmpty()){
            v=cola.poll();
            rev.addVertex(v.getData());
            for(Edge<E> e : v.getEdges()){
                rev.addVertex(e.getVDestino().getData());
                rev.addEdge(e.getVDestino().getData(),v.getData(), e.getPeso());
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    cola.offer(e.getVDestino());
                }
            }
        }
        cleanVertexes();
        return rev;
    }
    
    public List<Set<E>> connectedComponents(){
        if(tablas.isEmpty()) return null;
        List<Set<E>> components = new ArrayList<>();
        Set<E> vertx = new HashSet<>();
        for(Vertex v : tablas) vertx.add((E) v.getData());
        GraphBingo<E> reverse=null;
        if(directed){
             reverse = this.reverse();
        }
        int cantData = 0;
        while(cantData != tablas.size()){
            E  data = vertx.iterator().next();
            Set<E> component = new HashSet<>(bfs(data));
            if(directed){
                Set<E> rComponent = new HashSet<>(reverse.bfs(data));
                component.retainAll(rComponent);
            }
            if(component.isEmpty()){
                component.add(data);
            }
            components.add(component);
            cantData+=component.size();
            vertx.removeAll(component);
        }
        return components;
    }
    
    public GraphBingo<E> unirGrafos(GraphBingo<E> other){
        GraphBingo<E> result = new GraphBingo(false);
        if(this.directed || other.directed || other==null) return result;
        if(this.tablas.isEmpty()) return other;
        if(other.tablas.isEmpty()) return this;
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1,Edge<E> e2)->e1.getPeso()-e2.getPeso());
        for(Vertex<E> v : tablas){
           for(Edge<E> e : v.getEdges()){
               cola.offer(e);
           }
        }
        for(Vertex<E> v : other.tablas){
           for(Edge<E> e : v.getEdges()){
               cola.offer(e);
           }
        }
        Edge<E> e = cola.poll();
        while(!cola.isEmpty()){
            result.addVertex((E) e.getVOrigen().getData());
            result.addVertex((E) e.getVDestino().getData());
            result.addEdge(e.getVOrigen().getData(), e.getVDestino().getData(),e.getPeso());
            e=cola.poll();
        }
        return result;
    }
    
    public GraphBingo prim(){
        if(directed || !isConnected() || tablas.isEmpty()) return null;
        GraphBingo<E> graph = new GraphBingo<E>(false);
        for(Vertex<E> v :tablas){
            graph.addVertex(v.getData());
        }
        Set<E> set = new HashSet<>();
        Vertex<E> v = tablas.getFirst();
        v.setVisited(true);
        set.add(v.getData());
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1,Edge<E> e2)->e1.getPeso()-e2.getPeso());
        for(Edge<E> e : v.getEdges()){
            cola.offer(e);
        }
        Edge<E> e;
        while(set.size() != tablas.size()){
            e = cola.poll();
            if(e.getVDestino().isVisited() == false ){
                e.getVDestino().setVisited(true);
                set.add(e.getVDestino().getData());
                graph.addEdge(e.getVOrigen().getData(), e.getVDestino().getData(),e.getPeso());
                for(Edge<E> ed : e.getVDestino().getEdges()){
                    if(ed.getVOrigen().equals(e.getVDestino()))
                        cola.offer(ed);
                }
            }
        }
        cleanVertexes();    
        return graph;
    }
    
    public GraphBingo kruskal(){
        GraphBingo graph = new GraphBingo<>(true);
        if(directed || !isConnected() || tablas.isEmpty()) return null;
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1,Edge<E> e2)->e1.getPeso()-e2.getPeso());
        for(Vertex<E> v : tablas){
            graph.addVertex(v.data);
        }
        for(Vertex<E> v : tablas){
           for(Edge<E> e : v.getEdges()){
               cola.offer(e);
           }
        }
        while(graph.isConnected()){
            Edge<E> e = cola.poll();
            graph.addEdge(e.getVOrigen(), e.getVDestino(), e.getPeso());
        }
        return graph;
    }
    
    private void dijkstra(E inicio) {
        Vertex<E> ve = searchVertex(inicio);
        ve.setDistancia(0);
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2) -> v1.getDistancia() - v2.getDistancia());
        cola.offer(ve);
        while (!cola.isEmpty()) {
            ve = cola.poll();
            ve.setVisited(true);
            for (Edge<E> ed : ve.getEdges()) {
                if (!ed.getVDestino().isVisited()) {
                    if (ve.getDistancia() + ed.getPeso() < ed.getVDestino().getDistancia()) {
                        ed.getVDestino().setDistancia(ve.getDistancia() + ed.getPeso());
                        ed.getVDestino().setAntecesor(ve);
                        cola.offer(ed.getVDestino());
                    }
                }
            }
        }
    }
    
    private void dijkstraModificado(E inicio) {
        Vertex<E> v = searchVertex(inicio);
        v.setDistancia(0);
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2) -> v1.getDistancia() - v2.getDistancia());
        cola.offer(v);
        while (!cola.isEmpty()) {
            v = cola.poll();
            v.setVisited(true);
            for (Edge<E> e : v.getEdges()) {
                if (!e.getVDestino().isVisited()) {
                    if (v.getDistancia() + 1 < e.getVDestino().getDistancia()) {
                        e.getVDestino().setDistancia(v.getDistancia() + 1);
                        e.getVDestino().setAntecesor(v);
                        cola.offer(e.getVDestino());
                    }
                }
            }
        }
    }
    
    public int menorDistancia(E start, E end) {
        if (start == null || end == null) {
            return -1;
        }
        if (start.equals(end)) {
            return 0;
        }
        dijkstraModificado(start);
        int retorno = searchVertex(end).getDistancia();
        cleanVertexes();
        return retorno;
    }
    
    public List<E> caminoMinimo(E  inicio, E fin) {
        Deque<E> result = new LinkedList<>();
        if (inicio == null || fin == null) {
            return null;
        }
        if (inicio.equals(fin)) {
            return null;
        }
        dijkstraModificado(inicio);
        Vertex<E> v = searchVertex(fin);
        while (v != null) {
            result.addLast(v.getData());
            v = v.getAntecesor();
        }
        cleanVertexes();
        List<E> list = new LinkedList<>();
        list.addAll(result);
        return list;
    }*/
}
