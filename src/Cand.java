
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author moyan
 */
public class Cand {
    	int idCiudad;
	int budget;//limite
	float alfa;
	ArrayList<Paquete> soluciones=new ArrayList<Paquete>();

    public Cand(listaRestaurantes lista,int ciudad,int presupuesto){
        this.idCiudad=ciudad;
        this.budget=presupuesto;
        //cuando se inicia deberia crear un paque te de tods los restaurantes
        generarCandidatos(lista);
    }
        public Cand(int ciudad,int presupuesto){
        this.idCiudad=ciudad;
        this.budget=presupuesto;
        //cuando se inicia deberia crear un paque te de tods los restaurantes
        
    }

   
    public ArrayList<Paquete> getSoluciones(){
        return soluciones;
    }
    /**
     * tamaño de la cantidad de candidatos
     */
    
    public void generarCandidatos(listaRestaurantes lista){
        //un for que crea un paquete por cada elemento de la lista
        for(int i=0;i<lista.getTamanoLista();i++){
            Paquete nuevoPaquete=new Paquete(budget,idCiudad);
            
            nuevoPaquete.agregarRestaurante(lista.getRestaurante(i));
            
            soluciones.add(nuevoPaquete);
        }
    }
    /**
     * Crea el paquete en el cluster y devuelve su indice
     * @param lista 
     */
    
    public int size(){
        return soluciones.size();
    }
/**
     * Ingresamos el id y validamos la union de 2 cluster
     * @return regresa falso si la union es invalida
     */

        /**
     * Retorna el valor obtenido en la union de 2 cluster
     * @param primerCluster
     * @param segundoCluster
     * @return 
     */

    /**
     * Retorna el valor obtenido en la union de 2 cluster tambien denominado como el inter
     * @param primerCluster
     * @param segundoCluster
     * @return 
     */
    public float score(int primerCluster,int segundoCluster){
        Paquete uno = soluciones.get(primerCluster);
        Paquete dos = soluciones.get(segundoCluster);
        Paquete auxiliar;
        auxiliar = Union(primerCluster,segundoCluster);
        //System.out.println("des"+(uno.compatibilidad()+dos.compatibilidad()));
        //System.out.println("das"+auxiliar.compatibilidad());
        return (auxiliar.compatibilidad());
    }
    
    public float inter(int primerCluster,int segundoCluster){

        Paquete paqueteUno=soluciones.get(primerCluster);
        Paquete paqueteDos= soluciones.get(segundoCluster);
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
    
    public float intraCluster(int idCluster) {
    	return soluciones.get(idCluster).compatibilidad();
    }
    
    /**
     * Nueva funcion de fitness implementada en el paper del clei
     * se busca mejorar los resultado presentados por el chac
     */
    
    public float IntraInter(float t, int primerCluster, int segundoCluster){
        return (alfa*score( primerCluster, segundoCluster))+((1-alfa)*t*inter(primerCluster,segundoCluster));
    }    
    /**
     * Devuelve la union de dos cluster
     * @param primerCluster
     * @param segundoCluster
     * @return paquete
     */
    public Paquete Union(int primerCluster,int segundoCluster){
        Paquete union=new Paquete(budget,idCiudad);
        Paquete primer=soluciones.get(primerCluster);
        Paquete segundo=soluciones.get(segundoCluster);
        union.addRestaurantes(primer.getRestaurantes());
        union.addTiposCocinas(primer.getTiposCocinas());
        union.addPresupuesto(primer.getPresupuesto());
        union.addRestaurantes(segundo.getRestaurantes());
        union.addTiposCocinas(segundo.getTiposCocinas());
        union.addPresupuesto(segundo.getPresupuesto());
        
        return union;
    }
    
    
    /**
     * Agregar un paquete(cluster)a la lista de candidatos 
     * @param bestCandidate 
     */
    public void add(Paquete bestCandidate){
        soluciones.add(bestCandidate);
    }
    
    public void deleteS_bestcandidate(int firstCluster, int SecondCluster){
        Paquete first=soluciones.get(firstCluster);
        Paquete second=soluciones.get(SecondCluster);
        soluciones.remove(first);
        soluciones.remove(second);
        
    }
    
    /**
     * Obtiene la totalidad de los elemestos de los candidatos
     * @return 
     */
    public int totalElementos(){
        int total=0;
        for(int i=0 ; i<soluciones.size();i++){
            total=soluciones.get(i).tamanoSolucion()+total;
        }
        return total;
    }
    
    
    public boolean existeTipoCosina(ArrayList<Integer> clusterUno,ArrayList<Integer> clusterDos){

            //obtine la lista del paquete ,
            //por restaurante del paquete revisa si existe el tipo de cocina
            //si existe igualdad retorna true , en caso contrario retorna false
            for(int i=0;i<clusterUno.size();i++){
                for (int j = 0; j < clusterDos.size(); j++) {
                    if(clusterUno.get(i) == clusterDos.get(j)){
                        return true;
                    }
                }
            }
            return false;
    }
    public boolean validarUnion(int primerCluster,int segundoCluster){
        Paquete uno = soluciones.get(primerCluster);
        Paquete dos = soluciones.get(segundoCluster);
        if(existeTipoCosina(uno.getTiposCocinas(),dos.getTiposCocinas()))
            return false;
        if((uno.getPresupuesto()+dos.getPresupuesto())>budget)
            return false;
        return true;
    }
    
    
    public void internValue(){
        for(int i=0;i<size();i++){
            System.out.println(soluciones.get(i).tamanoSolucion());
            System.out.println(soluciones.get(i).compatibilidad());
        }
            
    }
    public String getElements(int i) {
    	return soluciones.get(i).imprimirIDPaquete();
    }
    
    /**
     * Funcion que devuelve una matriz de tamaño Cluster x Cluster con todos los inter de los elementos
     * @return una matriz  ID1 ID2 InterValue
     */
    public int getValueCluster(){
        return (size()*(size()-1))/2;
    }
    public double[][] matrixInter(){
    	int sizeMat=(size()*(size()-1))/2;
    	System.out.print(sizeMat);
    	int id=0;
    	double[][]interArr=new double[sizeMat][3];
    	for (int i=0;i<size();i++){
    		
    		for(int j=i;j<size();j++) {
    			if(i==j) {	
    			}
    			else {
    			interArr[id][0]=i;
    			interArr[id][1]=j;
    			interArr[id][2]=inter(i,j);		
    			id=id+1;
    		
    			}
    		
    		}
    		
    	}
    	return interArr;
    }
    public double[][] matrixInter2(){
    	int sizeMat=size();
    	//System.out.print(sizeMat);
    	
    	double[][]interArr=new double[sizeMat][sizeMat];
    	for (int i=0;i<size();i++){
    		
    		for(int j=0;j<(size());j++) {
    			if(i==j){
    				interArr[i][j]=0;
    			}
    			else{
    				interArr[i][j]=inter(i,j);
    			}
    			
    		}
    		
    	}
    	return interArr;
    }
    public double[][] matrixIntra(){
    	
    	double[][]intraArr=new double[size()][2];
    	for (int i=0;i<size();i++){
    		intraArr[i][0]=i;
    		intraArr[i][1]=intraCluster(i);		
    				
    	}
    	return intraArr;
    }
    
}
