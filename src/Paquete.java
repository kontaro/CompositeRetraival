import java.util.ArrayList;
import java.util.Random;

//solucion tiene un grupo de restaurantes
//y el valor del fitness del paquete
public class Paquete {
	ArrayList<Restaurante> paquete =new ArrayList<Restaurante>();
	ArrayList<Integer> tiposCocinas= new ArrayList<Integer>();
	int fitnessPaquete= 0;
	int ciudad=0;
	int budget=0;
	int presupuesto=0;
	// Una solucion no deberia poder superar el limite 
        
	public Paquete(int limite, int idCiudad){

		budget=limite;
		ciudad=idCiudad;
		
	}
	
	public void generarSolucion(listaRestaurantes lista){
		paquete.clear();
        int iteracion=0;
		
		//re hace esta linea de codigo pudes dejar la iteracion dentro del while
		while (presupuesto<budget) {
			Restaurante nuevo=new Restaurante();
			nuevo=restauranteAzar(lista);
			agregarRestaurante(nuevo);
			
			if(presupuesto>budget || iteracion>=40){
				//eliminarRestaurante(iteracion);
				break;
			}
			
			iteracion=iteracion+1;
		
		}
	}
	
	public ArrayList<Integer> agregarSolucion(ArrayList<Restaurante> restaurantes){
		listaRestaurantes lista = new listaRestaurantes(restaurantes);
		ArrayList<Integer> indices = new ArrayList<Integer>();
        int iteracion=0;
		
		
		while (presupuesto<budget) {
			Restaurante nuevo=new Restaurante();
			nuevo=restauranteAzar(lista);
			if(agregarRestaurante(nuevo)==1) {
				indices.add(restaurantes.indexOf(nuevo));
			}
			if(presupuesto>=budget || iteracion>=40){
				//eliminarRestaurante(iteracion);
				break;
			}
			
			iteracion=iteracion+1;
		
		}
		if(indices.isEmpty()) {
			return null;
		}
		
		return indices;
	}
	
	public void agregarSolucion(listaRestaurantes lista){
			
	        int iteracion=0;
			
			
			while (presupuesto<budget) {
				Restaurante nuevo=new Restaurante();
				nuevo=restauranteAzar(lista);
				agregarRestaurante(nuevo);
				
				if(presupuesto>budget || iteracion>=40){
					//eliminarRestaurante(iteracion);
					break;
				}
				
				iteracion=iteracion+1;
			
			}
		}
	
	//---------------Getter and Setter Functions ---------------------------------//
        
       
	
        
        public ArrayList<Restaurante> getRestaurantes(){
            return paquete;
        }
        
        public ArrayList<Restaurante> getRestaurantes2(){
        	ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
            for(int i=0;i<paquete.size();i++){
            	Restaurante r = new Restaurante();
            	r.setDatos(ciudad, paquete.get(i).getCosto());
            	r.setid(ciudad, paquete.get(i).getIdresturant());
            	r.setTipo(paquete.get(i).getTipo2());
            	r.setReviews(paquete.get(i).getReviews());
            	
            	restaurantes.add(r);
            }
            
            return restaurantes;
        }
        
        public ArrayList<Integer> getTiposCocinas(){
            return tiposCocinas;
        }
        /***
         * Retorna la suma de los precios de los elementos que conforman el paquete
         * @return
         */
        public int getPresupuesto(){
            return presupuesto;
        }
        
        
        public void addRestaurantes(ArrayList<Restaurante> nuevosRestaurantes){
            paquete.addAll(nuevosRestaurantes);
        }
        public void addTiposCocinas(ArrayList<Integer> nuevosTiposCocinas){
            tiposCocinas.addAll(nuevosTiposCocinas);
        }
        public void addPresupuesto(int segundoPresupuesto){
            presupuesto=presupuesto+segundoPresupuesto;
            
        }
        
       
        
        
        //--------------------Class Functions ---------------------------------------//
	
        
        public void setRestaurantes(ArrayList<Restaurante> listaPaquete){
           
            for(int i=0;i<listaPaquete.size();i++){
                agregarRestaurante(listaPaquete.get(i));
            }
        }
        
        
        
