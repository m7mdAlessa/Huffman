import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HuffmanOutputStream extends BufferedOutputStream {
	private byte currentByte;
	private int numOfBits;
	public HuffmanOutputStream(OutputStream out, int size) {
		super(out, size);
		currentByte = 0;
		numOfBits = 0;
	}

	public void writeBits(byte b,int n) throws IOException {
		byte num = (byte) ((1<<n) - 1);
		b = (byte) (b&num);
		if(numOfBits + n > 8) {
			int i = numOfBits + n - 8;
			byte a = (byte) (b >>> i);
			int j = (1<<(8-i))-1;
			a &=j;
			currentByte = (byte) (currentByte | a);
			write(currentByte);
			currentByte = 0;
			numOfBits = 0;
			writeBits(b,i);
			return;
		}
		b = (byte) (b << (8-(n+numOfBits)));
		currentByte = (byte) (currentByte | b);
		numOfBits+=n;
	}

	public int size() {
		return (count * 8)+numOfBits;
	}
	public int sizeInByte() {
		return (count) + (numOfBits >0?1:0);
	}
	public void flushByte() throws IOException {
		if(numOfBits > 0) {
			write(currentByte);
		}
	}
}
