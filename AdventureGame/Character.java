import java.awt.event.*;

public abstract class Character extends GameObject {
    
    protected String name;
    protected String[] dialog;
    private int dialogIndex = 0;
    protected int currentDialogIndex = 0;
    protected float dialogTimer = 0;
    
    protected boolean selected = false;
    protected boolean activated = false;
    
    protected ImageTile image;
    protected double tileX = 0, tileY = 0;
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        tileX += dt * 2;
        
        if(this.collidesWith(l.getPlayer())) {
            
            selected = true;
            
            if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                
                activated = true;
                
            } else {
                
                activated = false;
                
            }
            
        } else {
            
            selected = false;
            
        }
        
        if(this.distanceTo(l.getPlayer()) <= 100 && dialogTimer <= 0) {
                dialogTimer = 2f;
                currentDialogIndex = dialogIndex;
                dialogIndex++;
        }
        
        if(dialogTimer > 0) dialogTimer -= dt;
        
    }
    
}