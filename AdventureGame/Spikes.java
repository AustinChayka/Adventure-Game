public class Spikes extends GameObject { //todo add sprite

    private static int tagNum = 0;

    enum Direction {

        Top, Bottom, Left, Right

    }

    private boolean extended;

    private float extendDelay, resetDelay;

    private Direction dir;

    private ImageTile image = new ImageTile("Res/Textures/Spikes.png", 16, 16);
    private int tileX = 0, tileY = 0;

    public Spikes(float x, float y, Direction d) {

        this.tag = "spikes." + tagNum++;

        this.dir = d;

        this.x = x;
        this.y = y;

        this.width = image.getWidth();
        this.height = image.getHeight();

        switch(dir) {

            case Top:
            //setTile
            break;
            case Bottom:
            //setTile
            break;
            case Left:
            //setTile
            break;
            case Right:
            //setTile
            break;

        }

    }

    @Override
    public void update(Container c, Level l, float dt) {

        if(extended && this.collidesWith(l.getPlayer())) 
            l.getPlayer().dealDamage(2);

        if(this.collidesWith(l.getPlayer()) && !extended && extendDelay == -1)
            extendDelay = 0.5f;

        if(extendDelay > 0) extendDelay -= dt; 
        if(extendDelay <= 0 && extendDelay > -1) {
            tileX = 1;
            extended = true;
            extendDelay = -1;
            resetDelay = 1.5f;
        }
        if(resetDelay > 0) resetDelay -= dt;
        else if(extended) { extended = false; tileX = 0; }

    }

    @Override
    public void render(Container c, Renderer r) {

        r.drawImageTile(image, (int)x, (int)y, tileX, tileY);

    }

}