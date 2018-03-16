package com.edu.jet.server;
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

public class Acceptor {
    public static String clients;
    Set<Socket> listSockets = new HashSet<Socket>();//упорядоченная коллекция уникальных элементов
    List<String> list = new LinkedList<String>();

    public void startServer() {
        try (ServerSocket portListener = new ServerSocket(7778);) {
            while (true) {
                try (Socket clientSession = portListener.accept(); ObjectInputStream message = new ObjectInputStream(clientSession.getInputStream())) {
                    //TODO обсудить хранине сокетов в листе
                    ObjectOutputStream sendBack=new ObjectOutputStream(clientSession.getOutputStream());
                    //listSockets.add(clientSession);
                    //System.out.println(message.readObject().toString());
                    //TODO обсудить порядок выполнения запросов клиент-сервер-клиент
                    System.out.println(message.readObject().toString());
                    //TODO записывать сообщения в очередь, пока сервер отправляет новое сообщение всем пользователям
                   // list.add(message.readObject().toString());
                    System.out.println(message.readObject().toString());

                    sendBack.writeObject(message);
              //      sendMessageToAll(message.readObject().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод отправляет сообщиние всем активным пользователям.
     *
     */
    private synchronized void sendMessageToAll(String message) throws IOException {
//            for(Socket so:listSockets){
//                ObjectOutputStream sendBack=new ObjectOutputStream(so.getOutputStream());
//                sendBack.writeObject(message);
//            }
    }
}
