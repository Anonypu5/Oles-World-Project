package no.Strohm.game2D.Multiplayer;

import com.sun.glass.ui.EventLoop;
import no.Strohm.game2D.Game;
import no.Strohm.game2D.state.State;
import no.Strohm.game2D.state.StateGame;
import no.Strohm.game2D.world.World;
import no.Strohm.game2D.world.tiles.Tile;


import static no.Strohm.game2D.state.State.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client extends Thread{

    public Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static String gameTag;
    public static boolean run;

    public Client(String ip, int port, String gameTag){
        try {
            socket = new Socket(ip, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("CLIENT: Checking for room");
            if(dataInputStream.readUTF().equals("room")){
                System.out.println("CLIENT: Found room on server");
                dataOutputStream.writeUTF(Game.VERSION);
                if(dataInputStream.readUTF().equals("version ok")){
                    System.out.println("CLIENT: Version ok");
                    dataOutputStream.writeUTF(gameTag);
                    if(dataInputStream.readUTF().equals("game tag ok")){
                        System.out.println("CLIENT: Game tag ok");
                        Game.client = this;
                        this.gameTag = gameTag;
                        states.get(gameId).start();
                        run = true;
                        this.start();
                    }else{
                        System.out.println("CLIENT: Game tag taken");
                    }
                }else{
                    System.out.println("CLIENT: You have to update your game");
                }
            }else{
                System.out.println("CLIENT: Did not find room on server");
            }
        } catch (Exception e) {
            System.out.println("CLIENT: Could not connect to " + ip + ":" + port);
        }
    }

    public void run(){
        try {
            while (run) {
                String in = dataInputStream.readUTF();
                new input(in).start();
            }
            System.out.println("CLIENT: Exiting loop");
            System.out.println("CLIENT: Disconnecting");
        }catch(Exception e){
            System.out.println("CLIENT: Disconnecting");
            World.onlinePlayers = new OnlinePlayers[0];
            no.Strohm.game2D.state.State.setState(no.Strohm.game2D.state.State.startId);
        }
    }
}class input extends Thread{

    private String input;
    public static boolean quantitySet = false;

    public input(String input){
        this.input = input;
    }
    public void run(){
        try {
            if(input.startsWith("setMapTile;")){
                int x, y, tile;
                input = input.substring("setMapTile;".length());

                for (int i = 0; true; i++) {
                    if (input.startsWith(i + ";")) {
                        x = i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i > Game.mapWidth) {
                        System.out.println("CLIENT: Map width to large: "+input);
                        throw new Exception();
                    }
                }
                for (int i = 0; true; i++) {
                    if (input.startsWith(i + ";")) {
                        y = i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i > Game.mapHeight){
                        System.out.println("CLIENT: Map height to large: "+input);
                        throw new Exception();}
                }
                for (int i = 0; true; i++) {
                    if (input.startsWith(i + ";")) {
                        tile = i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i >= Tile.numberOfTiles){
                        System.out.println("CLIENT: Map tile to large: "+input);
                        throw new Exception();
                    }
                }
                World.tiles[x][y] = Tile.createTile(tile, y, x, StateGame.getWorld());
            }

            else if(input.startsWith("setPlayerQuantity;")){
                System.out.println("CLIENT: Setteing Player Quantity");
                input = input.substring("setPlayerQuantity;".length());
                int number;
                for (int i = 0; true; i++) {
                    if (input.startsWith(i + ";")) {
                        number = i-1;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                }
                World.onlinePlayers = new OnlinePlayers[number];
                quantitySet = true;
            }else if(input.startsWith("setPlayer;")){
                while(!quantitySet){
                    Thread.sleep(1000);
                }
                input = input.substring("setPlayer;".length());
                String gameTag;
                for (int i = 0; true; i++) {
                    if (input.substring(i).startsWith(";")) {
                        gameTag = input.substring(0,i);
                        input = input.substring(gameTag.length()+1);
                        System.out.println("CLIENT: Setteing Player named "+gameTag);
                        break;
                    }
                }
                for(int i = 0; i < World.onlinePlayers.length; i++){
                    if(World.onlinePlayers[i] == null){
                        World.onlinePlayers[i] = new OnlinePlayers(gameTag);
                        break;
                    }
                }
            }else if(input.startsWith("setPlayerStat;")){

                input = input.substring("setPlayerStat;".length());
                String gameTag;
                int anim = 0;
                int dir = 0;
                boolean moving = false;

                for (int i = 0; true; i++) {
                    if (input.substring(i).startsWith(";")) {
                        gameTag = input.substring(0,i);
                        input = input.substring(gameTag.length()+1);
                        break;
                    }
                }
                int x, y;
                for (double i = 0; true; i+=0.5) {
                    if (input.startsWith(i + ";")) {
                        x = (int) i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i > Game.mapWidth*16) {
                        System.out.println("CLIENT: Player x to heigh: "+input);
                        throw new Exception();
                    }
                }

                for (double i = 0; true; i+=0.5) {
                    if (input.startsWith(i + ";")) {
                        y = (int) i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i > Game.mapHeight*16){
                        System.out.println("CLIENT: Player y to heigh: "+input);
                        throw new Exception();}
                }

                if(input.startsWith("true;")){
                    input = input.substring("true;".length());
                    moving = true;
                }else if(input.startsWith("false;")){
                    input = input.substring("false;".length());
                    moving = false;
                }else{
                    System.out.println("CLIENT: Fucked up player-stat message "+gameTag);
                    throw new Exception();
                }

                for (int i = 0; i < 1000; i++) {
                    if (input.startsWith(i + ";")) {
                        anim = (int) i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                }

                for (int i = 0; true; i++) {
                    if (input.startsWith(i + ";")) {
                        dir = (int) i;
                        input = input.substring(String.valueOf(i).length()+1);
                        break;
                    }
                    if (i > 3){
                        System.out.println("CLIENT: Dir to heigh "+input);
                        throw new Exception();}
                }

                for(int i = 0; i < World.onlinePlayers.length; i++) {
                    if (World.onlinePlayers[i] != null && World.onlinePlayers[i].gameTag.equals(gameTag)) {
                        World.onlinePlayers[i].xPos = x;
                        World.onlinePlayers[i].yPos = y;
                        World.onlinePlayers[i].moving = moving;
                        World.onlinePlayers[i].anim = anim;
                        World.onlinePlayers[i].dir = dir;
                    }
                }
            }else if(input.startsWith("deletePlayer;")){
                input = input.substring("deletePlayer;".length());
                String gameTag;
                for (int i = 0; true; i++) {
                    if (input.substring(i).startsWith(";")) {
                        gameTag = input.substring(0,i);
                        input = input.substring(gameTag.length()+1);
                        System.out.println("CLIENT: Deleting Player named "+gameTag);
                        break;
                    }
                }
                for(int i = 0; i < World.onlinePlayers.length; i++){
                    if(World.onlinePlayers[i].gameTag.equals(gameTag)){
                        World.onlinePlayers[i] = null;
                        break;
                    }
                }
            }


        }catch(Exception e){
        }
    }
}
