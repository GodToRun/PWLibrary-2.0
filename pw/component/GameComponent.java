package pw.component;

import pw.common.GameObject;

public abstract class GameComponent {
	public GameObject object;
	public abstract void componentLoop();
	public abstract void componentInit();
	public abstract void onActive();
	public abstract void onInactive();
	public void onDelete() {
		
	}
}
