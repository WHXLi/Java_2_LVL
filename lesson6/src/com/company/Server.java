package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader in;
        PrintWriter out;
        try {
            server = new ServerSocket(777);
            System.out.println("Сервер запущен, ожидание подключения");
            client = server.accept();
            System.out.println("Клиент подключился");
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message.equals("/end")) {
                            System.out.println("Клиент отключился");
                            break;
                        }
                        out.println("Клиент: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
