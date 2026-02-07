package jelly;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jelly jelly;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image jellyImage = new Image(this.getClass().getResourceAsStream("/images/Jelly.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Jelly instance */
    public void setJelly(Jelly jelly) {
        this.jelly = jelly;
        setJellyWelcome();
    }

    public void setJellyWelcome() {
        this.dialogContainer.getChildren().add(
                DialogBox.getJellyDialog(jelly.getGreeting(), this.jellyImage)
        );
    }


    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = jelly.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJellyDialog(response, jellyImage)
        );
        userInput.clear();
    }
}