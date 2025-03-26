package multiplayer.test;

import java.io.*;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) throws IOException {

        Thread t1 = new Thread(new Runnable(){

            public void run() {
                try (Socket socket = new Socket("localhost", 12345)) {

                    // send a message to the server
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);

                    bw.write("Hello, Server!\nHow are you?!\n");
                    bw.flush();

                    socket.shutdownOutput();

                    // receive a message
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

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        t1.start();

        System.out.println("Program is waiting!");

    }
}
