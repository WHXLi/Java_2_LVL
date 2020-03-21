package Server;

public interface Authorization {
    String getNameByLoginAndPassword(String login, String password);
}