        /**
	 * Cambia un Restaurante por un indice, si despues de 10 iteraciones no encuentra un retaurante
	 * la lista se queda con un Restaurante menos
	 * @param index
	 * @param lista
	 */
	public void cambiarRestaurante(int index,listaRestaurantes lista){
		
		int iteracion=0;
		int situacion=0;

		if(tamanoSolucion()!=0 && (budget/2)<presupuesto)
			eliminarRestaurante(index);
			actualizarTipos();

		while (situacion==0 && iteracion<100) {
			
			//situacion=agregarRestaurante(restauranteAzar(lista));
			if(agregarRestaurante(restauranteAzar(lista))==1){
				
				situacion=1;
			}
			iteracion=iteracion+1;
		}
		actualizarTipos();
	
	}
	
	/**
	 * Cambia un Restaurante por un indice, si despues de 10 iteraciones no encuentra un retaurante
	 * la lista se queda con un Restaurante menos
	 * @param index
	 * @param lista
	 */
	public void cambiarRestauranteAzar(listaRestaurantes lista){
		Random random=new Random();
		int index=(int)(random.nextDouble()*tamanoSolucion());
		cambiarRestaurante(index, lista);
		
	}
	

	
	/**
	 * Es la sumatoria de las compatjaccard entre los restaurantes
	 * 
	 * @return
	 */
	public float compatibilidad(){
		float compatibilidad=0;
		int tamano=paquete.size();
		int idRestauranteAComparar;
                //int h=0;
		
		//suma las compatibilidades de los restaurantes
		for(int i =0;i<tamano;i++){
			idRestauranteAComparar=paquete.get(i).getIdresturant();
			for (int j = i+1 ; j < tamano; j++) {
                                //h=h+1;
				compatibilidad=compatibilidad + paquete.get(j).searchCompatInstersection(idRestauranteAComparar);
				//System.out.println(paquete.get(j).searchCompatInstersection(idRestauranteAComparar));		
			}		
                        
		}
                //System.out.println(h);
		return compatibilidad;
	}
	
	/**
	 * Escoge un Restaurante al azar
	 * el cual respeta el id de la ciudad
	 * @return
	 */
	public Restaurante restauranteAzar(listaRestaurantes lista){
		return lista.getRestauranteAzar(ciudad);
	}
	
	/**
	 * Agrega el Restaurante a la solucion
	 * @param posibleSolucion
	 * @return retorna 1 si se agrego y 0 si no se agrego
	 */
	public int agregarRestaurante(Restaurante posibleSolucion){
		//
		//Restricciones
		//ninguna cocina de Restaurante se puede repetir, por ende ningun restaurante se repite
		ArrayList<Integer> posiblesTiposCocina =posibleSolucion.getTipo();
		if(posibleSolucion.getIdresturant()==0){
                    return 0;
                }
		
		//Revisa si existe el tipo de cocina en el paquete
		if (existeTipoCosina(posiblesTiposCocina)) {
			return 0;
		}
		//Revisa que si es agregado al paquete no sobrepasa el presupuesto
		
		if(presupuesto+posibleSolucion.getCosto()<=budget){
			paquete.add(posibleSolucion);
			presupuesto=presupuesto+posibleSolucion.getCosto();
			
			for (int i = 0; i < posiblesTiposCocina.size() ; i++) {
				tiposCocinas.add(posiblesTiposCocina.get(i));
			}
			return 1;
		}
		//en caso contrario termina la funcion
		else
			return 0;
	}
	public void agregarRestaurante(listaRestaurantes lista,int idRestaurante){
		Restaurante nuevo = lista.obtenerRestaurante(idRestaurante);
		paquete.add(nuevo);
		budget=nuevo.getCosto();
		
	}
	
/**
 * se encarga de eliminar el Restaurante pedido, y actualiza el presupuesto del paquete
 * @param index indice del Restaurante a eliminar
 */
	public void eliminarRestaurante(int index){
		presupuesto=presupuesto-paquete.get(index).getCosto();
		paquete.remove(index);
		actualizarTipos();
	}
	
