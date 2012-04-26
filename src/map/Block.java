package map;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * A class that contains information about a block in the map.
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-22
 */
public class Block implements Square{
	boolean immovable =true;
	private Image imgBlockImmovable,imgBlockMovable;
	int gridx,gridy,posx,posy,sizex,sizey;


	public int getGridx() {
		return gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public Block(int x, int y, GameContainer container, int tiles) {
		try {
			imgBlockImmovable = new Image("res/concrete.png");
			imgBlockMovable = new Image("res/brick.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gridx = x;
		gridy = y;
		sizex=container.getWidth()/tiles;
		sizey=container.getHeight()/tiles;
		posx = container.getWidth()/tiles * x;
		posy = container.getHeight()/tiles * y;
	}

	/**
	 * Checks for immovable walls or noth. 
	 * @return true/false. 
	 */
	public boolean isImmovable() {
		return immovable;
	}

	//Block that can't move. 
	public Block setImmovable(boolean b){
		immovable = b;
		return this;
	}
	public void render(GameContainer container, Graphics g,
			int x, int y, int tiles)
	throws SlickException {

		if(immovable){
			g.drawImage(imgBlockImmovable,
					posx,
					posy,
					posx+sizex,
					posy+sizey,
					0,
					0,
					imgBlockImmovable.getWidth(),
					imgBlockImmovable.getHeight());
		}else{
			g.drawImage(imgBlockMovable,
					posx,
					posy,
					posx+sizex,
					posy+sizey,
					0,
					0,
					imgBlockMovable.getWidth(),
					imgBlockMovable.getHeight());
		}
	}
}
