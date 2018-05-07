import java.util.*;
import java.io.*;
import java.lang.*;

public class TSP {
	
	private final int N;
	private final int START_NODE;
	private final int FINISHED_estadoInicial;
	public static int pesoFB[][];
	public static int n;
	public static int camino[];
	public static int cost;
	public static int xValor[];
	public static int yValor[];
	public static double[][] aryNumbers;
	public static double[][] costos;
	private double[][] distancia;
	private double costoMinimoDelRecorrido = Double.POSITIVE_INFINITY;
	private List<Integer> recorrido = new ArrayList<>();
	private boolean correSolucion = false;
	
	public static void TSPDP(String archivo)throws FileNotFoundException{//Funcion para leer un archivo e introducirlo a un arreglo.
		int V, E;
		double x1,y1,x2,y2,x,y;
		Scanner in = new Scanner(new File(archivo));
		V = in.nextInt();//agremamos la primera letra 
		aryNumbers = new double[V][2];
		costos = new double[V][V];
		for (int i = 0; i < V; i++){ //Se llena el grafo
			for (int j = 0; j < 2; j++){
				aryNumbers[i][j] = in.nextDouble();
			}
		}
		for (int i = 0; i < V; i++){
			x1 = aryNumbers[i][0];
			y1 = aryNumbers[i][1];
			for (int j = 0; j < V; j++){
				x2 = aryNumbers[j][0];
				y2 = aryNumbers[j][1];
				x =	x2-x1;	
				y =	y2-y1;	
				costos[i][j]=Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));		
					
			}
		}	
		//Se utiliza para imprimir los valores de la solucion.
		int nodoInicial = 0;		
		TSP solucion = new TSP(nodoInicial, costos);
		System.out.println("Usando Programacion Dinamica:");
		long startTime = System.currentTimeMillis();
		System.out.println("Camino: " + solucion.getrecorrido());

		System.out.println("Costo Del Camino: " + solucion.getrecorridoCost());
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Milisegundos: " + totalTime);
	}
	
	

	public TSP(int nodoInicial, double[][] distancia){//Funcion para iniciar la solucion del agente viajero, Cuando todos los nodos han sido visitados, entonces se regresara el estado final.
		this.distancia = distancia;
		N = distancia.length;
		START_NODE = nodoInicial;
		FINISHED_estadoInicial = (1 << N) - 1;
	}

	// Regresa el camino mas corto del agente viajero
	public List<Integer> getrecorrido() {
		if (!correSolucion) 
			solucionar();//Si no se ha corrido la funicon solucionar() entonces que se corra y se regrese la variable recorrido
		return recorrido;
	}

	// Regresa el costo del camino mas corto del agente viajero
	public double getrecorridoCost() {
		if (!correSolucion) solucionar();
		return costoMinimoDelRecorrido;
	}
	//Funcion solucion
	public void solucionar() {
		int estadoInicial = 1 << START_NODE;//Se establece el nodo inicial, dos arreglos, memoria y anterior los cuales guardaran los estados anteriores, asi como el costo minimo del recorrido, el cual su valor sera la llamada de la funcion tcp().
		Double[][] memoria = new Double[N][1 << N];
		Integer[][] anterior = new Integer[N][1 << N];
		costoMinimoDelRecorrido = tsp(START_NODE, estadoInicial, memoria, anterior);
		int index = START_NODE;
		while (true){//Mientras sea verdad, entonces se agrega el estado actual al recorrido y se establece el siguiente estado que se metera al recorrido, pero si este es nulo entonces se sale del ciclo, sino el siguiente estado va a ser el estado inicial o el siguiente estado.
			recorrido.add(index);
			Integer siguienteIndice = anterior[index][estadoInicial];
			if (siguienteIndice == null) break;
			int siguienteEstado = estadoInicial | (1 << siguienteIndice);
			estadoInicial = siguienteEstado;
			index = siguienteIndice;
		}
		recorrido.add(START_NODE);//Se establece el ultimo nodo como el inicial y se establece que se ha resuelto.
		correSolucion = true;
	}
	//Funcion principal para resolucion el TSP, recibe un indice, un estado inicial y los estados anteriores.
	private double tsp(int i, int estadoInicial, Double[][] memoria, Integer[][] anterior) {
		if (estadoInicial == FINISHED_estadoInicial){// Si ya se hizo el estado actual entronces regresa el costo del estado actual al inicial. Si el resultado guardado ya fue calculado, entonces regresa el calculado en el indice y el estado inicial. 
			return distancia[i][START_NODE];
		}
		
		if (memoria[i][estadoInicial] != null){
			return memoria[i][estadoInicial];
		}
		double minCost = Double.POSITIVE_INFINITY;
		int index = -1;
		for (int next = 0; next < N; next++) {//Desde 0 hasta el tamaño del arreglo, entonces si el siguiente estado ya fue visitado entonces se salta, sino el siguiente estado sera el estado inicial.
			if ((estadoInicial & (1 << next)) != 0) continue;			
			int siguienteEstado = estadoInicial | (1 << next);
			double newCost = distancia[i][next] + tsp(next, siguienteEstado, memoria, anterior);//Se le asigna el valor de la distancia actual mas la llamada recursiva de la funcion tcp, 
			if (newCost < minCost) {//si el costo nuevo es menor que el costo minimo entonces el costo minimo sera el nueo y el indice actual sera el siguiente.
				minCost = newCost;
				index = next;
			}
		}
		
		anterior[i][estadoInicial] = index;//se agrega el indice a los estados visitados y se regresa el costo minimo hasta ahora.
		return memoria[i][estadoInicial] = minCost;
	}
	public static void bruteForce(String file){//Funcion para empezar el TCP con fuerza bruta.
		Scanner s = new Scanner (System.in);//Se lee el archivo y se asignan los valores obteidos a las variables que se utilizaran en la solucion del problema.
			int[] Array = readFiles(file);
			n = Array[0];
			pesoFB = new int[n][n];
			xValor = new int[n];
			yValor = new int[n];
			camino = new int[n-1];
			int k = 0;
			for (int i=1; i<Array.length; i = i+2){
				xValor[k] = Array[i];
				k++;
			}
			int d = 0;
			for (int i=2; i<Array.length; i = i+2){
				yValor[d] = Array[i];
				d++;
			}
			for (int i = 0; i < n; i ++){//Se calculan los pesos que hay entre los nodos(Distancias Eucledianas) y se llama la funcion evaluar para empezar a solucionar el problema.
				for (int j = 0; j < n; j++){
					double x = Math.pow((xValor[j] - xValor[i]),2);int x_ = (int) x;
					double y = Math.pow((yValor[j] - yValor[i]),2);
					int y_ = (int) y;
					double r = Math.sqrt( x_ + y_ );
					int w = (int) r;
					pesoFB[i][j] = w;
				}
			}
			evaluar();
	}
	//Funcion para leer el archivo.
	public static int[] readFiles(String file){
		try{
			File f = new File(file);
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt()){
				ctr++;
				s.nextInt();
			}
			int[] Array = new int [ctr];
			Scanner s1 = new Scanner(f);
			for (int i = 0; i < Array.length; i++)
			Array[i] = s1.nextInt();
			return Array;
		}
		catch(Exception e){
		return null;
		}
	}
	
	public static void evaluar(){//Funcion para evaluar cada nodo en el grafo, para i que es igual a 1, va a asignar a la variable cost, el valor de la llamada de la funcion getCost, pasandole subconjuntos actuales y despues se llamara el constructorDeCamino.
			int trySet[] = new int [n-1];
			for(int i = 1; i < n; i++)
				trySet[i-1] = i;
				cost = getCost(0, trySet, n-1);
				constructorDeCamino();
		}
	
	public static int getCost(int currentCity, int input[], int size){//Funcion que se utilizara para conseguir el costo.
		if (size == 0)//Si el tamaño es igual a 0 entonces se regresara el peso en la ciudad actual y en el indice 0, despues se establece el valor del costo maximo posible.
		return pesoFB[currentCity][0];
		int min=999999;
		int minIndex = 0;
		int setToCost[] = new int[n-1];
		for (int i = 0; i < size; i++){
			int k = 0; //Se inicializa un nuevo conjunto, despues si el input en el espacio de i es diferente al input en j, entonces se establece un nuevo costo.
			for (int j = 0; j < size; j++){
				if(input[i] != input[j]){
					setToCost[k++] = input[j];
				}
			}
			int tmp = getCost(input[i], setToCost, size-1);//Se llama recursivamente la funcion getCost.
			if((pesoFB[currentCity][input[i]]+tmp)<min){
			min = pesoFB[currentCity][input[i]]+tmp;
			minIndex = input[i];
			}
		}
		return min;
	}
	public static int getMin(int currentCity, int input[], int size){//Funcion que se utiliza para conseguir el valor minimo. 
		if(size == 0)
		return pesoFB[currentCity][0];//Si el tamaño es igual a 0 entonces se regresara el peso en la ciudad actual y en el indice 0, despues se establece el valor del costo maximo posible.
		int min = 999999;
		int minIndex = 0;
		int setToCost[] = new int [n-1];
		for (int i = 0; i < size; i++){ //Se inicializa un nuevo conjunto, despues si el input en el espacio de i es diferente al input en j, entonces se establece un nuevo costo.
			int k = 0; //Se inicializa un nuevo conjunto, despues si el input en el espacio de i es diferente al input en j, entonces se establece un nuevo costo.
			for (int j = 0; j < size; j++){
				if(input[i] != input[j]){
					setToCost[k++] = input[j];
				}
			}
			int tmp = getCost(input[i], setToCost, size-1);//Se llama a la funcion getCost.
			if((pesoFB[currentCity][input[i]]+tmp) < min){
				min = pesoFB[currentCity][input[i]]+tmp;
				minIndex = input[i];
			}
		}
		return minIndex;
	}
	public static void constructorDeCamino(){//Funcion para construir el camino.
		int ultimoSet[] = new int[n - 1];
		int siguienteSet[] = new int[n - 2];
		for(int i = 1; i < n; i++)//Para i que es igual a 1, i tiene que ser menor que n, entonces se esyablece que el ultimo set en i-1 sera i, el nuevo tamaño sera n - 1, el camino en su posicion inicial sera la llamada de getMin().
			ultimoSet[i - 1] = i;
			int size = n - 1;
			camino[0] = getMin(0,ultimoSet,size);
			
		for(int i = 1; i<n-1; i++){//Se establece el siguiente set.
			int k = 0;
			for(int j = 0; j < size; j++){
				if(camino[i - 1] != ultimoSet[j])
					siguienteSet[k++] = ultimoSet[j];
		}
		--size;
		camino[i] = getMin(camino[i-1], siguienteSet, size);
		for(int j = 0; j < size; j++)
			ultimoSet[j] = siguienteSet[j];
		}
		printResult();//Se llama la impresion del resultado.
	}


	public static void printResult(){//Funcion para imprimir el camino y su costo.
		System.out.println("Usando Fuerza Bruta:");
		System.out.print("Camino: [0, ");
		for(int i = 0; i < n-1; i++){
			System.out.print((camino[i] )+", ");
		}
		System.out.println("0]");
		System.out.println("Costo del Camino: " + cost);
	}

	public static void main(String[] args) throws FileNotFoundException{//fuincion main en el cual se llaman las funciones.
		long startTime = System.currentTimeMillis();
		bruteForce("mediumMap.txt");	
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Milisegundos: " + totalTime);
		System.out.println();
		
		TSPDP("mediumMap.txt");

			
	}
	
}