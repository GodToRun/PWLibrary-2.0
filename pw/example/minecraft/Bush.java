package pw.example.minecraft;

public class Bush extends Tile {
	public Bush(int id, String tex) {
		super(id, tex);
	}
	@Override
	public boolean isSolid() {
		return false;
	}
}
