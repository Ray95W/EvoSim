package application;

public class Rectangle {
	public double x;
	public double y;
	public double width;
	public double height;
	
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean overlaps(Rectangle other) {
		if(this.x + this.width < other.x ||
			other.x + other.width < this.x ||
			this.y + this.height < other.y ||
			other.y + other.height < this.y) {
			return false;
		}else {
			return true;
		}
			
		/*boolean noOverlap = 
				this.x + this.width < other.x ||
				other.x + other.width < this.x ||
				this.y + this.height < other.y ||
				other.y + other.height < this.y;
		return !noOverlap;*/
	}
	
	public boolean contains(double x, double y) {
		if(x > this.x && x < this.x + this.width
		&& y > this.y && y < this.y + this.height) {
			return true;
		}else {
			return false;
		}
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
}
