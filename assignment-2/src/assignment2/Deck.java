package assignment2;

import java.util.Random;

public class Deck {
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();

	public int numOfCards; // contains the total number of cards in the deck
	public Card head; // contains a pointer to the card on the top of the deck
	
	private Card[] createCards(int numOfCardsPerSuit, int numOfSuits) {
		// creates an array of cards to be converted into a doubly linked list (helper method)
		Card[] cards = new Card[this.numOfCards];
		for (int i=0; i<numOfSuits; i++) {
			String CURRENT_SUIT = suitsInOrder[i];
			for (int j=0; j<numOfCardsPerSuit; j++) {
				int CURRENT_RANK = j+1;
				int INDEX = i*numOfCardsPerSuit+j;
				cards[INDEX] = new PlayingCard(CURRENT_SUIT, CURRENT_RANK);
			}
		}
		
		cards[this.numOfCards-2] = new Joker("red");
		cards[this.numOfCards-1] = new Joker("black");
		
		return cards;
	}
	
	private void createCircularList(Card[] cards) {
		//runs in O(n); it links up every card to its neighbours (helper method)
		for (int i=0; i<this.numOfCards; i++) {
			if (i == this.numOfCards-1) {
				cards[i].next = cards[0];
				cards[i].prev = cards[i-1];
			}
			
			else if (i==0) {
				cards[i].next = cards[i+1];
				cards[i].prev = cards[this.numOfCards-1];
			}
			
			else {
				cards[i].next = cards[i+1];
				cards[i].prev = cards[i-1];
			}
		}
	}
	
	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		//tested 
		if (!(numOfCardsPerSuit<=13)||!(numOfCardsPerSuit>=1)) {
			throw new IllegalArgumentException(); 
		}
		if (!(numOfSuits>=1)||!(numOfSuits<=suitsInOrder.length)) {
			throw new IllegalArgumentException(); 
		}
		
		this.numOfCards = numOfCardsPerSuit*numOfSuits+2;
		
		Card[] cards = this.createCards(numOfCardsPerSuit, numOfSuits);
		
		this.head = cards[0];
		this.createCircularList(cards);
		
