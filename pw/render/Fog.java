package pw.render;

import java.awt.Color;
import java.awt.Graphics;

public class Fog {
	private int fogWidth;
	private Color fogColor;
	private int screenWidth;
	private int screenHeight;
	public Fog(int fogWidth, Color fogColor, int screenWidth, int screenHeight) {
		this.fogWidth = fogWidth;
		this.fogColor = fogColor;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	public void renderFog(Graphics g) {
		g.setColor(fogColor);
		g.fillRect(0, 0, (int)(fogWidth / 2.5f), screenHeight);
		int x = (int)(fogWidth / 2.5f);
		for (float i = fogWidth; i > 0; i--) {
			float red = (float)fogColor.getRed() / 255;
			float green = (float)fogColor.getGreen() / 255;
			float blue = (float)fogColor.getBlue() / 255;
			g.setColor(new Color(red, green, blue, i / fogWidth));
			g.drawLine(x, 0, x, screenHeight);
			x++;
		}
		x = screenWidth - (int)(fogWidth / 2.5f);
		g.setColor(fogColor);
		g.fillRect(screenWidth - (int)(fogWidth / 2.5f), 0, (int)(fogWidth / 2.5f), screenHeight);
		for (float i = fogWidth; i > 0; i--) {
			float red = (float)fogColor.getRed() / 255;
			float green = (float)fogColor.getGreen() / 255;
			float blue = (float)fogColor.getBlue() / 255;
			g.setColor(new Color(red, green, blue, i / fogWidth));
			g.drawLine(x, 0, x, screenHeight);
			x--;
		}
	}
}
