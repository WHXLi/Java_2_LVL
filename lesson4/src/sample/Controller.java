package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    @FXML
    public TextField myMessage;
    @FXML
    public VBox myMessageZone;

    public void btnPushClick(ActionEvent actionEvent) {
        if (myMessage.getText().length() > 0){
            TextArea textArea = new TextArea(myMessage.getText());
            textArea.setId("myMessageArea");
            myMessageZone.getChildren().add(textArea);
            textArea.setVisible(true);
            myMessage.setText("");
        }
    }

    public void enterPush(){
        myMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    if (myMessage.getText().length() > 0){
                        TextArea textArea = new TextArea(myMessage.getText());
                        textArea.setId("myMessageArea");
                        myMessageZone.getChildren().add(textArea);
                        textArea.setVisible(true);
                        myMessage.setText("");
                    }
                }
            }
        });
    }

    public void menuCloseClick(ActionEvent actionEvent) {
        Stage stage = (Stage) myMessage.getScene().getWindow();
        stage.close();
    }
}
