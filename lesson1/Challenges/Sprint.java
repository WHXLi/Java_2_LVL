package lesson1.Challenges;

import lesson1.Challengers.Challengers;

public class Sprint implements Challenges {

    // Длина дороги
    private int distance;

    // Конструктор
    public Sprint(int distance) {
        this.distance = distance;
    }

    // По дороге участники бегут
    @Override
    public void doIt(Challengers challengers) {
        challengers.run(distance);
    }

}