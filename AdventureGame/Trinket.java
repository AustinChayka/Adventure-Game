public class Trinket extends Item {
    
    private Type type;
    
    public enum Type {
        
        RubberCement, FatBullets, JarOfAir, NinjaGear, LuckyPenny, ThrowPillow
        
    }
    
    public Trinket(float x, float y, Type t) {
        
        this.icon = new ImageTile("Res/Textures/ItemIcons/Trinkets.png", 24, 24);
        
        this.x = x;
        this.y = y;
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        
        this.type = t;
        switch(type) {
            
            case RubberCement:
                this.name = "Rubber Cement";
                this.statText = "extra bouncy";
                this.rarity = 4;
                this.init();
                this.imgX = 0;
                break;
            case FatBullets:
                this.name = "Fat Bullets";
                this.statText = "extra heavy bullets";
                this.rarity = 4;
                this.init();
                this.imgX = 1;
                break;
            case JarOfAir:
                break;
            case NinjaGear:
                break;
            case LuckyPenny:
                break;
            case ThrowPillow:
                break;
                
        }
        
        this.value = 20;
        
    }
    
    public Trinket(float x, float y) {
        
        this.icon = new ImageTile("Res/Textures/ItemIcons/Trinkets.png", 24, 24);
        
        this.x = x;
        this.y = y;
        this.width = dropImage.getWidth();
        this.height = dropImage.getHeight();
        
        switch((int)(Math.random() * 2)) {
            
            case 0:
                this.type = Type.RubberCement;
                this.name = "Rubber Cement";
                this.statText = "extra bouncy";
                this.rarity = 4;
                this.init();
                this.imgX = 0;
                break;
            case 1:
                this.type = Type.FatBullets;
                this.name = "Fat Bullets";
                this.statText = "extra heavy bullets";
                this.rarity = 4;
                this.init();
                this.imgX = 1;
                break;
            case 2:
                this.type = Type.JarOfAir;
                break;
            case 3:
                this.type = Type.NinjaGear;
                break;
            case 4:
                this.type = Type.LuckyPenny;
                break;
            case 5:
                this.type = Type.ThrowPillow;
                break;
                
        }
        
        this.value = 20;
        
    }
    
    public void applyItem(Level l, int n) {
        
        switch(this.type) {
            
            case RubberCement:
                Bullet.changeBounces(3 * n);
                break;
            case FatBullets:
                Bullet.changeGravity(3 * n);
                break;
            case JarOfAir:
                break;
            case NinjaGear:
                break;
            case LuckyPenny:
                break;
            case ThrowPillow:
                break;
                
        }
        
    }
    
    @Override
    public void displayStats(Renderer r) {
        
        r.drawText(name, (int)mX + r.getCamX() + width, (int)mY + r.getCamY(), rarColor);
        r.drawText(statText, (int)mX + r.getCamX() + width, (int)mY + r.getCamY() + 20, rarColor);
        r.drawText(value + " gold", (int)mX + r.getCamX() + width, (int)mY + r.getCamY() + 40, rarColor);
        
    }
    
    @Override
    public void displayInfo(Renderer r) {
        
        r.drawText(name, (int)x, (int)y - 10, rarColor);
        
    }
    
    @Override
    public void activate(Level l) {
        
        l.getPlayer().addToInventory(this);
        
    }
    
}