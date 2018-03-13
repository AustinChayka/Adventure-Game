import java.awt.image.*;
import javax.imageio.*;

public class Image {
    
    private int width, height;
    private int pixels[];
    
    private int lightBlock = Light.NONE;
    
    public Image(int width, int height) {
        
        this.width = width;
        this.height = height;
        
            pixels = new int[width * height];
        
        for(int i: pixels) {
            
            i = 0x00ffffff;
            
        }
        
    }
    
    public Image(String path) {
        
        BufferedImage image = null;
        
        try {
            
            image = ImageIO.read(Image.class.getResourceAsStream(path));
                                  
        } catch(Exception e) {
        
            System.out.println(e);
        
        }
        
        width = image.getWidth();
        height = image.getHeight();
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
        
        image.flush();
        
    }
    
    public void setPixel(int x, int y, int value) {
        
        if(x < 0 || x >= width || y < 0 || y >= height || value >> 24 == 0 ||
            value == 0xffff00ff) {
            return;
        }
        
        pixels[x + y * width] = value;
        
    }
    
    public void add(Image image, int offX, int offY) {
        
        for(int y = 0; y < image.getWidth(); y++) {
            
            for(int x = 0; x < image.getHeight(); x++) {
                
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
            
            }
            
        }
        
    }
    
    public void addTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        
        for(int y = 0; y < image.getTileHeight(); y++) {
            
            for(int x = 0; x < image.getTileWidth(); x++) {
                
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileWidth())
                + (y + tileY * image.getTileHeight()) * image.getWidth()]);
                            
            }
            
        }
        
    }
    
    public int[] getPixels() {
        
        return pixels;
        
    }
    
    public int getWidth() {
        
        return width;
        
    }
    
    public int getHeight() {
        
        return height;
        
    }
    
    public int getLightBlock() {
        
        return lightBlock;
        
    }
    
    public void setLightBlock(int b) {
        
        lightBlock = b;
        
    }
    
}