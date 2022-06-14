import java.io.*;
import java.util.*;

//import gurobi.GRBException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kontaro
 */
public class Algorithm {
    int iteracionesVecinos;
    int kpaquetes;
    float alfa;
    int ciudad;
    int presupuesto;
    listaRestaurantes listaPrueba;
    
    
    
    public Algorithm(int iteracionesVecinos,int kpaquetes,float alfa,int presupuesto){
        this.iteracionesVecinos=iteracionesVecinos;
        this.kpaquetes=kpaquetes;
        this.alfa=alfa;
        this.presupuesto=presupuesto;
        
    }
    
    public Solucion RandomSearch(int maxIter,int ciudad,listaRestaurantes listaPrueba){
            int iter=0;
            ArrayList<Solucion> bestSolutions= new ArrayList<Solucion>();
            Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            bestSolutions.add(best);

            while(iter<maxIter){
                iter++;
                Solucion solucion=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
                if(solucion.funcionFitness()>=best.funcionFitness()){
                        best=solucion;
                        bestSolutions.add(solucion);
                }
            }
            return best;

        }
    
    /**
     * Funcion que hace una busqueda local en la ciudad especificada
     * @param ciudad id de la ciudad en la cual se escogen los restaurantes
     * @param listaPrueba lista de restaurantes
     * @return Rotarna una solucion valida
     */
    public Solucion LocalSearchFirstImprove1(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
      
        boolean flag=true;

       
        while(flag){
        	
 
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarRestauranteAzar(listaPrueba);
                        //vecino.cambiarPaqueteAzar(listaPrueba);
                        if(vecino.funcionFitness()>best.funcionFitness()){
                        	best.update(vecino); 

                            break;
                        
			            }else{
			                flag=false;
			            }
            }
            
        }
        
