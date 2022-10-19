package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Dragon extends WorldEntity implements Collisionable{
	
	private Point2D speed;
		
	private final double size = 45;

	public Dragon(World world, Point2D position, Point2D speed) {
		super(world, position);
		this.speed = speed;
	}

	
	@Override
	public void simulate(double timeDelta) {
		double timeDeltaS = timeDelta;
		double newX = (position.getX() + speed.getX() * timeDeltaS + world.getWidth()) % world.getWidth(); 
		double newY = (position.getY() + speed.getY() * timeDeltaS + world.getHeight()) % world.getHeight();
		position = new Point2D(newX, newY);
	}
	
	
	@Override
	public Rectangle2D getBB() {
		return getBoundingBox();
	}
	
	@Override
	public void hitBy(Collisionable other) {
		if (other instanceof BulletAnimated) {
			hit();
		}
	}

	@Override
	protected void drawInternal(GraphicsContext gc) {
		Point2D canvasPosition = world.getCanvasPoint(position);
		gc.drawImage(Constants.DRAGON_IMAGE, canvasPosition.getX(), canvasPosition.getY(), size, size);
	}

	public Rectangle2D getBoundingBox() {
		return new Rectangle2D(position.getX(), position.getY(), size, size);
	}


	public void hit() {
		speed = speed.multiply(-1.);
	}

	
	
	
	
}
