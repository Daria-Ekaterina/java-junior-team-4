package com.edu.jet.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientSession implements Runnable {
    private Socket socket;
    private volatile String message = "";

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Acceptor.aliveSocketList.add(this);
        try (BufferedReader stringInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter stringOutputStream = new PrintWriter(socket.getOutputStream(), true)
        ) {
            Thread thread = new Thread(() -> {
                while (true) {
                    if (!message.equals("")) {
                        stringOutputStream.println(message);
                        message = "";
                    }
                }
            });
            thread.start();
            while (!Thread.interrupted()) {

                Handler.sendToAll(stringInputStream.readLine());
                if (message.startsWith("/hist")) {
                    for (String string : Acceptor.saver.getData()) {
                        stringOutputStream.println(string);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                Acceptor.aliveSocketList.remove(socket);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void write(String message) {
        this.message = message;
    }
}
