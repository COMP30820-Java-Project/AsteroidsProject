import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

abstract public class GameObjectUpload {
    Point2D position;
    Point2D velocity;

//    Use toDegrees function change into angle
    double radian;
//    Angular acceleration of aircraft(Radian acceleration)
    double AngularAcc;
//    Place the created components in gameroot
    Group gameRoot;
//    The main program can be drawn directly by passing the dynamic components of each object to root
    Group gameTransform;

    boolean alive;

//    Initialize all attributes (initialize constructors)


    public GameObjectUpload(Group root,Point2D p0,Point2D v0,double rad,double accrad) {
//        root: Groups that have been put into GUI components
        gameRoot = new Group();
        gameTransform = new Group();

        gameRoot.getChildren().add(gameTransform);
        root.getChildren().add(gameRoot);
//      Position, velocity, radian, angular acceleration initialized
        position=p0;
        velocity=v0;
        radian=rad;
        AngularAcc=accrad;

        alive=true;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
//    Position update function
    public void update(double deltaTime){
        position = position.add(velocity.multiply(deltaTime));
        radian = (radian+AngularAcc*deltaTime);

        gameTransform.getTransforms().clear();
        gameTransform.getTransforms().addAll(
                new Translate(position.getX(),position.getY()),
                new Rotate(Math.toDegrees(radian))
        );
    }

    public void destory(Group root){
//        When a total object crashes or dies remove the object from the component and the main function will not print it
        root.getChildren().remove(gameRoot);
        alive = false;
    }
//    Create this object according to your needs(Mark the outer boundary of this object)
    abstract Shape getShapeOfObject();

    public boolean strike(GameObjectUpload other){
//       /*
//       Determine when to collide
//       */
        return alive && other.alive && !Shape.intersect(getShapeOfObject(),other.getShapeOfObject()).getBoundsInLocal().isEmpty();
    }

    static Point2D FindAngleDirection(double angle,double mag){
        /*
         * Find out how much an object has moved in a certain direction
         * */
        double x = Math.cos(angle)*mag;
        double y = Math.sin(angle)*mag;
        return new Point2D(x,y);
    }

    static double rand(double min,double max){
        /*
         * 这个物体可以在哪个范围内运动
         * */
        return Math.random()*(max-min)+min;

    }

    public boolean leavingBounds(Rectangle bounds) {
        if(!Shape.intersect(getShapeOfObject(), bounds).getBoundsInLocal().isEmpty()){
            return false;
        }
        double x = position.getX();
        double y = position.getY();
        double bx = bounds.getWidth();
        double by = bounds.getHeight();

        return x <=0 && velocity.getX() <= 0 || x >= bx && velocity.getX() >=0 || y <=0 && velocity.getY() <= 0||y>=by && velocity.getY()>=0;

    }



}
