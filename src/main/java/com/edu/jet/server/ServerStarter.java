package com.edu.jet.server;

public class ServerStarter {
    private ServerStarter(){};
    public static void main(String[] args) {

        Thread threadMain=new Thread(new Acceptor());
        threadMain.start();
        System.out.println("server started");

    }
}
