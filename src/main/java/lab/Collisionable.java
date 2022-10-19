package lab;

import javafx.geometry.Rectangle2D;

public interface Collisionable {

	Rectangle2D getBB();
	
	default boolean isInCollisionWith(Collisionable other) {
		return getBB().intersects(other.getBB());
	}
	
	void hitBy(Collisionable other);
}
