package pw.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameRenderer {
	public static void renderFillRectangle(Graphics g, int x, int y, int width, int height, Color col) {
		g.setColor(col);
		g.fillRect(x, y, width, height);
	}
	public static void renderText(Graphics g, String str, int x, int y, Font font, Color col) {
		g.setColor(col);
		g.setFont(font);
		g.drawString(str, x, y);
	}
	
}
