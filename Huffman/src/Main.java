import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		if(args.length < 3) {
			System.exit(1);
		}
		if(args[0].equals("hc")) {
			if(args[1].equals("-c")) {
				String fileName = args[2];
				Huffman.encode(fileName);
			}else if(args[1].equals("-d")) {
				String fileName = args[2];
				Huffman.decode(fileName);
			}
		}
		
		
	}
}
