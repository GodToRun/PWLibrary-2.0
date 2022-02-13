package pw.common;
import java.awt.Rectangle;
import java.util.*;
public class AABB {
	private Rectangle rect;
	public int layer = 0;
	public static ArrayList<AABB> aabbs = new ArrayList<AABB>();
	public AABB(int x, int y, int width, int height) {
		aabbs.add(this);
		rect = new Rectangle(x, y, width, height);
	}
	public void remove() {
		aabbs.remove(this);
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	public Rectangle getRect() {
		return this.rect;
	}
}
