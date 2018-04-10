package frontend.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;

//import com.sun.beans.finder.ClassFinder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import sun.reflect.Reflection;

import frontend.components.*;

public class EntityView extends BorderPane {
	private double entityViewWidth = 300;
	private ArrayList<String> tabsList = new ArrayList<String>();
	private Object clipboard;
	private ArrayList<String> entityTypes = new ArrayList<String>();
	private TabPane tabPane = new TabPane();
	private Consumer clipboardHandler;
	
	//Consumer for Handling the onClose event of an EntityBuilderView
	BiConsumer<String, File> onClose = (e,y) -> {saveEntity(e,y);};
	//Consumer for Creating a new Entity(Opens EntityBuilderView)
	Consumer newEntity = (e) -> {
		entityTypes.addAll(Arrays.asList(getEntitiesInEntitiesPackage()));
		EntityBuilderView entityBuilderView = new EntityBuilderView(entityTypes, onClose);
	};
	private Map<String, Consumer> consumerMap = new HashMap<String,Consumer>(){{
		this.put("newEntity", newEntity);
	}};
	
	public EntityView(Consumer ch) {
		super();
		clipboardHandler = ch;
		this.setPrefWidth(entityViewWidth);
		this.getStyleClass().add("entity-view");
		this.setTop(new Toolbar("Entity", consumerMap));
		this.setCenter(tabPane);
	}
	
	
	private void addTab(String type) {
			ClipboardListener c = new ClipboardListener();
			EntityTab temp = new EntityTab(type, entityViewWidth);
			temp.getSelectedElementProperty().addListener(c);
			tabPane.getTabs().add(temp);
	}
	
	/**
	 * Opens the window to delete an entity
	 */
	public void deleteEntity() {
	}
	/**
	 * Opens the window to edit an entity
	 */
	public void editEntity() {
		
	}
	/**
	 * Called when a EntityBuilderView is closed
	 * @param entityType String representing the type of entity that this object represents
	 * @param imgFile Image file corresponding to the Sprite Image for this object.
	 */
	public void saveEntity(String entityType, File imgFile) {
	//Turn the imageFile into a usableImage
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imgFile);
		} catch (IOException e) {
			//TODO: Show ImageNotFoundException
		}
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
    //Check to see if a tab exists for the type
        if(tabsList.isEmpty() || !tabsList.contains(entityType)) { 
        	addTab(entityType);
        	tabsList.add(entityType);
        }   
    //Add the entityBox
        for(Tab tab : tabPane.getTabs()) {
        	if(tab.getText().equals(entityType)) {
        		((EntityTab) tab).addNewEntity("object", image);
        	}
        }
    }
	
	/**
	 * Gets all of the class names from a given package. Useful when determining which properties can be changed.
	 * @return a String array of classes from a given package
	 */
	protected String[] getEntitiesInEntitiesPackage() {
		String pckgName = "frontend/entities";
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new IllegalStateException("Can't get class loader.");
        }
 
        URL resource = cld.getResource(pckgName.replace('.', '/'));
        if (resource == null) {
            throw new RuntimeException("Package " + pckgName + " not found on classpath.");
        }
        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            throw new IllegalArgumentException("Could not get directory resource for package " + pckgName + ".");
        }
        List<String> classes = new ArrayList<String>();
        for (String filename : directory.list()) {
            if (filename.endsWith(".class") && !filename.startsWith("Entity")) { //Check to make sure its a class file and not the superclass
                String classname = buildClassname(pckgName, filename);
                String clazz = classname.replace(".java", "");
				// Strip everything except for the word following the last period (the actual class name)
               classes.add(clazz.substring(clazz.lastIndexOf(".") + 1));
            }
        }
        return classes.toArray(new String[classes.size()]);
	}
	
	/**
	 * Builds the class name to fully represent a given class
	 * @param pckgName the package to look for the class ine
	 * @param fileName the name of the class file
	 * @return a String representing the fully-qualified class name
	 */
    private String buildClassname(String pckgName, String fileName) {
    		String className = pckgName + '.' + fileName.replace(".class", "");
        return className;
    }

	private class ClipboardListener implements ChangeListener{

		@Override
		public void changed(ObservableValue clipboardObject, Object oldValue, Object newValue) {
			clipboardHandler.accept(newValue);
		}
	}

}