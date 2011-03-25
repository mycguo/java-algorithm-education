package edu.mit.algorithm;
/* Adopted from MIT:
 * http://ocw.mit.edu/OcwWeb/Electrical-Engineering-and-Computer-Science/6-046JFall-2005/LectureNotes/index.htm
 * 
 *  */
public class InsertionSort
{

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int[] a = {8,2,4,9,3,6};
        
        int size= a.length;
        
        //start from second elment
        for (int j=1;j<size;j++) {
            int key = a[j];
            int i=j-1;
            while (i>=0 && a[i] > key) { //use while loop 
                a[i+1] = a[i];
                i--;
            }
            a[i+1] = key;
            printArray(a);
        }
        System.out.println("Sorted Array: " );
        printArray(a);
    }
    
    public static void printArray(int[] a) {
        int i=0;
        while ( i <a.length) {
            System.out.print(" " + a[i]);
            i++;
        }
        System.out.println("\n");
    }

}
