public class Amulet extends Equipment {
    
    public Amulet(float x, float y, int r, int l) {
        
        icon = new ImageTile("Res/Textures/ItemIcons/Amulet.png", 24, 24);
        
        this.type = Equipment.Type.Amulet;
        
        this.x = x;
        this.y = y;
        
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        
        this.rarity = r;
        this.init();
        
        this.level = l;
        
        this.name += "amulet of vitality";
        this.statText = "+" + (rarity + 1) * (level + 1) + " health";
        
    }
    
    @Override
    public void applyItem(Level l, int n) {
        
        Container.getGame().getPlayer().increaseHealth((int)((rarity + 1) * (level + 1) / 2.0 * n) + 1);
        Container.getGame().getPlayer().heal((int)((rarity + 1) * (level + 1) / 2.0 * n) + 1);
       
    }
    
    @Override
    public void activate(Level l) {
        
        l.getPlayer().addToInventory(this);
        
    }
    
    @Override
    public void displayInfo(Renderer r) {
        
        r.drawText("Amulet", (int)x, (int)y - 10, rarColor);
        
    }
    
}