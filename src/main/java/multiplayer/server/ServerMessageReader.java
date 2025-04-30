package multiplayer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
    This class is used to read incoming messages for the Server. The Server owns the ServerSocket, and the ServerSocket
    accept() method returns a socket object everytime a connection is made. Then that socket is used to send and receive data
    to the socket that connected to it. So, when that accept() method returns the socket, the Server creates a ServerMessageReader
    and passes the socket to this class. This class uses that socket to start listening for messages in a separate thread.
    When the readLine() method for BufferedReader is called, it will block the current thread, which is why it needs to happen in its own
    thread.

    The Server also sets the playerID for this ServerMessageReader so that it can identify who this client is in the game.
 */

public class ServerMessageReader {

    private BufferedReader reader; // create BufferedReader to read incoming messages
    private Server server; // store reference to the Server to call the handleMessage() method
    private int playerID; // store reference to playerID so Server can identify who this client is in the game
    private Thread thread; // create reference to the background thread so that it can be shut down from outside
    private volatile boolean running = true; // boolean variable for shutting down thread

    // The constructor accepts the Server and the socket for receiving messages
    public ServerMessageReader(Server server, Socket socket) {
        this.server = server;

        // create new BufferedReader and pass the InputStream from the socket to the BufferedReader
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        This method starts listening to any messages that the client sends to the Server.
        The readLine() method blocks the current thread, so this takes place in its own thread.
        Inside the thread, the readLine() method is called continuously in a while loop so that it keeps
        reading all messages that are sent. When a message is received, it will use the Server's handleMessage() to
        read the message, and the server will respond. Then the loop repeats and starts listening again.
     */
    public void listen() {

        this.thread = new Thread(new Runnable() {
            public void run() {

                while (running) {
                    try {
                        // listen to the messages coming in from the client
                        String message = reader.readLine();

                        // when the socket is closed. readLine() returns null
                        if (message == null) {
                            System.out.println("From Server Message Reader: Closing thread " + thread.getName());
                            break;
                        }

                        // passes the message off to the server and calls the servers method to process the message
                        server.handleMessage(message, playerID);
                    } catch (IOException e) {
                        System.out.println("From Server Message Reader: Error closing socket");
                        System.out.flush();
                    }
                }
            }
        }, "[Server Message Reader: " + playerID + "]"); // name of the thread
        thread.start(); // start the thread
    }

    // set the playerID
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    // set running false to shut down the while loop
    public void shutDown() {
        running = false;
    }
}
