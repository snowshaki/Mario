package mario.entity.mob;

import java.awt.Graphics;

import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.states.BossState;
import mario.states.PlayerState;
import mario.tile.Tile;

public class Player extends Entity{
	private PlayerState state;
	private int pixelsTravelled = 0;
	
//	private boolean animate = false;
	public Player(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		state = PlayerState.SMALL;
	}
	public void render(Graphics g) { //face
		if(facing == 0) {
			g.drawImage(Game.player[frame+5].getBufferedImage(), x,y,width,height,null);
		}else if(facing == 1) {
			g.drawImage(Game.player[frame].getBufferedImage(), x,y,width,height,null);
		}
	}
	public void tick() {
		x+=velX;
		y+=velY;
		
		/*if(x+width>=1080) x=1080-width;
		if(y+height>=771) y=771-height;
		if(velX!=0) animate = true;
		else animate = false;		
		for(Tile t:handler.tile) {
			if(!t.solid) break;
			if(t.getId()==Id.wall) {
				if(getBonudsTop().intersects(t.getBonuds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
				}if(getBonudsBottom().intersects(t.getBonuds())) {
					setVelY(0);
					if(falling) falling = false;
				}else {
					if(!falling&&!jumping) {
						gravity = 0.8;
						falling = true;
						}
				}if(getBonudsLeft().intersects(t.getBonuds())) {
					setVelX(0);
					x = t.getX() + t.width;
				}if(getBonudsRight().intersects(t.getBonuds())) {
					setVelX(0);
					x = t.getX() - t.width;
				}
			}
		}*/
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(t.isSolid()&&!goingDownPipe) {
				if(getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping&&!goingDownPipe) {
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
					if(t.getId()==Id.powerUp) {
						if(getBoundsTop().intersects(t.getBounds())) t.activated = true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling) falling = false;
				}else if(!falling&&!jumping) {
					gravity = 0.8;
					falling = true;
				}if(getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() + t.width;
				}if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() - t.width;
				}
//				if(getBounds().intersects(t.getBounds())) {
//					if(t.getId()==Id.flag) Game.switchLevel();
//				}
			}
		}
		for(int i=0; i<handler.entity.size();i++) {
			Entity e = handler.entity.get(i);
			
			if(e.getId()==Id.mushroom) {
				switch(e.getType()) {
				case 0:
					if(getBounds().intersects(e.getBounds())) {
						int tpX = getX();
						int tpY = getY();
						width+=(width/3);
						height+=(height/3);
						setX(tpX-width);
						setY(tpY-height);
						if(state==PlayerState.SMALL) state = PlayerState.BIG;
						e.die();
					}
				case 1:
					if(getBounds().intersects(e.getBounds())) {
						e.die();
					}
				}
			}else if(e.getId()==Id.goomba||e.getId()==Id.towerBoss) {
				if(getBoundsBottom().intersects(e.getBoundsTop())) {
					if(e.getId()!=Id.towerBoss) e.die();
					else if(e.attackable){
						e.hp--;
						e.falling = true;
						e.gravity = 3.0;
						e.bossState = BossState.RECOVERING;
						e.attackable = false;
						e.phaseTime = 0;
						
						jumping = true;
						falling = false;
						gravity = 3.5;
					}
				}else if(getBounds().intersects(e.getBounds())) {
					if(state==PlayerState.BIG) {
						state = PlayerState.SMALL;
						width/=3;
						height/=3;
						x+=width;
						y+=height;
					}else if(state==PlayerState.SMALL) {
						die();
					}
				}
			} else if(e.getId()==Id.coin) {
				if(getBounds().intersects(e.getBounds())&&e.getId()==Id.coin) {
					Game.coins++;
					e.die();
				}
			}
		}
		if(jumping&&!goingDownPipe) {
			gravity-=0.17;
			setVelY((int)-gravity);
			if(gravity<=0.5) {
				jumping = false;
				falling = true;
			}
		}
		if(falling&&!goingDownPipe) {
			gravity+=0.17;
			setVelY((int)gravity);
		}
		if(velX!=0) { //(animate)¿¡¼­
			frameDelay++;
			if(frameDelay>=3) {
				frame++;
				if(frame>=5) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
		if(goingDownPipe) {
			for(int i=0; i<Game.handler.tile.size(); i++) {
				Tile t = Game.handler.tile.get(i);
				if(t.getId()==Id.pipe) {
					if(getBounds().intersects(t.getBounds())) {
						switch(t.facing) {
						case 0: //up
							setVelY(-5);
							setVelX(0);
							pixelsTravelled+=velY;
							break;
						case 2: //down
							setVelY(5);
							setVelX(0);
							pixelsTravelled+=velY;
							break;
						}
						if(pixelsTravelled>t.height) {
							goingDownPipe = false;
							pixelsTravelled= 0;
						}
					}
				}
			}
		}
	}
}
