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
			length = val.length()-1;
			// throw new IllegalArgumentException("Note: addition of negative numbers not currently supported.");
		} else {
			begin = 0;
			positive = true;//note integer will be positive
			length = val.length();
		}

		for (int i = begin; i < val.length(); i++){
			int currentChar = val.charAt(i);
			if ( currentChar < '0' || currentChar > '9') {
				throw new IllegalArgumentException("Please enter a string of integers"); //throw exception if a non-digit is present
			} else {
				integer += (char) currentChar; //otherwise add that digit into the stored integer string
			}
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

		return new HugeInteger(output);
	}

	// public HugeInteger subtract(HugeInteger h){

	// 	String output = "";
	// 	int carry = 0;

	// 	for (int i = 1; i < (h.getLength() > this.getLength() ? h.getLength()+1 : this.getLength()+1); i++){

	// 		int valA;
	// 		int valB;
	// 		int diff;

	// 		try {
	// 			valA = this.getInteger().charAt(this.getInteger().length()-i)-48;
	// 		} catch (StringIndexOutOfBoundsException e) {
	// 			valA = 0;
	// 		}

	// 		try {
	// 			valB = h.getInteger().charAt(h.getInteger().length()-i)-48;
	// 		} catch (StringIndexOutOfBoundsException e) {
	// 			valB = 0;
	// 		}

	// 		if ((valA - valB - carry) < 0) {
	// 			valA += 10;
	// 			diff = valA - valB - carry;
	// 			carry = 1;
				
	// 		} else {
	// 			diff = valA - valB - carry;
	// 			carry = 0;
	// 		}

	// 		output = Integer.toString(diff%10) + output;
	// 	}


	// 	return new HugeInteger(output);
	// }	

	// public HugeInteger multiply(HugeInteger h){

	// }

	// public int compareTo(HugeInteger h){
		
	// }

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
}