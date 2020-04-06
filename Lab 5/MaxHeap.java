class MaxHeap {
    private Integer[] storage;
    private int storageSize;
    private int numElems;

    public MaxHeap(int size){ //O(1)
        storage = new Integer[size]; //allocates array of null elements
        storageSize = size;
        numElems = 0;
    }

    public MaxHeap(Integer[] someArray){ //O(n logn)
        storageSize = someArray.length+1; //extra 1 to include null value at beginning
        storage = new Integer[storageSize]; 

        for (Integer i: someArray){
            insert(i); //repeatedly call insert to ensure all values are at the appropriate positions
        }
    }

    public void insert(int n){ //O(logn)
        //check if we have enough space
        if (numElems+1 >= storageSize){ //index 0 is always null so we can only store storageSize-1 values
            Integer[] temp = new Integer[storageSize*2]; //allocate array of double the size
            for (int i = 0; i < storageSize; i++){
                temp[i] = storage[i]; //transfer heap to temp array
            }
            storage = temp; //make temp our new heap
            storageSize = storageSize*2; //update size field
        }

        int index = numElems+1; //new value is initially stored at the end
        storage[index] = n;

        while (index/2 > 0){//bubble up from child to parent
            if (storage[index] > storage[index/2]){ //swap values if child is larger than parent
                Integer temp = storage[index];
                storage[index] = storage[index/2];
                storage[index/2] = temp;
            }
            index /= 2;
        }
        numElems++;
    }

    private int deleteMax(){//O(logn)
        int val = storage[1];

        storage[1] = storage[numElems];//make root the last element 
        storage[numElems] = null;//remove last element

        int index = 1;
        while (index*2 < numElems+1 && storage[index*2] != null){//bubble down until there are no elements left
            
            int biggerIndex = 0;
            if(storage[index*2+1] != null && storage[index*2] < storage[index*2+1]){
                biggerIndex = 1;//+1 to index correctly if the right number is bigger than the left 
            }

            if(storage[index] < storage[index*2+biggerIndex]){//check if parent is less than child
                Integer temp = storage[index]; //swap parent and child
                storage[index] = storage[index*2+biggerIndex];
                storage[index*2+biggerIndex] = temp;
            }
            index *= 2;//move down one level
            if(biggerIndex == 1){
                index++;//move to the right side if it was bigger
            }
        }
        numElems--;
        return val;
    }

    public String toString(){ //O(n)
        String output = new String();
        for (Integer i: storage){
            if (i != null){
                // output += Integer.toString(i) + " ";//add everything in the array that is not null, separated by spaces
                output += Integer.toString(i) + ", ";//add everything in the array that is not null, separated by comma+space
            }
        }
        return output;
    }

    public static void heapsort(Integer[] arrayToSort){ //O(n logn)
        MaxHeap sortedHeap = new MaxHeap(arrayToSort);

        for (int i = 0; i < arrayToSort.length; i++){
            arrayToSort[i] = sortedHeap.deleteMax();//array ordered in place
        }
    }

    public Integer[] getStorage() {
        return storage;
    }
    public int getStorageSize() {
        return storageSize;
    }
    public int getNumElems() {
        return numElems;
    }


    public int getCapacity(){
        return getStorageSize();
    }
    public int getSize(){
        return getNumElems();
    }
}