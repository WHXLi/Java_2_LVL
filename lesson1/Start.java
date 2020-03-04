package lesson1;

import lesson1.Challengers.*;
import lesson1.Challenges.*;

class Start {
    public static void main(String[] args) {
        // Массив с участниками
        Challengers[] challengers = { new Human("Всеволод", 300, 1), new Cat("Алинка", 500, 3),
                new Robot("Mr.Robot", 10000, 10) };
        // Массив с препятствиями
        Challenges[] challenges = { new Sprint(500), new Wall(1) };

        System.out.println(); // Для красоты

        // Логика соревнований
        for (Challengers unit : challengers) {
            for (Challenges challenge : challenges) {
                challenge.doIt(unit);
                if (!unit.isActive()) {
                    break;
                }
            }
        }

        System.out.println(); // Для красоты

        //Подведение итогов
        for (Challengers unit : challengers) {
            unit.info();
        }

        System.out.println(); // Для красоты
    }
}