import java.awt.event.*;

public class Door extends GameObject {
    
    private ImageTile image = new ImageTile("Res/Textures/Door.png", 48, 48);
    private float tileX;
    
    private boolean selected = false;
    
    private String destination;
        
    public Door(String destination, int x, int y) {
        
        this.tag = "door." + destination; 
        
        this.x = x;
        this.y = y;
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        this.destination = destination;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        if(this.collidesWith(l.getObject("player"))) {
            
            selected = true;
            
            if(tileX == 0) {
                
                tileX = 1;
                
            }
            
            tileX += dt * 7;
            
            if(tileX >= 10) {
                
                tileX = 1;
                
            }   
            
            if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                
                c.getGame().changeState(destination, c);
                c.getGame().removeState(l.getStateName());
                
                switch(l.getStateName()) {
                    
                    case "level1":
                        c.getGame().addState(new Level3());
                        break;
                        
                    case "level2":
                        c.getGame().addState(new Level4());
                        break;
                        
                    case "level3":
                        c.getGame().addState(new Level5());
                        break;
                        
                    case "level4":
                        c.getGame().addState(new Level6());
                        break;
                    
                }
                
            }
            
        } else {
            
            selected = false;
            
            tileX = 0;
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y, (int)tileX, 0);
        if(selected) r.drawText("[e]", (int)x, (int)y - 10, 0xffffffff);
        
    }
    
}