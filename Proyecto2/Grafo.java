//Emiliano Abascal Gurría A01023234
//Apoyo y asesoría por Enrique Lira Martinez y Daniela Flores Javier


import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Scanner;


class Grafo {
	Grafo(){}
	static int E,V,count;//Declaracion del numero de vertices y de edges, asimismo como un contador, un arreglo de edges, una lista de listas de vertices y un heap.
	static Edge edge[]; 
	static ArrayList<ArrayList<int[]>> vertices;
	boolean[] explorado;
	Heap noExplorado;
	static float total;
	Grafo(int v, int e){//Constructor de la clase Grafo, en la cual se le asigna el tamaño de los vertices y de los edges, asi como la inicializacion de la variable edge con un tamaño de el numero de Edges.
			V = v;
			E = e;
				
			edge = new Edge[E];
			for (int i=0; i<e; ++i)
				edge[i] = new Edge();
		}
	
	static class Edge implements Comparable<Edge>{//Declaracion de clase Edge para representar la union entre los nodos, entre sus atributos estan el nodo de origen, el nodo de destino, y el peso.
		int src, dest;
		boolean visited = false;
		Float weight;
		public int compareTo(Edge compareEdge){
			if (weight<compareEdge.weight)return -1;
			else if (weight>compareEdge.weight)return +1;
			else return 0;
		}
	};
	
	static class subConjunto{//clase para crear un nuevo sub-grafo y asignarle un rango y su nodo padre
		int padre, rango;
	};

	
	
	
	static Float kruskalDFS(String archivo)throws FileNotFoundException{//Profesor, ya había acabado mi implementacion de kruskal con DFS, pero no funcionaba con todos los casos, lo volvi a empezar pero el tiempo se me acabo, le pido una disculpa pero aun asi le dejo hasta donde me quede:
//			Edge result[] = new Edge[E];
//			List<Integer> visited = new ArrayList<Integer>();
//			float total = 0;
//			int e = 0;  
//			int i = 0;  
//			for (i=0; i<E; ++i){
//				result[i] = new Edge();
//			}
//			Arrays.sort(edge);
//
//			i = 0;
//			edge[0].visited = true;
//			visited.add(edge[0].dest);
//			result[0] = edge[0];
//			while (e < V-1) {
//				Edge next_edge = new Edge();
//				next_edge = edge[i + 1];
//				Integer[] stockArr = new Integer[visited.size()];
//				stockArr = visited.toArray(stockArr);
//				if(next_edge.visited == false && search(stockArr, next_edge.dest) == false){
//					next_edge.visited = true;
//					result[i] = next_edge;
//					visited.add(next_edge.dest);
//				}
//				e++;
//				i++;
//				
//			}
//			for (i = 0; i <= result.length-1; i++) {
//				System.out.println(result[i].src + "	" + result[i].dest + "	" + result[i].weight);
//				total = total + result[i].weight;
//			}
//			
//			System.out.println(total);
//			return total;
		return kruskalUF("P2Edges.txt");//Llama a Kruskal pero con Union Find
	}

	
	
	static Float kruskalUF(String archivo)throws FileNotFoundException{//Implementacion de KruskalUF
			Scanner in = new Scanner(new File(archivo));//Se lee el archivo y se asignan el numero de vertices y de aristas que hay en el grafo, se crea un nuevo grafo con estos tamaños y se asigna cada nodo a cada edge del grafo.
			V = in.nextInt();
			E = in.nextInt();
			Grafo g = new Grafo(V, E);
			for (int i = 0; i < E; i++) {
				g.edge[i].src = in.nextInt();
				g.edge[i].dest = in.nextInt();	
				g.edge[i].weight = in.nextFloat();
			}
			in.close();//Se cierra el archivo y se llama a la funcion KruskalMST
			return g.KruskalMST();
		}
	
