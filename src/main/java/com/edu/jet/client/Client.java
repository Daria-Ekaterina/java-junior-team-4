package com.edu.jet.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

class Client {
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 7778;

    public void startRunning() {

        try (Socket clientSocket = new Socket(IP_ADDRESS, PORT)) {

            try (BufferedReader clientConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                String userInputMessageLine;

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

                    System.out.println(userInputMessageLine);
                }

                System.out.println("You have left the chat");
            } catch (IOException e) {
                System.out.println("IO error has occurred");
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host. Connection failed");
        } catch (IOException e) {
            System.out.println("IO error has occurred");
        }



    }
    public void send(String message) {
        try (Socket socket = new Socket(IP_ADDRESS, PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(
                     socket.getOutputStream())) {

            outputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String message(){
        Scanner input = new Scanner(System.in);
        String message = input.nextLine();
        if(message.contains("/snd")) {
            return message.substring(5)+(new Date(System.currentTimeMillis())).toString();
        }
        return message="";
    }
}