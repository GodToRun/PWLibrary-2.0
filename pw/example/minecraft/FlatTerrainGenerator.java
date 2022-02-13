package pw.example.minecraft;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

import com.mojang.minecraft.level.generator.noise.CombinedNoise;
import com.mojang.minecraft.level.generator.noise.OctaveNoise;

import pw.common.GameObject;
import pw.common.ObjectType;
import pw.component.Physics;
import pw.ui.PWPanel;

public class FlatTerrainGenerator extends TerrainGenerator {

	@Override
	public void generateTerrain(Level level, PWPanel pwPanel, JPanel panel) {
		for (int tx = 0; tx < 64; tx++) {
			for (int ty = 0; ty < 16; ty++) {
				if (ty < 4)
					Tile.setTile(level, panel, tx, ty, Tile.grass, false);
				else if (ty == 15) {
					Tile.setTile(level, panel, tx, ty, Tile.bedrock, false);
				}
				else
					Tile.setTile(level, panel, tx, ty, Tile.rock, false);
			}
		}
	}
	
}
