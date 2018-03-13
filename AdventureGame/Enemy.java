public abstract class Enemy extends GameObject {
    
    protected int health;
    protected ImageTile image;
    protected int level;
    
    public void dealDamage(int d) {
        
        health -= d;
        
    }
        
}