package multiplayer.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(12345)) {
                    // start accepting messages
                    Socket socket = serverSocket.accept();

                    // start waiting to receive a message
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    while (true) {
                        String line = br.readLine();

                        if (line == null) {
                            break;
                        }

                        System.out.println(line);

                    }

                    // send a message
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);

                    bw.write("Hello, Client!\nHow are you??\n");
                    bw.flush();

                    socket.shutdownOutput();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        t1.start();

        for (int i = 0; ; i++) {
            System.out.println(i);
            Thread.sleep(2000);
        }
    }
}
