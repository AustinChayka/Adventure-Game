public class Key extends Item {
    
    public Key(float x, float y, String name) {
        
        this.name = "key." + name;
        this.tag = "item." + this.name;
        this.icon = new ImageTile("Res/Textures/ItemIcons/Key.png", 24, 24);
        
        this.x = x;
        this.y = y;
        
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        
        this.rarity = 0;
        this.init();
        
    }
    
    @Override
    public void displayStats(Renderer r) {
        
        r.drawText(name.substring(4) + " key", (int)mX + r.getCamX() + width, (int)mY + r.getCamY(), rarColor);
        
    }
    
    @Override
    public void displayInfo(Renderer r) {
        
        r.drawText("Key", (int)x, (int)y - 10, rarColor);
        
    }
    
    @Override
    public void activate(Level l) {
        
        l.getPlayer().addToInventory(this);
        
    }
    
}