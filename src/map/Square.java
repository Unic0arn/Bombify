package map;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public interface Square {
	/**
	 * Renders this square with the specified data. 
	 * The size of the square is proportional to the size of the field. 
	 * Size is calculated by dividing the width and height with the number of tiles.
	 * @param container - The container from which the method retrieves the size of the canvas.
	 * @param g - The graphics component to be used while rendering.
	 * @param x - The x-coordinate in the grid.
	 * @param y - The y-coordinate in the grid.
	 * @param tiles - The amount of tiles in the grid. Expected to be a square.
	 * @throws SlickException
	 */
	public void render(GameContainer container, Graphics g,
			int x, int y, int tiles) throws SlickException ;
	public int getGridx();
	public int getGridy();
}
