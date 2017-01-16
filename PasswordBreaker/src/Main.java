import java.util.ArrayList;
import java.util.zip.CRC32;
import java.util.Scanner;
import java.nio.channels.Channels;
import java.net.URL;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Calendar;

public class Main {
	
	public static void main(String[] args) throws IOException {
        long hash = 0x0BA02B6E1L;
		
		System.out.println("Downloading words...");
		ArrayList<String> words = new ArrayList<String>();
		
		Scanner scanner = new Scanner(new URL("https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/10k_most_common.txt").openStream());
		while(scanner.hasNext()) words.add(scanner.next());
		scanner.close();
		
        System.out.append("There are ");
		System.out.append(Integer.toString(words.size()));
		System.out.println(" words in dictionary file\nGenerating passwords...");
        
		long start = Calendar.getInstance().getTimeInMillis();
		CRC32 crc32 = new CRC32();
		for(String word: words) {
			for(int i = 0; i < 10000; i++) {
				String password = word + i;
				crc32.update(password.getBytes());
				if(crc32.getValue() == hash) {
					long finish = Calendar.getInstance().getTimeInMillis();
					System.out.append("Answer: \"");
					System.out.append(password);
					System.out.append("\" in ");
					System.out.append(Long.toString(finish - start));
					System.out.println(" millis");
					return;
				}
				
				crc32.reset();
			}
		}
	}
}
