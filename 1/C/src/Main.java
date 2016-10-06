import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Введите цену:");
		int[] price = askForMoney(scanner);
		
		System.out.println();
		
		System.out.println("Введите деньгу:");
		int[] money = askForMoney(scanner);
		
		System.out.println();
		
		System.out.println("Сдача:");
		System.out.print("• руб ");
		System.out.println(money[0] - price[0]);
		System.out.print("• коп ");
		System.out.print(money[1] - price[1]);
	}
	
	public static int[] askForMoney(Scanner scanner) {
		int[] out = new int[2];
		System.out.print("• руб: "); out[0] = scanner.nextInt();
		System.out.print("• коп: "); out[1] = scanner.nextInt();
		return out;
	}
}
