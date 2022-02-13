package pw.example.minecraft;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;

import pw.common.GameObject;
import pw.common.ObjectType;
import pw.component.Physics;

public class Tile {
	public int id;
	public static Tile[] tiles = new Tile[128];
	public static final int LEVEL_WIDTH = 128;
	public static final int LEVEL_HEIGHT = 64;
	private static GameObject[][] totalTiles = new GameObject[LEVEL_WIDTH][LEVEL_HEIGHT];
	public static int air = 0;
	public static int grass = 1;
	public static int cobblestone = 2;
	public static int bedrock = 3;
	public static int dirt = 4;
	public static int planks = 5;
	public static int rock = 6;
	public static int sapling = 7;
	public static int wood = 8;
	public static int leaves = 9;
	public String imagePath;
	public Tile(int id, String imagePath) {
		tiles[id] = this;
		this.id = id;
		this.imagePath = imagePath;
	}
	public boolean isSolid() {
		return true;
	}
	static {
		Tile grass = new Tile(1, "grass.png");
		Tile cobblestone = new Tile(2, "cobblestone.png");
		Tile bedrock = new Tile(3, "rock.png");
		Tile dirt = new Tile(4, "dirt.png");
		Tile planks = new Tile(5, "planks.png");
		Tile stone = new Tile(6, "stone.png");
		Bush sapling = new Bush(7, "sapling.png");
		Tile wood = new Tile(8, "wood.png");
		Tile leaves = new Tile(9, "leaves.png");
	}
	public static void setTile(Level level, JPanel panel, int tileX, int tileY, int tileId, boolean active) {
		if (tileX < 0 || tileX >= LEVEL_WIDTH || tileY < -32 || tileY >= LEVEL_HEIGHT+32) {
			return;
		}
		if (tileId != 0) {
			if (getTotalTiles(level, tileX, tileY) == null) {
				GameObject tile = new GameObject(tileX * 50, tileY * 50, 50, 50, ObjectType.Image, panel, Color.DARK_GRAY);
				tile.tag = String.valueOf(tileId);
				try {
					tile.setImage(tiles[tileId].imagePath);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				if (tiles[tileId].isSolid()) {
					Physics physics = new Physics();
					physics.gravity = 0;
					tile.addComponent(physics);
				}
				tile.setActive(active);
				level.chunks[Chunk.toChunkPos(tileX)].blocks[tileX % 16][tileY + 32] = tile;
			}
		}
		else {
			if (getTotalTiles(level, tileX, tileY) != null) {
				getTotalTiles(level, tileX, tileY).Delete();
				level.chunks[Chunk.toChunkPos(tileX)].blocks[tileX % 16][tileY + 32] = null;
			}
		}
		
		
	}
	public static GameObject getTotalTiles(Level level, int tileX, int tileY) {
		return level.chunks[Chunk.toChunkPos(tileX)].blocks[tileX % 16][tileY + 32];
	}
}
