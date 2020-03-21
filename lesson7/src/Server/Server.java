package Server;

import Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Server {

    //СПИСОК КЛИЕНТОВ
    private Vector<ClientHandler> clients;

    //ССЫЛКА НА ИНТЕРФЕЙС АВТОРИЗАЦИИ
    private Authorization authorization;


    //ГЕТТЕР
    public Authorization getAuthorization() {
        return authorization;
    }

    public Server() {
        //СОКЕТЫ
        ServerSocket server = null;
        Socket client = null;

        //ПОРТ
        final int PORT = 777;

        //ИНИЦИАЛИЗАЦИЯ СПИСКА КЛИЕНТОВ И АВТОРИЗАЦИИ
        clients = new Vector<>();
        authorization = new SimpleAuthorization();

        try {
            //СОЗДАЕМ СЕРВЕР
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен, ожидание подключения...");

            while (true) {
                //ОЖИДАЕМ ПОДКЛЮЧЕНИЯ КЛИЕНТА
                client = server.accept();
                System.out.println("Клиент подключен: " + client.getLocalSocketAddress());

                //СОЗДАЕМ ЭКЗЕМПЛЯР КЛАССА ОТВЕЧАЮЩЕГО ЗА РАБОТУ С ПОДКЛЮЧЕННЫМ КЛИЕНТОМ
                new ClientHandler(client, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    //МЕТОД ДЛЯ МАССОВОЙ ОТПРАВКИ СООБЩЕНИЙ
    public void broadcastMessage(String message, String nickname) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(nickname + ": " + message);
        }
    }

    //МЕТОД ДЛЯ ПРИВАТНОЙ ОТПРАВКИ СООБЩЕНИЙ (НЕ РАБОТАЕТ)
    public void privateMessage(String message, String nickname){
        for (ClientHandler clientHandler: clients) {
            //ИЩУ СРЕДИ КЛИЕНТОВ ТОГО, КОМУ ОТПРАВЛЯЮТ СООБЩЕНИЕ
            if (clientHandler.getNickname().equals(clientHandler.getPrivateName())){
                clientHandler.sendMessage(nickname + ": " + message);
            }
        }
    }

    //МЕТОД ДОБАВЛЕНИЯ КЛИЕНТА В КЛАСС ОТВЕЧАЮЩИЙ ЗА РАБОТУ С ПОДКЛЮЧЕННЫМ КЛИЕНТОМ
    public void subscribeAdd(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    //МЕТОД УДАЛЕНИЯ КЛИЕНТА В КЛАСС ОТВЕЧАЮЩИЙ ЗА РАБОТУ С ПОДКЛЮЧЕННЫМ КЛИЕНТОМ
    public void subscribeDel(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
