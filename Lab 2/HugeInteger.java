class HugeInteger {

	private String integer;
	private int length;
	private boolean positive;

	public HugeInteger(String val){

		if (val == "") { //check first if the input is empty
			throw new IllegalArgumentException("Please enter a string of integers");
		}

		int begin;
		integer = "";//initialize integer as an empty string
		if (val.charAt(0) == '-') { //check if number will be negative
			begin = 1; //set beginning read point as 2nd character of the input string (will be a digit)
			positive = false; //note that integer will be negative
		} else {
			begin = 0;
			positive = true;//note integer will be positive
		}

		boolean nonZeroFlag = false;
		length = 0;
		for (int i = begin; i < val.length(); i++){
			
			char currentChar = val.charAt(i);
			
			if (currentChar != '0') {
				nonZeroFlag = true;
			}

			if ( currentChar < '0' || currentChar > '9') {
				throw new IllegalArgumentException("Please enter a string of integers"); //throw exception if a non-digit is present
			} else if (nonZeroFlag == true) {
				integer += currentChar; //otherwise add that digit into the stored integer string
				length++;
			}
		}

		if (length == 0) { //edge case if the input is simply a zero
			integer = "0";
			length = 1;
			positive = true;
		}
	}

	private HugeInteger(String n, boolean checkVals){
		if (checkVals == false) {
			integer = n;
			length = n.length();
			positive = true;
		}
	}
	
	public HugeInteger(int n){
		if (n < 1){ //check for positive input
			throw new IllegalArgumentException("Please enter a positive integer");
		}

		integer = "";
		length = n;
		positive = true;

		for (int i = 0; i < n-1; i++){
			int randomNum = (int) (Math.random()*10); //Math.random outputs a decimal, take the tens of this decimal
			integer = randomNum + integer;
		}

		int lastNum = 0;
		while(lastNum == 0){ //generate a leading random digit that is not zero
			lastNum = (int) (Math.random()*10);
		}
		integer = lastNum + integer;
	}


	public HugeInteger add(HugeInteger h){

		if (this.isPositive() && !h.isPositive()){
			HugeInteger positiveH = h.copy();
			positiveH.positive = true;
			return subtract(positiveH);
		}

		if (!this.isPositive() && h.isPositive()){
			return h.add(this);
		}

		String output = "";//initialize empty string
		int carry = 0; //no carry at first

		//iterate through the longest number (number with the most amount of digits)
		for (int i = 1; i < (h.getLength() > this.getLength() ? h.getLength()+1 : this.getLength()+1); i++){

			int valA;
			int valB;

			try {
				valA = this.getInteger().charAt(this.getInteger().length()-i)-48;//set first value as current "this" digit (convert ascii value)
			} catch (StringIndexOutOfBoundsException e) {//if the character does not exist, replace it with a zero
				valA = 0;
			}

			try {
				valB = h.getInteger().charAt(h.getInteger().length()-i)-48;//set first value as current "h" digit
			} catch (StringIndexOutOfBoundsException e) {
				valB = 0;
			}

			int sum = valA + valB + carry;//sum the values and the carry

			output = Integer.toString(sum%10) + output;//add the ones digit of the sum to the output string

			carry = sum/10;//set the carry
		}

		if (carry != 0) {
			//place the carry (if present) at the beginning after all digits have been iterated
			output = Integer.toString(carry) + output;
		}

		HugeInteger resultSum = new HugeInteger(output);

		if (!this.isPositive() && !h.isPositive()){
			resultSum.positive = false;
		}

		return resultSum;
	}

	public HugeInteger subtract(HugeInteger h){

		if ( !h.isPositive()) { //pos minus neg is always positive
			HugeInteger positiveH = h.copy();
			positiveH.positive = true;
			return add(positiveH);
		}

		if (compareTo(h) == 0) { //check if result will be negative
			return new HugeInteger("0");
		}
		
		if (compareTo(h) == -1){ 
			HugeInteger result = h.subtract(this); //recursively run the subtract function to determine the difference between this and h
			result.positive = false; //change to negative (private instance accessible within class)
			return result;
		}

		String output = "";
		int carry = 0;

		for (int i = 1; i < (h.getLength() > this.getLength() ? h.getLength()+1 : this.getLength()+1); i++){

			int valA;
			int valB;
			int diff;

			try {
				valA = this.getInteger().charAt(this.getInteger().length()-i)-48;
			} catch (StringIndexOutOfBoundsException e) {
				valA = 0;
			}

			try {
				valB = h.getInteger().charAt(h.getInteger().length()-i)-48;
			} catch (StringIndexOutOfBoundsException e) {
				valB = 0;
			}

			if ((valA - valB - carry) < 0) {
				valA += 10;
				diff = valA - valB - carry;
				carry = 1;
			} else {
				diff = valA - valB - carry;
				carry = 0;
			}

			output = Integer.toString(diff%10) + output;
		}
		return new HugeInteger(output);
	}	

	public HugeInteger multiply(HugeInteger h){ // Karatsuba multiplication O(n^log3)

		int mostDigits = (h.getLength() > this.getLength() ? h.getLength() : this.getLength()); //find the longest number
		boolean positiveProduct = true;

		if ((this.isPositive() && !h.isPositive()) || (!this.isPositive() && h.isPositive())) {
			if (this.getInteger() != "0" && h.getInteger() != "0") {
				positiveProduct = false; //flag if the result should be negative
			}
		}

		if (mostDigits % 2 == 1){
			mostDigits++; //ensure even number of digits (number will be split in two)
		}

		String a1 = ""; //first half of digits in "this"
		String a2 = ""; //2nd half of digits in "this"

		String b1 = ""; //as above but for "h" 
		String b2 = "";

		for (int i = 1; i < mostDigits+1; i++) {

			try {
				if ( i > mostDigits / 2) {
					a1 = this.getInteger().charAt(this.getInteger().length()-i)-48 + a1; //get first half of digits in "this"
				} else {
					a2 = this.getInteger().charAt(this.getInteger().length()-i)-48 + a2; //get 2nd half of digits in "this"
				}
			} catch (StringIndexOutOfBoundsException e) { //if the number of digits in "this" and "h" numbers is not equal, pad with zeros
				if ( i > mostDigits / 2) {
					a1 = "0" + a1;
				} else {
					a2 = "0" + a2;
				}
			}

			try {
				if ( i > mostDigits / 2) { 
					b1 = h.getInteger().charAt(h.getInteger().length()-i)-48 + b1;
				} else {
					b2 = h.getInteger().charAt(h.getInteger().length()-i)-48 + b2;
				}
			} catch (StringIndexOutOfBoundsException e) {
				if ( i > mostDigits / 2) {
					b1 = "0" + b1;
				} else {
					b2 = "0" + b2;
				}
			}
		}

		if (a1.length() == 1 && a2.length() == 1 && b1.length() == 1 && b2.length() == 1) { //base case check -> we have all single digit numbers

			int a1Integer = Integer.parseInt(a1); //cast string to integer
			int a2Integer = Integer.parseInt(a2);
			int b1Integer = Integer.parseInt(b1);
			int b2Integer = Integer.parseInt(b2);

			//this is the Karatsuba multiplication algorithm
			int firstTerm = a1Integer*b1Integer;  //multiply first half of digits from both numbers together
			int secondTerm = a2Integer*b2Integer; // multiply 2nd half of digits together
			int thirdTerm = (a1Integer+a2Integer) * (b1Integer+b2Integer);

			//use gauss elimination to get final term of Karatsuba algorithm
			int gaussTerm = thirdTerm - secondTerm - firstTerm; 
			
			//add n zeros to the first term where n is the number of digits in a and b (will always be 2 in base case)
			//add the second term to this first term
			//add n/2 zeros to the gauss term again where n is the number of digits in a and b (2 digits)
			//result is the product of the a and b
			int product = firstTerm*100 + secondTerm + gaussTerm*10;

			// String productString =  String.valueOf(product);

			HugeInteger hugeProduct = new HugeInteger(String.valueOf(product), false);//cast product to string and create a new HugeInteger

			hugeProduct.positive = positiveProduct; //set the integer to + or - as appropriate given input

			return hugeProduct; //return the HugeInteger product
		} else { //recursive part of algorithm
			HugeInteger a1HugeInteger = new HugeInteger(a1, false); //creating new HugeIntegers with upper+lower digits of "this"
			HugeInteger a2HugeInteger = new HugeInteger(a2, false);
	
			HugeInteger b1HugeInteger = new HugeInteger(b1, false); //as above but with "h"
			HugeInteger b2HugeInteger = new HugeInteger(b2, false);

			//this is the recursive part of the Karatsuba algorithm 
			//steps are the same as in the base case but call our HugeInteger methods
			HugeInteger firstTerm = a1HugeInteger.multiply(b1HugeInteger);
			HugeInteger secondTerm = a2HugeInteger.multiply(b2HugeInteger);
			HugeInteger thirdTerm = (a1HugeInteger.add(a2HugeInteger)).multiply((b1HugeInteger.add(b2HugeInteger)));

			HugeInteger gaussTerm = thirdTerm.subtract(secondTerm.add(firstTerm));

			//here we add the appropriate amount of zeros dependent on the number of digits of the input terms
			String firstPad = new String(new char[mostDigits]).replace("\0", "0"); //adding n zeros
			String secondPad = new String(new char[mostDigits/2]).replace("\0", "0"); //adding n/2 zeros

			HugeInteger bigNumOne = new HugeInteger(firstTerm+firstPad, false); //appending generated pads and storing as HugeInteger
			HugeInteger bigNumTwo = new HugeInteger(gaussTerm+secondPad, false);

			HugeInteger resultProduct = bigNumTwo.add(bigNumOne.add(secondTerm)); //sum all values together to get product

			resultProduct.positive = positiveProduct;//set product as + or - as appropriate

			return resultProduct;
		}
	}

	public int compareTo(HugeInteger h){
		
		if (this.isPositive() && !h.isPositive()) { // check positive vs negative and make decision immediately
			return 1;
		} else if (!this.isPositive() && h.isPositive()) {
			return -1;
		}

		if (this.getLength() - h.getLength() > 0){ // next use number of digits in numbers to check 
			if (this.isPositive() && h.isPositive()) {
				return 1;
			} else {
				return -1;
			}
		} else if (this.getLength() - h.getLength() < 0) {
			if (this.isPositive() && h.isPositive()) {
				return -1;
			} else {
				return 1;
			}
		} else { // finally, compare individual digits

			for (int i = 0; i < getLength(); i++){ //lengths are the same so it doesn't matter which one we choose
				int valA;
				int valB;
	
				try {
					valA = this.getInteger().charAt(i)-48;
				} catch (StringIndexOutOfBoundsException e) {
					valA = -1;
				}
	
				try {
					valB = h.getInteger().charAt(i)-48;
				} catch (StringIndexOutOfBoundsException e) {
					valB = -1;
				}
	
				if (valA > valB) {
					return 1;
				}
	
				if (valA < valB) {
					return -1;
				}
			}
		}
		return 0; //if no case is ever tripped, the numbers are equal
	}

	public String toString(){
		if (positive) {
			return integer;
		} else {
			return "-" + integer;
		}
	}

	private int getLength() {
		return length;
	}

	private String getInteger() {
		return integer;
	}

	private boolean isPositive() {
		return positive;
	}

	private HugeInteger copy() {
		return new HugeInteger(this.toString());
	}
}