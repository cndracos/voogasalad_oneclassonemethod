package authoring.views;

import java.util.HashMap;
import java.util.Map;

import authoring.actions.ActionAdderView;
import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;
import authoring.factories.ClickElementType;
import authoring.factories.ElementFactory;
import data.DataRead;
import engine.components.Sprite;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class EntityBox extends VBox {
	private static final double IMAGE_BUFFER = 20;
	private static final double PROPORTION = 3;
	private Image image;
	private ImageView imageView;
	
	private ElementFactory eFactory = new ElementFactory();

	Map<Class, Object[]> componentAttributes = new HashMap<>();

	private double boxDimension = (EntityView.ENITITY_VIEW_WIDTH - EntityTab.SCROLLBAR_WIDTH)/PROPORTION;

	public EntityBox(String t, Map<Class, Object[]> m) {
		this.getStyleClass().add("entity-box");
		this.setWidth(boxDimension);
		this.setHeight(boxDimension);
		componentAttributes = m;
		setPreview();
		this.setOnDragDetected(e -> {
			Dragboard db = this.startDragAndDrop(TransferMode.COPY);
			ClipboardContent cc = new ClipboardContent();
			cc.putImage(image);
			cc.putString(componentAttributes.get(engine.components.Name.class)[0].toString());	
			db.setContent(cc);
			e.consume();
		});
	}



	private void setPreview() {
		image = DataRead.loadImage((String) componentAttributes.get(Sprite.class)[0]);
		imageView = new ImageView(image);
		imageView.setFitHeight(boxDimension-IMAGE_BUFFER);
		imageView.setFitWidth(boxDimension-IMAGE_BUFFER);
		this.getChildren().add(imageView);
	}

}
