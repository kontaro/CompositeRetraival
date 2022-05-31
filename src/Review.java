

public class Review {

	private float comentario;
	private float compatJaccard; //valor de similitud
	private int idRestaurante;
	
	public Review(int idRestaurante, float compatJaccard, float comentario) {
		// TODO Auto-generated constructor stub
		this.comentario=comentario;
		this.compatJaccard=compatJaccard;
		this.idRestaurante=idRestaurante;
		
	
	}
	public Review(int idRestaurante, float compatJaccard) {
		// TODO Auto-generated constructor stub

		this.compatJaccard=compatJaccard;
		this.idRestaurante=idRestaurante;
		
	
	}
	public float getComentarios() {
		return comentario;
	}

	public void setComentarios(float comentarios) {
		this.comentario = comentarios;
	}

	public float getCompatJaccard() {
		return compatJaccard;
	}

	public void setCompatJaccard(float compatJaccard) {
		this.compatJaccard = compatJaccard;
	}

	public int getIdRestaurantes() {
		return idRestaurante;
	}

	public void setIdRestaurantes(int idRestaurantes) {
		this.idRestaurante = idRestaurantes;
	}


	
	
	

}
