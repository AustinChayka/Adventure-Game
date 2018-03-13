public class Light {
    
    public static final int NONE = 0;
    public static final int FULL = 1;
    
    private int radius, diameter, color;
    private int[] lightMap;
    
    private int x = 0, y = 0;
    
    public Light(int radius, int color) {
        
        this.radius = radius;
        diameter = 2 * radius;
        this.color = color;
        lightMap = new int[diameter * diameter];
        
        for(int y = 0; y < diameter; y++) {
            
            for(int x = 0; x < diameter; x++) {
                
                double distance = Math.sqrt((x - radius) * (x - radius) + 
                    (y - radius) * (y - radius));
                    
                if(distance < radius) {
                    
                    double power =  1 - (int)(distance / radius * 10) / 10.0;
                    
                    lightMap[x + y * diameter] = (int)(((color >> 16) & 
                        0xff) * power) << 16| (int)(((color >> 8) & 0xff) 
                        * power) << 8 | (int)((color & 0xff) * power);
                    
                } else {
                    
                    lightMap[x + y * diameter] = 0;
                    
                }
                
            }
            
        }
        
    }
    
    public int getLightValue(int x, int y) {
        
        if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
            return 0;
        }
        return lightMap[x + y * diameter];
        
    }
    
    public int getRadius() {
        
        return radius;
        
    }
    
    public int getDiameter() {
        
        return diameter;
        
    }
    
    public int getColor() {
        
        return color;
        
    }
    
    public int[] getLightMap() {
        
        return lightMap;
        
    }
    
    public void setRadius(int r) {
        
        radius = r;
        diameter = 2 * r;
        
        for(int y = 0; y < diameter; y++) {
            
            for(int x = 0; x < diameter; x++) {
                
                double distance = Math.sqrt((x - radius) * (x - radius) + 
                    (y - radius) * (y - radius));
                    
                if(distance < radius) {
                    
                    double power =  1 - (distance / radius);
                    
                    lightMap[x + y * diameter] = (int)(((color >> 16) & 
                        0xff) * power) << 16| (int)(((color >> 8) & 0xff) 
                        * power) << 8 | (int)((color & 0xff) * power);
                    
                } else {
                    
                    lightMap[x + y * diameter] = 0;
                    
                }
                
            }
            
        }
        
    }
    
    public void setColor(int c) {
        
        color = c;
        
    }
    
    public int getX() {
        
        return x;
        
    }
    
    public int getY() {
        
        return y;
        
    }
    
    public void setX(int x) {
        
        this.x = x;
        
    }
    
    public void setY(int y) {
        
        this.y = y;
        
    }
    
}