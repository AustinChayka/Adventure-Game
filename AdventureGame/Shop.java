import java.awt.event.*;

public class Shop extends Level {

    private Item[] items = new Item[7];

    public Shop() {

        this.stateName = "shop";
        loadLevel("Res/Levels/Shop.png", null);
        camera = new Camera("player");
        
        level.setLightBlock(Light.FULL);

        objects.add(new ShopKeeper(27 * Level.TS, 8 * Level.TS - 4));
        objects.add(new LockDoor(39, 8, this, "Shop Door"));

    }

    @Override
    public void init(Container c) {
        
        if(!c.getGame().getPreviousState().getStateName().equals("menu"))
            for(int i = 0; i < items.length; i++) items[i] = null;

        c.getRenderer().setAmbientColor(0xff393939);
        c.getGUI().setShowing(true);

        c.getGame().getPlayer().setX(3 * Level.TS);
        c.getGame().getPlayer().setX(8 * Level.TS);
        c.getGUI().showTitleText(this.stateName);

    }

    @Override
    public void update(Container c, StateManager sm, float dt) {        

        if(c.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {

            sm.changeState("menu", c);

        }

        for(int i = 0; i < items.length; i++) {
            if(items[i] == null) {
                items[i] = Item.generateLoot((i * 2 + 7) * 20, 7 * Level.TS, 
                    Player.level, Player.level + 3, 3, 0);
                items[i].setPos((i * 2 + 7) * 20, 7 * Level.TS);
            }
        }

        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                if(items[i].collidesWith(getPlayer())) {
                    items[i].setSelected(true);
                    if(c.getInput().isKeyDown(KeyEvent.VK_E)) {
                        if(c.getGame().getPlayer().getGold() >= (int)(items[i].getValue() * 1.3)) {
                            c.getGame().getPlayer().addToInventory(items[i]);
                            c.getGame().getPlayer().addGold(-(int)(items[i].getValue() * 1.3));
                            items[i] = null;
                        } else {
                            c.getGUI().showInfoText("Not enough gold");
                        }
                    }
                } else { 
                    items[i].setSelected(false);
                }
            }
        }

        for(int i = 0; i < objects.size(); i++) {

            objects.get(i).update(c, this, dt);
            if(objects.get(i).isDead()) {

                objects.remove(i);
                i--;

            }

        }

        camera.update(c, this, dt);

    }

    @Override
    public void render(Container c, Renderer r) {

        camera.render(r);

        r.drawImage(background, (int)camera.getX() / 2, (int)camera.getY() / 2);
        r.drawImage(backing2, (int)camera.getX() / 4, (int)camera.getY() / 4);
        r.drawImage(backing1, 0, 0);
        r.drawImage(subLevel, 0, 0);
        r.drawImage(level, 0, 0);

        for(GameObject object: objects) {

            object.render(c, r);

        }

        for(Item item : items) {
            if(item != null) {
                item.drawIcon(r, 0);
                if(item.isSelected()) r.drawText((int)(item.getValue() * 1.3) + 
                    " gold", (int)item.getX(), (int)item.getY() - 16,
                    0xffffffff);
            }
        }

    }

    @Override
    public void exit() {

        //for(int i = 0; i < items.length; i++) items[i] = null;

    }

}