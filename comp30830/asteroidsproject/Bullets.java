package comp30830.asteroidsproject;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Bullets extends GameObjectUpload{

    protected Timer lifespan;
    Polyline line;

    public Bullets(Group root,
                   Point2D p0,
                   Point2D v0,
                   double rad,
                   double accrad) {
        super(root, p0, v0, rad, accrad);
        this.lifespan = new Timer();
    }

    // Make sure to implement this method once we know how to draw shapes in the window
    @Override
    Shape getShapeOfObject() {
        return null;
    }

    // Need to check if this.gameRoot destroys this object only or others - are the game root objects
    // connected
    void lifespanEnd() {
        super.destroy(this.gameRoot);
    }
}

class PlayerBullet extends Bullets {

    public PlayerBullet(Group root,
                        Point2D p0,
                        Point2D v0,
                        double rad,
                        double accrad
                        ) {
        super(root, p0, v0, rad, accrad);

        this.lifespan.schedule(new TimerTask() {
            @Override
            public void run() {
                lifespanEnd();
            }
        }, 1000);           // Hard coded player bullets to one second for now

        line = new Polyline(0.2, 0.2);
        gameTransform.getChildren().add(line);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(0.1);
        line.getTransforms().add(new Scale(30,30));
    }
}


class AlienBullet extends Bullets {

    public AlienBullet(Group root,
                       Point2D p0,
                       Point2D v0,
                       double rad,
                       double accrad) {
        super(root, p0, v0, rad, accrad);

        this.lifespan.schedule(new TimerTask() {
            @Override
            public void run() {
                lifespanEnd();
            }
        }, 800);            // Hard coded alien bullets to .8 seconds for now
    }

}
