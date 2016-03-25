import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Tester {

	public static void main(String[] args) throws IOException {
		File file = new File("/home/phoenix/Desktop/allints100.dat");
		file.createNewFile();
		OutputStream out = new FileOutputStream(file);
		for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE - 100; i += 100) {
			Protocol.writeInt(i, out);
			System.out.println(i);
		}
	}

}
