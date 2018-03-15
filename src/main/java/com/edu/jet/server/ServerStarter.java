package com.edu.jet.server;

public class ServerStarter {
    public static void main(String[] args) {
        Thread serverAcceptor = new Thread (new ServerAcceptor());
        serverAcceptor.start();
        Runtime.getRuntime().addShutdownHook(
                new Thread(serverAcceptor::interrupt));
    }
}
