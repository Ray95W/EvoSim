package application;

public class Vektor {
	public double x;
	public double y;
	
	public Vektor(double x, double y) {
		this.set(x,y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void add (double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void add(Vektor other) {
		this.add(other.x, other.y);
	}
	
	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
