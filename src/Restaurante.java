import java.util.ArrayList;

//item
public class Restaurante {
	private int idresturant;
	private int idciudad;
	private int costo;// 10 20 o 30 
	private ArrayList<Integer> tipo;
	private ArrayList<Review> reviews; //la lista contiene la similitud que tiene con otro restaurante

	//el compatjaccard es la relacion que tiene con otro restaurante por lo tanto deberia 
	//deberia contener esa informacion
	
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	Restaurante(){
		idresturant=0;
		idciudad=0;
		costo=0;

		tipo=new ArrayList<Integer>();
		reviews=new ArrayList<Review>();	
	}
	/**
	 * 
	 * @param ciudad
	 * @param costo
	 * @param compat
	 * @param compatJaccard
	 * @param tipo
	 */
	public void setDatos(int ciudad ,int costo ){
		setCiudad(ciudad);

		setCosto(costo);
				
		
	}
	public String imprimirDatos() {
		//System.out.print(getCiudad());
		//System.out.print(" ");
                return Integer.toString(getIdresturant());


	}
	public void imprimirTipo() {
		ArrayList<Integer> tipos =getTipo();
		for(int i=0;i<tipos.size();i++){
			System.out.print(tipos.get(i));
			System.out.print(" ");
		}
		//for que imprime los datos
	}
	
	// Se declaran los setter and getters de la clase
	public void setid(int ciudad, int restairan){
		setCiudad(ciudad);
		setIdresturant(restairan);
	}

	public int getIdresturant() {
		return idresturant;
	}
	public void setIdresturant(int idresturant) {
		this.idresturant = idresturant;
	}

	
	public int getCiudad() {
		return idciudad;
	}
	public void setCiudad(int ciudad) {
		this.idciudad = ciudad;
	}
	public int getCosto() {
		return costo;
	}
	public void setCosto(int costo) {
		this.costo = costo;
	}

	
	public ArrayList<Integer> getTipo() {
		return tipo;
	}
	
	public ArrayList<Integer> getTipo2(){
		ArrayList<Integer> t = new ArrayList<Integer>();
		for(int i = 0; i < tipo.size(); i++) {
			t.add(tipo.get(i));
		}
		return t;
	}
	public void setTipo(ArrayList<Integer> tipo) {
		this.tipo = tipo;
	}
	public void addTipo(int idTipo){

		if(tipo.contains(idTipo)==false){

			tipo.add(idTipo);
		}
	}

	
	public void addCompatibilidad(int idRestaurant , float jaccard, float comentario){
		
		Review restauranteCompatible=new Review(idRestaurant ,jaccard,comentario);
		reviews.add(restauranteCompatible);
		
	}
	public void addCompatibilidad(int idRestaurant , float jaccard){
		
		Review restauranteCompatible=new Review(idRestaurant ,jaccard);
		reviews.add(restauranteCompatible);
		
	}
	
	/**
	 * Busca al restaurante con el que tiene un review
	 * @param idRestaurante
	 * @return las datos del review , en caso de no encontrarlo devuelve null
	 */
	public Review searchIdRestauranReview(int idRestaurante) {
		
		for (int i = 0; i < reviews.size(); i++) {
			if (reviews.get(i).getIdRestaurantes()==idRestaurante) {
				//System.out.println(x);
				return reviews.get(i);
			}
		}
		return null;
		
	}
	//funciones para cargar los restaurantes
           
	public float searchCompatJaccard(int idRestaurant) {
		Review aux;
		if((aux=searchIdRestauranReview(idRestaurant))!=null){
			//System.out.println("Devolvio un flotante"+aux.getCompatJaccard());
			return aux.getCompatJaccard();
		}
		else{
		return 0;
	
		}
	}
	public float searchCompatInstersection(int idRestaurant) {
		Review aux;
		if((aux=searchIdRestauranReview(idRestaurant))!=null){
			//System.out.println("Devolvio un flotante"+aux.getCompatJaccard());
			return aux.getComentarios();
		}
		else{
		return 0;
	
		}
	}
	public int getQReview(){
		return reviews.size();
	}
}
