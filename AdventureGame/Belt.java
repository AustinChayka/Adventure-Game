public class Belt extends Equipment {
            
    public Belt(float x, float y, int r, int l) {
        
        icon = new ImageTile("Res/Textures/ItemIcons/Belt.png", 24, 24);
        
        this.type = Equipment.Type.Belt;
        
        this.x = x;
        this.y = y;
                
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        this.rarity = r;
        this.init();
        
        this.name += "belt";
        
        this.level = l;
        
        switch((int)(Math.random() * 2)) {
            
            case 0:
                this.statType = StatType.Mobility;
                name += " of mobility";
                if(rarity <= 3) {
                    statText = "+" + (rarity + 1) * (level + 1) / 10f + " jump height";
                } else {
                    statText = "+" + (int)((level + 1) / 10 + 1) + "jumps";
                }
                break;
            
            case 1:
                this.statType = StatType.Speed;
                name += " of speed";
                statText = "+" + (1 + rarity) * (level + 1) + " speed";
                break;
            
        }
        
    }
    
    @Override
    public void applyItem(Level l, int n) {
        
        switch(statType) {
            
            case Mobility:
                if(rarity <= 3) {
                    Container.getGame().getPlayer().addJumpHeight((rarity + 1) * (level + 1) / 20f * n);
                } else {
                    Container.getGame().getPlayer().addJumps((int)((level + 1) / 10 + 1) * n);
                }
                break;
            
            case Speed:
                Container.getGame().getPlayer().addSpeed(3 *  (1 + rarity) * (level + 1) * n);
                break;
                
        }
       
    }
    
    @Override
    public void activate(Level l) {
        
        l.getPlayer().addToInventory(this);
        
    }
    
    @Override
    public void displayInfo(Renderer r) {
        
        r.drawText("Belt", (int)x, (int)y - 10, rarColor);
        
    }
    
}