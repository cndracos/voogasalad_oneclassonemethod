package authoring.views.properties;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import authoring.entities.Entity;
import authoring.entities.data.EntityBuilderData;
import authoring.entities.data.SudoEntityLoader;
import authoring.forms.EntityComponentFormCollection;
import authoring.forms.FormCollection;
import authoring.forms.PropertiesComponentFormCollection;
import engine.components.Component;
import javafx.scene.Node;

public class EntityPropertiesView extends PropertiesView{
	private static final String ENTITIES_PACKAGE = "data/entities/";
	private static final String FILE_EXTENSION = ".xml";
	private static final String ENTITY_PREFIX = null;
	
	private SudoEntityLoader sel = new SudoEntityLoader();
	private File entityFile;
	private String type;
	private Object[] sudoEntity;
	private Map<Class,Object[]> sudoComponents;
	private Consumer onSubmit;
	private String name;
	private FormCollection componentFormCollection;
	

	/**
	 * 
	 * @param entityNumber
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public EntityPropertiesView(String name, Consumer<Map<Class, Object[]>> oS) throws Exception {
		super();
		this.name = name;
		this.entityFile = new File(ENTITIES_PACKAGE + name + FILE_EXTENSION);
		this.sudoEntity = sel.buildEntity(this.entityFile);
		this.type = (String) sudoEntity[0];
		this.sudoComponents = (Map<Class, Object[]>) sudoEntity[1];
		this.onSubmit = oS;
	}
	
	@Override
	protected void fill() {
		componentFormCollection = new EntityComponentFormCollection(sudoComponents, new String[] {""}, e-> {save((List<Object[]>) e);});
		this.getRoot().getChildren().add((Node) componentFormCollection);
		componentFormCollection.setLanguage(language);
		List<String> componentsToAdd = new ArrayList<String>();
		for(Entry entry : sudoComponents.entrySet()) {
			String t = ((Class) entry.getKey()).getName();
			t = t.replaceAll("engine.components.", "");
			componentsToAdd.add(t);
		}
		componentFormCollection.fill(componentsToAdd);
		this.getRoot().getScene().getWindow().sizeToScene();
	}

	private void save(List<Object[]> e) {
		EntityBuilderData ebd = new EntityBuilderData();
		try {
			ebd.save(e);
			this.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	protected String title() {
		return this.name;
	}

}
