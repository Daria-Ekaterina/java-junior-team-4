package com.edu.jet.client;

public class ClientRunner {
    public static void main(String[] args) {
        System.out.println("Welcome to net chat!");
        System.out.println("Type /snd <message> to send message");
        System.out.println("Type /quit to exit the chat");

        Client client = new Client();
        client.startRunning();
    }
}
