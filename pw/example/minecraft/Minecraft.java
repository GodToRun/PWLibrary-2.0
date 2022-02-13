/*
Example of PW Engine.
this source doesn't have license.
 */
package pw.example.minecraft;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import pw.component.*;
import java.io.*;

import javax.imageio.ImageIO;
import java.net.URL;
import java.util.ConcurrentModificationException;

import javax.swing.*;

import pw.common.*;
import pw.kit.*;
import pw.render.Fog;
import pw.ui.PWPanel;
public class Minecraft extends GameManager implements MouseListener {
	public static final int PLAYER_LAYER = 2;
	Physics playerPhysics;
	PWPanel pwPanel;
	GameObject selectedTile;
	FPSCounter fpsCounter;
	GameObject player;
	Level level;
	int selectedTileBrightness;
	Fog fog;
	Inventory inventory = new Inventory();
	GameObject[][][] tiles = new GameObject[16][32][16];
	boolean pressingA = false;
	boolean pressingD = false;
	public Minecraft() {
		super("Minecraft", null); //null = FULLSCREEN
		this.level = new Level();
		inventory.set(0, Tile.grass);
		inventory.set(1, Tile.cobblestone);
		inventory.set(2, Tile.rock);
		inventory.set(3, Tile.dirt);
		inventory.set(4, Tile.planks);
		inventory.set(5, Tile.bedrock);
		inventory.set(6, Tile.sapling);
		
		pwPanel = new PWPanel(0, 0, getWidth(), getHeight(), Color.GRAY);
		
		fpsCounter = new FPSCounter();
		fpsCounter.start();
		addMouseListener(this);
		ClassicTerrainGenerator generator = new ClassicTerrainGenerator();
		generator.generateTerrain(level, pwPanel, getPanel());
		
		GameObject invisibleWall = new GameObject(-50, -500, 50, 2000, ObjectType.Cube, getPanel(), new Color(1, 1, 1, 0));
		Physics wallPhysics = new Physics();
		wallPhysics.gravity = 0;
		invisibleWall.addComponent(wallPhysics);
		
		GameObject invisibleWall2 = new GameObject((64 * 50), -500, 50, 2000, ObjectType.Cube, getPanel(), new Color(1, 1, 1, 0));
		Physics wallPhysics2 = new Physics();
		wallPhysics2.gravity = 0;
		invisibleWall2.addComponent(wallPhysics2);
		
		selectedTile = new GameObject(320, -140, 50, 50, ObjectType.Cube, getPanel(), Color.WHITE);
		player = new GameObject(850,-200,35,35,ObjectType.Image,getPanel());
		try {
			selectedTile.setImage("hitbox.png");
			player.setImage("aim.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		fog = new Fog(500, Environment.skyboxColor, getWidth(), getHeight());
		playerPhysics = new Physics();
		player.addComponent(playerPhysics);
		playerPhysics.getAABB().layer = PLAYER_LAYER;
		GameObject.SetAllObjectsDefaultSetting();
	}
	public static void main(String[] args) {
		new Minecraft();
	}
	@Override
	protected void DrawScreen(Graphics g) {
		super.DrawScreen(g);
		try {
			int i;
			for (i = 0; i < Camera.Objects.size(); i++) {
				if (Camera.Objects.size() > 0 && i < Camera.Objects.size()) {
					GameObject obj = Camera.Objects.get(i);
					if (obj != selectedTile && obj != null)
						obj.Show(g);
				}
			}
			if (selectedTile != null)
				selectedTile.Show(g);
		} catch (ConcurrentModificationException e) { e.printStackTrace();}
		if (fog != null)
			fog.renderFog(g);
		if (pwPanel != null) {
			pwPanel.render(g);
		}
		if (fpsCounter != null) {
			g.setFont(new Font("Minecraftia", Font.PLAIN, 30));
			g.setColor(Color.BLACK);
			g.drawString((int)fpsCounter.fps() + "", 50, 50);
		}
	}
	@Override
	protected void MainLoop() {
		if (player != null) {
			Camera.X = 850 + -player.X;
			Camera.Y = 440 + -player.Y;
			int playerPos = Chunk.toChunkPos(player.X / 50);
			for (int cx = -1; cx < 2; cx++) {
				if (cx + playerPos >= 0 && cx + playerPos < Chunk.CHUNK_WIDTH) {
					Chunk c = level.chunks[playerPos + cx];
					if (!c.isActive())
						c.setActive(true);
				}
			}
			for (int cx = -2; cx < 3; cx++) {
				if (cx + playerPos >= 0 && cx + playerPos < Chunk.CHUNK_WIDTH &&
						(cx < -1 || cx > 1)) {
					Chunk c = level.chunks[playerPos + cx];
					if (c.isActive()) {
						c.setActive(false);
					}
				}
			}
		} 
		if (selectedTile != null) {
			Point point = MouseInfo.getPointerInfo().getLocation();
			point.x += -Camera.X;
			point.y += (-Camera.Y) - 25;
			point.x = Math.round(point.x / 50) * 50;
			point.y = Math.round(point.y / 50) * 50;
			selectedTile.SetPosition((int)point.getX(), (int)point.getY());
			float value = (float)Math.sin(selectedTileBrightness * .2f) / 2.5f;
			if (value > 0.9f) {
				value = 0.9f;
				selectedTileBrightness += 4;
			}
			if (value < 0.1f) {
				value = 0.1f;
				selectedTileBrightness += 4;
			}
			selectedTile.color = new Color(1f, 1f, 1f, value);
			selectedTileBrightness++;
		}
		if (playerPhysics != null) {
			if (pressingA) {
				playerPhysics.addForce(-3f, 0, 4);
			}
			if (pressingD) {
				playerPhysics.addForce(2f, 0, 4);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'r') {
			Camera.SetPosition(0, 0);
			player.SetPosition(850, -200);
		}
		else if (e.getKeyCode() == 27) {
			Application.Exit();
		}
		
		if (e.getKeyChar() == '1') {
			inventory.selectedSlot = 0;
		}
		else if (e.getKeyChar() == '2') {
			inventory.selectedSlot = 1;
		}
		else if (e.getKeyChar() == '3') {
			inventory.selectedSlot = 2;
		}
		else if (e.getKeyChar() == '4') {
			inventory.selectedSlot = 3;
		}
		else if (e.getKeyChar() == '5') {
			inventory.selectedSlot = 4;
		}
		else if (e.getKeyChar() == '6') {
			inventory.selectedSlot = 5;
		}
		else if (e.getKeyChar() == '7') {
			inventory.selectedSlot = 6;
		}
		else if (e.getKeyChar() == '8') {
			inventory.selectedSlot = 7;
		}
		else if (e.getKeyChar() == '9') {
			inventory.selectedSlot = 8;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			pressingA = true;
		}
		if (e.getKeyChar() == 'd') {
			pressingD = true;
		}
		if (e.getKeyCode() == 0x20 && playerPhysics.isGround) {
			player.Move(0, -60);
		}
	}
	@Override                      
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a')
			pressingA = false;
		else if (e.getKeyChar() == 'd')
			pressingD = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (selectedTile != null) {
			if (SwingUtilities.isLeftMouseButton(e)) { // place
				if (inventory.getSelected().item != null)
					Tile.setTile(level, getPanel(), selectedTile.X / 50, selectedTile.Y / 50, inventory.getSelected().item.id, true);
			}
			if (SwingUtilities.isRightMouseButton(e)) { // destroy
				GameObject tile = Tile.getTotalTiles(level, selectedTile.X / 50, selectedTile.Y / 50);
				if (tile != null && !tile.tag.equals(String.valueOf(Tile.bedrock))) {
					if (tile != null) {
						Particle p = new Particle(selectedTile.X, selectedTile.Y, 10, 15, getPanel(), tile.getImage());
					}
					Tile.setTile(level, getPanel(), selectedTile.X / 50, selectedTile.Y / 50, 0, false);
				}
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

}
class FPSCounter extends Thread{
    private long lastTime;
    private double fps; //could be int or long for integer values
    private int calc = 0;
    public FPSCounter() {
    	lastTime = System.nanoTime();
    }

    public void run(){
        while (true){//lazy me, add a condition for an finishable thread
            
            if (System.nanoTime() > lastTime+1000) {
                fps = calc;
                calc = 0;
                lastTime = System.nanoTime();
            }
            calc++;
        }
        
    }
    public double fps(){
        return fps;
    } 
}