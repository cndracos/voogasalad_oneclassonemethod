package authoring.gamestate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import authoring.exceptions.AuthoringAlert;
import authoring.exceptions.AuthoringException;

/**
 * Class to load in the state of an authoring environment (presets plus states)
 * @author Dylan Powers
 *
 */
public class AuthoringStateLoader {

	private DocumentBuilder documentBuilder;
	private final String ENTITY_FOLDER = "data/entities/";
	/**
	 * Create this loader with a given game name to load. 
	 * @param gameName the name of the game to load
	 */
	public AuthoringStateLoader(String gameName) {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new AuthoringException(e, AuthoringAlert.NO_SHOW);
		}
		File[] entityFiles = new File(ENTITY_FOLDER).listFiles();
		for (File f : findFilesForGame(gameName, entityFiles)) {
			System.out.println(f.getName());
		}
	}
	/**
	 * Finds all of the files that pertain to a specific game.
	 * @param gameName the name of the game to search for
	 * @param filesToSearch the files to search through for the given game type
	 * @return a list of files that pertain to a given game
	 */
	private List<File> findFilesForGame(String gameName, File[] filesToSearch) {
		List<File> gameEntities = new ArrayList<>();
		Document d;
		for (File poss : filesToSearch) {
			try {
				d = documentBuilder.parse(poss);
				Element e = d.getDocumentElement();
				if (e.getAttribute("game").equals(gameName)) {
					gameEntities.add(poss);
				}
			} catch (SAXException | IOException e) {
				throw new AuthoringException(e, AuthoringAlert.NO_SHOW);
			}
		}
		return gameEntities;
	}
}