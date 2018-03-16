package com.edu.jet.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Aleksey Bondarenko, Andrey Movchan
 */

public class Client {
    private final String IP_ADDRESS;
    private final int PORT;

    public Client() {
        IP_ADDRESS = "localhost";
        PORT = 7778;
    }

    public Client(String ipAddress, int port) {
        IP_ADDRESS = ipAddress;
        PORT = port;
    }

    //TODO постараться сделать метод компактнее
    public void startRunning() {
        String userInputMessageLine;
        String messageToServer;

        try (Socket clientSocket = new Socket(IP_ADDRESS, PORT);
             ObjectOutputStream clientOutputStream =
                     new ObjectOutputStream(clientSocket.getOutputStream());
              ObjectInputStream clinentInputStream =
                     new ObjectInputStream(clientSocket.getInputStream())
            ) {

            try (BufferedReader clientConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {

                while (!(userInputMessageLine = clientConsoleReader.readLine()).equals("/quit")) {
                    if (userInputMessageLine.length() > 150) {
                        System.out.println("Your message must be less than 150 characters");
                        continue;
                    }

                    //TODO make check with regexp
                    if (!(userInputMessageLine.toLowerCase().contains("/snd")) ||
                            userInputMessageLine.toLowerCase().contains("/hist")) {
                        System.out.println("Illegal command. Try again please");
                        continue;
                    }

                    if (userInputMessageLine.substring(4).replaceAll(" ", "").isEmpty()) {
                        System.out.println("Your message can not contain only spaces");
                    } else {
                        messageToServer = getTime() + userInputMessageLine.substring(4);
                        clientOutputStream.writeObject(messageToServer);
                        System.out.println(clinentInputStream.readObject().toString());

                    }
                }

                System.out.println("You have left the chat");
            } catch (IOException e) {
                System.out.println("IO error has occurred");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host. Connection failed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO error has occurred");
        }
    }

    //TODO обсудить с заказчиком формат даты
    private String getTime() {
        SimpleDateFormat date = new SimpleDateFormat("'['dd.MM.yyyy HH:mm']'");
//        DateFormat date = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        return date.format(new Date());
    }
}