public class Main {
	public static void main(String[] args) {
		int[] peas = {1,2,5,3,7,8,13,6,24,51,69,4,73,0,83,-15};

		BSTSet pizza = new BSTSet();
		BSTSet pasta = new BSTSet(peas);

		// System.out.println(pizza.isIn(0));
		// System.out.println(pasta.isIn(0));

		pizza.add(12);
		pizza.add(13);
		pizza.add(69);
		pizza.add(-15);

		// pasta.add(-13);
		// pasta.add(-18);
		// pasta.add(71);
		// pasta.add(70);
		// pasta.add(72);

		// pizza.remove(8);
		// pasta.printBSTSet();
		// pasta.remove(7);
		pasta.remove(6);
		// pasta.remove(6);

		// pizza.printBSTSet();
		// pasta.printBSTSet();
		// BSTSet pecans = pasta.difference(pizza);

		pasta.printBSTSet();
		pizza.printBSTSet();
		// pecans.printBSTSet();
		// System.out.println(pecans.height());
		pasta.printNonRec();
		// System.out.println(pasta.height());

		// pasta.union(pizza);

	}
}