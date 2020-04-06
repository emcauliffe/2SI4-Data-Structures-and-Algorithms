class TestHashTable {
    public static void main(String[] args) {

        //Constructor Tests

        System.out.println("-- Test various constructor inputs --");
        
        HashTableLin penne = new HashTableLin(10, 0.5);
        HashTableQuad rigatoni = new HashTableQuad(10, 0.5);

        System.out.println(penne.getSizeOfTable()); //should both be 23
        System.out.println(rigatoni.getSizeOfTable());

        HashTableLin macaroni = new HashTableLin(12, 0.7);
        HashTableQuad rotini = new HashTableQuad(12, 0.7);

        System.out.println(macaroni.getSizeOfTable());//should both be 19
        System.out.println(rotini.getSizeOfTable());

        try {
            System.out.println("Invalid Inputs:");
            HashTableLin farfalle = new HashTableLin(-1, 23); //both these methods throw an exception
            HashTableQuad fusilli = new HashTableQuad(-1, 23);
        } catch (Exception e) {
            System.out.println("Errors handled.");
        }

        System.out.println("-- Test Linear Insert (and rehash) --");
        penne.insert(8); //i: 8
        penne.insert(7); //i: 7
        penne.insert(30); //i: 7,8,9
        penne.insert(22); //i: 22
        penne.insert(23); //i: 0
        penne.insert(45); //i: 22,0,1
        penne.insert(2); //i: 2
        penne.insert(-3); //i: 3
        penne.insert(4); //i: 4
        penne.insert(5); //i: 5
        penne.insert(6); //i: 6

        System.out.println("Size after 11 insertions: " + penne.getSizeOfTable()); //should still be 23 (11/23=0.478)
        System.out.println("Table after 11 insertions: ");
        penne.printKeysAndIndexes();

        System.out.println("Rehash required...");
        penne.insert(666); //i: 8,9
        System.out.println("Size after 12 insertions: " + penne.getSizeOfTable()); //should be 47 (12/23 > 0.5), 23*2=46 => 47 is closest prime
        System.out.println("Table after 12 insertions: ");
        penne.printKeysAndIndexes();

        System.out.println("-- Test Quadratic Insert (and rehash) --");
        rigatoni.insert(8); //i: 8
        rigatoni.insert(7); //i: 7
        rigatoni.insert(30); //i: 7,8,11
        rigatoni.insert(22); //i: 22
        rigatoni.insert(23); //i: 0
        // rigatoni.insert(45); //i: 22,0,3
        rigatoni.insert(46);
        rigatoni.insert(2); //i: 2
        rigatoni.insert(3); //i: 3,4
        rigatoni.insert(4); //i: 4,5
        // rigatoni.insert(5); //i: 5,6
        rigatoni.insert(0);
        rigatoni.insert(6); //i: 6,7,10

        System.out.println("Size after 11 insertions: " + rigatoni.getSizeOfTable()); //should still be 23 (11/23=0.478)
        System.out.println("Table after 11 insertions: ");
        rigatoni.printKeysAndIndexes();

        System.out.println("Rehash required...");
        rigatoni.insert(666); //i: 8,9
        System.out.println("Size after 12 insertions: " + rigatoni.getSizeOfTable()); //should be 47 (12/23 > 0.5), 23*2=46 => 47 is closest prime
        System.out.println("Table after 12 insertions: ");
        penne.printKeysAndIndexes();

        System.out.println("-- Test Linear Check --");
        penne.printKeys();
        System.out.println("8 " + penne.isIn(8)); //all true
        System.out.println("23 " + penne.isIn(23));
        System.out.println("45 " + penne.isIn(45));
        System.out.println("6 " + penne.isIn(6));
        System.out.println("30 " + penne.isIn(30));

        System.out.println("654 " + penne.isIn(654)); // all false
        System.out.println("46 " + penne.isIn(46));
        System.out.println("0 " + penne.isIn(0));
        System.out.println("-14 " + penne.isIn(-14));
        System.out.println("13 " + penne.isIn(13));
        
        System.out.println("-- Test Quadratic Check --");
        rigatoni.printKeys();
        System.out.println("8 " + rigatoni.isIn(8)); //all true
        System.out.println("23 " + rigatoni.isIn(23));
        System.out.println("45 " + rigatoni.isIn(45));
        System.out.println("6 " + rigatoni.isIn(6));
        System.out.println("30 " + rigatoni.isIn(30));

        System.out.println("654 " + rigatoni.isIn(654)); // all false
        System.out.println("46 " + rigatoni.isIn(46));
        System.out.println("0 " + rigatoni.isIn(0));
        System.out.println("-14 " + rigatoni.isIn(-14));
        System.out.println("13 " + rigatoni.isIn(13));
        
        System.out.println("-- Linear S(λ) --");
        for (int i = 1; i < 10; i++){
            double linNumba = 0;
            for(int j = 0; j < 100; j++){
                linNumba += averageProbesSuccessLin(i/10.0);
            }
            System.out.println("λ = " + i/10.0);
            System.out.println("\tMeasured: " + linNumba/100.0);
            System.out.println("\tTheoretical: " + calculatedProbesSuccess(i/10.0));
        }

        System.out.println("\n-- Quadratic S(λ) --");
        for (int i = 1; i < 10; i++){
            double quadNumba = 0;
            for(int j = 0; j < 100; j++){
                quadNumba += averageProbesSuccessQuad(i/10.0);
            }
            System.out.println("λ = " + i/10.0);
            System.out.println("\tMeasured: " + quadNumba/100.0);
            System.out.println("\tTheoretical (linear): " + calculatedProbesSuccess(i/10.0));
        }

        System.out.println("-- Linear U(λ) --");
        for (int i = 1; i < 10; i++){
            double linNumba = 0;
            for(int j = 0; j < 100; j++){
                linNumba += averageProbesFailLin(i/10.0);
            }
            System.out.println("λ = " + i/10.0);
            System.out.println("\tMeasured: " + linNumba/100.0);
            System.out.println("\tTheoretical: " + calculatedProbesFail(i/10.0));
        }
    }

    public static double averageProbesSuccessLin(double loadFactor) {
        HashTableLin hashTable = new HashTableLin(100000, loadFactor);
        int totalProbes = 0;
        while(hashTable.getNumOfElems() < 100000){
            int randomInt = (int)Math.floor(Math.random()*1000000000);
            int probes = hashTable.insertCount(randomInt);
            if (probes != -1){
                totalProbes += probes;
            }
        }
        return (double) totalProbes/100000.0;
    }

    public static double averageProbesSuccessQuad(double loadFactor) {
        HashTableQuad hashTable = new HashTableQuad(100000, loadFactor);
        int totalProbes = 0;
        while(hashTable.getNumOfElems() < 100000){
            int randomInt = (int)Math.floor(Math.random()*1000000000);
            int probes = hashTable.insertCount(randomInt);
            if (probes != -1){
                totalProbes += probes;
            }
        }
        return (double) totalProbes/100000.0;
    }

    public static double averageProbesFailLin(double loadFactor) {
        HashTableLin hashTable = new HashTableLin(100000, loadFactor);
        while(hashTable.getNumOfElems() < 100000){
            int randomInt = (int)Math.floor(Math.random()*1000000000);
            hashTable.insert(randomInt);
        }

        int totalProbes = 0;
        for (int i = 0; i < 100000; i++){
            int randomInt = (int)Math.floor(Math.random()*1000000000);
            int probes = hashTable.isInCount(randomInt);
            if (probes != -1){
                totalProbes += probes;
            }
        }

        return (double) totalProbes/100000.0;
    }

    public static double calculatedProbesSuccess(double loadFactor){
        return 0.5*(1.0 + 1.0 / (1.0-loadFactor));
    }

    public static double calculatedProbesFail(double loadFactor){
        return 0.5*(1.0 + 1.0 / ((1.0-loadFactor)*(1.0-loadFactor)));
    }
}