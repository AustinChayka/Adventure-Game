import java.awt.event.*;

/**
 *This is a platformer level 
 * @author Andrew Gamsaragan, Austin Chayka
 * @version 3/4/18
 */
public class Level4 extends Level{
    
    private String[] playerDialog = {"Strange..", "This pathway", "must have collapsed..",
    "but I must continue..."};
    private double dialogTimer = 2f;
    private int dialogIndex = 0;
    
   /**
     * This is the constructor class for the Level 
     * AnnoyingLevel 
     * This loadLevel has you go into the folder , the next level that is loaded after the level
     * camera is what the camera will follow so set the state name of
     * in this case the player so it follows the player
     * leve.setLightBLock is the amount of light that is shown
     */
    public Level4() {
        
        this.stateName = "level4";
        loadLevel("Res/Levels/Level4.png", "level5");
        
        camera = new Camera("player");
        
        level.setLightBlock(Light.FULL);
        
    }
  /**
     * This is the initializer which sets the ambient color which is the 
     * background color
     * setShowing maks it show
     * and showTitleText RIP is the name of the level that comes up when the level
     * first loads look at the top of the sreen
     */
  
    @Override
    public void init(Container c) {
        
        c.getRenderer().setAmbientColor(0xff393939);
        c.getGUI().setShowing(true);
        c.getGUI().showTitleText("Chasm");
        
    }
      /**
     * If the escape key is pressed the state will change 
     * to the menu state,  container which is c
     * if you are dead then it will remove the objects
     */
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        dialogTimer -= dt;
        
        if(dialogIndex < playerDialog.length && dialogTimer <= 0) {
            c.getGUI().showInfoText(playerDialog[dialogIndex++]);
            if(dialogIndex == 1) dialogTimer = 6f;
            else dialogTimer = 3f;
        }
        
        if(c.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            
            sm.changeState("menu", c);
            
        }
        
        for(int i = 0; i < objects.size(); i++) {
            
            objects.get(i).update(c, this, dt);
            if(objects.get(i).isDead()) {
                
                objects.remove(i);
                i--;
                
            }
            
        }
        
        camera.update(c, this, dt);
        
    }
     /**
     * r.drawImage draws the image with consturctor of object,int , int
     * .redner takes container c and renderer r 
     */
    @Override
    public void render(Container c, Renderer r) {
        
        camera.render(r);
        
        r.drawImage(background, (int)camera.getX() / 2, (int)camera.getY() / 2);
        r.drawImage(backing2, (int)camera.getX() / 4, (int)camera.getY() / 4);
        r.drawImage(backing1, 0, 0);
        r.drawImage(subLevel, 0, 0);
        r.drawImage(level, 0, 0);
        
        for(GameObject object: objects) {
            
            object.render(c, r);
            
        }
        
    }
    /**
     * this is the exit method that is overriden although it has no code in it
     * because there is no need to
     */
    @Override
    public void exit() {
        
        
        
    }
    
}