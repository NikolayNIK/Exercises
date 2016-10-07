import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Время расчета: ");
		final int n = scanner.nextInt();
		
		System.out.print("Время жизни: ");
		final int m = scanner.nextInt();
		
		System.out.append("\n");
		
		scanner.close();
		
		List<Rabbit> rabbits = new ArrayList<>();
		rabbits.add(new Rabbit(m));
		for(int i = 0; i < n - 1; i++) {
			System.out.append("Месяц ");
			System.out.append(Integer.toString(i + 1));
			System.out.append("\n До\n");
			for(Rabbit rabbit: rabbits) {
				System.out.append("  ");
				System.out.append(rabbit.toString());
				System.out.append('\n');
			}
			
			for(Rabbit rabbit: rabbits.toArray(new Rabbit[rabbits.size()])) { // Костылище
				if(rabbit.isMature()) rabbits.add(new Rabbit(m));
				rabbit.age();
				if(rabbit.isDead()) rabbits.remove(rabbit);
			}

			System.out.append(" После\n");
			for(Rabbit rabbit: rabbits) {
				System.out.append("  ");
				System.out.append(rabbit.toString());
				System.out.append('\n');
			}
			
			System.out.append('\n');
		}
		
		System.out.append("Ответ: ");
		System.out.print(rabbits.size());
	}
}
