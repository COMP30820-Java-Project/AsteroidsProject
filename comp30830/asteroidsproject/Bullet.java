import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Bullet extends GameObjectUpload {
//    Define the bullet as a circle
    Circle circle;
//    speedOfbullet
    static double muzzle = 500;
    public Bullet(Group root, Point2D p0, Point2D vShip, double angle) {
        super(root, p0, FindAngleDirection(angle,muzzle).add(vShip),angle,0);
//        Define bullet color, size, etc.
        circle = new Circle(3, Color.rgb(100,100,0));
        gameTransform.getChildren().add(circle);
    }
    @Override
    Shape getShapeOfObject() {
        return circle;
    }
}
