package Server;

public interface Authorization {
    String getNameByLoginAndPassword(String login, String password);

    boolean registration(String login, String password, String nickname);

}
