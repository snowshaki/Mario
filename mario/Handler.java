package mario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import mario.entity.Coin;
import mario.entity.Entity;
import mario.entity.mob.Goomba;
import mario.entity.mob.Player;
import mario.entity.mob.TowerBoss;
import mario.tile.Flag;
import mario.tile.Pipe;
import mario.tile.PowerUpBlock;
import mario.tile.Tile;
import mario.tile.Wall;

public class Handler {
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();

	public void render(Graphics g) {
		for(int i=0; i<entity.size();i++) {
			Entity e = entity.get(i);
			if(Game.getVisibleArea()!=null&&e.getBounds().intersects(Game.getVisibleArea()))e.render(g);
		}
		for(int i=0; i<tile.size();i++) {
			Tile t = tile.get(i);
			if(Game.getVisibleArea()!=null&&t.getBounds().intersects(Game.getVisibleArea()))t.render(g);
		}
	}
	public void tick() {
		for(int i=0; i<entity.size();i++) {
			Entity e = entity.get(i);
			e.tick();
		}
		for(int i=0; i<tile.size();i++) {
			Tile t = tile.get(i);
			if(Game.getVisibleArea()!=null&&t.getBounds().intersects(Game.getVisibleArea()))t.tick();
		}
	}
	public void addEntity(Entity e) {
		entity.add(e);
	}
	public void removeEntity(Entity e) {
		entity.remove(e);
	}
	public void addTile(Tile t) {
		tile.add(t);
	}
	public void removeTile(Tile t) {
		tile.remove(t);
	}
	public void createLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();
		
		for(int y = 0;y<height;y++) {
			for(int x = 0; x<width;x++) {
				int pixel = level.getRGB(x, y);
				
				int red = (pixel>>16)&0xff;
				int green = (pixel>>8)&0xff;
				int blue = (pixel)&0xff;
				
				if(red==0&&green==00&&blue==0)addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this));
				if(red==0&&green==00&&blue==255)addEntity(new Player(x*64,y*64,48,48,Id.player,this));
//				if(red==255&green==00&blue==0)addEntity(new Mushroom(x*64,y*64,64,64,Id.mushroom,this));
				if(red==255&&green==119&&blue==0)addEntity(new Goomba(x*64,y*64,64,64,Id.goomba,this));
				if(red==0&&(green>123&&green<129)&&blue==0)addTile(new Pipe(x*64,y*64,64,64*15,true,Id.pipe,this,128-green));
				if(red==255&&green==255&&blue==0)addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
				if(red==0&&green==255&blue==255)addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.mushroom,0));
				if(red==255&&green==0&&blue==255)addEntity(new TowerBoss(x*64,y*64,64,64,Id.towerBoss,this,3));
				if(red==0&&green==255&&blue==0)addTile(new Flag(x*64,y*64,64,64*5,true,Id.flag,this));
			}
		}
	}
	public void clearLevel() {
		entity.clear();
		tile.clear();
	}
}
