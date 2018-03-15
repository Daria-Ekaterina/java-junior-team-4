package com.edu.jet.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

class Client {
    public void send(String message) {
        try (Socket socket = new Socket("127.0.0.1", 7778);
             ObjectOutputStream outputStream = new ObjectOutputStream(
                     socket.getOutputStream())) {

            outputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.send(message());
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