package com.edu.jet.server;

import com.edu.jet.server.saver.MemorySaver;
import com.edu.jet.server.saver.Saver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Acceptor implements Runnable {
    public static HashSet<ClientSession> aliveSocketList = new HashSet<>();
    private final int MAX_ONLINE = 10;
    public static Saver saver = new MemorySaver();

    @Override
    public void run() {

        try (ServerSocket portListener = new ServerSocket(8888)) {
            ExecutorService executorService = Executors.newFixedThreadPool(MAX_ONLINE);
            while (!Thread.interrupted()) {
                try {
                    Socket newConnection = portListener.accept();
                    if (newConnection == null) {
                        throw new ClassCastException();
                    }
                    executorService.submit(new ClientSession(newConnection));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientSession implements Runnable {
    private Socket socket;
    private volatile String message = "";
    private String currentMassage ="";

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
                currentMassage = stringInputStream.readLine();
                Handler.sendToAll(currentMassage);
                if (currentMassage.startsWith("/hist")) {
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


