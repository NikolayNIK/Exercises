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
			moneys += (int)(moneys * (p / 100f) * 100f) / 100f;
			years++;
		}
		
		System.out.print(years);
	}
}
