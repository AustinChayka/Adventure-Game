import java.awt.event.*;

public class Level3 extends Level {
    
    private String[] playerDialog = {"I wonder how deep", "these caverns go...",
        "Maybe there were people", "here before me"};
    private double dialogTimer = 5f;
    private int dialogIndex = 0;
    
    public Level3() {
        
        this.stateName = "level3";
        loadLevel("Res/Levels/Level3.png", "level4");
        
        camera = new Camera("player");
        
        level.setLightBlock(Light.FULL);
        
        objects.add(new ProjectileBlock(4, 134, 1, this));
        objects.add(new ProjectileBlock(4, 133, 1, this));
        objects.add(new ProjectileBlock(72, 116, -1, this));
        objects.add(new Chest(10 * TS, 105 * TS, 
            new Item[] {new Key(10 * TS, 105 * TS, "Shop Door")}));
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getRenderer().setAmbientColor(0xff393939);
        c.getGUI().setShowing(true);
        c.getGUI().showTitleText("Depths");
        
    }
    
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