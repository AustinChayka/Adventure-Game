public class Camera {
    
    private float offX, offY;
    
    private GameObject target = null;
    private String targetTag;
    
    public Camera(String tag) {
        
        targetTag = tag;
        
    }
    
    public void update(Container c, Level l, float dt) {
        
        if(target == null) {
            
            target = l.getObject(targetTag);
            
        }
        if(target == null) {
            
            return;
            
        }
        
        float targetX = (target.getX() + target.getWidth() / 2) - c.getWidth() / 2;
        float targetY = (target.getY() + target.getHeight() / 2) - c.getHeight() / 2;
        
        offX -= dt * (offX - targetX) * 5;
        offY -= dt * (offY - targetY) * 5;
        
        if(offX < 0) {
            offX = 0;
        } else if(offX + c.getWidth() > l.getLevelWidth() * l.TS) {
            offX = l.getLevelWidth() * l.TS - c.getWidth();
        }
        if(offY < 0) {
            offY = 0;
        } else if(offY + c.getHeight() > l.getLevelHeight() * l.TS) {
            offY = l.getLevelHeight() * l.TS - c.getHeight();
        }
        
    }
    
    public void render(Renderer r) {
        
        r.setCamX((int)offX);
        r.setCamY((int)offY);
        
    }
    
    public float getX() {
        
        return offX;
        
    }
    
    public float getY() {
        
        return offY;
        
    }
    
    public String getTargetTag() {
        
        return targetTag;
        
    }
    
    public GameObject getTarget() {
        
        return target;
        
    }
    
    public void setX(float x) {
        
        offX = x;
        
    }
    
    public void setY(float y) {
        
        offY = y;
        
    }
    
    public void setTargetTag(String tag) {
        
        targetTag = tag;
        
    }
    
}