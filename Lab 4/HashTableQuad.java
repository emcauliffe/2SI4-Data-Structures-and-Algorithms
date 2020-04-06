import java.util.InputMismatchException;

public class HashTableQuad{
	private Integer[] table;
	private int sizeOfTable;
	private int numOfElems;
	private double maxLoadFactor;

	public HashTableQuad(int maxNum, double load) throws InputMismatchException{

		if (maxNum < 1 || load <= 0 || load > 1) {
			throw new InputMismatchException("Please make sure maxNum is at least 1 or load is between 0 and 1");
		}

		int smallestSize = (int) Math.ceil(maxNum/load);

		int primeSize = (smallestSize != 2 && smallestSize % 2 == 0 ? smallestSize + 1 : smallestSize); //makes primeSize odd (unless it's 2)
		while(isPrime(primeSize) == false){
			primeSize += 2;//skip even numbers
		}

		table = new Integer[primeSize];
		sizeOfTable = primeSize;
		numOfElems = 0;
		maxLoadFactor = load;
		
	}

	public void insert(int n){
		double newLoadFactor = ((double) numOfElems + 1) / (double) getSizeOfTable(); //rehash if adding an element goes over the maxLoad factor
		if (newLoadFactor > maxLoadFactor){
			rehash();
		}

		int h = Math.abs(n%getSizeOfTable());
		int quadraticCollisionFactor = 0;
		int index = h;
		boolean present = false;

		while(getTable()[index] != null) { //can only insert at a null value
			if (getTable()[index] == n) {//if the element is already there we don't need to add it
				present = true;
				break;
			}
			quadraticCollisionFactor++;
			index = (h+quadraticCollisionFactor*quadraticCollisionFactor)%getSizeOfTable(); //quadratic scaling means multiplying the collision factor by itself
		}

		if (present == false){ //only add if the element is not yet inserted
			table[index] = n;
			numOfElems++;
		}
	}

	private void rehash(){
		int smallestSize = getSizeOfTable() * 2;

		int primeSize = smallestSize + 1; //make primeSize odd
		while(isPrime(primeSize) == false){
			primeSize += 2;
		}

		Integer[] newTable = new Integer[primeSize];
		Integer[] oldTable = getTable();

		table = newTable;
		sizeOfTable = primeSize;
		numOfElems = 0;
		
		for (Integer i : oldTable) {
			if (i != null) {//for every element in the old table that's not null, insert it into the new table
				insert(i);
			}
		}
	}

	public boolean isIn(int n){
		int h = Math.abs(n%getSizeOfTable());
		int quadraticFactor = 0;
		int index = h;

		while(quadraticFactor < getSizeOfTable() && getTable()[index] != null){ // stop when we've run out of indexes to check or the index where our value should be is null
			if(getTable()[index] != null && getTable()[index] == n) {//short circuit to avoid null pointer exceptions
				return true;
			}
			quadraticFactor++;
			index = (h+quadraticFactor*quadraticFactor)%getSizeOfTable();
		}
		return false;
	}

	public void printKeys(){
		for (Integer i : getTable()){
			if (i != null){
				System.out.print(i + ", ");//print every key value followed by a comma
			}
		}
		System.out.println();
	}

	public void printKeysAndIndexes(){
		for (int i = 0; i < getSizeOfTable(); i++){
			if (getTable()[i] != null){
				System.out.println("[" + i + "] : " + getTable()[i]); //print the index followed by the key
			}
		}
	}

	public int getNumOfElems() {
		return numOfElems;
	}

	public int getSizeOfTable() {
		return sizeOfTable;
	}

	public Integer[] getTable() {
		return table;
	}

	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}

	public int insertCount(int n){//same as insert but returns number of probes
		double newLoadFactor = ((double) numOfElems + 1) / (double) getSizeOfTable();
		if (newLoadFactor > maxLoadFactor){
			rehash();
		}

		int h = Math.abs(n%getSizeOfTable());
		int quadraticCollisionFactor = 0;
		int index = h;
		boolean present = false;

		while(getTable()[index] != null) {
			if (getTable()[index] == n) {
				present = true;
				break;
			}
			quadraticCollisionFactor++;
			index = (h+quadraticCollisionFactor*quadraticCollisionFactor)%getSizeOfTable();
		}

		if (present == false){
			table[index] = n;
			numOfElems++;
			return quadraticCollisionFactor+1;//number of probes will be the same as the sqrt(collision factor), however we only store the sqrt of this number
			//add one to include the probe required to store it
		}
		return -1;//return -1 if the value is already there
	}

	private static boolean isPrime(int n){
		if (n <= 1){
			return false;
		}

		for (int i = 2; i < n; i++) {//check all numbers less than 
			if (n%i == 0){
				return false;
			}
		}
		return true;
	}
}