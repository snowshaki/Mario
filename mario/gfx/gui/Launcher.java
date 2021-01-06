package mario.gfx.gui;

import java.awt.Color;
import java.awt.Graphics;

import mario.Game;

public class Launcher {
	public Button[] buttons;
	public Launcher() {
		buttons = new Button[2];
		buttons[0] = new Button(100, 100, 100, 100, "Start Game");
		buttons[1] = new Button(200, 200, 100, 100, "Exit Game");
	}
	public void render(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
		
		for(int i=0; i<buttons.length; i++) {
			buttons[i].render(g);
		}
	}
}
