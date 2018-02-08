import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
class Arreglo{
	static Integer[] arreglo = null;
	static int nMaxDigitos = 0;
	static void lecturaDatos(String archivo){
		try {
			String line = null;
			FileReader fileReader = new FileReader(archivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int contador = 0;
			while((line = bufferedReader.readLine()) != null) {
				if (contador == 0){
					arreglo = new Integer[Integer.parseInt(line)];
				}else if(contador > 0){
					arreglo[contador-1] = Integer.parseInt(line);
					if (nMaxDigitos < line.length()){
						nMaxDigitos = line.length();
					}
				}
				contador++;
			}
			bufferedReader.close();
		}catch (FileNotFoundException ex) {
			System.out.println(
			"No se pudo leer el archivo '" + archivo + "'");
		}catch(IOException ex) {
			System.out.println("No se pudo leer el archivo '" + archivo + "'");    
		}              
	}
	// Imprime el arreglo
	static void imprimeArreglo(){
		try{
			System.out.println("Arreglo: " + Arrays.toString(arreglo));
		}catch(NullPointerException arreglo){
			System.out.println("El arreglo esta vacio");
		}
	}
	static void heapSort(){// Ordena los elemento del arreglo de la clase usando el algoritmo Heap Sort. 
		
	}	
	static void radixSort(){// Ordena los elemento del arreglo de la clase usando el algoritmo radixSort
			ArrayList<ArrayList<Integer>> bucket = new ArrayList<ArrayList<Integer>>();//ArrayList bidimensional para usar como cubeta
			ArrayList<Integer> resultado = new ArrayList<Integer>();//ArrayList para asignar los valores de bucket en orden.
			double temporal;
			int t;
			for (int i = 1; i <= nMaxDigitos; i++) {//Ciclo que va desde 1 hasta el numero de digitos mas grande.
				temporal = Math.pow(10.0, (double)i-1);//operacion para separar el digito dependiendo de la iteracion en curso.
				for (int j = 0; j <= arreglo.length - 1; j++) {//Ciclo que va desde 0 hasta el ultimo elemento del arreglo para crear
					bucket.add(new ArrayList<Integer>());
				}
				for (int j = 0; j < arreglo.length; j++) {
					t = (int)((arreglo[j]/temporal)%10);
					bucket.get(t).add(new Integer(arreglo[j]));
				}
				for (int j = 0; j < arreglo.length; j++) {
					for (int w = 0; w <= bucket.get(j).size() -1; w++) {
						resultado.add(bucket.get(j).get(w));
					}
				}
				arreglo = resultado.toArray(arreglo);
				resultado.clear();
				bucket.clear();
			}
			
		}
	
	public static void main(String[] args){
		lecturaDatos("archivo.txt");
		radixSort();
		imprimeArreglo();
		
	}
}