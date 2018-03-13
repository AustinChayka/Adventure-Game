import java.awt.event.*;

/**
 * 
 *  Input.java
 *      input controller object, listens for and handles input from the
 *      keyboard and mouse
 *      
 *  @author Austin Chayka
 *  @version Febuary 28, 2018
 * 
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    
    Container c;
    
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];
    
    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
    
    private int mouseX, mouseY;
    
    private int scroll;
    
    /**
     *  Constructor method
     *      sets up the object with a container to get information from
     *      
     *  @param c a Container object
     */
    public Input(Container c) {
        
        this.c = c;
        
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        
        c.getFrame().getCanvas().addKeyListener(this);
        c.getFrame().getCanvas().addMouseListener(this);
        c.getFrame().getCanvas().addMouseMotionListener(this);
        c.getFrame().getCanvas().addMouseWheelListener(this);
        
    }
    
    /**
     *  update method
     *      updates which buttons are currently being held down 
     */
    public void update() {
        
        for(int i = 0; i < NUM_KEYS; i++) {
            
            keysLast[i] = keys[i];
            
        }
        
        for(int i = 0; i < NUM_BUTTONS; i++) {
            
            buttonsLast[i] = buttons[i];
            
        }
        
    }
    
    /**
     *  isKey method
     *      checks if the indicated key is being held down
     *      
     *  @param keyCode the keycode for the key
     *  
     *  @returns true or false if the key is being held
     */
    public boolean isKey(int keyCode) {
        
        return keys[keyCode];
        
    }
    
    /**
     *  isKeyUp method
     *      checks if the key has been released
     *      
     *  @param keyCode the keycode of the key
     *  
     *  @returns true or false if the key is released
     */
    public boolean isKeyUp(int keyCode) {
        
        return !keys[keyCode] && keysLast[keyCode];
        
    }
    
    /**
     *  isKeyDown method
     *      check if the key is pressed
     *      
     *  @param keyCode the keycode of the key
     *  
     *  @returns true of false if the key is pressed
     */
    public boolean isKeyDown(int keyCode) {
        
        return keys[keyCode] && !keysLast[keyCode];
        
    }
    
    /**
     *  isButton method
     *      checks if the indicated mouse button is being held
     *      
     *  @param buttonCode the buttoncode of the button
     *  
     *  @returns true or false if the button is being held
     */
    public boolean isButton(int buttonCode) {
        
        return buttons[buttonCode];
        
    }
    
    /**
     *  isButtonUp method
     *      checks if the button has been released
     *      
     *  @param buttonCode the buttoncode of the button
     *  
     *  @returns true or false if the button has been released
     */
    public boolean isButtonUp(int buttonCode) {
        
        return !buttons[buttonCode] && buttonsLast[buttonCode];
        
    }
    
    /**
     *  isButtonDown method
     *      checks if the button has been pressed
     *      
     *  @param buttonCode the buttoncode for the button
     *  
     *  @returns true or flase if the button has been released
     */
    public boolean isButtonDown(int buttonCode) {
        
        return buttons[buttonCode] && !buttonsLast[buttonCode];
        
    }
    
    /**
     *  keyTyped method
     *      handles events on key typed
     *      
     *  @param k the key event
     */
    @Override
    public void keyTyped(KeyEvent k) { }
    
    /**
     *  keyPressed method
     *      handles events on key pressed
     *      
     *  @param k the key event
     */
    @Override
    public void keyPressed(KeyEvent k) {
        
        keys[k.getKeyCode()] = true;
        
    }
    
    /**
     *  keyReleased method
     *      handles events on key released
     *      
     *  @param k the key event
     */
    @Override
    public void keyReleased(KeyEvent k) {
        
        keys[k.getKeyCode()] = false;
        
    }
        
    /**
     *  mouseExcited method
     *      handles events on mouse excited
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseExited(MouseEvent m) { }
    
    /**
     *  mouseEntered method
     *      handles events on mouse entered
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent m) { }
    
    /**
     *  mouseReleased method
     *      handles events on mousebutton released
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent m) {
        
        buttons[m.getButton()] = false;
        
    }
    
    /**
     *  mousePressed method
     *      handles events on mousebutton pressed
     *      
     *  @param m the mouse event
     */
    @Override
    public void mousePressed(MouseEvent m) {
        
        buttons[m.getButton()] = true;
        
    }
    
    /**
     *  mouseClicked method
     *      handles events on mousebutton clicked
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent m) { }
    
    /**
     *  mouseMoved method
     *      handles events on the mouse moved
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent m) {
        
        mouseX = (int)(m.getX() / c.getScale());
        mouseY = (int)(m.getY() / c.getScale());
        
    }
    
    /**
     *  mouseDragged method
     *      handels events on the mouse dragged
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent m) {
        
        mouseX = (int)(m.getX() / c.getScale());
        mouseY = (int)(m.getY() / c.getScale());
        
    }
    
    /**
     *  mouseWheelMoved method
     *      handles events on the mouse wheel moved
     *      
     *  @param m the mouse event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent m) {
        
        scroll = m.getWheelRotation();
        
    }
    
    /**
     *  getMouseX method
     *      gets the x-position of the mouse
     *      
     *  @returns the mouse's x-position
     */
    public int getMouseX() {
        
        return mouseX;
        
    }
    
    /**
     *  getMouseY method
     *      gets the y-position of the mouse
     *      
     *  @returns the mouse's y-position
     */
    public int getMouseY() {
        
        return mouseY;
        
    }
    
    /**
     *  getScroll method
     *      gets the scroll direction
     *      
     *  @returns the scroll direction as an int
     */
    public int getScroll() {
        
        return scroll;
        
    }
    
}