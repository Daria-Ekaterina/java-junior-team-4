package com.edu.jet.server;

public class ServerStarter {
    public static void main(String[] args) {


        Acceptor acceptor=new Acceptor();
        Thread threadMain=new Thread();
        acceptor.run();


    }
}
