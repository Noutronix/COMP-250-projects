package assignment2;

import assignment2.Deck.Card;

private Card[] getCards() {
	Card[] cards = new Card[this.numOfCards];
	Card currentCard = this.head;
	for (int i=0; i<this.numOfCards; i++) {
		cards[i] = currentCard;
		currentCard = currentCard.next;
	}
	return cards;
}

public class stuff {
	private void swap(Card c1, Card c2) {
		Card tmp_next = c2.next;
		Card tmp_prev = c2.prev;
		
		if (c1.equals(c2)) {
			return;
		}
		
		else if (c1.next.equals(c2)) {
			c2.next = c1;
			c2.prev = c1.prev;
			c1.next = tmp_next;
			c1.prev = c2;
		}
		
		else if (c2.next.equals(c1)) {
			c2.next = c1.next;
			c2.prev = c1;
			c1.next = c2;
			c1.prev = tmp_prev;
		}
		
		else {
			c2.next = c1.next;
			c2.prev = c1.prev;
			c1.next = tmp_next;
			c1.prev = tmp_prev;
		}
		
		c1.next.prev = c1;
		c1.prev.next = c1;
		c2.prev.next = c2;
		c2.next.prev = c2;
	}
	
	public void shuffle() {
		// tested; seems to work, but it's a bit overcomplicated
		// there may be hidden bugs that come up later
		
		//NOT IMPLEMENTED AS DIRECTED
		
		Card[] cards = new Card[this.numOfCards];
		Card currentCard = this.head;
		
		for (int i=0; i<this.numOfCards; i++) {
			cards[i] = currentCard;
			currentCard = currentCard.next;
		}
		
		for (int i=0; i<this.numOfCards-1; i++) {
			int j = gen.nextInt(i, this.numOfCards);
			if (cards[i].equals(this.head)) {
				this.head = cards[j];
			}
			else if (cards[j].equals(this.head)) {
				this.head = cards[i];
			}
			swap(cards[i], cards[j]);
		}
	}
}

public static void main(String[] args) {
	Deck D = new Deck(5, 2);
	D.shuffle();
	Deck D1 = new Deck(5, 2);
	
	Card currentCard = D.head;
	for (int i=0; i<12; i++) {
		System.out.print(currentCard.toString()+" ");
		currentCard = currentCard.next;
	}
	System.out.print("\n");
	
	Card currentCard1 = D1.head;
	for (int i=0; i<12; i++) {
		System.out.print(currentCard1.toString()+" ");
		currentCard1 = currentCard1.next;
	}
	
	public static void main(String[] args) {
		Deck D = new Deck(5, 2);
		D.shuffle();
		D.swap(D.locateJoker("red"), D.head.prev);
		Deck D1 = new Deck(D);
		
		Card currentCard1 = D1.head;
		for (int i=0; i<12; i++) {
			System.out.print(currentCard1.toString()+" ");
			currentCard1 = currentCard1.next;
		}
		System.out.print("\n");
		Card j1 = D.locateJoker("black");
		Card j2 = D.locateJoker("red");
		D.tripleCut(j1, j2);
		
		Card currentCard = D.head;
		for (int i=0; i<12; i++) {
			System.out.print(currentCard.toString()+" ");
			currentCard = currentCard.next;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
7D AC 4D 2D KD 5D 6C 4C 7C 10C KC 3D 6D AD 10D QD 5C 8C JC JD 9C QC RJ 8D 9D 3C BJ 2C 
7D BJ AC 4D 2D KD 5D 6C 4C 7C 10C KC 3D 6D AD 10D QD 5C 8C JC JD 9C QC 8D RJ 9D 3C 2C
7D AC BJ 4D 2D KD 5D 6C 4C 7C 10C KC 3D 6D AD 10D QD 5C 8C JC JD 9C QC 8D RJ 9D 3C 2C 
	
	
	
	

