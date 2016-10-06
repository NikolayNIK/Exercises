import java.util.Scanner;

public class Main {
	
	public static final int COUNT = 4;
	
	public static void main(String[] args) {
		System.out.print("Введите ");
		System.out.print(COUNT);
		System.out.println(" чисел(а):");
		
		Scanner scanner = new Scanner(System.in);
		
		int positiveCount = 0;
		
		for(int i = 0; i < COUNT; i++)
			if(scanner.nextInt() > 0)
				positiveCount++;
		
		scanner.close();
				
		if(positiveCount == 2) System.out.print("ya");
		else System.out.print("nein");
	}
}
