import java.math.BigInteger;

class ReportTest {
	public static void main(String[] args) {

		// int MAXRUN[] = {800000, 60000, 5000, 1200, 60, 18};
		// int MAXRUN[] = {350, 8, 1, 1, 1, 1};
		// int n[] = {10,100,500,1000,5000,10000};

		int MAXRUN[] = {1, 1};
		int n[] = {5000,10000};

		for (int i = 0; i < MAXRUN.length; i++){
			// for (int j = 0; j < 3; j++) {
				int MAXIMUMINTS = 20;
				// int MAXRUN = 800000; //n=10
				// int MAXRUN = 60000; //n=100
				// int MAXRUN = 5000; //n=500
				// int MAXRUN = 1200; //n=1000
				// int MAXRUN = 60; //n=5000
				// int MAXRUN = 18; //n=10 000
				// int n = 10000;
				
				HugeInteger huge1, huge2, huge3;
				// BigInteger big1, big2, big3;
				long startTime, endTime;
				double runTime = 0.0;

				for (int numInts = 0; numInts < MAXIMUMINTS; numInts++){
					int numRun;

					huge1 = new HugeInteger(n[i]); //creates a random integer of n digits
					huge2 = new HugeInteger(n[i]); //creates a random integer of n digits

					// big1 = new BigInteger(huge1.toString());
					// big2 = new BigInteger(huge2.toString());
					

					startTime = System.currentTimeMillis();
					for(numRun = 0; numRun < MAXRUN[i]; numRun++){
						huge3 = huge1.multiply(huge2); 
						// big3 = big1.add(big2);
					}
					endTime = System.currentTimeMillis();

					runTime +=(double) (endTime - startTime)/((double) MAXRUN[i]);
					System.out.println("Start-End: " + (startTime-endTime));
				}
				runTime = runTime/((double)MAXIMUMINTS);
				System.out.println(n[i] + " " + runTime);
			// }
		}
	}
}