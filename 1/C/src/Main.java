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
		
		int rub = money[0] - price[0];
		int cop = money[1] - price[1];
		
		if(rub < 0) {
			System.out.println("Не хватает");
			return;
		}
		
		if(cop < 0) {
			rub--;
			cop+=100;
		}
		
		System.out.println("Сдача:");
		System.out.print("• руб ");
		System.out.println(rub);
		System.out.print("• коп ");
		System.out.print(cop);
	}
	
	public static int[] askForMoney(Scanner scanner) {
		int[] out = new int[2];
		System.out.print("• руб: "); out[0] = scanner.nextInt();
		System.out.print("• коп: "); out[1] = scanner.nextInt();
		return out;
	}
}
