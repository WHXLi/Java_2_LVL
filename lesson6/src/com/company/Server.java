package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client = null;
        DataInputStream in;
        DataOutputStream out;

        int PORT = 777;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен, ожидание подключения...");
            client = server.accept();
            System.out.println("Клиент подключен");

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            //ВХОДНОЙ ПОТОК
            Thread input = new Thread(()->{
                try {
                    while (true){
                        String message = in.readUTF();
                        if (message.equals("/end")){
                            System.out.println("Клиент отключен");
                            break;
                        }
                        System.out.println("Клиент: " + message);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            //ВЫХОДНОЙ ПОТОК
            Thread output = new Thread(()->{
                try {
                    while (true){
                        String message = scanner.nextLine();
                        out.writeUTF(message);
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

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert client != null;
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
