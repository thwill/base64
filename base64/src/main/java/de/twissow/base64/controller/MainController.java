package de.twissow.base64.controller;

import de.twissow.base64.Base64EnDeCoder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML ImageView imageView;

    @FXML TextArea textArea;

    @FXML Button buttonResetImage;

    @FXML Button buttonLoadImage;

    @FXML Button buttonEncode;

    @FXML Button buttonResetText;

    @FXML Button buttonPasteText;

    @FXML Button buttonCopyText;

    @FXML Button buttonDecode;

    @Inject Base64EnDeCoder base64EnDeCoder;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        imageView.setLayoutX(10);
        imageView.setLayoutY(10);
        textArea.setMinWidth(300);
        textArea.setMinHeight(300);
        buttonLoadImage.setLayoutX(10);
        buttonLoadImage.setLayoutY(300);
        buttonLoadImage.setMinWidth(100);
        buttonResetImage.setLayoutX(120);
        buttonResetImage.setLayoutY(300);
        buttonResetImage.setMinWidth(100);
        buttonResetImage.setDisable(true);
        buttonEncode.setLayoutX(230);
        buttonEncode.setLayoutY(300);
        buttonEncode.setMinWidth(100);
        buttonEncode.setDisable(true);
        textArea.setLayoutX(10);
        textArea.setLayoutY(350);
        textArea.setMinWidth(400);
        textArea.setMinHeight(400);
        textArea.setStyle(".scrollpane");
        buttonPasteText.setLayoutX(10);
        buttonPasteText.setLayoutY(760);
        buttonPasteText.setMinWidth(100);
        buttonResetText.setLayoutX(120);
        buttonResetText.setLayoutY(760);
        buttonResetText.setMinWidth(100);
        buttonResetText.setDisable(true);
        buttonCopyText.setLayoutX(230);
        buttonCopyText.setLayoutY(760);
        buttonCopyText.setMinWidth(100);
        buttonCopyText.setDisable(true);
        buttonDecode.setLayoutX(340);
        buttonDecode.setLayoutY(760);
        buttonDecode.setMinWidth(100);
        buttonDecode.setDisable(true);
        textArea.setDisable(true);
        textArea.setEditable(false);
    }

    @FXML
    private void resetImage() {
        base64EnDeCoder.setFileContent(null);
        imageView.setImage(null);
        buttonResetImage.setDisable(true);
        buttonEncode.setDisable(true);
    }

    @FXML
    private void resetText() {
        textArea.setText(null);
        buttonResetText.setDisable(true);
        buttonDecode.setDisable(true);
        buttonCopyText.setDisable(true);
    }

    @FXML
    private void copyText() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(textArea.getText());
        clipboard.setContent(content);
        textArea.setText(null);
        buttonDecode.setDisable(true);
        buttonResetText.setDisable(true);
        buttonCopyText.setDisable(true);
    }

    @FXML
    private void pasteText() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        String base64 = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        textArea.setText(base64);
        if (textArea.getText() != null) {
            base64EnDeCoder.setFileContent(null);
            imageView.setImage(null);
            buttonDecode.setDisable(false);
            buttonResetText.setDisable(false);
            buttonCopyText.setDisable(false);
        }
    }

    @FXML
    private void loadImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("GIFF", "*.giff")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(new FileInputStream(file));
            imageView.setImage(image);
            base64EnDeCoder.setFileContent(Files.readAllBytes(file.toPath()));
            buttonResetImage.setDisable(false);
            buttonEncode.setDisable(false);
        }
    }

    @FXML
    private void decode() {
        base64EnDeCoder.setFileContent(null);
        base64EnDeCoder.setEncodedString(textArea.getText());
        Image image = new Image(new ByteArrayInputStream(base64EnDeCoder.decode()));
        imageView.setImage(image);
        textArea.setText(null);
        buttonCopyText.setDisable(true);
        buttonResetText.setDisable(true);
        buttonDecode.setDisable(true);
        buttonResetImage.setDisable(false);
        buttonEncode.setDisable(false);

    }

    @FXML
    private void encode() {
        textArea.setText(base64EnDeCoder.encode());
        buttonResetText.setDisable(false);
        buttonCopyText.setDisable(false);
        imageView.setImage(null);
        buttonEncode.setDisable(true);
        buttonResetImage.setDisable(true);
        buttonDecode.setDisable(false);
    }
}
