import java.util.*;

public class Torch extends GameObject {
    
    private int x, y;
    private Light light;
    
    private ImageTile image;
    
    private float imgFrame = (float)(Math.random() * 2.5);
    
    private boolean lit = true;
    
    public Torch(int x, int y, int tagNum) {
        
        this.x = x;
        this.y = y;
        
        light = new Light(75, 0xffffe899);
        light.setX(x + Level1.TS / 2);
        light.setY(y + Level1.TS / 2);
        
        image = new ImageTile("Res/Textures/Torch.png", 16, 22);
        
        this.tag = "torch" + tagNum;
        
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        imgFrame += dt * 2.5;
        
        if(imgFrame >= 3) { imgFrame = 0; } 
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, x, y - 6, (int)imgFrame, 0);
        
        if(lit) { r.drawLight(light); }
        
        //r.drawText(tag, x, y, 0xffffffff);
                
    }
    
    public void setLit(boolean l) {
        
        lit = l;
        
    }
    
}