        return best;
    }
    
    /**
     * Funcion que hace una busqueda local en la ciudad especificada
     * @param ciudad id de la ciudad en la cual se escogen los restaurantes
     * @param listaPrueba lista de restaurantes
     * @return Rotarna una solucion valida
     */
    public Solucion LocalSearchFirstImprove(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
      
        boolean flag=true;

       
        while(flag){
        	
 
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        //vecino.cambiarRestauranteAzar(listaPrueba);
                        vecino.cambiarPaqueteAzar(listaPrueba);
                        if(vecino.funcionFitness()>best.funcionFitness()){
                        	best.update(vecino); 

                            break;
                        
			            }else{
			                flag=false;
			            }
            }
            
        }
        
        return best;
    }
    /**
     * Funcion que hace una busqueda local en la ciudad especificada
     * @param ciudad id de la ciudad en la cual se escogen los restaurantes
     * @param listaPrueba lista de restaurantes
     * @return Rotarna una solucion valida
     */
    public Solucion LocalSearch(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
        //System.out.println("entro a local");
        boolean flag=true;
        //
        //System.out.println(best.funcionFitness());
        while(flag){
        	
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarRestauranteAzar(listaPrueba);
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            //
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	//System.out.println("encontro algo mejor");
            	//System.out.println(mejorVecino.funcionFitness());
            	//System.out.println(mejorVecino.datosFitness());
            	
            	best.update(mejorVecino);
            	//System.out.println(best.funcionFitness());
            }else{
                flag=false;
            }
        }
        
        return best;
    }
    public Solucion StartLocalSearch(int ciudad,listaRestaurantes listaPrueba,int tipo){
    	//System.out.println("antes "+tipo);
    	Solucion best=null;
    	if(tipo==1){
    		best=RandomSearch(10,ciudad,listaPrueba);
    	}
    	else {
    		if(tipo==2){
    			//System.out.println("antes de nuevo");
    			best=NewInitialSolution(ciudad,listaPrueba);
    			//System.out.println("despues de nuevo");
    		}
    		if(tipo==3) {
    			best=NewLocalSearch3(ciudad, listaPrueba);
    		}
    		else {
    			best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
    		}
    	
    	}
    	//System.out.println(best.funcionFitness());
        //System.out.println("entro a local");
        boolean flag=true;
        //System.out.println("despues");
        while(flag){
        	
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            //System.out.println("entro al for");
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarRestauranteAzar(listaPrueba);
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            
            //System.out.println("salio del for");
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	//System.out.println("encontro algo mejor");
            	//System.out.println(mejorVecino.funcionFitness());
            	best.update(mejorVecino);
            	//System.out.println(best.funcionFitness());
            }else{
                flag=false;
            }
        }
        
        return best;
    }
    public Solucion LocalSearchPaquete(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
        System.out.println(best.funcionFitness());
        boolean flag=true;
        while(flag){
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarPaqueteAzar(listaPrueba);
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            //
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	//System.out.println(mejorVecino.datosFitness());
                best.update(mejorVecino);
                System.out.println(best.funcionFitness());
            }else{
                flag=false;
            }
        }
        //System.out.println("Salio de un while");
        return best;
    }
    
    /**
     * Sin funcion swap no sirve
     * @param ciudad
     * @param listaPrueba
     * @return
     */
    public Solucion LocalSearchExhautivo(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
        //System.out.println("entro a local");
        boolean flag=true;

        //System.out.println(best.funcionFitness());
        while(flag){
        	
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarRestauranteExhaustivo(listaPrueba);
                        //System.out.println("vecino "+vecino.funcionFitness());
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            //
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	//System.out.println("encontro algo mejor");
            	//System.out.println(mejorVecino.funcionFitness());
            	best.update(mejorVecino);

            }else{
                flag=false;
            }
        }
        
        return best;
    }
    
    /**
     * Sini funcion swap no sirve
     * @param ciudad
     * @param listaPrueba
     * @return
     */
    public Solucion LocalSearchPaqueteExhautivo(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
        //System.out.println("entro a local");
        boolean flag=true;
        
  
        while(flag){
        	
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        vecino.cambiarPaqueteExhaustivo(listaPrueba);
                        //System.out.println("vecino "+vecino.funcionFitness());
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            //
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	best.update(mejorVecino);

            }else{
                flag=false;
            }
        }
        
        return best;
    }
    
    public Cand C_HAC(int ciudad,listaRestaurantes lista){
        //para adaptarnos a este algoritmo convertiremos cada restaurante en un paquete
        //ademas implementaremos una funcion para juntar paquetes ? o sera mejor implementarlo en la Solucion 

            //Solucion candidatos;
            Cand candidatos= new Cand(lista,ciudad, presupuesto);
            //IMPORTANTE:Las funcionces de Candidatos no se encuentran toalmente IMPLEMENTADAS

            //score

            //

            while(candidatos.size()>kpaquetes){ 
                float bestScore=-1;
                Paquete bestCandidate=null;
                int indexClusterFirst=0;
                int indexClusterSecond=0;
                for(int i=0;i< candidatos.size();i++){
                    for(int j=0;j< candidatos.size() ;j++){
                        //no se puede juntar un paquete consigo mismo
                        if(i!=j){
                           
                           if(candidatos.validarUnion(i,j)){
                               
                               if(candidatos.score(i,j)>bestScore){
                                   
                                   bestScore=candidatos.score(i,j);
                                   bestCandidate=candidatos.Union(i,j);
                                   indexClusterFirst=i;
                                   indexClusterSecond=j;
                                }                                   
                            }
                        }
                    }
                }

                if(bestCandidate==null){
                    break;
                }
                else{
                  
                    candidatos.deleteS_bestcandidate(indexClusterFirst, indexClusterSecond);
                    candidatos.add(bestCandidate);
                }

            }
            return candidatos;

        }
        public Cand Intra_Inter_C_HAC(int ciudad,listaRestaurantes lista,float t){
        //para adaptarnos a este algoritmo convertiremos cada restaurante en un paquete
        //ademas implementaremos una funcion para juntar paquetes ? o sera mejor implementarlo en la Solucion 

            //Solucion candidatos;
            Cand candidatos= new Cand(lista,ciudad, presupuesto);
            //IMPORTANTE:Las funcionces de Candidatos no se encuentran toalmente IMPLEMENTADAS

            //score

            //

            while(candidatos.size()>kpaquetes){ 
                float bestScore=-1;
                Paquete bestCandidate=null;
                int indexClusterFirst=0;
                int indexClusterSecond=0;
                for(int i=0;i< candidatos.size();i++){
                    for(int j=0;j< candidatos.size() ;j++){
                        //no se puede juntar un paquete consigo mismo
                        if(i!=j){
                           
                           if(candidatos.validarUnion(i,j)){
                               
                               if(candidatos.IntraInter(t, i, j) > bestScore){
                                   
                                   bestScore=candidatos.score(i,j);
                                   bestCandidate=candidatos.Union(i,j);
                                   indexClusterFirst=i;
                                   indexClusterSecond=j;
                                }                                   
                            }
                        }
                    }
                }

                if(bestCandidate==null){
                    break;
                }
                else{
                  
                    candidatos.deleteS_bestcandidate(indexClusterFirst, indexClusterSecond);
                    candidatos.add(bestCandidate);
                }

            }
            return candidatos;

        }
    
        public Solucion PAC(int ciudad,listaRestaurantes lista, int hac){
            Cand cluster;
            if(hac==1){
                cluster=C_HAC(ciudad,lista);
                
            }//System.out.println(cluster.soluciones.size());
            else{
                cluster=Intra_Inter_C_HAC(ciudad,lista,20);
            }
            //seeCandidates(cluster);
            Graph MES = new Graph(cluster,kpaquetes,alfa);
            //System.out.println(MES.vertices.size());
            MES.choose();
            //System.out.println(MES.vertices.size());
            Solucion solucion=new Solucion(ciudad,alfa,presupuesto,MES.getVertice());
            //System.out.println(solucion.paquetes.size());
            return solucion;
        }
        
        /**
        public Solucion PAC_IP(int ciudad,listaRestaurantes lista, int hac){
            Cand cluster;
            
            //Se elije que tipo de C-hac se va a utilizar
            if(hac==1){
                cluster=C_HAC(ciudad,lista);
                
            }//System.out.println(cluster.soluciones.size());
            else{
                cluster=Intra_Inter_C_HAC(ciudad,lista,20);
            }
            
            //Transformamos los datos para el modelo de gurobi
            double[][] intraArray= cluster.matrixIntra();
            double[][] interArray = cluster.matrixInter2();
            int sizeMat=cluster.size();
            int escogidos[] = null;
            //Instanciamos el problema y lo resolvemos
            Problem gurobi_Solver=new Problem(kpaquetes,sizeMat,alfa,intraArray,interArray);
            try {
                
                escogidos = gurobi_Solver.armarProblema();
            } catch (GRBException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            //System.out.println("sali del solver");

            //Solucion solucion=new Solucion(ciudad,alfa,presupuesto,MES.getVertice());
            
            ArrayList<Paquete> listaAux= new ArrayList<Paquete>();
            for(int i=0;i<escogidos.length;i++) {
            	listaAux.add(cluster.soluciones.get(escogidos[i]));
            }
            Solucion solucion=new Solucion(ciudad, alfa, presupuesto, listaAux);
            //System.out.println(solucion.paquetes.size());
            return solucion;
        }

        public Solucion PAC_aux(int ciudad,listaRestaurantes lista, int hac){
            Cand cluster;
            
            //Se elije que tipo de C-hac se va a utilizar
            if(hac==1){
                cluster=C_HAC(ciudad,lista);
                
            }//System.out.println(cluster.soluciones.size());
            else{
                cluster=Intra_Inter_C_HAC(ciudad,lista,20);
            }
            double[][] intraArray= cluster.matrixIntra();
            double[][] interArray = cluster.matrixInter();
            int tam=cluster.soluciones.size();
            //for(int i=0;i<tam;i++) {
            //	System.out.print(Integer.parse(intraArray[i][0]+" "));
            //	System.out.println(intraArray[i][1]);
            //}
            
            String  inter=""; 
            for(int i=0;i<((tam*(tam-1))/2);i++) {
            	
            	inter=inter+Double.toString(interArray[i][0])+" ";
            	inter=inter+Double.toString(interArray[i][1])+" ";
            	inter=inter+Double.toString(interArray[i][2])+"\n";
            	
            }
            
            
            FileWriter Inter=crearArchivo("Inters2.txt");
    

        	writeFile(Inter,inter);
        	
        	try {

				Inter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
            
            //recuperamos los paquetes y lo transformamos en una solucion
            //System.out.println(MES.vertices.size());
           
            //System.out.println(MES.vertices.size());
            //Solucion solucion=new Solucion(ciudad,alfa,presupuesto,MES.getVertice());
            
            
            //System.out.println(solucion.paquetes.size());
            return null;
        }
        **/
        public Solucion VNS(int ciudad,listaRestaurantes lista){
        	
        	int mov=1;
        	boolean flag1=true;
        	boolean flag2=true;
        	//Solucion best=RandomSearch(200,ciudad,lista);
        	Solucion best=NewInitialSolution(ciudad,lista);
        	//System.out.println(best.datosFitness());
        	if(true) {
        		//System.out.println(best.funcionFitness());
            }
            while(flag1 || flag2){
            	
                ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
                Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
                mejorVecino.costo = 0;
                for(int j=0;j<iteracionesVecinos;j++){
                			Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                			vecino.update(best);
                			//System.out.println(vecino.datosSolucion(2));
                			if(flag1==true) {
                				//vecino.cambiarPaqueteAzar(lista);
                				vecino.cambiarRestauranteAzar(lista);
                				//System.out.println("entro paquete");
                				//System.out.println(vecino.datosSolucion(2));
                			}
                            else {
                            	vecino.cambiarPaqueteAzar(lista);
                            	//vecino.cambiarRestauranteAzar(lista);
                            	//System.out.println("entro");
                            }
                			
                		
                            if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            	
                                mejorVecino.update(vecino);  
                            }
                }
                //
                if(mejorVecino.funcionFitness() > best.funcionFitness()){
                	//System.out.println("encontro algo mejor");
                	//System.out.println(mejorVecino.funcionFitness());
                	best.update(mejorVecino);
                	//System.out.println(mejorVecino.datosFitness());
                	//System.out.println(best.funcionFitness());
                	if(flag1==false) {
                		flag1=true;
                	}
                	if(true) {
                		//System.out.println(best.funcionFitness());
                    }
                }else{
                	//System.out.println("entro flag");
                	if(flag1==false)flag2=false;
                    flag1=false;
                    
                }
            }
            
            
            return best;
        }
