
package assignment1;

public class SniperBee extends HoneyBee{
	private int atk;
	private int piercing;
	public static int BASE_HEALTH;;
	public static int BASE_COST;
	private boolean charging = true;
	
	public SniperBee(Tile tile, int atk, int p) {
		super(tile, BASE_HEALTH, BASE_COST);
		this.atk = atk;
		this.piercing = p;
	}
	
	public boolean takeAction() {
		if (!this.getPosition().isOnThePath()) {
			return false;
		}
		if (this.charging) {
			this.charging = false;
			return false;
		}
		else {
			this.charging = true;
			Tile t = this.getPosition();
			
			while (true) {
				if (t.isHive()) {
					return false;
				}
				if (t.getNumOfHornets() == 0) {
					t = t.towardTheNest();
				}
				else {
					int num = Math.min(t.getNumOfHornets(), this.piercing);
					Hornet[] h_arr = t.getHornets();
					for (int i=0; i<num; i++) {
						h_arr[i].takeDamage(this.atk);
					}
					return true;
				}
			}
		}
	}
}

