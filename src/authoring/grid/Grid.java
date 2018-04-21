package authoring.grid;

import authoring.entities.Entity;
import javafx.scene.layout.TilePane;

/**
 * A container class for all of the cells in a grid, which each represent entities if they are filled.
 * @author Dylan Powers
 * @author Hemanth Yakkali
 *
 */
public class Grid extends TilePane {

	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 600;
	
	private int numRows;
	private int numCols;
	
	/**
	 * Initializes the grid with a given number of rows and columns
	 * @param width the desired width of the grid
	 * @param height the desired height of the grid
	 */
	public Grid(int width, int height) {
		this.numRows = width/Entity.ENTITY_HEIGHT;
		this.numCols = height/Entity.ENTITY_WIDTH;
		
		for(int i=0;i<this.numCols*this.numRows;i++) {
			Cell c = new Cell();
			this.getChildren().add(c);
		}

		this.setPrefSize(width, height);
		this.setPrefTileWidth(Entity.ENTITY_WIDTH);
		this.setPrefTileHeight(Entity.ENTITY_HEIGHT);
	}
	
	/**
	 * Empty constructor, use default values
	 */
	public Grid() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Adds new row of cells to the grid
	 */
	public void addRow() {
		for(int i=0;i<this.numRows;i++) {
			Cell c = new Cell();
			this.getChildren().add(c);
		}
		this.setPrefHeight(this.getHeight()+Entity.ENTITY_HEIGHT);
	}
	
	/**
	 * Adds new column of cells to the grid
	 */
	public void addCol() {
		int index = this.numRows;
		for(int i=0;i<this.numCols;i++) {
			Cell c = new Cell();
			this.getChildren().add(index,c);
			index+=this.numRows+1; //add one because width of grid increases by one ENTITY_WIDTH
		}
		this.setPrefWidth(this.getWidth()+Entity.ENTITY_WIDTH);
	}

}
