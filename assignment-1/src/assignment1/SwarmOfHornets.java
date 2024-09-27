package assignment1;

public class SwarmOfHornets {
	private Hornet[] hornets;
	private int size;
	public static double QUEEN_BOOST;
	
	public SwarmOfHornets() {
		this.hornets = new Hornet[10];
		this.size = 0;
	}
	
	public int sizeOfSwarm() {
		return this.size;
	}
	
	public Hornet[] getHornets() {
		Hornet[] rHornets = new Hornet[this.size];
		for (int i=0; i<this.size; i++) {
			rHornets[i] = this.hornets[i];
		}
		return rHornets;
		
	}
	
	public Hornet getFirstHornet() {
		return this.hornets[0]; 
	}
	
	private void resize() {
		Hornet[] hArr = new Hornet[this.size*2];
		for (int i=0; i<this.size; i++) {
			hArr[i] = this.hornets[i];
		}
		this.hornets = hArr;
	}
	
	public void addHornet(Hornet h) {
		//no idea if you're supposed to make sure there are no duplicates
		
		if (h.isTheQueen()) {
			for (Hornet x : this.hornets) {
				if (x!= null) {
					x.regenerateHealth(QUEEN_BOOST);
				}
			}
		}
		
		if (!(this.hornets[this.hornets.length-1]==null)) {
			this.resize();
		}
		this.hornets[this.size] = h;
		this.size++;
		
	}
	
	public boolean removeHornet(Hornet h) {
		for (int i=0; i<this.size; i++) {
			if (h==this.hornets[i]) {
				for (int j=i; j<this.size; j++) {
					if (j<this.hornets.length-1) {
						this.hornets[j] = this.hornets[j+1];
					}
					else {
						this.hornets[j] = null;
					}
				}
				this.size--;
				return true;
			}
		}
		
		return false;
		
	}

}

