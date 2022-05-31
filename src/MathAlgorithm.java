import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import gurobi.*;
public class MathAlgorithm extends Algorithm {

	public MathAlgorithm(int iteracionesVecinos, int kpaquetes, float alfa, int presupuesto) {
		super(iteracionesVecinos, kpaquetes, alfa, presupuesto);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/***
	 * Algoritmo basado en el local searhc con busqueda exhaustiva, donde 
	 * la busqueda exhaustiva se le pasa a un modelo ip
	 */
    public Solucion LocalSearch(int ciudad,listaRestaurantes listaPrueba){
        Solucion best=new Solucion(kpaquetes,listaPrueba,alfa,ciudad,presupuesto);
        //System.out.println("entro a local");
        boolean flag=true;
      
        //System.out.println(best.funcionFitness());
        while(flag){
        	
            ArrayList<Solucion> vecinos = new ArrayList<Solucion>();
            Solucion mejorVecino =new Solucion(ciudad,alfa,presupuesto);
            mejorVecino.costo = 0;
            for(int j=0;j<iteracionesVecinos;j++){

                        Solucion vecino =new Solucion(ciudad,alfa,presupuesto);
                        vecino.update(best);
                        //Esta funcion se debe cambiar
                        vecino.cambiarRestauranteExhaustivo(listaPrueba);
                        
                        if(vecino.funcionFitness()>mejorVecino.funcionFitness()){
                            mejorVecino.update(vecino);  
                        }
            }
            //
            if(mejorVecino.funcionFitness() > best.funcionFitness()){
            	//System.out.println("encontro algo mejor");
            	//System.out.println(mejorVecino.funcionFitness());
            	best.update(mejorVecino);

            }else{
                flag=false;
            }
        }
        
        return best;
    }

     
	    //--------------Algoritmos Multi-objetivos
     //public Solucion C
     public int RunIP(int kpaquetes) throws IOException {
     	String comando="./selpaq Intra.txt Inters.txt "+kpaquetes;
     	Process p = Runtime.getRuntime().exec(comando);
     	return 0;
     }
}
