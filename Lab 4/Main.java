public class Main {
	public static void main(String[] args) {
		HashTableLin pasta = new HashTableLin(10, 0.5);
		HashTableQuad pizza = new HashTableQuad(5, 0.5);

		pasta.insertCount(8);
		pasta.insertCount(9);
		pasta.insertCount(10);
		pasta.insertCount(11);
		pasta.insertCount(86);
		pasta.insertCount(654);
		pasta.insertCount(36);
		pasta.insertCount(46);
		pasta.insertCount(32);
		pasta.insertCount(8);

		System.out.println(pasta.getNumOfElems());
		pasta.printKeysAndIndexes();
		System.out.println();

		// pizza.insert(2);
		// pizza.insert(13);
		// pizza.insert(24);
		// pizza.insert(35);
		// pizza.insert(0);

		// pizza.printKeysAndIndexes();
		// for (int i = 0; i < pizza.getSizeOfTable(); i++){
		// 	System.out.println(i + " : " + pizza.getTable()[i]);
		// }

		// System.out.println(pizza.isIn(35));
	}
}