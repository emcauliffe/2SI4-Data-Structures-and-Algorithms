class Main {
    public static void main(String[] args) {

        Integer[] peas = {5,12,40,30,55,60,50,45,6465,54,1,542,4,52,1,1,1,1,2,2-1,2,-1,2,-2,2,0,2,2-2,-2,-1,-14,654,132,65465,14654654,6514,65,6948,654654654,654,321,654,659,65918,49,169,4987,1,4,21,65465,1498,47651,654,651,6584,651,9846,5168,469581,684,651,684,651,654,621,354,98,651,6584,-14};

        // MaxHeap pasta = new MaxHeap(peas);

        MaxHeap.heapsort(peas);

        for (int i:peas){
            System.out.println(i);
        }

    }
}