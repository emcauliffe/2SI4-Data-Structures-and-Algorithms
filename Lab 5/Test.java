class Test{
	public static void main(String[] args) {

		//test constructors
		MaxHeap pizza = new MaxHeap(8);

		Integer[] grain = {-5,7,3,19,0,24,-18,111,45,67,3,8};
		MaxHeap pasta = new MaxHeap(grain);

		//test getters + toString
		System.out.println("Testing pizza getters:");
		System.out.println("Size of array: " + pizza.getStorageSize());
		System.out.println("Number of elements: " + pizza.getNumElems());
		System.out.print("Array contents (via loop and storage[] getter): ");
		for (Integer i : pizza.getStorage()){
			System.out.print(i + ", ");
		}
		System.out.println();
		System.out.println("Array contents (via toString): " + pizza.toString());

		System.out.println();

		System.out.println("Testing pasta getters:");
		System.out.println("Size of array: " + pasta.getStorageSize());
		System.out.println("Number of elements: " + pasta.getNumElems());
		System.out.print("Array contents (via loop and storage[] getter): ");
		for (Integer i : pasta.getStorage()){
			System.out.print(i + ", ");
		}
		System.out.println();
		System.out.println("Array contents (via toString): " + pasta.toString());

		//testing insert
		System.out.println();
		System.out.println("Testing insertion:");
		pizza.insert(8);
		System.out.println("with 8 inserted: " + pizza.toString());

		pasta.insert(8);
		pasta.insert(15);
		pasta.insert(-15);
		System.out.println("with 8, 15, and -15 inserted: " + pasta.toString());

		//test heapsort
		System.out.println();
		System.out.println("Testing heapsort:");
		System.out.println("Original array: ");
		for (int i : grain){
			System.out.print(i + ", ");
		}
		System.out.println();

		MaxHeap.heapsort(grain);

		System.out.println("Sorted array: ");
		for (int i : grain){
			System.out.print(i + ", ");
		}
	}
}