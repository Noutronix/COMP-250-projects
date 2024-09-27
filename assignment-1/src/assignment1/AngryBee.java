package assignment1;

public class AngryBee extends HoneyBee {
	private int atk;
	public static int BASE_HEALTH;
	public static int BASE_COST;
	
	public AngryBee(Tile tile, int atk) {
		super(tile, BASE_HEALTH, BASE_COST);
		this.atk = atk;
	}
	
	public boolean takeAction() {
		Tile t = this.getPosition();
		if (!t.isOnThePath()) {
			return false;
		}
		else if (t.getHornet() != null) {
			t.getHornet().takeDamage(this.atk);
			return true;
		}
		else if (t.towardTheNest().getHornet() != null) {
			t.towardTheNest().getHornet().takeDamage(this.atk);
			return true;
		}
		return false;
	}
}
