import java.util.*;

public abstract class Menu extends GameState {
    
    protected ArrayList<Button> buttons = new ArrayList<Button>();
    
    
    public Button getButton(String name) {
        
        for(Button b : buttons) {
            
            if(b.getName().equals(name)) {
                
                return b;
                
            }
            
        }
        
        return null;
        
    }
    
}