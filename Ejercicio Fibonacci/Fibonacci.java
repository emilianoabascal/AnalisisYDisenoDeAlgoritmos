import java.util.Scanner;
class Fibonacci {
	static int fibonacciRecursivo(int r){
		if(r == 0 || r == 1)
			return r;
		else
			return fibonacciRecursivo(r-1) + fibonacciRecursivo(r-2);
	}
	
	static int fibonacciArreglo(int n){
		int[] array;
		array = new int[n+1];
		array[0] = 0;
		array[1] = 1;
		for(int i = 2; i <= n; i++)
			array[i] = array[i-1] + array[i-2];
		
		
		return array[n];
	}
	
	public static void main(String[] args) {
		int n = 0;
		Scanner in = new Scanner(System.in);
	System.out.println("Fib(" + n + ") = " + fibonacciArreglo(n) );
	}
}