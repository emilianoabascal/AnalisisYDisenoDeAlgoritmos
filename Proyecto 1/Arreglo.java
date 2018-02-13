//Por Emiliano Abascal Gurria A01023234
//API Por Victor Manuel de la Cueva
//
//Colaboracion sobre el funcinamiento de Heap Sort: Victor Manuel de la Cueva y Enrique Lira Martinez.
//El programa fue probado con un archivo con 10 millones de numeros enteros al azar, el documento esta anexado con la clase Arreglo y tiene el titulo millonDeNumeros.txt y no requiere que se le pase ningún valor al constructor, solamente el nombre del archivo al método leerArchivo(Nombre).
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
class Arreglo{
	//Declaracion de variables necesarias para el funcionamiento correcto del programa.
	private Integer[] arreglo;
	private int nMaxDigitos;
	private static int tamano;
	private static int iz;
	private static int der;
	private static int masGrande;
	public void lecturaDatos(String archivo){//Funcion para leer un archivo e introducirlo a un arreglo.
		try {
			String line = null;
			FileReader fileReader = new FileReader(archivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int contador = 0;
			/*Se declara una instancia de FileReader para poder acceder a un archivo, asimismo una instancia de BufferedReader que obtendra la informacion de este. Se declara un contador que nos servira para identificar el primer valor del archivo, el cual sera el tamanio del arreglo de tipo Integer.*/
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
			/*Mientras no sea el final del archivo, entonces se asignara el tamanio del arreglo y los valores que obtendra este.*/
			bufferedReader.close();
			fileReader.close();
			/*Se cierran los lectores del archvo*/
		}catch (FileNotFoundException ex) {
			System.out.println("El archivo '" + archivo + "' no se encuentra en el directorio.");
		}catch(IOException ex) {
			System.out.println("No se pudo leer el archivo '" + archivo + "'.");    
		}
		/*Si no se encuentra el archivo o no se puede leer, entonces se muestra mensaje indicando la situacion*/
	}
	public void imprimeArreglo(){// Imprime el arreglo
		try{
			System.out.println("Arreglo: " + Arrays.toString(arreglo));
			
		}catch(NullPointerException arreglo){
			System.out.println("El arreglo esta vacio");
		}
		//Se imprime el arreglo, si este no tiene elementos, se mandara un mensaje de error.
	}
	
	
	public void heapSort(){
		//Se Crea el heap
		tamano = arreglo.length - 1;
		for(int i = tamano/2; i >= 0; i--){
			maxHeap(i);
		}//Fin de creacion de heap
		/*Se empiezan a acomodar los valores que se encuentran dentro del heap, utilizando las funciones cambio(int, int) y maxHeap(int).*/
		for(int i = tamano; i > 0; i--){
			cambio(0, i);
			tamano = tamano - 1;
			maxHeap(0);
		}
		
	}
	public void cambio(int i, int j){/*Metodo Cambio para hacer cambio de lugar de los valores.*/
		int t = arreglo[i];
		arreglo[i] = arreglo[j];
		arreglo[j] = t; 
	}
	public void maxHeap(int i){/*Metodo para reacomodar a cada hijo con su respectivo padre.*/
		iz = 2 * i;
		der = 2 * i + 1;
		if(iz <= tamano && arreglo[iz] > arreglo[i]){//Si el valor del hijo izquierdo es menor o igual al tamanio y el arreglo en la posicion del hijo izquierdo es mayor al arreglo en i, entonces iz sera el valor mas grande, sino el masGrande sera i.
			masGrande = iz;
		}else{
			masGrande = i;
		}
		
		if(der <= tamano && arreglo[der] > arreglo[masGrande]){//Si el valor del hijo derecho es menor o igual al tamanio y el arreglo en la posicion del hijo derecho es mayor al arreglo en masGrande, entonces der sera el valor masGrande.
			masGrande = der;
		}
		if(masGrande != i){//Si mas grande es diferente a i se llamara a la funcion cambio, pasandole los valores de i y mas grande, cambiandolos de lugar y se llamara recursivamente a maxHeap pasandole el valor de masGrande.
			cambio(i, masGrande);
			maxHeap(masGrande);
		}
	}

			
	public void radixSort(){// Ordena los elemento del arreglo de la clase usando el algoritmo radixSort
			ArrayList<ArrayList<Integer>> bucket = new ArrayList<ArrayList<Integer>>();//ArrayList bidimensional para usar como cubeta
			ArrayList<Integer> resultado = new ArrayList<Integer>();//ArrayList para asignar los valores de bucket en orden.
			double temporal;
			int t;
			for (int i = 1; i <= nMaxDigitos; i++) {//Ciclo que va desde 1 hasta el numero de digitos mas grande para poder sacar el digito dependiendo de la iteracion i.
				temporal = Math.pow(10.0, (double)i-1);
				for (int j = 0; j <= 9; j++) {//Ciclo que va desde 0 hasta el ultimo elemento del arreglo para crear nuevos ArrayList de tipo Integer en bucket, para tener una cubeta que valla del 0 al 9.
					bucket.add(new ArrayList<Integer>());
				}
				for (int j = 0; j < arreglo.length; j++) {//Ciclo que va desde 0 al tamano del arreglo, que saca el indice dependiendo de cual sea el digito que se saco, para despues insertar en la cubeta el numero completo que se encuentra en arreglo.
					t = (int)((arreglo[j]/temporal)%10);
					bucket.get(t).add(new Integer(arreglo[j]));
				}
				for (int j = 0; j <= 9; j++) {//Ciclo que va desde 0 hasta el 9 para iterar entre los valores bidimensionales de bucket y poderlos asignar al ArrayList resultado.
					for (int w = 0; w <= bucket.get(j).size() -1; w++) {
						resultado.add(bucket.get(j).get(w));
					}
				}
				arreglo = resultado.toArray(arreglo);//Se asigna el valor de resultado al arreglo original y se limpian los valores de resultado y bucket.
				resultado.clear();
				bucket.clear();
			}
		}
}