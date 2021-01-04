package mario.entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.tile.Tile;

public class Mushroom extends Entity{
	private Random random = new Random();
	
	public Mushroom(int x, int y, int width, int height, Id id, Handler handler, int type) {
		super(x, y, width, height, id, handler);
		this.type = type;
		
		int dir = random.nextInt(2);
		switch(dir) {
		case 0:
			setVelX(-2);
			break;
		case 1:
			setVelX(2);
			break;
		}
	}
	public void render(Graphics g) { 
		switch(getType()) {
		case 0:
			g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
			break;
		case 1:
			g.drawImage(Game.lifeMushroom.getBufferedImage(), x, y, width, height, null);
			break;
		}
	}
	public void tick() {
		x+=velX;
		y+=velY;
		
		for(int i=0;i<handler.tile.size();i++) { //°ýÈ£¾È Tile t:handler.tile »©°í ¼öÁ¤
			Tile t = handler.tile.get(i);
			if(t.isSolid()) {
				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling) falling = false;
				}else if(!falling) {
					gravity = 0.8;
					falling = true;
				}if(getBoundsLeft().intersects(t.getBounds())) {
					setVelX(2);
				}if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(-2);
				}
			}
		}
		if(falling) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
	}
}
