import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static String localPath;
	private static String foreignPath;
	
	/*
	 * args = {port, local path, foreign path}
	 */
	public static void main(String[] args) throws IOException {
		int port = Integer.parseInt(args[0]);
		localPath = args[1];
		foreignPath = args[2];
		
		System.out.println("Waiting for connection");
		//ServerSocket serverSocket = new ServerSocket(port);
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(port));
		Socket connection = serverSocket.accept();
		serverSocket.close();
		System.out.println("Connected to " + connection);
		
		System.out.println("About to send files");
		OutputStream out = connection.getOutputStream();
		send(new File(localPath), out);
		out.write(2);
		System.out.println("Sent files");
	}
	
	public static void send(File file, OutputStream out) throws IOException {
		if (file.isFile()) {
			out.write(1);
			Protocol.writeString(localToForeignPath(file.getPath()), out);
			Protocol.writeInt((int) file.length(), out);
			int count;
			byte[] buffer = new byte[8192];
			InputStream in = new FileInputStream(file);
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			in.close();
		} else {
			out.write(0);
			Protocol.writeString(localToForeignPath(file.getPath()), out);
			for (File subfile : file.listFiles()) {
				send(subfile, out);
			}
		}
	}
	
	/*
	 * Assumes both parties use the same folder seperator
	 * If they're both using windows or unix, it should be fine
	 * Better support would be easy to add
	 */
	public static String localToForeignPath(String str) {
		return foreignPath + str.substring(localPath.length());
	}
	
}
