package comp30830.asteroidsproject;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public class Alien extends GameObjectUpload {
	Polygon pg;
	double acceleration = 150;
	protected static double muzzleA = 500;
	 public Alien(Group parent,double radius, Point2D p0, Point2D v0,double omega0) {
	    	super(parent, p0, v0,0, omega0);
	    	pg = new Polygon(
	                	//size1~3, map from 40~20	large
	                	//0.3,0.6, 0.4,0.3, 0.9,0, 0.4,-0.4, -0.4,-0.4, -0.9,0, -0.4,0.3, -0.3, 0.6
	    			0.4,0.4, 0.9,0, 0.4,-0.4, 0.3,-0.6, -0.3,-0.6, -0.4,-0.4, -0.9,0, -0.4,0.4
	                );
	        pg.getTransforms().add(new Scale(30,30));
	       
	        //pg.setStroke(Color.PINK);
	        //pg.setStrokeWidth(0.05);
	        pg.setFill(Color.WHITE);
	        gameTransform.getChildren().add(pg);
	        update(0);
	    }

	
	public void update(double delta,double omega,double accelerator) {
//      When this ship is revving up
//      if(accelerator != 0){
      Point2D acc =FindAngleDirection(radian,acceleration*1);
      velocity = velocity.add(acc.multiply(delta));
//      }else{
//      velocity = velocity.multiply(1-0.2*delta);
//      }
      this.AngularAcc = FindAngleDirection(radian,muzzleA).angle(0,0);
//      radian = FindAngleDirection(angle,muzzleA);
      super.update(delta);
  }
	
//	¸Ä
	static Alien make(Group parent,Point2D size){
        double angle = Math.random()*Math.PI*2;
        double radius = rand(20,30);
        double omega = rand(0,0);
        double disCenter = size.magnitude()/2*rand(1,2);
        Point2D p = null;
        Point2D v = null;
        // splitting==False, meant the asteroids never generate before
        // splitting==True, meant the asteroids was splitting by an original one
//        if (!splitting) {
        p= FindAngleDirection(angle,disCenter).add(size.multiply(0.5));
//        }else {
//        	p= size;
//        }
        
//        if (type == AsteroidSize.Large) {
        v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(50,80));
//        }else if(type == AsteroidSize.Medium) {
//        	v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(50,80));
//        }else if(type == AsteroidSize.Small) {
//        	v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(80,100));
//        }
        return new Alien(parent,radius,p,v,omega);
    }
	
	
	  public Bullet fireBullet(Group root,Point2D p0,Point2D v0,double angle) {
		    Bullet bullet = new Bullet(root, p0, v0, angle);
			return bullet;
		  }
	
	
	
	public Shape getShapeOfObject() {
        return pg;
    }
	
	
	
}
