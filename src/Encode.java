import java.util.ArrayList;

public class Encode extends Solucion{
	
	ArrayList<ArrayList<Integer>> gen;
	listaRestaurantes restaurantes =new listaRestaurantes(); // todos los restaurantes disponibles
	int ciudad;
	int presupuesto;
	int k; //cantidad de paquetes
	float alfa; 
    ArrayList<ArrayList<Integer>>  tiposCocina;
    float fitness;
    ArrayList<Integer> gasto;
    ArrayList<Float> similitud;
    ArrayList<ArrayList<Float>> diversidad;
    
    //considerar tipos cocina ( camplementaridad 
    
    
    
    
    /**    
    

	public Encode(int kSoluciones, listaRestaurantes lista, float alfa, int ciudad, int presupuesto, int[][] gen,
			ArrayList<Restaurante> restaurantes) {
		super(kSoluciones, lista, alfa, ciudad, presupuesto);
		this.gen = gen;
		this.restaurantes = restaurantes;
		i = super.getPaquetes2().size();
		j = restaurantes.size();
	}
	
	

	public Encode(int kSoluciones, listaRestaurantes lista, float alfa, int ciudad, int presupuesto) {
		super(kSoluciones, lista, alfa, ciudad, presupuesto);
		restaurantes = lista.getRestaurantes();
		i = super.getPaquetes2().size();
		j = restaurantes.size();
		gen = new int [i][j];
		generarGen();
	}



	public Encode(int ciudad, float alfa, int presupuesto) {
		super(ciudad, alfa, presupuesto);
		restaurantes =new ArrayList<Restaurante>();
	}




	public Encode(int ciudad, float alfa, int presupuesto, ArrayList<Paquete> soluciones, ArrayList<Restaurante> restaurant ) {
		super(ciudad, alfa, presupuesto, soluciones);
		gen = new int [super.paquetes.size()][restaurantes.size()];
		generarGen();
	}
	
	
	
	public Encode(int ciudad, float alfa, int presupuesto, ArrayList<Restaurante> restaurantes) {
		super(ciudad, alfa, presupuesto);
		this.restaurantes = restaurantes;
	}



	this.i = super.paquetes.size();
		this.j = restaurantes.size();
		gen = new int [this.i][this.j];
		for(int i = 0; i <super.paquetes.size(); i++) {
			for(int j = 0; j < restaurantes.size(); j++) {
				for(int k = 0; k < super.paquetes.get(i).tamanoSolucion(); k++) {
					if(super.paquetes.get(i).getRestaurantes().get(k).getIdresturant() == restaurantes.get(j).getIdresturant()) {
						gen[i][j] = 1;
						break;
					}else {
						gen[i][j] = 0;
					}
				}
			}
		}



**/
    
    

    
    public Encode(listaRestaurantes restaurantes, int ciudad, int presupuesto, int k, float alfa) {
		super();
		this.restaurantes = new listaRestaurantes(restaurantes.getRestaurantes());
		this.ciudad = ciudad;
		this.presupuesto = presupuesto;
		this.k = k;
		this.alfa = alfa;
		tiposCocina = new ArrayList<ArrayList<Integer>>();
		gen = new ArrayList<ArrayList<Integer>>();
		gasto =  new ArrayList<Integer>();
		similitud = new ArrayList<Float>();
		diversidad = new ArrayList<ArrayList<Float>>();
		generarGen(k);
		
		
	}
	
	public Encode(int ciudad, int presupuesto, int k, float alfa) {
		super();
		restaurantes = new listaRestaurantes();
		this.ciudad = ciudad;
		this.presupuesto = presupuesto;
		this.k = k;
		this.alfa = alfa;

		tiposCocina = new ArrayList<ArrayList<Integer>>();
		gen = new ArrayList<ArrayList<Integer>>();
		gasto = new ArrayList<Integer>();
		similitud =  new ArrayList<Float>();
		diversidad = new ArrayList<ArrayList<Float>>();
	}

