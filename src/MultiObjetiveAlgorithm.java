import java.util.ArrayList;

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
    

