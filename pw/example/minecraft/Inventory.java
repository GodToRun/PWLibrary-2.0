package pw.example.minecraft;

public class Inventory {
	private Slot[] slots = new Slot[9];
	public int selectedSlot = 0;
	public Inventory() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new Slot();
		}
	}
	public Slot get(int i) {
		return slots[i];
	}
	public Slot getSelected() {
		return slots[selectedSlot];
	}
	public void set(int slot, int tile) {
		slots[slot].item = Tile.tiles[tile];
	}
}
