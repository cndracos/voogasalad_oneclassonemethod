package authoring.views;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Logger;

import authoring.factories.ClickElementType;
import authoring.factories.ElementFactory;
import authoring.factories.ElementType;
import authoring.languages.AuthoringLanguage;
import authoring.views.properties.EntityPropertiesView;
import engine.components.Component;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

/**
 * 
 * @author Collin Brown(Cdb55)
 *
 */
public class EntityTab extends Tab implements AuthoringLanguage {
	Properties language = new Properties();
	public static final double SCROLLBAR_WIDTH = 20;
	public static final double VIEW_WIDTH = 0;
	private static final String COMPONENT_PREFIX = "engine.components.";

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ElementFactory eFactory = new ElementFactory();
	private Consumer onSave = e->{save(e);};

	FlowPane pane;
	ScrollPane externalPane;

	double myEntityViewWidth;

	public EntityTab(String name, double entityViewWidth) {
		super(name);
		this.setId(name);
		myEntityViewWidth = entityViewWidth;	
		myEntityViewWidth = entityViewWidth;		
		this.setClosable(false);
		this.getStyleClass().add("entity-tab");
		assemble();
	}

	private void save(Object e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * builds the ScrollPane and the FlowPane within it
	 */
	private void assemble() {
		externalPane = new ScrollPane();
		externalPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		externalPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		pane = new FlowPane();
		pane.setPrefWidth(myEntityViewWidth);
		externalPane.setContent(pane);
		this.setContent(externalPane);
	}

	/**
	 * Adds a new entity to the entityView
	 * @param type the type of entity being created
	 * @param componentAttributes the attributes of a component
	 */
	public void addNewEntity(String type, Map<Class, Object[]> componentAttributes) {
		EntityBox eb = new EntityBox(type, componentAttributes);
		eb.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
				ContextMenu cMenu = new ContextMenu();
				try {
					MenuItem delete = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Delete", e->{removeEntity(eb);});
					MenuItem edit = (MenuItem) eFactory.buildClickElement(ClickElementType.MenuItem, "Edit", e->{editEntity(componentAttributes);});
					cMenu.getItems().addAll(edit, delete);

					cMenu.setAutoHide(true);
				} catch (Exception e1) {
					LOGGER.log(java.util.logging.Level.SEVERE, e1.toString(), e1);
				}
				cMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
			}
		});
		pane.getChildren().add(eb);
	}

	private void editEntity(Map<Class,Object[]> eb) {
		Map<Class, Object[]> sudoComponents = eb;
		try {
			EntityPropertiesView epv = new EntityPropertiesView(((String)eb.get(Class.forName(COMPONENT_PREFIX + "Name"))[0]), onSave);
			epv.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the graphic representation of the ComponentTab
	 */
	public Node getNode() {
		return pane;
	}

	/**
	 * Removes and entity from the entityView
	 * @param eb the entitBox to be removed
	 */
	private void removeEntity(EntityBox eb) {
		pane.getChildren().remove(eb);
	}

	@Override
	public void setLanguage(Properties lang) {
		this.language = lang;
		this.setText(language.getProperty(this.getId()));
	}


}
