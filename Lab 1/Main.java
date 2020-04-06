public class Main {
	public static void main(String[] args) {
		// Constructor 1
		// 1.1
		HugeInteger pie = new HugeInteger("123$#");
		HugeInteger soup = new HugeInteger("3.141596535");
		HugeInteger rice = new HugeInteger("00-4671");
		// 1.2
		HugeInteger apple = new HugeInteger("00345");
		HugeInteger broccoli = new HugeInteger("00000");
		System.out.println(apple);
		System.out.println(broccoli);
		//1.3
		HugeInteger carrot = new HugeInteger("");
		//1.4
		HugeInteger greenBeans = new HugeInteger("13456789012345678901234567890");
		System.out.println(greenBeans);
		//1.5
		HugeInteger onion = new HugeInteger("-357184");
		System.out.println(onion);
		HugeInteger garlic = new HugeInteger("-00871162");
		System.out.println(garlic);

		//Constructor 2
		//2.1
		HugeInteger almonds = new HugeInteger(8);
		System.out.println(almonds);
		//2.2
		HugeInteger pecans = new HugeInteger(0);

		//"add" method
		HugeInteger pasta = new HugeInteger("99");
		HugeInteger orange = new HugeInteger("1");
		System.out.println(pasta.add(orange));
	}
}