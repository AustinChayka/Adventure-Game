import java.util.*;
import java.awt.event.*;

public class Level1 extends Level {
    
    private float showName = 0;
    private String[] playerDialog = {"....", "Where am I...", "So dark in here..",
    "Maybe if I go deeper", "I can find a way out..."};
    private double dialogTimer = 2f;
    private int dialogIndex = 0;
    
    public Level1() {
        
        this.stateName = "level1";
        
        loadLevel("Res/Levels/Level1.png", "level2");
        camera = new Camera("player");
        
        level.setLightBlock(Light.FULL);
        
        objects.add(new PuzzleDoor(71, 73, this, "SecretDoor1"));
        objects.add(new ProjectileBlock(6, 59, 1, this));
        objects.add(new ProjectileBlock(46, 53, -1, this));
        objects.add(new Campfire(84 * TS, 76 * TS));
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getRenderer().setAmbientColor(0xff393939);
        c.getGUI().setShowing(true);
        c.getGUI().showTitleText("Caves");
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        dialogTimer -= dt;
        
        if(dialogIndex < playerDialog.length && dialogTimer <= 0) {
            c.getGUI().showInfoText(playerDialog[dialogIndex++]);
            if(dialogIndex == 3) dialogTimer = 6f;
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
    
    @Override
    public void exit() {
                
        
    }
    
}