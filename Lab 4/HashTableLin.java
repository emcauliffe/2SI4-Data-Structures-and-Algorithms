import java.util.InputMismatchException;

public class HashTableLin{
	private Integer[] table;
	private int sizeOfTable;
	private int numOfElems;
	private double maxLoadFactor;

	//SEE QUADRATIC CLASS FOR MORE DETAILED COMMENTS

	public HashTableLin(int maxNum, double load) throws InputMismatchException{ //O(n^2)

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

	public void insert(int n){//O(n)

		double newLoadFactor = ((double) numOfElems + 1) / (double) getSizeOfTable();
		if (newLoadFactor > maxLoadFactor){
			rehash();
		}

		int h = Math.abs(n%getSizeOfTable());
		int linearCollisionFactor = 0;
		int index = h;
		boolean present = false;

		while(getTable()[index] != null) {
			if (getTable()[index] == n) {
				present = true;
				break;
			}
			linearCollisionFactor++;
			index = (h+linearCollisionFactor)%getSizeOfTable();
		}

		if (present == false){
			table[index] = n;
			numOfElems++;
		}
	}

	private void rehash(){ //O(n)
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
			if (i != null) {
				insert(i);
			}
		}
	}

	public boolean isIn(int n){ //O(n)

		int h = Math.abs(n%getSizeOfTable());
		int linearFactor = 0;
		int index = h;

		while(linearFactor < getSizeOfTable() && getTable()[index] != null){
			if(getTable()[index] != null && getTable()[index] == n) {//short circuit to avoid null pointer exceptions
				return true;
			}
			linearFactor++;
			index = (h+linearFactor)%getSizeOfTable();
		}
		return false;
	}

	public void printKeys(){ //O(n)
		for (Integer i : getTable()){
			if (i != null){
				System.out.print(i + ", ");
			}
		}
		System.out.println();
	}

	public void printKeysAndIndexes(){ //O(n)
		for (int i = 0; i < getSizeOfTable(); i++){
			if (getTable()[i] != null){
				System.out.println("[" + i + "] : " + getTable()[i]);
			}
		}
	}

	public int getNumOfElems() { //O(1)
		return numOfElems;
	}

	public int getSizeOfTable() { //O(1)
		return sizeOfTable;
	}

	public Integer[] getTable() { //O(1)
		return table;
	}

	public double getMaxLoadFactor() { //O(1)
		return maxLoadFactor;
	}

	public int insertCount(int n){//O(n)

		double newLoadFactor = ((double) numOfElems + 1) / (double) getSizeOfTable();
		if (newLoadFactor > maxLoadFactor){
			rehash();
		}

		int h = Math.abs(n%getSizeOfTable());
		int linearCollisionFactor = 0;
		int index = h;
		boolean present = false;

		while(getTable()[index] != null) {
			if (getTable()[index] == n) {
				present = true;
				break;
			}
			linearCollisionFactor++;
			index = (h+linearCollisionFactor)%getSizeOfTable();
		}

		if (present == false){
			table[index] = n;
			numOfElems++;
			return linearCollisionFactor+1;
		}
		return -1;
	}

	public int isInCount(int n){ //O(n)

		int h = Math.abs(n%getSizeOfTable());
		int linearFactor = 0;
		int index = h;

		while(linearFactor < getSizeOfTable() && getTable()[index] != null){
			if(getTable()[index] != null && getTable()[index] == n) {//short circuit to avoid null pointer exceptions
				return -1;
			}
			linearFactor++;
			index = (h+linearFactor)%getSizeOfTable();
		}
		return linearFactor+1;
	}

	private static boolean isPrime(int n){//O(n)
		if (n <= 1){
			return false;
		}
		for (int i = 2; i < n; i++) {
			if (n%i == 0){
				return false;
			}
		}
		return true;
	}
}