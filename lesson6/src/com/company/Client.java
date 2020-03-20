package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket client = null;
        DataInputStream in;
        DataOutputStream out;

        String IP = "localhost";
        int PORT = 777;

        Scanner scanner = new Scanner(System.in);

        try {
            client = new Socket(IP,PORT);

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            //ВХОДНОЙ ПОТОК
            Thread input = new Thread(()->{
                try {
                    while (true){
                        String messageFromServer = in.readUTF();
                        if (messageFromServer.equals("/end")){
                            System.out.println("Клиент отключен");
                            break;
                        }
                        System.out.println("Ответ сервера: " + messageFromServer);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            //ВЫХОДНОЙ ПОТОК
            Thread output = new Thread(()->{
                try {
                    while (true){
                        String messageFromServer = scanner.nextLine();
                        out.writeUTF(messageFromServer);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            input.start();
            output.setDaemon(true);
            output.start();

            try {
                input.join();
                output.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                assert client != null;
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
