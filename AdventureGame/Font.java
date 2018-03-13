public class Font { //Matura MT Script Capitals
    
    private Image fontImage;
    
    private int[] offsets;
    private int[] widths;
    
    public Font(String path) {
        
        fontImage = new Image(path);
        
        offsets = new int[95];
        widths = new int[95];
        
        int val = 0;
        
        for(int i = 0; i < fontImage.getWidth(); i++) {
            
            if(fontImage.getPixels()[i] == 0xff0000ff) {
                
                offsets[val] = i;
                
            } else if(fontImage.getPixels()[i] == 0xffffff00) {
                
                widths[val] = i - offsets[val];
                
                val++;
                
            }
            
        }
        
    }
    
    public Image getFontImage() {
        
        return fontImage;
        
    }
    
    public int[] getOffsets() {
        
        return offsets;
        
    }
    
    public int[] getWidths() {
        
        return widths;
        
    }
    
}