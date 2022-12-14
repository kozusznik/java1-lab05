package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletAnimated extends WorldEntity implements Collisionable {

	private Point2D start;
	private Point2D speed;
	private Point2D initialSpeed;
	private double size;
	private double mass = 2;
	private double strenghtOfCannon = 100;
	private double cannonLength = 100;
	private boolean accelerate = true;
	private boolean hitToGround = false;

	private double crossSectionalArea;
	private double dragCoefficient = 0.47;
	
	private Image image;
	private Cannon cannon;

	public BulletAnimated(World world, Cannon cannon) {
		this(world, cannon, new Point2D(0, 0), new Point2D(0, 0), 10);
	}

	public BulletAnimated(World world, Cannon cannon, Point2D start, Point2D speed, double size) {
		super(world, start);
		this.start = start;
		this.position = this.start;
		this.initialSpeed = speed;
		this.speed = speed;
		this.size = size;
		this.cannon = cannon;
		image = new Image(getClass().getResourceAsStream("fireball-transparent.gif"), size, size,
				true, true);
	}

	@Override
	public void simulate(double deltaT) {
		if (accelerate && start.distance(position) < cannonLength) {
			double cannonAngle = cannon.getAngle(); 
			speed = speed
					.add(new Point2D(Math.cos(cannonAngle) * strenghtOfCannon, Math.sin(cannonAngle) * strenghtOfCannon)
							.multiply(deltaT / mass));
		} else if (!hitToGround) {
			accelerate = false;
			Point2D airResistanceforce = new Point2D(
					-1. / 2 * crossSectionalArea * Constants.AIR_DENSITY * dragCoefficient * Math.pow(speed.getX(), 2),
					-1. / 2 * crossSectionalArea * Constants.AIR_DENSITY * 0.47 * Math.pow(speed.getY(), 2));
			Point2D acceleration = new Point2D(-airResistanceforce.getX() * mass,
					(-Constants.GRAVITATIONAL_ACCELERATION + airResistanceforce.getY()) * mass);
			speed = speed.add(acceleration.multiply(deltaT));
		}
		if (!hitToGround) {
			position = position.add(speed.multiply(deltaT*100));
			if (!accelerate && position.getY() <= size / 2) {
				hitToGround = true;
				position = new Point2D(position.getX(), size / 2);
			}
		} else {
			reload();
		}		
	}
	
	@Override
	public Rectangle2D getBB() {
		return getBoundingBox();
	}
	
	
	@Override
	public void hitBy(Collisionable other) {
		if (other instanceof Dragon) {
			reload();
		}
		
	}

	public Rectangle2D getBoundingBox() {
		return new Rectangle2D(position.getX(), position.getY(), size, size);
	}
	
	@Override
	protected void drawInternal(GraphicsContext gc) {
		Point2D canvasPosition = world.getCanvasPoint(position);
		gc.drawImage(image, canvasPosition.getX(), canvasPosition.getY());
	}

	public boolean overlaps(Dragon dragon) {
		return getBoundingBox().intersects(dragon.getBoundingBox());
	}
	
	
	public void reload() {
		position = start;
		speed = initialSpeed;
		hitToGround = false;
		accelerate = true;
	}
	
}
