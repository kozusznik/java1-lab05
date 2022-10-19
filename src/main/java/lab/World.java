package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class World {
	private double width;
	private double height;
	private DrawableSimulable[] objects;
	public World(double width, double height) {
		super();
		this.width = width;
		this.height = height;
		
		
		Cannon cannon = new Cannon(this, new Point2D(50, 50), new Point2D(100, 20));
		BulletAnimated bulletAnimatted = new BulletAnimated(this, cannon, new Point2D(30, 60), new Point2D(0, 0), 40);
		objects = new DrawableSimulable[] {cannon, bulletAnimatted
				, new Dragon(this, new Point2D(50, 200), new Point2D(100, 5)),
				new Dragon(this, new Point2D(50, 230), new Point2D(60, 5)),
				new Dragon(this, new Point2D(50, 270), new Point2D(-50, 20)) };
	}

	public Point2D getCanvasPoint(Point2D worldPoint) {
		return new Point2D(worldPoint.getX(), height - worldPoint.getY());
	}

	public void draw(GraphicsContext gc) {
		gc.clearRect(0, 0, width, height);
		for (DrawableSimulable drawableSimulable : objects) {
			drawableSimulable.draw(gc);
		}
	}

	public void simulate(double timeDelta) {
		for (DrawableSimulable drawableSimulable : objects) {
			drawableSimulable.simulate(timeDelta);
		}
	
		for (DrawableSimulable drawableSimulable : objects) {
			if (drawableSimulable instanceof Collisionable o1) {
				for (DrawableSimulable drawableSimulable2 : objects) {
					if (drawableSimulable != drawableSimulable2 && drawableSimulable2 instanceof Collisionable o2) {
						if (o1.isInCollisionWith(o2)) {
							o1.hitBy(o2);
							o2.hitBy(o1);
						}
					}
				}
				
			}
		}
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
