package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import static sample.Main.*;

public class Controller {

    @FXML
    public TextArea arr1FieldMtd1;
    @FXML
    public TextArea arr2FieldMtd1;
    @FXML
    public TextArea timerMtd1;
    @FXML
    public TextArea arr1FieldMtd2;
    @FXML
    public TextArea arr2FieldMtd2;
    @FXML
    public TextArea timerMtd2;

    public void startBtn() {
        arrInit(arr);
        arrEdit1(arr);
        halfInit();
        arrEdit2(arr);
        halfConnect();
    }

    public void arrInit(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
            arr1FieldMtd1.appendText(i + ": " + arr[i] + "\n");
            arr1FieldMtd2.appendText(i + ": " + arr[i] + "\n");
        }
    }

    public void arrEdit1(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            mathForm(arr, i);
            arr2FieldMtd1.appendText(i + ": " + arr[i] + "\n");
        }
        timerMtd1.appendText("Время расчёта: " + (System.currentTimeMillis() - a) + ". ");
    }

    public void arrEdit2(float[] arr){
        long a = System.currentTimeMillis();
        new Thread(() -> halfEdit1()).start();
        new Thread(() -> halfEdit2()).start();
        for (int i = 0; i < arr.length; i++) {
            arr2FieldMtd2.appendText(i + ": " + arr[i] + "\n");
        }
        timerMtd2.appendText("Время расчёта: " + (System.currentTimeMillis() - a)  + "; \n");
    }

    public void mathForm(float[] arr, int i) {
        arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }

    public void halfInit(){
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arrCopy1, 0, h);
        System.arraycopy(arr, h, arrCopy2, 0, h);
        timerMtd2.appendText("Время разбивки массива: " + (System.currentTimeMillis() - a)  + "; \n");
    }

    public void halfConnect(){
        long a = System.currentTimeMillis();
        System.arraycopy(arrCopy1, 0, arr, 0, h);
        System.arraycopy(arrCopy2, 0, arr, h, h);
        timerMtd2.appendText("Время склейки массива: " + (System.currentTimeMillis() - a)  + "; \n");
    }

    public synchronized void halfEdit1() {
        for (int i = 0; i < arrCopy1.length; i++) {
            mathForm(arrCopy1, i);
        }
        System.arraycopy(arrCopy1, 0, arr, 0, h);
    }

    public synchronized void halfEdit2() {
        for (int i = h; i < arr.length; i++) {
            mathForm(arrCopy2, i);
        }
    }
}
