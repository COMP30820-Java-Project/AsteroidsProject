//import com.sun.javafx.geom.Point2D;
package comp30830.asteroidsproject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class MainUpdate extends Application {
	
	//level to control the level
	int level = 0;
	Asteroid tempA = null;
	Asteroid tempB = null;
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

    boolean restartScheduled;

    @Override
    public void start(Stage stage){
        restartScheduled = true;
        long animationStart;



        //create buttons for mainMenu scene
        Button playBtn = new Button("PLAY");
        playBtn.getStyleClass().add("button");
        Button highScoreBtn = new Button("HIGH SCORE");
        highScoreBtn.getStyleClass().add("button");
        Button instructionsBtn = new Button("HOW TO PLAY");
        instructionsBtn.getStyleClass().add("button");
        Text heading = new Text("ASTEROIDS");
        heading.getStyleClass().add("Title");


        //Insert buttons and headings into VBox object
        VBox vboxMain = new VBox(heading,playBtn,highScoreBtn,instructionsBtn);
        vboxMain.setSpacing(10.0);
        vboxMain.setAlignment(Pos.CENTER);


        //create main menu scene with vbox added
        Scene mainMenu = new Scene(vboxMain,size.getX(), size.getY());
        mainMenu.setFill(Color.BLACK);
        mainMenu.getStylesheets().add("comp30830/asteroidsproject/MainMenu.css");
        //stage.setScene(mainMenu);
        //stage.show();


        //create content  for high score scene VBOX

        // Read string array from external text file and create individual text objects
        String[] highScoreLines = HighScores.readHighScores();
        Text highScore1 = new Text(highScoreLines[0]);
        Text highScore2 = new Text(highScoreLines[1]);
        Text highScore3 = new Text(highScoreLines[2]);
        Text highScore4 = new Text(highScoreLines[3]);
        Text highScore5 = new Text(highScoreLines[4]);
        Text highScore = new Text("HIGH SCORE");
        Text escTextHighScore = new Text("PRESS ESC TO EXIT");

        // Style each text element as title
        escTextHighScore.getStyleClass().add("info");
        highScore.getStyleClass().add("info");
        highScore1.getStyleClass().add("info");
        highScore2.getStyleClass().add("info");
        highScore3.getStyleClass().add("info");
        highScore4.getStyleClass().add("info");
        highScore5.getStyleClass().add("info");


        //Add content to vbox and create highScoreScene
        VBox vboxHS = new VBox(highScore,
                highScore1,
                highScore2,
                highScore3,
                highScore4,
                highScore5,
                escTextHighScore);
        vboxHS.setSpacing(10.0);
        vboxHS.setAlignment(Pos.CENTER);
        Scene highScoreScene = new Scene(vboxHS,size.getX(), size.getY());
        highScoreScene.getStylesheets().add("comp30830/asteroidsproject/MainMenu.css");


        //create instruction content for VBox
        Text shootTxt = new Text("PRESS SPACE TO SHOOT");
        shootTxt.getStyleClass().add("info");
        Text rotateTxt = new Text("USE ARROWS KEYS TO ROTATE");
        rotateTxt.getStyleClass().add("info");
        Text bulletTxt = new Text("PRESS SPACEBAR TO SHOOT");
        bulletTxt.getStyleClass().add("info");
        Text escTextInstructions = new Text("PRESS ESC TO EXIT");
        escTextInstructions.getStyleClass().add("info");
        //Font.loadFont(Title.class.getResource("TRON.TTF").toExternalForm(), 10);

       //Insert content to a VBox object and create instructionScene
        VBox vboxInstruct = new VBox(shootTxt,rotateTxt,bulletTxt,escTextInstructions);
        vboxInstruct.setSpacing(10.0);
        vboxInstruct.setAlignment(Pos.CENTER);
        Scene instructionScene = new Scene(vboxInstruct,size.getX(), size.getY());
        instructionScene.getStylesheets().add("comp30830/asteroidsproject/MainMenu.css");


        //Create content to display in game over scene
        Text gameOverTxt = new Text("GAME OVER");
        Text newHighScoreTxt = new Text();
        Text escTextGameOver = new Text("PRESS ESC TO EXIT");
        gameOverTxt.getStyleClass().add("gameOver");
        newHighScoreTxt.getStyleClass().add("info");

        //Insert content to HBOx object and create game over scene
        VBox gOverVbox = new VBox(gameOverTxt,newHighScoreTxt,escTextGameOver);
        gOverVbox.setSpacing(10);
        gOverVbox.setAlignment(Pos.CENTER);
        Scene gameOverScene = new Scene(gOverVbox,size.getX(), size.getY());
        gameOverScene.getStylesheets().add("comp30830/asteroidsproject/MainMenu.css");








        //create HBox to display in game information (added to gRoot)
        Text scoreTxt = new Text("| SCORE: 100 |");
        scoreTxt.getStyleClass().add("inGameInfo");
        Text livesTxt = new Text();

        livesTxt.getStyleClass().add("inGameInfo");
        Text inGameEscTxt = new Text("| ESC TO EXIT |");
        inGameEscTxt.getStyleClass().add("inGameInfo");
        Text shipHitTxt = new Text("|| SHIP HIT !!! ||");
        shipHitTxt.getStyleClass().add("shipHit");

        Timer shipHitTimer = new Timer();

        HBox tophbox = new HBox(scoreTxt,livesTxt,inGameEscTxt);
        tophbox.getStylesheets().add("comp30830/asteroidsproject/MainMenu.css");
        tophbox.setSpacing(50.0);
        tophbox.setAlignment(Pos.TOP_CENTER);





        Group gRoot = new Group();
        Scene scene = new Scene(gRoot,size.getX(),size.getY());
        stage.setScene(scene);


        stage.setTitle("Asteroids");
        scene.setFill(Color.BLACK);

//      Store all of the ship's components
      Group gGame = new Group();

      /*
      * Adding bullets and asteroids to components
      * */
      Group gAsteroids = new Group();
      Group gBullets = new Group();
//      ��
      Group gAlien = new Group();
      gGame.getChildren().addAll(gAsteroids,gBullets,gAlien);


////        Create a fly spawn and specify a starting location
        shipUpload ship = new shipUpload(gGame,size.multiply(0.5));

/*
*     Load bullets and asteroids in a LinkedList here
* */
        List<Asteroid> asteroids = new LinkedList<>();
        List<Bullet> bulletsPlayer = new LinkedList<>();
        List<Bullet> bulletsAlien = new LinkedList<>();
        List<Alien> aliens = new LinkedList<>();


//        Get all the components
        gRoot.getChildren().addAll(gGame,tophbox);

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

                KeyCode key = event.getCode();

                if (key == KeyCode.ESCAPE){
                    System.exit(0);

                }


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
             int asteroidCount = 8;
             int alienBCount = 15;
             double bulletWaitTime = 0.3;
             double bulletTimer = 0;
             double AbulletTimer = 0;
             double AbulletWaitTime = 1;
             boolean flagForA = false;


            @Override
            public void handle(long nanoTime) {

                boolean shipHit = false;

                //set lives text
                livesTxt.setText("| LIVES: " + String.valueOf(ship.lives) + " | " );

                if(oldTime<0){
                    oldTime = nanoTime;
                }
                double delta = (nanoTime-oldTime)/1e9;
                oldTime = nanoTime;
                /*
                 * GAME LOOP
                 * */
//                Angle change can be obtained by user button
                /*
                * The specific control logic is implemented here
                * */
                
                List<Asteroid> addList = new LinkedList<>();
                List<Asteroid> delList = new LinkedList<>();



                for (Asteroid asteroid : asteroids) {
                    for (Bullet bullet : bulletsPlayer) {
                        if(asteroid.strike(bullet)){
         
                            // start splitting
                            if (asteroid.getAsteroidSize()==AsteroidSize.Large) {
                            	level = 1;
                            	tempA = asteroid.split(gAsteroids);
                            	tempB = asteroid.split(gAsteroids);
                            }else if(asteroid.getAsteroidSize()==AsteroidSize.Medium) {
                            	level = 2;
                            	tempA = asteroid.split(gAsteroids);
                            	tempB = asteroid.split(gAsteroids);
                            }else {
                            	level = 0;
                            }
                            
                            // avoid concurrent error
                            addList.add(tempA);
                            addList.add(tempB);
                            delList.add(asteroid);
                            asteroid.destroy(gAsteroids);
                            bullet.destroy(gBullets);
                        }
                        if(aliens.get(0).strike(bullet)) {
                            aliens.get(0).destroy(gAlien);
                            flagForA=true;
                           }
                    }

                    if(!ship.invincible){

                    	for (Bullet b:bulletsAlien) {
                            if (b.strike(ship)) {
                                shipHit = true;
                                ship.setPosition(size.multiply(0.5));
                                      ship.setVelocity(Point2D.ZERO);
                                      b.destroy(gBullets);

                            }
                           }
                           if(aliens.get(0).strike(ship)) {
                               shipHit = true;
                            ship.setPosition(size.multiply(0.5));
                                  ship.setVelocity(Point2D.ZERO);
                                  aliens.get(0).destroy(gAlien);
                               flagForA=true;

                           }


                        if (asteroid.strike(ship)){
                            shipHit = true;

                        	//�Ӵ���
//                          ship.destroy(gGame);
                          ship.setPosition(size.multiply(0.5));;
                          ship.setVelocity(Point2D.ZERO);

                       // start splitting
                          if (asteroid.getAsteroidSize()==AsteroidSize.Large) {
                           level = 1;
                           tempA = asteroid.split(gAsteroids);
                           tempB = asteroid.split(gAsteroids);
                          }else if(asteroid.getAsteroidSize()==AsteroidSize.Medium) {
                           level = 2;
                           tempA = asteroid.split(gAsteroids);
                           tempB = asteroid.split(gAsteroids);
                          }else {
                           level = 0;
                          }

                          // avoid concurrent error
                          addList.add(tempA);
                          addList.add(tempB);
                          delList.add(asteroid);
                          asteroid.destroy(gAsteroids);




                          }


                    }

                    if(shipHit){
                        ship.lives -= 1;
                        System.out.println(ship.lives);
                        ship.handleInvincible();
                        inGameEscTxt.getStyleClass().add("shipHit");
                        inGameEscTxt.setText("|| SHIP HIT ||");
                        shipHitTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                inGameEscTxt.getStyleClass().remove("shipHit");
                                inGameEscTxt.getStyleClass().add("inGameInfo");
                                inGameEscTxt.setText(" | ESC TO EXIT |");
                            }
                        }, 1000);



                        break;
                    }


                    if(ship.lives ==0){
                        gGame.getChildren().clear();
                        gRoot.getChildren().clear();
                        stage.setScene(gameOverScene);

                    }



//
                    if(asteroid.leavingBounds(bounds)){
                        asteroid.destroy(gAsteroids);
                    }else{
                        asteroid.update(delta);
                    }

                }
 
                // after looping, add and delete the asteroid from the list
                for (Asteroid addasteroid : addList) {
                	 asteroids.add(addasteroid);
                }
                for (Asteroid delasteroid : delList) {
               	 asteroids.add(delasteroid);
               }
  
                for (Bullet bullet : bulletsPlayer) {
                    if(bullet.leavingBounds(bounds)){
                        bullet.destroy(gBullets);
                    }else{
                        bullet.update(delta);
                    }
                }
                asteroids.removeIf(asteroid -> !asteroid.alive);
                bulletsPlayer.removeIf(bullet -> !bullet.alive);
                
                // ensure always generates 10 asteroids
                while(asteroids.size()<asteroidCount){
                	if (level==0) {
                        asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Large, false));
                	}else if(level==1) {
                        asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Medium, false));
                        asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Large, false));
                        
                	}else if (level==2) {
                       asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Small, false)); 
                       asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Large, false));
                       asteroids.add(Asteroid.make(gAsteroids,size, AsteroidSize.Medium, false));
                	}
                	
                }
                if(key(KeyCode.SPACE)==1&&bulletTimer<=0){
                    Bullet b = ship.fireBullet(gBullets,ship.position,ship.velocity,ship.radian);
                    bulletsPlayer.add(b);
                    bulletTimer = bulletWaitTime;
                }
                bulletTimer -= delta;

                double rot = (key(KeyCode.LEFT)-key(KeyCode.RIGHT))*1.5;
                ship.update(delta,rot*-1,key(KeyCode.UP));


                if(flagForA) {
                 aliens.clear();
                }
                flagForA=false;
                while(aliens.size()<1){
                 aliens.add(Alien.make(gAlien,size));
//                 break;
                }
