package Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea chat;
    @FXML
    public TextField message;
    public TextField password;
    public TextField login;
    public HBox messagePanel;
    public HBox authorizationPanel;

    //СОКЕТ
    Socket client;

    //ВХОДНОЙ И ВЫХОДНОЙ ПОТОК
    DataInputStream in;
    DataOutputStream out;

    //АЙПИ АДРЕС, ПОРТ И НАЗВАНИЕ ОКНА КЛИЕНТА ПО-УМОЛЧАНИЮ
    final String IP = "localhost";
    final int PORT = 777;
    final String CHAT_NAME = "Чат";

    //ПЕРЕМЕННАЯ ОТВЕЧАЮЩАЯ ЗА УСПЕШНУЮ АВТОРИЗАЦИЮ
    private boolean authorizationDone;

    //ПЕРМЕННАЯ ДЛЯ ХРАНЕНИЯ НИКНЕЙМА
    private String nickname;

    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ВИЗУАЛЬНЫЕ ИЗМЕНЕНИЯ ОКНА В СЛУЧАЕ УСПЕШНОЙ АВТОРИЗАЦИИ
    public void setAuthorizationDone(boolean authorizationDone) {
        this.authorizationDone = authorizationDone;
        authorizationPanel.setVisible(!authorizationDone);
        authorizationPanel.setManaged(!authorizationDone);
        messagePanel.setVisible(authorizationDone);
        messagePanel.setManaged(authorizationDone);

        //ВЫСТАВЛЕНИЕ ЗНАЧЕНИЙ ПО-УМОЛЧАНИЮ ДЛЯ НИКНЕЙМА И НАЗВАНИЯ ОКНА
        if (!authorizationDone){
            nickname = "";
            setTitle(CHAT_NAME);
        }

        //ОЧИСТКА ЧАТА ОТ СООБЕЩНИЙ ДО ИЛИ ПОСЛЕ АВТОРИЗАЦИИ
        chat.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ИЗНАЧАЛЬНО АВТОРИЗАЦИЯ НЕ ПРОЙДЕНА
        authorizationDone = false;
    }


    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ПОДКЛЮЧЕНИЕ КЛИЕНТА К СЕРВЕРУ
    public void connect(){
        try {
            //ИНИЦИАЛИЗИРУЕМ СОКЕТ, ВХОДНОЙ И ВЫХОДНОЙ ПОТОК
            client = new Socket(IP, PORT);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            //ПОТОК ОТВЕЧАЮЩИЙ ЗА ОБМЕН ДАННЫМИ МЕЖДУ СЕРВЕРОМ И КЛИЕНТОМ
            new Thread(() -> {
                //ЦИКЛ АВТОРИЗАЦИИ
                while (true){
                    try {
                        //ПОЛУЧЕНИЕ СООБЩЕНИЯ ОТ СЕРВЕРА
                        String authorization = in.readUTF();

                        //ПРИСУЖДАЕМ ПОЛЬЗОВАТЕЛЮ НИК И ВЫСТАВЛЯЕМ ЗНАЧЕНИЕ АВТОРИЗАЦИИ НА УСПЕШНОЕ
                        if (authorization.startsWith("/authorizationOK")){
                            nickname = authorization.split(" ")[1];
                            setAuthorizationDone(true);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //МЕНЯЕМ НАЗВАНИЕ ОКНА НА УНИКАЛЬНОЕ ПОЛЬЗОВАТЕЛЬСКОЕ
                setTitle(CHAT_NAME + ". " + nickname);

                //ЦИКЛ РАБОТЫ
                try {
                    while (true) {
                        //ПОЛУЧАЕМ СООБЩЕНИЕ ОТ СЕРВЕРА
                        String message = in.readUTF();

                        //РЕАЛИЗАЦИЯ ВОЗМОЖНОСТИ КЛИЕНТА ВЫЙТИ ПРИ ВВОДЕ КОМАНДЫ
                        if (message.equals("/end")){
                            break;
                        }

                        //ВЫВОД СООБЕЩНИЯ ПОЛЬЗОВАТЕЛЯ В ЧАТ
                        chat.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorizationDone(false);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ОТПРАВКУ СООБЩЕНИЯ ПРИ НАЖАТИИ НА КНОПКУ ИЛИ НА ENTER
    public void sendMessage() {
        try {
            //СЧИТЫВАЕМ СООБЩЕНИЯ С ПОЛЯ ВВОДА И ОТПРАВЛЯЕМ НА СЕРВЕР
            out.writeUTF(message.getText());
            message.clear();
            message.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ПОПЫТКИ АВТОРИЗАЦИИ
    public void tryAuthorization(ActionEvent actionEvent) {
        if (client == null || client.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/authorization " + login.getText().trim() + " " + password.getText().trim());
            login.clear();
            password.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ИЗМЕНЕНИЕ НАЗВАНИЯ ОКНА
    public void setTitle(String title){
        Platform.runLater(()->{
            ((Stage)chat.getScene().getWindow()).setTitle(title);
        });
    }
}
