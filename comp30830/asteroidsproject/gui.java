package comp30830.asteroidsproject;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class gui extends Application {

        private static final int width = 800;
        private static final int height = 600;

        public void start(Stage stage) throws Exception{
            stage.setTitle("PONG");
            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(10),));
            tl.setCycleCount(Timeline.INDEFINITE);

            stage.setScene(new Scene(new StackPane(canvas)));
            stage.show();
            tl.play();
        }
}
