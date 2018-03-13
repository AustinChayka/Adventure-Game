import java.awt.image.*;
import java.util.*;

public class Renderer {
    
    private int pWidth, pHeight;
    private int[] pixels;
    
    private int[] lightMap;
    private int[] lightBlock;
    
    private int ambientColor = 0x00ffffff;
            
    static Font font;
    
    private int camX = 0, camY = 0;
    
    public Renderer(Container c) {
        
        pWidth = c.getWidth();
        pHeight = c.getHeight();
        
        pixels = ((DataBufferInt)c.getFrame().getImage().getRaster().getDataBuffer()).getData();
        font = new Font("Res/Fonts/MainFont.png");
        
        lightMap = new int[pixels.length];
        lightBlock = new int[pixels.length];
        
    }
    
    public void process() {
        
        for(int i = 0; i < pixels.length; i++) {
            
            float red = ((lightMap[i] >> 16) & 0xff) / 255f;
            float green = ((lightMap[i] >> 8) & 0xff) / 255f;
            float blue = (lightMap[i] & 0xff) / 255f;
            
            pixels[i] = (int)(((pixels[i] >> 16) & 0xff) * red) << 16| 
                (int)(((pixels[i] >> 8) & 0xff) * green) << 8 |
                (int)((pixels[i] & 0xff) * blue);
            
        }
        
    }
    
    public void clear() {
        
        for(int i = 0; i < pixels.length; i++) {
            
            pixels[i] = 0xff000000;
            lightMap[i] = ambientColor;
            lightBlock[i] = 0;
            
        }
        
    }
    
    public void setPixel(int x, int y, int value) {
        
        int alpha = (value >> 24) & 0xff;
                
        if((x < 0 || y < 0 || x >= pWidth || y >= pHeight) || value >> 24 == 0 ||
            value == 0xffff00ff) {
            
            return;
            
        }
        
        int index = x + y * pWidth;
        
        if(alpha == 255) {
            
            pixels[index] = value;
            
        } else {
            
           int color = 0; 
           int pColor = pixels[index];
           int newRed = ((pColor >> 16) & 0xff) - (int)((((pColor >> 16) & 0xff)
                - ((value >> 16) & 0xff)) * (alpha / 255f));
           int newGreen = ((pColor >> 8) & 0xff) - (int)((((pColor >> 8) & 0xff)
                - ((value >> 8) & 0xff)) * (alpha / 255f));
           int newBlue = (pColor & 0xff) - (int)(((pColor & 0xff)
                - (value & 0xff)) * (alpha / 255f));
            
           pixels[index] = (newRed << 16 | 
                newGreen << 8 | newBlue);
           
        }
        
    }
    
    public void setLightMap(int x, int y, int value) {
        
        int baseColor = lightMap[x + y * pWidth];
        
        if(x < 0 || y < 0 || x >= pWidth || y >= pHeight) {
            
            return;
            
        }
                
        int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
        int maxGreen= Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
        int maxBlue = Math.max(baseColor & 0xff, value & 0xff);
                
        lightMap[x + y * pWidth] = (maxRed << 16 | 
                maxGreen << 8 | maxBlue);
        
    }
    
    public void setLightBlock(int x, int y, int value) {
        
        if((x < 0 || y < 0 || x >= pWidth || y >= pHeight)) {
            
            return;
            
        }
        
        lightBlock[x + y * pWidth] = value;
        
    }
    
