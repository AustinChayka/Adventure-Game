public class GUI {
    
    private boolean showing = false;
    private int goldCounter = 0;
    private int healthCounter = 0;
    private int maxHealth = 1;
    private int levelCounter = 1;
    private int expCounter = 1;
    
    private int pastGold = 3;
    private int pastHealth = 3;
    private int pastExp = 3;
    
    private float displayHealth = 0f;
    private float displayExp = 0f;
    private float displayGold = 0f;
    
    private Image reloadIcon = new Image("Res/Textures/Icons/Reload.png");
    private float reloadCounter;
    
    private float showTitleText = 0;
    private String titleText = "";
    
    private float showInfoText = 0;
    private String infoText = "";
    
    private Image healthShell = new Image("Res/Textures/HealthShell.png");
    
    public GUI() {
        
        
        
    }
    
    public void update(Container c, float dt) {
        
        goldCounter = c.getGame().getPlayer().getGold();
        healthCounter = c.getGame().getPlayer().getHealth();
        maxHealth = c.getGame().getPlayer().getMaxHealth();
        levelCounter = c.getGame().getPlayer().getLevel();
        expCounter = c.getGame().getPlayer().getExp();
        reloadCounter = c.getGame().getPlayer().getReloadCount();
                
        if(pastGold != goldCounter) displayGold = 3;
        if(pastHealth != healthCounter) displayHealth = 3;
        if(pastExp != expCounter) displayExp = 3;
        
        if(displayGold > 0) displayGold -= dt;
        if(displayHealth > 0) displayHealth -= dt;
        if(displayExp > 0) displayExp -= dt;
        if(showTitleText > 0) showTitleText -= dt;
        if(showInfoText > 0) showInfoText -= dt;
        
        pastGold = goldCounter;
        pastHealth = healthCounter;
        pastExp = expCounter;
        
    }
    
    public void render(Container c, Renderer r) {
        
        if(showing) {
            
            if(displayGold > 0) {
                r.drawText("" + goldCounter, r.getCamX(), 
                    r.getCamY() + 295, 0xffffff00);
            }
                
            if(displayHealth > 0) { 
                r.fillRect(r.getCamX() + 200, r.getCamY() + 10, 100,
                    10, 0xff666666, 0);
                r.fillRect(r.getCamX() + 200, r.getCamY() + 10, 100 
                    * healthCounter / maxHealth, 10, 0xffff0000, 0);
                r.drawImage(healthShell, r.getCamX() + 198, r.getCamY() + 0);
            }
            
            if(displayExp > 0) {
                r.drawText("" + levelCounter, r.getCamX() + 125,
                    r.getCamY() + 295, 0xff00ffff);
                r.fillRect(r.getCamX() + 150, r.getCamY() + 300, 200,
                    10, 0xff666666, 0);
                r.fillRect(r.getCamX() + 150, r.getCamY() + 300, 200 
                    * expCounter / ((levelCounter + 1) * 2), 10, 0xff00ffff, 0);
            }
            
            if(reloadCounter > 0) {
                r.drawImage(reloadIcon, 460 + r.getCamX(), 300 + r.getCamY());
                r.fillRect(460 + r.getCamX(), 300 + r.getCamY() + 16 - 
                    (int)(17 * reloadCounter /  c.getGame().getPlayer().getReloadSpeed()), 16, (int)(17 * reloadCounter / 
                    c.getGame().getPlayer().getReloadSpeed()), 0xaae03000, 0);
            }
            
        }
        
        if(showTitleText > 0) {
            r.drawText(titleText, r.getCamX() + 200, r.getCamY() + 30, 0xffffffff);
        }
        
        if(showInfoText > 0) {
            if(c.getGame().getCurrentState() instanceof Level) 
            r.drawText(infoText, (int)(((Level)c.getGame().getCurrentState()).getPlayer().getX()) 
                - (int)(r.getTextLength(infoText) / 2), (int)(((Level)c.getGame().getCurrentState()).getPlayer().getY()) 
                - 48, 0xffffffff);
        }
        
    }
    
    public void setShowing(boolean s) {
        
        this.showing = s;
        
    }
    
    public void showTitleText(String text) {
        
        titleText = text;
        showTitleText = 5;
        
    }
    
    public void showInfoText(String text) {
        
        infoText = text;
        showInfoText = 3;
        
    }
    
}