public Solucion SecondVNS(int ciudad,listaRestaurantes lista){
        	
        	int mov=1;
        	boolean flag1=true;
        	boolean flag2=true;
        	//Solucion best=RandomSearch(200,ciudad,lista);
        	Solucion best=NewInitialSolution(ciudad,lista);
        	//System.out.println(best.datosLista());
        	
            while(flag1 || flag2){
            	
                ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
                Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
                mejorVecino.costo = 0;
                for(int j=0;j<iteracionesVecinos;j++){
                			Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                			vecino.update(best);
                			//System.out.println(vecino.datosSolucion(2));
                			if(flag1==true) {
                				//vecino.cambiarPaqueteAzar(lista);
                				vecino.cambiarRestauranteExhaustivo(lista);;
                				//System.out.println("entro paquete");
                				//System.out.println(vecino.datosSolucion(2));
                			}
                            else {
                            	vecino.cambiarPaqueteExhaustivo(lista);
                            	//vecino.cambiarRestauranteAzar(lista);
                            	//System.out.println("entro");
                            }
                			
                		
                            if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                                mejorVecino.update(vecino);  
                            }
                }
                //
                if(mejorVecino.funcionFitness() > best.funcionFitness()){
                	//System.out.println("encontro algo mejor");
                	//System.out.println(mejorVecino.funcionFitness());
                	best.update(mejorVecino);
                	if(flag1==false) {
                		flag1=true;
                	}
                	
                }else{
                	//System.out.println("entro flag");
                	if(flag1==false)flag2=false;
                    flag1=false;
                    
                }
            }
            
            return best;
        }
        
