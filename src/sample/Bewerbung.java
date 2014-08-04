package sample;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;




public class Bewerbung extends Application {

    @Override
    public void start(final Stage stage) {
        final BorderPane pane = new BorderPane();
        final Scene scene = new Scene(pane, 800,600);
        final VBox vBox = new VBox();

        final Label label = new Label("test");
        label.setText("Hallo, hiermit möchte ich kurz meine derzeitigen Java Kentnisse vorzustellen");
        label.setFont(Font.font("Verdana", 16));
        pane.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);

        Label labelBot = new Label("bottom");
        String bottomText = new String();
        bottomText = "blabla";


        labelBot.setText("bitte die Maus auf die Mittlere Fläche bewegen, dann  durch klicken der linker Maustaste  \n gefolgt von  bewegung  der  Maus einen Viereck erzeugen, \n welches durch das Loslassen der Maustaste durch ein Button Objekt ausgetauscht wird ");
        labelBot.setFont(Font.font("Verdana", 16));
        labelBot.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(labelBot, Pos.CENTER);



       final VBox vBoxBottom = new VBox();
        final Label explainBindings = new Label();
        explainBindings.setText("Durch das scalieren des unten entstandenes Sliders, können sie die Gröse des Buttons direkt beeinflussen \n dies wird durch bind Propertys gemacht \n wenn sie auf den Button klicken, wird der Button über den gesammten Fenster instanziert. \n Ergebnisse hängen von der Größe des Fensters und des Buttons. \n Achtung, die Fenstergröse wird nach dem Klicken nicht mehr veränderbar sein !");
        explainBindings.setFont(Font.font("Verdana", 16));
        explainBindings.setTextAlignment(TextAlignment.CENTER);
        vBoxBottom.getChildren().add(explainBindings);

       pane.setBottom(labelBot);

        final Pane pane2 = new Pane();


        //making canvas for center position of borderpane, so i can draw there
       final Canvas canvas = new Canvas(600, 400);
        vBox.getChildren().add(canvas);


        //diese werden später gebraucht, ich deklariere die hier oben, dami tauch submethoden darauf zugreifen können
       pane.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);

        final Slider slider = new Slider(0, 2, 1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
        slider.setVisible(false);//wird noch nicht gebraucht daher unsichtbar

        final Button button = new Button();
        button.setVisible(false);
        button.scaleXProperty().bind(slider.valueProperty());
        button.scaleYProperty().bind(slider.valueProperty());
        //hier wurden grade die propertys festgelegt, für die ellemente die erst später sichtbar werden.




        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override //Hier wird definiert, dass nach dem buttonclick die mittlere fläche voll von buttons gemacht wird
            public void handle(ActionEvent event) {
                stage.setResizable(false);
               double paneW = pane2.getWidth();
               double paneH = pane2.getHeight();
               int xline = (int) (paneW/button.getWidth());
               int yline = (int) (paneH/button.getHeight());
               double exWidth =  button.getWidth();
                double exHeight = button.getHeight();


                pane2.getChildren().remove(button);
                Button[][] buttons = new Button[xline][yline];// sonst wird je dimension 1 zu viel deklariert.
                for (int x=0;x<xline;x++){
                    for(int y=0;y<yline;y++){
                        buttons[x][y] = new Button();
                        buttons[x][y].setPrefWidth(exWidth);
                        buttons[x][y].setPrefHeight(exHeight);
                        buttons[x][y].setLayoutX(x * exWidth);
                        buttons[x][y].setLayoutY(y * exHeight);
                        slider.setMax(1);// der Bildschirm ist bereits voll, es soll nur noch kleiner werden.
                        buttons[x][y].scaleXProperty().bind(slider.valueProperty());
                        buttons[x][y].scaleYProperty().bind(slider.valueProperty());
                        buttons[x][y].setText("Reihe:"+(y+1)+" Nr."+(x+1));
                        pane2.getChildren().add( buttons[x][y]);





                    }
                }
                //das erklärungs kommentar nachdem die for schleifen durchgelaufen sind.
                Label comment3 = new Label();
                comment3.setText("natürlich können sie mit dem Slider immernoch die Größe von allen skalieren");
                comment3.setFont(Font.font("Verdana", 16));
                vBoxBottom.getChildren().removeAll(explainBindings, slider);
                vBoxBottom.getChildren().addAll(comment3, slider);


            }
        });










        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        canvas.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

                //hier werden die aktuell geklickten Mauspositions x und y gespeichert.
                final double x  = event.getX();
                final double y = event.getY();


                canvas.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        double width;
                        double height;
                        width = x - event.getX();
                        height = y - event.getY();

                        //rect muss mit positiven werden gemacht werden

                        if (width < 0) {
                            width = width * (-1);
                        }
                        ;
                        if (height < 0) {
                            height = height * (-1);
                        }
                        ;

                        //rect muss das oberste linke Ecke des Gebildes finden
                        // das wird oben links x und y wert.

                        double xx;
                        double yy;


                        if (x > event.getX()) {
                            xx = event.getX();
                        } else xx = x;

                        if (y > event.getY()) {
                            yy = event.getY();
                        } else yy = y;


                        gc.strokeRect(xx, yy, width, height);

                    }
                }); //ende von mause draged

                canvas.setOnMouseReleased(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent event) {
                        //canvas.setVisible(false);

                        double width;
                        double height;

                        width = x - event.getX();
                        height = y - event.getY();
                        //rect muss mit positiven werden gemacht werden

                        if (width < 0) {
                            width = width * (-1);
                        }
                        ;
                        if (height < 0) {
                            height = height * (-1);
                        }
                        ;

                        //rect muss das oberste linke Ecke des Gebildes finden
                        // das wird oben links x und y wert.

                        double xx;
                        double yy;


                        if (x > event.getX()) {
                            xx = event.getX();
                        } else xx = x;

                        if (y > event.getY()) {
                            yy = event.getY();
                        } else yy = y;
                        // those are xx und yy coordinates on Canvas, now we remove canwas, so we must ajjust x and y

                        xx=xx+canvas.getLayoutX();
                        yy = yy+canvas.getLayoutY();

                        gc.strokeRect(xx, yy, width, height);
                        vBox.getChildren().remove(canvas);

                        pane2.getChildren().add(button);
                        pane.setCenter(pane2);

                        // xx und yy müssen jetzt an die pane2 angepasst werden.
                        xx=xx +pane2.getLayoutX();
                        yy=yy +pane2.getLayoutY();
                        button.setLayoutX(xx);
                        button.setLayoutY(yy);
                        button.setPrefWidth(width);
                        button.setPrefHeight(height);
                        button.setText("klick me");
                        button.setVisible(true);

                        slider.setVisible(true);
                        vBoxBottom.getChildren().add(slider);
                        vBoxBottom.setAlignment(Pos.BOTTOM_CENTER);
                        pane.setBottom(vBoxBottom);
                        BorderPane.setAlignment(vBoxBottom,Pos.BASELINE_CENTER);











                    }
                }); //ende von maus released


            }
        });  //ende von maus pressed










        stage.setScene(scene);
        stage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }
}
