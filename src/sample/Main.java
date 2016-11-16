package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static java.lang.StrictMath.sqrt;

public class Main extends Application {

    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Rozwiązanie równania kwadratowego");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15, 10, 10, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Scene scene1 = new Scene(grid, 280, 410);
        window.setScene(scene1);
        window.show();
        scene1.getStylesheets().add("css/sample.css");

        Text text = new Text("Podaj parametry równania kwadratowego \ny=ax2+bx+c \n ");
        GridPane.setConstraints(text, 0, 0, 4, 1);

        //a label
        Label aLabel = new Label("a");
        GridPane.setConstraints(aLabel, 0, 1);
        //a input
        TextField aInput = new TextField();
        GridPane.setConstraints(aInput, 1, 1);

        //b label
        Label bLabel = new Label("b");
        GridPane.setConstraints(bLabel, 0, 2);
        //b input
        TextField bInput = new TextField();
        GridPane.setConstraints(bInput, 1, 2);

        //c label
        Label cLabel = new Label("c");
        GridPane.setConstraints(cLabel, 0, 3);
        //c input
        TextField cInput = new TextField();
        GridPane.setConstraints(cInput, 1, 3);

        //send button
        Button sendButton = new Button("Wyślij");
        GridPane.setConstraints(sendButton, 1, 4);
        //reset button
        Button resetButton = new Button("Kasuj");
        GridPane.setConstraints(resetButton, 1, 5);
        //close button
        Button closeButton = new Button("Zamknij");
        closeButton.setOnAction(e -> closeProgram());
        GridPane.setConstraints(closeButton, 1, 10);

        Text text2 = new Text("Rozwiązania: ");
        GridPane.setConstraints(text2, 0, 7, 2, 1);

        //x1 label
        Label x1Label = new Label("x1");
        x1Label.setId("bold-label");
        x1Label.getStyleClass().add("blue-label");
        GridPane.setConstraints(x1Label, 0, 8);
        //x1 field
        TextField x1 = new TextField();
        x1.setEditable(false);
        GridPane.setConstraints(x1, 1, 8);

        //x2 label
        Label x2Label = new Label("x2 ");
        x2Label.setId("bold-label");
        x2Label.getStyleClass().add("blue-label");
        GridPane.setConstraints(x2Label, 0, 9);
        //x2 field
        TextField x2 = new TextField();
        x2.setEditable(false);
        GridPane.setConstraints(x2, 1, 9);

        grid.getChildren().addAll(text, aLabel, aInput, bLabel, bInput, cLabel, cInput, sendButton, resetButton,
                closeButton, text2, x1Label, x1, x2Label, x2);

        sendButton.setOnAction( e -> {
            if(isDouble(aInput) && isDouble(bInput) && isDouble(cInput)) {  //if a b and c are double
                double numA = Double.parseDouble(aInput.getText());
                double numB = Double.parseDouble(bInput.getText());
                double numC = Double.parseDouble(cInput.getText());
                double discriminant = numB*numB - 4*numA*numC;
                aInput.setId("correct-input");
                bInput.setId("correct-input");
                cInput.setId("correct-input");
                double result1, result2;
                String res1, res2;

                if(numA == 0 && numB != 0){
                    result1 = -numC/numB;
                    res1 = String.format("%.3f", result1);
                    x1.setText(res1);
                    x2.clear();
                }
                else if(numA == 0 && numB == 0 && numC == 0) {
                    x1.setText("Nieskończenie wiele rozwiązań");
                    x2.setText("Nieskończenie wiele rozwiązań");
                }
                else if(numA == 0 && numB == 0 && numC != 0) {
                    x1.setText("Sprzeczność");
                    x2.setText("Sprzeczność");
                }
                else if(discriminant >=0) {                               // if a != 0
                    result1 = (-numB-sqrt(discriminant))/(2*numA);
                    result2 = (-numB+sqrt(discriminant))/(2*numA);
                    res1 = String.format("%.3f", result1);
                    res2 = String.format("%.3f", result2);
                    x1.setText(res1);
                    x2.setText(res2);
                } else {                                                 // if discriminant < 0
                    x1.setText("Brak rozwiązań rzeczywistych");
                    x2.setText("Brak rozwiązań rzeczywistych");
                }
            } else {                                                      //if a b or c is not a double
                x1.clear();
                x2.clear();
                if (isDouble(aInput)) {
                    aInput.setId("correct-input");
                }else{aInput.setId("error-input");}
                if(isDouble(bInput)) {
                    bInput.setId("correct-input");
                }else{bInput.setId("error-input");}
                if(isDouble(cInput)) {
                    cInput.setId("correct-input");
                }else{cInput.setId("error-input");}
            }
        });

        resetButton.setOnAction(e -> {
            aInput.clear();
            aInput.setId("correct-input");
            bInput.clear();
            bInput.setId("correct-input");
            cInput.clear();
            cInput.setId("correct-input");
            x1.clear();
            x2.clear();
        });
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Równanie kwadratowe", "Czy na pewno chcesz zamknąć program?");
        if(answer) window.close();
    }

    private boolean isDouble(TextField input) {
        try{
            Double.parseDouble(input.getText());
            return true;
        }catch (NumberFormatException nfe) {
            return false;
        }
    }
}
