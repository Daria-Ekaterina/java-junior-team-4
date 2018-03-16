package com.edu.jet.server;

class Handler {
    public static void sendToAll(String message) {
        if (message.equals("/hist")) {
            return;
        }
        Acceptor.saver.save(message);
        for (ClientSession a : Acceptor.aliveSocketList) {
            a.write(message);
        }
    }
}
