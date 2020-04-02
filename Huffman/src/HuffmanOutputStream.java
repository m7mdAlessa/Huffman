import java.io.*;

public class HuffmanOutputStream extends FileOutputStream {
	private byte currentByte;//byte with the bits used in the leftmost bits
	
	private int numOfBits;//numOfBits used in the currentByte
	
	//creates an output stream to write to fileName
	public HuffmanOutputStream(String fileName) throws FileNotFoundException {
		super(fileName);
		currentByte = 0;
		numOfBits = 0;
	}
	
	//write the rightmost n bits in b into the file.
	public void writeBits(byte b,int n) throws IOException {
		//sets the leftmost (8-n) bits to zero.
		b &=((1<<n)-1);
		
		//if you can't add all the n bits into currentByte
		if(numOfBits + n > 8) {
			
			//i is the number of bits in b that can't be added to the currentByte
			int i = numOfBits + n - 8;
			
			// a is the bits that can be added into currentByte
			byte a = (byte) ((b&255) >>> i);
			
			//add a to currentByte
			currentByte |= a;
			
			//write the currentByte to the file and reset the currentByte
			write(currentByte);
			currentByte = 0;
			numOfBits = 0;
			
			//write the remaining i bits
			writeBits(b,i);
			
		}else {
			//write n bits into the currentByte
			b  <<= (8-(n+numOfBits));
			currentByte |= b;
			numOfBits+=n;
		}
	}
	
	//write the currentByte into the file
	public void flushByte() throws IOException {
		if(numOfBits > 0) {
			write(currentByte);
		}
	}
}
