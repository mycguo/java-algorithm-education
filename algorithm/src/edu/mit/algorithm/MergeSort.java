package edu.mit.algorithm;

public class MergeSort
{

    /**
     * @param args
     */
    public static void main(String[] args) {

        int[] input = {98, 39, 2, 13, 20, 7, 9, 12, 11, 1, 26};
       
       
        int[] c = new int[input.length];
        c= sort(input);
        System.out.println("Sorted Result: ");
        InsertionSort.printArray(c);

    }
    
    public static int[] sort (int[] a) {

        
        
        if (a.length==1)
            return a;
        else if (a.length==2) {
            if (a[0] > a[1]) {
                int tmp = a[0];
                a[0] = a[1];
                a[1] = tmp;
            }
            System.out.println("When size is two: ");
            InsertionSort.printArray(a);
            return a;
        } else {
            int[] aa = new int[a.length/2];
            int[] bb = new int[a.length - a.length/2];
           
            //split the array
            System.arraycopy(a, 0, aa, 0, aa.length);
            InsertionSort.printArray(aa);
            System.arraycopy(a,a.length/2,bb,0,bb.length);
            InsertionSort.printArray(bb);
            
            
            return merge(sort(aa), sort(bb));
            
            
        }
        
    }
    
    public static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length+b.length];
        System.out.println("merge input:");
        InsertionSort.printArray(a);
        InsertionSort.printArray(b);
        int r=0,i=0, j=0; //keep the current location for a, b in i, j
        //run the loop with the output using r
        for (r=0; r<=c.length-1; r++ ) {
            System.out.println("pick the " + r + " th elment");
            
            if (i>=a.length) {//if a is over, just copy b, from j onwards since it is not swapped yet
                c[r]=b[j];
                j++;
            }
            else if (j>=b.length) {
                c[r]=a[i];
                i++;
            }
            else { 
                //compare the first
                if (a[0] > b[0]) { 
                    c[r]=b[0];
                    j = j+1;
                    if (j < b.length)
                        b[0]=b[j];
                } else {
                    c[r]=a[0];
                    i=i+1;
                    if (i<a.length)
                        a[0]=a[i];
                }
            }

        }
        System.out.println("merge output:");
        InsertionSort.printArray(c);       
        return c;
        
        
    }

}