	/**
	 * Crea soluciones iniciales aleatorias
	 */
	public void generarGen(int k) {
		for(int i = 0; i < k; i++) {
			ArrayList<Integer> paquete = new ArrayList<Integer>();
			ArrayList<Integer> cocina = new ArrayList<Integer>();
			int iteracion = 0;
			int j = 0;
			float intra = 0;
			gasto.add(i, 0);
			while (gasto.get(i)<presupuesto) {
				Restaurante nuevo=new Restaurante();
				nuevo = restaurantes.getRestauranteAzar(ciudad);
				int aux = gasto.get(i);
				aux = aux + nuevo.getCosto();
				if(aux > presupuesto || iteracion>=40){
					break;
				}else if(aux <= presupuesto && !contieneCocina(cocina, nuevo.getTipo())){
					gasto.set(i, aux);
					
					paquete.add(nuevo.getIdresturant());
				
					
					cocina.addAll(nuevo.getTipo());
					j++;
				}
				iteracion++;
			
			}
			gen.add(paquete);
			tiposCocina.add(cocina);
			similitud.add(intraPaquete(paquete));
		}
		inter();
		fitness();
	}
	
	public boolean contieneCocina(ArrayList<Integer> tiposExistentes, ArrayList<Integer> cocinaComparar) {
		
		for(int i = 0 ; i < tiposExistentes.size(); i++) {
			for(int j = 0 ; j < cocinaComparar.size(); j++) {
				if(tiposExistentes.get(i) == cocinaComparar.get(j)) {
					return true;
				}
			}
		}
		return false;
	}
	

	

	/**
	 * Entrega compatibilidad en el paquete considerando el alfa
	 */
	public float intraPaquete(ArrayList<Integer> paquete) {
		
		float compatibilidad=0, intra = 0;
		
		for(int i = 0 ; i < paquete.size(); i++) {
			for(int j = i+1; j < paquete.size(); j++) {
				intra = intra + restaurantes.obtenerRestaurante(paquete.get(i)).searchCompatInstersection(paquete.get(j));
			}
		}
		
		return intra;
		
		
	}
	
	/**
	 * Entrega compatibildiad entre paquetes
	 */
	public float inter() {
		float valorDiversidad=0;
		float maxCompatibilidad=0;
		
		//System.out.println("--------------------------------------------");
		for (int i = 0; i < k; i++) {
			ArrayList<Float> diver = new ArrayList<Float>();	
			for(int num = 0 ; num < i+1; i++) {
				diver.add(num,(float) 0);
			}
			for (int j = i+1; j < k; j++) {
				maxCompatibilidad=1-maximaCompatibilidad(i, j);
				valorDiversidad=valorDiversidad+(maxCompatibilidad);
				diver.add(j, maxCompatibilidad);
			}
			diversidad.add(i,diver);
		}
		
		return valorDiversidad;
	}
	
	public float sumaIntra() {
		float intra = 0;
		for(int i = 0 ; i < similitud.size(); i++) {
			intra = intra + similitud.get(i);
		}
		return intra;
	}
	
	public float sumaInter() {
		float inter = 0;
		
		for(int i = 0; i < k; i++) {
			for(int j = i+1; j < k ; j++ ) {
				inter = inter + diversidad.get(i).get(j);
			}
		}
		return inter;
	}
	
	public float maximaCompatibilidad(int paqueteUno, int paqueteDos) {
		float compatibilidadMaxima=0;
		
		for (int i = 0; i < gen.get(paqueteUno).size(); i++) {
			for (int j = 0; j < gen.get(paqueteDos).size(); j++) {
				if(restaurantes.obtenerRestaurante(gen.get(paqueteUno).get(i)).searchCompatJaccard(gen.get(paqueteDos).get(j))> compatibilidadMaxima) {
					compatibilidadMaxima=restaurantes.obtenerRestaurante(gen.get(paqueteUno).get(i)).searchCompatJaccard(gen.get(paqueteDos).get(j));
				}						
			}	
		}
		return compatibilidadMaxima;
	}
	
