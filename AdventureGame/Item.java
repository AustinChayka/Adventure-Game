import java.awt.event.*;

public abstract class Item extends GameObject {

    protected String name = "";
    protected int rarity;
    protected static Image dropImage = new Image("Res/Textures/ItemIcons/Bag.png");
    protected boolean selected;

    private Light light;
    protected int rarColor;

    private float fallDistance = -1.7f;
    protected float xVol = 0;

    protected ImageTile icon;
    protected int imgX = 0, imgY = 0;
    protected float mX, mY;
    protected String statText = "";

    private int tileX, tileY;
    private float offX, offY;
    
    private boolean ground = false;
    
    public static int common = 100;
    public static int uncommon = 50;
    public static int rare = 10;
    public static int legendary = 5;
    public static int epic = 1;
    
    protected int level;
    
    protected int value;
    
    public static Item generateLoot(float x, float y, int seed, int level, int levelBoost) {
        
        int r = 0;
        int total = common + uncommon + rare + legendary + epic;
        int num = (int)(Math.random() * (total + 1)) + seed;
        if(num >= (total -= epic)) {
            r = 4;
        } else if(num >= (total -= legendary)) {
            r = 3;
        } else if(num >= (total -= rare)) {
            r = 2;
        } else if(num >= (total -= uncommon)) {
            r = 1;
        }
        
        int l = level + (int)(Math.random() * (levelBoost + 1));
            
        switch((int)(Math.random() * 5)) {

            case 0:
            return new Gold(x, y);

            case 1:
            return new Belt(x, y, r, l);

            case 2:
            return new Amulet(x, y, r, l);
            
            case 3:
            return new Helm(x, y, r, l);
            
            case 4:
            return new Trinket(x, y);

        }

        return new Gold(x, y);

    }
    
    public static Item generateLoot(float x, float y, int seed, int level, int levelBoost, int n) {
        
        int r = 0;
        int total = common + uncommon + rare + legendary + epic;
        int num = (int)(Math.random() * (total + 1)) + seed;
        if(num >= (total -= epic)) {
            r = 4;
        } else if(num >= (total -= legendary)) {
            r = 3;
        } else if(num >= (total -= rare)) {
            r = 2;
        } else if(num >= (total -= uncommon)) {
            r = 1;
        }
        
        int l = level + (int)(Math.random() * (levelBoost + 1));
            
        switch((int)(Math.random() * 4)) {

            case 1:
            return new Belt(x, y, r, l);

            case 2:
            return new Amulet(x, y, r, l);
            
            case 3:
            return new Helm(x, y, r, l);

        }

        return new Trinket(x, y);

    }

    protected void init() {

        this.xVol = (float)Math.random() * .5f - .25f;

        this.tileX = (int)(x / Level.TS);
        this.tileY = (int)(y / Level.TS);
        this.offX = 0;
        this.offY = 0;

        switch (this.rarity) {

            case 0:
                rarColor = 0xffffffff;
                this.imgX = 0;
                if(this instanceof Equipment) this.name += "common ";
                break;
            case 1:
                rarColor = 0xffa6ffa0;
                this.imgX = 1;
                if(this instanceof Equipment) this.name += "uncommon ";
                break;
            case 2:
                rarColor = 0xff82ddff;
                this.imgX = 2;
                if(this instanceof Equipment) this.name += "rare ";
                break;
            case 3:
                rarColor = 0xffcb00ff;
                this.imgX = 3;
                if(this instanceof Equipment) this.name += "legendary ";
                break;
            case 4:
                rarColor = 0xffffd12d;
                this.imgX = 4;
                if(this instanceof Equipment) this.name += "epic ";
                break;

        }

        light = new Light(20, rarColor);
        
        value = 5 * (level + 1) * (rarity + 1);

    }

    @Override
    public void update(Container c, Level l, float dt) {
     
        if(xVol != 0) xVol += Math.signum(xVol) * .6 * dt;

        if(l.getCollision(tileX + (int)Math.signum((int)offX), tileY) ||
            l.getCollision(tileX, tileY +  (int)Math.signum((int)offY))) {

            if(offX < 0) {

                offX += xVol;
                if(offX > 0) {

                    offX = 0;
                    xVol = 0;

                }

            } else {

                offX = 0;
                xVol = 0;

            }

        } else {

            offX += xVol;

        }
        
        fallDistance += dt * 4;
        offY += fallDistance;
        
        if(fallDistance < 0) {
            if(l.getCollision(tileX + (int)Math.signum((int)offX), tileY - 1) 
                || l.getCollision(tileX, tileY - 1) && offY < 0) {

                fallDistance = 0;
                offY = 0;

            }
        } else if(fallDistance > 0) {
            if(l.getCollision(tileX + (int)Math.signum((int)offX), tileY + 1)
                || l.getCollision(tileX, tileY + 1) && offY >= 0) {

                fallDistance = 0;
                offY = 0;
                xVol = 0;
                ground = true;

            }
        }
        
        if(offY > Level.TS / 2) {

            tileY++;
            offY -= Level.TS;

        } else if(offY < -Level.TS / 2) {

            tileY--;
            offY += Level.TS;

        }
        if(offX > Level.TS / 2) {

            tileX++;
            offX -= Level.TS;

        } else if(offX < -Level.TS / 2) {

            tileX--;
            offX += Level.TS;

        }

        x = tileX * Level1.TS + offX;
        y = tileY * Level1.TS + offY;

        if(this.collidesWith(l.getPlayer())) {

            selected = true;

            if(c.getInput().isKeyUp(KeyEvent.VK_E) && ground) {
                if(l.getPlayer().getInventorySpace() > 0 || this instanceof Gold) {
                    this.activate(l);
                    this.dead = true;
                }
            }

        } else {

            selected = false;

        }

    }

    @Override
    public void render(Container c, Renderer r) {

        r.drawImage(dropImage, (int)x, (int)y);
        r.drawLight(light, (int)x + dropImage.getWidth() / 2, 
            (int)y + dropImage.getHeight() / 2);

        if(selected) {
            this.displayInfo(r);
        }

    }

    public abstract void activate(Level l);

    public abstract void displayInfo(Renderer r);

    public abstract void displayStats(Renderer r);

    public void drawIcon(Renderer r) {

        r.drawImageTile(icon, (int)mX + r.getCamX(), (int)mY + r.getCamY(), imgX, imgY);

    }
    
    public void drawIcon(Renderer r, int n) {

        r.drawImageTile(icon, (int)mX, (int)mY, imgX, imgY);

    }

    public void setPos(float x, float y) {

        mX = x;
        mY = y;

    }

    public String getName() {

        return this.name;

    }

    public void setSelected(boolean s) {

        selected = s;

    }

    public boolean isSelected() {

        return selected;

    }

    public boolean isHighlighted(Container c) {

        if(mX < c.getInput().getMouseX() && mX + icon.getTileWidth() > c.getInput().getMouseX()
        && mY < c.getInput().getMouseY() && mY + icon.getTileHeight() > c.getInput().getMouseY()) {

            return true;

        }

        return false;

    }
    
    public int getValue() {
        
        return value;
        
    }

}