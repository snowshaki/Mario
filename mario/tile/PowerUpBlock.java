package mario.tile;

import java.awt.Graphics;

import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.powerup.Mushroom;
import mario.gfx.Sprite;

public class PowerUpBlock extends Tile{
	private Sprite powerUp;
	private boolean poppedUp = false;
	private int spriteY = getY();
	public int type;
	public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, int type) {
		super(x, y, width, height, solid, id, handler);
		this.powerUp = powerUp;
		this.type = type;
	}
	public void render(Graphics g) {
		if(!poppedUp) g.drawImage(powerUp.getBufferedImage(), x, spriteY, width, height, null);
		if(!activated) g.drawImage(Game.powerUp.getBufferedImage(), x, y, width, height, null);
		else g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, width, height, null);
	}
	public void tick() {
		if(activated&&!poppedUp) {
			spriteY--;
			if(spriteY<=y-height) {
				handler.addEntity(new Mushroom(x,spriteY,width,height,Id.mushroom,handler,type));
				poppedUp = true;
			}
		}
	}
}
