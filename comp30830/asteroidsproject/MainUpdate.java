package comp30830.asteroidsproject;

//import com.sun.javafx.geom.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class MainUpdate extends Application {
    //Setting window size
    Point2D size = new Point2D(800,600);
    //Avoid bonding. Press a key and only lift it to get the next key
    Set<KeyCode> keysDown = new HashSet<>();

    //   Method to check if the key is pressed
    public int key(KeyCode k) {
        return keysDown.contains(k)?1:0;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        Group gRoot = new Group();
        Scene scene = new Scene(gRoot,size.getX(),size.getY());
        stage.setScene(scene);
        stage.setTitle("Asteroids");
        scene.setFill(Color.BLACK);

//        Store all of the ship's components
        Group gGame = new Group();
////        Create a fly spawn and specify a starting location
        shipUpload ship = new shipUpload(gGame,size.multiply(0.5));
//        Get all the components
        gRoot.getChildren().addAll(gGame);

        /*
         * SETUP
         * */
//Event listeners
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            //         Event handler
            @Override
            public void handle(KeyEvent event) {
//                Gets the current key pressed to avoid sticking into the set
                keysDown.add(event.getCode());
                System.out.println(key(KeyCode.SPACE));
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
//                Release this bond when you lift it up
                keysDown.remove(keyEvent.getCode());
            }
        });




        AnimationTimer loop = new AnimationTimer(){
            double oldTime = -1;

            @Override
            public void handle(long nanoTime) {
                if(oldTime<0){
                    oldTime = nanoTime;
                }
                double delta = (nanoTime-oldTime)/1e9;
                oldTime = nanoTime;

                /*
                 * GAME LOOP
                 * */
//                Angle change can be obtained by user button
                double rot = (key(KeyCode.LEFT)-key(KeyCode.RIGHT));
                ship.update(delta,rot,key(KeyCode.UP));
                if (key(KeyCode.SPACE) == 1)
                    ship.fireBullet();

            }
        };

        loop.start();

        stage.show();

    }


}
