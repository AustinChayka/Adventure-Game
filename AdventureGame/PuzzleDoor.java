import java.awt.event.*;

public class PuzzleDoor extends GameObject {
    
    private String doorName;
    private ImageTile image = new ImageTile("Res/Textures/PuzzleDoor.png", 15, 48);
    
    private int tileX = 0, tileY = 0;
    
    private boolean locked = true;
    private boolean open = false;
    private boolean selected = false;
    
    private PuzzleLock pl;
    
    public PuzzleDoor(int tX, int tY, Level l, String name) {
        
        this.x = tX * Level.TS;
        this.y = tY * Level.TS;
        
        this.doorName = name;
        this.tag = "puzzleDoor." + name;
        
        if(!open) {
            l.setCollision(tX, tY, true);
            l.setCollision(tX, tY - 1, true);
            l.setCollision(tX, tY - 2, true);
        }
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        image.setLightBlock(Light.FULL);
        
        pl = new PuzzleLock(this);
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        if(open) tileX = 1;
        else tileX = 0;
        
        if(this.distanceTo(l.getObject("player")) <= 32) {
            
            selected = true;
            
            tileX += dt * 7;
            
            if(locked && c.getInput().isKeyDown(KeyEvent.VK_E)) {
            
                Container.getGame().changeState(pl, c);
            
            }
           
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
    
    public void setLocked(boolean b) {
        
        locked = b;
        
    }
    
}