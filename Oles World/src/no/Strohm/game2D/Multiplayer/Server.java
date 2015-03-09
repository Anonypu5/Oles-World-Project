package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.world.World;
import no.Strohm.game2D.world.tiles.Tile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server extends Thread{

    public ServerSocket serverSocket;
    public static boolean run = false;
    public boolean serverFull = false;
    public static ServerManager serverManager[];
    public Tile[][] tiles;

    public Server(int port, int antPlayers) throws Exception{
        try {
            String ip = null;
            ip = Inet4Address.getLocalHost().getHostAddress();
            System.out.println( "Your local ip is " + ip);
        } catch (Exception e) {
        }
        try{
            serverSocket = new ServerSocket(port);
            serverManager = new ServerManager[antPlayers];
            for(int c = 0; c < serverManager.length; c++){
                serverManager[c] = new ServerManager();
            }
            System.out.println("SERVER: Successfully created server");
        }catch(Exception e){
            System.out.println("SERVER: Was not abel to create server on port: " + port);
            throw e;
        }
    }

    public void run(){
        run = true;
        System.out.println("SERVER: Started running server-loop");
        while(loop());
        System.out.println("SERVER: Stopped running server-loop");
    }

    public boolean loop(){
        int csm = -1;
        ServerManager currentConnection = null;
        try {
            System.out.println("SERVER: Looking for new player to connect");
            currentConnection = new ServerManager(serverSocket.accept());
            System.out.println("SERVER: New player connected");
        } catch (IOException e) {
            System.out.println("SERVER: Faild to accept");
        }
        for(int c = 0; c < serverManager.length; c++){
            System.out.println("SERVER: Slot "+(c+1)+" is "+(serverManager[c].running ? "running" : "not running"));
            if(!serverManager[c].running){
                csm = c;
                break;
            }
        }
        System.out.println("SERVER: Asigned player to slot = "+(csm+1));
        try{
            System.out.println("SERVER: Connecting to "+currentConnection.socket.getInetAddress().getHostAddress()+"...");
            if(csm == -1){
                System.out.println("SERVER: Server is full, could not connect");
                currentConnection.dataOutputStream.writeUTF("full");
                throw new Exception();
            }else{
                currentConnection.dataOutputStream.writeUTF("room");
                currentConnection.ID = csm;
                serverManager[csm] = currentConnection;
                new Thread(serverManager[csm]).start();
            }
        }catch(Exception e){
            serverManager[csm].running = false;
        }
        return run;
    }

    public static boolean testGameTag(String gameTag){
        for(int i = 0; i < serverManager.length; i++){
            if(serverManager[i] != null) {
                if (gameTag.equals(serverManager[i].gameTag) && serverManager[i].running) {
                    return false;
                }
            }
        }
        return true;
    }

    public void getTiles(){
        tiles = new Tile[Game.mapHeight][Game.mapWidth];
        for (int y = 0; y < Game.mapWidth; y++) {
            for (int x = 0; x < Game.mapHeight; x++) {
                tiles[y][x] = Tile.createTile(World.tiles[y][x].id, World.tiles[y][x].getX(), World.tiles[y][x].getY(), World.tiles[y][x].getWorld());
            }
        }
    }

}class ServerManager implements Runnable{

    public Socket socket;
    public boolean running = false;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    int ID = 0;
    public String gameTag;

    public ServerManager(){}

    public ServerManager(Socket getSocket){
        socket = getSocket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {}
        running = true;
    }

    public void run(){
        try {
            if(dataInputStream.readUTF().equals(Game.VERSION)){
                dataOutputStream.writeUTF("version ok");
                String gameTagBuffer;
                gameTagBuffer = dataInputStream.readUTF();
                if(Server.testGameTag(gameTagBuffer)){
                    dataOutputStream.writeUTF("game tag ok");
                    gameTag = gameTagBuffer;
                    System.out.println("SERVER: Successfully connected to " + gameTag + " on ip: " + socket.getInetAddress());
                    sendMap();
                    sendPlayers();
                    addPlayer();
                    new ConnectionTester(socket,ID,gameTag).start();
                    while(loop());
                }else{
                    dataOutputStream.writeUTF("game tag taken");
                    System.out.println("SERVER: Game tag taken");
                }
            }else{
                dataOutputStream.writeUTF("version outdated");
                System.out.println("SERVER: Version outdated");
            }
        } catch (Exception e) {
        }
    }

    private void sendPlayers() {
        try {
            dataOutputStream.writeUTF("setPlayerQuantity;" + Server.serverManager.length + ";");
            for(int i = 0; i < Server.serverManager.length; i++) {
                if(Server.serverManager[i].ID != ID && Server.serverManager[i].running) {
                    dataOutputStream.writeUTF("setPlayer;"+Server.serverManager[i].gameTag+";");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(){
        for(int i = 0; i < Server.serverManager.length; i++) {
            if(Server.serverManager[i].ID != ID && Server.serverManager[i].running) {
                try {
                    Server.serverManager[i].dataOutputStream.writeUTF("setPlayer;"+gameTag+";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMap(){
        try {
            for (int y = 0; y < Game.mapHeight; y++) {
                for (int x = 0; x < Game.mapWidth; x++) {
                    dataOutputStream.writeUTF(createMapTileString(x,y,World.tiles[x][y].id));
                }
            }
        }catch(Exception e){
            System.out.println("SERVER: Failed to send map");
        }
    }

    public boolean loop(){
        try {
            String message = dataInputStream.readUTF();
            new read(message, ID).start();
        } catch (IOException e) {
            System.out.println("SERVER: Disconnected from "+gameTag);
            running = false;
            return running;
        }
        return running;
    }

    public String createMapTileString(int x, int y, int tile){
        return "setMapTile;"+x+";"+y+";"+tile+";";
    }
}class read extends Thread{

    String input = "";
    int senderId = 0;

    public read(String input,int senderId){
        this.input = input;
        this.senderId = senderId;
    }

    public void run(){
        ServerManager[] players = Server.serverManager;
        for(int i = 0; i < players.length; i++){
            try {
                if(players[i].running && i != senderId){
                    players[i].dataOutputStream.writeUTF(input);
                }
            } catch (IOException e) {

            }
        }
    }
}class ConnectionTester extends Thread{

    private Socket socket;
    private int ID;
    private String gameTag;

    public ConnectionTester(Socket socket, int ID, String gameTag){
        this.socket = socket;
        this.ID = ID;
        this.gameTag = gameTag;
    }

    public void run(){
        while(loop());
        new read("deletePlayer;"+gameTag+";",ID).start();
    }

    private boolean loop(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(socket.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
