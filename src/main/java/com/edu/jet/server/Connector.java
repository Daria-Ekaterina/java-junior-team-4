package com.edu.jet.server;

import java.io.*;
import java.net.Socket;

public class Connector {
    private  String IP_ADDRESS;
    private  int PORT;
    public Connector(String address, int port){
        this.IP_ADDRESS=address;
        this.PORT=port;
    }

    public void connect(){
        try(Socket connection = new Socket(IP_ADDRESS,PORT);BufferedWriter outS = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        BufferedReader inS=new BufferedReader(new InputStreamReader(connection.getInputStream()));){
            while (true){
                outS.write("lil");
                outS.newLine();
                outS.flush();
                System.out.println();
                Thread.sleep(1000);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Client{
        public static void main(String[] args) {
            Connector connector=new Connector("localhost",7778);
            connector.connect();
        }
    }
}
