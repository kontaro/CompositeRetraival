import java.io.BufferedReader;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

public class listaRestaurantes {
	ArrayList<Restaurante> listarestaurantes=new ArrayList<Restaurante>();

	public listaRestaurantes() {
		// Deja la lista de restaurantes vacia
		

	}
        public listaRestaurantes(ArrayList<Restaurante> lista) {
		// Deja la lista de restaurantes vacia
		cargarLista(lista);

	}
	public listaRestaurantes(int numero) {
		// Lista de restaurantes cargada
		
		listarestaurantes=cargarResturantes();
		

	}
	
        
        public Restaurante getRestaurante(int indice){
            return listarestaurantes.get(indice);
        }
        public ArrayList<Restaurante> getRestaurantes(){
            return listarestaurantes;
        }
        
        
        
        
        
        
        
	/**
	 * Crea una lista de restaurantes llenados con la BD
	 * 
	 */
	public ArrayList<Restaurante> cargarResturantes(){
		ArrayList<Restaurante> restaurantes=new ArrayList<Restaurante>();
		//primero creamos los restaurantes y les asignamos us idciudad y idrestaurante
		restaurantes=leerRestaurantes();
		//System.out.println("cargado ID restaurante-ciudad");
		
		//se le asignan los costes
		leerCostos(restaurantes);
		System.out.println("cargado costos restaurantes");	
		//se le asignan los tipos de cocina
		leerTipos(restaurantes);
		System.out.println("cargado tipos cocinas");
		//se le asignan las compatibilidades
		leerReview(restaurantes);
		System.out.println("cargado review de restaurantes");
		return restaurantes;
	}
	public int cargarLista(ArrayList<Restaurante> listaCaso){
		if(listaCaso!=null){
			listarestaurantes=listaCaso;
			return 1;
		}
		else {
			return 0;
		}
		
	}
	
	public Restaurante buscarRestaurante(ArrayList<Restaurante> lista, int id) {
		
		Restaurante encontrado=null;//restaurante que buscamos
		for(int i=0;i<lista.size();i++){
			if(id==lista.get(i).getIdresturant()){
				
				encontrado=lista.get(i);
				
			}
			
		}
		
		return encontrado;
		
		
	}
	/**
	 * Funcion encargada asignar los idciudad y de restaurante
	 * 
	 * @return
	 */
	public ArrayList<Restaurante> leerRestaurantes(){
		ArrayList<Restaurante> lista=new ArrayList<Restaurante>();
		try
		{
			
			String linea;
			String []aux;
			int idciudad;
			int idrestaurante;
			
			String dir = System.getProperty("user.dir");

			// directory from where the program was launched
			// e.g /home/mkyong/projects/core-java/java-io
			System.out.println(dir);
			
			FileReader lector=new FileReader("city_restaurant.txt");
			BufferedReader contenido=new BufferedReader(lector);
			while((linea=contenido.readLine())!=null)
			{
				aux=linea.split("\t");
				idciudad=Integer.parseInt(aux[0]);
				idrestaurante=Integer.parseInt((aux[1]));
				Restaurante nuevo= new Restaurante();
				nuevo.setid(idciudad,idrestaurante);

				lista.add(nuevo);
	
			}
			contenido.close();
		}
		catch(FileNotFoundException h)
		{
			JOptionPane.showMessageDialog(null, "No se pudo cargar la base de datos");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en leer los registros de una base de datos");
			//e.printStackTrace();
		}
		return lista;
		
	}
	
	public ArrayList<Restaurante> leerCostos(ArrayList<Restaurante> lista){
		
		try
		{
			
			String linea;
			String []aux;
			int costo;
			int idrestaurante;
			Restaurante auxiliar=null;
			FileReader lector=new FileReader("restaurant_cost.txt");
			BufferedReader contenido=new BufferedReader(lector);
			while((linea=contenido.readLine())!=null)
			{
				aux=linea.split("\t");
				idrestaurante=Integer.parseInt(aux[0]);
				costo=Integer.parseInt((aux[1]));
				auxiliar=buscarRestaurante(lista,idrestaurante);
				
				//existena algunos restaurantes que no se encuentran asociadas a la ciudad
				//existen 2 opciones incluirlas y dejarlas con el ido de ciudad=0 o
				//excluirlas y trabajar con las que se encuentran en el bd de restaurantes y ciudades
				
				//para razones de pruebas dejare las dos formas implementadas pero se comentara
				//el codigo que incluye los restaurantes sin idciudad
				
				//El error se debia a una limpieza por lo tanto no se consideran los in idciudad
				if(auxiliar==null){
					//no hace nada
						//auxiliar=new restaurante();
						//auxiliar.setid(0, idrestaurante);
						//auxiliar.setCosto(costo);
						//lista.add(auxiliar);
				}
				else{
					auxiliar.setCosto(costo);
				}
				//habitantes=Integer.parseInt(contenido.readLine());
				 //agregarComuna(nombre,habitantes);	
			}
			contenido.close();
		}
		catch(FileNotFoundException h)
		{
			JOptionPane.showMessageDialog(null, "No se pudo cargar la base de datos");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en leer los registros de una base de datos");
			//e.printStackTrace();
		}
		return lista;
		
	}
	
