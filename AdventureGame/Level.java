import java.util.*;

public abstract class Level extends GameState {
    
    protected static final int TS = 16;
    
    protected boolean[] collision;
    protected int levelWidth, levelHeight;
    protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
    protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    protected Camera camera;
    
    protected Image level;
    protected Image subLevel;
    protected Image backing1;
    protected Image backing2;
    protected Image background;
                        
    public void loadLevel(String path, String nextLevel) {
        
        Image levelImage = new Image(path);
        
        levelWidth = levelImage.getWidth() * TS;
        levelHeight = levelImage.getHeight() * TS;
        
        level = new Image(levelWidth, levelHeight);
        subLevel = new Image(levelWidth, levelHeight);
        backing1 = new Image(levelWidth, levelHeight);
        backing2 = new Image(levelWidth, levelHeight);
        background = new Image(levelWidth, levelHeight);
        
        ImageTile pot = new ImageTile("Res/Textures/Pot.png", 16, 16);
        ImageTile block = new ImageTile("Res/Textures/Block.png", 16, 16);
        ImageTile stone = new ImageTile("Res/Textures/Stone.png", 16, 16);
                
        levelWidth = levelImage.getWidth();
        levelHeight = levelImage.getHeight();
        collision = new boolean[levelWidth * levelHeight];
                
        int tagNum = 0;
        
        int pX = 0, pY = 0;
                
        for(int y = 0; y < levelImage.getHeight(); y++) {
            
            for(int x = 0; x < levelImage.getWidth(); x++) {
                
                background.addTile(stone, x * TS, y * TS, 3, 0);
                
                if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff000000) {
                    
                    collision[x + y * levelImage.getWidth()] = true;
                    level.addTile(block, x * TS, y * TS, 0, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(block, (x - 1) * TS, y * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(block, (x + 1) * TS, y * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(block, x * TS, (y + 1) * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(block, x * TS, (y - 1) * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(block, (x - 1) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(block, (x + 1) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(block, x * TS, (y + 1) * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(block, x * TS, (y - 1) * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(block, (x - 2) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(block, (x + 2) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(block, x * TS, (y + 2) * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(block, x * TS, (y - 2) * TS, 2, 0);
                    
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff808080) {
                    
                    collision[x + y * levelImage.getWidth()] = true;
                    level.addTile(stone, x * TS, y * TS, 0, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(stone, (x - 1) * TS, y * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(stone, (x + 1) * TS, y * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(stone, x * TS, (y + 1) * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing1.addTile(stone, x * TS, (y - 1) * TS, 1, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(stone, (x - 1) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(stone, (x + 1) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(stone, x * TS, (y + 1) * TS, 2, 0);
                    if((int)(Math.random() * 2) == 1) backing2.addTile(stone, x * TS, (y - 1) * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(stone, (x - 2) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(stone, (x + 2) * TS, y * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(stone, x * TS, (y + 2) * TS, 2, 0);
                    if((int)(Math.random() * 4) == 1) backing2.addTile(stone, x * TS, (y - 2) * TS, 2, 0);
                    
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff00ff00) {
                                        
                    subLevel.addTile(pot, x * TS, y * TS, (int)(Math.random() * 3), 0);
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff6B6B6B) {
                                        
                    backing1.addTile(stone, x * TS, y * TS, 1, 0);
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffffff00) {
                                       
                    objects.add(new Torch(x * TS, y * TS, tagNum++));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffff0000) {
                                       
                    objects.add(new Door(nextLevel, (x - 1) * TS, (y - 2) * TS));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff966900) {
                                       
                    objects.add(new ShopDoor(x * TS, y * TS));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff00ffff) {
                                       
                    pX = x;
                    pY = y - 1;
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffaa0000) {
                                       
                    objects.add(new Chest(x * TS, y * TS));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffff9f00) {
                                       
                    enemies.add(new Ghost(x * TS, y * TS));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xff5b7f00) {
                                       
                    enemies.add(new Goblin(x, y));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffff9719) {
                                       
                    objects.add(new Spikes(x * TS, y * TS, Spikes.Direction.Bottom));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffCB49FF) {
                                       
                    objects.add(new PuzzleDoor(x, y, this, "Door"));
                                        
                } else if(levelImage.getPixels()[x + y * levelImage.getWidth()] == 0xffEAFF05) {
                                       
                    objects.add(new Campfire(x * TS, y * TS));
                                        
                } else { 
                    
                    collision[x + y * levelImage.getWidth()] = false;
                    
                }
                
            }
            
        }
        
        objects.add(new Player(pX, pY));
            
    }
    
    public boolean getCollision(int x, int y) {
        
        try {
            
            return collision[x + y * levelWidth];
            
        } catch(Exception e) {return true;}
        
    }
    
    public void setCollision(int x, int y, boolean b) {
        
        collision[x + y * levelWidth] = b;
        
    }
    
    public void setCollision(boolean[] b) {
        
        collision = b;
        
    }
    
    public GameObject getObject(String tag) {
        
        for(int i = 0; i < objects.size(); i++) {
            
            if(objects.get(i).getTag().equals(tag)) {
                
                return objects.get(i);
                
            }
            
        }
        
        return null;
        
    }
    
    public int getLevelWidth() {
        
        return levelWidth;
        
    }
    
    public int getLevelHeight() {
        
        return levelHeight;
        
    }
    
    public void addObject(GameObject go) {
        
        objects.add(go);
        
    }
    
    public ArrayList<Enemy> getEnemies() {
        
        return enemies;
        
    }
    
    public Player getPlayer() {
        
        Player p = (Player)getObject("player");
        return p;
        
    }
    
    public void setObjects(ArrayList<GameObject> al) {
        
        objects = al;
        
    }
    
    public void setEnemies(ArrayList<Enemy> al) {
        
        enemies = al;
        
    }
    
    public ArrayList<GameObject> getObjects() {
        
        return objects;
        
    }
    
    public boolean[] getCollisions() {
        
        return collision;
        
    }
        
}