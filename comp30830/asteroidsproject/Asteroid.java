package comp30830.asteroidsproject;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
enum AsteroidSize{Large,Medium,Small};
public class Asteroid extends GameObject {

    Polygon pg;
    private AsteroidSize sizetype;
    private int score;
    // three sizes/shapes asteroids
    public Asteroid(Group parent,double radius, Point2D p0, Point2D v0,double omega0,AsteroidSize type) {
    	super(parent, p0, v0,0, omega0);
    	sizetype = type;
        if (sizetype == AsteroidSize.Large) {
        	score = 20;
        	pg = new Polygon(
                	//size1~3, map from 40~20	large
                	0.3,0.8,	0.9,0.15,	0.9,-0.15,	0.1,-0.9,	-0.3,-0.9,	-0.15,-0.15,	-0.6,-0.9,	-0.9,-0.15, 	-0.6,0,	-0.9,0.1,	-0.2,0.8
                );
            pg.getTransforms().add(new Scale(40,40));
            
        }else if (sizetype == AsteroidSize.Medium) {
        	score = 50;
        	pg = new Polygon(
                	//size1~3, map from 40~20	med
                	0,0.4,	0.3,0.6,	0.9,0.3,	0.2,0.1,	0.9,-0.4,	0.2,-0.99,	-0.2,-0.67,	-0.6,-0.8,	-0.9,-0.5,	-0.6,0,	-0.9,0.3,	-0.3,0.6			
                );
        	
            pg.getTransforms().add(new Scale(30,30));
        }else if (sizetype == AsteroidSize.Small) {
        	score = 100;
        	pg = new Polygon(
                	//size1~3, map from 40~20 small	
                	0,0.45,	0.3,0.6,	0.8,0.45,	0.6,0,	0.8,-0.3,	0.1,-0.8,	-0.6,-0.8,	-0.8,-0.6,	-0.8,0.3,	-0.45,0.6
                );
        	pg.getTransforms().add(new Scale(20,20));
        }
        pg.setStroke(Color.WHITE);
        pg.setStrokeWidth(0.05);
        gameTransform.getChildren().add(pg);
        update(0);
    }

    @Override
    Shape getShapeOfObject() {
        return pg;
    }

    
    static Asteroid make(Group parent,Point2D size, AsteroidSize type, boolean splitting,double s){
        double angle = Math.random()*Math.PI*2;
        double radius = rand(20,30);
        double omega = rand(-2,2);
        double disCenter = size.magnitude()/2*rand(1,2);
        Point2D p = null;
        Point2D v = null;
        // splitting==False, meant the asteroids never generate before
        // splitting==True, meant the asteroids was splitting by an original one
        if (!splitting) {
        	p= FindAngleDirection(angle,disCenter).add(size.multiply(0.5));
        }else {
        	p= size;
        }
        
        if (type == AsteroidSize.Large) {
        	v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(30,50)*s);
        }else if(type == AsteroidSize.Medium) {
        	v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(50,80)*s);
        }else if(type == AsteroidSize.Small) {
        	v = FindAngleDirection(Math.PI+angle+rand(-0.2,0.2),rand(80,100)*s);
        }
        return new Asteroid(parent,radius,p,v,omega,type);
    }


    
    // get the size (small-medium-large
    public AsteroidSize getAsteroidSize() {
    	return sizetype;
    }
    
    public int getScore() {
    	return score;
    }
  
    
    
    //asteroid splits
    public Asteroid split(Group mygroup,double s) {
    	Point2D p= this.getPosition();
    	AsteroidSize spawn = this.getAsteroidSize();
    	//System.out.println(spawn);
    	Asteroid tempA = null;
    	
    	if (spawn == AsteroidSize.Large) {
    		tempA = Asteroid.make(mygroup, p, AsteroidSize.Medium,true,s);
	
    	}else if (spawn == AsteroidSize.Medium) {
    		tempA = Asteroid.make(mygroup, p, AsteroidSize.Small,true,s);
    		
    	}
		return tempA;

    }
}
