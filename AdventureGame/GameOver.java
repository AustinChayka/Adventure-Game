public class GameOver extends Menu {
    
    private Image image = new Image("Res/Textures/GameOver.png");
    
    public GameOver() {
        
        this.stateName = "gameOver";
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getGUI().showTitleText("");
        c.getRenderer().setAmbientColor(0xffffffff);
        c.getGUI().setShowing(false);
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImage(image, r.getCamX(), r.getCamY());
                
    }
    
    @Override
    public void exit() {
        
        
        
    }
    
}