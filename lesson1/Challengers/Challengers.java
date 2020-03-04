package lesson1.Challengers;

public interface Challengers {

    // Участники умеют бегать
    void run(int distance);

    // Участники умеют прыгать
    void jump(int height);

    // Участники могут не дойти до конца соревнований
    boolean isActive();

    // Информация об успехах участников выводится в консоль
    void info();
}