package multiplayer.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.cardgame.card.unocard.UnoSuit;
import multiplayer.client.clientmessage.*;
import multiplayer.server.servermessage.*;
import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.unogame.Difficulty;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.cardgame.unogame.ServerUnoGameManager;
import model.cardgame.unogame.UnoGameState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
    This Server class represents the game server. The Server owns and controls the ServerUnoGameManager,
    which is the class that contains the code for the game logic. The ServerUnoGameManager owns the UnoGameState, and it's
    version of the game state is the one true source. When the Server uses the ServerUnoGameManager's methods to play the game,
    the UnoGameState will update to reflect changes that are made, and those changes are being made to the game directly.
    The Client class has a ClientUnoGameManager, and it also owns an UnoGameState, but it is supposed to mirror the Server's game state.
    When the Client class uses the ClientUnoGameManager to play the game, it doesn't directly change anything in the game. The
    ClientUnoGameManager sends messages/requests to the Server class so that the Server can use the ServerUnoGameManager to
    perform the action at the client's request. If the action is successful, the Server will send a message back to the client
    so that the client can update it's game state to mirror the change that took place on ServerUnoGameManager's game state. If,
    for some reason, the action is not successful, the server will send a message back to the client to inform them that no operation
    took place, and why.

    How this Server class acts a server:

       Java has built-in classes for sending messages back and forth between different machines.
       For basic networking, there are two main classes to know about:

        - ServerSocket
            - The ServerSocket class is a class that opens a connection. When you create a new ServerSocket(), the constructor
                accepts a port (e.g., 12345), and that is the port used for communication.
                e.g., ServerSocket serverSocket = new ServerSocket(12345);
            - Once you create a new ServerSocket, the ServerSocket object has a method called accept(). The accept() method
                is what starts the entire operation, because this is what makes the ServerSocket start to listen to any
                incoming connections from other IP addresses so that communication can start taking place. It's important to
                know that the accept() method is blocking. Blocking means that this method will block the current thread. In other words,
                if you call serverSocket.accept() in the main thread, then it will pause the program indefinitely until it receives a connection.
                It basically just sits there and waits forever until something connects. Any code that comes after will not execute until
                a connection has been made. Since this is the case, the accept() method must be called in another thread, that way it
                can't block the main program's thread where other things need to be happening. It will only block its own thread.
            - When a connection has been made, the accept() method will return a socket object, and this socket object is used
                to send messages to the client that connected to it. The 'client' who connected to the ServerSocket is actually
                another socket object. So the 'client' socket is used to send/receive messages from the ServerSocket and the 'server'
                uses the socket that was returned by the accept() method to send/receive messages to that 'client' socket.
            - The 'ServerSocket' is basically what is referred to as the 'server', because it is the host who opened the connection,
                and it begins listening for another IP address to connect to it (which is referred to as a 'client').
            - So, this Server class creates and owns the ServerSocket, and it is the one who opens the connection and begins listening
                to other clients that want to connect to it. So that's why it's called a 'server.'
        - Socket
            - The Socket class is used for sending messages through the network via streams. The way that it works is
            that the Socket object has methods called getOutputStream() and getInputStream(). These methods return an OutputStream
            and InputStream. So in order to send messages, you create a BufferedReader or BufferedWriter, and pass the socket's
            output and input stream to the constructor of the BufferedWriter or BufferedReader, and that's the stream that it will send/receives messages
            to and from.
                - When you create a BufferedWriter or BufferedReader and use the OutputStream and InputStream returned by the socket object,
                    then you can use the BufferedWriter and BufferedReader methods, like readLine() and write(), to send/receive messages.
            - When you create a Socket object, it needs an IP address and a port so that it knows where to send information to.
                - e.g., Socket socket = new Socket("localhost", 12345)
                     - in the constructor, localhost refers to the ip address on the same local machine, and 12345 is the port
                     - "localhost" can also be an actual IP address, like 192.168.1.1
            - In this UNO game, the Server opens a connection at port 12345. Since we've only implemented 'offline' game play,
                the client connects to "localhost" at port 12345, which basically means that it connects to itself, because
                the 'server' is opening a connection using its own local IP address. You can see how it wouldn't be much more
                work to allow players to connect to other machines who are hosting their own game, because the game is already
                operating using client/server communication.
 */

