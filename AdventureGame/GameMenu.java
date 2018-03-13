import java.awt.event.*;
import java.util.*;

public class GameMenu extends Menu {
    
    private ImageTile playerImage = new ImageTile("Res/Textures/Player.png", 32, 48);
    private Image background = new Image("Res/Textures/MenuBackground.png");
    
    private ArrayList<Icon> icons = new ArrayList<Icon>();
    
    private static boolean selling = false;
    
    public GameMenu() {
        
        this.stateName = "menu";
        
        icons.add(new Icon("Gold", "Res/Textures/Icons/Gold.png", 120, 5));
        icons.add(new Icon("Damage", "Res/Textures/Icons/Damage.png", 120, 26));
        icons.add(new Icon("Fire rate", "Res/Textures/Icons/FireRate.png", 120, 47));
        icons.add(new Icon("Health", "Res/Textures/Icons/Health.png", 120, 68));
        icons.add(new Icon("Speed", "Res/Textures/Icons/Speed.png", 120, 89));
        icons.add(new Icon("Mobility", "Res/Textures/Icons/Jump.png", 120, 110));
        
    }
    
    @Override
    public void init(Container c) {
        
        c.getGUI().showTitleText("");
        c.getRenderer().setAmbientColor(0xffffffff);
        c.getGUI().setShowing(false);
        
    }
    
    @Override
    public void update(Container c, StateManager sm, float dt) {
           
        if(c.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            
            if(selling) selling = false;
            sm.changeState(sm.getPreviousState(), c);
            
        }
        
        for(Icon icon : icons) {
            
            icon.update(c, dt);
            
        }
        
        for(Item item : c.getGame().getPlayer().getInventory()) {
            
            if(item != null) {
                if(item.isHighlighted(c)) {
                
                    item.setSelected(true);
                    
                    if(selling && c.getInput().isButtonDown(1)
                        && (item instanceof Equipment ||
                        item instanceof Trinket)) {
                        sm.getPlayer().addGold(item.getValue());
                        sm.getPlayer().removeFromInventory(item);
                    } else if(item instanceof Equipment && c.getInput().isButtonDown(1)) 
                        c.getGame().getPlayer().equipItem((Equipment)item, (Level)c.getGame().getState("level1"));
                    else if(item instanceof Trinket && c.getInput().isButtonDown(1)) 
                        c.getGame().getPlayer().equipTrinket((Trinket)item, (Level)c.getGame().getState("level1"));
                                        
                } else {
                
                    item.setSelected(false);
                
                }
            }
            
        }
        
        for(Item item : c.getGame().getPlayer().getEquipment()) {
            
            if(item != null) {
                if(item.isHighlighted(c)) {
                
                    item.setSelected(true);
                
                } else {
                
                    item.setSelected(false);
                
                }
            }
            
        }
        
        for(Item item : c.getGame().getPlayer().getTrinkets()) {
            
            if(item != null) {
                if(item.isHighlighted(c)) {
                
                    item.setSelected(true);
                    
                    if(c.getInput().isButtonDown(1)) 
                        c.getGame().getPlayer().unequipTrinket((Trinket)item, (Level)c.getGame().getState("level1"));
                
                } else {
                
                    item.setSelected(false);
                
                }
            }
            
        }
        
    }
    
    @Override
    public void render(Container c, Renderer r) {
        
        r.drawImage(background, r.getCamX(), r.getCamY());
                
        r.drawImageTile(playerImage, r.getCamX() + 40, r.getCamY() + 40, 0, 0);
        
        r.drawText("" + c.getGame().getPlayer().getGold(), 140 + r.getCamX(), 2 + r.getCamY(), 0xffffff00);
        r.drawText("" + (int)(c.getGame().getPlayer().getDamage() * 100) / 100.0,
            140 + r.getCamX(), 22 + r.getCamY(), 0xffff0000);
        r.drawText("" + (int)(c.getGame().getPlayer().getReloadSpeed() * 100) / 100.0, 
            140 + r.getCamX(), 44 + r.getCamY(), 0xff00ffff);
        r.drawText("" + c.getGame().getPlayer().getMaxHealth(), 
            140 + r.getCamX(), 65 + r.getCamY(), 0xffff0000);
        r.drawText("" + (int)(c.getGame().getPlayer().getSpeed() * 100) / 10000.0, 
            140 + r.getCamX(), 86 + r.getCamY(), 0xffff0000);
        r.drawText("" + (int)(c.getGame().getPlayer().getMobility() * 100) / 1000.0, 
            140 + r.getCamX(), 107 + r.getCamY(), 0xffff0000);
        
        
        for(Icon icon : icons) {
            
            icon.render(c, r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getInventory()) {
            
            if(item != null) item.drawIcon(r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getEquipment()) {
            
            if(item != null) item.drawIcon(r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getTrinkets()) {
            
            if(item != null) item.drawIcon(r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getInventory()) {
            
            if(item != null && item.isSelected()) item.displayStats(r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getEquipment()) {
            
            if(item != null && item.isSelected()) item.displayStats(r);
            
        }
        
        for(Item item : c.getGame().getPlayer().getTrinkets()) {
            
            if(item != null && item.isSelected()) item.displayStats(r);
            
        }
        
    }
    
    @Override
    public void exit() {
        
        
        
    }
    
    public static void setSelling(boolean b) {
        
        selling = b;
        
    }
        
}