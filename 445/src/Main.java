import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		float[] values = new float[6];
		
		for(int i = 0; i < values.length; i++) {
			values[i] = scanner.nextFloat();
		}
		
		scanner.close();
		
		float a = getDistance(values[0], values[1], values[2], values[3]);
		float b = getDistance(values[2], values[3], values[4], values[5]);
		float c = getDistance(values[4], values[5], values[0], values[1]);
		
		float p = (a + b + c) / 2f; 
		
		System.out.println(Math.sqrt(p * (p - a) * (p - b) * (p - c)));
	}
	
	private static float getDistance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.abs(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
}
