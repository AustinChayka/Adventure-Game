import java.awt.event.*;

public class Controls extends Menu {
    
    private Image image = new Image("Res/Textures/ControlsMenu.png");
    
    public Controls() {
        
        this.stateName = "controls";
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getGUI().showTitleText("");
        c.getRenderer().setAmbientColor(0xffffffff);
        c.getGUI().setShowing(false);
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) { 
        
        if(c.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            
            sm.changeState("mainMenu", c);
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImage(image, 0, 0);
        
    }
    
    @Override
    public void exit() {
        
        
        
    }
    
}