

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 * Static class for binary operations
 */
public class BinUtils {

	private BinUtils() {}
	
	public static byte[] intToBytes(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	public static byte[] stringToBytes(String string) {
    	return string.getBytes(StandardCharsets.UTF_8);
    }
    
	public static String bytesToString(byte[] bytes) {
    	return new String(bytes, StandardCharsets.UTF_8);
    }
	
}
