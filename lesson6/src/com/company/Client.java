package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String IP = "localhost";
        int PORT = 777;
        BufferedReader in;
        PrintWriter out;
        Socket client = null;
        try {
            client = new Socket(IP, PORT);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            new Thread(() -> {
                try {
                    while (true) {
                        String message = scan.nextLine();
                        out.println(message);
                        String readMessage = in.readLine();
                        System.out.println(readMessage);
                    }
                } catch (IOException e) {
                    System.err.println("�訡�� �� ����祭�� ᮮ�饭��.");
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
