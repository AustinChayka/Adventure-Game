import java.util.*;

public class StateManager {
    
    public static Container container;
    
    private GameState previousState;
    
    private GameState currentState;
    
    private Level previousLevel;
        
    public ArrayList<GameState> states = new ArrayList<GameState>();
            
    public StateManager() {
        
        states.add(new MainMenu());
        states.add(new GameMenu());
        states.add(new Level1());
        states.add(new Level2());
        states.add(new GameOver());
        states.add(new Shop());
        states.add(new Controls());
        states.add(new WinScreen());
        
    }
    
    public void init(Container c) {
        
        changeState("mainMenu", c);
        
    }
    
    public void update(Container c, float dt) {
        
        currentState.update(c, this, dt);
        
    }
    
    public void render(Container c, Renderer r) {
        
        currentState.render(c, r);
        
    }
    
    public static void main(String[]args) {
        
        StateManager sm = new StateManager();
        Container c = new Container(sm, 480, 320, 2f, "Adventure Game");
        sm.container = c;
        c.start();
        
    }
    
    public void changeState(String stateName, Container c) {
        
        if(currentState != null) currentState.exit();
        previousState = currentState;
        if(previousState instanceof Level && !(previousState instanceof Shop)) previousLevel = (Level)previousState;
        currentState = getState(stateName);
        currentState.init(c);
        
    }
    
    public void changeState(GameState newState, Container c) {
        
        previousState = currentState;
        currentState = newState;
        currentState.init(c);
        
    }
    
    public GameState getState(String stateName) {
        
        for(GameState state: states) {
            
            if(state.getStateName().equals(stateName)) {
                
                return state;
                
            }
            
        }
        
        return null;
        
    }
    
    public void removeState(String stateName) {
        
        for(int i = 0; i < states.size(); i++) {
            
            if(states.get(i).getStateName().equals(stateName)) {
                
                states.remove(i);
                
            }
            
        }
        
    }
    
    public void addState(GameState gs) {
        
        states.add(gs);
        
    }
    
    public GameState getPreviousState() {
        
        return previousState;
        
    }
    
    public GameState getCurrentState() {
        
        return currentState;
        
    }
    
    public Player getPlayer() {
        
        Level l = (Level)getState("shop");
        Player p = (Player)l.getObject("player");
        return p;
        
    }
    
    public Level getPreviousLevel() {
        
        return previousLevel;
        
    }
    
}