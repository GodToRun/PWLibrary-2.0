package pw.component;

import pw.common.*;
import java.math.*;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.lang.Math;

public class Physics extends GameComponent {
	public float gravity = 3f;
	float force = 0;
	public boolean isGround = false;
	ArrayList<Integer> ignoreLayers = new ArrayList<Integer>();
	private AABB aabb;
	public AABB getAABB() {
		return aabb;
	}
	public void setAABB(AABB aabb) {
		this.aabb = aabb;
	}
	public void ignoreLayerWith(int layer) {
		ignoreLayers.add(layer);
	}
	@Override
	public void componentInit() {
		this.aabb = new AABB(object.X, object.Y, object.width, object.height);
	}
	public AABB getCollisionAABBWall() {
		return collisionCheck(true);
	}
	private AABB collisionCheck(boolean isWall) {
		for (int i = 0; i < AABB.aabbs.size(); i++) {
			if (i < AABB.aabbs.size()) {
				AABB aabb = AABB.aabbs.get(i);
				if (aabb != null && !ignoreLayers.contains(aabb.layer)) {
					if (this.aabb.getRect().intersects(aabb.getRect()) && this.aabb.getRect() != aabb.getRect()) {
						if (!isWall || (isWall && aabb.getRect().y-10 < this.aabb.getRect().y))
							return aabb;
					}
				}
				else {
					continue;
				}
			}
		}
		return null;
	}
	public AABB getCollisionAABB() {
		return collisionCheck(false);
	}
	@Override
	public void componentLoop() {
		if (aabb != null) {
			Rectangle rect = this.aabb.getRect();
			this.aabb.setRect(new Rectangle(object.X, object.Y, rect.width, rect.height));
			if (gravity != 0) {
				
				AABB aabb = getCollisionAABB();
				if (aabb != null) {
					isGround = true;
				}
				else {
					isGround = false;
					if (true) {
						addForce(0, gravity, 1.7);
						force++;
					}
				}
			}
		}
	}
	@Override
	public void onDelete() {
		this.aabb.remove();
	}
	public boolean moveRelative(int x, int y) {
		object.Move(x, y);
		if (getCollisionAABBWall() != null) {
			object.Move(-x, -y);
			return false;
		}
		else {
			return true;
		}
	}
	public void addForce(float x, float y, double friction) {
		PhysicsThread thread = new PhysicsThread(this, x, y, friction);
		thread.start();
	}
	@Override
	public void onActive() {
		if (!AABB.aabbs.contains(aabb))
			AABB.aabbs.add(aabb);
	}
	@Override
	public void onInactive() {
		AABB.aabbs.remove(aabb);
	}

}
class PhysicsThread extends Thread {
	Physics physics;
	float x, y;
	double f, m = 1f, a, friction;
	public PhysicsThread(Physics physics, float x, float y, double friction) {
		this.physics = physics;
		this.x = x;
		this.y = y;
		this.friction = friction;
	}
	@Override
	public void run() {
		double acc = friction;
		if (y != 0) {
			acc *= y / Math.abs(y);
			while (true) {
				if (y > 0) {
					acc = Math.pow(acc, 1.22f);
				}
				else {
					acc *= y / Math.abs(y);
					acc = Math.pow(acc, 1.22f);
					acc *= y / Math.abs(y);
				}
				a = acc / 10;
				//F=ma
				f = m * a;
				physics.object.Move(0, (int)Math.ceil(f));
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (physics.isGround && y > 0) {
					physics.force--;
					return;
				}
				if (y < 0 && a < y) {
					physics.force--;
					return;
				}
				else if (y > 0 && a > y) {
					physics.force--;
					return;
				}
			}
		}
		if (x != 0) {
			acc *= x / Math.abs(x);
			while (true) {
				if (x > 0) {
					acc = Math.pow(acc, 1.22f);
				}
				else {
					acc *= x / Math.abs(x);
					acc = Math.pow(acc, 1.22f);
					acc *= x / Math.abs(x);
				}
				a = acc / 10;
				//F=ma
				f = m * a;
				physics.moveRelative((int)Math.ceil(f), 0);
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (x < 0 && a < x) {
					physics.force--;
					return;
				}
				else if (x > 0 && a > x) {
					physics.force--;
					return;
				}
			}
		}
	}
	public void secondFunc() {
		
	}
}