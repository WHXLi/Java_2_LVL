package Server;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthorization implements Authorization {
    private class UserData {

        //ПЕРЕМЕННЫЕ ДЛЯ ХРАНЕНИЯ ДАННЫХ ПОЛЬЗОВАТЕЛЯ
        private String login;
        private String password;
        private String nickname;

        //КОНСТРУКТОР
        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    //СПИСОК ПОЛЬЗОВАТЕЛЕЙ ДЛЯ ХРАНЕНИЯ ДАННЫХ О НИХ
    private List<UserData> users;

    public SimpleAuthorization() {
        users = new ArrayList<>();

        //ПОЛЬЗОВАТЕЛЬСКИЕ УЧЁТНЫЕ ЗАПИСИ
        for (int i = 1; i <= 10; i++) {
            users.add(new UserData("login" + i, "password" + i, "Пользователь_" + i ));

        }

        //АДМИНИСТРАТОРСКАЯ УЧЁТНАЯ ЗАПИСЬ
        users.add(new UserData("admin", "admin", "admin"));
    }

    //МЕТОД ОТВЕЧАЮЩИЙ ЗА ПОЛУЧЕНИЕ НИКНЕЙМА ЧЕРЕЗ ЛОГИН И ПАРОЛЬ
    @Override
    public String getNameByLoginAndPassword(String login, String password) {
        for (UserData user: users) {
            if (user.login.equals(login) && user.password.equals(password)){
                return user.nickname;
            }
        }
        return null;
    }
}
