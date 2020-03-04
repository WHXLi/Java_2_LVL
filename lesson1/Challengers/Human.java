package lesson1.Challengers;

public class Human implements Challengers {
    // Имя
    private String name;

    // Максимальная дистанция бега
    private int maxDistnace;

    // Максимальная высота прыжка
    private int maxHeight;

    // Состояние участия в соревнованиях
    private boolean active;

    // Конструктор
    public Human(String name, int maxDistnace, int maxHeight) {
        this.name = name;
        this.maxDistnace = maxDistnace;
        this.maxHeight = maxHeight;
        this.active = true;
    }

    // Логика бега
    @Override
    public void run(int distance) {
        if (distance <= maxDistnace) {
            System.out.println(name + " успешно пробежал(-ла) " + distance + " (м.)");
        } else {
            System.out.println(
                    name + " вышел(-ла) с дистанции пробежав " + maxDistnace + " (м.)" + " из " + distance + " (м.)");
            active = false;
        }
    }

    // Логика прыжка
    @Override
    public void jump(int height) {
        if (height <= maxHeight) {
            System.out.println(name + " успешно перепрыгнул(-ла) стену длиной " + height + " (м.)");
        } else {
            System.out.println(
                    name + " вышел(-ла) с дистанции прыгнув на " + maxDistnace + " (м.) " + "из " + height + " (м.)");
            active = false;
        }
    }

    // Логика вывода конечного результата соревнований
    @Override
    public void info() {
        if (active) {
            System.out.println(name + " прошел(-ла) все испытания!");
        } else {
            System.out.println(name + " желаем успехов и ждём на следующих соревнованиях!");
        }
    }

    // Возвращение текущего состояния участия в соревнованиях
    @Override
    public boolean isActive() {
        return active;
    }

}