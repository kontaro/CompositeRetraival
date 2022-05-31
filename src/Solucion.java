import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class Solucion {
	int ciudad;
	int presupuesto;
	float alfa; 
    float costo;//costo hace referancia al valor fitness  que toma la solucion.
	ArrayList<Paquete> paquetes=new ArrayList<Paquete>();

	/**
	 * 
	 * @param kSoluciones cantidad de paquetes
	 * @param lista :lista qur contiene los restaurantes
	 * @param alfa: el valor alfa dado para la compatibilidad
	 * @param ciudad: el numero de la lista(que comprende de 1 - 121)
	 * @param presupuesto: presupuesto que tendra cada paquete
	 */
	public Solucion (int kSoluciones, listaRestaurantes lista,float alfa,int ciudad,int presupuesto) {
		// TODO Auto-generated constructor stub
		
		this.ciudad=ciudad;
		this.alfa=alfa;
		this.presupuesto=presupuesto;
		generarSoluciones(kSoluciones,lista);
        costo=funcionFitness();
	}
	
	public Solucion() {
		
	}
	public Solucion(int ciudad,float alfa,int presupuesto,ArrayList<Paquete> soluciones) {
		this.ciudad=ciudad;
		this.alfa=alfa;
		this.presupuesto=presupuesto;
        updatePaquete(soluciones);
        actualizar();
                
		
	}
        public Solucion(int ciudad,float alfa,int presupuesto) {
		this.ciudad=ciudad;
		this.alfa=alfa;
		this.presupuesto=presupuesto;
	
                
		
	}
    public Solucion(int kSoluciones, listaRestaurantes lista,float alfa,int ciudad,int presupuesto,int nada){
    	this.ciudad=ciudad;
		this.alfa=alfa;
		this.presupuesto=presupuesto;
		generarSolucionesInteligentes(kSoluciones,lista);
        costo=funcionFitness();
    }
    public Solucion(int kSoluciones, listaRestaurantes lista,float alfa,int ciudad,int presupuesto,String nada){
    	this.ciudad=ciudad;
		this.alfa=alfa;
		this.presupuesto=presupuesto;
		generarSolucionesVacias(kSoluciones,lista);
        costo=funcionFitness();
    }
	public void generarSolucionesVacias(int numero, listaRestaurantes lista){
		
		for (int i = 0; i < numero; i++) {
			Paquete paqueteRandom = new Paquete(presupuesto, ciudad);
			//paqueteRandom.generarSolucion(lista);
			paqueteRandom.agregarRestaurante(restauranteAzar(lista));
			paquetes.add(paqueteRandom);

		}
	}
	//recuperaremos los paquetes y las soluiones
        public void update(Solucion s){
            this.alfa=s.alfa;
            this.ciudad=s.ciudad;
            //this.paquetes=(ArrayList)s.getSoluciones().clone();
            this.paquetes.clear();
            setPaquetes(s.paquetes);
            actualizar();
            costo=funcionFitness();
            
        }
        public void updatePaquete(ArrayList<Paquete> paquetes){
            //this.alfa=s.alfa;
            //this.ciudad=s.ciudad;
            //this.paquetes=(ArrayList)s.getSoluciones().clone();
            this.paquetes.clear();
            setPaquetes(paquetes);
            actualizar();
            costo=funcionFitness();
            
        }
        
        public void writeBestSolutions(String nameFile)throws IOException{
            File archivo = new File((nameFile+".txt"));
            BufferedWriter bw;
            if(archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(datosLista());
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(datosLista());
            }
            bw.close();
        }
        
        
        /**
         * Crea los paquetes con los restaurantes
         * @param soluciones 
         */
	public void setPaquetes(ArrayList<Paquete> paquetes) {		
            
            //System.out.println(paquetes.size());       
                for(int j=0; j<paquetes.size(); j++){
                    
                    Paquete paquete = new Paquete(presupuesto, ciudad);
                    paquete.setRestaurantes(paquetes.get(j).getRestaurantes());
                    
                    this.paquetes.add(paquete);
                    
                }
            //System.out.println("Salio"); 
                
	}
	
	public ArrayList<Paquete> getPaquetes2() {		
        ArrayList<Paquete> nuevos = new ArrayList<Paquete>();
        //System.out.println(paquetes.size());       
            for(int j=0; j<paquetes.size(); j++){
                
                Paquete paquete = new Paquete(presupuesto, ciudad);
                paquete.setRestaurantes(paquetes.get(j).getRestaurantes());
                
                nuevos.add(paquete);
                
            }
        //System.out.println("Salio"); 
       return nuevos;
            
}
	public int getCiudad() {
		return ciudad;
	}
	public int getPresupuesto() {
		return presupuesto;
	}
	public float getAlfa() {
		return alfa;
	}
	public ArrayList<Paquete> getPaquetes() {
		return paquetes;
	}
	//genera un grupo de paquetes al azar
	public void generarSoluciones(int numero, listaRestaurantes lista){
		
		for (int i = 0; i < numero; i++) {
			Paquete paqueteRandom = new Paquete(presupuesto, ciudad);
			paqueteRandom.generarSolucion(lista);

			paquetes.add(paqueteRandom);

		}
	}
	public void generarSolucionesInteligentes(int numero, listaRestaurantes lista){
		//agregamos un elemento al azar en cada paquete
		
		
		lista.sortByReview();
		//Restaurante nuevoRestaurante= new Restaurante();
		for (int i = 0; i < numero; i++) {
			Paquete paqueteRandom = new Paquete(presupuesto, ciudad);
			//paqueteRandom.generarSolucion(lista);
			
			paqueteRandom.agregarRestaurante(lista.getRestaurante(i));
			paquetes.add(paqueteRandom);
			//System.out.println("Entro al while y se cayo "+ i);
			int iter=0;
			while(paquetes.get(i).getPresupuesto()<presupuesto && iter<150){
				paquetes.get(i).agregarRestaurante(paquetes.get(i).restauranteAzar(lista));
				iter++;
			}
			//System.out.println("no se cayo aqui");
		}
		
		
		//luego agregamos un elemento que aumente el 
	}
	
	/**
	 * Se copia las paquetes de la lista obtenida
	 * @param numero
	 * @param lista
	 * 
	 * @param soluciones
	 */
	public void generarSoluciones(int numero, listaRestaurantes lista,ArrayList<Paquete> soluciones){
		int restauranteABuscar=0;
		for (int i = 0; i < numero; i++) {
			Paquete nuevoPaquete = new Paquete(presupuesto, ciudad);
			for(int j=0;j<soluciones.get(i).tamanoSolucion();j++){
				restauranteABuscar=soluciones.get(i).obtenerRestaurante(j);
				nuevoPaquete.agregarRestaurante(lista, restauranteABuscar);
			}
			nuevoPaquete.actualizarTipos();

			soluciones.add(nuevoPaquete);

		}
	}
        public void actualizar(){
            for(int i=0;i<paquetes.size();i++){
                paquetes.get(i).actualizarTipos();
            }
        }
        

    
    public void cambiarRestauranteLejano(listaRestaurantes lista,int movPaquete,int iter){
    	int iteracion=iter%movPaquete;
        
		paquetes.get(iteracion).cambiarRestaurantePorCentroide(lista);
		costo=funcionFitness();
		
		
    }
    public void cambiarRestauranteLejano(listaRestaurantes lista){
    	Random rnd=new Random();
		int iteracion=(int)(rnd.nextDouble()*paquetes.size());
        
		paquetes.get(iteracion).cambiarRestaurantePorCentroide(lista);
		costo=funcionFitness();
		
		
    }
    public void cambiarRestauranteLejano2(listaRestaurantes lista){
    	Random rnd=new Random();
		int iteracion=(int)(rnd.nextDouble()*paquetes.size());
        
		paquetes.get(iteracion).cambiarRestaurantePorCentroide2(lista);;
		costo=funcionFitness();	
		
    }
    public void cambiarRestauranteLejano3(listaRestaurantes lista){
    	Random rnd=new Random();
		int iteracion=(int)(rnd.nextDouble()*paquetes.size());
        
		paquetes.get(iteracion).cambiarRestaurantePorCentroide3(lista);
		costo=funcionFitness();	
		
    }
	public void cambiarRestauranteAzar(listaRestaurantes lista){
		
		Random rnd=new Random();
		int iteracion=(int)(rnd.nextDouble()*paquetes.size());
		if(paquetes.get(iteracion).tamanoSolucion()==0) {
			paquetes.get(iteracion).agregarRestaurante(restauranteAzar(lista));
		}
		paquetes.get(iteracion).cambiarRestauranteAzar(lista);
		//costo=funcionFitness();
	}
    public void cambiarRestauranteAzar(listaRestaurantes lista,int movPaquete,int iter){
		//System.out.println(paquetes.size());
                
		int iteracion=iter%movPaquete;
        iteracion=iteracion;
                
                 //iteracion=iteracion-1;
		paquetes.get(iteracion).cambiarRestauranteAzar(lista);
		costo=funcionFitness();
	}

	public void cambiarPaqueteAzar(listaRestaurantes lista){
		
		
		Random rnd=new Random();
		int iteracion=(int)(rnd.nextDouble()*paquetes.size());
		paquetes.get(iteracion).generarSolucion(lista);
		costo=funcionFitness();
	}
	
	public int totalRestaurantes() {
		int totalRestaurantes=0;
		for (int i = 0; i < paquetes.size(); i++) {
			totalRestaurantes=totalRestaurantes+paquetes.get(i).tamanoSolucion();
		}
		return totalRestaurantes;
	}
	
	public int costo() {
		int totalCostoPaquetes=0;
		for (int i = 0; i < paquetes.size(); i++) {
			totalCostoPaquetes=totalCostoPaquetes+paquetes.get(i).presupuesto;
		}
		return totalCostoPaquetes;
	}
	public float intra() {
		return compatibilidadPaquetesSinAlfa();
	}
	public float inter() {
		return diversidad(paquetes,0);
	}
	public String datosFitness(){
		String texto=new String("");
		System.out.print(intra()+","+inter());
		return texto;
	}
	public String datosLista(){
		String texto=new String("");
		
		for (int i = 0; i < paquetes.size(); i++) {
                    
                    texto=texto+"---------------\n";
                    texto=texto+"Paquete: ";
                    texto=texto+Integer.toString(i)+"\n";
                    texto=texto+"tamaño: "+Integer.toString(paquetes.get(i).tamanoSolucion())+"\n";
                   
                    texto=texto+ paquetes.get(i).imprimirSolucion();
                    texto=texto+datosSolucion(i);
		}
                return texto;
	}
	
	
	public float funcionFitness() {
		
		float fitness=0;
		fitness=compatibilidadPaquetes()+(diversidad(paquetes,alfa));
                //System.out.println("aqui esta el alfa"+alfa);
		return fitness;
	}
	
	
	public float compatibilidadPaquetes(){
		
		float compatibilidad=0;
		//System.out.println("esto es de un paquete");
		for (int i = 0; i < paquetes.size(); i++) {
			//System.out.println("paquete:"+i);
			compatibilidad=compatibilidad + paquetes.get(i).compatibilidad();
			
		}
		//System.out.println("combatiblidad sin alfa: "+compatibilidad);
		return compatibilidad*alfa;
	}
	public float compatibilidadPaquetesSinAlfa(){
		
		float compatibilidad=0;
		//System.out.println("esto es de un paquete");
		for (int i = 0; i < paquetes.size(); i++) {
			//System.out.println("paquete:"+i);
			compatibilidad=compatibilidad + paquetes.get(i).compatibilidad();
			
		}
		//System.out.println("combatiblidad sin alfa: "+compatibilidad);
		return compatibilidad;
	}
	
	public float diversidadPaquete() {

		return diversidad(paquetes,alfa);
	}
	public float diversidadPaqueteSinAlfa() {

		return diversidad(paquetes,0);
	}
	
	/**
	 * Esta funcion devuelve los datos
	 * actualmente es un void, modificar a string
	 */
	public String datosSolucion(int i){
            String texto=new String("");
                texto=texto+"Compatibilidad:"+Float.toString(paquetes.get(i).compatibilidad())+" Diversidad:"+Float.toString(diversidad(paquetes,0));
		//System.out.println("Compatibilidad:"+compatibilidadPaquetesSinAlfa()+" Diversidad:"+(diversidad(paquetes,0)));
                return texto;
	}
	
	
	/**
	 * calcula la diversidad entre los paquetes
	 * esta es calculado sumando la maxima simulitud de los restaurantes entre paquetes
	 * @return
	 */
	public float diversidad(ArrayList<Paquete> diversidadPaquetes,float alfa) {
		float valorDiversidad=0;
		float maxCompatibilidad=0;
		
		ArrayList<Paquete> paquetes=diversidadPaquetes;
		Paquete paqueteUno;
		Paquete paqueteDos;
		//System.out.println("--------------------------------------------");
		for (int i = 0; i < paquetes.size(); i++) {
			paqueteUno=paquetes.get(i);
			for (int j = i+1; j < paquetes.size(); j++) {
				paqueteDos=paquetes.get(j);
				maxCompatibilidad=1-maxCompatibilidadEntrePaquetes(paqueteUno, paqueteDos);
				//System.out.println(maxCompatibilidad);
				valorDiversidad=valorDiversidad+(maxCompatibilidad*(1-alfa));
				
				//no deberia ser 1 -el valor max	
			}
			
		}
		
		return valorDiversidad;
		
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
	 * Obtiene un restaurante al azar de la lista
	 * @param lista
	 * @return un restaurante
	 */
	public Restaurante restauranteAzar(listaRestaurantes lista){
		return lista.getRestauranteAzar(ciudad);
	}

	public void cambiarRestauranteExhaustivo(listaRestaurantes lista){
		
		//Random rnd=new Random();
		Restaurante nuevo=restauranteAzar(lista);
		//System.out.println("restaurante "+ nuevo);
		bestChange(nuevo,lista);
		//costo=funcionFitness();
	}
	
	
	public void cambiarPaqueteExhaustivo(listaRestaurantes lista){
		Random rnd=new Random();
		Paquete nuevo=new Paquete(presupuesto, ciudad);
		nuevo.generarSolucion(lista);
		
		
		
		//System.out.println("restaurante "+ nuevo);
		bestChangePaquete(nuevo,lista);
		//costo=funcionFitness();
	}
	public int bestChange(Restaurante pivote,listaRestaurantes lista) {
		
		Solucion aux;
		float best=funcionFitness();
		int idBestRestaurant;
		int iteracionPaquete = 0;
		int iteracionRestaurante=0;
		//iteracion por paquete
		
		//System.out.println(funcionFitness());
		for(int i=0;i<paquetes.size();i++) {
			//iteracion por restaurante
			

				//se cambia el nuevo restaurante por el viejo
				Restaurante antiguo=paquetes.get(i).swap( pivote);
				if(antiguo==null)break;
				if(funcionFitness()>best) {
					best=funcionFitness();
					iteracionPaquete=i;
					
				}
				//se devuelve el restaurante antiguo a su posicion original
				
				paquetes.get(i).swap(antiguo);
			
			
				
		}
		
		
		if(best>funcionFitness()) {
			//System.out.println(funcionFitness());
			paquetes.get(iteracionPaquete).swap(pivote);
			return 0;
		}
		else
		
		return 1;
	}
	public int bestChangePaquete(Paquete pivote,listaRestaurantes lista) {
		
		Solucion aux;
		float best=funcionFitness();
		int idBestRestaurant;
		int iteracionPaquete = 0;
		int iteracionRestaurante=0;
		//iteracion por paquete
		
		//System.out.println(funcionFitness());
		for(int i=0;i<paquetes.size();i++) {
			//iteracion por restaurante
			

				//se cambia el nuevo restaurante por el viejo
				Paquete antiguo=paquetes.get(i);
				paquetes.remove(i);
				paquetes.add(i, pivote);
				if(antiguo==null)break;
				if(funcionFitness()>best) {
					best=funcionFitness();
					iteracionPaquete=i;
					
				}
				//se devuelve el restaurante antiguo a su posicion original
				
				paquetes.remove(i);
				paquetes.add(i,antiguo);
			
			
				
		}
		
		
		if(best>funcionFitness()) {
			//System.out.println(funcionFitness());
			paquetes.remove(iteracionPaquete);
			paquetes.add(iteracionPaquete,pivote);
			return 0;
		}
		else
		
		return 1;
	}

	

}
