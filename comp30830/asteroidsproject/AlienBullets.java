package comp30830.asteroidsproject;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class AlienBullets extends Bullet {

    protected Circle bullet;

    AlienBullets(Group root,
                 Point2D p0,
                 Point2D vShip,
                 double angle,
                Type type) {
        super(root, p0, vShip, angle, type);

        bullet = new Circle(3, Color.rgb(255,0,0));
        gameTransform.getChildren().add(bullet);
    }

    @Override
    Shape getShapeOfObject() {
        return bullet;
    }
}
