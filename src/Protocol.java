
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Protocol {

	private Protocol() {}
	
	public static void writeByteArray(byte[] array, OutputStream out) throws IOException {
		out.write(BinUtils.intToBytes(array.length));
		out.write(array);
	}
	
	public static byte[] readByteArray(InputStream in) throws IOException {
		byte[] head = new byte[4];
		in.read(head);
		byte[] body = new byte[BinUtils.bytesToInt(head)];
		in.read(body);
		return body;
	}
	
	public static void writeString(String string, OutputStream out) throws IOException {
		writeByteArray(BinUtils.stringToBytes(string), out);
	}
	
	public static String readString(InputStream in) throws IOException {
		return BinUtils.bytesToString(readByteArray(in));
	}
	
	public static void writeInt(int n, OutputStream out) throws IOException {
		out.write(BinUtils.intToBytes(n));
	}
	
	public static int readInt(InputStream in) throws IOException {
		byte[] data = new byte[4];
		in.read(data);
		return BinUtils.bytesToInt(data);
	}
	
}
