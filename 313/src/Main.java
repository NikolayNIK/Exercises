import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		
		scanner.close();
		
		System.out.println(getCombinations(n, k));
	}
	
	public static int getCombinations(int n, int k) {
		if(k == 0 || n == k) return 1;
		return getCombinations(n - 1, k - 1) + getCombinations(n - 1, k);
	}
}
