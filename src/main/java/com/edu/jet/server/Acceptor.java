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

class Acceptor implements Runnable {
    public static HashSet<ClientSession> aliveSocketList = new HashSet<>();
    private final int MAX_ONLINE = 1000;
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