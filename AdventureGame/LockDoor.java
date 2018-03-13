import java.awt.event.*;

public class LockDoor extends GameObject {
    
    private String doorName;
    private ImageTile image = new ImageTile("Res/Textures/LockDoor.png", 15, 48);
    
    private int tileX = 0, tileY = 0;
    
    private boolean locked = true;
    private boolean open = false;
    private boolean selected = false;
    
    public LockDoor(int tX, int tY, Level l, String name) {
        
        this.x = tX * Level.TS;
        this.y = tY * Level.TS;
        
        this.doorName = name;
        this.tag = "lockedDoor." + name;
        
        if(!open) {
            l.setCollision(tX, tY, true);
            l.setCollision(tX, tY - 1, true);
            l.setCollision(tX, tY - 2, true);
        }
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        image.setLightBlock(Light.FULL);
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        if(open) tileX = 1;
        else tileX = 0;
        
        if(this.distanceTo(l.getObject("player")) <= 32) {
            
            selected = true;
            
            tileX += dt * 7;
            
            if(locked && c.getInput().isKeyDown(KeyEvent.VK_E)) {
                if (l.getPlayer().hasItem("key." + this.doorName)) {
                    locked = false;
                    l.getPlayer().removeFromInventory(l.getPlayer().getItem("key." + this.doorName));
                    c.getGUI().showInfoText(this.doorName + " key used");
                } else c.getGUI().showInfoText("Requires " + this.doorName + " key");}
           
            if(!locked && c.getInput().isKeyDown(KeyEvent.VK_E)) {
                
                if(!open) {
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS), false);
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS) - 1, false);
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS) - 2, false);
                    open = true;
                } else {
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS), true);
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS) - 1, true);
                    l.setCollision((int)(x / Level.TS), (int)(y / Level.TS) - 2, true);
                    open = false;
                }
                
            }
            
        } else {
            
            selected = false;
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y - (int)(this.height * 2 / 3), tileX, tileY);
        
        if(selected) r.drawText("[e]", (int)x - 16, (int)y - 55, 0xffffffff);
        
    }
    
}