	public void imprimirLista(){
		for(int i=0;i<listarestaurantes.size();i++){
			listarestaurantes.get(i).imprimirDatos();
		}
	}
	
	public ArrayList<Restaurante> leerTipos(ArrayList<Restaurante> lista){
		
		try
		{
			
			String linea;
			String []aux;
			int tipoCocina;
			int idrestaurante;
			Restaurante auxiliar=null;
			FileReader lector=new FileReader("restaurant_cuisine.txt");
			BufferedReader contenido=new BufferedReader(lector);
			while((linea=contenido.readLine())!=null)
			{
				aux=linea.split("\t");
				idrestaurante=Integer.parseInt(aux[0]);
				tipoCocina=Integer.parseInt((aux[1]));
				auxiliar=buscarRestaurante(lista,idrestaurante);
				if(auxiliar==null){
					//System.out.println(idrestaurante);
					//System.out.println(tipoCocina);
				}
				else{
					auxiliar.addTipo(tipoCocina);
				}
	
			}
			contenido.close();
		}
		catch(FileNotFoundException h)
		{
			JOptionPane.showMessageDialog(null, "No se pudo cargar la base de datos");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en leer los registros de una base de datos");
			//e.printStackTrace();
		}
		return lista;
		
	}

	
	
	
	
	
	/**
	 * Carga los valores  de los archivos compat intersecction y
	 *  compat jaccard, lo cuales son guardados en las estructura review del restaurante
	 * @param lista
	 * @return
	 */
	public ArrayList<Restaurante> leerReview(ArrayList<Restaurante> lista){
		
		try
		{
			//en los dos archivos que utilizamos los valores coinciden por lo que se ingresara
			//los dos valores en una sola operacion
			
			String lineaJaccard;
            String lineaIntersection;
			//variable para obtener los datos
			String []aux;
            String []aux2;
			
			//variables para guardar momentatiamente los datos

			float jaccard;
            float intersection;
		
			int idrestaurante1;
			int idrestaurante2;
			Restaurante auxiliarRestauranteUno=null;
			Restaurante auxiliarRestauranteDos=null;
			FileReader lectorJaccard=new FileReader("restaurant_compat_jaccard.txt");
			FileReader lectorIntersection=new FileReader("restaurant_compat_intersection.txt");
			BufferedReader contenidoJaccard=new BufferedReader(lectorJaccard);
			BufferedReader contenidoIntersection=new BufferedReader(lectorIntersection);
			while(((lineaJaccard=contenidoJaccard.readLine())!=null)&&((lineaIntersection=contenidoIntersection.readLine())!=null) )
			{
				//se obtiene los datos y se separan
				aux=lineaJaccard.split("\t");
                                aux2=lineaIntersection.split("\t");
				idrestaurante1=Integer.parseInt(aux[0]);
				idrestaurante2=Integer.parseInt((aux[1]));
				jaccard=Float.parseFloat((aux[2]));
				intersection=Float.parseFloat((aux2[2]));
				//como solo nos interesa el valor de intersection no guardamos los id de los restaurantes
		
				
				
				//se comprueba que lso restaurantes existan y se recuperan sus direccones
				auxiliarRestauranteUno=buscarRestaurante(lista,idrestaurante1);
				//si no existe uno no se puede crear
				
				if(auxiliarRestauranteUno==null){
					//System.out.println(idrestaurante1);
				}
				else{
					auxiliarRestauranteDos=buscarRestaurante(lista,idrestaurante2);
					if(auxiliarRestauranteDos==null){
						//System.out.println(idrestaurante2);
					}
					else{
						//se ingresa el id del restaurante contrario y sus valores
						auxiliarRestauranteDos.addCompatibilidad(idrestaurante1,jaccard,intersection);
						
						auxiliarRestauranteUno.addCompatibilidad(idrestaurante2,jaccard,intersection);
					}
				}
			}
			
			//Desechamos los punteros a los archivos
			contenidoJaccard.close();
			
		}
		catch(FileNotFoundException h)
		{
			JOptionPane.showMessageDialog(null, "No se pudo cargar la base de datos");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en leer los registros de una base de datos");
			//e.printStackTrace();
		}
		return lista;		
	}

