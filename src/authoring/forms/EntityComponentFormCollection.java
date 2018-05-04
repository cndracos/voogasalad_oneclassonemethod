package authoring.forms;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import authoring.entities.Entity;


/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class EntityComponentFormCollection extends AbstractComponentFormCollection implements FormCollection {
	
	Map<Class,Object[]> sudoComponents = null;
	
	public EntityComponentFormCollection() {
		super(EntityComponentForm.class);
	}
	public EntityComponentFormCollection(Map<Class,Object[]> sudoComponents, String[] newExceptions, Consumer onSave) {
		super(newExceptions, onSave, EntityComponentForm.class);
		this.sudoComponents = sudoComponents;

	}
	
	public EntityComponentFormCollection( String[] newExceptions, Consumer onSave) {
		super(newExceptions, onSave, EntityComponentForm.class);

	}

	@Override
	protected void save(Consumer onSave) {
		ArrayList<Object[]> componentsToAdd = new ArrayList<Object[]>();
		for(ComponentForm cf : this.getActiveForms()) {
			Object[] component = ((EntityComponentForm) cf).buildComponent();
			componentsToAdd.add(component);	
		}
		onSave.accept(componentsToAdd);
		
	}

	@Override
	protected boolean hasCurrentValue(String componentName) {
			try{
				if(!sudoComponents.equals(null)) {
					if(sudoComponents.containsKey(Class.forName("engine.components." + componentName)))
					return true;
				}
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		return false;
	}

	@Override
	protected String getCurrentValue(String componentName) {
		try {
			Object[] temp = sudoComponents.get(Class.forName("engine.components." + componentName));
			return (String) temp[0];
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void delete(ComponentForm cf) {
		this.getActiveForms().remove(cf);
		this.getChildren().remove(cf);
	}
}

