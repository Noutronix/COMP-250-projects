package assignment1;

public abstract class Insect {
	
	private Tile tile;
	private int hp;
	
	public Insect(Tile tile, int hp) {
		this.tile = tile;
		this.hp = hp;	
		
		if (this.tile != null) {
			if (!this.tile.addInsect(this)) {
				throw new IllegalArgumentException("Insect added incorrectly.");
			}
		}
		
	}
	
	
	public final Tile getPosition() {
		return this.tile;
	}
	
	
	public final int getHealth() {
		return this.hp;
	}
	
	
	 public void setPosition(Tile tile){
		 this.tile = tile;
	 }
	 
	 public void takeDamage(int dmg) {
		 this.hp -= dmg;
		 if (this.hp <= 0) {
			 this.tile.removeInsect(this);
		 }
		 
	 }
	 
	 public abstract boolean takeAction();
	 
	 public boolean equals(Object obj) {
		 if (obj.getClass()==this.getClass()) {
			 if (((Insect)obj).getHealth() == this.hp) {
				 if (((Insect)obj).getPosition() == this.tile) {
					 return true;
				 }
			 }
		 }
		 return false;
		 
	 }
	 
	 public void regenerateHealth(double percent) {
		 this.hp = (int)(this.hp*(1+percent));
	 }
	 
}
