import java.util.Scanner;
class Fibonacci {
	static int fibonacciRecursivo(int r){
		if(r == 0 || r == 1)
			return r;
		else
			return fibonacciRecursivo(r-1) + fibonacciRecursivo(r-2);
	}
	public static void main(String[] args) {
		int n = 0;
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
	System.out.println("Fib(" + n + ") = " + fibonacciRecursivo(n));
	}
}