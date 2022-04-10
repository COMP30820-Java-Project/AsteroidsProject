package comp30830.asteroidsproject;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public abstract class Bullet extends GameObjectUpload {
//    Define the bullet as a circle
    Circle circle;
//    speedOfbullet
    protected static double muzzle = 500;
    public Bullet(Group root, Point2D p0, Point2D vShip, double angle) {
        super(root, p0, FindAngleDirection(angle,muzzle).add(vShip),angle,0);
//        Define bullet color, size, etc.
    }

}
