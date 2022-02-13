package pw.example.minecraft;

import javax.swing.JPanel;

import pw.ui.PWPanel;

public abstract class TerrainGenerator {
	public abstract void generateTerrain(Level level, PWPanel pwPanel, JPanel panel);
}
