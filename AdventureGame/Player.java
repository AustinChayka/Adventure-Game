import java.awt.event.*;
import java.util.*;

public class Player extends GameObject {

    private int tileX, tileY;
    private float offX, offY;

    private static float speed = 150;
    private static float fallSpeed = 20;
    private static float jump = -5;
    private static int jumps = 0;
    private static int maxJumps = 2;
    private static boolean wallJumps = true, leftJump = false, rightJump = false;
    private int xVol = 0;

    private int dir = 1;

    private boolean ground = false;

    private float fallDistance = 0;

    private static ImageTile image = new ImageTile("Res/Textures/Player.png", 32, 48);
    private float imgX = 0, imgY = 0;

    private static int health = 5;
    private static int maxHealth = 5;
    private static int exp = 0;
    public static int level = 0;
    private static int strength = 1;
    private static float attackSpeed = 1;
    private static int gold = 0;
    private static float reloadSpeed = 1;
    private static float reload = 0;

    private float recover = 0;

    private static int levelPoints = 0;

    private Light light = new Light(150, 0xffffffd5);

    private static Equipment[] equipment = new Equipment[8];
    private static Item[] inventory = new Item[28];
    private static Trinket[] trinkets = new Trinket[5];

    private float fireDelay = 0;

    public Player(int x, int y) {

        this.tag = "player";
        this.tileX = x;
        this.tileY = y;
        this.offX = y;
        this.offY = x;
        this.x = x * Level.TS;
        this.y = y * Level.TS;
        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        if(!this.hasItem("key.Strange"))
            this.addToInventory(new Key(0, 0, "Strange"));

    }

