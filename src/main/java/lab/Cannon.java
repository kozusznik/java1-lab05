package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Cannon extends WorldEntity {

	private int direction=-1;
	private double angle = 0;
	private Point2D size;
	
	
	 

	public Cannon(World world, Point2D position, Point2D size) {
		super(world, position);
		this.size = size;
	}

	@Override
	public void simulate(double timeStep) {
		angle = angle + direction*80*timeStep;
		if(angle <=-90 || angle >= 0) {
			direction*=-1;
		}
	}
	
	@Override
	protected void drawInternal(GraphicsContext gc) {
		Point2D worldPosition = world.getCanvasPoint(position);
		gc.setFill(Color.BROWN);
		gc.fillRect(worldPosition.getX()-10, worldPosition.getY()+size.getY(), size.getX()+20, size.getY()/2);
		gc.fillOval(worldPosition.getX()-20, worldPosition.getY()+size.getY()/2, 40, 40);
		gc.fillOval(worldPosition.getX()+size.getX(), worldPosition.getY()+size.getY()/2, 40, 40);
		gc.setTransform(new Affine(Affine.rotate(angle, worldPosition.getX(), worldPosition.getY()+size.getY()/2)));
		gc.setFill(Color.BLACK);
		gc.fillRect(worldPosition.getX(), worldPosition.getY(), size.getX(), size.getY());
	}

	public double getAngle() {
		return (angle * -1) / 180 * Math.PI;
	}
}