	///////FUNCIONES LISTA
	
	//public restaurante getRestauranteAzar(){
	//	Random rnd=new Random();
	//	int iteracion=(int)(rnd.nextDouble()*listarestaurantes.size());
	//	
	//}
	/**
	 * Devuelve un restaurante al azar por el id de la ciudad
	 * @param idciudad
	 * @return
	 */
	public Restaurante getRestauranteAzar(int idciudad){
		Random rnd=new Random();
		
		ArrayList<Restaurante> lista=restaurantesCiudad(idciudad);
		
		int iteracion=(int)(rnd.nextDouble()*lista.size());
		
		return lista.get(iteracion);
	}
	
	
	/**
	 * Funcion para obetener el restaurante segun el id
	 * @param idRestaurante
	 * @return Restaurante
	 */
	public Restaurante obtenerRestaurante(int idRestaurante){
		Restaurante restauranteValido= null;
		for(int i =0;i<listarestaurantes.size();i++){
			restauranteValido=listarestaurantes.get(i);
			if (restauranteValido.getIdresturant()==idRestaurante) {
				return restauranteValido;
			}
		}
		return null;
	}
	
	/**
	 * Modifica la lista 
	 * 
	 * @param idciudad
	 * @return
	 */
	public ArrayList<Restaurante> restaurantesCiudad(int idciudad){
		ArrayList<Restaurante> nuevaLista= new ArrayList<Restaurante>();
		Restaurante restauranteValido= null;
		for(int i =0;i<listarestaurantes.size();i++){
			restauranteValido=listarestaurantes.get(i);
			if (restauranteValido.getCiudad()==idciudad) {
				nuevaLista.add(restauranteValido);
			}
			
		}
		return nuevaLista;
	}
	public int getTamanoLista(){
		return listarestaurantes.size();
	}
	
	/**
	 * Obtener la cantidad de ciudades que tiene  una cantidad menor a x restaurantes
	 * @param cantidad
	 * @return
	 */
	public int restaurantesMenoresQue(int cantidad){
		int coun=0;
		int totalrestaurantes=0;

		for (int i = 1; i <= 121; i++) {
			coun=0;
			for(int j=0;j < listarestaurantes.size();j++){
				if(listarestaurantes.get(j).getCiudad()==i){
					coun=coun+1;
				}
			}
			if(coun<=100)
				
				totalrestaurantes=totalrestaurantes+1;
		}
		
		return totalrestaurantes;
	}
	public int restaurantesMenores(){
		int coun=0;
		int totalrestaurantes=0;
		int menor=5000;
		for (int i = 1; i <= 121; i++) {
			coun=0;
			for(int j=0;j < listarestaurantes.size();j++){
				if(listarestaurantes.get(j).getCiudad()==i){
					coun=coun+1;
				}
			}
			if(coun<=menor)
				
				menor=coun;
		}
		
		return menor;
	}
	
	public int obtenerId(Restaurante buscado) {
    	return buscado.getIdresturant();
    }
	
      public void removeElements(ArrayList<Restaurante> lista ){
            
            for(int i =0;i<lista.size();i++){
                listarestaurantes.remove(lista.get(i));
            }
        }
        public void removeElement(Restaurante item ){
            
            
            listarestaurantes.remove(item);
            
        }
        
    public void sortByReview(){
    	Collections.sort(listarestaurantes,new MyListCom());
    	
    }
    
}
class MyListCom implements Comparator<Restaurante>{
	public int compare(Restaurante r1, Restaurante r2) {
		if(r1.getQReview()==r2.getQReview())return 0;
		if(r1.getQReview() < r2.getQReview()){
            return 1;
        } else {
            return -1;
        }
        
        
    }
}

