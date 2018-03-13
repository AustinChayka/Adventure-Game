public class Bullet extends GameObject {
    
    private float speed = 400;
    private int dir = 0;
    private float yVol = 0;
    private static float gravity = 0;
    
    private int damage = 1;
    
    private Light light = new Light(10, 0xff0000ff);
    
    private int bounces = 0;
    private static int maxBounces = 0;
    
    public Bullet(float x, float y, int dir) {
        
        this.x = x;
        this.y = y;
        
        this.dir = dir;
        this.width = 1;
        this.height = 1;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
         
        if(l.getCollision((int)((x + 3 * dir) / 16), (int)(y / 16))) {
            
            if(bounces < maxBounces) {
                dir *= -1;
                bounces++;
            } else {
                this.dead = true;
            }
            
        } else if(l.getCollision((int)(x / 16), (int)((y + 3 * Math.signum(yVol)) / 16))) {
            
            if(bounces < maxBounces) {
                yVol *= -1;
                bounces++;
            } else {
                this.dead = true;
            }
            
        }
        
        for(Enemy e: l.getEnemies()) {
            
            if(this.collidesWith(e)) {
                
                e.dealDamage(damage);
                this.dead = true;
                
            }
            
        }
        
        if(x < 0 || x >= l.getLevelWidth() * Level.TS || y < 0 || y >= l.getLevelHeight() * Level.TS) {
            
            this.dead = true;
            
        }
        
        x += dir * speed * dt;
        y += yVol * dt;
        yVol += gravity;
        if(speed - gravity > 0) speed -= gravity / 2;
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.fillRect((int)x, (int)y, 1, 1, 0xff0000ff, 0);
        r.drawLight(light, (int)x, (int)y);
        
    }
    
    public static void changeGravity(float f) {
        
        gravity += f;
        
    }
    
    public static void changeBounces(int n) {
        
        maxBounces += n;
        
    }
    
}