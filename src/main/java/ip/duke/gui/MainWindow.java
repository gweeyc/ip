package ip.duke.gui;

import static javafx.application.Platform.exit;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import ip.duke.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private final Image userImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/DaUser.png")));
    private final Image dukeImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/DaDuke.png")));
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
        outputMsg(d.sendGreetings());
    }

    private void outputMsg(String out) {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(out, dukeImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.matches("b|bye")) {
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    exit();
                    System.exit(0);
                }
            }, 1500);
        }
    }
}
