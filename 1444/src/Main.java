import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int width = scanner.nextInt();
		int height = scanner.nextInt();
		Vector[] mines = new Vector[scanner.nextInt()];
		for(int i = 0; i < mines.length; i++)
			mines[i] = new Vector(scanner.nextInt(), scanner.nextInt());
		scanner.close();
		
		int[][] field = new int[width][height];
		
		for(Vector vector: mines)
			setMine(field, vector, width, height);
			
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int i = field[x][y];
				if(i <= -1) System.out.append('*');
				else System.out.append(Integer.toString(i));
				System.out.append(' ');
			}
			
			System.out.println();
		}
	}
	
	public static void setMine(int[][] field, Vector pos, int width, int height) {
		try {
			field[pos.x][pos.y] = -1;
			for(int x = pos.x - 1; x <= pos.x + 1; x++)
				for(int y = pos.y - 1; y <= pos.y + 1; y++)
					try {
						if(field[x][y] >= 0)
							field[x][y]++;
					} catch (Throwable t) {}
		} catch (Throwable t) {}
	}
}
