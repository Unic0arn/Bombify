package entities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException; 
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.loading.LoadingList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Resource Manager Tutorial from Slick Wiki, helper for sound, 
 * animation and texts with XML. 
 * @author Tiago �Spiegel� Costa
 *
 */
public class ResourceManager {
 
	private static String SPRITE_SHEET_REF = "__SPRITE_SHEET_";
 
	private static ResourceManager _instance = new ResourceManager();
 
	private Map<String, Sound> soundMap;
	private Map<String, Image> imageMap;
	private Map<String, ResourceAnimationData> animationMap;
	private Map<String, String> textMap;
 
	private ResourceManager(){
		soundMap 	 = new HashMap<String, Sound>();
		imageMap 	 = new HashMap<String, Image>();
		animationMap = new HashMap<String, ResourceAnimationData>();
		textMap 	 = new HashMap<String, String>();
	}
 
	public final static ResourceManager getInstance(){
		return _instance;
	}
 
	public void loadResources(InputStream is) throws SlickException {
		loadResources(is, false);
	}
 
	public void loadResources(InputStream is, boolean deferred) throws SlickException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SlickException("Could not load resources", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SlickException("Could not load resources", e);
		} catch (IOException e) {
			throw new SlickException("Could not load resources", e);
		}
 
		// normalize text representation
        doc.getDocumentElement ().normalize ();
 
        NodeList listResources = doc.getElementsByTagName("resource");
 
        int totalResources = listResources.getLength();
 
        if(deferred){
        	LoadingList.setDeferredLoading(true);
        }
 
 
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
 
        	Node resourceNode = listResources.item(resourceIdx);
 
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
 
        		String type = resourceElement.getAttribute("type");
 
        		if(type.equals("image")){
        			addElementAsImage(resourceElement);
        		}else if(type.equals("sound")){
        			addElementAsSound(resourceElement);
        		}else if(type.equals("text")){
        			addElementAsText(resourceElement);
        		}else if(type.equals("font")){
 
        		}else if(type.equals("animation")){
        			addElementAsAnimation(resourceElement);
        		}
        	}
        }
 
	}
 
	private void addElementAsAnimation(Element resourceElement) throws SlickException{
		loadAnimation(resourceElement.getAttribute("id"), resourceElement.getTextContent(), 
				Integer.valueOf(resourceElement.getAttribute("tw")),
				Integer.valueOf(resourceElement.getAttribute("th")),
				Integer.valueOf(resourceElement.getAttribute("duration")));
	}
 
	private void loadAnimation(String id, String spriteSheetPath,
			int tw, int th, int duration) throws SlickException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		loadImage( SPRITE_SHEET_REF + id, spriteSheetPath);
 
		animationMap.put(id, new ResourceAnimationData(SPRITE_SHEET_REF+id, tw, th, duration));
	}
 
	public final Animation getAnimation(String ID){
		ResourceAnimationData rad = animationMap.get(ID);
 
		SpriteSheet spr = new SpriteSheet(getImage(rad.getImageId()), rad.tw, rad.th);
 
		Animation animation = new Animation(spr, rad.duration);
 
		return animation;
	}
 
	private void addElementAsText(Element resourceElement) throws SlickException{
		loadText(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public String loadText(String id, String value) throws SlickException{
		if(value == null)
			throw new SlickException("Text resource [" + id + "] has invalid value");
 
		textMap.put(id, value);
 
		return value;
	}
 
	public String getText(String ID) {
		return textMap.get(ID);
	}
 
	private void addElementAsSound(Element resourceElement) throws SlickException {
		loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public Sound loadSound(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Sound resource [" + id + "] has invalid path");
 
		Sound sound = null;
 
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load sound", e);
		}
 
		this.soundMap.put(id, sound);
 
		return sound;
	}
 
	public final Sound getSound(String ID){
		return soundMap.get(ID);
	}
 
 
	private final void addElementAsImage(Element resourceElement) throws SlickException {
		loadImage(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
 
	public Image loadImage(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		Image image = null;
		try{
			image = new Image(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load image", e);
		}
 
		this.imageMap.put(id, image);
 
		return image;
	}
 
	public final Image getImage(String ID){
		return imageMap.get(ID);
	}
 
  
 
 
 
	private class ResourceAnimationData{
		int duration;
		int tw;
		int th;
		String imageId;
 
		public ResourceAnimationData(String id, int tw, int th, int duration){
			this.imageId = id;
			this.tw = tw;
			this.th = th;
			this.duration = duration;
		}
 
		public final int getDuration() {
			return duration;
		}
		public final void setDuration(int duration) {
			this.duration = duration;
		}
		public final int getTw() {
			return tw;
		}
		public final void setTw(int tw) {
			this.tw = tw;
		}
		public final int getTh() {
			return th;
		}
		public final void setTh(int th) {
			this.th = th;
		}
		public final String getImageId() {
			return imageId;
		}
		public final void setImageId(String imageId) {
			this.imageId = imageId;
		}
 
	}
}