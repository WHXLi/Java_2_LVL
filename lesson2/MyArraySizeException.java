package lesson2;

public class MyArraySizeException extends Exception {
    // Для вывода ошибки с указанием на некорректное число
    private int number;

    public MyArraySizeException(int number) {
        super("Некорректный размер массива = " + number);
        this.number = number;
    }
}