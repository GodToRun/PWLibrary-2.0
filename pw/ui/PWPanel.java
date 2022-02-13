package pw.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import pw.render.GameRenderer;

public class PWPanel extends PWWidget {
	private Color col;
	public ArrayList<PWWidget> widgets = new ArrayList<PWWidget>();
	public PWPanel(int x, int y, int width, int height, Color col) {
		super(x, y, width, height, col);
	}
	public void addTextWidget(String str, int x, int y, Color col, Font font) {
		widgets.add(new PWText(str, x, y, col, font));
	}
	@Override
	public void render(Graphics g) {
		if (isActive()) {
			GameRenderer.renderFillRectangle(g, x, y, width, height, col);
			for (PWWidget widget : widgets) {
				widget.render(g);
			}
		}
	}
}
