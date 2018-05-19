package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Controller {

    @FXML
    private TextArea textView;

    @FXML
    private Button loadFileButton;


    public void initialize() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik do odczytu");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text file", Collections.singletonList("txt")));
        loadFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(loadFileButton.getScene().getWindow());
                try {
                    Files.readAllLines(Paths.get(file.toURI())).forEach(line -> consume(line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void  consume(String input) {
        textView.appendText("==== " + input + " ==== \n");
        StateMachine stateMachine = new StateMachine();
        for (Character character : input.toCharArray()) {
            stateMachine.proccess(String.valueOf(character));
        }
        textView.appendText(stateMachine.getHistory() + "\n");
        textView.appendText(stateMachine.getResult().name() + "\n");
    }

}