    public void drawImageTile(ImageTile image, int offX, int offY,
        int tileX, int tileY) {
        
        
        offX -= camX;
        offY -= camY;
        
        if(offX >= pWidth) {
            
            return;
            
        }else if(offX <= -image.getTileWidth()) {
            
            return;
            
        }
        if(offY >= pHeight) {
            
            return;
            
        }else if(offY <= -image.getTileHeight()) {
            
            return;
            
        }
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileWidth();
        int newHeight = image.getTileHeight();
        
        if(offX < 0) {
            
            newX -= offX;
            
        }else if(newWidth + offX >= pWidth) {
            
            newWidth -= newWidth + offX - pWidth;
            
        }
        if(newHeight + offY >= pHeight) {
            
            newHeight -= newHeight + offY - pHeight;
            
        } else if(offY < 0) {
            
            newY -= offY;
            
        }
        
        for(int y = newY; y < newHeight; y++) {
            
            for(int x = newX; x < newWidth; x++) {
                
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileWidth())
                + (y + tileY * image.getTileHeight()) * image.getWidth()]);
                setLightBlock(x + offX, y + offY, image.getLightBlock());
                
            }
            
        }
        
    }
    
    public void drawText(String text, int offX, int offY, int color) {
        
        offX -= camX;
        offY -= camY;
        
        int unicode;
        int offset = 0;
        
        for(int i = 0; i < text.length(); i++) {
            
            unicode = text.codePointAt(i) - 32;
            
            for(int y = 0; y < font.getFontImage().getHeight(); y++) {
                
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    
                    if(font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
                        
                        setPixel(x + offX + offset, y + offY, color);
                        
                    }
                    
                }
                
            }
            
            offset += font.getWidths()[unicode];
            
        }
        
    }
    
    public static int getTextLength(String text) {
        
        int unicode;
        int offset = 0;
        
        for(int i = 0; i < text.length(); i++) {
            
            unicode = text.codePointAt(i) - 32;
            
            offset += font.getWidths()[unicode];
            
        }
        
        return offset;
        
    }
    
    public void drawImage(Image image, int offX, int offY) {
        
        offX -= camX;
        offY -= camY;
        
        if(offX >= pWidth) {
            
            return;
            
        } else if(offX <= -image.getWidth()) {
            
            return;
            
        }
        if(offY >= pHeight) {
            
            return;
            
        }else if(offY <= -image.getHeight()) {
            
            return;
            
        }
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();
        
        if(offX < 0) {
            
            newX -= offX;
            
        }else if(newWidth + offX > pWidth) {
            
            newWidth -= newWidth + offX - pWidth;
            
        }
        if(newHeight + offY > pHeight) {
            
            newHeight -= newHeight + offY - pHeight;
            
        } else if(offY < 0) {
            
            newY -= offY;
            
        }
        
        for(int y = newY; y < newHeight; y++) {
            
            for(int x = newX; x < newWidth; x++) {
                
                if(image.getPixels()[x + y * image.getWidth()] >> 24 != 0) {
                    setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
                    setLightBlock(x + offX, y + offY, image.getLightBlock());
                }
                
            }
            
        }
        
    }
    
    public void drawRect(int offX, int offY, int width, int height, int color) {
        
        offX -= camX;
        offY -= camY;
        
        for(int y = 0; y <= height; y++) {
            
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
            
        }
        for(int x = 0; x < width; x++) {
            
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
            
        }
        
    }
    
    public void fillRect(int offX, int offY, int width, int height, int color, int block) {
                    
        offX -= camX;
        offY -= camY;
        
        if(offX < -width) {
            return;
        }
        if(offX < -width) {
            return;
        }
        if(offX >= pWidth) {
            return;
        }
        if(offY >= pHeight) {
            return;
        }
        
        for(int y = 0; y <= height; y++) {
            
            for(int x = 0; x <= width; x++) {
                
                setPixel(x + offX, y + offY, color);
                if(block != 0) setLightBlock(x + offX, y + offY, block);
                
            }
            
        }
        
    }
    
    public int getCamX() {
        
        return camX;
        
    }
    
    public int getCamY() {
        
        return camY;
        
    }
    
    public void setCamX(int x) {
        
        camX = x;
        
    }
    
    public void setCamY(int y) {
        
        camY = y;
        
    }
    
    public void drawLight(Light light, int offX, int offY) {
        
        offX -= camX;
        offY -= camY;
        
        for(int i = 0; i <= light.getDiameter(); i++) {
            
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offX, offY);
            
        }
        
    }
    
    public void drawLight(Light light) {
        
        int offX = light.getX();
        int offY = light.getY();
        
        offX -= camX;
        offY -= camY;
        
        for(int i = 0; i <= light.getDiameter(); i++) {
            
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offX, offY);
            
        }
        
    }
    
    private void drawLightLine(Light light, int x0, int y0, int x1,int y1,
        int offX, int offY) {
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        
        int err = dx - dy;
        int err2;
        
        while(true) {
            
            int screenX = x0 - light.getRadius() + offX;
            int screenY = y0 - light.getRadius() + offY;
            
            if(screenX < 0 || screenX >= pWidth || screenY < 0 ||
                screenY >= pHeight) {
                 
                    return;
                    
            }
            
            int lightColor = light.getLightValue(x0, y0);
            if(lightColor == 0) {
                return;
            }
            
            if(lightBlock[screenX + screenY * pWidth] == Light.FULL) {
                return;
            }
            
            setLightMap(screenX, screenY, lightColor);
            
            if(x0 == x1 && y0 == y1) {
                
                break;
                
            }
            
            err2 = 2 * err;
            
            if(err2 > -1 * dy) {
                
                err -= dy;
                x0 += sx;
                
            }
            
            if(err2 < dx) {
                
                err += dx;
                y0 += sy;
                
            }
            
        }
        
    }
    
    public void setAmbientColor(int c) {
        
        ambientColor = c;
        
    }
    
}