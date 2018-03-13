public class Ghost extends Enemy {
    
    private float targetX, targetY;
    
    private int tileX = 0, tileY = 0;
    
    public Ghost(float x, float y) {
        
        this.tag = "ghost";
        
        image = new ImageTile("Res/Textures/Ghost.png", 32, 32);
        
        health = 3;
        this.x = x;
        this.y = y;
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        targetX = x;
        targetY = y;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {     
        
        if(this.collidesWith(l.getPlayer())) {
            
            l.getPlayer().dealDamage(1);
            
        }
        
        if(health <= 0) {
            l.addObject(new Gold(x, y));
            l.getPlayer().addExp(1);
            dead = true;
        }
        
        if(distanceTo(l.getPlayer()) <= 100 || this.health < 3) {
            
            targetX = l.getPlayer().getX() - l.getPlayer().getWidth() / 2;
            targetY = l.getPlayer().getY() - l.getPlayer().getHeight() / 2;
            
            if(targetX > x) {
               tileY = 1; 
            } else {
              tileY = 0; 
            }
            
        }
        
        x += (x - targetX) / 3 * dt * -2;
        y += (y - targetY) / 3 * dt * -2;
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)(y - height), tileX, tileY);
        
    }
    
}