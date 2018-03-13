import java.awt.event.*;

public class Campfire extends GameObject {
    
    private Light light = new Light(70, 0xff9b21);
    
    private boolean selected = false;
    
    ImageTile image = new ImageTile("Res/Textures/campFire.png", 16, 16);
    private float tileX = 0;
    
    public Campfire(float x, float y) {
        
        this.x = x;
        this.y = y;
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        this.tag = "campfire";
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        tileX += dt * 3;
        if(tileX >= 5) tileX = 0;
        
        if(this.collidesWith(l.getPlayer())) {
            selected = true;
            if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                l.getPlayer().heal(l.getPlayer().getMaxHealth());
            }
        } else {
            selected = false;
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y, (int)tileX, 0);
        r.drawLight(light, (int)(x + width / 2), (int)(y + height / 2));
        if(selected) r.drawText("[e]", (int)x, (int)y - 20, 0xffffffff);
        
    }
    
}