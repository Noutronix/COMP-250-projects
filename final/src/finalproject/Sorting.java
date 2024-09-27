package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may (or may not) need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) < 0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(HashMap<K, V> results) {
    	ArrayList<K> urls = new ArrayList<K>();
        urls.addAll(results.keySet());
        
        return Sorting.quickSort(results, urls, 0, urls.size()-1);
    }
    
    public static <K, V extends Comparable<V>> ArrayList<K> quickSort(HashMap<K, V> results, ArrayList<K> list, int leftIndex, int rightIndex){
    	if (leftIndex<rightIndex) {
    		int i = placeAndDivide(results, list, leftIndex, rightIndex);
    		Sorting.quickSort(results, list, leftIndex, i-1);
    		Sorting.quickSort(results, list, i+1, rightIndex);
    	}
    	return list;
    }
   
    
    
    
    public static <K, V extends Comparable<V>> int placeAndDivide(HashMap<K, V> results, ArrayList<K> list, int leftIndex, int rightIndex){
    	K pivot = list.get(leftIndex+(rightIndex-leftIndex)/2);
    	int pivotIndex = leftIndex+(rightIndex-leftIndex)/2;
    	int wall = leftIndex-1;
    	for (int i=leftIndex; i<=rightIndex; i++) {
    		K cur = list.get(i);
    		if (results.get(cur).compareTo(results.get(pivot))>0) {
    			wall++;
    			if (wall==pivotIndex) {
    				pivotIndex = i;
    			}
    			K tmp = cur;
    			list.set(i, list.get(wall));
    			list.set(wall, tmp);
    		}
    	}
    	K tmp = pivot;
    	list.set(pivotIndex, list.get(wall+1));
    	list.set(wall+1, tmp);
    	return wall+1;

    }    

}

















