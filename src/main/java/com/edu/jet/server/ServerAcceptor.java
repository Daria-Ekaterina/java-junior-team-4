package com.edu.jet.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ServerAcceptor implements Runnable {

    /*public static void start() {

        try (ServerSocket serverSocket = new ServerSocket(7778)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    while (true) {
                        String message = (String) in.readObject();
                        System.out.println(message);
                    }

                } catch (EOFException e) {

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(7778)) {
//            serverSocket.setSoTimeout(10000);
            while (!Thread.interrupted()) {
                System.out.println("in w1");
                try (Socket socket = serverSocket.accept()) {
                    if (socket != null) {
                        System.out.println("if");
                        Thread thread = new Thread(new ClientSession(socket));
                        thread.start();
                    }
                    Thread.sleep(99999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientSession implements Runnable {
    Socket socket;
    public ClientSession(Socket inputSocket) {
        this.socket=inputSocket;
    }

    @Override
    public void run() {
        System.out.println("run");
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
            System.out.println("INNN");
            while (true) {
                String message = (String) in.readObject();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}