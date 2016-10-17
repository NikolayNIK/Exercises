import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int[] arrayA = new int[scanner.nextInt()];
		for(int i = 0; i < arrayA.length; i++) arrayA[i] = scanner.nextInt();
		
		int[] arrayB = new int[scanner.nextInt()];
		for(int i = 0; i < arrayB.length; i++) arrayB[i] = scanner.nextInt();
		
		scanner.close();
		
		int a = 0;
		int b = 0;
		int c = 0;
		int[] out = new int[arrayA.length + arrayB.length];
		
		while(c < out.length) {
			if(a < arrayA.length) {
				if(b < arrayB.length) {
					if(arrayA[a] < arrayB[b]) {
						out[c] = arrayA[a];
						a++;
					} else {
						out[c] = arrayB[b];
						b++;
					}
				} else {
					out[c] = arrayA[a];
					a++;
				}
			} else {
				out[c] = arrayB[b];
				b++;
			}
			c++;
		}
		
		System.out.println(Arrays.toString(out));
	}
}