	static Float KruskalMST(){//Funcion para hacer el MST del grafo utilizando kruskal con union find.
		long startTime = System.currentTimeMillis();//se guarda el tiempo en el que se empieza a correr la funcion y se asignan valores a los iteradores "e" e "i".
		Edge result[] = new Edge[E];  
		int e = 0;  
		int i = 0;  
		for (i = 0; i < E; ++i){//Se le asigna un edge a la variable resultado por cada edge que haya en el grafo.
			result[i] = new Edge();
		}
		Arrays.sort(edge);//Se ordena el arreglo edge en relacion a los pesos, de menor a mayor y se instancía un subconjunto que va a tener el tamaño de la cantidad de los edges del grafo.
 
		subConjunto subConjuntoTemporal[] = new subConjunto[E];
		for(i = 0; i <E; ++i){
			subConjuntoTemporal[i] = new subConjunto();
		}
 
		for (int v = 0; v < V; ++v){//Para cada vertice se le asigna un rango y un padre a cada subconjunto.
			subConjuntoTemporal[v].rango = 0;
			subConjuntoTemporal[v].padre = v;
		}
		i = 0;   
		while (e < V - 1){//Mientras no se hayan recorrido todos los vertices entonces se crea un edge siguiente, despues se llama a la funcion find dos veces con el subconjunto que creamos antes y el origen del siguiente nodo para la primera llamada y el destino para la segunda, estos valores se guardan en variables "x" y "y".
			Edge next_edge = new Edge();
			next_edge = edge[i++];
			int x = find(subConjuntoTemporal, next_edge.src);
			int y = find(subConjuntoTemporal, next_edge.dest);
 		
			if (x != y){//Si x es diferente que y entonces el resultado en la posicion e + 1 sera el siguiente edge, y se llama a la funcion union con el subconjunto, "x" y "y".
				result[e++] = next_edge;
				union(subConjuntoTemporal, x, y);
			}
			
		}
		System.out.println("El MST Resultante obtenido con Kruskal es el siguiente:");//Se imprime cada uno de las conexiones resultantes asi como su peso total y el tiempo de ejecucion.
		float total = 0;
		for (i = 0; i < e; ++i){
		total=total+result[i].weight;
			System.out.println("("+result[i].src+", " + 
				   result[i].dest+", " + result[i].weight + ")");
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(" ");
		System.out.println("Tiempo de ejecucion: "+totalTime+" milisegundos");
		System.out.println("Costo Total: "+total);
		return total;
	}
	
	
	
	static int find(subConjunto subConjuntoTemporal[], int i){//Funcion find la cual se llama desde kruskalMST
			if (subConjuntoTemporal[i].padre != i){//Si el padre que se encuentra en el subconjunto es diferente que el valor de i entonces se llama recursivamente la funcion asi misma pero con el padre en el posicion de la i.
				subConjuntoTemporal[i].padre = find(subConjuntoTemporal, subConjuntoTemporal[i].padre);
			}//Sino se regresa el padre que se encuentra en el subconjunto.
			return subConjuntoTemporal[i].padre;
		}
		
	static void union(subConjunto subConjuntoTemporal[], int x, int y){//Funcion union que se llama desde kruskalMST.
		int xr = find(subConjuntoTemporal, x);//Se llama a la funcion find con el subconjunto y el valor de x y despues con el valor de y, y se guardan sus valores.
		int yr = find(subConjuntoTemporal, y);
		if (xr == yr){//Si los valores anteriores son iguales, entonces no se hace nada, si el rango en el subconjunto en la posicion de xr es menor que el rango en el subconjunto en la posicion de yr, entonces el padre en la posicion xr sera yr. Sino si el rango en el subconjunto en la posicion de xr es mayor que el rango en el subconjunto en la posicion de yr entonces el padre en la posicion yr sera xr. Sino el padre en el subconjunto en la posicion yr sera xr y el rango en el subconjunto en la posicion xr sera el rango + 1.
			return;
		}else if(subConjuntoTemporal[xr].rango < subConjuntoTemporal[yr].rango){
			subConjuntoTemporal[xr].padre = yr;
		}else if(subConjuntoTemporal[xr].rango > subConjuntoTemporal[yr].rango){
			subConjuntoTemporal[yr].padre = xr;
		}else{
			subConjuntoTemporal[yr].padre = xr;
			subConjuntoTemporal[xr].rango++;
		}
	} 
	
	static float prim(String archivo)throws FileNotFoundException{//La implementacion de prim
			Grafo g = new Grafo();//Se lee el archivo y se asignan el numero de vertices y de aristas que hay en el grafo y se asignan los valores para poder llevar a cabo el algoritmo.
			long startTime = System.currentTimeMillis();
			Scanner in = new Scanner(new File(archivo));
			V = in.nextInt();
			E = in.nextInt();
			vertices = new ArrayList<ArrayList<int[]>>();
			for (int i = 0; i < V; i++) {
				vertices.add(new ArrayList<int[]>());
			}
			for (int i = 0; i < E; i++) {
				int u = in.nextInt();
				int v = in.nextInt();
				int cost = in.nextInt();
				vertices.get(u - 1).add(new int[]{v, cost});
				vertices.get(v - 1).add(new int[]{u, cost});
			}
			
			in.close();//Se cierra el archivo y se llama la funcion computeMST, la cual su resultado se guardara.
			float res = g.computeMST();
			long endTime  = System.currentTimeMillis();//Se imprime el tiempo de ejecucion, las conexiones resultantes y el costo total.
			long totalTime = endTime - startTime;
			System.out.println("El tiempo de ejecucion fue de: " + totalTime + " milisegundos");
			System.out.println("El costo total es de: "+res);
			return res;//Se regresa el costo total.
		}
		public int computeMST(){//La implementacion de computeMST.
			explorado = new boolean[V];//Se inicializa la variable explorado con el numero de vertices que tiene el grafo y el primer valor se asigna como explorado. Se hace un nuevo heap para los no explorados.
			explorado[0] = true;
			noExplorado = new Heap();
			int sumCost = 0;
			for (int i = 1; i < V; i++) {//Para cada i hasta que i sea menor que el numero de vertices, el costo minimo sera de 1000000000.
				int minCost = 1000000000;
				for (int[] edge : vertices.get(i)) {//Para cada edge que hay en los vertices en la posicion i, si el primer edge es igual a 1 y el edge en la posicion siguiente es menor que el costo minimo, entonces el costo minimo es el edge en la posicion 1.
					if (edge[0] == 1 && edge[1] < minCost) {
						minCost = edge[1];
					}
				}
				noExplorado.add(new int[]{i + 1, minCost});//Se agrega a no explorado i + 1 y el valor del costo minimo.
			}
			for (int i = 1; i < V; i++){//Para cada i que empieza en 1, hasta el numero de vertices en el grafo, entonces se explora cada edge que esta en los vertices, si el costo es menot que el no explorado con el costo minimo en el vertice inicial, entonces el no explorado sera el vertice inicial con el costo que se comparo.
				int v = 0;
				int[] u = noExplorado.poll();
				for (int[] edge : vertices.get(u[0] - 1)){
					v = edge[0];
					if (explorado[v - 1] == false){
						int cost = edge[1];
						if (cost < noExplorado.costoMinimo(v)) {
							noExplorado.update(new int[]{v, cost});
						}
					}
				}
				System.out.println("("+u[0]+", "+v+", "+u[1]+")");//Se imprime cada conexion, se marca el nodo actual como exporado, y se acumulan los valores del peso.
				explorado[u[0] - 1] = true;
				sumCost = sumCost + u[1];
			}
			return sumCost;//Se regresa el valor de la suma total del costo.
		}
	
	class Heap {//Los comentarios sobre la funcionalidad general de un monticulo ya se habian analizado por lo tanto no se incluiran.
		private ArrayList<int[]> heap;
		HashMap<Integer, Integer> iMap;	
		public Heap() {
			heap = new ArrayList<int[]>();
			iMap = new HashMap<Integer, Integer>();
		}
		public void add(int[] a) {
			int n = heap.size() - 1;
			heap.add(a);
			n++;
			while (n > 0 && heap.get((n-1)/2)[1] > heap.get(n)[1]) {
				Collections.swap(heap, (n-1)/2, n);
				int[] b = heap.get(n);
				iMap.remove(b[0]);
				iMap.put(b[0], n);
				n = (n-1)/2;
			}
			iMap.put(a[0], n);
		}

		public int[] poll() {
			if (heap.size() == 0) {
				return (int[]) null;
			}
			int[] smallest = heap.get(0);
			int n = heap.size() - 1;
			int i = 0;
			Collections.swap(heap, 0, n);
			heap.remove(n);
			iMap.remove(smallest[0]);
			if (heap.size() > 0) {
				iMap.remove(heap.get(0)[0]);
				iMap.put(heap.get(0)[0], 0);
			}
			n--;
			boolean balanced = false;
			while (!balanced) {
				if (i * 2 + 1 > n) {
					balanced = true;
				}else if (i * 2 + 1 == n) {
					if (heap.get(i)[1] > heap.get(i * 2 + 1)[1]) {
						Collections.swap(heap, i, 2 * i + 1);
						iMap.remove(heap.get(i)[0]);
						iMap.remove(heap.get(i * 2 + 1)[0]);
						iMap.put(heap.get(i)[0], i);
						iMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
					}
					balanced = true;
				}else{
					if (heap.get(i)[1] <= heap.get(i * 2 + 1)[1] && heap.get(i)[1] <= heap.get(i * 2 + 2)[1]) {
						balanced = true;
					} else {
						if (heap.get(i * 2 + 1)[1] < heap.get(i * 2 + 2)[1]) {
							Collections.swap(heap, i, i * 2 + 1);
							iMap.remove(heap.get(i)[0]);
							iMap.remove(heap.get(i * 2 + 1)[0]);
							iMap.put(heap.get(i)[0], i);
							iMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
							i = i * 2 + 1;
						} else {
							Collections.swap(heap, i, i * 2 + 2);
							iMap.remove(heap.get(i)[0]);
							iMap.remove(heap.get(i * 2 + 2)[0]);
							iMap.put(heap.get(i)[0], i);
							iMap.put(heap.get(i * 2 + 2)[0], i * 2 + 2);
							i = i * 2 + 2;
						}
					}
				}
			}
			return smallest;
		}
		public boolean update(int[] verConCosto) {
			boolean actual = false;
			if (iMap.containsKey(verConCosto[0])) {
				int index = iMap.get(verConCosto[0]);
				heap.set(index, verConCosto);
				while (index > 0 && heap.get((index-1)/2)[1] > heap.get(index)[1]) {
					Collections.swap(heap, (index-1)/2, index);
					int[] b = heap.get(index);
					iMap.remove(b[0]);
					iMap.put(b[0], index);
					index = (index-1)/2;
				}
				iMap.remove(verConCosto[0]);
				iMap.put(verConCosto[0], index);
				actual = true;
			}
			return actual;
		}

		public int costoMinimo(int ver) {
			int index = iMap.get(ver);
			return heap.get(index)[1];
		}
		public int size() {
			return heap.size();
		}
	}
	public static void main (String[] args) throws Exception{
	 		kruskalUF("P2Edges.txt");
			kruskalDFS("P2Edges.txt");
			prim("P2Edges.txt");
		}
}

