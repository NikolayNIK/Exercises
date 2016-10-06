import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		final char[] word = scanner.nextLine().toLowerCase().toCharArray();
		
		scanner.close();
		
		List<Character> chars = new ArrayList<>();
		int i = 0;
		while(i < chars.size()) {
			char ch = chars.get(i);
			if(Character.isAlphabetic(ch)) i++;
			else chars.remove(i);
		}
		
		boolean isPolindrom = true;
		for(i = 0; i < chars.size(); i++)
			if(!chars.get(i).equals(chars.get(chars.size() - i - 1)))
				isPolindrom = false;
		
		if(isPolindrom) System.out.print("ya");
		else System.out.print("nein");
	}
}
