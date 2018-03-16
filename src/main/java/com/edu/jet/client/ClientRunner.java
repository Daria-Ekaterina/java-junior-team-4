package com.edu.jet.client;

import java.util.Scanner;

public class ClientRunner {
    private static String name;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to net chat!");
        System.out.println("Type /snd <message> to send message");
        System.out.println("Type /quit to exit the chat");
        System.out.println("Your name:");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        Client client;
        try {
            if (args.length < 2) {
                client = new Client(name);
                client.startRunning();
            } else {
                try {
                    client = new Client(args[0], Integer.parseInt(args[1]));
                    client.startRunning();
                } catch (NumberFormatException e) {
                    System.out.println("Illegal connection arguments");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Emergency exit");
            System.exit(1);
        }
    }
}
