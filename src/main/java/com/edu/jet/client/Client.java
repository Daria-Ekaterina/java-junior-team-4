package com.edu.jet.client;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

class Client {
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 7778;

    public static void main(String[] args) {
        System.out.println("Welcome to net chat!");
        System.out.println("Type /snd <message> to send message");

        try (BufferedReader clientConsoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInputMessageLine;
            while (!(userInputMessageLine = clientConsoleReader.readLine()).equals("/quit")) {
                if (userInputMessageLine.length() > 150) {
                    System.out.println("Your message must be less than 150 characters");
                    continue;
                }
                System.out.println(userInputMessageLine);
            }
            System.out.println("Exited chat");
        } catch (IOException e) {
            e.printStackTrace();
        }







//        Client client = new Client();
//        client.send(message());
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