package com.example.lopu_projekt;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class Wordle_fr extends Application {

    private Button[][] squareButtons;
    private Button[][] keyboardButtons;
    private int currentSquareIndex = 0;
    private int currentRowIndex = 0;
    private Kontroll kontroll;
    private Sõna otsitavSõna;
    private Täht[][] keyboard;
    private String[][] keyboardLayout;
    private BorderPane juur;
    private Scene stseen;
    private StackPane suurJuur;
    private static Font font = Font.loadFont("file:src/Condiment-Regular.ttf", 25);
    private static Font keyboardfont = Font.loadFont("file:src/Condiment-Regular.ttf", 16);


    public Wordle_fr() throws IOException {
        initializeGame();
    }

    private void initializeGame() throws IOException {
        otsitavSõna = new Sõna();
        System.out.println(otsitavSõna.getOtsitavSõna());
        squareButtons = new Button[6][5];
        juur = new BorderPane();

        keyboardLayout = new String[][]{
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ž"},
                {"Z", "X", "C", "V", "B", "N", "M", "ENTER", "DELETE", "Š"}
        };

        keyboard = new Täht[3][10];
        keyboardButtons = new Button[3][10];

        for (int i = 0; i < 3; i++) {
            for (int t = 0; t < keyboardLayout[i].length; t++) {
                keyboard[i][t] = new Täht(keyboardLayout[i][t].toLowerCase(), "lightgrey");
            }
        }

        kontroll = new Kontroll(keyboard);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Wordle");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        for (int row = 0; row < keyboardLayout.length; row++) {
            for (int col = 0; col < keyboardLayout[row].length; col++) {
                Button button = new Button(keyboardLayout[row][col]);
                if (keyboardLayout[row][col].equals("ENTER")) {
                    button.setPrefSize(70, 50);
                    button.setStyle("-fx-background-color: LIGHTGRAY; -fx-text-fill: #1d1e19");

                } else if (keyboardLayout[row][col].equals("DELETE")) {
                    button.setPrefSize(70, 50);
                    button.setStyle("-fx-background-color: LIGHTGRAY; -fx-text-fill: #1d1e19");
                } else {
                    button.setPrefSize(35, 50);
                    button.setStyle("-fx-background-color: LIGHTGRAY; -fx-text-fill: #1d1e19");
                }
                button.setOnAction(event -> handleKeyboardButtonPress(button.getText()));
                button.setFont(keyboardfont);
                GridPane.setConstraints(button, col, row);
                gridPane.getChildren().add(button);
                keyboardButtons[row][col] = button;
            }
        }

        VBox keyboardBox = new VBox(gridPane);
        keyboardBox.setAlignment(Pos.BOTTOM_CENTER);

        GridPane topGridPane = new GridPane();
        topGridPane.setHgap(5);
        topGridPane.setVgap(5);
        topGridPane.setAlignment(Pos.BOTTOM_CENTER);


        for (int row = 0; row < squareButtons.length; row++) {
            for (int col = 0; col < squareButtons[row].length; col++) {
                Button button = new Button();
                button.setStyle("-fx-border-color: #1d1d1b; -fx-border-width: 2px; -fx-background-color: #ea6196; -fx-text-fill: #faf1db");
                button.setFont(font);
                //button.setStyle("-fx-background-color: LIGHTGREY; -fx-text-fill: PURPLE");
                //button.setBackground(new Background(new BackgroundFill(Paint.valueOf("grey"), null, null)));
                button.setPrefSize(90, 90);
                squareButtons[row][col] = button;
                topGridPane.add(button, col, row);
            }
        }

        juur.setTop(topGridPane);
        juur.setBottom(keyboardBox);
        juur.setPadding(new Insets(10));
        juur.setStyle("-fx-background-color: #fef9e5");

        suurJuur = new StackPane(juur);
        stseen = new Scene(suurJuur, 500, 750);
        // stseen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stseen.setOnKeyTyped(e -> {
            if (e.getCharacter().equals("\r") || e.getCharacter().equals("\n")) {
                enterPressed();
            } else {
                String letter = e.getCharacter().toUpperCase();
                System.out.println(e.getCharacter().strip().length());
                if (letter.length() > 0) {
                    if (currentSquareIndex < 5) {
                        squareButtons[currentRowIndex][currentSquareIndex].setText(letter);
                        currentSquareIndex++;
                    }
                }
            }
        });

        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(500);
        primaryStage.setScene(stseen);
        primaryStage.show();

    }

    private void enterPressed() {
        if (currentSquareIndex == 5) {
            StringBuilder word = new StringBuilder();

            for (int i = 0; i < currentSquareIndex; i++) {
                word.append(squareButtons[currentRowIndex][i].getText());
            }

            System.out.println(word.toString().toLowerCase());
            if (kontroll.kontrolliSõna(word.toString().toLowerCase(), otsitavSõna.getOtsitavSõna())) {
                endButton("Slaaaaayyyy, sinu voit!");
            } else if (currentRowIndex == 5) {
                endButton("Sorry beib, kaotasid!");
            }

            changeLetterColors();

            currentSquareIndex = 0;
            currentRowIndex++;
        }
    }

    private void changeLetterColors() {
        for (int i = 0; i < keyboardButtons.length; i++) {
            for (int j = 0; j < keyboardButtons[i].length; j++) {
                keyboardButtons[i][j].setStyle("-fx-background-color: %s; -fx-text-fill: #1d1e19".formatted(kontroll.getTähed()[i][j].getVärv()));
                keyboardButtons[i][j].setFont(keyboardfont);
            }
        }

        for (int i = 0; i < squareButtons[currentRowIndex].length; i++) {

            char currentLetter = squareButtons[currentRowIndex][i].getText().toLowerCase().charAt(0);
            if (currentLetter == otsitavSõna.getOtsitavSõna().charAt(i)) {
                squareButtons[currentRowIndex][i].setStyle("-fx-border-color: #1d1d1b; -fx-border-width: 2px;-fx-background-color: #77b780; -fx-text-fill: #fef9e3"); // ROHELINE
            } else if (otsitavSõna.getOtsitavSõna().contains(String.valueOf(currentLetter))) {
                squareButtons[currentRowIndex][i].setStyle("-fx-border-color: #1d1d1b; -fx-border-width: 2px;-fx-background-color: #fbd62f; -fx-text-fill: #fef9e3"); // KOLLANE
            }
            else {
                squareButtons[currentRowIndex][i].setStyle("-fx-border-color: #1d1d1b; -fx-border-width: 2px;-fx-background-color: #f98eb8; -fx-text-fill: #fef9e3"); // HALL
            }
            squareButtons[currentRowIndex][i].setFont(font);
        }
    }
    private void handleKeyboardButtonPress(String letter) {
        if (letter.equals("DELETE") && currentSquareIndex > 0) {
            squareButtons[currentRowIndex][currentSquareIndex - 1].setText("");
            currentSquareIndex--;
        }  else if (letter.equals("ENTER") && currentSquareIndex == 5) {
            enterPressed();
        } else if (!letter.equals("ENTER") && currentSquareIndex < 5) {
            squareButtons[currentRowIndex][currentSquareIndex].setText(letter);
            currentSquareIndex++;
        }
    }

    public void endButton(String text) {
        Button closeGame = new Button(text);

        closeGame.setFont(font);
        closeGame.setStyle("-fx-border-color: #1d1d1b; -fx-border-width: 2px; -fx-background-color: #fef9e3; -fx-text-fill: #e75e94");
        closeGame.setOnAction(e -> System.exit(0));
        closeGame.setPrefSize(250, 30);
        closeGame.setLayoutY(1000);
        suurJuur.getChildren().add(closeGame);
    }
}
