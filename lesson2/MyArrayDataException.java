package lesson2;

public class MyArrayDataException extends Exception{
    int x;
    int y;
    public MyArrayDataException(int x, int y){
        super("Неудалось преобразовать ячейку массива. [" + x + "][" + y + "].");
        this.x = x;
        this.y = y;
    }
}