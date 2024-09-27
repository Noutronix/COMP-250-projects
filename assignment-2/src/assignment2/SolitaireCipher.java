package assignment2;

public class SolitaireCipher {
	public Deck key;

	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}

	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		int[] ks = new int[size];
		for(int i=0; i<size; i++) {
			ks[i] = this.key.generateNextKeystreamValue();
		}
		return ks;
	}

	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		msg = msg.toUpperCase();
		msg = msg.replaceAll("[^a-zA-Z]", "");
		char[] returnMsg = msg.toCharArray();
		int [] keystream = this.getKeystream(msg.length());
		
		for (int i=0; i<msg.length(); i++) {
			int shifted = returnMsg[i]+keystream[i]-65;
			shifted = shifted%26 + 65;
			
			returnMsg[i] = (char)shifted;
		}
		
		return new String(returnMsg);
	}

	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		char[] returnMsg = msg.toCharArray();
		int [] keystream = this.getKeystream(msg.length());
		for (int i=0; i<msg.length(); i++) {
			int shifted = returnMsg[i]-keystream[i]-39;
			shifted = shifted%26 + 65;
			
			returnMsg[i] = (char)shifted;
		}
		return new String(returnMsg);
		
		
	}

}
