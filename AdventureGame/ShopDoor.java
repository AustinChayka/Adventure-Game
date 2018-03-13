import java.awt.event.*;

public class ShopDoor extends GameObject {
    
    private Image image = new Image("Res/Textures/ShopDoor.png");
    
    private boolean selected = false;
    
    public ShopDoor(float x, float y) {
        
        this.tag = "shopDoor";
        
        this.x = x;
        this.y = y;
        
        this.width = image.getWidth();
        this.height = image.getHeight();
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        if(l.getPlayer().collidesWith(this)) {
            
            selected = true;
            
            if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                
                if(c.getGame().getCurrentState() == c.getGame().getState("shop")) c.getGame().changeState(c.getGame().getPreviousLevel(), c);
                else c.getGame().changeState("shop", c);
                
            }
            
        } else {
            
            selected = false;
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImage(image, (int)x, (int)(y - height * 2/3f));
        if(selected) r.drawText("[e]", (int)x, (int)y - 10, 0xffffffff);
        
    }
    
}