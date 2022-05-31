
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kontaro
 */
public class Graph {
    ArrayList<Paquete> vertices=new ArrayList<Paquete>();
    //vertices 
    ArrayList<Arista> aristas=new ArrayList<Arista>();
    int k;
    float gamma;
    //aristas
    
    public Graph(Cand candidatos,int kpaquetes,float gamma){
        
        this.gamma=gamma;
        this.k=kpaquetes;
        generarVertices(candidatos);
        generarAristas();
        
        
    }
    
    public ArrayList<Paquete> getVertice(){
        return vertices;
    }
    
    public void generarVertices(Cand candidatos){
        vertices=candidatos.getSoluciones();
    }
    
    /**
     * No recibe nada ya que la variable vertice es publica
     */
    public void generarAristas(){
        float diversidad;
        for(int i=0; i<vertices.size(); i++){
            for(int j=0; j<vertices.size(); j++){
                if(i!=j){
                    diversidad=interGrafos(i,j);
                    Arista aristaAxuliar= new Arista(i,j,diversidad);
                    aristas.add(aristaAxuliar);
                    //obtenemos la diversidad entre los paquetes
                }
            }
        }
        
    }
    public float interGrafos(int indiceUno , int indiceDos){
        float diversidad;
        diversidad=1-maxCompatibilidadEntrePaquetes(vertices.get(indiceUno),vertices.get(indiceDos));
        return diversidad;
    }
    
    public float maxEdgeNodeFunc(int indiceUno , int indiceDos){
        float valor;
        valor=((gamma/((k-1)*2))*(pesoNodo(indiceUno)+pesoNodo(indiceDos)))+((1-gamma)*interGrafos(indiceUno,indiceDos));
        return valor; 
    }
    public float pesoNodo(int indice){
        
        return vertices.get(indice).compatibilidad();
    }
    
    public float maxCompatibilidadEntrePaquetes(Paquete paqueteUno,Paquete paqueteDos) {

            float compatibilidadMaxima=0;

            for (int i = 0; i < paqueteUno.tamanoSolucion(); i++) {			
                    for (int j = 0; j < paqueteDos.tamanoSolucion(); j++) {
                            if(paqueteUno.paquete.get(i).searchCompatJaccard(paqueteDos.paquete.get(j).getIdresturant())>compatibilidadMaxima){
                                    compatibilidadMaxima=paqueteUno.paquete.get(i).searchCompatJaccard(paqueteDos.paquete.get(j).getIdresturant());
                            }
                    }

            }
            return compatibilidadMaxima;

    }
    /**
     * Argumento que minimiza el grafo 
     * @return 
     */
    public int argmin(){
        int indice=0;
        float valorArgumento;
        float valorMinimo=10000;
        
        for(int i=0;i<vertices.size();i++){
            valorArgumento=sumMaxNodeEdge(i);//iremos guardando el valor y el indice que minimice la funcion 
            if(i==0){
                indice=i;
                valorMinimo=valorArgumento;
            }
            if(valorArgumento< valorMinimo){
                indice=i;
                valorMinimo=valorArgumento;
            }
        }
        return indice;
    }
    
    public float sumMaxNodeEdge(int indice){
        float valorArgumento=0;
        for(int j=0;j<vertices.size();j++){
                if(indice!=j){//
                    //indice=0;
                    
                    valorArgumento=valorArgumento+maxEdgeNodeFunc(indice,j);
                }
                
            }
        return valorArgumento;
    }
    public ArrayList<Paquete> choose(){
        while(vertices.size()>k){
            vertices.remove(argmin());
            
        }
        return vertices;
    }
    public void imprimir(){
        for(int i=0;i< vertices.size();i++){
            vertices.get(i);
        }
    }

}
