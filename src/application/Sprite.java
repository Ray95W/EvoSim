package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	public Vektor position;
	public Vektor velocity;
	public Image image;
	public Rectangle boundary;
	public boolean isSelected;
	
	public Sprite() {
		position = new Vektor(0,0);
		velocity = new Vektor(0,0);
		boundary = new Rectangle(0,0,0,0);
		isSelected = false;
	}
	
	public void setPosition(double x, double y) {
		position.set(x,y);
	}
	
	public void setVelocity(double x, double y) {
		velocity.set(x, y);
	}
	
	public void setImage(String filename) {
		image = new Image(filename);
		boundary.width = image.getWidth();
		boundary.height = image.getHeight();
	}
	
	public void setImage(Image image) {
		this.image = image;
		boundary.width = image.getWidth();
		boundary.height = image.getHeight();
	}
	
	public Rectangle getBoundary() {
		boundary.x = position.x;
		boundary.y = position.y;
		return boundary;
	}
	
	public boolean overlaps(Sprite other) {
		return this.getBoundary().overlaps(other.getBoundary());
	}
	
	public void render(GraphicsContext context) {
		context.drawImage(image, position.x, position.y);
	}
	
	public Vektor getPosition(){
		return position;
	}

	public boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
