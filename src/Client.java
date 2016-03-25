import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Client {

	/*
	 * args = {ip, port}
	 */
	public static void main(String[] args) throws IOException {
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		System.out.println("Attempting to connect to server");
		Socket socket = new Socket(ip, port);
		InputStream in = socket.getInputStream();
		System.out.println("Connected to server");
		
		while (true) {
			int header = in.read();
			if (header == 0) {
				File dir = new File(Protocol.readString(in));
				System.out.println("creating dir " + dir);
				dir.mkdirs();
			} else if (header == 1) {
				File file = new File(Protocol.readString(in));
				System.out.println("creating file " + file);
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				int toRead = Protocol.readInt(in);
				byte[] buffer = new byte[8192];
				while (toRead > 0) {
					if (toRead >= buffer.length) {
						in.read(buffer);
						out.write(buffer);
						toRead -= buffer.length;
					} else {
						buffer = new byte[toRead];
						in.read(buffer);
						out.write(buffer);
						toRead -= buffer.length;
					}
				}
				out.close();
				System.out.println("filled " + file);
			} else if (header == 2) {
				socket.close();
				return;
			}
		}
	}

}
