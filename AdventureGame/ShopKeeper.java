public class ShopKeeper extends Character {
    
    public ShopKeeper(float x, float y) {
        
        name = "Shop Keeper";
        tag = "character.shopKeeper";
        dialog = new String[] {"Hello Traveler", "I am shopkeeper", "please buy something", "or leave"};
        
        image = new ImageTile("Res/Textures/ShopKeeper.png", 35, 40);
        
        this.x = x;
        this.y = y;
        
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
                
    }
    
    @Override
    public void update(Container c, Level l, float dt) {
        
        super.update(c, l, dt);
        if(tileX >= 6) {
            
            tileX = 0;
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImageTile(image, (int)x, (int)y - 19, (int)tileX, (int)tileY);
        
        if(activated) {
            activated = false;
            GameMenu.setSelling(true);
            c.getGame().changeState("menu", c);
        }
        
        try {
        if(this.collidesWith(((Level)c.getGame().getCurrentState()).getPlayer()))
            r.drawText("Sell Items", (int)x, (int)y - height, 0xffffffff);
        } catch(Exception e) { }
        
        if(dialogTimer > 0 && currentDialogIndex < dialog.length) r.drawText(dialog[currentDialogIndex], (int)x - 
            Renderer.getTextLength(dialog[currentDialogIndex]) / 2, 
            (int)y - height, 0xff00ffff);
        
    }
    
}