public class Server implements GameEventListener {
    /*
    This map is used to map the playerID to the ServerMessageReader. The ServerMessageReader class is used
    to read all incoming messages from a client. Each ServerMessageReader is responsible for listening to incoming
    messages for a single client.
    */
    private Map<Integer, ServerMessageReader> serverMessageReaders = new HashMap<>();
    /*
    This map is used to map the playerID to the ServerMessageWriter. The ServerMessageWriter class is used
    to send messages to a client. Each ServerMessageWriter is responsible for sending messages to a single client.
    */
    private Map<Integer, ServerMessageWriter> serverMessageWriters = new HashMap<>();
    // The Server owns and creates the ServerSocket, which opens a connection and starts listening for incoming connections
    private ServerSocket serverSocket;
    // The ServerUnoGameManager controls the game
    private ServerUnoGameManager gameManager;
    // This UnoGameState is the one true source of information about the current state of the game
    private UnoGameState gameState;
    /*
    The playerID starts at 1. Each time a new connection is made, the playerID is assigned a playerID and then
    the playerID is incremented for the next client who connects.
     */
    private int playerID = 1;
    // This is the port that the ServerSocket will be open at
    private int port = 12345;
    // This refers to the main thread for the ServerSocket connection that continuously listens for new connections
    private Thread thread;
    // This is a boolean flag used for shutting down threads when closing the program
    private volatile boolean running = false;
    // This method starts the Server by calling the openConnection() method
    public void start() {
        openConnection();
    }

    // get the port
    public int getPort() {
        return port;
    }

