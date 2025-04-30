package multiplayer.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
    This class is used to write messages to the client using the socket received from the ServerSocket's accept() method.
    The Server owns the ServerSocket, and the ServerSocket accept() method returns a socket object everytime a connection is made.
    Then that socket is used to send and receive data to the socket that connected to it. So, when that accept() method returns the socket,
    the Server creates a ServerMessageWriter and passes the socket to this class.

    The way this class works is that there is a BlockingQueue, which stores String objects. When you retrieve items
    from the BlockingQueue, it will be stored and retrieved in a first in, first out basis, like a queue. So when the Server is ready
    to send a message to the client, they will call the storeMessage() method, which puts the Json String message into the
    BlockingQueue.

    The BlockingQueue has a take() method that retrieves an item from the queue. The take() method blocks the current thread,
    so it needs to be called in its own thread. So, there is another background thread where take() is being called, and it is being
    called repeatedly in a while loop. The way it works is that if there is a message in the queue, it will take the message out of the
    queue and then send the message using the BufferedWriter, which was passed the OutputStream from the socket when it was created.

    So here's the series of events when a message needs to be sent from the server to a client:
        (Assuming the ServerMessageWriter exists, and the startWriting() method has been called)
        - Step 1: The Server uses the ServerMessageWriter to call the storeMessage(). It passes the Json String message to storeMessage().
        - Step 2: The storeMessage() method puts the Json String message in the BlockingQueue.
        - Step 3: The startWriting() method is running a thread, and inside that thread, the take() method is waiting to
            take a message out of the queue. But, if there are no messages inside the BlockingQueue to be taken, then it will
            pause and wait. When the storeMessage put the Json String message in, the take() method detects that a message
            is ready to be taken out, and it takes it out of the queue.
        - Step 4: The Json String message that was taken out of the queue is passed to the sendMessage() method
        - Step 5: The sendMessage() method uses the BufferedWriter to write the message.
        - This process is repeated indefinitely until the thread running in the startWriting() method is shut down.
 */

public class ServerMessageWriter {

    // create LinkedBlockingQueue to store messages
    private BlockingQueue<String> messages = new LinkedBlockingQueue<>(1000);
    private BufferedWriter writer; // create BufferedWriter
    private int playerID; // store the playerID
    private volatile boolean running = true; // create boolean flag to shut down while loop
    private Thread thread; // create reference to thread so that the thread can be shutdown from outside

    // The constructor accepts the Server and the socket for sending messages
    public ServerMessageWriter(Socket socket) {
        // create new BufferedWriter and pass the OutputStream from the socket to the BufferedWriter
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        This method receives a String message and stores it in the LinkedBlockingQueue.
     */
    public void storeMessage(String message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            System.out.println("From Server Message Writer: Failed to store message in queue");
            System.out.flush();
        }
    }

    /*
        This method uses the BufferedWriter to write messages.
     */
    public void sendMessage(String message) {
        try {
            writer.write(message); // write the message
            writer.newLine(); // add a new line to mark the end of the message
            writer.flush(); // force the BufferedWriter to push everything through immediately
        } catch (IOException e) {
            System.out.println("From Server Message Writer: Error getting output stream from socket");
            System.out.flush();
        }
    }

    /*
        This method creates a thread where the BlockingQueue can start taking items out of the queue.
        This method continuously runs in a while loop so that it keeps taking messages out of the queue as
        messages are put into the queue.

        Once a message is added to the BlockingQueue, it will be taken out and then that message will be sent.
     */
    public void startWriting() {
            this.thread = new Thread(new Runnable() {
                public void run() {

                    while (running) {
                        try {
                            String message = messages.take(); // take message out of the LinkedBlockingQueue if something is there, otherwise, wait

                            /*
                                This checks if the message that is received is "SHUTDOWN". If it receives the message "SHUTDOWN",
                                then it will begin the shutdown process, which includes stopping this thread, breaking the while loop,
                                and clearing the messages in the queue.
                             */
                            if (message.equals("SHUTDOWN")) { // if "SHUTDOWN"
                                System.out.println("From Server Message Writer: Closing thread " + thread.getName()); // print output for debugging
                                System.out.flush();
                                messages.clear(); // clear the messages LinkedBlockingQueue
                                running = false; // set running false to stop the while loop
                                break; // break out of the while loop
                            }

                            sendMessage(message); // send the message
                        } catch (InterruptedException e) {
                            System.out.println("From Server Message Writer: Error shutting down Server Message Writer");
                            System.out.flush();
                        }
                    }
                }
            }, "[Server Message Writer Thread: " + playerID + "]"); // name of the thread
            thread.start(); // start the thread
    }

    // set playerID
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

}
