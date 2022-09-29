import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MultiObjetiveAlgorithm extends Algorithm{
	float epsilon;
    public MultiObjetiveAlgorithm(int iteracionesVecinos, int kpaquetes, float alfa, int presupuesto) {
        super(iteracionesVecinos, kpaquetes, alfa, presupuesto);
        // TODO Auto-generated constructor stub
        this.epsilon=0;
      
    }

    public ArrayList<Solucion> ParetoLocalSearch(int ciudad,listaRestaurantes lista){
    	 //long Tiempo=System.currentTimeMillis();
        //epsilon=(float)0.1;
    	ArrayList<Solucion> allNeighborhood = new ArrayList<Solucion>();
        ArrayList<Solucion> Frontera = new ArrayList<Solucion>();
        ArrayList<Solucion> Visited = new ArrayList<Solucion>();
        boolean allVisited=false;
        for(float i=(float)0.0;i<1.1;i=i+(float)0.1) {
        	alfa=i;
        	Solucion best=VNS(ciudad, lista);
        	Frontera.add(best);
        }
        System.out.println("inicial: "+Frontera.size()); 
       // for(float i=(float)0.1;i<1.1;i=i+(float)0.1) {
       // 	alfa=i;
       // 	Solucion best=SecondVNS(ciudad, lista);
       // 	Frontera.add(best);
       // }
        //Solucion best=NewInitialSolution(ciudad,lista);
       // best.datosFitness();
        //System.out.println(","+best.funcionFitness());
        //Frontera.add(best);
        //printFront(Frontera);
        int iteraciones=0;
       
        //Mientras se puedea expandir la frontera
        while(!allVisited ) {
        //while(iteraciones<3) {
        	iteraciones++;
            for(Solucion nodo : Frontera ) {//Se genera la vecindad de los nodos no visitados en la frontera
                if(!Visited.contains(nodo)) {
                    generateNeighborhood(nodo, allNeighborhood,lista,1);
                    Visited.add(nodo);
                }

            }
            System.out.println("antes de actualizar "+allNeighborhood.size());         
            //actualizar dominancia
            NDP(Frontera, allNeighborhood,Visited,epsilon);
            NDPFront(Frontera);
            //System.out.println(Frontera.size());
            
            //actualizar estado de visitados
            if(Visited.containsAll(Frontera))
                allVisited=true;
            
            //printFront(Frontera);
            System.out.println(Frontera.size());
        }
        
        
        return Frontera;
    }
    
    //--------------------Otras funciones_----------------------//
    public ArrayList<Solucion> MOVNS(int ciudad,listaRestaurantes lista,int iteraciones){
    	int sizeFront=0;
    	boolean flag=false;
    	boolean allVisited=false;
    	int iterWithoutImp = 0;
    	ArrayList<Solucion> allNeighborhood = new ArrayList<Solucion>();
        ArrayList<Solucion> Frontera = new ArrayList<Solucion>();
        ArrayList<Solucion> Visited = new ArrayList<Solucion>();
        
    	int k=1;//flag de movimiento
    	
    	while(iterWithoutImp==2){
    		iteraciones++;
    		flag=false;
            for(Solucion nodo : Frontera ) {//Se genera la vecindad de los nodos no visitados en la frontera
                if(!Visited.contains(nodo)) {
                    generateNeighborhood(nodo, allNeighborhood,lista,k);
                    Visited.add(nodo);
                }

            }
            sizeFront=Frontera.size();
            //
            NDP(Frontera, allNeighborhood,Visited,0);
            
            if(k==2 && (sizeFront-Frontera.size())==0) {
            	iterWithoutImp=iterWithoutImp+1;
            	k=1;
            	
            }
            else {
	            if(((sizeFront-Frontera.size())==0)) {//no encontro ningun punto mejor
	            	k=k+1;
	            }
	            else {
	            	iterWithoutImp=0;
	            }
            }
            
            
            
    	}
           
        return Frontera;
        
    }
    public void NDP(ArrayList<Solucion> Frontera ,ArrayList<Solucion> allNeighborhood,ArrayList<Solucion> Visited, float epsilon) {
        //llamamos al sort
        boolean domino;
        //System.out.println("Entre a la funcion");
        ArrayList<Solucion> dominados=new ArrayList<Solucion>();//Se guardan en una lista para no generar problemas
        for(Solucion  neighbor: allNeighborhood ) {
        	//System.out.println("Neigbor");
        	//neighbor.datosFitness();
        	//System.out.println("");
            domino=false;
            for(Solucion nodo : Frontera ) {
                //si cumple condicion lo agrega a la frontera
                //si domina
            	
            	//System.out.println("Nodo");
            	//nodo.datosFitness();
            	//System.out.println("");
            	
            	
                if(neighbor.intra()>(nodo.intra()+epsilon) && neighbor.inter()>(nodo.inter()+epsilon)) {
                	
                    domino=true;
                    dominados.add(nodo);
                    
                }
                if(neighbor.intra()==(nodo.intra()+epsilon) && neighbor.inter()==(nodo.inter()+epsilon)) {
                    domino=true;
                    dominados.add(nodo);
                    
                }
                if((neighbor.intra()>=(nodo.intra()+epsilon) && neighbor.inter()!=(nodo.inter()))||(neighbor.intra()!=nodo.intra() && neighbor.inter()>=(nodo.inter()+epsilon))) {
                    domino=true;
                    //System.out.println("Domine ");
                    //dominados.add(nodo);
                    
                }
                if(neighbor.intra()<(nodo.intra()+epsilon) && neighbor.inter()<(nodo.inter()+epsilon)) {
                    domino=false;
                    //System.out.println("pase por aqui");
                    break;
                    
                    //dominados.add(nodo);
                    
                }
                
                
                
            }
            if(domino) {
            	//System.out.println("Si domino");
                Frontera.add(neighbor);
                if(!dominados.isEmpty()) {
                    Frontera.removeAll(dominados);
                }
                dominados.clear();
            }
            
        }
        allNeighborhood.clear();
    }
    
    //Entrega la frontera de pareto
    public void NDPFront(ArrayList<Solucion> Frontera) {
        //llamamos al sort
        boolean domino;
        //System.out.println("Entre a la funcion");
        ArrayList<Solucion> dominados=new ArrayList<Solucion>();//Se guardan en una lista para no generar problemas
        for(Solucion  neighbor: Frontera ) {
        	//System.out.println("Neigbor");
        	//neighbor.datosFitness();
        	//System.out.println("");
            domino=false;
            for(Solucion nodo : Frontera ) {
                //si cumple condicion lo agrega a la frontera
                //si domina
            	
            	//System.out.println("Nodo");
            	//nodo.datosFitness();
            	//System.out.println("");
            	
            	
                if(neighbor.intra()>(nodo.intra()) && neighbor.inter()>(nodo.inter())) {
                	
                    domino=true;
                    dominados.add(nodo);
                    
                }
                /**
                if((neighbor.intra()>=(nodo.intra()) && neighbor.inter()!=(nodo.inter()))||(neighbor.intra()!=nodo.intra() && neighbor.inter()>=(nodo.inter()))) {
                    domino=true;
                    //System.out.println("Domine ");
                    //dominados.add(nodo);
                    
                }
                **/
                
                
            }
            
        }
        if(!dominados.isEmpty()) {
        	Frontera.removeAll(dominados);        		
        }
        dominados.clear();
        
        
        
        
    }
    
    /**
     * Funcion que indica si una solucion p domina a una solucion q, en caso de ser cierto devuelve true
     * @param p
     * @param q
     * @return
     */
    public boolean dominar(Solucion p,Solucion q) {

        if((p.intra()>(q.intra()) && p.inter()>=(q.inter())) || (p.intra()>=(q.intra()) && p.inter()>(q.inter()))) {
            return true;
        }

        return false;

    }
    
    /**
     * Funcion que indica si una solucion p domina a una solucion q, en caso de ser cierto devuelve true
     * @param p
     * @param q
     * @return
     */
    public boolean dominar(Encode p,Encode q) {

        return((p.sumaIntra()>(q.sumaIntra()) && p.sumaInter()>=(q.sumaInter())) || (p.sumaIntra()>=(q.sumaIntra()) && p.sumaInter()>(q.sumaInter()))) ;

    }
    
    	
    public void ordenamientoFuncionIntra(SolucionGenetico S) {
    	
    	S.ordenarListaIntra(S.getSoluciones());
    }
    
    /**
     * Funcion que devuelve las fronteras del nsga de un conjunto de puntos
     * @param Frontera
     */
    public ArrayList<ArrayList<Solucion>> fastNonDominatedSorting(ArrayList<Solucion> poblation) {

        ArrayList<ArrayList<Integer>> fronts=new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> F1=new ArrayList<Integer>();
        
        ArrayList <ArrayList<Integer>> S=new ArrayList <ArrayList<Integer>>();
        ArrayList <Integer> n=new ArrayList <Integer>();
        

        int indexp=0;
        for(Solucion  p: poblation ) {
        	ArrayList<Integer> Sp=new ArrayList <Integer>();
        	int np=0;
        	int indexq=0;
            for(Solucion q : poblation ) {
            	
                if(dominar(p,q)) {
                	Sp.add(indexq);
                }else {
                	np++;
                }
                indexq++;
            }
            n.add(indexp);
            S.add(Sp);
            if(np==0) {
            	F1.add(indexp);
            }
            indexp++;
        }
        fronts.add(F1);
        
        ArrayList<Integer>Fi=F1;
       
        while(!Fi.isEmpty()) {
        	ArrayList<Integer> F=new ArrayList<Integer>();
        	
        	Set<Integer> s=new HashSet<Integer>();
        	for(Integer a:Fi) {
        		s.addAll(S.get(a));
        	}
        	ArrayList<Integer> Sp=new ArrayList<>(s);
        	
        	for(Integer q:Sp) {
        		int nq=n.get(q)-1;
        		n.set(q,nq);
        		if(nq==0){
        			F.add(q);
        		}
        	}

        	fronts.add(F);
        	Fi=F;
        }
        ArrayList<ArrayList<Solucion>> Fronts=new ArrayList<ArrayList<Solucion>>();
        
        for(ArrayList<Integer> f: fronts ) {
        	ArrayList <Solucion> front=new ArrayList <Solucion>();
        	for(Integer  p: f ) {
            	front.add(poblation.get(p));
            }
        	Fronts.add(front);
        }
        
        return Fronts; 
    }
    
    /**
     * Funcion que devuelve las fronteras del nsga de un conjunto de puntos
     * @param Frontera
     */
    public ArrayList<ArrayList<Encode>> fastNonDominatedSorting(SolucionGenetico poblation) {

        ArrayList<ArrayList<Integer>> fronts=new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> F1=new ArrayList<Integer>();
        
        ArrayList <ArrayList<Integer>> S=new ArrayList <ArrayList<Integer>>();
        ArrayList <Integer> n=new ArrayList <Integer>();
        

        int indexp=0;
        for(Encode  p: poblation.getSoluciones() ) {
        	ArrayList<Integer> Sp=new ArrayList <Integer>();
        	int np=0;
        	int indexq=0;
            for(Encode q : poblation.getSoluciones() ) {
            	
                if(dominar(p,q)) {
                	Sp.add(indexq);
                }else {
                	np++;
                }
                indexq++;
            }
            n.add(indexp);
            S.add(Sp);
            if(np==0) {
            	F1.add(indexp);
            }
            indexp++;
        }
        fronts.add(F1);
        
        ArrayList<Integer>Fi=F1;
       
        while(!Fi.isEmpty()) {
        	ArrayList<Integer> F=new ArrayList<Integer>();
        	
        	Set<Integer> s=new HashSet<Integer>();
        	for(Integer a:Fi) {
        		s.addAll(S.get(a));
        	}
        	ArrayList<Integer> Sp=new ArrayList<>(s);
        	
        	for(Integer q:Sp) {
        		int nq=n.get(q)-1;
        		n.set(q,nq);
        		if(nq==0){
        			F.add(q);
        		}
        	}

        	fronts.add(F);
        	Fi=F;
        }
        ArrayList<ArrayList<Encode>> Fronts=new ArrayList<ArrayList<Encode>>();
        
        for(ArrayList<Integer> f: fronts ) {
        	ArrayList <Encode> front=new ArrayList <Encode>();
        	for(Integer  p: f ) {
            	front.add(poblation.getSoluciones().get(p));
            }
        	Fronts.add(front);
        }
        
        return Fronts; 
    }
    
    public ArrayList<Encode> pareto(SolucionGenetico poblation){
    	
    	ArrayList<Encode> P = poblation.getSoluciones();
    	ArrayList<ArrayList<Encode>> fronts = new ArrayList<ArrayList<Encode>>();
    	ArrayList<ArrayList<Integer>> dominado=new ArrayList<ArrayList<Integer>>();
    		
    	for(Encode p:P) {
    		ArrayList<Integer> dom = new ArrayList<Integer>();
    		for(Encode q:P) {
    			if(!p.equals(q)) {
    				if(dominar(q,p)) {
    					dom.add(1);
    				}
    			}
    		}
    		dominado.add(dom);
    	}    
    	ArrayList<Encode> front = new ArrayList<Encode>(); 
   		for(int i = 0; i < dominado.size(); i++) {
   			ArrayList<Integer> dom = dominado.get(i);
   			if(0 == dom.size()) {
   				boolean existe = false;
   				for(int j = 0; j < front.size();j++) {
   					if(P.get(i).inter == P.get(j).inter && P.get(i).intra == P.get(j).intra) {
   						existe = true;
   						break;
   					}
   				}
   				if(!existe) {
   					front.add(P.get(i));
   				}
    			
    		}
    	}
    	 
        return front; 
    }
    
    public ArrayList<ArrayList<Encode>> clasificacionFronteras(SolucionGenetico poblation){
    	
    	ArrayList<Encode> P = poblation.getSoluciones();
    	ArrayList<ArrayList<Encode>> fronts = new ArrayList<ArrayList<Encode>>();
    	ArrayList<ArrayList<Integer>> dominado=new ArrayList<ArrayList<Integer>>();
    		
    	for(Encode p:P) {
    		ArrayList<Integer> dom = new ArrayList<Integer>();
    		for(Encode q:P) {
    			if(!p.equals(q)) {
    				if(dominar(q,p)) {
    					dom.add(1);
    				}
    			}
    		}
    		dominado.add(dom);
    	}    
    	
    	int aux=0, individuos = 0;
    	while(individuos < P.size()) {
    		ArrayList<Encode> front = new ArrayList<Encode>(); 
    		for(int i = 0; i < dominado.size(); i++) {
    			ArrayList<Integer> dom = dominado.get(i);
    			if(aux == dom.size()) {
    				front.add(P.get(i));
    				individuos++;
    			}
    		}
    		aux++;
    		if(front.size()>0) {
    			fronts.add(front);
    		}
 
    	}
        
        return fronts; 
    }
    
    public ArrayList<Encode> SeleccionElitistaMO(SolucionGenetico P, float porcentaje){
    	ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		boolean flag = false;
		cantidad = (int) (P.getSoluciones().size()*(div));
		
		for(ArrayList<Encode> Front: clasificacionFronteras(P)) {
			for(Encode s: Front) {
				padres.add(s);
				if(padres.size()==cantidad) {
					flag = true;
					break;
				}
			}
			if(flag) {
				break;
			}
		}
		
		
		
		return padres;
    }
    public ArrayList<Encode> SeleccionRandomMO(SolucionGenetico P, float porcentaje){
    	ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		cantidad = (int) (P.getSoluciones().size()*(div));
		
		while(cantidad>0) {
			int aleatorio = (int)(Math.random()* (P.getSoluciones().size()-1));
			if(!padres.contains(P.getSoluciones().get(aleatorio))) {
				padres.add(P.getSoluciones().get(aleatorio));
				cantidad--;
			}
		}

		return padres;
    }
    public ArrayList<Encode> SeleccionTorneoMO(SolucionGenetico P, float porcentaje){
    	ArrayList<Encode> padres = new ArrayList<Encode>();
    	ArrayList<Encode> candidatos = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		cantidad = (int) (P.getSoluciones().size()*(div));
		int indice1, indice2;
		
		for(ArrayList<Encode> Front: clasificacionFronteras(P)) {
			for(Encode s: Front) {
				candidatos.add(s);	
			}
		}
		
		while(cantidad>0) {	
			
			indice1 = (int)(Math.random()* (P.getSoluciones().size()-1));
			indice2 = (int)(Math.random()* (P.getSoluciones().size()-1));
			
			
			if(candidatos.indexOf(P.getSoluciones().get(indice1))<=candidatos.indexOf(P.getSoluciones().get(indice2))){
				if(!padres.contains(P.getSoluciones().get(indice1))) {
					padres.add(P.getSoluciones().get(indice1));
					cantidad--;
				}
			}else {
				if(!padres.contains(P.getSoluciones().get(indice2))) {
					padres.add(P.getSoluciones().get(indice2));
					cantidad--;
				}
			}
				
		}
		
		
		return padres;
    }
    
    public void ordenarListaIntra(ArrayList<Encode> lista) {
		
		for(int i = (lista.size()-1); i > 0; i--) {
			for(int j = 0 ; j<i ; j++) {
				if(lista.get(i).sumaIntra()>lista.get(j).sumaIntra()) {
					Encode aux = lista.get(i);
					lista.set(i, lista.get(j));
					lista.set(j, aux);
				}
			}
		}
	}
	
	public void ordenarListaInter(ArrayList<Encode> lista) {
		
		for(int i = (lista.size()-1); i > 0; i--) {
			for(int j = 0 ; j<i ; j++) {
				if(lista.get(i).sumaInter()>lista.get(j).sumaInter()) {
					Encode aux = lista.get(i);
					lista.set(i, lista.get(j));
					lista.set(j, aux);
				}
			}
		}
	}
    
    public ArrayList<Float> crowdingDistance(ArrayList<Encode> front) {
    	ArrayList<Float> distance = new ArrayList<Float>();
    	for(int i = 0; i<front.size(); i++) {
    		distance.add(0f);
    	}
    	for(int i = 1 ; i <=2; i++) {
    		ArrayList<Encode> auxFront = new ArrayList<Encode>();
    		float crowDis = 0;
    		auxFront = (ArrayList<Encode>) front.clone();
    		if(i == 1) {
    			ordenarListaIntra(auxFront);
    			for(int j = 0 ; j< auxFront.size(); j++) {
    				int index = front.indexOf(auxFront.get(j));
        			if(j==0 || j == auxFront.size()-1) {
        				crowDis = 4294967;
        				distance.set(index,crowDis);
        				crowDis = 0;
        			}else {
        				crowDis = auxFront.get(j+1).sumaIntra() - auxFront.get(j-1).sumaIntra();
        				crowDis = crowDis/ (auxFront.get(auxFront.size()-1).sumaIntra() - auxFront.get(0).sumaIntra());
        				crowDis = Math.abs(crowDis);
        				distance.set(index,crowDis);
        				crowDis = 0;
        			}
    			}
    		}else {
    			ordenarListaInter(auxFront);
    			for(int j = 0 ; j< auxFront.size(); j++) {
    				int index = front.indexOf(auxFront.get(j));
        			if(j==0 || j == auxFront.size()-1) {
        				crowDis = 4294967;
        				crowDis = distance.get(index) + crowDis;
        				distance.set(index,crowDis);
        				crowDis = 0;
        			}else {
        				crowDis = auxFront.get(j+1).sumaInter() - auxFront.get(j-1).sumaInter();
        				crowDis = crowDis/ (auxFront.get(auxFront.size()-1).sumaInter() - auxFront.get(0).sumaInter());
        				crowDis = Math.abs(crowDis);
        				crowDis = distance.get(index) + crowDis;
        				distance.set(index,crowDis);
        				crowDis = 0;
        			}
    			}
    		}
    		
    	}
    	
    	return distance;
    }
    
    
    public void ordenarPorDistancia(ArrayList<Encode> front) {
    	ArrayList<Float> distancia = crowdingDistance(front);
    	
    	for(int i = (distancia.size()-1); i > 0; i--) {
			for(int j = 0 ; j<i ; j++) {
				if(distancia.get(i)>distancia.get(j)) {
					Encode aux = front.get(i);
					float dist = distancia.get(i);
					front.set(i, front.get(j));
					distancia.set(i, distancia.get(j));
					front.set(j, aux);
					distancia.set(i, dist);
				}
			}
		}
    	
    }
    
    public ArrayList<Encode> nAleatorios(ArrayList<Encode> soluciones, int n){
    	ArrayList<Encode> aleatorios = new ArrayList<Encode>();
    	
    	for(int i = 0; i < n; i++) {
    		int index = (int) (Math.random()*soluciones.size()-1);
    		aleatorios.add(soluciones.get(index));
    	}
    	
    	return aleatorios;
    }
    
    public ArrayList<Encode> MOGA(int ciudad, listaRestaurantes listaprueba, float porcentajePadres, int seleccion, float porcentajeMutacion){  	
    	int numPoblacion = 1000;
     	int iteraciones = 100;
     	SolucionGenetico actual = new SolucionGenetico( listaprueba, ciudad, (float)0.5, presupuesto, kpaquetes, numPoblacion);
     	ArrayList<Encode> noDominadas = pareto(actual);
     	
     	while(iteraciones > 0) {
     		actual.actualizarSolucionesMOGA(porcentajePadres, seleccion, porcentajeMutacion, iteraciones);
     		
     		if(noDominadas.size()>0) {
     			if(noDominadas.size()<=10) {
     				actual.borrarNPeoresSoluciones(noDominadas.size());
     				actual.getSoluciones().addAll(noDominadas);
     			}else {
     				actual.borrarNPeoresSoluciones(10);
     				actual.getSoluciones().addAll(nAleatorios(noDominadas, 10));
     			}
     			
     		}
     		
     		noDominadas = pareto(actual);
     		iteraciones--;
     	}
    	 
    	 return noDominadas;
    }
    
    public ArrayList<Encode> NSGAII(int ciudad, listaRestaurantes listaprueba, float porcentajePadres, int seleccion, float porcentajeMutacion){
    	ArrayList<ArrayList<Encode>> fronteras = new ArrayList<ArrayList<Encode>>();
    	int numPoblacion = 700;
     	int iteraciones = 100;
 		
     	SolucionGenetico actual = new SolucionGenetico( listaprueba, ciudad, (float)0.5, presupuesto, kpaquetes, numPoblacion);
     	
     	while(iteraciones > 0) {
     		ArrayList<Encode> soluciones = new ArrayList<Encode>();
     		int i = 0;
     		//actual.actualizarSolucionesNSGA2(SeleccionElitistaMO(actual, porcentajePadres), porcentajeMutacion);
     		//actual.actualizarSolucionesNSGA2(SeleccionTorneoMO(actual, porcentajePadres), porcentajeMutacion);
     		actual.actualizarSolucionesNSGA2(SeleccionRandomMO(actual, porcentajePadres), porcentajeMutacion);
     		
     		fronteras = clasificacionFronteras(actual);
     		while(soluciones.size()+fronteras.get(i).size() <= numPoblacion ) {
     			soluciones.addAll(fronteras.get(i));
     			i++;
     		}
     		/*
     		i = 0;
     		fronteras.add(actual.getSoluciones());
     		*/
     		if(soluciones.size()< numPoblacion ) {
     			ordenarPorDistancia(fronteras.get(i));
     			int j = 0;
     			while(soluciones.size()< numPoblacion ) {
     				soluciones.add(fronteras.get(i).get(j));
     				j++;
     			}
     		}
     		
     		actual.setSoluciones(soluciones);
     		iteraciones--;
     	}
    	 
     	ArrayList<Encode> sol = new ArrayList<Encode>();
     	int cont = 0;
     	for(ArrayList<Encode> front: fronteras) {
     		sol.addAll(front);
     		cont++;
     		if(cont == 3) {
     			break;
     		}
     	}
     	//return sol;
    	 //return actual.getSoluciones();
    	 return fronteras.get(0);
    }
    
     public void generateNeighborhood(Solucion nodo,ArrayList<Solucion> allNeighborhood,listaRestaurantes lista) {
    	//System.out.println("----------------------------");

        
        for(int j=0;j<(iteracionesVecinos);j++){

            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            vecino.update(nodo);
            vecino.cambiarRestauranteAzar(lista);
            //vecino.datosFitness();
            //System.out.println(","+vecino.funcionFitness());
            allNeighborhood.add(vecino);
        }
        
        for(int j=0;j<(iteracionesVecinos/2);j++){

            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            vecino.update(nodo);
            vecino.cambiarRestauranteExhaustivo(lista);
            //vecino.datosFitness();
            //System.out.println(","+vecino.funcionFitness());
            allNeighborhood.add(vecino);
        }
        for(int j=0;j<(iteracionesVecinos/2);j++){

            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            vecino.update(nodo);
            vecino.cambiarPaqueteAzar(lista);
            //vecino.datosFitness();
            //System.out.println(","+vecino.funcionFitness());
            allNeighborhood.add(vecino);
        }
        for(int j=0;j<(iteracionesVecinos/2);j++){

            Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
            vecino.update(nodo);
            vecino.cambiarPaqueteExhaustivo(lista);
            //vecino.datosFitness();
            //System.out.println(","+vecino.funcionFitness());
            allNeighborhood.add(vecino);
        }
	
    }
     public void generateNeighborhood(Solucion nodo,ArrayList<Solucion> allNeighborhood,listaRestaurantes lista, int mov) {
     	//System.out.println("----------------------------");

         if(mov==1){
	         for(int j=0;j<(iteracionesVecinos);j++){
	
	             Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
	             vecino.update(nodo);
	             vecino.cambiarRestauranteAzar(lista);
	             //vecino.datosFitness();
	             //System.out.println(","+vecino.funcionFitness());
	             allNeighborhood.add(vecino);
	         }
         }
         if(mov==2){
	         for(int j=0;j<(iteracionesVecinos);j++){
	
	             Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
	             vecino.update(nodo);
	             vecino.cambiarRestauranteExhaustivo(lista);
	             //vecino.datosFitness();
	             //System.out.println(","+vecino.funcionFitness());
	             allNeighborhood.add(vecino);
	         }	
         }
         if(mov==3){
	         for(int j=0;j<(iteracionesVecinos);j++){
	
	             Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
	             vecino.update(nodo);
	             vecino.cambiarPaqueteAzar(lista);
	             //vecino.datosFitness();
	             //System.out.println(","+vecino.funcionFitness());
	             allNeighborhood.add(vecino);
	         }
         }
         if(mov==4){
	         for(int j=0;j<(iteracionesVecinos);j++){
	
	             Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
	             vecino.update(nodo);
	             vecino.cambiarPaqueteExhaustivo(lista);
	             //vecino.datosFitness();
	             //System.out.println(","+vecino.funcionFitness());
	             allNeighborhood.add(vecino);
	         }
         }
 	
     }

     public void printFront(ArrayList<Solucion> front) {
    	
    	System.out.println("Intra,Inter");
    	for(Solucion nodo: front) {
	    	
    		nodo.datosFitness();
    		System.out.println("");
	    }
    	System.out.println("----------------------------");
    }
    
}
    

