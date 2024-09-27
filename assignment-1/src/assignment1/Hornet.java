package assignment1;

public class Hornet extends Insect {
	
	private int atk;
	public static int BASE_FIRE_DMG;
	private boolean isQueen = false;
	private static int numQueens = 0;
	
	
	public Hornet(Tile tile, int hp, int atk) {
		super(tile, hp);
		this.atk = atk;
	}
	
	public boolean takeAction() {
		
		int num = 1;
		if (this.isQueen) {
			num++;
		}
		
		for (int i=0; i<num; i++) {
			if (this.getPosition().isOnFire()) { 
				this.takeDamage(BASE_FIRE_DMG);
				if (this.getHealth() <= 0) {
					return false;
				}
			}
			
			Tile t = this.getPosition();
			if (t.isHive()) {
				return false;
			}
			
			if (t.getBee() != null) {
				t.getBee().takeDamage(this.atk);
			}
			else {
				t.removeInsect(this);
				t.towardTheHive().addInsect(this);
			}
		}
		return true;
		
	}
	
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (this.atk == ((Hornet)obj).atk){
				return true;
			}
		}
		return false;
	}
	
	public boolean isTheQueen() {
		return this.isQueen;
	}
	
	//this might not be what they're asking for :(
	public void promote() {
		if (numQueens == 0) {
			this.isQueen = true;
			numQueens = 1;
		}
	}

	
	
	public static void main(String[] args) {
		Tile t = new Tile();
		Hornet jan = new Hornet(t, 5, 5);
		Hornet cindy = new Hornet(t, 5, 5);
		System.out.println(jan.equals(cindy));
		
	}
}


