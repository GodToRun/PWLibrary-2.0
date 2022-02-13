package pw.example.minecraft;
import pw.common.*;
import pw.component.Physics;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
public class Particle extends Entity {
	public static final int LAYER = 3;
	ArrayList<GameObject> particles = new ArrayList<GameObject>();
	public Particle(int x, int y, int emission, int particleRadius, JPanel panel, Image image) {
		Random random = new Random();
		for (int i = 0; i < emission; i++) {
			Point coord = new Point(random.nextInt(particleRadius) + x, random.nextInt(particleRadius) + y);
			GameObject obj = new GameObject(coord.x, coord.y, 8, 8, ObjectType.Image, panel, 65);
			int dir;
			if (random.nextBoolean()) {
				dir = 12;
			}
			else {
				dir = -12;
			}
			obj.setImage(image);
			Physics physics = new Physics();
			physics.addForce(dir, 0, 9f);
			obj.addComponent(physics);
			physics.getAABB().layer = LAYER;
			physics.ignoreLayerWith(LAYER);
			physics.ignoreLayerWith(Minecraft.PLAYER_LAYER);
		}
	}
}