	/**
	 * @return valor de la funcion objetivo de la solucion
	 */
	public float fitness() {
		fitness = sumaIntra()*alfa +sumaInter()*(1-alfa);
		return fitness;
	}
	
	public void arreglarInter(int indicePaquete) {
		
		for(int i = indicePaquete+1; i < k; i++) {
			diversidad.get(indicePaquete).set(i,(1- maximaCompatibilidad(indicePaquete,i)));
		}
		
		for(int j = 0; j < indicePaquete; j++) {
			diversidad.get(j).set(indicePaquete,(1- maximaCompatibilidad(indicePaquete,j)));
		}
		
	}
	
	
	/**
	 * @param primer padre del cruce
	 * @return hijo generado en el cruce
	 */
	/**
	 * @param padre1
	 * @return
	 */
	public ArrayList<Encode> cruce1(Encode padre1) {
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		Encode hijo1 = new Encode(ciudad, presupuesto, k , alfa);
		Encode hijo2 = new Encode(ciudad, presupuesto, k , alfa);
		int posicion = (int)(Math.random()*(k-1));
		
		hijo1.setGen((ArrayList<ArrayList<Integer>>)padre1.getGen().clone());
		hijo1.setRestaurantes(padre1.getRestaurantes());
		
		hijo2.setGen(getGen());
		hijo2.setRestaurantes(restaurantes);
		
		hijo1.getGen2().set(posicion, getGen().get(posicion));
		hijo2.getGen2().set(posicion, padre1.getGen().get(posicion));
		
		hijo1.getGasto().set(posicion, getGasto().get(posicion));
		hijo2.getGasto().set(posicion, padre1.getGasto().get(posicion));
		
		hijo1.getTiposCocina().set(posicion, getTiposCocina().get(posicion));
		hijo2.getTiposCocina().set(posicion, padre1.getTiposCocina().get(posicion));
		
		hijo1.getSimilitud().set(posicion, hijo1.intraPaquete(hijo1.getGen2().get(posicion)));
		hijo2.getSimilitud().set(posicion, hijo2.intraPaquete(hijo2.getGen2().get(posicion)));
		
		hijo1.arreglarInter(posicion);
		hijo2.arreglarInter(posicion);
		
		hijos.add(hijo1);
		hijos.add(hijo2);
		
		return hijos;
		
	}
	
	/*
	public Encode cruce2(Encode padre1, int posicion) {
		Encode hijo = new Encode(ciudad, presupuesto, k , alfa);
		
		hijo.setGen(padre1.getGen());
		hijo.setRestaurantes(padre1.getRestaurantes());
		
		
		for(int j = posicion; j < k; j++) {
			gasto[j] = 0;
			
			
			for(int i = 0 ; i < restaurantes.getTamanoLista(); i++) {
				if(gen[j][i] == 1) {
					gasto[j] = gasto[j] + restaurantes.getRestaurante(i).getCosto();
				}
				hijo.gen[j][i] = gen[j][i];
			}
		}
		
		return hijo;
		
	}
	*/
	
	public ArrayList<Encode> cruceVariable1(Encode padre1, int puntosCorte) {
		
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		Encode hijo1 = new Encode(ciudad, presupuesto, k , alfa);
		Encode hijo2 = new Encode(ciudad, presupuesto, k , alfa);
		int posicionAnterior = 0 , posicion;
		
		hijo1.setGen(padre1.getGen());
		hijo1.setRestaurantes(padre1.getRestaurantes());
		
		hijo2.setGen(getGen());
		hijo2.setRestaurantes(restaurantes);
		
		for(int puntos = 0 ; puntos < puntosCorte; puntos++) {
			posicion = (int) (Math.random()* (k-1));
			posicion = posicion + posicionAnterior;
			if((posicion)>= k) {
				posicion = posicion - k +1 ;
			}
			
			hijo1.getGen2().set(posicion, getGen().get(posicion));
			hijo2.getGen2().set(posicion, padre1.getGen().get(posicion));
			
			hijo1.getGasto().set(posicion, getGasto().get(posicion));
			hijo2.getGasto().set(posicion, padre1.getGasto().get(posicion));
			
			hijo1.getTiposCocina().set(posicion, getTiposCocina().get(posicion));
			hijo2.getTiposCocina().set(posicion, padre1.getTiposCocina().get(posicion));
			
			hijo1.getSimilitud().set(posicion, hijo1.intraPaquete(hijo1.getGen2().get(posicion)));
			hijo2.getSimilitud().set(posicion, hijo2.intraPaquete(hijo2.getGen2().get(posicion)));
			
			hijo1.arreglarInter(posicion);
			hijo2.arreglarInter(posicion);
			
			
			posicionAnterior = posicion;
		}
		
		hijos.add(hijo1);
		hijos.add(hijo2);
		
		return hijos;
	}
	
