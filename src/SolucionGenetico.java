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
			if(aux.get(i).getSimilitud().size()>10) {
				System.out.println("Ayura seleccion");
			}
			padres.add(i,aux.get(i));
		}
		
		return padres;
	}
	
	
	
	public ArrayList<Encode> seleccionMoga(float porcentaje) {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		ArrayList<Encode> aux = new ArrayList<Encode>();
		int cantidad;
		float div = porcentaje;
		alfa = (float) Math.random();
		
		aux = (ArrayList<Encode>) soluciones.clone();
		
		for(int i = 0; i< aux.size(); i++) {
			aux.get(i).setAlfa(alfa);
		}
		
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
	
	
	public ArrayList<Encode> seleccionRuleta(float porcentajeASeleccionar,int iter) {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		int cantidad =(int) (soluciones.size()*porcentajeASeleccionar);
		float suma = fitnessPromedio()*soluciones.size();
		float[] individuos = new float[soluciones.size()];
		
		individuos[0] = soluciones.get(0).fitness()/suma;;
		
		for(int i = 1; i < soluciones.size(); i++) {
			individuos[i] = individuos[i-1]+(soluciones.get(i).fitness()/suma);
		}
		
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
					
					cantidad--;	
			}
		}
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
	
	public ArrayList<Encode> cruzamiento(float porcentajePadres, int seleccion, int iter)  {
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
			//int posicion = 2;
			int posicion = (int) (Math.random()*((padres.get(pares.get(i).getX1()).getPaquetes().size())-1));
			hijos.addAll(padres.get(pares.get(i).getX1()).cruce1(padres.get(pares.get(i).getX2())));
			
		}
		
		return hijos;
		
	}
	
	public ArrayList<Encode> cruzamientoTorneo_Mutacion(ArrayList<Encode> padres, float porcentajeMutacion) {
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		ArrayList<Pair> pares = new ArrayList<Pair>();
		boolean flag = true;
		int tamano = soluciones.size()/2;
		int indice1 = 0;
		int indice2 = 0;
		Pair par;

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
			hijos.addAll(padres.get(pares.get(i).getX1()).cruce1(padres.get(pares.get(i).getX2())));
		}
		
		
		hijos = mutacion2(porcentajeMutacion, hijos);
		hijos.addAll(padres);
		return hijos;
	}
	
	public ArrayList<Encode> cruzamientoElite_Mutacion(ArrayList<Encode> padres, float porcentajeMutacion) {
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		ArrayList<Pair> pares = new ArrayList<Pair>();
		boolean flag = true;
		int tamano = soluciones.size()/2;
		int indice1 = 0;
		int indice2 = 0;
		Pair par;

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
			hijos.addAll(padres.get(pares.get(i).getX1()).cruce1(padres.get(pares.get(i).getX2())));
		}
		
		
		hijos = mutacion2(porcentajeMutacion, hijos);
		hijos.addAll(padres);
		return hijos;
	}
	
	public ArrayList<Encode> cruzamientoElite_Mutacion(float porcentajePadres, float porcentajeMutacion, boolean moga) {
		ArrayList<Encode> padres = new ArrayList<Encode>();
		ArrayList<Encode> hijos = new ArrayList<Encode>();
		ArrayList<Pair> pares = new ArrayList<Pair>();
		boolean flag = true;
		int tamano = soluciones.size()/2;
		int indice1 = 0;
		int indice2 = 0;
		Pair par;

		if(moga) {
			padres = seleccionMoga(porcentajePadres);
		}else {
			padres = seleccionElitista(porcentajePadres);
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
			hijos.addAll(padres.get(pares.get(i).getX1()).cruce1(padres.get(pares.get(i).getX2())));
		}
		hijos = mutacion2(porcentajeMutacion, hijos);
		return hijos;
	}
	
	public void mutacion(float porcentajeMutacion) {
		double muta;
		
		for(int i = 0; i < soluciones.size(); i++) {
			muta = Math.random();
			if(muta <= porcentajeMutacion) {
				Encode mutado = new Encode(idCiudad, presupuesto, kPaquetes, alfa);
				mutado = soluciones.get(i).mutacion2();
				if(mutado.getSimilitud().size()>10) {
					System.out.println("ayura");
				}
				//if(mutado.fitness()>= soluciones.get(i).fitness()) {
					soluciones.set(i, mutado);
				//}
				//soluciones.add(mutado);
			}
		}
		
	}
	
	public ArrayList<Encode> mutacion2(float porcentajeMutacion, ArrayList<Encode> hijos) {
		double muta;
		
		for(int i = 0; i < hijos.size(); i++) {
			muta = Math.random();
			if(muta <= porcentajeMutacion) {
				Encode mutado = new Encode(idCiudad, presupuesto, kPaquetes, alfa);
				mutado = hijos.get(i).mutacion2();
				
				if(!mutado.revisarSimilitud()) {
					System.out.println("ayura");
				}
				//if(mutado.fitness()>= soluciones.get(i).fitness()) {
					hijos.set(i, mutado);
				//}
				//soluciones.add(mutado);
			}
		}
		return hijos;
		
	}
	
	public void actualizarSoluciones(float porcentajePadres, int seleccion, float porcentajeMutacion, int iter) {
		soluciones.addAll(cruzamiento(porcentajePadres, seleccion,iter));

		//soluciones = cruzamiento(porcentajePadres, seleccion, iter);
	
		soluciones = seleccionElitista(0.5f);
		//mutacion(porcentajeMutacion);
		
		
	}
	
	public void actualizarSolucionesNSGA2(ArrayList<Encode> padres, float porcentajeMutacion) {
		soluciones.addAll(cruzamientoElite_Mutacion(padres, porcentajeMutacion));
	}
	
	public void actualizarSolucionesNSGA(float porcentajePadres, int seleccion, float porcentajeMutacion, int iter) {
		soluciones.addAll(cruzamientoElite_Mutacion(porcentajePadres, porcentajeMutacion, false));
	}
	
	public void actualizarSolucionesMOGA(float porcentajePadres, int seleccion, float porcentajeMutacion, int iter) {
		soluciones = cruzamientoElite_Mutacion(porcentajePadres, porcentajeMutacion, true);
	}
	
	public boolean borrarNSoluciones(int n) {
		if(n>soluciones.size()) {
			return false;
		}
		
		for(int i = 0; i < n; i++) {
			int index = (int)(Math.random()*(soluciones.size()-1));
			soluciones.remove(index);
		}
		
		return true;
	}
	
	public boolean borrarNPeoresSoluciones(int n) {
		if(n>soluciones.size()) {
			return false;
		}
		ordenarLista(soluciones);
		
		for(int i = n-1; i >= 0; i--) {
			soluciones.remove(i);
		}
		
		return true;
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
