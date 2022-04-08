import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public class Asteroid extends GameObjectUpload {

    Polygon pg;

    public Asteroid(Group parent,double radius, Point2D p0, Point2D v0,double omega0) {
        super(parent, p0, v0,0, omega0);
        pg = new Polygon(
                0.7,0,-0.7,-0.4,-0.7,0.4
        );
        pg.setStroke(Color.WHITE);
        pg.setStrokeWidth(0.1);
        pg.getTransforms().add(new Scale(50,50));

        gameTransform.getChildren().add(pg);
        update(0);
    }

    @Override
    Shape getShapeOfObject() {
        return pg;
    }

    static Asteroid make(Group parent,Point2D size){
        double angle = Math.random()*Math.PI*2;
        double radius = rand(20,30);
        double omega = rand(-2,2);
        double disCenter = size.magnitude()/2*rand(1,2);
        Point2D p= FindAngleDirection(angle,disCenter).add(size.multiply(0.5));
        Point2D v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(50,100));
        return new Asteroid(parent,radius,p,v,omega);
    }
}