	//no funciona
	/*
	public Encode cruceVariable2(Encode padre1, int puntosCorte) {
		Encode hijo = new Encode(ciudad, presupuesto, k , alfa);
		int posicionAnterior = 0 , posicion;
		
		hijo.setGen(padre1.getGen());
		hijo.setRestaurantes(padre1.getRestaurantes());
		
		for(int puntos = 0 ; puntos < puntosCorte; puntos++) {
			posicion = (int) (Math.random()* (k-1));
			posicion = posicion + posicionAnterior;
			if((posicion)>= k) {
				posicion = posicion - k +1 ;
			}
			
			for(int j = posicion; j < k; j++) {
				for(int i = 0 ; i < restaurantes.getTamanoLista(); i++) {
					hijo.gen[j][i] = gen[j][i];
				}
			}
		}
		
		
		return hijo;
	}
	*/
	
	/*
	public Encode mutacion1() {
		int fila = k -1 , columna = restaurantes.getTamanoLista()-1, cambio = 0, aux;
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		Encode mutado = new Encode(ciudad, presupuesto, k , alfa);
		mutado.setGen(getGen());
		mutado.setRestaurantes(getRestaurantes());
		
		fila = (int) (Math.random()*fila);
		
		columna = (int) (Math.random()*columna);
		
		if(mutado.getGen2().get(fila).contains(restaurantes.getRestaurante(columna))) {
			gasto.set(fila, (gasto.get(fila)-restaurantes.getRestaurante(columna).getCosto()));
			
		}
		
		if(mutado.getGen2()[fila][columna]==0) {	
			mutado.setGenIndice(fila,columna,1);
			gasto[fila] = gasto[fila] + restaurantes.getRestaurante(columna).getCosto();
			do{
				aux = llenoAleatorioEnPaquete(fila);
				mutado.setGenIndice(fila,aux,0);
				gasto[fila] = gasto[fila]- restaurantes.getRestaurante(aux).getCosto();
				
			}while(gasto[fila]> presupuesto);
			
			
		}else if(mutado.getGen2()[fila][columna]==1){ //puede entrar en un while infinito upsis
			gasto[fila] = gasto[fila] - restaurantes.getRestaurante(columna).getCosto();
			int costo = restaurantes.getRestaurante(columna).getCosto();
			int iter = 0;
			mutado.setGenIndice(fila,columna,0);
			do{
				iter++;
				aux = vacioAleatorioEnPaquete(fila);
				if(restaurantes.getRestaurante(aux).getCosto() <= costo ) {
					mutado.setGenIndice(fila,aux,1);
					gasto[fila] = gasto[fila]+ restaurantes.getRestaurante(aux).getCosto();
					costo = costo - restaurantes.getRestaurante(aux).getCosto();
				}
			}while((costo > 0) || iter >= 30);
			
			
		}
		return mutado; 
	}
	*/
	
