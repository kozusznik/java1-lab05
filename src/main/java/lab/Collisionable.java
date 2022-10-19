package lab;

import javafx.geometry.Rectangle2D;

public interface Collisionable {

	Rectangle2D getBB();
	
	boolean isInCollisionWith(Collisionable other);
	
	void hitBy(Collisionable other);
}
