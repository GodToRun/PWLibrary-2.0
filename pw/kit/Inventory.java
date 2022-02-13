package pw.kit;
import java.util.*;
public class Inventory {
	public final static String WOOD = "wood";
	public static final String WATER = "water";
	public static final String STONE = "stone";
	public static final String GRASS = "grass";
	public static final String LEAVE = "leave";
	public static final String COAL = "coal";
	public static final String IRON = "iron";
	public static final String DIAMOND = "diamond";
	public static final String SAND = "sand";
	private Slot CurrentSlot = null;
	public static ArrayList<Slot> slots = new ArrayList<Slot>();
	public void setCurrentSlot(Slot slot) {
		this.CurrentSlot = slot;
	}
	public Slot getCurrentSlot() {
		return this.CurrentSlot;
	}
}
