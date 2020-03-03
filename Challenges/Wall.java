package Challenges;

import Challengers.Challengers;

public class Wall implements Challenges {

    // Высота стены
    private int height;

    // Конструктор
    public Wall(int height) {
        this.height = height;
    }

    // Через стену участники перепрыгивают
    @Override
    public void doIt(Challengers challengers) {
        challengers.jump(height);
    }

}