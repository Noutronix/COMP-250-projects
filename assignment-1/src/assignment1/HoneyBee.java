package assignment1;

public abstract class HoneyBee extends Insect {
	
	private int cost;
	public static double HIVE_DMG_REDUCTION;
	
	public HoneyBee(Tile tile, int hp, int cost) {
		super(tile, hp);
		this.cost = cost;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void takeDamage(int dmg) {
		if (this.getPosition().isHive()) {
			dmg *= (1-HIVE_DMG_REDUCTION);
		}
		super.takeDamage((int)dmg);
	}

}
