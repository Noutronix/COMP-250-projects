package assignment1;

public class Tile {
	private int food;
	private boolean hasHive;
	private boolean hasNest;
	private boolean onPath;
	private Tile towardHive;
	private Tile towardNest;
	private HoneyBee bee;
	private SwarmOfHornets swarm;
	private boolean onFire = false;
	
	
	public Tile() {
		this.food = 0;
		this.hasHive = false;
		this.hasNest = false;
		this.onPath = false;
		this.towardHive = null;
		this.towardNest = null;
		this.swarm = new SwarmOfHornets();
	}
	
	public Tile(int food,
			boolean hasHive,
			boolean hasNest,
			boolean onPath,
			Tile towardHive,
			Tile towardNest,
			HoneyBee bee,
			SwarmOfHornets swarm) {
		
		this.food = food;
		this.hasHive = hasHive;
		this.hasNest = hasNest;
		this.onPath = onPath;
		this.towardHive = towardHive;
		this.towardNest = towardNest;
		this.swarm = swarm;
		this.addInsect(bee);
		
		//might need to replace towards with toward
		
	}
	
	
	public boolean isHive() {
		return this.hasHive;
	}
	
	public boolean isNest() {
		return this.hasNest;
	}
	
	public void buildHive() {
		this.hasHive = true;
	}
	
	public void buildNest() {
		this.hasNest = true;
	}
	
	public boolean isOnThePath() {
		return this.onPath;
	}
	
	public Tile towardTheHive() {
		return this.towardHive;
	}
	
	public Tile towardTheNest() {
		return this.towardNest;
	}
	
	public void createPath(Tile hiveni, Tile nestni) {
		//edge cases
		if (hiveni==null && nestni==null) {
			throw new IllegalArgumentException("Null was inputed as next tile for non-hive/non-nest tile.");
		}
		
		if (hiveni == null) {
			if (!this.hasHive) {
				throw new IllegalArgumentException("Null was inputed as next tile for non-hive/non-nest tile.");
			}
		}
		if (nestni == null) {
			if (!this.hasNest) {
				throw new IllegalArgumentException("Null was inputed as next tile for non-hive/non-nest tile.");
			}
		}
		
		this.towardHive = hiveni;

		this.towardNest = nestni;
		
		this.onPath = true;
		
		/*
		
		
		
		I have no idea if we need to update these fields or not :(
		This code probably doesn't work though cuz they're private fields
		
		
		
		if (hiveni != null) {
			hiveni.towardNest = this;
		}
		
		if (nestni != null) {
			nestni.towardHive = this;
		}
		*/
		
	}
	
	public int collectFood() {
		int tmp_food = this.food;
		this.food = 0;
		return tmp_food;
	}
	
	public void storeFood(int f) {
		this.food += f;
	}
	
	public int getNumOfHornets() {
		return this.swarm.sizeOfSwarm();
	}
	
	public HoneyBee getBee() {
		return this.bee;
	}
	
	public Hornet getHornet() {
		return this.swarm.getFirstHornet();
	}
	
	public Hornet[] getHornets() {
		return this.swarm.getHornets();
	}
	
	public boolean addInsect(Insect i) {
		if (i instanceof HoneyBee){
			if ((this.bee == null)&&!(this.hasNest)) {
				if (i.getPosition() != null){
					i.getPosition().removeInsect(i);
				}
				this.bee = (HoneyBee) i;
				i.setPosition(this);
				return true;
			}
		}
		
		else if (i instanceof Hornet) {
			if (this.onPath) {
				if (this.swarm==null) {
					this.swarm = new SwarmOfHornets();
				}
				this.swarm.addHornet((Hornet) i);
				i.setPosition(this);
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean removeInsect(Insect i) {
		
		if (i instanceof HoneyBee) {
			if (i==this.bee) {
				this.bee = null;
				i.setPosition(null);
				return true;
			}
		}
		
		else if (i instanceof Hornet) {
			boolean b = this.swarm.removeHornet((Hornet) i);
			if (b) {
				i.setPosition(null);
				return true;
			}
		}
		
		return false;
				
	}
	
	public void setOnFire() {
		this.onFire = true;
	}
	
	public boolean isOnFire() {
		return this.onFire;
	}
	
	
}








