import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.List;

class Main {
	public static void main(String[] args) {
		Arreglo objeto = new Arreglo();
		objeto.lecturaDatos("archivo.txt");
		//objeto.radixSort();
		objeto.heapSort();
		objeto.imprimeArreglo();
	}
}