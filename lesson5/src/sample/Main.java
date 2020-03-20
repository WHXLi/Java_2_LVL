package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Многопоточность");
        primaryStage.setScene(new Scene(root, 600, 900));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static final int size = 10000;
    public static final int h = size / 2;
    public static float[] arr = new float[size];
    public static float[] arrCopy1 = new float[h];
    public static float[] arrCopy2 = new float[size];

    public static void main(String[] args) {launch(args);}
}