    @Override
    public void update(Container c, Level l, float dt) {

        while(exp >= ((level + 1) * 2)) {

            exp -= (level + 1) * 2;
            level += 1;

            if(level <= 20) {

                levelPoints += 1;

            }

        }

        if(health <= 0) {

            c.getGame().changeState("gameOver", c);

        } else {

            if(c.getInput().isKey(KeyEvent.VK_A)) {

                dir = -1;

            } else if(c.getInput().isKey(KeyEvent.VK_D)) {

                dir = 1;

            }

            if(reload > 0) {

                reload -= dt;

            }

            if(c.getInput().isKey(KeyEvent.VK_SPACE) && reload <= 0) {

                if(c.getInput().isKey(KeyEvent.VK_CONTROL)) {
                    if(dir == -1) {
                        l.addObject(new Bullet(x, y - 10, dir));
                    }else {
                        l.addObject(new Bullet(x + 32, y - 10, dir));
                    }
                } else {
                    if(dir == -1) {
                        l.addObject(new Bullet(x, y - 18, dir));
                    }else {
                        l.addObject(new Bullet(x + 32, y - 18, dir));
                    }

                }

                reload = reloadSpeed;

            }

            if(c.getInput().isKey(KeyEvent.VK_D) && 
            !c.getInput().isKey(KeyEvent.VK_CONTROL)) {

                dir = 1;

                imgY = 0;
                imgX += dt * 7.5f;
                if(imgX >= 4) { imgX = 0; }

                if(l.getCollision(tileX + 2, tileY) || l.getCollision(tileX + 2, tileY - 2) ||
                l.getCollision(tileX + 2, tileY - 1) ||
                l.getCollision(tileX + 2, tileY +  (int)Math.signum((int)offY))) {

                    if(offX < 0) {

                        offX += dt * speed;
                        if(offX > 0) {

                            offX = 0;

                        }

                    } else {

                        offX = 0;

                    }

                } else {

                    offX += dt * speed;

                }

            } else if(c.getInput().isKey(KeyEvent.VK_A) &&
            !c.getInput().isKey(KeyEvent.VK_CONTROL)) {

                dir = -1;

                imgY = 1;
                imgX += dt * 7.5f;
                if(imgX >= 4) { imgX = 0; }

                if(l.getCollision(tileX - 1, tileY) || l.getCollision(tileX - 1, tileY - 2) || 
                l.getCollision(tileX - 1, tileY - 1) ||
                l.getCollision(tileX - 1, tileY + (int)Math.signum((int)offY))) {

                    if(offX > 0) {

                        offX -= dt * speed;
                        if(offX < 0) {

                            offX = 0;

                        }

                    } else {

                        offX = 0;

                    }

                } else {

                    offX -= dt * speed;

                }

            }

            if(c.getInput().isKeyDown(KeyEvent.VK_W) &&
            !c.getInput().isKey(KeyEvent.VK_CONTROL)) {

                if(((l.getCollision(tileX + 2, tileY) || 
                        l.getCollision(tileX + 2, tileY + (int)Math.signum((int)offY))) &&
                    c.getInput().isKey(KeyEvent.VK_D)) &&
                (wallJumps && offX == 0) && !rightJump) {

                    xVol -= 5;
                    fallDistance = jump;
                    ground = false;
                    rightJump = true;
                    leftJump = false;

                } else if(((l.getCollision(tileX - 1, tileY) || 
                        l.getCollision(tileX - 1, tileY + (int)Math.signum((int)offY))) &&
                    c.getInput().isKey(KeyEvent.VK_A))  &&
                (wallJumps && offX == 0) && !leftJump) {

                    xVol += 5;
                    fallDistance = jump;
                    ground = false;
                    leftJump = true;
                    rightJump = false;

                } else if(ground || jumps > 0) {

                    fallDistance = jump;
                    ground = false;
                    jumps--;
                    rightJump = false;
                    leftJump = false;

                }

            }

            fallDistance += dt * fallSpeed;

            offY += fallDistance;

            if(fallDistance < 0) {
                if((l.getCollision(tileX + (int)Math.signum((int)offX), tileY - 3) 
                    || l.getCollision(tileX, tileY - 3)|| l.getCollision(tileX + 1, tileY - 3))
                || l.getCollision(tileX + 1 + (int)Math.signum((int)offX), tileY - 3)
                && offY < 0) {

                    fallDistance = 0;
                    offY = 0;

                }
            } else if(fallDistance > 0) {
                if((l.getCollision(tileX + (int)Math.signum((int)offX), tileY + 1)
                    || l.getCollision(tileX + 1 + (int)Math.signum((int)offX), tileY + 1)
                    || l.getCollision(tileX, tileY + 1) || l.getCollision(tileX + 1, tileY + 1)) 
                && offY >= 0) {

                    if(fallDistance > 10) {

                        health -= (int)(fallDistance - 10) / 2;

                    }

                    fallDistance = 0;
                    offY = 0;
                    ground = true;
                    jumps = maxJumps;

                    if(!c.getInput().isKey(KeyEvent.VK_A) && !c.getInput().isKey(KeyEvent.VK_D)) {
                        if(dir == 1) {imgX = 0; imgY = 0;} else {imgX = 0; imgY = 1;}
                    }

                } else {

                    ground = false;
                    if(jumps == maxJumps) {

                        jumps--;

                    }

                }
            }

            if(offY > Level1.TS / 2) {

                tileY++;
                offY -= Level1.TS;

            } else if(offY < -Level1.TS / 2) {

                tileY--;
                offY += Level1.TS;

            }
            if(offX > Level1.TS / 2) {

                tileX++;
                offX -= Level1.TS;

            } else if(offX < -Level1.TS / 2) {

                tileX--;
                offX += Level1.TS;

            }

            x = tileX * Level1.TS + offX;
            y = tileY * Level1.TS + offY;

            if(c.getInput().isKeyUp(KeyEvent.VK_D)) {

                imgX = 0; imgY = 0;

            } else if(c.getInput().isKeyUp(KeyEvent.VK_A)) {

                imgX = 0; imgY = 1;

            }

            recover -= dt;

        }

        if(c.getInput().isKey(KeyEvent.VK_CONTROL)) {
            if(dir == 1) { imgX = 0; imgY = 3; }
            else { imgX = 1; imgY = 3; }
        }

        if(!ground) { 
            if(dir == 1) {imgX = 0; imgY = 2;} else {imgX = 1; imgY = 2;}
        }

    }

    @Override
    public void render(Container c, Renderer r) {

        r.drawImageTile(image, (int)x, (int)(y - height * 2/3), (int)imgX, (int)imgY);
        r.drawLight(light, (int)x + width / 2,
            (int)y - height / 2);

        //r.drawRect((int)x, (int)y - height * 2/3, width, height, 0xffffffff);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addGold(int a) {
        gold += a;
    }

    public int getGold() {
        return gold;        
    }

    public void increaseHealth(int h) {
        maxHealth += h;
    }

    public void heal(int h) {
        if(health + h < maxHealth) {
            health += h;
        } else {
            health = maxHealth;
        }
    }

    public void addExp(int e) {
        exp += e;
    }

    public void addXVol(float f) {
        xVol += f;
    }

    public void dealDamage(int d) {
        if(recover <= 0) {
            health -= d;
            recover = 1;
        }

    }

