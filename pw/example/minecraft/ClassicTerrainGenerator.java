package pw.example.minecraft;

import java.awt.Color;
import java.awt.Font;

import pw.ui.PWPanel;
import pw.ui.PWText;

import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

import com.mojang.minecraft.level.generator.noise.CombinedNoise;
import com.mojang.minecraft.level.generator.noise.OctaveNoise;

import pw.common.GameObject;
import pw.common.ObjectType;
import pw.component.Physics;
import pw.example.minecraft.levelgen.*;

public class ClassicTerrainGenerator extends TerrainGenerator {
	private void setProgress(PWPanel pwPanel, String progress) {
		PWText text = (PWText)pwPanel.widgets.get(0);
		text.setText(progress);
	}
	@Override
	public void generateTerrain(Level level, PWPanel pwPanel, JPanel panel) {
		for (int cx = 0; cx < 8; cx++) {
			Chunk c = new Chunk(level, cx);
		}
		pwPanel.addTextWidget("", (int)(panel.getWidth() / 2.2f), panel.getHeight() / 2, Color.WHITE,
				new Font("Minecraftia", Font.PLAIN, 24));
		Random random = new Random();
		OctaveNoise noise = new OctaveNoise(random, 8);
		OctaveNoise noise2 = new OctaveNoise(random, 6);
		CombinedNoise comNoise = new CombinedNoise(noise, noise2);
		PerlinNoise perlin = new PerlinNoise();
		int y = 15;
		setProgress(pwPanel, "Eroding...");
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = 0; ty < 16; ty++) {
				
				
				float ran = random.nextFloat() * 3.14159265358979323846264338327f;
				double comNoiseValue = comNoise.compute(tx * .3f, y * .3f) * 2 + 4 - ran;
				if ((perlin.compute(tx * .3f, y * .3f + comNoiseValue * 4)) * 24 - y > 0) {
					if (ty < 4)
						Tile.setTile(level, panel, tx, ty, Tile.dirt, false);
					else if (ty == 15) {
						Tile.setTile(level, panel, tx, ty, Tile.bedrock, false);
					}
					else
						Tile.setTile(level, panel, tx, ty, Tile.rock, false);
				}
				y--;
			}
			y = 0;
		}
		setProgress(pwPanel, "Rasing...");
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = 0; ty < 16; ty++) {
					if (Tile.getTotalTiles(level, tx, ty + 1) == null &&
							Tile.getTotalTiles(level, tx, ty) != null) {
						Tile.setTile(level, panel, tx, ty + 1, Integer.parseInt(Tile.getTotalTiles(level, tx, ty).tag), false);
				}
			}
		}
		setProgress(pwPanel, "Carving...");
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = 0; ty < 16; ty++) {
				float ran = random.nextFloat() * (float)Math.PI;
				if (perlin.compute(tx * .2f, ty * .2f) - 0.4f - Math.sin(Math.toRadians(ran)) > 0) {
					if (Tile.getTotalTiles(level, tx, ty) != null &&
						Tile.getTotalTiles(level, tx, ty).tag.equals(String.valueOf(Tile.rock))) {
						Tile.setTile(level, panel, tx, ty, Tile.air, false);
					}
				}
			}
		}
		setProgress(pwPanel, "Growing...");
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = -1; ty < 16; ty++) {
				GameObject down = Tile.getTotalTiles(level, tx, ty + 1);
					if (down != null && down.tag.equals(String.valueOf(Tile.dirt)) &&
							Tile.getTotalTiles(level, tx, ty) == null) {
						Tile.setTile(level, panel, tx, ty, Tile.grass, false);
				}
			}
		}
		setProgress(pwPanel, "Planting...");
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = -1; ty < 16; ty++) {
				GameObject tile = Tile.getTotalTiles(level, tx, ty);
					if (tile != null && tile.tag.equals(String.valueOf(Tile.grass)) &&
							random.nextInt(13) == 5) {
						int treeHeight = ty - (random.nextInt(4) + 2);
						for (int i = treeHeight+1; i < ty; i++) {
							Tile.setTile(level, panel, tx, i, Tile.wood, false);
						}
						for (int x = tx - 2; x < tx + 3; x++) {
							for (int treeY = treeHeight; treeY > treeHeight-2; treeY--)
								Tile.setTile(level, panel, x, treeY, Tile.leaves, false);
						}
						
						Tile.setTile(level, panel, tx-1, treeHeight-2, Tile.leaves, false);
						Tile.setTile(level, panel, tx, treeHeight-2, Tile.leaves, false);
						Tile.setTile(level, panel, tx+1, treeHeight-2, Tile.leaves, false);
						
						Tile.setTile(level, panel, tx, treeHeight-3, Tile.leaves, false);
				}
			}
		}
		pwPanel.setActive(false);
	}
	
}