public Solucion newVNS(int ciudad,listaRestaurantes lista,int iteraciones, int iterExploracion,int tipoExplotacion){
	

	boolean flag1=false;//flag de convergencia
	int iter=0;// No me agrada utilizar iteraciones podriacambiarse despues
	Solucion best=RandomSearch(400,ciudad,lista);
	Solucion bestActual=NewInitialSolution(ciudad,lista);

	while(iter<iteraciones){
		flag1=false;
    	//System.out.println("entre exploracion");
        Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
        mejorVecino.costo = 0;
        for(int i=0;i<iterExploracion;i++ ) {
        	
        	//Voy a convertir esto en una funcion despues
            for(int j=0;j<iteracionesVecinos;j++){
            		//genero la solucion que se va a mover
            		Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            		vecino.update(best);
            		//vecino.cambiarPaqueteAzar(lista);
            		//System.out.println("entre valor "+vecino.funcionFitness());
            		if(tipoExplotacion==0)
            			vecino.cambiarPaqueteAzar(lista);
        			else
        				vecino.cambiarPaqueteExhaustivo(lista);
        		
            		
            		
            		if(vecino.funcionFitness()>=mejorVecino.funcionFitness()){   	
            			mejorVecino.update(vecino);  
                    }
            }
            if(mejorVecino.funcionFitness()>=best.funcionFitness()) {
            	bestActual.update(mejorVecino);
            	break;
            }
        }	
        //Termina la exploracion
        
        bestActual.update(mejorVecino);
       // System.out.println("valor Actual "+bestActual.funcionFitness()+" Valor best "+best.funcionFitness());
        
        //comienza la explotacion
        while(flag1==false){//mientras no converga la explotacion
        	//System.out.println("entre explotacion");
            for(int j=0;j<iteracionesVecinos;j++){
    			//genero la solucion que se va a mover
    			Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
    			vecino.update(bestActual);
    			if(tipoExplotacion==0)
    				vecino.cambiarRestauranteAzar(lista);
    			else
    				vecino.cambiarRestauranteExhaustivo(lista);
    		
                if(vecino.funcionFitness()>=mejorVecino.funcionFitness()){
                	
                    mejorVecino.update(vecino);  
                }   
            }
            
            if(mejorVecino.funcionFitness()>bestActual.funcionFitness()) {
            	bestActual.update(mejorVecino);
            }//si no es mejor que el actual significa que convergio
            else {
            	flag1=true;
            }
        }
        
        if(bestActual.funcionFitness() > best.funcionFitness()){
        	//System.out.println("entre mejorar");
        	best.update(bestActual);
        	
        	//System.out.println(best.funcionFitness());
        }
        iter++;
    }
    //Cuando termina retorna la mejor solucion que se encuentra en best

    return best;
    
}
public Solucion newVNS(int ciudad,listaRestaurantes lista,int iteraciones, int iterExploracion,int tipoExplotacion,long chac){
	

	boolean flag1=false;//flag de convergencia
	int iter=0;// No me agrada utilizar iteraciones podriacambiarse despues
	Solucion best=NewInitialSolution(ciudad,lista);
	Solucion bestActual=NewInitialSolution(ciudad,lista);
	Solucion initialState=RandomSearch(400,ciudad,lista);
	initialState.update(best);
	//long actualTime=System.currentTimeMillis();
	//ArrayList<Solucion> vecindad=new ArrayList<Solucion>();
	while(iter<iteraciones ){
		flag1=false;
    	
        Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
        bestActual.update(initialState);
        //System.out.println(initialState.funcionFitness());
        mejorVecino.costo = 0;
        
        for(int i=0;i<iterExploracion;i++ ) {
        	
        	//Voy a convertir esto en una funcion despues
            for(int j=0;j<iteracionesVecinos;j++){
            		//genero la solucion que se va a mover
            		Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            		vecino.update(bestActual);

            		if(tipoExplotacion==0)
            			vecino.cambiarPaqueteAzar(lista);
        			else
        				vecino.cambiarPaqueteExhaustivo(lista);
        		
            		
            		
            		if(vecino.funcionFitness()>=mejorVecino.funcionFitness()){   	
            			mejorVecino.update(vecino);  
                    }
            }
            if(mejorVecino.funcionFitness()>=bestActual.funcionFitness()) {
            	bestActual.update(mejorVecino);
            	
            }
            if(mejorVecino.funcionFitness()>=best.funcionFitness()) {
            	bestActual.update(mejorVecino);
            	break;
            }
        }	
        //Termina la exploracion
        
        bestActual.update(mejorVecino);
       // System.out.println("valor Actual "+bestActual.funcionFitness()+" Valor best "+best.funcionFitness());
        
        //comienza la explotacion
        while(flag1==false){//mientras no converga la explotacion
        	//System.out.println("entre explotacion");
            for(int j=0;j<iteracionesVecinos;j++){
    			//genero la solucion que se va a mover
    			Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
    			vecino.update(bestActual);
    			if(tipoExplotacion==0)
    				vecino.cambiarRestauranteAzar(lista);
    			else
    				vecino.cambiarRestauranteExhaustivo(lista);
    			//vecindad.add(vecino);
                if(vecino.funcionFitness()>=mejorVecino.funcionFitness()){
                	
                    mejorVecino.update(vecino);  
                }   
            }
            
            if(mejorVecino.funcionFitness()>bestActual.funcionFitness()) {
            	bestActual.update(mejorVecino);
            }//si no es mejor que el actual significa que convergio
            else {
            	flag1=true;
            }
        }
        
        if(bestActual.funcionFitness() > best.funcionFitness()){
        	best.update(bestActual);
        	initialState.update(best);
        	initialState.cambiarPaqueteAzar(lista);
        	
        }else{
        	initialState.update(best);
        	initialState.cambiarPaqueteAzar(lista);
        }
        iter++;
    }
    //Cuando termina retorna la mejor solucion que se encuentra en best
    return best;
    
}

       
        
        public Solucion ILS(int ciudad,listaRestaurantes listaPrueba, long timeCHAC,int movimiento){//iterative-LocaL Search
            long time=(System.currentTimeMillis());
            int cont=1;
            Solucion bestBudget=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            Solucion result=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            //se pone antes del while para que no pierda tiempo en la consulta
            if(movimiento==1)bestBudget=LocalSearch(ciudad,listaPrueba);
            if(movimiento==2)bestBudget=LocalSearchPaquete(ciudad,listaPrueba);
            if(movimiento==3)bestBudget=LocalSearchExhautivo(ciudad,listaPrueba);
            if(movimiento==4)bestBudget=LocalSearchPaqueteExhautivo(ciudad,listaPrueba);
            if(movimiento==5)bestBudget=InitialSolution(ciudad,listaPrueba);
            if(movimiento==6)bestBudget=NewInitialSolution(ciudad,listaPrueba);
            //System.out.println(bestBudget.funcionFitness());
            while(timeCHAC>(System.currentTimeMillis()-time)){
            	cont=cont+1;
            	//System.out.println(timeCHAC+" "+(System.currentTimeMillis()-time));
            	if(movimiento==1)result=LocalSearch(ciudad,listaPrueba);
                if(movimiento==2)result=LocalSearchPaquete(ciudad,listaPrueba);
                if(movimiento==3)result=LocalSearchExhautivo(ciudad,listaPrueba);
               // System.out.println("entro al localSearch");
                if(movimiento==4)result=LocalSearchPaqueteExhautivo(ciudad,listaPrueba);
                if(movimiento==5)result=VNS(ciudad,listaPrueba);
                if(movimiento==6)result=VNS(ciudad,listaPrueba);
                if(result.costo>bestBudget.costo){
                //	System.out.println("entro al if"); 
                	bestBudget.update(result);
                  //  System.out.println("salio del if");
                } 
                //System.out.println("salio del while");
            }
            System.out.println(cont);
//parte un local search
            //si converge y aun queda tiempo tira un random
            return bestBudget;
            
        }
        

        public Solucion ILSImprove(int ciudad,listaRestaurantes listaPrueba, long timeCHAC,int movimiento){//iterative-LocaL Search
            long time=(System.currentTimeMillis());
            int cont=1;
            Solucion bestBudget=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            Solucion result=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            //se pone antes del while para que no pierda tiempo en la consulta
            if(movimiento==1)bestBudget=LocalSearchFirstImprove1(ciudad, listaPrueba);
            if(movimiento==2)bestBudget=LocalSearchFirstImprove(ciudad, listaPrueba);

            //System.out.println(bestBudget.funcionFitness());
            while(timeCHAC>(System.currentTimeMillis()-time)){
            	cont=cont+1;
            	//System.out.println(timeCHAC+" "+(System.currentTimeMillis()-time));
            	if(movimiento==1)result=LocalSearchFirstImprove1(ciudad, listaPrueba);
                if(movimiento==2)result=LocalSearchFirstImprove(ciudad, listaPrueba);

                if(result.costo>bestBudget.costo){
                //	System.out.println("entro al if"); 
                	bestBudget.update(result);
                  //  System.out.println("salio del if");
                } 
                //System.out.println("salio del while");
            }

//parte un local search
            //si converge y aun queda tiempo tira un random
            return bestBudget;
            
        }
        public Solucion IVNS(int ciudad,listaRestaurantes listaPrueba, long timeCHAC,int movimiento){//iterative-LocaL Search
            long time=(System.currentTimeMillis());
            Solucion bestBudget=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            Solucion result=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
            //se pone antes del while para que no pierda tiempo en la consulta
            if(movimiento==1)bestBudget=newVNS(ciudad, listaPrueba,70,30,0);
            if(movimiento==2)bestBudget=newVNS(ciudad, listaPrueba,70,30,1);

            
            while(timeCHAC>(System.currentTimeMillis()-time)){
            	//System.out.println(timeCHAC+" "+(System.currentTimeMillis()-time));
            	if(movimiento==1)result=newVNS(ciudad, listaPrueba,70,30,0);
                if(movimiento==2)result=newVNS(ciudad, listaPrueba,70,30,1);
 
                if(result.costo>bestBudget.costo){
                //	System.out.println("entro al if"); 
                	bestBudget.update(result);
                  //  System.out.println("salio del if");
                } 
                //System.out.println("salio del while");
            }
//parte un local search

            return bestBudget;
            
        }
        
        public Solucion FlyAlgorithm(int ciudad,listaRestaurantes lista,int flies){
            return null;
        }
        
    //--------------Nuevos algoritmos local search
        /**
         * Algoritmo de busqueda local, la creacion de la vecindad depende del centroide de la solucion
         * 
         * @param ciudad
         * @param listaPrueba
         * @return
         */
        public Solucion NewLocalSearch(int ciudad,listaRestaurantes listaPrueba){
            Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);

            boolean flag=true;
            while(flag){
                ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
                Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
                mejorVecino.costo = 0;
                for(int j=0;j<iteracionesVecinos;j++){

                            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                            vecino.update(best);
                            
                            vecino.cambiarRestauranteLejano(listaPrueba);
                            if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                                mejorVecino.update(vecino);  
                            }
                }
                //
                if(mejorVecino.funcionFitness() > best.funcionFitness()){
                    best.update(mejorVecino);
                }else{
                    flag=false;
                }
            }
            return best;
        }
        public Solucion NewLocalSearch2(int ciudad,listaRestaurantes listaPrueba){
            Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);

            boolean flag=true;
            while(flag){
                ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
                Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
                mejorVecino.costo = 0;
                for(int j=0;j<iteracionesVecinos;j++){

                            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                            vecino.update(best);
                            
                            vecino.cambiarRestauranteLejano2(listaPrueba);
                            if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                                mejorVecino.update(vecino);  
                            }
                }
                //
                if(mejorVecino.funcionFitness() > best.funcionFitness()){
                    best.update(mejorVecino);
                }else{
                    flag=false;
                }
            }
            return best;
        }
        public Solucion NewLocalSearch3(int ciudad,listaRestaurantes listaPrueba){
            Solucion best=NewInitialSolution(ciudad,listaPrueba);

            boolean flag=true;
            while(flag){
                ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
                Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
                mejorVecino.costo = 0;
                for(int j=0;j<iteracionesVecinos;j++){

                            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                            vecino.update(best);
                            
                            vecino.cambiarRestauranteLejano3(listaPrueba);
                            if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                                mejorVecino.update(vecino);  
                            }
                }
                //
                if(mejorVecino.funcionFitness() > best.funcionFitness()){
                    best.update(mejorVecino);
                }else{
                    flag=false;
                }
            }
            return best;
        }
        public Solucion NewInitialSolution(int ciudad,listaRestaurantes listaPrueba){
        	
        	 Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto,1);
        	 return best;
        }
        public Solucion InitialSolution(int ciudad,listaRestaurantes listaPrueba){
        	
          	 Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto,"1");
          	 return best;
          }
        
    // Algoritmo genetico de la Nicolle c:
        public Solucion geneticAlgorithm(int ciudad, listaRestaurantes listaprueba, float porcentajePadres, int seleccion, float porcentajeMutacion) throws FileNotFoundException {
        	int numPoblacion = 100;
        	int iteraciones = 100;
        	long millis = System.currentTimeMillis();
    		
        	SolucionGenetico actual = new SolucionGenetico( listaprueba, ciudad, alfa, presupuesto, kpaquetes, numPoblacion);
        	Solucion mejor = new Solucion(ciudad, alfa, presupuesto);
        	
        	mejor.updatePaquete(actual.mejorSolucion().getPaquetes2());
        	while(iteraciones > 0) {
        		actual.actualizarSoluciones(porcentajePadres, seleccion, porcentajeMutacion, iteraciones);
        		
        		if((actual.mejorSolucion().fitness() - mejor.funcionFitness()) > 0){
        			mejor.updatePaquete(actual.mejorSolucion().getPaquetes());
        			
        		}
        		iteraciones--;
        	}
        	return mejor;
        }
        
        
        
    // Nuevos Algoritmos para Composite Retrieval
        
