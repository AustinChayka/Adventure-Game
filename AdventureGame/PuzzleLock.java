import java.awt.event.*;

public class PuzzleLock extends GameState {
    
    private int[] puzzleNums;
    
    private int selected = 0;
    private float slider = 0;
    private int sliderDir = 1;
    private float sliderSpeed = 150f;
    private float timer;
    
    PuzzleDoor door;
    
    private int centeringOffset;
    
    public PuzzleLock(PuzzleDoor pd) {
        
        door = pd;
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getGUI().showTitleText("");
        c.getRenderer().setAmbientColor(0xfffffff);
        c.getGUI().setShowing(false);
        
        puzzleNums = new int[(int)(Math.random() * 4) + 6];
        for(int i = 0; i < puzzleNums.length; i++) puzzleNums[i] = (int)(Math.random() * 10) + 1;
        
        timer = 1.7f * puzzleNums.length;
        selected = 0;
        
        centeringOffset = (480 - (puzzleNums.length * 50)) / 2;
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
        
        if(c.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            
            sm.changeState(sm.getPreviousState(), c);
            
        }
        
        if(c.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
            
            if(((int)slider <= (10 - puzzleNums[selected]) * 10 + 10 && 
                (int)slider >= (10 - puzzleNums[selected]) * 10) ||
                ((int)slider + 3 <= (10 - puzzleNums[selected]) * 10 + 10 && 
                (int)slider + 3 >= (10 - puzzleNums[selected]) * 10)) {
                
                selected++;
                if(selected == puzzleNums.length) {
                    sm.changeState(sm.getPreviousState(), c);
                    door.setLocked(false);
                }
                
            } else {
                
                timer -= 1.3f;
                
            }
            
        }
        
        slider += sliderSpeed * dt * sliderDir;
        if(slider <= 0) sliderDir = 1;
        if(slider >= 100 - 3) sliderDir = -1;
        
        timer -= dt;
        if(timer <= 0) {
            sm.changeState(sm.getPreviousState(), c);
            c.getGUI().showInfoText("Lock failed, try again");
        }
                
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.fillRect(r.getCamX(), r.getCamY(), c.getWidth(), c.getHeight(), 0xff6a8289, 0);
        
        for(int i = 0; i < puzzleNums.length; i++) {
            
            r.fillRect(i * 50 + centeringOffset + r.getCamX(), 100 + r.getCamY(), 30, 100, 0xff4c4168, 0);
            r.fillRect(i * 50 + centeringOffset + r.getCamX(), 100 + r.getCamY() + (10 - puzzleNums[i]) * 10, 30, 10, 0xffffff00, 0);
            r.drawText("" + puzzleNums[i], i * 50 + centeringOffset + r.getCamX(), 200 + r.getCamY(), 0xffffffff);
            
        }
        
        r.fillRect(selected * 50 + centeringOffset + r.getCamX(), 100 + r.getCamY() + (int)slider, 30, 3, 0xffffffff, 0);
        
        r.fillRect(r.getCamX(), r.getCamY() + 280, (int)((timer / (puzzleNums.length * 1.7)) * 480), 20, 0xffffffff, 0);
        r.drawText("" + (int)(timer * 100) / 100f, r.getCamX(), r.getCamY() + 255, 0xffffffff);
        
    }
    
    @Override
    public void exit() {
        
        
        
    }
        
}