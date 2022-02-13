package pw.kit;

public class Slot {
	public int itemCount = 0;
	private String item = null;
	public Slot() {
		Inventory.slots.add(this);
	}
	public void setItem(String item,int count) {
		this.itemCount = count;
		this.item = item;
	}
	public String getItem() {
		return this.item;
	}
	public void SubstractCount(int i) {
		if (itemCount > 0)
			itemCount -= i;
		else
			this.item = null;
		if (itemCount < 1)
			this.item = null;
	}
	public void AddCount(int i) {
		itemCount += i;
	}
}