		// maybe assert that there are no null cards in the list? 
		// that would be good for debugging
	}
	

	/* 
	 * TODO: Implements a copy constructor for Deck using Card.getCopy().
	 * This method runs in O(n), where n is the number of cards in d.
	 */
	public Deck(Deck d) {
		//tested 
		
		this.numOfCards = d.numOfCards;
		
		Card[] cards = new Card[d.numOfCards];
		Card currentCard = d.head;
		
		for (int i=0; i<d.numOfCards; i++) {
			cards[i] = currentCard.getCopy();
			currentCard = currentCard.next;
		}
		
		this.head = cards[0];
		this.createCircularList(cards);
	}

	public Deck() {
		this.numOfCards = 0;
	} //For testing purposes we need a default constructor.
	// prob doesn't need changing
	

	/* 
	 * TODO: Adds the specified card at the bottom of the deck. This 
	 * method runs in O(1). 
	 */
	public void addCard(Card c) {
		// untested
		if (this.numOfCards==0) {
			this.head = c;
			Deck.bind(c, c);
			
		}
		else {
			Card last = this.head.prev;
			Deck.bind(last, c);
			Deck.bind(c, this.head);
		}
		this.numOfCards += 1;
	}

	/*
	 * TODO: Shuffles the deck using the algorithm described in the pdf. 
	 * This method runs in O(n) and uses O(n) space, where n is the total 
	 * number of cards in the deck.
	 */
	public void shuffle() {
		//tested and less verbose than original (yay)
		//*****TODO:HAS PROBLEMS*****
		
		if (this.numOfCards <= 1) {
			return;
		}
		Card[] cards = new Card[this.numOfCards];
		Card currentCard = this.head;
		
		//creating array
		for (int i=0; i<this.numOfCards; i++) {
			cards[i] = currentCard;
			currentCard = currentCard.next;
		}
		
		// shuffling array
		for (int i=this.numOfCards-1; i>0; i--) {
			int j = gen.nextInt(i+1);
			Card tmp = cards[i];
			cards[i] = cards[j];
			cards[j] = tmp;
		}
		
		//converting to doubly linked list
		this.createCircularList(cards);
		this.head = cards[0];
		
	}
	
	/*
	 * TODO: Returns a reference to the joker with the specified color in 
	 * the deck. This method runs in O(n), where n is the total number of 
	 * cards in the deck. 
	 */
	public Joker locateJoker(String color) {
		// partially tested
		Card currentCard = this.head;
		for (int i=0; i<this.numOfCards; i++) {
			if (currentCard instanceof Joker) {
				if (((Joker) currentCard).getColor().equalsIgnoreCase(color)) {
					return ((Joker) currentCard);
				}
			}
			currentCard = currentCard.next;
		}
		return null;
	}

	/*
	 * TODO: Moved the specified Card, p positions down the deck. You can 
	 * assume that the input Card does belong to the deck (hence the deck is
	 * not empty). This method runs in O(p).
	 */
	public void moveCard(Card c, int p) {
		// tested
		for (int i=0; i<p; i++) {
			Card backward = c.prev;
			Card forward = c.next;
			Card twoForward = c.next.next;
			
			Deck.bind(backward, forward);
			Deck.bind(forward, c);
			Deck.bind(c, twoForward);
			//no need to put c = c.next, because the relevant card never changes
		}
	}
	
	
	public String toString() {
		String returnString = "";
		Card currentCard = this.head;
		for (int i=0; i<this.numOfCards; i++) {
			returnString += (currentCard.toString()+" ");
			currentCard = currentCard.next;
		}
		return returnString;
	}
	
	

	
	/*
	 * TODO: Performs a triple cut on the deck using the two input cards. You 
	 * can assume that the input cards belong to the deck and the first one is 
	 * nearest to the top of the deck. This method runs in O(1)
	 */
	
	public void tripleCut(Card firstCard, Card secondCard) {
		// tested; should work
		// NEEDS TO CHECK WHICH JOKER COMES FIRST
		
		// initializing relevant nodes
		Card start = this.head;
		Card break1 = firstCard.prev;
		Card break2 = secondCard.next;
		Card end = this.head.prev;
	
		// edge cases
		if (secondCard.equals(end)) {
			this.head = firstCard;
			return;
		}
		if (firstCard.equals(this.head)) {
			this.head = break2;
			return;
		}
		
		// actual triple cut section
		Deck.bind(secondCard, start);
		Deck.bind(end, firstCard);		
		Deck.bind(break1, break2);
		this.head = break2;
	}
	
	
	/*
	 * TODO: Performs a count cut on the deck. Note that if the value of the 
	 * bottom card is equal to a multiple of the number of cards in the deck, 
	 * then the method should not do anything. This method runs in O(n).
	 */
	private static void bind(Card card1, Card card2) {
		// helper method that links two cards together (super useful)
		card1.next = card2;
		card2.prev = card1;
	}
	
	
	public void countCut() {
		// tested
		// most of these methods don't account for numOfCards zero, but it's a simple fix
		
		int num = this.head.prev.getValue()%this.numOfCards;
		
		if (num == this.numOfCards-1||num==0){
			return;
		}
		
		Card currentCard = this.head;
		for (int i=0; i<num-1; i++) {
			currentCard = currentCard.next;
		}
		
		Card last = this.head.prev; 
		Card secondLast = last.prev; 
		Card first = this.head; 
		this.head = currentCard.next;
		
		Deck.bind(currentCard, last);
		Deck.bind(secondLast, first);
		Deck.bind(last, this.head);
	
	}

	/*
	 * TODO: Returns the card that can be found by looking at the value of the 
	 * card on the top of the deck, and counting down that many cards. If the 
	 * card found is a Joker, then the method returns null, otherwise it returns
	 * the Card found. This method runs in O(n).
	 */
	public Card lookUpCard() {
		// untested
		
		int num = (this.head.getValue())%this.numOfCards;
		Card currentCard = this.head;
		for (int i=0; i<num; i++) {
			currentCard = currentCard.next;
		}
		
		if (currentCard instanceof Joker) {
			return null;
		}
		else {
			return currentCard;
		}		
		
	}

	/*
	 * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
	 * using this deck. This method runs in O(n).
	 */
	public int generateNextKeystreamValue() {
		
		// worked at least once!! not throwing errors
		
		//step 1: move red joker down 1, passing the head
		Joker ron = this.locateJoker("red");
		this.moveCard(ron, 1);
		
		//step 2: move the black joker down 2, also passing the head
		Joker blake = this.locateJoker("black");
		this.moveCard(blake, 2);
		
		//step 3: executing a triple cut
		Joker firstJoker = null;
		Joker secondJoker = null;
		Card currentCard = this.head;
		for (int i=0; i<this.numOfCards; i++) {
			if (currentCard instanceof PlayingCard) {
				currentCard = currentCard.next;
				continue;
			}
			if (firstJoker == null) {
				firstJoker = (Joker)currentCard;
				currentCard = currentCard.next;
			}
			else {
				secondJoker = (Joker)currentCard;
				break;
			}
		}
		this.tripleCut(firstJoker, secondJoker);
		
		// step 4: performing a count cut
		
		this.countCut();
		
		//step 5: generating the value
		Card returnCard = this.lookUpCard();
		if (returnCard == null) {
			return this.generateNextKeystreamValue();
		}
		else {
			return returnCard.getValue();
		}		
	}

	public abstract class Card { 
		public Card next;
		public Card prev;

		public abstract Card getCopy();
		public abstract int getValue();

	}

	public class PlayingCard extends Card {
		public String suit;
		public int rank;

		public PlayingCard(String s, int r) {
			this.suit = s.toLowerCase();
			this.rank = r;
		}

		public String toString() {
			String info = "";
			if (this.rank == 1) {
				//info += "Ace";
				info += "A";
			} else if (this.rank > 10) {
				String[] cards = {"Jack", "Queen", "King"};
				//info += cards[this.rank - 11];
				info += cards[this.rank - 11].charAt(0);
			} else {
				info += this.rank;
			}
			//info += " of " + this.suit;
			info = (info + this.suit.charAt(0)).toUpperCase();
			return info;
		}

		public PlayingCard getCopy() {
			return new PlayingCard(this.suit, this.rank);   
		}

		public int getValue() {
			int i;
			for (i = 0; i < suitsInOrder.length; i++) {
				if (this.suit.equals(suitsInOrder[i]))
					break;
			}

			return this.rank + 13*i;
		}

	}

	public class Joker extends Card{
		public String redOrBlack;

		public Joker(String c) {
			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
				throw new IllegalArgumentException("Jokers can only be red or black"); 

			this.redOrBlack = c.toLowerCase();
		}

		public String toString() {
			//return this.redOrBlack + " Joker";
			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
		}

		public Joker getCopy() {
			return new Joker(this.redOrBlack);
		}

		public int getValue() {
			return numOfCards - 1;
		}

		public String getColor() {
			return this.redOrBlack;
		}
	}

}
