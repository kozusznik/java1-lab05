package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class WorldEntity  implements DrawableSimulable{
	final protected World world;
	protected Point2D position;
	
	public WorldEntity(World world, Point2D position) {
		this.world = world;
		this.position = position;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		drawInternal(gc);
		gc.restore();
		
	}

	protected abstract void drawInternal(GraphicsContext gc);
	
}
