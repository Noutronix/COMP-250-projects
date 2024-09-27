package finalproject;

import java.util.HashMap;

import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, ArrayList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does an exploration of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	
	public void crawlAndIndex(String url) throws Exception {
		this.internet.addVertex(url);
		if (!this.internet.getVisited(url)) {
			this.internet.setVisited(url, true);
			for (String v : this.parser.getLinks(url)) {
				this.crawlAndIndex(v);
				this.internet.addEdge(url, v);
			}
			ArrayList<String> words = parser.getContent(url);
			for (int i=0; i<words.size(); i++) {
				String lowercase = words.get(i).toLowerCase();
				if (!this.wordIndex.containsKey(lowercase)) {
					ArrayList<String> urls = new ArrayList<String>();
					urls.add(url);
					this.wordIndex.put(lowercase, urls);
				}
				else if (!this.wordIndex.get(lowercase).contains(url)){
					this.wordIndex.get(lowercase).add(url);
				}
			}
		}
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		
		ArrayList<Double> prevRanks = new ArrayList<Double>();
		
		for (String v: this.internet.getVertices()) {
			this.internet.setPageRank(v, 1);
			prevRanks.add(1.0);
		}
		
		while (true) {
			ArrayList<Double> newRanks = computeRanks(this.internet.getVertices());
			boolean anyLarger = false;
			for (int i=0; i<prevRanks.size(); i++) {
				if (Math.abs(prevRanks.get(i)-newRanks.get(i))>=epsilon) {
					anyLarger = true;
					break;
				}
			}
			if (!anyLarger) {
				break;
			}
			else {
				prevRanks = newRanks;
			}
			
		}
		
	}

	
	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 * 
	 * This method will probably fit in about 20 lines.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		
		
		
		ArrayList<Double> updated = new ArrayList<Double>();
		
		for (int i=0; i<vertices.size(); i++) {
			String cur = vertices.get(i);
			ArrayList<String> into = this.internet.getEdgesInto(cur);
			updated.add(0.5);
			for (int j=0; j<into.size(); j++) {
				String url = into.get(j);
				Double val = 0.5*this.internet.getPageRank(url)/this.internet.getOutDegree(url);
				updated.set(i, updated.get(i)+val);
			}
		}
		
		for (int i=0; i<vertices.size(); i++) {
			this.internet.setPageRank(vertices.get(i), updated.get(i));
		}
		
		return updated;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method will probably fit in about 10-15 lines.
	 */
	public ArrayList<String> getResults(String query) {
		
		HashMap<String, Double> results = new HashMap<String, Double>();
		ArrayList<String> urls = this.wordIndex.get(query.toLowerCase());
		
		if (urls == null) {
			return new ArrayList<String>();
		}
		
		for (int i=0; i<urls.size(); i++) {
			results.put(urls.get(i), this.internet.getPageRank(urls.get(i)));
		}
		return Sorting.fastSort(results);
	}
	
}
