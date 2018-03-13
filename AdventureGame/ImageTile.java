public class ImageTile extends Image {
    
    private int tileW, tileH;
    
    public ImageTile(String path, int tileW, int tileH) {
        
        super(path);
        this.tileW = tileW;
        this.tileH = tileH;
        
    }
    
    public int getTileWidth() {
        
        return tileW;
        
    }
    
    public int getTileHeight() {
        
        return tileH;
        
    }
    
    public void setTileWidth(int w) {
        
        tileW = w;
        
    }
    
    public void setTileHeight(int h) {
        
        tileH = h;
        
    }
    
}