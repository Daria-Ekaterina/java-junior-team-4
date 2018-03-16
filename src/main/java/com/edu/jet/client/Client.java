package com.edu.jet.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Aleksey Bondarenko, Andrey Movchan
 */

public class Client {
    private final String IP_ADDRESS;
    private final int PORT;

    public Client() {
        IP_ADDRESS = "localhost";
        PORT = 8888;
    }

    public Client(String ipAddress, int port) {
        IP_ADDRESS = ipAddress;
        PORT = port;
    }

    //TODO постараться сделать метод компактнее
    public void startRunning() throws InterruptedException {
        String userInputMessageLine;
        String messageToServer;

        try (Socket clientSocket = new Socket(IP_ADDRESS, PORT);
             PrintWriter clientOutputStream =
                     new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader clientInputStream =
                     new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            try (Scanner scanner = new Scanner(System.in)) {
                Thread thread = new Thread(() -> {

                    while (true) { //thread INPUT
                        try {
                            System.out.println(clientInputStream.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                Thread.sleep(2_000);
                while (!(userInputMessageLine = scanner.nextLine()).equals("/quit")) { //Thread OUTPUT
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
                        clientOutputStream.println(messageToServer);

                    }
                }

                System.out.println("You have left the chat");
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