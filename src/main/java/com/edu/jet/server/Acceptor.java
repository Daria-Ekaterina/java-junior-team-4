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

    Set<Socket> listSockets = new HashSet<Socket>();//упорядоченная коллекция уникальных элементов
    // List<String> list = new LinkedList<String>();
    List<Client> clients = new LinkedList<Client>();
    private List<Thread> listThread = new LinkedList<>();
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

    public void stop() {
        for (Socket connect : listSockets) {
            try {
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (Thread threadClient : listThread) {
            threadClient.interrupt();
        }
    }
}

class SomeClass implements Runnable {
    private Socket socket;

    public SomeClass(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            while (!Thread.interrupted()) {
                System.out.println(objectInputStream.readObject().toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO дождаться от Client runble
    }
}



