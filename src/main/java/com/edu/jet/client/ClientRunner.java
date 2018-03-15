package com.edu.jet.client;

public class ClientRunner {
    public static void main(String[] args) {
        System.out.println("Welcome to net chat!");
        System.out.println("Type /snd <message> to send message");
        System.out.println("Type /quit to exit the chat");

        Client client;
        if (args.length < 2) {
            client = new Client();
            client.startRunning();
        } else {
            try {
                client = new Client(args[0], Integer.parseInt(args[1]));
                client.startRunning();
            } catch (NumberFormatException e) {
                System.out.println("Illegal connection arguments");
            }
        }
    }
}
