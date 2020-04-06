public class BSTSet {
	private TNode root;

	public BSTSet(){ //O(1)
		root = null;
	}

	public BSTSet(int[] input){ //O(n^2), Space: O(n)

		input = bubbleSort(input); //sort array smallest to largest
		root = smallestHeightTree(input, 0, input.length-1); //call method to generate smallest height tree
		
	}

	public boolean isIn(int v){ // O(n), Space: O(n)

		if (root == null){  //null set contains nothing
			return false;
		}

		TNode iterator = root; //set up iterator so root doesn't get changed
		while (true) {
			if (v == iterator.element) {
				return true; //return true if input is present
			} else if (v > iterator.element) {//if the input is larger than node, move right
				if (iterator.right == null) { //if at a leaf, the value is not present
					return false; 
				} else {
					iterator = iterator.right;
				}
			} else if ( v < iterator.element) { //same as above, but move left if value is smaller
				if (iterator.left == null) {
					return false;
				} else {
					iterator = iterator.left;
				}
			}
		}
	}

	public void add(int v){ //O(n), Space: O(n)

		if ( root == null) {
			root = new TNode(v, null, null); //set-up root if the initial set is empty
		} else {
			TNode iterator = root;
			while (true) {
				if (v == iterator.element) { //if the element is already in the set, do nothing and break
					break;
				} else if (v > iterator.element) { //if the input is larger than the current node, look right
					if (iterator.right == null) {
						iterator.right = new TNode(v, null, null); //if at the end of a branch, add on the right and break
						break;
					} else {
						iterator = iterator.right; //otherwise move right
					}
				} else if ( v < iterator.element) { //if the input is smaller than current node, look left
					if (iterator.left == null) {
						iterator.left = new TNode(v, null, null); //add to the left and break
						break;
					} else {
						iterator = iterator.left; //otherwise move left
					}
				}
			}
		}
	}

	public boolean remove(int v){ //O(n), Space: O(n)

		if (root == null) { //can't remove from empty set
			return false;
		}

		TNode iterator = root;
		while (true) {
			if (iterator.right != null && v == iterator.right.element) { //if the right node is our value
				switch (nodeChildren(iterator.right)) { //use children method to determine how many children/where the children are
					case 0: //no children, chop off the node
						iterator.right = null;
						break;
					case -1: //one left child, point to it
						iterator.right = iterator.right.left;
						break;
					case 1: //one right child, point to it
						iterator.right = iterator.right.right;
						break;
					case 2: // if we have two children
						TNode jiterator = iterator.right;
						while(jiterator.left != null){
							jiterator = jiterator.left;
						}
						int value = jiterator.element;
						remove(value);
						iterator.right.element = value;
						break;
				}

				return true;
			} else if (iterator.left != null && v == iterator.left.element){ // if the left node is our value
				switch (nodeChildren(iterator.left)) {
					case 0:
						iterator.left = null;
						break;
					case -1:
						iterator.left = iterator.left.left;
						break;
					case 1:
						iterator.left = iterator.left.right;
						break;
					case 2:
						TNode jiterator = iterator.left;
						while(jiterator.left != null){
							jiterator = jiterator.left;
						}
						int value = jiterator.element;
						remove(value);
						iterator.left.element = value;
						break;
				}
				return true;
			} else if (v > iterator.element) { //if our value is larger
				if (iterator.right == null) {
					return false; //if we're at a leaf, the value isn't there
				} else {
					iterator = iterator.right;//move right
				}
			} else if ( v < iterator.element) { //if our value is smaller
				if (iterator.left == null) {
					return false;
				} else {
					iterator = iterator.left; //move left
				}
			} else if (v == iterator.element){ //used for when we are deleting the first node
				switch (nodeChildren(root)) { //use children method to determine how many children/where the children are
					case 0: //no children, chop off the node
						iterator = null;
						break;
					case -1: //one left child, point to it
						iterator = root.left;
						break;
					case 1: //one right child, point to it
						iterator = root.right;
						break;
					case 2: // if we have two children
						TNode jiterator = iterator.right;
						while(jiterator.left != null){
							jiterator = jiterator.left;
						}
						int value = jiterator.element;
						remove(value);
						iterator.element = value;
						break;
				}
				return true;
			}
		}
	}

	public BSTSet union(BSTSet s){ //O(n^2), Space: O(n)

		//return corresponding set if one of the sets are empty
		if (root == null) {
			return s.copy(); 
		}

		if (s.root == null) {
			return copy();
		}

		int[] thisArray = new int[size()];
		int[] sArray = new int[s.size()];

		TNode thisIterator = root;
		TNode sIterator = s.root;

		treeToArray(thisArray, thisIterator, 0); //convert trees to arrays
		treeToArray(sArray, sIterator, 0);

		int[] combinedArray = new int[thisArray.length + sArray.length]; //combined list will be the length of the arrays of both sets

		for (int i = 0; i < combinedArray.length; i++) { //combine the two arrays
			if (i < thisArray.length) {
				combinedArray[i] = thisArray[i];
			} else {
				combinedArray[i] = sArray[i-thisArray.length];
			}
		}
		return new BSTSet(combinedArray);
	}

	public BSTSet intersection(BSTSet s){ //O(n^2), Space: O(n)
		if (root == null || s.root == null) { //if either sets are empty the result will be an empty set
			return new BSTSet();
		}

		int[] thisArray = new int[size()];
		int[] sArray = new int[s.size()];

		TNode thisIterator = root;
		TNode sIterator = s.root;
		treeToArray(thisArray, thisIterator, 0);
		treeToArray(sArray, sIterator, 0);

		String outputString = new String();//using output string so that the array size can be manipulated
		for (int i = 0; i < thisArray.length; i++) {
			for (int j = 0; j < sArray.length; j++) {
				if (thisArray[i] == sArray[j]){//if values are in both arrays, add to string
					outputString += sArray[j] + ",";//add a comma so we can split later
				}
			}
		}

		if (outputString.length() == 0){//empty string means empty set
			return new BSTSet();
		}

		String[] outputStringArray = outputString.split(",");//split into string array
		int[] outputArray = new int[outputStringArray.length];

		for (int i = 0; i < outputStringArray.length; i++){
			outputArray[i] = Integer.parseInt(outputStringArray[i]);//cast from strings to ints
		}

		return new BSTSet(outputArray); //return new organized, minimum sized array
	}

	public BSTSet difference(BSTSet s){ //O(n^2), Space: O(n)
		
		if (root == null || s.root == null) { //"this" if either are null
			return copy();
		}

		BSTSet intersection = intersection(s); //only values that are important are the intersecting values

		int[] thisArray = new int[size()];
		int[] intersectionArray = new int[intersection.size()];

		TNode thisIterator = root;
		TNode intersectionIterator = intersection.root;
		treeToArray(thisArray, thisIterator, 0);
		treeToArray(intersectionArray, intersectionIterator, 0);

		int[] outputArray = new int[thisArray.length - intersectionArray.length];

		int outputPos = 0;
		for (int i = 0; i < thisArray.length; i++) {
			boolean addFlag = true;
			for (int j = 0; j < intersectionArray.length; j++){
				if (thisArray[i] == intersectionArray[j]){//compare each value in "this" array to intersection
					addFlag = false;
				}
			}

			if (addFlag == true) {
				outputArray[outputPos++] = thisArray[i];//add to string only if value is not in intersection
			}
		}

		return new BSTSet(outputArray);

	}

	public int size(){ //O(n), Space: O(n)
		return nodeSize(root); //first node is counted
	}

	public int height(){ //O(n), Space: O(n)
		return nodeHeight(root)-1;//first node is not counted
	}

	public void printBSTSet(){ //O(n)
		if (root==null){
			System.out.println("The set is empty");
		} else { 
			System.out.print("The set elements are: ");
			printBSTSet(root);
			System.out.print("\n");
		}
	}

	private void printBSTSet(TNode t){
		if (t!=null){
			printBSTSet(t.left);
			System.out.print(" " + t.element + ", ");
			printBSTSet(t.right);
		}
	}

	public void printNonRec(){ //O(n), Space: O(n)

		if (root==null){//null root means empty set
			System.out.println("The set is empty");
		} else { 
			Stack<TNode> nodeStack = new Stack();
			TNode iterator = root;
			System.out.print("The set elements are:  ");
	
			while (true){
				while (iterator != null) { //when we are not at a null value keep moving left, adding each node to a stack
					nodeStack.push(iterator);
					iterator = iterator.left;
				}
	
				if (nodeStack.isEmpty() == false) { //if the stack is not empty
					iterator = nodeStack.pop(); //pop out the top value and store as an iterator
					System.out.print(iterator.element + ",  ");//print out the value
					iterator = iterator.right;//move right for in-order traversal
				} else {
					break; //when stack is empty end the loop
				}
			}
		}
		System.out.println();
	}

	private static int nodeChildren(TNode node){ //O(1)
		if (node.left == null && node.right == null){
			return 0; //returns zero if there are no children
		} else if (node.left == null && node.right != null) {
			return 1; //returns 1 for one right child
		} else if (node.left != null && node.right == null) {
			return -1; //returns -1 for one left child
		} else {
			return 2; //returns 2 if there are 2 children
		}
	}

	private static int nodeSize(TNode node){ //O(n), Space: O(n)
		if (node == null){
			return 0;//an empty tree has zero size
		} else {
			int leftSize = nodeSize(node.left);
			int rightSize = nodeSize(node.right);//recursively determine size of right/left subtrees
			return leftSize + rightSize + 1;//add one to account for current node
		}
	}

	private static int nodeHeight(TNode node){ //O(n), Space: O(n)
		if (node == null){
			return 0;
		} else {
			int leftHeight = nodeHeight(node.left);
			int rightHeight = nodeHeight(node.right);
			return Math.max(leftHeight, rightHeight) + 1;//get the max size of either left or right subtrees and add one to account for the current node. This is the height
		}
	}

	private int[] bubbleSort(int[] input){ //O(n^2), Space: O(n)
		//bubble sort array in place and return an array with removed duplicates

		for (int i = 0; i < input.length; i++){//bubble sort algorithm
			for (int j = 0; j < input.length-i-1; j++){
				if (input[j] > input[j+1]){
					int temp = input[j];
					input[j] = input[j+1];
					input[j+1] = temp;
				}
			}
		}

		String result = new String(); //use a string so that the array can be of a non-fixed size
		
		for (int i = 0; i < input.length-1; i++) {
			if (input[i] != input[i+1]) {
				result += input[i] + ",";//append a comma so we can split later
			}
		}

		result += input[input.length-1];//add the missing last value

		if (result.length() != 0){
			String[] outputStringArray = result.split(",");//split string

			int[] outputArray = new int[outputStringArray.length];

			for (int i = 0; i < outputStringArray.length; i++){
				outputArray[i] = Integer.parseInt(outputStringArray[i]); //cast string array to integer array
			}

			return outputArray;

		} else {
			return input;
		}
	}

	private TNode smallestHeightTree(int[] sortedInput, int left, int right){ //O(n), Space: O(n)
		if (left > right){ //base case when the left is now bigger than the right
			return null;
		}

		int middle = (left + right)/2;

		TNode rightNode = smallestHeightTree(sortedInput, middle+1, right); //recursively call function for each left and right
		TNode leftNode = smallestHeightTree(sortedInput, left, middle-1);

		return new TNode(sortedInput[middle], leftNode, rightNode); //each node will have a tree attached and we can merge these together to generate trees
	}

	private int treeToArray(int[] resultArray, TNode node, int position){ //O(n), Space: O(n)

		if (node.left != null){ //move left as far as possible to get smallest value 
			position = treeToArray(resultArray, node.left, position); //recursive call using our new leftmost position
		}

		resultArray[position] = node.element;//store this value in the array
		position++;//move forward each time a new value is added

		if (node.right != null) { //move as far right in our left subtree as possible
			position = treeToArray(resultArray, node.right, position);  //recursive call using our right value of the leftmost position
		}

		return position;
	}

	private BSTSet copy() { //O(n), Space: O(n)
		int[] dataArray = new int[size()];
		TNode iterator = root;		
		treeToArray(dataArray, iterator, 0);
		return new BSTSet(dataArray); //generate an unlinked copy of our BSTSet 
	}
}

class TNode {
	int element;
	TNode left;
	TNode right;
	TNode(int i, TNode l, TNode r){
		element = i;
		left = l;
		right = r;
	}
}