//                for(Alien a:aliens) {
                 if(aliens.get(0).leavingBounds(bounds)){
//                  System.out.println(aliens.get(0).leavingBounds(bounds));
                  aliens.get(0).destroy(gAlien);
//                  aliens.remove(0);
                  flagForA=true;
                    }else{
                     aliens.get(0).update(delta);
                    }

                 while(bulletsAlien.size()<alienBCount&&AbulletTimer<=0){
                        Bullet b = aliens.get(0).fireBullet(gBullets,aliens.get(0).position,aliens.get(0).velocity,aliens.get(0).radian+Math.random());
                        bulletsAlien.add(b);

                        AbulletTimer = AbulletWaitTime;
                    }
                 AbulletTimer -= delta;

//                }
                for (Bullet b : bulletsAlien) {
                    if(b.leavingBounds(bounds)){
                        b.destroy(gBullets);
                        bulletsAlien.remove(b);

                    }else{
                        b.update(delta);
                    }
                }



            }

        };



        //When game started, set scene to mainMenu
        stage.setScene(mainMenu);
        stage.show();

        //add event listeners to navigate through menu/highscores/insrauctions/game
        playBtn.setOnAction(actionEvent ->  {

            //start loop
            loop.start();
            //set scene to game scene
            stage.setScene(scene);

            stage.show();
        });


        highScoreBtn.setOnAction(actionEvent ->  {

            //set scene to game scene
            stage.setScene(highScoreScene);

            stage.show();
        });

        //press ESC to exit
        highScoreScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();

                if (key == KeyCode.ESCAPE){
                    stage.setScene((mainMenu));
                }
            }
        });

        //press ESC to exit
        gameOverScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();

                if (key == KeyCode.ESCAPE){
                    System.exit(0);
                }
            }
        });




        instructionsBtn.setOnAction(actionEvent ->  {



            //set scene to game scene
            stage.setScene(instructionScene);



            //show
            stage.show();
        });


        //press ESC to exit
        instructionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();

                if (key == KeyCode.ESCAPE){
                    stage.setScene((mainMenu));
                }
            }
        });

    }


}
