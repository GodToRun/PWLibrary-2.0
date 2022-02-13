package pw.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pw.render.GameRenderer;

public class PWText extends PWWidget {
	private String text;
	private Font font;
	public PWText(String text, int x, int y, Color col, Font font) {
		super(x, y, 0, 0, col);
		this.text = text;
		this.font = font;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(Graphics g) {
		GameRenderer.renderText(g, text, x, y, font, getCol());
	}
}
