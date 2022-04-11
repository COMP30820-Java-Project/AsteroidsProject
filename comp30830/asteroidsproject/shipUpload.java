package comp30830.asteroidsproject;


import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public class shipUpload extends GameObjectUpload {
    public boolean invincible;
    public int lives;
    public int scores;
    //    acceleration
    double acceleration = 150;
//    draw plane
    Polygon pg;

    public shipUpload(Group parent, Point2D p) {



        super(parent, p, Point2D.ZERO,0,0);

        boolean invincible = false;
//        Draw the spacecraft
        pg = new Polygon(
                0.7,0,-0.7,-0.4,-0.7,0.4
        );
//        Add this component to our total component
        gameTransform.getChildren().add(pg);
//        Set some other properties of the plane
        pg.setStroke(Color.WHITE);
        pg.setStrokeWidth(0.1);
        pg.getTransforms().add(new Scale(30,30));

        update(0,0,0);

    }


    public void update(double delta,double omega,double accelerator) {
//        When this ship is revving up
        if(accelerator != 0){
            Point2D acc =FindAngleDirection(radian,acceleration*accelerator);
            velocity = velocity.add(acc.multiply(delta));
        }else{
            velocity = velocity.multiply(1-0.2*delta);
        }
        this.AngularAcc = omega;

//        Boundary condition judgment
        if(position.getX()>800){
//            transform.getChildren().removeAll();
            Point2D newPosition = new Point2D(0,position.getY());
            this.setPosition(newPosition);
//            this.setVelocity(this.getVelocity());

        }
        if(position.getX()<0){
            Point2D newPosition = new Point2D(800,position.getY());
            this.setPosition(newPosition);
        }
        if(position.getY()>600){
            Point2D newPosition = new Point2D(position.getX(),0);
            this.setPosition(newPosition);
        }
        if(position.getY()<0){
            Point2D newPosition = new Point2D(position.getX(),600);
            this.setPosition(newPosition);
        }
        super.update(delta);
    }
    public Shape getShapeOfObject() {
        return pg;
    }

    public PlayerBullets fireBullet(Group root,Point2D p0,Point2D v0,double angle) {
        PlayerBullets bullet = new PlayerBullets(root, p0, v0, angle);
        return bullet;
    }
}
