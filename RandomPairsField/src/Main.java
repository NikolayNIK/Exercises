import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int width = scanner.nextInt();
		int height = scanner.nextInt();
		
		scanner.close();
		
		Random random = new Random();
		List<Integer> values = new ArrayList<>();
		
		for(int i = (width * height) / 2; i > 0; i--) {
			values.add(i);
			values.add(i);
		}
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				System.out.append(Integer.toString(values.remove(random.nextInt(values.size()))));
				System.out.append(' ');
			}
			
			System.out.append('\n');
		}
		System.out.flush();
	}
}
