public class ProjectileBlock extends GameObject {
    
    private int dir;
    
    private float delay = 0;
    
    private ImageTile image = new ImageTile("Res/Textures/ProjectileBlock.png", 16, 16);
    
    private int tileX = 0;
    
    public ProjectileBlock(int x, int y, int dir, Level l) {
        
        this.x = x * Level.TS;
        this.y = y * Level.TS;
        this.width = 16;
        this.height = 16;
        
        l.setCollision(x, y, true);
        
        this.dir = dir;
        
        if(dir == 1) tileX = 0;
        else tileX = 1;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        if((int)Math.signum(l.getPlayer().getX() - this.x) == dir) {
            if(delay <= 0) {
                if(dir == 1) l.addObject(new EnemyBullet(x + 16, y + 8, 1));
                else l.addObject(new EnemyBullet(x - 1, y + 8, -1));
                delay = 1.3f;
            }
        }
        
        delay -= dt;
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y, tileX, 0);
        
    }
    
}