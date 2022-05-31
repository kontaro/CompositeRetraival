import java.util.ArrayList;

public class Convex {
	/**
	 * 
	 */
	ArrayList<Solucion> convex;
	ArrayList<Solucion> paretoFront;
	boolean max=false;
	/**
	 * Input: frente de pareto ordenado por un eje
	 * @param paretoFront
	 */
	public Convex() {
		convex=new ArrayList<Solucion>();
		paretoFront=new ArrayList<Solucion>();
	}
	
	//inter function
	private float pendiente(Solucion p1,Solucion p2) {
		float m;
		m=(p2.intra()-p1.intra()/(p2.inter()-p1.inter()));
		return m;
	}
	private void cleanList() {
		convex.clear();
	}
	
	//extern functions
	
	public void setParetoFront(ArrayList<Solucion> listPoints) {
		paretoFront.clear();
		paretoFront.addAll(listPoints);
		
	}
	public ArrayList<Solucion> convexHalf(ArrayList<Solucion> listPoints, boolean guide){
		setParetoFront(listPoints);
		return convex;
	}
	
	
}
