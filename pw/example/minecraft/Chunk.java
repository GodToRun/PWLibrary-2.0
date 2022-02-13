package pw.example.minecraft;

import java.awt.Point;

import pw.common.GameObject;

public class Chunk {
	int x;
	public static final int CHUNK_WIDTH = 16;
	public static final int CHUNK_HEIGHT = 64;
	private boolean active;
	public GameObject[][] blocks = new GameObject[CHUNK_WIDTH][CHUNK_HEIGHT];
	public Chunk(Level level, int x) {
		this.x = x;
		level.chunks[x] = this;
	}
	public static int toChunkPos(int xCoord) {
		return xCoord / 16;
	}
	public static Point toChunkBlockPos(int xCoord, int yCoord) {
		return new Point(xCoord % 16, yCoord % 64);
	}
	public static int toNormalPos(int chunkXCoord) {
		return chunkXCoord * 16;
	}
	public boolean isActive() {
		return this.active;
	}
	public void setActive(boolean active) {
		this.active = active;
		if (active) {
			for (int x = 0; x < CHUNK_WIDTH; x++) {
				for (int y = 0; y < CHUNK_HEIGHT; y++) {
					if (blocks[x][y] != null) {
						blocks[x][y].setActive(true);
					}
				}
			}
		}
		else {
			for (int x = 0; x < CHUNK_WIDTH; x++) {
				for (int y = 0; y < CHUNK_HEIGHT; y++) {
					if (blocks[x][y] != null) {
						blocks[x][y].setActive(false);
					}
				}
			}
		}
	}
}
