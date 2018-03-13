public abstract class GameState {
    
    protected String stateName;
    
    public abstract void init(Container c);
    public abstract void update(Container c, StateManager sm, float dt);
    public abstract void render(Container c, Renderer r);
    public abstract void exit();
    
    public String getStateName() {
        
        return stateName;
        
    }
    
}