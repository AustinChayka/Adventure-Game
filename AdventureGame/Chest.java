import java.awt.event.*;

public class Chest extends GameObject {
    
    private Item[] contents;
    
    private static int chestNum = 0;
    
    private ImageTile image = new ImageTile("Res/Textures/Chest.png", 16, 16);
    
    private boolean selected = false;
    private boolean opened = false;
    
    private int tileX = 0, tileY = 0;
    
    public Chest(float x, float y) {
        
        this.x = x;
        this.y = y;
        
        this.tag = "chest_" + chestNum;
        chestNum++;
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        contents = new Item[(int)(Math.random() * 3) + 1];
        
        for(int i = 0; i < contents.length; i++) {
            
            contents[i] = Item.generateLoot(x, y, Player.level + 10, Player.level, 3);
            
        }
        
    }
    
    public Chest(float x, float y, Item[] items) {
        
        this.x = x;
        this.y = y;
        
        this.tag = "chest_" + chestNum;
        chestNum++;
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        contents = items;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
                
        if(this.collidesWith(l.getPlayer()) && !opened) {
            
            selected = true;
            
            if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                
                opened = true;
                tileX = 1;
                
                for(Item i: contents) {
                    
                    l.addObject(i);
                    
                }
                
            }
            
        } else {
            
            selected = false;
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y, tileX, tileY);
        if(selected) r.drawText("[e]", (int)x, (int)y - 10, 0xffffffff);
        
    }
        
}