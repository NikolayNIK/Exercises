import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		final int x = scanner.nextInt();
		final int p = scanner.nextInt();
		final int y = scanner.nextInt();
		
		scanner.close();
		
		int moneys = x;
		int years = 0;
		
		while(moneys < y) {
			moneys += moneys * p / 100;
			years++;
		}
		
		System.out.print(years);
	}
}
