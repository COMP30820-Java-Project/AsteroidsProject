//import com.sun.javafx.geom.Point2D;
package comp30830.asteroidsproject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MainUpdate extends Application {
    //Setting window size
    Point2D size = new Point2D(800,600);
    Rectangle bounds = new Rectangle(800,600);
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

        /*
        * Adding bullets and asteroids to components
        * */
        Group gAsteroids = new Group();
        Group gBullets = new Group();
        gGame.getChildren().addAll(gAsteroids,gBullets);


////        Create a fly spawn and specify a starting location
        shipUpload ship = new shipUpload(gGame,size.multiply(0.5));

/*
*     Load bullets and asteroids in a LinkedList here
* */
        List<Asteroid> asteroids = new LinkedList<>();
        List<Bullet> bullets = new LinkedList<>();


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

            int asteroidCount = 10;
            double bulletWaitTime = 0.3;
            double bulletTimer = 0;

            @Override
            public void handle(long nanoTime) {
                if(oldTime<0){
                    oldTime = nanoTime;
                }
                double delta = (nanoTime-oldTime)/1e9;
                oldTime = nanoTime;
                /*
                 * GAMP LOOP
                 * */
//                Angle change can be obtained by user button
                /*
                * The specific control logic is implemented here
                * */
                for (Asteroid asteroid : asteroids) {
                    for (Bullet bullet : bullets) {
                        if(asteroid.strike(bullet)){
                            asteroid.destroy(gAsteroids);
                            bullet.destroy(gBullets);
                            break;
                        }
                    }
//                    if(!asteroid.alive){
//                        continue;
//                    }
                    if(asteroid.leavingBounds(bounds)){
                        asteroid.destroy(gAsteroids);
                    }else{
                        asteroid.update(delta);
                    }

                }

                for (Bullet bullet : bullets) {
                    if(bullet.leavingBounds(bounds)){
                        bullet.destroy(gBullets);
                    }else{
                        bullet.update(delta);
                    }
                }
                asteroids.removeIf(asteroid -> !asteroid.alive);
                bullets.removeIf(bullet -> !bullet.alive);
                while(asteroids.size()<asteroidCount){
                    asteroids.add(Asteroid.make(gAsteroids,size));
                }
                if(key(KeyCode.SPACE)==1&&bulletTimer<=0){
                    PlayerBullets b = ship.fireBullet(gBullets,ship.position,ship.velocity,ship.radian);
                    bullets.add(b);
                    bulletTimer = bulletWaitTime;
                }
                bulletTimer -= delta;

                double rot = (key(KeyCode.LEFT)-key(KeyCode.RIGHT));
                ship.update(delta,rot,key(KeyCode.UP));

            }
        };

        loop.start();

        stage.show();

    }


}
