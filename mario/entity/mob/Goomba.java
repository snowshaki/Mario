package mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.tile.Tile;

public class Goomba extends Entity{

	private Random random = new Random();

	public Goomba(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		int dir = random.nextInt(2);
		switch(dir) {
		case 0:
			setVelX(-2);
			facing = 0;
			break;
		case 1:
			setVelX(2);
			facing = 1;
			break;
		}
	}
	public void render(Graphics g) {
		if(facing == 0) {
			g.drawImage(Game.goomba[frame+5].getBufferedImage(), x,y,width,height,null);
		}else if(facing == 1) {
			g.drawImage(Game.goomba[frame].getBufferedImage(), x,y,width,height,null);
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
					facing =1;
				}if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(-2);
					facing=0;
				}
			}
		}
		if(falling) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
		if(velX!=0) { //(animate)¿¡¼­
			frameDelay++;
			if(frameDelay>=10) {
				frame++;
				if(frame>3) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
	}
}
