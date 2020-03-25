package Client;

import Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    //ССЫЛКИ НА СОКЕТЫ
    private Server server;
    private Socket client;

    //ВХОДНОЙ И ВЫХОДНОЙ ПОТОКИ
    private DataInputStream in;
    private DataOutputStream out;

    //ПЕРЕМЕННЫЕ ДЛЯ ХРАНЕНИЯ НИКНЕЙМА И ЛОГИНА
    private String nickname;
    private String privateName;
    private String login;

    public String getNickname() {
        return nickname;
    }

    public String getPrivateName() {
        return privateName;
    }

    //КОНСТРУКТОР
    public ClientHandler(Socket client, Server server) {
        try {
            this.client = client;
            this.server = server;
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            //ПОТОК ОТВЕЧАЮЩИЙ ЗА ОБМЕН ДАННЫМИ МЕЖДУ СЕРВЕРОМ И КЛИЕНТОМ
            new Thread(() -> {
                try {
                    //ЦИКЛ АВТОРИЗАЦИИ
                    while (true){
                        //ПОЛУЧАЕМ СООБЩЕНИЕ ОТ СЕРВЕРА
                        String authorization = in.readUTF();

                        //СЧИТЫВАЕМ ИНФОРМАЦИЮ ИЗ ПОПЫТКИ ВХОДА
                        if (authorization.startsWith("/authorization ")){
                            //РАЗБИВАЕМ СТРОКУ ЧЕРЕЗ ПРОБЕЛ
                            String[] token = authorization.split(" ");
                            //ПОЛУЧАЕМ НИКНЕЙМ ЧЕРЕЗ ЛОГИН И ПАРОЛЬ ПОЛЬЗОВАТЕЛЯ
                            String newNickname = server.getAuthorization().getNameByLoginAndPassword(token[1],token[2]);

                            //В СЛУЧАЕ УСПЕШНОЙ АВТОРИЗАЦИИ ПРИСУЖДАЕМ ПОЛЬЗОВАТЕЛЮ НИКНЕЙМ
                            if (newNickname != null){
                                sendMessage("/authorizationOK " + newNickname);
                                nickname = newNickname;
                                login = token[1];
                                server.subscribeAdd(this);
                                System.out.println("Клиент " + nickname + " авторизовался");
                                break;
                            }else {
                                sendMessage("Неверный логин / пароль");
                            }
                        }
                    }

                    //ЦИКЛ РАБОТЫ
                    while (true) {
                        //ПОЛУЧАЕМ СООБЩЕНИЕ ОТ СЕРВЕРА
                        String message = in.readUTF();

                        //ВОЗМОЖНОСТЬ ВЫХОДА ДЛЯ КЛИЕНТА
                        if (message.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }

                        //ВОЗМОЖНОСТЬ ОТПРАВКИ ПРИВАТНОГО СООБЩЕНИЯ (НЕ РАБОТАЕТ)
                        if (message.startsWith("/w ")){
                            String[] token = message.split(" ");
                            privateName = token[1];
                            server.privateMessage(message,nickname);
                        } else {
                            //ВЫВОДИМ СООБЩЕНИЕ ПОЛЬЗОВАТЕЛЯ В ЧАТ
                            server.broadcastMessage(message, nickname);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //В СЛУЧАЕ ВЫХОДА КЛИЕНТА, ОН УДАЛЯЕТСЯ ИЗ СПИСКА ОБРАБАТЫВАЕМЫХ КЛИЕНТОВ
                    server.subscribeDel(this);
                    System.out.println(nickname + " отключен");
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ОТПРАВКУ СООБЩЕНИЙ НА СЕРВЕР
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
