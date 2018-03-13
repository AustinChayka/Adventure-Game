public abstract class Equipment extends Item {
    
    public static enum Type {
        
        Belt, Amulet, Helm
        
    }
    
    public enum StatType {
        
        Mobility, Health, Speed
        
    }
    
    protected Equipment.Type type;
    protected Equipment.StatType statType;
        
    public abstract void applyItem(Level l, int n);
    
    @Override
    public void displayStats(Renderer r) {
        
        r.drawText(this.name, (int)mX + r.getCamX() + width, (int)mY + r.getCamY(), rarColor);
        r.drawText("level " + this.level, (int)mX + r.getCamX() + width, (int)mY + r.getCamY() + 20, rarColor);
        r.drawText(statText, (int)mX + r.getCamX() + width, (int)mY + r.getCamY() + 40, rarColor);
        r.drawText(value + " gold", (int)mX + r.getCamX() + width, (int)mY + r.getCamY() + 60, rarColor);
        
    }
    
    public Equipment.Type getType() {
        
        return this.type;
        
    }
    
}