import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Excel {
	Workbook libro;
	Sheet hoja;
	Row fila;
	int filaActual;

	public Excel(Workbook libro) {
		super();
		this.libro = libro;
	}
	
	public Excel() {
		libro = new XSSFWorkbook();
		filaActual = -1;
	}
	
	
	public void GuardarExcel(String nombre) {
		FileOutputStream outputStream;
		File miDir = new File (".");
		String archivo = miDir.getAbsolutePath();
		archivo = archivo.substring(0, archivo.length()-1)+ nombre;
		try {
		    outputStream = new FileOutputStream(archivo);
		    libro.write(outputStream);
		    libro.close();
		    System.out.println("Libro guardado correctamente");
		} catch (FileNotFoundException ex) {
		    System.out.println("Error de filenotfound");
		} catch (IOException ex) {
		    System.out.println("Error de IOException");
		}
		
	}
	
	public void crearHoja(String nombre) {
		filaActual = -1;
		hoja = libro.createSheet(nombre);
	}
	
	public void escribirFila(ArrayList<String> escribir) {
		filaActual++;
		fila = hoja.createRow(filaActual);
		
		for(int i = 0; i < escribir.size(); i++) {
			String palabra = escribir.get(i);
			Cell celda = fila.createCell(i);
			celda.setCellValue(palabra);
		}
	}
	
	public void escribirFila(String[] escribir) {
		filaActual++;
		fila = hoja.createRow(filaActual);
		
		for(int i = 0; i < escribir.length; i++) {
			String palabra = escribir[i];
			Cell celda = fila.createCell(i);
			celda.setCellValue(palabra);
		}
	}
	
	
}
