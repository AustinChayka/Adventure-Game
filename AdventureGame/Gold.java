import java.awt.event.*;

public class Gold extends Item {
    
    private int value;
    
    public Gold(float x, float y) {
        
        value = (int)(Math.random() * (Player.level / 2) + 3) + Player.level;
        this.name = "gold";
        this.tag = "item.gold";
        
        this.x = x;
        this.y = y;
        
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        
        this.rarity = 0;
        this.init();
        
    }
    
    @Override
    public void activate(Level l) {
        
        l.getPlayer().addGold(value);
        
    }
    
    @Override
    public void displayInfo(Renderer r) {
        
        r.drawText("" + value + " gold", (int)x, (int)y - 10, this.rarColor);
        
    }
    
    @Override
    public void displayStats(Renderer r) {
        
        
    }
    
}