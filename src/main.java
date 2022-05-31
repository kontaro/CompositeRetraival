
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kontaro
 */

public class main {

    public static void main(String[] args) throws IOException{
    	
    	//excel
    	//FileWriter archivo = new FileWriter("datos.csv");
    	//String fila = new String();
    	
    	 /*   
        int funcion =Integer.parseInt(args[0]);//funcion a utilizar
        float ganma=Float.parseFloat(args[1]);//factor de peso 
        int presupuesto=Integer.parseInt(args[2]);//presupuesto maximo de un paquete
        int kpaquetes=Integer.parseInt(args[3]);//cantidad de paquetes
        int iter=Integer.parseInt(args[4]);//cantidad de iteraciones
        int vecindad=Integer.parseInt(args[5]);//tamaño de la vecindad

        //Se declaran variables 
         */ 
        int funcion=0;
        float ganma=(float)0;
        int presupuesto=0;
        int vecindad=200; // cantidad de vecinos a generar
        
        //de manera que se puedan correr varias pruebas a la vez
        float[] ganmas={(float) 0.5};
        int[] presupuestos= {50}; 
        int[] funcions= {1,2}; 
        int kpaquetes=10;//cantidad de paquetes
        int iter=10;
        int ciudad;
        long CHAC_Tiempo=20000;
        //long CHAC_Tiempo=20000;
        

        //eliminar esto
        int cargarLista=1;
        //long time=System.currentTimeMillis();
        //cargar lista de restaurantes para prueba
        listaRestaurantes listarestaurantesCompleta=new listaRestaurantes(cargarLista);
        long time=0;
        Cand cluster;
        
        /**
        archivo.append("Random");
        archivo.append(",");
        archivo.append("Ruleta");
        archivo.append(",");
        archivo.append("Torneo");
        archivo.append(",");
        archivo.append("Elitista");
        archivo.append(",");
        archivo.append("Local");
        archivo.append(",");
        archivo.append("VNS");
        archivo.append("\n");
        **/
        //List<List<String>> rows = new ArrayList<>();
        
        /*
    	PrintWriter salida = new PrintWriter("datos.csv");
    	String cadena = "Ciudad,Random,Ruleta,Torneo,Elitista,Local,VNS";
        salida.println(cadena);
        salida.flush();
        */
        
        //Se llama a los algoritmos
        float promRan=0, promE = 0, promRu = 0, promLoc = 0, promvns = 0, promTor = 0;
        float mejorRan = 0, mejorE = 0, mejorRu = 0, mejorLoc = 0, mejorvns = 0, mejorTor = 0;
        for(int q=0;q<funcions.length;q++) {
        	for(int w=0;w<presupuestos.length;w++) {
        		for(int r=0;r<ganmas.length;r++) {
        			funcion=funcions[q];
        			presupuesto=presupuestos[w];
        	        ganma=ganmas[r];
        	        Algorithm compositeAlgorithm=new Algorithm(vecindad,kpaquetes,ganma,presupuesto);
        	        MultiObjetiveAlgorithm multiAlgorithm=new MultiObjetiveAlgorithm(vecindad,kpaquetes,ganma,presupuesto);
        	 
        	       
        	        //System.out.println(System.currentTimeMillis()-time);
        	        //System.out.println(listarestaurantesCompleta.restaurantesMenores());
        	        ArrayList<Double> lista= new ArrayList<Double>();
        			
        	        //poner ciudad < 122
			        for(ciudad=1;ciudad<2;ciudad++){
			        
			        	System.out.println("ciudad: "+ ciudad);
			        	
			        	listaRestaurantes listaPrueba=new listaRestaurantes();
			            listaPrueba.cargarLista(listarestaurantesCompleta.restaurantesCiudad(ciudad));
			            //System.out.println("--------------------");
			            //long actualTime=System.currentTimeMillis();
			            
			            for(int i=0; i<iter;i++){//121 ciudades //76 2100 mas grande
			            	
			            	
			            	/*
			            	//System.out.println("Random: ");
			            	long millis = System.currentTimeMillis();
			            	//float genRan = compositeAlgorithm.geneticAlgorithm(ciudad, listaPrueba, 0.2f, 1, 0.6f).funcionFitness();
			            	System.out.println("genetico random "+(System.currentTimeMillis()- millis));
			        		millis = System.currentTimeMillis();
			            	//System.out.println("Elitista: ");
			        		float genEli = compositeAlgorithm.geneticAlgorithm(ciudad, listaPrueba, 0.2f, 2, 0.6f).funcionFitness();
			            	System.out.println("genetico elitista "+(System.currentTimeMillis()- millis));
			        		millis = System.currentTimeMillis();
			            	//System.out.println("Ruleta: ");
			            	//float genRul = compositeAlgorithm.geneticAlgorithm(ciudad, listaPrueba, 0.2f, 3, 0.6f).funcionFitness();
			            	System.out.println("genetico ruleta "+(System.currentTimeMillis()- millis));
			        		millis = System.currentTimeMillis();
			            	//System.out.println("Torneo: ");
			            	//float genTor = compositeAlgorithm.geneticAlgorithm(ciudad, listaPrueba, 0.2f, 4, 0.6f).funcionFitness();
			            	System.out.println("genetico torneo "+(System.currentTimeMillis()- millis));
			        		millis = System.currentTimeMillis();
			            	
			            	float local =compositeAlgorithm.LocalSearch(ciudad, listaPrueba).funcionFitness();
			  
			            	System.out.println("local "+(System.currentTimeMillis()- millis));
			        		millis = System.currentTimeMillis();
			            	float vns = compositeAlgorithm.VNS(ciudad, listaPrueba).funcionFitness();
			            	System.out.println("VNS "+(System.currentTimeMillis()- millis));
			            	//float random = compositeAlgorithm.RandomSearch(120, ciudad, listaPrueba).funcionFitness();
			                time=System.currentTimeMillis();
			                //actualTime=0;
			                //System.out.println(ciudad);
			                //System.out.println("Genetico: "+gen);
			               //System.out.println("Random: "+random);
			               //System.out.println("local Search: "+local);
			                
			                
			               //Arrays.asList(String.valueOf(genRan), String.valueOf(genRul), String.valueOf(genTor), String.valueOf(genEli) , String.valueOf(local) , String.valueOf(vns))
			               
			                
			                //rows.add(Arrays.asList(String.valueOf(local) , String.valueOf(vns)));
			         
			                //rows.add(Arrays.asList(genRan, genRul, genTor, genEli , local , vns);
			                
			                //rows.add(Arrays.asList(String.valueOf(genRan)+ "," + String.valueOf(genRul) + "," + String.valueOf(genTor) + "," + String.valueOf(genEli) + "," + String.valueOf(local) + "," + String.valueOf(vns))); 
			                //archivo.append("\n");
			                
			                cadena = String.valueOf(local) + "," + String.valueOf(vns); 
			                salida.println(cadena);
			                salida.flush();
			                
			                /**
			                
			                System.out.println(i+1);
			                System.out.println(local-genEli);
			                System.out.println(vns-genEli);
			                //System.out.println(random-gen);
			                
			               /*
			               promE = promE + genEli;
			                if(genEli> mejorE) {
			                	mejorE = genEli;
			                }
			               
			                
			                
			                promRan = promRan+ genRan;
			                if(genRan> mejorRan) {
			                	mejorRan = genRan;
			                }
			                
			                
			                promTor = promTor + genTor;
			                if(genTor > mejorTor) {
			                	mejorTor = genTor;
			                }
			                
			                
			                promRu = promRu + genRul;
			                if(genRul> mejorRu) {
			                	mejorRu = genRul;
			                }
			                
			                promLoc = promLoc + local;
			                if(local > mejorLoc) {
			                	mejorLoc = local;
			                }
			                
			                promvns = promvns +vns;
			                if(vns > mejorvns) {
			                	mejorvns = vns;
			                }
			                
			                **/
			            	if(funcion==1){
			                    System.out.println(compositeAlgorithm.RandomSearch(120, ciudad, listaPrueba).funcionFitness());
			                	}
			                if(funcion==2){
			                
			                    System.out.println(compositeAlgorithm.LocalSearch(ciudad, listaPrueba).funcionFitness());
			                    //System.out.println("----------------");
			                	//compositeAlgorithm.LocalSearch(ciudad, listaPrueba);
			                    //System.out.println((System.currentTimeMillis()-time));
			                }
			              /**  
			                if(funcion==51){
			                    System.out.println(compositeAlgorithm.LocalSearchFirstImprove1(ciudad, listaPrueba).funcionFitness());
			                	}
			               
			                if(funcion==52){
			                    System.out.println(compositeAlgorithm.LocalSearchFirstImprove(ciudad, listaPrueba).funcionFitness());
			                    //System.out.println("----------------");
			                	}
			                if(funcion==54){//ILS con primer movimiento
				                   System.out.println(compositeAlgorithm.ILSImprove(ciudad, listaPrueba, CHAC_Tiempo, 1).funcionFitness());

				                }
	
			                if(funcion==55){//ILS con primer movimiento
				                   System.out.println(compositeAlgorithm.ILSImprove(ciudad, listaPrueba, CHAC_Tiempo, 2).funcionFitness());

				                }

			                if(funcion==1){
			                    System.out.println(compositeAlgorithm.RandomSearch(120, ciudad, listaPrueba).funcionFitness());
			                	}
			                if(funcion==2){
			                
			                    System.out.println(compositeAlgorithm.LocalSearch(ciudad, listaPrueba).funcionFitness());
			                    //System.out.println("----------------");
			                	//compositeAlgorithm.LocalSearch(ciudad, listaPrueba);
			                    //System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==3){
			                    System.out.println(compositeAlgorithm.LocalSearchPaquete(ciudad, listaPrueba).funcionFitness());
			                    //System.out.println("----------------");
			                	//compositeAlgorithm.LocalSearchPaquete(ciudad, listaPrueba);
			                    //System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==4){//C-Hac
			                    //System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,1).costo);
			                	compositeAlgorithm.PAC(ciudad,listaPrueba,1);
			                	System.out.println((System.currentTimeMillis()-time));
			
			                }
			                if(funcion==5){//C-Hac inter-intra
			                    //System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,0).costo);
			                	compositeAlgorithm.PAC(ciudad,listaPrueba,0);
			                	System.out.println((System.currentTimeMillis()-time));
			
			                }
			                if(funcion==6){
			                	System.out.println(compositeAlgorithm.NewLocalSearch3(ciudad, listaPrueba).funcionFitness());
			                	//compositeAlgorithm.NewLocalSearch3(ciudad, listaPrueba);
			                	//System.out.println((System.currentTimeMillis()-time));
			
			                }
			                if(funcion==7){//WPR
			                	//System.out.println(compositeAlgorithm.StartLocalSearch(ciudad, listaPrueba, 1).funcionFitness());
			                	compositeAlgorithm.StartLocalSearch(ciudad, listaPrueba, 1);
			                	System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==8){//WP
			                	System.out.println(compositeAlgorithm.StartLocalSearch(ciudad, listaPrueba, 2).funcionFitness());
			                	//compositeAlgorithm.StartLocalSearch(ciudad, listaPrueba, 2);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }

			                if(funcion==9){//ILS con primer movimiento
			                   System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 1).funcionFitness());
			                	//compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 1);
			                   System.out.println("");
			                   //System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==10){//ILS con segundo Movimiento
			                   System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 2).funcionFitness());
			                	//compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 2);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==11){//ILS con StartpointLocalSearch random
			                	System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 3).funcionFitness());
			                	//compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 3);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==12){//ILS con StartpointLocalSearch inteligente
			              
			                	System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 4).funcionFitness());
			                	//compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 4);
			                	//System.out.println((System.currentTimeMillis()-time));
			
			                }
			                if(funcion==13){
			                	//System.out.println(compositeAlgorithm.PAC_aux(ciudad,listaPrueba,0));
			
			                }
			                if(funcion==14){//FlyAlgorithm
			                	System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 5).funcionFitness());
			                }
			                if(funcion==15){//C-IP
			                	//System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,0).funcionFitness());
			                	//System.out.println(compositeAlgorithm.PAC_IP(ciudad,listaPrueba,0).funcionFitness());
			                }
			                
			                //----------------Algoritmos Multi-objetivo
			                
			
			                //----------------Otros
			                if(funcion==16){
			                	System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,1).funcionFitness());
			                	System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,0).funcionFitness());
			                	System.out.println(compositeAlgorithm.LocalSearch(ciudad, listaPrueba).funcionFitness());
			                	System.out.println(compositeAlgorithm.StartLocalSearch(ciudad, listaPrueba, 2).funcionFitness());
			
			                    
			                }
			                if(funcion==17){
			                	System.out.println(compositeAlgorithm.NewLocalSearch(ciudad, listaPrueba).funcionFitness());
			                	//compositeAlgorithm.NewLocalSearch(ciudad, listaPrueba);
			                	//actualTime=System.currentTimeMillis()-time;
			                    //System.out.println(actualTime );
			                }
			                if(funcion==18){
			                	//System.out.println(compositeAlgorithm.NewLocalSearch2(ciudad, listaPrueba).funcionFitness());
			                	//compositeAlgorithm.NewLocalSearch2(ciudad, listaPrueba);
			                	//actualTime=System.currentTimeMillis()-time;
			                    //System.out.println(actualTime );
			                }
			                if(funcion==19){
			                	System.out.println(compositeAlgorithm.NewLocalSearch3(ciudad, listaPrueba).funcionFitness());
			                	
			                	//compositeAlgorithm.NewLocalSearch3(ciudad, listaPrueba);
			                	//actualTime=System.currentTimeMillis()-time;
			                    //System.out.println(actualTime );
			                }
			                if(funcion==20){
			                	System.out.println(compositeAlgorithm.VNS(ciudad, listaPrueba).funcionFitness());
			                	//System.out.println((System.currentTimeMillis()-time));
			 
			                }
			                if(funcion==21){
			                	System.out.println(compositeAlgorithm.ILS(ciudad, listaPrueba, CHAC_Tiempo, 6).funcionFitness());
			                }
			                
			                if(funcion==22){//C-Hac
			                    System.out.println(compositeAlgorithm.PAC(ciudad,listaPrueba,1).costo);
			                	//compositeAlgorithm.VNS(ciudad, listaPrueba);
			                	System.out.println((System.currentTimeMillis()-time));
			
			                }
			                if(funcion==23) {
			                	System.out.println(compositeAlgorithm.newVNS(ciudad, listaPrueba,30,25,0,2000).costo);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==24) {
			                	System.out.println(compositeAlgorithm.newVNS(ciudad, listaPrueba,30,25,1,2000).costo);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==25) {
			                	System.out.println(compositeAlgorithm.IVNS(ciudad, listaPrueba,CHAC_Tiempo,1).costo);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                if(funcion==26) {
			                	System.out.println(compositeAlgorithm.IVNS(ciudad, listaPrueba,CHAC_Tiempo,2).costo);
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			                
			                if(funcion==30){
			                	for(float j=(float)0.1;j<1;j=j+(float)0.1) {
			                		compositeAlgorithm.alfa=j;
			                		System.out.println(compositeAlgorithm.LocalSearch(ciudad, listaPrueba).datosFitness());
			                	}
			
			                
			                }
			                if(funcion==31) {
			                	multiAlgorithm.epsilon = (float)0.0;
			                	multiAlgorithm.ParetoLocalSearch(ciudad, listaPrueba);
			                	//multiAlgorithm.printFront(multiAlgorithm.ParetoLocalSearch(ciudad, listaPrueba));
			                	//System.out.println((System.currentTimeMillis()-time));
			                }
			            	**/
			            }
			            
			        }
        
        		}
        	}
        }
        /**
        for (List<String> rowData : rows) {
            archivo.append(String.join(",", rowData));
            archivo.append("\n");
        }
     	**/
        //salida.flush();
        
        /*
        salida.close();
        
        System.out.println("Random promedio: "+ promRan/30);
        System.out.println("Random mejor: "+mejorRan);
        
        System.out.println("Elitista promedio: "+ promE/30);
        System.out.println("Elitista mejor: "+mejorE);
        
        System.out.println("Ruleta promedio: "+ promRu/30);
        System.out.println("Ruleta mejor: "+mejorRu);
        
        System.out.println("Torneo promedio: "+ promTor/30);
        System.out.println("Torneo mejor: "+mejorTor);
        
        System.out.println("Local promedio: "+ promLoc/30);
        System.out.println("Local mejor: "+mejorLoc);
        
        System.out.println("VNS promedio: "+ promvns/30);
        System.out.println("VNS mejor: "+mejorvns);
        */
        
    }
       
        //imprimirSolucion(lista);
}
