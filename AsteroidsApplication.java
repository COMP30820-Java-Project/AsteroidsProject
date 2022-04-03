package test;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.geometry.Point2D;

public class AsteroidsApplication extends Application {
	@Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        // set panel,ship and asteroid size
        pane.setPrefSize(600, 400);
        Ship ship = new Ship(150, 100);
        
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Random rnd = new Random();
            // random location of coordinates
            Asteroid asteroid = new Asteroid(rnd.nextInt(500), rnd.nextInt(350));
            asteroids.add(asteroid);
        }
        
        // display on the pane
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

 
        Scene scene = new Scene(pane);
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                ship.move();
                // move of ship inherits from Character, move of asteroids is override
                asteroids.forEach(asteroid -> asteroid.move());

                // stop and remove while collide (the function is just to test and split into two asteroids
                
                List<Asteroid> collisions = asteroids.stream().filter(asteroid -> asteroid.collide(ship)).collect(Collectors.toList());
                collisions.stream().forEach(collided -> {
                Random rnd = new Random();
                int x = (int) collided.getCharacter().getTranslateX();
                int y = (int) collided.getCharacter().getTranslateY();
                Asteroid asteroid1 = new Asteroid(x,y);
                //Asteroid asteroid2 = new Asteroid(x,y);
                asteroids.add(asteroid1);
                //asteroids.add(asteroid2);
                asteroids.remove(collided);
                pane.getChildren().remove(collided.getCharacter());
                pane.getChildren().add(asteroid1.getCharacter());
                //pane.getChildren().add(asteroid2.getCharacter());
                //stop();
				});

            }
        }.start();
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
