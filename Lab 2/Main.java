public class Main {
	public static void main(String[] args) {

		/*Addition*/

		HugeInteger greenBeans = new HugeInteger("888");
		HugeInteger peas = new HugeInteger("-800");

		// HugeInteger greenBeans = new HugeInteger("-7891");
		// HugeInteger peas = new HugeInteger("8765");

		// HugeInteger greenBeans = new HugeInteger("8917");
		// HugeInteger peas = new HugeInteger("-4884");

		/*Subtraction*/

		HugeInteger carrots = new HugeInteger("7561");
		HugeInteger potatoes = new HugeInteger("1128");

		// HugeInteger carrots = new HugeInteger("8999");
		// HugeInteger potatoes = new HugeInteger("-9946");

		// HugeInteger carrots = new HugeInteger("-12345");
		// HugeInteger potatoes = new HugeInteger("-45678");

		/*Multiplication*/

		HugeInteger apples = new HugeInteger("-333");
		HugeInteger pears = new HugeInteger("0");

		// HugeInteger apples = new HugeInteger("-817421");
		// HugeInteger pears = new HugeInteger("1879982");

		// HugeInteger apples = new HugeInteger("-99999");
		// HugeInteger pears = new HugeInteger("-111111");

		/*Comparison*/

		HugeInteger pasta = new HugeInteger("-9999");
		HugeInteger rice = new HugeInteger("-20000");

		// HugeInteger pasta = new HugeInteger("999");
		// HugeInteger rice = new HugeInteger("1000");

		// HugeInteger pasta = new HugeInteger("1001");
		// HugeInteger rice = new HugeInteger("1001");

		// HugeInteger pasta = new HugeInteger("3486");
		// HugeInteger rice = new HugeInteger("1234");


		System.out.print("Addition:\t");
		System.out.println(greenBeans + " + " + peas + " = " + greenBeans.add(peas));

		System.out.print("Subtraction:\t");
		System.out.println(carrots + " - " + potatoes + " = " + carrots.subtract(potatoes));
		
		System.out.print("Multiplication:\t");
		System.out.println(apples + " * " + pears + " = " +apples.multiply(pears));

		System.out.print("Comparison:\t");
		System.out.println(pasta + " > " + rice + " -> " +pasta.compareTo(rice));

	}
}