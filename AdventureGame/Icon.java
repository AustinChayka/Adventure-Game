public class Icon {
    
    private String name;
    private Image image;
    private float x, y;
    private int width, height;
    
    private boolean highlighted;
    
    public Icon(String name, String path, float x, float y) {
        
        this.name = name;
        this.image = new Image(path);
        this.x = x;
        this.y = y;
        
        this.width = image.getWidth();
        this.height = image.getHeight();
        
    }
    
    public void update(Container c, float dt) {
        
        if(c.getInput().getMouseX() > x && c.getInput().getMouseX() < x + width && c.getInput().getMouseY() > y &&
            c.getInput().getMouseY() < y + height) {
            
            highlighted = true;
            
        } else {
            
            highlighted = false;
            
        }
        
    }
    
    public void render(Container c, Renderer r) {
        
        r.drawImage(image, (int)x + r.getCamX(), (int)y + r.getCamY());
        if(highlighted) r.drawText(name, (int)x + r.getCamX(), (int)y + r.getCamY(), 0xffffffff);
        
    }
    
}