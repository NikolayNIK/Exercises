import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		final int a = scanner.nextInt();
		final int b = scanner.nextInt();
		
		scanner.close();
		
		int e = getMaximumDevider(a, b);
		int c = a / e;
		int d = b / e;
		
		System.out.append(Integer.toString(c));
		System.out.append(' ');
		System.out.append(Integer.toString(d));
		System.out.flush();
	}
	
	public static int getMaximumDevider(int a, int b) {
		for(int i = Math.max(a, b); i >= 0; i--)
			if(a % i == 0 && b % i == 0)
				return i;
				
		return 1;
	}
}
