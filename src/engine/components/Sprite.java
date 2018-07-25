package engine.components;

import java.io.FileNotFoundException;
import java.util.function.Consumer;


import data.DataRead;
import engine.exceptions.EngineException;
import engine.systems.SpriteTransition;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;


/**
 * Sprite component containing an image. Constructor and setter throw file not found if the filepath is incorrect.
 * Allows for animation with a special system tool. Animation system calls the sprite's animate method to do this.
 * @author fitzj
 */
public class Sprite extends SingleStringComponent implements Component, StringComponent, ReadStringComponent {

	private String name;
	private Consumer removeAction;

	public static final String KEY = "Sprite";
	
	/**
	 * Boolean used to track state of animation. Not used for non-animated sprites.
	 */
	private boolean isPlaying = false;
	
	@XStreamOmitField
	private transient ImageView image;
	
	@XStreamOmitField
	private transient Animation a;
	
	public Sprite(int pid, String path) throws FileNotFoundException {
	    super(pid, path);
		setData(path);
		name = path;
	}
	
	public ImageView getImage() {
		if (image == null) {
			setData(getData());
		}
		return image;
	}

	@Override
	public void setData(String im) {
		if(a != null) {
			a.stop();
			isPlaying = false;
		}
		try {
			name = im;
			image = new ImageView(DataRead.loadImage(im));
			
		} catch(RuntimeException e) {
			System.out.print("Cant load image no Application");
		}
		
	}
	
	/**
	 * Animates sprite using a Sprite Transition, a kind of animation that changes the image's viewport.
	 * @throws EngineException 
	 */
	public void animate(String file) throws EngineException {
		a = new SpriteTransition(getImage(), file);
		a.setCycleCount(Animation.INDEFINITE);
		a.play();
		isPlaying = true;
	}
	
	/**
	 * Pauses animation
	 */
	public void pauseAnimation() {
		if(a != null) {
			a.pause();
		}
	}
	
	/**
	 * Plays animation, does not work if animiate  has not been called
	 */
	public void playAnimation() {
		if(a != null) {
			a.play();
		}
	}
	
	/**
	 * Returns if the animation is playing
	 * @return	If the animation is playing
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setOnImageRemoved(Consumer action) {
		this.removeAction = action;
	}

	public void remove() {
		removeAction.accept(null);
	}
	
	public String getKey() {
		return KEY;
	}
	public String getImageName() {
		return name;
	}
	public String getImageFile(){
		return getData();
	}
}

