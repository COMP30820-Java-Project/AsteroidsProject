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

    // Height and width of all windows
    private static final int width = 800;
    private static final int height = 600;

    // Establishing stage object with animation framerate and creating canvas within the window
    // Animation function called is the run method. Placeholder for now to run the application
    // Can be used as main entry point for game or could be changed depending on what we want
    public void start(Stage stage) throws Exception{
        stage.setTitle("ASTEROIDS");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e->run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    // Function that runs game over the keyframes specified in the start method
    private void run(GraphicsContext gc) {

        // Set the background colour
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

    }
}
