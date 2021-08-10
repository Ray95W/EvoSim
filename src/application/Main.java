package application;
	
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	// SIMULATION PARAMETERS
	final int 	       START_MICROBES = 10000;
	final int 	   MICROBES_PER_FRAME = 5;
	final int MICROBE_NUTRITION_VALUE = 40;
	final int 			START_HUNTERS = 1;	
	//
	
	int microbeCount = 0;
	int hunterCount = 0;
	int drawCounter = 0;
	int frameCounter = 0;
	final int ACTIONS_PER_SECOND = 30;
	
	static int canvasWidth = 600;
	static int canvasHeight = 600;
	
	public Random r;
	
	Color BACKGROUND_COLOR = Color.BEIGE;
	
	BorderPane root;
	Scene scene;
	private ArrayList<Microbe> microbeList;
	private ArrayList<Hunter> hunterList;
	
	Sprite counterSprite;
	Text counter;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			scene = new Scene(root,canvasWidth,canvasHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			initialize();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void initialize() {
		Canvas canvas =  new Canvas(canvasWidth, canvasHeight);
		GraphicsContext context = canvas.getGraphicsContext2D();
		root.setCenter(canvas);
		context.setFill(BACKGROUND_COLOR);
		context.fillRect(0, 0, canvasWidth, canvasHeight);
		
		counter = new Text();
		counterSprite = new Sprite();
		counter.setX(20);
		counter.setY(20);
		counter.setFill(Color.BLACK);
		counter.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		microbeList = new ArrayList<Microbe>();
		hunterList = new ArrayList<Hunter>();
		
		r = new Random();
		
		for(int i=0; i<START_MICROBES; i++) {
			spawnMicrobe();
		}
		
		double[] genom = {0.125, 0.125, 0.125, 0.125, 0.125, 0.125, 0.125, 0.125};
		
		for(int i=0; i<START_HUNTERS; i++) {
			spawnHunter(genom);
		}
		
		loop(context);
		
	}
	
	public void loop(GraphicsContext context) {
		AnimationTimer loop = new AnimationTimer() {
			public void handle(long nanotime) {
			
				frameCounter++;
				if(frameCounter == 60/ACTIONS_PER_SECOND) {
					frameCounter = 0;
					
					context.setFill(BACKGROUND_COLOR);
					context.fillRect(0, 0, canvasWidth, canvasHeight);
				
					drawCounter++;
					if(drawCounter == 60) {
						updateCounter();
						drawCounter = 0;
					}
					for(int i=0; i<MICROBES_PER_FRAME; i++) {
						spawnMicrobe();
					}
					for(Microbe tempMicrobe : microbeList) {
						tempMicrobe.render(context);
					}
					for(int i=0; i<hunterList.size(); i++) {
						hunterList.get(i).move();
						checkBorderCollision(hunterList.get(i));
						for(int j=0; j<microbeList.size(); j++) {
							if(hunterList.get(i).getBoundary().overlaps(microbeList.get(j).getBoundary())) {
								microbeList.remove(j);
								microbeCount--;
								hunterList.get(i).addEnergy(MICROBE_NUTRITION_VALUE);
							}
						}
						if(hunterList.get(i).getEnergy() >= 1000) {
							hunterList.get(i).setEnergy(500);
							spawnHunter(hunterList.get(i).getGenom(), hunterList.get(i).getPosition());
						}
					}
					for(int i=0; i<hunterList.size(); i++) {
						if(hunterList.get(i).getEnergy() <= 0) {
							hunterList.remove(hunterList.get(i));
							hunterCount--;
						}
					}
					for(int i=0; i<hunterList.size(); i++) {	
						hunterList.get(i).render(context);
					}
					counterSprite.render(context);				
				}
			}
		};
		loop.start();
	}
	
	public void spawnHunter(double[] genom, Vektor position) {
		Hunter hunter = new Hunter(genom);
		hunter.setPosition(position.getX(), position.getY());
		hunter.setImage(new File("Sprites/hunter.png").toURI().toString());
		hunter.newMutation();
		hunterList.add(hunter);
		hunterCount++;
	}
	
	public void spawnHunter(double[] genom) {
		Hunter hunter = new Hunter(genom);
		hunter.setPosition(r.nextInt(canvasWidth), r.nextInt(canvasHeight));
		hunter.setImage(new File("Sprites/hunter.png").toURI().toString());
		hunter.newMutation();
		hunterList.add(hunter);
		hunterCount++;
	}
	
	public void spawnMicrobe() {
		Microbe microbe = new Microbe();
		microbe.setPosition(r.nextInt(canvasWidth), r.nextInt(canvasHeight));
		microbe.setImage(new File("Sprites/microbe.png").toURI().toString());
		microbeList.add(microbe);
		microbeCount++;
	}
	
	public void checkBorderCollision(Hunter hunter) {
		if(hunter.getPosition().getX() > canvasWidth) {
			hunter.getPosition().setX(0);
		}
		if(hunter.getPosition().getX() < 0) {
			hunter.getPosition().setX(canvasWidth);
		}
		if(hunter.getPosition().getY() > canvasHeight) {
			hunter.getPosition().setY(0);
		}
		if(hunter.getPosition().getY() < 0) {
			hunter.getPosition().setY(canvasHeight);
		}
	}
	
	public void updateCounter() {
		counter.setText("Microbes: "+microbeCount+" | Hunters: "+hunterCount);
		counterSprite.setImage(textToImage(counter));
	}
	
	public WritableImage textToImage(Text text) {
		SnapshotParameters snapPara = new SnapshotParameters();
		snapPara.setFill(BACKGROUND_COLOR);
	    return text.snapshot(snapPara, null);
	}
	
}