    // set the game manager
    public void setGameManager(ServerUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    // set the game state
    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    /*
        This method creates the ServerSocket and calls the accept() method, which begins listening to incoming connections.

        How it works:
            - The creation of the ServerSocket and accept() method take place in a background thread, so that it doesn't
                block the program's main thread. Recall that when a connection is made, the accept() method will then
                return a socket object. That socket object is used by the 'server' to send and receive messages to and from the 'client'.
                Once it does return a socket object, then a ServerMessageReader and ServerMessageWriter object
                is created, and the socket object is passed to those classes which will handle sending/receiving messages to and from
                the client. Then, the serverMessageWriter and serverMessageReader are stored in a map, and are assigned to a PlayerID
                that is assigned to the client that connected to it. This way, this Server class knows which player in the game belongs to that client.
                The local UnoPlayer objects on the client's side will be assigned the same playerID to identify them.
            - In the above paragraph, everything that took place happens inside a while loop that runs continuously, until the running boolean
                variable is set to false or the thread is interrupted.

                Here is the series of events in the while loop:
                    - Step 1: Start listening to incoming connections
                    - Step 2: A client connects
                    - Step 3: Create ServerMessageReader and pass the socket to it so it can read messages using that socket. Set the playerID
                    - Step 4: Create ServerMessageWriter and pass the socket to it so it can send messages using that socket. Set the playerID
                    - Step 5: Add the ServerMessageReader to a map with the current playerID as the key
                    - Step 6: Add the ServerMessageWriter to a map with the current playerID as the key
                    - Step 7: Call the listen() method for ServerMessageReader so that it starts listening for incoming messages from the socket in its own thread
                    - Step 8: Call the startWriting() method for ServerMessageWriter to start queuing messages to send in its own thread
                    - Step 9: Add the ServerMessageReader and ServerMessageWriter to a map
                    - Step 10: Increment playerID for the next client connection
                    - Step 11: Repeat this loop and start listening for another connection

     */
    public void openConnection() {
        running = true; // boolean flag for the thread

        this.thread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        serverSocket = new ServerSocket(port); // create serverSocket object at port 12345
                        while (running) {
                            Socket socket = serverSocket.accept(); // start listening to incoming connection
                            System.out.println("From Server: New Client connected"); // debugging output

                            ServerMessageReader serverMessageReader = new ServerMessageReader(Server.this, socket); // create ServerMessageReader
                            serverMessageReader.setPlayerID(playerID); // set playerID
                            System.out.println("From Server: Client handler (reader) created with playerID: " + playerID); // debugging output

                            ServerMessageWriter serverMessageWriter = new ServerMessageWriter(socket);
                            serverMessageWriter.setPlayerID(playerID); // set playerID
                            System.out.println("From Server: Client handler (writer) created with playerID: " + playerID); // debugging output

                            serverMessageWriters.put(playerID, serverMessageWriter); // put the serverMessageWriter in the map
                            serverMessageWriter.startWriting(); // start the thread for writing messages

                            serverMessageReaders.put(playerID, serverMessageReader); // put the serverMessageReader in the map
                            serverMessageReader.listen(); // start listening to messages from the socket

                            playerID++; // increment the playerID
                        }
                    } catch (IOException e) {
                        if (!running) {
                            break;
                        }
                        System.out.println("From Server: Error opening server connection"); // error message
                    }
                }
            }
        }, "[Server Socket Thread]"); // name of the thread
        thread.start(); // start the thread
    }

    /*
        This method is responsible for shutting down all the background threads that are running here.
     */
    public void shutDown() {
        // stop the continueTurnCycle() method from the ServerUnoGameManager
        gameManager.cancelTurnCycle();

        /*
        create a ShutDownEvent object and send it to all the clients to inform them that they should start shutting down
        their threads as well
         */
        ShutDownEvent shutDownEvent = new ShutDownEvent();
        String message = shutDownEvent.toJson();
        sendMessageToAllClients(message);

        // loop through all the ServerMessageWriters and have them send a message to the client's socket to shut down
        serverMessageWriters.forEach((key, messageWriter) -> {
            messageWriter.storeMessage("SHUTDOWN");
        });

        // loop through all the ServerMessageReaders and call the shutDown() method to close the threads
        serverMessageReaders.forEach((key, messageReader) -> {
            messageReader.shutDown();
        });

        // clear the maps that holds the serverMessageReaders and serverMessageWriters
        serverMessageReaders.clear();
        serverMessageWriters.clear();

        // set the gameManager to null and gameState to null
        gameManager = null;
        gameState = null;

        // reset playerID to 1
        playerID = 1;

        // set running to false
        running = false;

        // Try to close the server socket
        try {
            if (serverSocket != null && !serverSocket.isClosed()) { // check if the serverSocket is not null and make sure it isn't closed already
                System.out.println("From Server Socket: Closing thread " + thread.getName()); // output message for debugging
                serverSocket.close(); // close the server socket
            }
        } catch (IOException e) {
            System.out.println("From Server: Error closing server connection"); // error message
            System.out.flush();
        }
    }

    /* This method was never used and may be deleted
    public void shutDownClient(int playerID) {
        serverMessageReaders.remove(playerID);
    }
     */

    /*
        This method is used to send messages to the client. The ServerMessageWriter class is actually the class
        who is responsible for sending the messages using the socket that was returned when a client connected. Recall
        that there is a ServerMessageWriter for each client that connected, and this sendMessage() will get the appropriate
        ServerMessageWriter from the map based on the given playerID that corresponds to that ServerMessageWriter.

        Then that ServerMessageWriter can be used to send a message to the client who has that playerID.

     */
    public void sendMessage(String message, int playerID) {
        ServerMessageWriter clientHandlerWriter = serverMessageWriters.get(playerID); // get the ServerMessageWriter from the given playerID

        // check if the clientHandlerWriter is not null
        if (clientHandlerWriter != null) { // if it is not null
            clientHandlerWriter.storeMessage(message); // store the message in the ServerMessageWriter
        }

    }

    /*
        This method will send a message to all the clients by looping through all the ServerMessageWriters and using them
        to send the message.
     */
    public void sendMessageToAllClients(String message) {
        // loop through all the serverMessageWriters
        for (var clientHandlerWriter: serverMessageWriters.values()) {
            clientHandlerWriter.storeMessage(message); // store the message
        }
    }

    /*
        This method handles all the incoming messages from the clients. It is synchronized so that it can only handle one message
        at a time, since this is where the ServerUnoGameManager will make changes to the game state.

        This method is called in the ServerMessageReader class. The ServerMessageReader stores a reference to this Server, and it
        continuously listens for incoming messages from the client's socket, and when it receives a message, it calls this method
        and passes the message and playerID to it.

        The messages that are received are JSON Strings.
     */
    public synchronized void handleMessage(String message, int playerID) {
        // get the GameActionType from the message
        GameActionType type = getActionType(message);

        // This statement will switch on the type of GameAction and execute the appropriate method based on the GameActionType
        switch (type) {
            case SETUP_GAME:
                handleSetupGame(message, playerID);
                break;
            case JOIN_GAME:
                handleJoinGame(message, playerID);
                break;
            case CHANGE_NAME:
                handleChangeName(message, playerID);
                break;
            case CHANGE_IMAGE:
                handleChangeImage(message, playerID);
                break;
            case PASS_TURN:
                handlePassTurn();
                break;
            case SAY_UNO:
                handleSayUno(message, playerID);
                break;
            case CALL_UNO:
                handleCallUno(message);
                break;
            case PLAY_CARD:
                handlePlayCard(message, playerID);
                //gameManager.printDeckInformation(); // for debugging
                break;
            case DRAW_CARD:
                handleDrawCard(message, playerID);
                //gameManager.printDeckInformation(); // for debugging
                break;
            case CHANGE_SUIT:
                handleSuitChange(message, playerID);
                break;
            case DISCONNECT:
                System.out.println("Client Disconnected");
                break;
            default:
                System.out.println("Server received unknown action type");
        }
    }

    /*
        This method handles the SetupGameAction message from the client
     */
    public void handleSetupGame(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        SetupGameAction setupGame = gson.fromJson(message, SetupGameAction.class); // convert the Json message back into the SetupGameAction object
        Difficulty difficulty = setupGame.getDifficulty(); // get the difficulty
        UnoEdition edition = setupGame.getEdition(); // get the edition
        UnoCardTheme theme = setupGame.getTheme(); // get the theme
        int numberOfOpponents = setupGame.getNumberOfOpponents(); // get the number of opponents

        // call game manager method
        gameManager.setupGame(edition, theme, difficulty, numberOfOpponents, playerID);
    }

    /*
        This method handles the JoinGameAction message from the client
    */
    public void handleJoinGame(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        JoinGameAction joinGame = gson.fromJson(message, JoinGameAction.class); // convert the Json message back into the JoinGameAction object
        String name = joinGame.getName(); // get the name
        PlayerImage playerImage = joinGame.getPlayerImage(); // get the player image

        // call game manager method
        gameManager.addLocalPlayer(name, playerID, playerImage);
    }

    /*
        This method handles the ChangeNameAction message from the client
    */
    public void handleChangeName(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        ChangeNameAction changeNameAction = gson.fromJson(message, ChangeNameAction.class); // convert the Json message back into the ChangeNameAction object
        String name = changeNameAction.getName(); // get the name
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID); // get the player
        int playerIndex = gameState.getPlayerIndex(player); // get the player index

        // call game manager method
        gameManager.updatePlayerName(playerIndex, name);
    }

    /*
        This method handles the ChangeImageAction message from the client
    */
    public void handleChangeImage(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        ChangeImageAction changeImageAction = gson.fromJson(message, ChangeImageAction.class); // convert the Json message back into the ChangeImageAction object
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID); // get the player
        int playerIndex = gameState.getPlayerIndex(player); // get the player index
        PlayerImage image = changeImageAction.getPlayerImage(); // get the player image

        // call game manager method
        gameManager.updatePlayerImage(playerIndex, image);
    }

    /*
        This method handles the PassTurnAction message from the client
    */
    public void handlePassTurn() {
        // call game manager method
        gameManager.passTurn();
    }

    /*
        This method handles the SayUnoAction message from the client
     */
    public void handleSayUno(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        SayUnoAction sayUnoAction = gson.fromJson(message, SayUnoAction.class); // convert the Json message back into the SayUnoAction object
        boolean sayUno = sayUnoAction.isSayUno(); // get the say uno status
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID); // get the player
        int playerIndex = gameState.getPlayerIndex(player); // get the player index

        // call game manager method
        gameManager.sayUno(playerIndex, sayUno);
    }

    /*
        This method handles the CallUnoAction message from the client
     */
    public void handleCallUno(String message) {
        Gson gson = new Gson(); // create Gson object
        CallUnoAction callUno = gson.fromJson(message, CallUnoAction.class); // convert the Json message back into the CallUnoAction object
        int playerIndex = callUno.getPlayerIndex(); // get the player index

        // call game manager method
        gameManager.callUno(playerIndex);
    }

    /*
        This method handles the PlayCardAction message from the client
    */
    public void handlePlayCard(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        PlayCardAction playCard = gson.fromJson(message, PlayCardAction.class); // convert the Json message back into the PlayCardAction object
        int cardIndex = playCard.getCardIndex(); // get the card index
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID); // get the player
        int playerIndex = gameState.getPlayers().indexOf(player); // get the player index

        // call game manager method
        gameManager.playCard(playerIndex, cardIndex);
    }

    /*
        This method handles the DrawCardAction message from the client
    */
    public void handleDrawCard(String message, int playerID) {
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID); // get the player
        int playerIndex = gameState.getPlayers().indexOf(player); // get the player index

        // call game manager method
        gameManager.playerDrawCardFromDrawPile(playerIndex);
    }

    /*
        This method handles the ChangeSuitAction message from the client
    */
    public void handleSuitChange(String message, int playerID) {
        Gson gson = new Gson(); // create Gson object
        ChangeSuitAction changeSuitAction = gson.fromJson(message, ChangeSuitAction.class); // convert the Json message back into the ChangeSuitAction
        UnoSuit suit = changeSuitAction.getSuit(); // get the suit

        // call game manager method
        gameManager.changeSuit(suit);
    }

    /*
        This method sends an event message to the client using the socket. This is the method which is implemented by the GameEventListener and allows
        the ServerUnoGameManager to send messages using the GameEventListener interface to the specified client.
    */
    public void sendEventMessage(GameEvent event, int playerID) {
        String message = event.toJson(); // convert the event object to Json String
        sendMessage(message, playerID); // send the message to the client
    }

    /*
        This method also sends messages to clients using the socket. This method is also implemented by the GameEventListener and allows
        the ServerUnoGameManager to send messages using the GameEventListener interface to all the clients.
     */
    public void sendEventMessageToAll(GameEvent event) {
        String message = event.toJson(); // convert the event object to Json String
        sendMessageToAllClients(message); // send the message to all clients
    }

    // This method gets the action type from the Json message it receives
    public GameActionType getActionType(String message) {
        Gson gson = new Gson(); // create Gson object
        JsonObject json = gson.fromJson(message, JsonObject.class); // Convert the Json String to JsonObject
        String type = json.get("type").getAsString(); // Get the type of event
        return GameActionType.valueOf(type); // return the GameActionType
    }
}


