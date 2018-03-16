package com.edu.jet.server;

import com.edu.jet.client.Client;
import com.edu.jet.client.ClientRunner;
import sun.nio.ch.ThreadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Acceptor implements Runnable {
    public static HashSet<SomeClass> aliveSocketList = new HashSet<SomeClass>();
    private final int MAX_ONNLINE = 10;

    @Override
    public void run() {

        try (ServerSocket portListener = new ServerSocket(7778)) {
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
    private String message = "";

    public SomeClass(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Acceptor.aliveSocketList.add(this);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            while (!Thread.interrupted()) {
                Data.sendToAll(objectInputStream.readObject().toString());
                if (!message.equals("")) {
                    System.out.println(message);
                    objectOutputStream.writeObject(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        this.message=message;
    }
}

class Data {
    public static void sendToAll(String message) {
        for (SomeClass a : Acceptor.aliveSocketList) {
            a.write(message);
        }
    }
}


