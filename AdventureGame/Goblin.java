public class Goblin extends Enemy {

    int tileX, tileY;
    float offX, offY;

    private boolean ground = false;
    private float speed = 120;
    private float fallSpeed = 20;
    private float fallDistance = 0;
    private float jump = -5;
    
    private boolean hidden = true;
    
    private ImageTile image = new ImageTile("Res/Textures/Goblin.png", 16, 23);
    private float imgX = 0, imgY = 0;

    public Goblin(int x, int y) {

        this.tileX = x;
        this.tileY = y;
        this.offX = 0;
        this.offY = 0;
        this.x = tileX * Level.TS;
        this.y = tileY * Level.TS;

        this.width = image.getTileWidth();
        this.height = image.getTileHeight();
        
        this.health = 2;

    }

    @Override
    public void update(Container c, Level l, float dt) {
        
        if(health <= 0) {
            l.addObject(Item.generateLoot(x, y, Player.level, Player.level, this.level));
            l.getPlayer().addExp(3);
            dead = true;
        }
        
        if(this.collidesWith(l.getPlayer())) {
            
            l.getPlayer().dealDamage(1);
            
        }
        
        if(distanceTo(l.getPlayer()) < 100 || health < 2) { hidden = false; imgY = 0; imgX = 0;} else { hidden = true; imgY = 0; imgX = 1;}
        if(l.getPlayer().getX() > x + 5 && !hidden) {
            
            imgY = 2;
            imgX += dt * 8f;
            if(imgX >= 2) imgX = 0;

            if(l.getCollision(tileX + 1, tileY) ||
            l.getCollision(tileX, tileY +  (int)Math.signum((int)offY))) {

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

        } else if(l.getPlayer().getX() < x - 5 && !hidden) {
            
            imgY = 1;
            imgX += dt * 8f;
            if(imgX >= 2) imgX = 0;

            if(l.getCollision(tileX - 1, tileY) ||
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

        if(l.getPlayer().getY() < y && ground && !hidden) {

            fallDistance = jump;
            ground = false;

        }

        fallDistance += dt * fallSpeed;

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

                if(fallDistance > 10) {

                    health -= (int)(fallDistance - 10) / 2;

                }

                fallDistance = 0;
                offY = 0;
                ground = true;

            } else {

                ground = false;

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
        
        if(!ground) { imgX = 0; imgY = 0; }

        x = tileX * Level1.TS + offX;
        y = tileY * Level1.TS + offY;

    }

    @Override
    public void render(Container c, Renderer r) {

        r.drawImageTile(image, (int)x, (int)(y - 7), (int)imgX, (int)imgY);
        
    }

}