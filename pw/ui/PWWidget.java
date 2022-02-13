package pw.ui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class PWWidget {
	public int x, y, width, height;
	private Color col;
	private boolean active = true;
	public PWWidget(int x, int y, int width, int height, Color col) {
		this.col = col;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Color getCol() {
		return col;
	}
	public abstract void render(Graphics g);
	
	public void setCol(Color col) {
		this.col = col;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
