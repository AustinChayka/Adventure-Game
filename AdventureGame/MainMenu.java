public class MainMenu extends Menu {
     
    public MainMenu() {
        
        this.stateName = "mainMenu";
        buttons.add(new Button(150, 40, "Res/Textures/NewGame.png", 200, 75, "newGame"));
        buttons.add(new Button(150, 210, "Res/Textures/Controls.png", 200, 75, "controls"));
        
    }
    
    @Override
    public void init(Container c) {
        
        
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        if(getButton("newGame").isUp()) {
            sm.changeState("level1", c);
            sm.removeState(this.stateName);
            sm.removeState("controls");
        }
        else if(getButton("controls").isUp()) sm.changeState("controls", c);
        
        for(Button b : buttons) {
            
            b.update(c, dt);
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.fillRect(r.getCamX(), r.getCamY(), c.getWidth(), c.getHeight(), 0xff445c63, 0);
        
        for(Button b : buttons) {
            
            b.render(c, r);
            
        }
        
        r.drawText("Main Menu", r.getCamX() + 100, r.getCamY(), 0xffc7d31d);
        
    }
    
    @Override
    public void exit() {
        
        
        
    }
        
}