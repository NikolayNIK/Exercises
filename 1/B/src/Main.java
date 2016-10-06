import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		System.out.print("Введите количество фишек: ");
		
		Scanner scanner = new Scanner(System.in);
		
		float s = 0.25f * scanner.nextInt() + 1;
		
		scanner.close();
		
		if(s == (int)s) System.out.print("ya");
		else System.out.print("nein");
	}
}
