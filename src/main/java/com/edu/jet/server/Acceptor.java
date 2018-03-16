package com.edu.jet.server;

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
    public static HashSet<SomeClass> aliveSocketList = new HashSet<>();
    private final int MAX_ONNLINE = 10;

    @Override
    public void run() {

        try (ServerSocket portListener = new ServerSocket(8888)) {
            ExecutorService executorService = Executors.newFixedThreadPool(MAX_ONNLINE);
            while (!Thread.interrupted()) {
                try {
                    Socket newConnection = portListener.accept();
                    if (newConnection == null) {
                        throw new ClassCastException();
                    }
                    executorService.submit(new SomeClass(newConnection));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SomeClass implements Runnable {
    private Socket socket;
    private volatile String message = "";

    public SomeClass(Socket socket) {
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
//                        System.out.println(message);
                        stringOutputStream.println(message);
                        message = "";
                    }
                }
            });
            thread.start();
            while (!Thread.interrupted()) {
                Data.sendToAll(stringInputStream.readLine());
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

        //TODO дождаться от Client runble
    }

    public void write(String message) {
        this.message = message;
    }
}

class Data {
    public static void sendToAll(String message) {
        for (SomeClass a : Acceptor.aliveSocketList) {
            a.write(message);
        }
    }
}