/**        
        public Cand C_IP(int ciudad,listaRestaurantes lista){
        	//se llama a un algoritmo de clusterin 
        	Cand cluster=C_HAC(ciudad,lista);
        	FileWriter ID=crearArchivo("IDPaquete.txt");
        	FileWriter Intra=crearArchivo("Intra.txt");
        	FileWriter Inter=crearArchivo("Inters.txt");
        	String IDtexto="";
        	String IntraTexto="";
        	String InterTexto="";
        	for(int i=0;i<cluster.size();i++) {
        		
        		IDtexto=IDtexto+i+" "+cluster.getElements(i)+"\n";
        		IntraTexto=IntraTexto+i+" "+cluster.intraCluster(i)+" "+"\n";
        		for(int j=i;j<cluster.size();j++) {
        			InterTexto=InterTexto+i+" "+j+" "+(1-cluster.inter(i, j))+" "+"\n";
        		}
        	}
        	writeFile(ID,IDtexto);
        	writeFile(Intra,IntraTexto);
        	writeFile(Inter,InterTexto);
        	
        	try {
				ID.close();
				Intra.close();
				Inter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	try {
				RunIP(kpaquetes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	//se obtienen se separan los paquetes y se llama  a la parte entera
        	return null;
        	
        }
        
    //--------------Algoritmos Multi-objetivos
        //public Solucion C
        public int RunIP(int kpaquetes) throws IOException {
        	String comando="./selpaq Intra.txt Inters.txt "+kpaquetes;
        	Process p = Runtime.getRuntime().exec(comando);
        	return 0;
        }

**/
        public FileWriter crearArchivo(String nombreArchivo){
        	
        	FileWriter archivo=null;
        	try {
        		archivo= new FileWriter(nombreArchivo); 
        		
        	}
        	catch(IOException ex){
        		
        	}
        	
       	
        	return archivo;
        }
        public void writeFile(FileWriter archivo, String texto){
        	PrintWriter pw = null;
        	
        	pw = new PrintWriter(archivo);
        	pw.println(texto);
        	
        }

        //algoritmos de apoyo
        //public void 
}