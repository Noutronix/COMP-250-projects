package assignment1;

public class FireBee extends HoneyBee {
	private int maxRange;
	public static int BASE_HEALTH;
	public static int BASE_COST;
	
	public FireBee(Tile tile, int mR) {
		super(tile, BASE_HEALTH, BASE_COST);
		this.maxRange = mR;
		
	}
	
	public boolean takeAction() {
		if (!this.getPosition().isOnThePath()) {
			return false;
		}
		
		Tile t = this.getPosition().towardTheNest();
		
		for (int i=0; i<this.maxRange; i++) {
			if (t.isOnFire()||t.getNumOfHornets()==0) {
				t = t.towardTheNest();
				continue;
			}
			else if (t.isNest()) {
				return false;
			}
			else {
				t.setOnFire();
				return true;
			}
		}
		
		return false;
		
	}
}
