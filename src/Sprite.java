//Rachel Bransom
//This manages all the sprites
//This assumes all the subclasses are working correctly
//This class has no dependencies
//This is just used so that all the subclasses have getters abnd setters for their velocities

public class Sprite {

	private double vX;
	private double vY;

	public double getXv() {
		return this.vX;
	}

	public double getYv() {
		return this.vY;
	}

	public void setXv(double newX) {
		this.vX = newX;
	}

	public void setYv(double newY) {
		this.vY = newY;
	}

}
