package application;

import java.util.Random;

public class Hunter extends Sprite {

	/* Erkenntnisse:
	 * Nahrungsüberschuss führt zu Massenaussterben (75-80%)
	 * Aussterberate genau wie bei erdhistorischen Massenaussterben - Zufall?
	 * Jäger "lernen" sich in eine bestimmte Richtung zu bewegen --> Evolutionsartig
	 */
	
	
	// SIMULATION PARAMETERS
	final int SPEED = 3;
	final double MUTATION_INTENSITY = 0.005;
	final boolean directionalCosts = true;
	//

	private Random r;
	private int energy;
	private double[] genom = new double[8];
	private int previousVektor;
	
	public Hunter(double[] genom) {
		super();
		this.r = new Random();
		this.energy = 500;
		this.genom = genom;
		this.newMutation();
		this.previousVektor = 0;
	}
	
	public void move() {
		double g = r.nextDouble();
		if(directionalCosts) {
			if(g < this.genom[0]) {
				this.addToPosition(SPEED, 0);
				useEnergy(0);
			}else if (g < this.genom[1] + this.genom[0]) {
				this.addToPosition(SPEED, SPEED);
				useEnergy(1);
			}else if (g < this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(0, SPEED);
				useEnergy(2);
			}else if (g < this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, SPEED);
				useEnergy(3);
			}else if (g < this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, 0);
				useEnergy(4);
			}else if (g < this.genom[5] + this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, -SPEED);
				useEnergy(5);
			}else if (g < this.genom[6] + this.genom[5] + this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(0, -SPEED);
				useEnergy(6);
			}else{
				this.addToPosition(SPEED, -SPEED);
				useEnergy(7);
			}
		}else {
			if(g < this.genom[0]) {
				this.addToPosition(SPEED, 0);
			}else if (g < this.genom[1] + this.genom[0]) {
				this.addToPosition(SPEED, SPEED);
			}else if (g < this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(0, SPEED);
			}else if (g < this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, SPEED);
			}else if (g < this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, 0);
			}else if (g < this.genom[5] + this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(-SPEED, -SPEED);
			}else if (g < this.genom[6] + this.genom[5] + this.genom[4] + this.genom[3] + this.genom[2] + this.genom[1] + this.genom[0]) {
				this.addToPosition(0, -SPEED);
			}else{
				this.addToPosition(SPEED, -SPEED);
			}
			useEnergy();
		}
	}
	
	public void useEnergy(int actualVektor) {
		int course = previousVektor - actualVektor;
		if(course == 0) { 
			// straight forward
			this.energy -= 2;
		}else if(course == 1 || course == -1 || course == 7 || course == -7) {
			// slightly left / right
			this.energy -= 2;
		}else if(course == 2 || course == -2 || course == 6 || course == -6) {
			// left / right
			this.energy -= 3;
		}else if(course == 3 || course == -3 || course == 5 || course == -5) {
			// hard left / right
			this.energy -= 4;
		}else if (course == 4 || course == -4) {
			// turn around
			this.energy -= 4;
		}else {
			System.out.println("useEnergy: Error");
		}
		this.previousVektor = actualVektor;
	}
	
	public void useEnergy() {
		this.energy -= 2;
	}
	
	public void newMutation() {
		int genomIte = r.nextInt(8);
		if(this.genom[genomIte] > MUTATION_INTENSITY) {
			this.genom[genomIte] -= MUTATION_INTENSITY;
			this.genom[r.nextInt(8)] += MUTATION_INTENSITY;
		}
	}

	public void addToPosition(double x, double y) {
		this.setPosition(this.getPosition().getX()+x, this.getPosition().getY()+y);
	}
	
	public double[] getGenom() {
		return this.genom;
	}

	public int getEnergy() {
		return energy;
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
}