	/*
	public Encode mutacion3() {
		
		ArrayList<Paquete> nuevos = super.getPaquetes2();
		int fila = super.getPaquetes().size() -1 , columna = restaurantes.size()-1, cambio = 0, aux;
		int filas = super.getPaquetes().size() -1;
		Paquete paqueteMutado = new Paquete(presupuesto, ciudad);
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Integer> usadas = new ArrayList<Integer>();
		
		Encode mutado = new Encode(ciudad, alfa, presupuesto);
		mutado.setGen(getGen());
		mutado.setRestaurantes(getRestaurantes());
		mutado.updatePaquete(super.getPaquetes2());
		
		
		filas = ((int) (Math.random()*filas))+1;
		
		
		
		while(filas>0) {
			fila = super.getPaquetes().size() -1;
			columna = restaurantes.size()-1;
			cambio = 0;
			do {
				fila = (int) (Math.random()*fila);
			}while (usadas.contains(fila));
			usadas.add(fila);
			
			paqueteMutado.setRestaurantes(nuevos.get(fila).getRestaurantes2());
			paqueteMutado.actualizarCostos();
			paqueteMutado.actualizarTipos();
			nuevos.remove(fila);
			columna = (int) (Math.random()*columna);
		
			if(mutado.getGen2()[fila][columna]==0) {	
				while(cambio==0) {
					aux = paqueteMutado.eliminarRestauranteAzarId();
					mutado.setGenIndice(fila,buscarIndiceRestaurante(aux),0);
					cambio = paqueteMutado.agregarRestaurante(restaurantes.get(columna));
				}
				mutado.setGenIndice(fila,columna,1);
			}else if(mutado.getGen2()[fila][columna]==1){ //aqui puede entrar en un while infinito upsis
				paqueteMutado.eliminarRestauranteId(restaurantes.get(columna).getIdresturant());
				
				indices = paqueteMutado.agregarSolucion(restaurantes);
				if(indices != null) {
					for(int i = 0; i < indices.size(); i++) {
					mutado.setGenIndice(fila,indices.get(i),1);
					}
				}
		
				mutado.setGenIndice(fila,columna,0);
			}
			nuevos.add(fila, paqueteMutado);
			filas--;
			paqueteMutado = new Paquete(presupuesto, ciudad);
		}
		
		mutado.updatePaquete(nuevos);
		
		return mutado; 
	}
	
	
	public Encode mutacion2() {
		ArrayList<Paquete> nuevos = super.getPaquetes2();
		int fila = super.getPaquetes().size() -1;
		Paquete paqueteMutado = new Paquete(presupuesto, ciudad);
		listaRestaurantes r = new listaRestaurantes(restaurantes);
		
		fila = (int) (Math.random()*fila);
		

		Encode mutado = new Encode(ciudad, alfa, presupuesto);
		
		mutado.setGen(getGen());
		mutado.setRestaurantes(getRestaurantes());
		
		
		do{
			paqueteMutado.generarSolucion(r);
		}while(nuevos.get(fila).equals(paqueteMutado));
		
		nuevos.remove(fila);
		nuevos.add(fila, paqueteMutado);
		mutado.updatePaquete(nuevos);
		
		for(int i = 0; i < restaurantes.size(); i++) {
			for(int k = 0; k < paqueteMutado.tamanoSolucion(); k++) {
				if(paqueteMutado.getRestaurantes().get(k).getIdresturant() == restaurantes.get(i).getIdresturant()) {
					gen[fila][i] = 1;
					break;
				}else {
					gen[fila][i] = 0;
				}
			}
		}
		
		return mutado;
	}
	*/
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
		public Encode mutacion() {
		ArrayList<Paquete> nuevos = super.getPaquetes2();
		
		int fila = super.getPaquetes().size(), columna = restaurantes.size(), cambio = 0, aux;
		Paquete paqueteMutado = new Paquete(presupuesto, ciudad);
		Encode mutado = new Encode(ciudad, alfa, presupuesto);
		mutado.setGen(getGen());
		mutado.setRestaurantes(getRestaurantes());
		mutado.updatePaquete(super.getPaquetes2());
		
		fila = (int) (Math.random()*fila);
		if(fila == super.getPaquetes2().size()) {
			fila--;
		}
		
		paqueteMutado.setRestaurantes(nuevos.get(fila).getRestaurantes2());
		paqueteMutado.actualizarCostos();
		paqueteMutado.actualizarTipos();
		nuevos.remove(fila);
		
		
		columna = (int) (Math.random()*columna);
		if(columna== restaurantes.size()) {
			columna--;
		}
		
		if(mutado.getGen()[fila][columna]==0) {	
			while(cambio==0) {
				aux = paqueteMutado.eliminarRestauranteAzarId();
				mutado.setGenIndice(fila,buscarIndiceRestaurante(aux),0);
				cambio = paqueteMutado.agregarRestaurante(restaurantes.get(columna));
			}
			mutado.setGenIndice(fila,columna,1);
		}else if(mutado.getGen()[fila][columna]==1){
			paqueteMutado.eliminarRestauranteId(restaurantes.get(columna).getIdresturant());
			while(cambio==0 ) {
				aux = vacioAleatorioEnPaquete(fila);
				cambio = paqueteMutado.agregarRestaurante(restaurantes.get(aux));
				if(cambio == 1) {
					mutado.setGenIndice(fila,aux,1);
				}
			}
			mutado.setGenIndice(fila,columna,0);
		}
		
		if(fila == nuevos.size()) {
			nuevos.add( paqueteMutado);
		}else {
			nuevos.add(fila, paqueteMutado);
		}
		
		mutado.updatePaquete(nuevos);
		//actualizarPaquetes();
		return mutado; 
	}
	 */
	
	
	