    public void addJumps(int n) {

        maxJumps += n;

    }

    public void addToInventory(Item item) {

        if(item == null) return;

        for(int i = 0; i < inventory.length; i++) {

            if(inventory[i] == null) {

                inventory[i] = item;
                int x = i, y = 0;
                while(x > 3) { y++; x -= 4; }
                inventory[i].setPos(x * 24 + 10, y * 24 + 140);
                return;

            }

        }

    }

    public void removeFromInventory(Item item) {

        if(item == null) return;

        for(int i = 0; i < inventory.length; i++) {

            if(inventory[i] == item) {

                inventory[i] = null;
                return;

            }

        }

    }

    public Equipment getEquipmentType(Equipment.Type type) {

        for(Equipment eq : equipment) {

            if(eq != null && eq.getType() == type) {

                return eq;

            }

        }

        return null;

    }

    public void equipItem(Equipment eq, Level l) {

        if(eq == null) return;

        addToInventory(getEquipmentType(eq.getType()));
        for(int i = 0; i < equipment.length; i++) {

            if(equipment[i] == null || equipment[i].getType() == eq.getType()) {

                if(equipment[i] != null) equipment[i].applyItem(l, -1);
                equipment[i] = eq;
                removeFromInventory(equipment[i]);
                eq.applyItem(l, 1);
                switch(equipment[i].getType()) {

                    case Belt:
                    equipment[i].setPos(6, 70);
                    break;

                    case Amulet:
                    equipment[i].setPos(6, 37);
                    break;

                    case Helm:
                    equipment[i].setPos(29, 5);
                    break;

                }
                return;

            }

        }

    }

    public void equipTrinket(Trinket t, Level l) {

        if(t == null) return;

        for(int i = 0; i < trinkets.length; i++) {

            if(trinkets[i] == null) {

                if(trinkets[i] != null) trinkets[i].applyItem(l, -1);
                trinkets[i] = t;
                removeFromInventory(trinkets[i]);
                t.applyItem(l, 1);
                t.setPos(125, 140 + 36 * i);
                return;

            }

        }

    }

    public void unequipTrinket(Trinket t, Level l) {

        if(t == null) return;
        t.applyItem(l, -1);
        addToInventory(t);
        trinkets[getTrinketindex(t)] = null;

    }

    public int getTrinketindex(Trinket t) {

        for(int i = 0; i < trinkets.length; i++) {

            if(trinkets[i] == t) return i;

        }

        return -1;

    }

    public Item[] getInventory() {

        return inventory;

    }

    public Equipment[] getEquipment() {

        return equipment;

    }

    public float getDPS() {

        return strength * 1 / reloadSpeed;

    }

    public float getSpeed() {

        return speed;

    }

    public int getMaxHealth() {

        return maxHealth;

    }

    public float getDamage() {

        return strength * 1;

    }

    public float getReloadSpeed() {

        return reloadSpeed;

    }

    public int getHealth() {

        return health;

    }

    public int getLevel() {

        return level;

    }

    public int getExp() {

        return exp;

    }

    public float getReloadCount() {

        return reload;

    }

    public float getMobility() {

        return -jump * maxJumps;

    }

    public void addJumpHeight(float n) {

        jump -= n;

    }

    public void addSpeed(float s) {

        speed += s;

    }

    public int getInventorySpace() {

        int i = 28;

        for(Item item : inventory) if(item != null) i--;

        return i;

    }

    public Item getItem(String name) {

        for(Item i : inventory) {

            if(i != null && i.getName().equals(name)) return i;

        }

        return null;

    }

    public boolean hasItem(String name) {

        for(Item i : inventory) {

            if(i != null && i.getName().equals(name)) return true;

        }

        return false;

    }

    public Trinket[] getTrinkets() {

        return trinkets;

    }

    public static void reset() {

        speed = 150;
        fallSpeed = 20;
        jump = -5;
        maxJumps = 2;
        wallJumps = true;

        health = 5;
        maxHealth = 5;
        exp = 0;
        level = 0;
        strength = 1;
        attackSpeed = 1;
        gold = 0;
        reloadSpeed = 1;
        reload = 0;

        levelPoints = 0;

        for(int i = 0; i < equipment.length; i++) equipment[i] = null;
        for(int i = 0; i < inventory.length; i++) inventory[i] = null;
        for(int i = 0; i < trinkets.length; i++) trinkets[i] = null;

    }

    public void setTilePos(int x, int y) {

        tileX = x;
        tileY = y;

    }

}