package authoring.forms;

import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public interface FormCollection {
	
	/**
	 * Populates the form
	 */
	public void fill(List<String> entityProperties);
	
	/**
	 * Adds a set of Exceptions to the list of possible components for this form collection
	 * @param exceptions
	 */
	public void setExceptions(String[] exceptions);
	
	/**
	 * Sets the action when the save button is pressed
	 * @param onSave
	 */
	public void setSaveConsumer(Consumer onSave);
	
	/**
	 * Gets a list of the currently active component forms
	 * @return
	 */
	public List<ComponentForm> getActiveForms();
	
	


	public void setLanguage(Properties language);

}