	public int vacioAleatorioEnPaquete(int fila) {
		int indice;
			
		do{
			indice =(int) (Math.random()*(restaurantes.getTamanoLista()-1));
				
		}while(gen.get(fila).contains(restaurantes.getRestaurante(indice).getIdresturant()));
		
		return indice;
		
	}

	public int llenoAleatorioEnPaquete(int fila) {
		int indice = (int) (Math.random()*((gen.get(fila).size())-1));
	
		return indice;
		
	}
	
	public ArrayList<ArrayList<Integer>> getGen() {
		ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<k; i++) {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			for(int j=0; j< gen.get(i).size(); j++){
				aux.add(gen.get(i).get(j));
			}
			g.add(aux);
		}
		
		return g;
	}
	
	public ArrayList<ArrayList<Integer>> getGen2(){
		return gen;
	}

	public void setGen(ArrayList<ArrayList<Integer>> gen) {
		this.gen = gen;
	}
	
	public void setGenIndice(int fila, int columna, int valor) {
		gen.get(fila).set(columna, valor)
;	}
	
	public listaRestaurantes getRestaurantes() {
		
		return restaurantes;
	}
	
	public int getIdRestaurante(int indice) {
		return restaurantes.getRestaurante(indice).getIdresturant();
	}
	
	public int buscarIndiceRestaurante(int idRestaurante) {
		for(int i = 0; i<restaurantes.getTamanoLista();i++) {
			if(getIdRestaurante(i) == idRestaurante) {
				return i;
			}
		}
		return -1;
	}

	public void setRestaurantes(listaRestaurantes restaurantes) {
		this.restaurantes = restaurantes;
	}

	public int getCiudad() {
		return ciudad;
	}

	public void setCiudad(int ciudad) {
		this.ciudad = ciudad;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public float getAlfa() {
		return alfa;
	}

	public void setAlfa(float alfa) {
		this.alfa = alfa;
	}

	public ArrayList<ArrayList<Integer>> getTiposCocina() {
		return tiposCocina;
	}

	public void setTiposCocina(ArrayList<ArrayList<Integer>> tiposCocina) {
		this.tiposCocina = tiposCocina;
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}

	public ArrayList<Integer> getGasto() {
		return gasto;
	}

	public void setGasto(ArrayList<Integer> gasto) {
		this.gasto = gasto;
	}

	public ArrayList<Float> getSimilitud() {
		return similitud;
	}

	public void setSimilitud(ArrayList<Float> similitud) {
		this.similitud = similitud;
	}

	public ArrayList<ArrayList<Float>> getDiversidad() {
		return diversidad;
	}

	public void setDiversidad(ArrayList<ArrayList<Float>> diversidad) {
		this.diversidad = diversidad;
	}
	
	


}
