import java.awt.event.*;

public class Level2 extends Level{
    
    private String[] playerDialog = {"There must be a way..", "to escape"};
    private double dialogTimer = 2f;
    private int dialogIndex = 0;
    
    public Level2() {
                
        this.stateName = "level2";
        loadLevel("Res/Levels/Level2.png", "level3");
        
        camera = new Camera("player");
        
        level.setLightBlock(Light.FULL);
                
    }
    
    @Override
    public void init(Container c) {
        
        c.getRenderer().setAmbientColor(0xff393939);
        c.getGUI().setShowing(true);
        c.getGUI().showTitleText("Ruins");
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        dialogTimer -= dt;
        
        if(dialogIndex < playerDialog.length && dialogTimer <= 0) {
            c.getGUI().showInfoText(playerDialog[dialogIndex++]);
            dialogTimer = 3f;
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
        
        for(int i = 0; i < enemies.size(); i++) {
            
            enemies.get(i).update(c, this, dt);
            if(enemies.get(i).isDead()) {
                
                enemies.remove(i);
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
        
        for(Enemy enemy: enemies) {
            
            enemy.render(c, r);
            
        }
        
    }
    
    @Override
    public void exit() {
        
        
    }
    
}