import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class SolucionGenetico {
	ArrayList<Encode> soluciones = new ArrayList<Encode>();
	ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
	listaRestaurantes restaurants;
	int idCiudad;
	float alfa;
	int presupuesto;
	int kPaquetes;

	public SolucionGenetico(int k,int idCiudad, float alfa, int presupuesto) {
		super();
		kPaquetes = k; 
		this.idCiudad = idCiudad;
		this.alfa = alfa;
		this.presupuesto = presupuesto;
	}
	

	public SolucionGenetico(ArrayList<Restaurante> restaurantes, int idCiudad, float alfa, int presupuesto, int k) {
		super();
		kPaquetes = k;
		this.restaurantes = restaurantes;
		this.idCiudad = idCiudad;
		this.alfa = alfa;
		this.presupuesto = presupuesto;
	}

	public SolucionGenetico(listaRestaurantes restaurantes, int idCiudad, float alfa, int presupuesto, int k, int numPob) {
		super();
		this.restaurantes = restaurantes.getRestaurantes();
		restaurants = restaurantes;
		this.idCiudad = idCiudad;
		this.alfa = alfa;
		this.presupuesto = presupuesto;
		kPaquetes = k;
		generarSolucionesGen(numPob);
	}
	
	public SolucionGenetico(ArrayList<Encode> soluciones, ArrayList<Restaurante> restaurantes, int idCiudad, float alfa,
			int presupuesto, int k) {
		super();
		this.soluciones = soluciones;
		this.restaurantes = restaurantes;
		this.idCiudad = idCiudad;
		this.alfa = alfa;
		this.presupuesto = presupuesto;
		kPaquetes = k;
		
	}

	
	
	public void generarSolucionesGen(int cantidadPoblacion) {
		
		Encode item; 
		for(int i = 0; i < cantidadPoblacion; i++) {
			item = new Encode(restaurants, idCiudad, presupuesto, kPaquetes, alfa);
			if(soluciones.isEmpty()) {
				soluciones.add(item);
			}else if(soluciones.contains(item)) {
				i--;
			}else {
				soluciones.add(item);
			}
		}
	}
	
	public ArrayList<Encode> seleccionRandom(float porcentaje){
		ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad =(int) (soluciones.size()*porcentaje);
		
		while(cantidad > 0) {
			int indice =(int) (Math.random()*(soluciones.size()-1));
			if(!padres.contains(soluciones.get(indice))) {
				padres.add(soluciones.get(indice));
				cantidad--;
			}
		}
		
		return padres;
		
	}
	
	
	public ArrayList<Encode> seleccionElitista(float porcentaje) {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		ArrayList<Encode> aux = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		aux = (ArrayList<Encode>) soluciones.clone();
		
		cantidad = (int) (soluciones.size()*(div));
		ordenarLista(aux);
		
		for(int i = (0); i < cantidad ; i++) {
			padres.add(i,aux.get(i));
		}
		
		return padres;
	}
	
	/**
	  public ArrayList<Encode> seleccionElitista(float porcentaje) {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		ArrayList<Encode> aux = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		Encode best = new Encode(idCiudad, alfa, presupuesto);
		aux = (ArrayList<Encode>) soluciones.clone();
		
		cantidad = (int) (soluciones.size()*(div));
		
		while(cantidad != 0) {
			best = aux.get(0);
			for(int i = 1 ; i < aux.size(); i++ ) {
				if(best.fitness()<aux.get(i).fitness()) {
					best = aux.get(i);
				}
			}
			padres.add(best);
			aux.remove(best);
			cantidad--;
		}
		
		return padres;
	}
	 * @throws FileNotFoundException 
	
	 */
	
	
	public ArrayList<Encode> seleccionRuleta(float porcentajeASeleccionar,int iter) throws FileNotFoundException{
		ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad =(int) (soluciones.size()*porcentajeASeleccionar);
		float suma = fitnessPromedio()*soluciones.size();
		float[] individuos = new float[soluciones.size()];
		
		PrintWriter s = new PrintWriter("seleccionados"+iter+".csv");
    	String cadena = "";
		
		individuos[0] = soluciones.get(0).fitness()/suma;;
		
		for(int i = 1; i < soluciones.size(); i++) {
			cadena = cadena + String.valueOf(soluciones.get(i).fitness())+ ",";
			individuos[i] = individuos[i-1]+(soluciones.get(i).fitness()/suma);
		}
		
		s.println(cadena);
		cadena="";
		while(cantidad>0) {
			float porcentaje = (float) Math.random();
			
			int i = 0;
			int j = soluciones.size()-1;
			int k;
			
			do {
				k = (i+j)/2;
				if(individuos[k]<= porcentaje) {
					i = k +1;
				}if(individuos[k]>= porcentaje) {
					j = k-1;
				}
			}while(i <= j);	
					
			if(!padres.contains(soluciones.get(k))){
					padres.add(soluciones.get(k));
					cadena = cadena + String.valueOf(soluciones.get(k).fitness())+ ",";
					
					
					cantidad--;	
			}
		}
		s.println(cadena);
		s.flush();
		s.close();
		return padres;
	}
	
	
	
	public ArrayList<Encode> seleccionTorneo(float porcentajeASeleccionar){
		ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad =(int) (soluciones.size()*porcentajeASeleccionar);
		float suma = fitnessPromedio()*soluciones.size();
		float[] individuos = new float[soluciones.size()];
		ArrayList<Encode> competidor = new ArrayList<Encode>();
		
		individuos[0] = soluciones.get(0).fitness()/suma;;
		
		for(int i = 1; i < soluciones.size(); i++) {
			individuos[i] = individuos[i-1]+(soluciones.get(i).fitness()/suma);
		}
		
		
		
		while(cantidad>0) {	
			
			for(int z = 0 ; z < 2; z ++) {
				
				float porcentaje = (float) Math.random();	
				int i = 0;
				int j = soluciones.size()-1;
				int k;
				
				do {
					k = (i+j)/2;
					if(individuos[k]<= porcentaje) {
						i = k +1;
					}if(individuos[k]>= porcentaje) {
						j = k-1;
					}
				}while(i <= j);
				competidor.add(soluciones.get(k));
			}
				
			if(competidor.get(0).fitness()>=competidor.get(1).fitness()) {
				if(!padres.contains(competidor.get(0))){
					padres.add(competidor.get(0));
					cantidad--;	
				}
			}else {
				if(!padres.contains(competidor.get(1))){
					padres.add(competidor.get(1));
					cantidad--;	
				}
			}
			competidor.clear();
				
		}
		
		
		return padres;
	}
	
	public ArrayList<Encode> seleccionTorneoRandom(float porcentajeASeleccionar){
		ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad =(int) (soluciones.size()*porcentajeASeleccionar);
		int indice1, indice2;
		
		
		
		while(cantidad>0) {	
			
			indice1 = (int)(Math.random()* (soluciones.size()-1));
			indice2 = (int)(Math.random()* (soluciones.size()-1));
			
			if(soluciones.get(indice1).fitness() >= soluciones.get(indice2).fitness()) {
				if(!padres.contains(soluciones.get(indice1))) {
					padres.add(soluciones.get(indice1));
					cantidad--;
				}
			}else {
				if(!padres.contains(soluciones.get(indice2))) {
					padres.add(soluciones.get(indice2));
					cantidad--;
				}
			}
				
		}
		
		
		return padres;
	}
	
	public ArrayList<Encode> cruzamiento(float porcentajePadres, int seleccion, int iter) throws FileNotFoundException {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		ArrayList<Pair> pares = new ArrayList<Pair>();
		boolean flag = true;
		int tamano = soluciones.size()/2;
		int indice1 = 0;
		int indice2 = 0;
		Pair par;
		
		
		
		
		if(seleccion == 1) {
			padres = seleccionRandom(porcentajePadres);
		}
		if(seleccion == 2) {
			padres = seleccionElitista(porcentajePadres);
		}
		if(seleccion == 3) {
			padres = seleccionRuleta(porcentajePadres, iter);
		}
		if(seleccion == 4) {
			padres = seleccionTorneoRandom(porcentajePadres);
		}
		
		
		
		
		
		
		indice1 = (int)(Math.random()*(padres.size()-1));
		indice2 = (int)(Math.random()*(padres.size()-1));
		
		while(indice1 == indice2) {
			indice2 = (int)(Math.random()*(padres.size()-1));
		}
		par= new Pair(indice1, indice2);
		pares.add(par);
		tamano--;
		
		while (tamano > 0) {
			flag = true;
			indice1 = (int)(Math.random()*(padres.size()-1));
			indice2 = (int)(Math.random()*(padres.size()-1));
			while(indice1 == indice2) {
				indice2 = (int)(Math.random()*(padres.size()-1));
			}
			par= new Pair(indice1, indice2);
			for(int i = 0; i < pares.size(); i++ ) {
				if(pares.get(i).contiene(par)) {
					flag = false;
					break;
				}
				if(indice1 == indice2) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				pares.add(par);
				tamano--;
			}
			
		}
		
		
		for(int i = 0; i <pares.size(); i++) {
			int posicion = 2;
			//int posicion = (int) (Math.random()*((padres.get(pares.get(i).getX1()).getPaquetes().size())-1));
			hijos.addAll(padres.get(pares.get(i).getX1()).cruce1(padres.get(pares.get(i).getX2())));
			
		}
		
		
		return hijos;
		
	}
	/*
	public void mutacion(float porcentajeMutacion, PrintWriter file) {
		double muta;
		String mutacion = "", antiguo ="";
		listaRestaurantes restaurant = new listaRestaurantes(restaurantes);
		for(int i = 0; i < soluciones.size(); i++) {
			muta = Math.random();
			if(muta <= porcentajeMutacion) {
				Encode mutado = new Encode(idCiudad, presupuesto, kPaquetes, alfa);
				mutado = soluciones.get(i).mutacion1();
				antiguo =antiguo+ String.valueOf(soluciones.get(i).fitness()) +",";
				mutacion = mutacion + String.valueOf(mutado.fitness())+",";
				
				//if(mutado.fitness()>= soluciones.get(i).fitness()) {
					soluciones.set(i, mutado);
				//}
				//System.out.println("muto ");
			}
		}
		file.println(antiguo);
		file.println(mutacion);
		file.flush();
	}
	*/
	public void actualizarSoluciones(float porcentajePadres, int seleccion, float porcentajeMutacion, int iter) throws FileNotFoundException {
		//soluciones.addAll(cruzamiento(porcentajePadres, seleccion));
		System.out.println("seleccion: "+seleccion);
		soluciones = cruzamiento(porcentajePadres, seleccion, iter);
		//System.out.println("antiguo: "+fitnessPromedio());
		
		PrintWriter file = new PrintWriter("mutacion"+iter+".csv");
		
		soluciones = seleccionElitista(0.5f);
		//mutacion(porcentajeMutacion,file);
		file.close();
		//System.out.println("nuevo: "+fitnessPromedio());
		
	}
	
	public Encode mejorSolucion() {
		Encode mejor = soluciones.get(0);
		
		for(int i = 1; i < soluciones.size(); i++) {
			if(mejor.fitness()<soluciones.get(i).fitness()) {
				mejor = soluciones.get(i);
			}
		}
		
		return mejor;
	}

	public float fitnessPromedio() {
		float promedio = 0;
		for(int i = 0; i< soluciones.size(); i++) {
			promedio = promedio + soluciones.get(i).fitness();
		}
		promedio = promedio / soluciones.size();
		return promedio;
	}
	
	
	public void ordenarLista(ArrayList<Encode> lista) {
		
		for(int i = (lista.size()-1); i > 0; i--) {
			for(int j = 0 ; j<i ; j++) {
				if(lista.get(i).fitness()>lista.get(j).fitness()) {
					Encode aux = lista.get(i);
					lista.set(i, lista.get(j));
					lista.set(j, aux);
				}
			}
		}
	}
	
	public ArrayList<Encode> getSoluciones() {
		return soluciones;
	}

	public void setSoluciones(ArrayList<Encode> soluciones) {
		this.soluciones = soluciones;
	}

	public ArrayList<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(ArrayList<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public float getAlfa() {
		return alfa;
	}

	public void setAlfa(float alfa) {
		this.alfa = alfa;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	
	
	
}