	public int eliminarRestauranteId(int id) {
		for(int i = 0; i<paquete.size(); i++) {
			//System.out.println("id paquete "+i+": "+paquete.get(i).getIdresturant());
			//System.out.println("buscado: "+ id);
			if(paquete.get(i).getIdresturant()== id) {
				presupuesto = presupuesto - paquete.get(i).getCosto();
				paquete.remove(i);
				actualizarTipos();
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Elimina un Restaurante al azar.
	 */
	public void eliminarRestauranteAzar(){
		Random rnd=new Random();
		
		
		int iteracion=(int)(rnd.nextDouble()*tamanoSolucion());
		eliminarRestaurante(iteracion);
	}
	/**
	 * Elimina un restaurante y entrega 
	 * el id del eliminado
	 */
	public int eliminarRestauranteAzarId(){
		Random rnd=new Random();
		int idRes;
		
		int iteracion=(int)(rnd.nextDouble()*tamanoSolucion());
		idRes = paquete.get(iteracion).getIdresturant();
		eliminarRestaurante(iteracion);
		
		return idRes;
	}
	
	public void cambiarRestaurantePorCentroide(listaRestaurantes lista){
		//primero se obtiene el elemento que maxsimiza la similitud
		int indiceCentroide=centroidePaquete();
		int indiceRestauranteAlejado=restauranteAlejado(indiceCentroide);
		eliminarRestaurante(indiceRestauranteAlejado);
		actualizarTipos();
		agregarRestaurante(restauranteAzar(lista));
		
	}
	public void cambiarRestaurantePorCentroide2(listaRestaurantes lista){
		//primero se obtiene el elemento que maxsimiza la similitud
		int indiceCentroide=centroidePaquete();
		int indiceRestauranteAlejado=restauranteAlejado(indiceCentroide);
		eliminarRestaurante(indiceRestauranteAlejado);
		actualizarTipos();
		agregarRestaurante(restauranteMaximiceIntra(indiceCentroide,lista));
		
	}
	public void cambiarRestaurantePorCentroide3(listaRestaurantes lista){
		//primero se obtiene el elemento que maxsimiza la similitud
		int indiceCentroide=centroidePaquete();
		
		eliminarRestauranteAzar();
		actualizarTipos();
		agregarRestaurante(restauranteMaximiceIntra(indiceCentroide,lista));
		
	}
	
	
	public Restaurante restauranteMaximiceIntra(int indiceCentroide,listaRestaurantes lista){
		Restaurante restauranteAuxiliar=new Restaurante();
		Restaurante restauranteMaximo=new Restaurante();
		int idCentroide=lista.getRestaurante(indiceCentroide).getIdresturant();
		float valorMaximo=0;
		//System.out.println("valor id "+idCentroide);
		//System.out.println("valor indice "+indiceCentroide);
		for(int i=0;i<lista.getTamanoLista();i++){
			restauranteAuxiliar=lista.getRestaurante(i);
			
			if(restauranteAuxiliar.searchCompatInstersection(idCentroide)>=valorMaximo){
				//System.out.println("existe alguno");
				if(existeTipoCosina(lista.getRestaurante(i).getTipo())){
					restauranteMaximo=lista.getRestaurante(i);
					valorMaximo=restauranteAuxiliar.searchCompatInstersection(idCentroide);
					//System.out.println("entro al if de Paquete-260");
				}
			}
			
		}
		//System.out.println("valors del maximice intra:"+valorMaximo);
		return restauranteMaximo;
	}
	
	
	
	/**
	 * Obtiene el restaurante centroide del paquete
	 * @return iindice en paquete del centroide, si es 1 solo elemente deberia devoolver el elemento 
	 */
	public int centroidePaquete(){
		int idRestaurante=0;
		float sumaSimilitud=0;
		int indiceCentroide=0;
		float sumaAuxiliar=0;
		int idRestauranteAComparar=0;
		for(int i=0;i<paquete.size();i++){
			idRestauranteAComparar=paquete.get(i).getIdresturant();
			for(int j=0;j<paquete.size();j++){
				if(i!=j){
					sumaAuxiliar=sumaAuxiliar+paquete.get(j).searchCompatInstersection(idRestauranteAComparar);
				}
			}
			if(sumaAuxiliar>sumaSimilitud){
				indiceCentroide=i;
				sumaSimilitud=sumaAuxiliar;
			}
			sumaAuxiliar=0;
		}
		
		return indiceCentroide;
	}
	/**
	 * obtiene el restaurante mas alejado, dependiendo del problema hay que cambiar la sumaMinima
	 * @param indiceCentroide
	 * @return indice restaurante
	 */
	public int restauranteAlejado(int indiceCentroide){
		int idRestauranteCentroide=paquete.get(indiceCentroide).getIdresturant();
		float sumaMinima=6000;//
		int indiceMinimo=0;
		float sumaAuxiliar=0;
		for(int i=0;i<paquete.size();i++){
			if(i!=indiceCentroide){
				sumaAuxiliar=paquete.get(i).searchCompatInstersection(idRestauranteCentroide);
				
			}
			if(sumaAuxiliar<sumaMinima){
				indiceMinimo=i;
			}
			
			
		}
		return indiceMinimo;
	}
	
	public String imprimirSolucion(){
                String texto =new String("");
		texto=texto+"idRestaurant:\n";
		for(int i=0;i<paquete.size();i++){
			texto=texto+paquete.get(i).imprimirDatos()+"\n";
		}
                return texto;
	}
	
	public String imprimirIDPaquete(){
        String texto =new String("");
		
		for(int i=0;i<paquete.size();i++){
			texto=texto+paquete.get(i).getIdresturant()+" ";
		}
        return texto;
	}
	
	
	
	public boolean existeTipoCosina(ArrayList<Integer> tipos){
		
		//obtine la lista del paquete ,
		//por Restaurante del paquete revisa si existe el tipo de cocina
		//si existe igualdad retorna true , en caso contrario retorna false
		for(int i=0;i<tiposCocinas.size();i++){
			for (int j = 0; j < tipos.size(); j++) {
				if(tiposCocinas.get(i)==tipos.get(j)){
					return true;
				}
			}
		}
		return false;
	}
        
        
	
	/**
	 * funcion para actualizar los tipos de restaurantes que tiene el paquete
	 */
	public void actualizarTipos(){
		tiposCocinas.clear();
		ArrayList<Integer> cocinas=new ArrayList<Integer>(); 
		for(int i=0;i<paquete.size();i++){
			cocinas=paquete.get(i).getTipo();
			for(int j=0;j<cocinas.size();j++){
				tiposCocinas.add(cocinas.get(j));
			}
		}
	}
	
	public int tamanoSolucion() {
		return paquete.size();
	}
	
	
	/**
	 * Obtener el id de la ciudad donde se encuentra el restaurante
	 * @return id ciudad del restaurante
	 */
	public int obtenerRestaurante(int indice) {
		return paquete.get(indice).getCiudad();
	}
	
	
	/**
	 * Arreglar funcion swap
	 * @param pivote
	 * @return
	 */
	public Restaurante swap(Restaurante pivote){
		int tamano=tamanoSolucion();
		int iteracion=0;
		//System.out.println("entra "+tamanoSolucion()+" "+tipos.size());
		
		if(iteracion==tamano)agregarRestaurante(pivote);
		else {
			Restaurante eliminado=paquete.get(iteracion);
			eliminarRestaurante(iteracion);
			agregarRestaurante(pivote);
			if( tamano>tamanoSolucion()) {
				agregarRestaurante(eliminado);
				return null;
			}
		
			//System.out.println("sale "+tamanoSolucion()+" "+tipos.size());
			return eliminado;
		}
		return null;
	}
	
	
	public void actualizarCostos() {
		presupuesto = 0;
		for(int i = 0 ; i < paquete.size(); i++) {
			presupuesto = presupuesto + paquete.get(i).getCosto();
		}
	}
	
}
