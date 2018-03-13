/**
 * 
 *  Container.java
 *      main program container, handles the thread along with the order
 *      of updating and renderering
 *      
 *  @author Austin Chayka
 *  @version Febuary 28, 2018
 * 
 */
public class Container implements Runnable {
    
    private Thread thread;
    private Frame mainFrame;
    private Renderer renderer;
    private Input input;
    private static StateManager game;
    private GUI gui;
    
    private Boolean running  = false;
    private final double UPDATE_CAP = 1.0 / 60.0;
    
    private int width = 800, height = 800;
    private float scale = 1f;
    private String title;
        
    /**
     *  Constructor method
     *      sets up the container with its needed information
     *      
     *  @param sm the state manager to run
     *  @param w frame width
     *  @param h frame height
     *  @param sc the frame scale
     *  @param t the frame title
     */
    public Container(StateManager sm, int w, int h, float sc, String t) {
        
        game = sm;
        width = w;
        height = h;
        scale = sc;
        title = t;
        
    }
    
    /**
     *  start method
     *      starts the thread and the other needed objects
     */
    public void start() {
        
        mainFrame = new Frame(this);
        renderer = new Renderer(this);
        input = new Input(this);
        gui = new GUI();
        
        thread = new Thread(this);
        thread.run();
        
    }
    
    /**
     *  run method 
     *      runs the thread and handles update timing
     */
    public void run() {
        
        running = true;
        
        boolean render = false;
        double firstTime = 0, lastTime = System.nanoTime() / 1000000000.0, 
            passedTime = 0, unprocessedTime = 0;
            
        double frameTime = 0;
        int fps = 0, frames = 0;
        
        game.init(this);
                
        while(running) {
            
            //render = false;
            
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            
            unprocessedTime += passedTime;
            frameTime += passedTime;
            
            while(unprocessedTime >= UPDATE_CAP) {
                
                unprocessedTime -= UPDATE_CAP;
                render = true;
                                
                game.update(this, (float)UPDATE_CAP);
                gui.update(this, (float)UPDATE_CAP);
                input.update();
                
                if(frameTime >= 1.0) {
                    
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    
                }
                
            }
            
            if(render) {
                
                renderer.clear();
                
                game.render(this, renderer);
                renderer.process();
                
                gui.render(this, renderer);
                
                renderer.drawText("" + fps, renderer.getCamX(), 
                    renderer.getCamY(), 0xff00ffff);
                
                mainFrame.update();
                frames++;
                                
            } else {
                
                try {
                    
                    Thread.sleep(1);
                    
                } catch(Exception e) {
                    
                    e.printStackTrace();
                    
                }
                
            }
            
        }
        
    }
    
    /**
     *  getWidth method
     *      gets the width
     *      
     *  @return the width
     */
    public int getWidth() {
        
        return width;
        
    }
    
    /**
     *  getHeight method
     *      gets the height
     *      
     *  @return the height
     */
    public int getHeight() {
        
        return height;
        
    }
    
    /**
     *  getScale method
     *      gets the scale
     *      
     *  @return the scale
     */
    public float getScale() {
        
        return scale;
        
    }
    
    /**
     *  getTitle method
     *      gets the title
     *      
     *  @return the title
     */
    public String getTitle() {
        
        return title;
        
    }
    
    /**
     *  getFrame method
     *      gets the main game frame
     *      
     *  @return the game frame
     */
    public Frame getFrame() {
        
        return mainFrame;
        
    }
    
    /**
     *  getInput method
     *      gets the control input object
     *      
     *  @return the input object
     */
    public Input getInput() {
        
        return input;
        
    }
    
    /**
     *  getRenderer method
     *      gets the renderer object
     *      
     *  @return the renderer
     */
    public Renderer getRenderer() {
        
        return renderer;
        
    }
    
    /**
     *  getGUI method
     *      gets the GUI object
     *      
     *  @return the GUI
     */
    public GUI getGUI() {
        
        return gui;
        
    }
    
    /**
     *  getGame method
     *      gets the state manager for the game
     *      
     *  @return the game's manager
     */
    public static StateManager getGame() {
        
        return game;
        
    }
    
}