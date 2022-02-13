package pw.common;
import javax.imageio.ImageIO;
import javax.swing.*;

import pw.common.Camera;
import pw.component.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class GameObject {
	private ArrayList<GameComponent> compolist = new ArrayList<GameComponent>();
	public int X;
	public int Y;
	public boolean moveable = true;
	public int width;
	public String tag = this.toString();
	public int height;
	public boolean ShowWhenPaint = false;
	public ObjectType objtype;
	private Image image;
	Graphics graphic;
	public boolean IsGround = false;
	private boolean active = true;
	int dispawnTick = 0;
	int dispawnTickTarget = -1;
	private String text = "";
	private Font font = new Font(Font.SERIF,0,25);
	public Color color;
	public Color shadowDepthColor = new Color(0, 0, 0, 0);
	JPanel panel;
	public GameObject(int x, int y, int width, int height, ObjectType type, JPanel panel) {
		this(x, y, width, height, type, panel, -1);
	}
	public GameObject(int x,int y,int width,int height,ObjectType type, JPanel panel, int dispawnTick) {
		dispawnTickTarget = dispawnTick;
		Camera.Objects.add(this);
		this.width = width;
		this.height = height;
		X = x;
		Y = y;
		this.panel = panel;
		objtype = type;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		if (active) {
			if (!Camera.Objects.contains(this)) {
				Camera.Objects.add(this);
				for (GameComponent com : compolist) {
					com.onActive();
				}
			}
		}
		else {
			Camera.Objects.remove(this);
			for (GameComponent com : compolist) {
				com.onInactive();
			}
		}
		this.active = active;
	}
	public void addComponent(GameComponent component) {
		this.compolist.add(component);
		component.object = this;
		component.componentInit();
	}
	public ArrayList<GameComponent> getComponentList() {
		return this.compolist;
	}
	public void removeComponent(GameComponent component) {
		this.compolist.remove(component);
	}
	public static void ObjectsShow(Graphics g) {
		for (GameObject obj : Camera.Objects) {
			if (obj.ShowWhenPaint)
				obj.Show(g);
		}
	}
	public static GameObject FindObjectWithTag(String tag) {
		for (GameObject OBJ : Camera.Objects) {
			if (OBJ.tag.equals(tag))
				return OBJ;
		}
		return null;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Font getFont() {
		return this.font;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void setImage(String path) throws IOException {
		this.image = ImageIO.read(new File(path));
	}
	public Image getImage() {
		return this.image;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return this.text;
	}
	public void Delete() {
		try {
			for (int i = 0; i < compolist.size(); i++) {
				if (compolist.size() > 0 && i < compolist.size() && compolist.get(i) != null)
					compolist.get(i).onDelete();
			}
		} catch (Exception e) { }
		compolist.clear();
		Camera.Objects.remove(this);
	}
	//overloading
	public GameObject(int x,int y,int width,int height,ObjectType type, JPanel panel, Color color) {
		this(x, y, width, height, type, panel);
		this.color = color;
	}
	public <T> GameComponent getComponent(T type) {
		for (GameComponent c : compolist) {
			if (c.getClass() == type.getClass()) {
				return c;
			}
		}
		return null;
	}
	public void Show(Graphics g) {
		int rx = X + Camera.X;
		int ry = Y + Camera.Y;
		graphic = g;
		if (objtype == ObjectType.Cube) {
			g.setColor(color);
			g.fillRect(rx, ry, width, height);
		}
		else if (objtype == ObjectType.Text) {
 			g.setColor(color);
			g.setFont(font);
			g.drawString(text,rx,ry);
		}
		else if (objtype == ObjectType.Image) {
			g.setColor(color);
			if (image != null) {
				//rect.setBounds(rx,ry,image.getWidth(panel),image.getHeight(panel));
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage(image, rx, ry, width, height, panel);
			}
		}
	}
	private static Color darker(Color c, double scale) {
	    int r = Math.min(0, (int) (c.getRed() * scale));
	    int g = Math.min(0, (int) (c.getGreen() * scale));
	    int b = Math.min(0, (int) (c.getBlue() * scale));
	    return new Color(r,g,b);
	}
	public void Move(int x,int y) { //움직임. (x,y)만큼
		if (moveable) {
			this.X = X + x;
			this.Y = Y + y;
		}
	}
	public void Move(int x,int y,boolean ForceMode) { //움직임. (x,y)만큼
			this.X = X + x;
			this.Y = Y + y;
	}
	public void SetPosition(int x,int y) { //움직임. (x,y)만큼
		if (moveable) {
			this.X = x;
			this.Y = y;
		}
	}
	public static GameObject getObjectWithPoint(Point point, boolean X_AND_Y) {
		for (GameObject obj : Camera.Objects) {
			if (X_AND_Y) {
				if (obj.X == point.x && obj.Y == point.y)
					return obj;
			}
			else {
				if (obj.X == point.x || obj.Y == point.y)
					return obj;
			}
		}
		return null;
	}
	public static GameObject getObjectWithRadius(Point point,int radiusx,int radiusy, boolean X_AND_Y) {
		for (GameObject obj : Camera.Objects) {
			if (X_AND_Y) {
				if (obj.X > point.x - radiusx && obj.X < point.x + radiusx &&obj.Y > point.y - radiusy && obj.Y < point.y + radiusy)
					return obj;
			}
			else {
				if (obj.X == point.x || obj.Y == point.y)
					return obj;
			}
		}
		return null;
	}
	public static void SetAllObjectsDefaultSetting() {
		LoopObject LO = new LoopObject();
		LO.start();
	}
	public void RefreshTempMove(int X,int Y,Graphics g) {
		panel.repaint();
		if (objtype == ObjectType.Cube) {
			g.fillRect(X, Y, width, height);
		}
	}
	public void Loop() {
		
	}
	static class LoopObject extends Thread {
		@Override
		public void run() {
			try {
				while(true) {
					for (int i = 0; i < Camera.Objects.size(); i++) {
						if (i < Camera.Objects.size()) {
							try {
								GameObject go = Camera.Objects.get(i);
								if (go != null) {
									go.Loop(); //reference
									go.dispawnTick++;
									
										for (int ci = 0; ci < go.compolist.size(); ci++)
											go.compolist.get(ci).componentLoop();
									
									if (go.dispawnTickTarget != -1 && go.dispawnTick >= go.dispawnTickTarget) {
										go.Delete();
									}
								}
							} catch(Exception e) { }
						}
					}
					Thread.sleep(20);
				}
			}
			catch (Exception e) {e.printStackTrace();}
		}
	}
}
