package edu.mit.algorithm;

public class QuickSort
{

    public static int[] a = { 6,10,13,5,8,3,2,11};
    public static int count=0;
    
    //public static int[] a = { 13,10,11};
    
    //public static int[] a = { 13,10,11};
    public static void main(String[] argv) {
        count++;
        quickSort( 0,a.length-1);

        
    }
    
    public static void quickSort( int p,int r) {
        count++;
        
        
        if (p <r ) {
            int q=partiton(p,r);
            System.out.println("total number of loop:" + count);

            
            System.out.println("Pivot location: " + q);
            InsertionSort.printArray(a);
            quickSort(p,q-1);
            quickSort(q+1,r);
        }
        
    }
    
    public static int partiton(int p, int r) {

        int length = r-p+1;
        
        int pivot = a[p];
        int i =p;

        if (length==2) {//should not call if length is one, swap if it is not in order
            
            if (a[p] > a[r]) {

                int tmp=a[p];
                a[p]=a[r];
                a[r]=tmp;
            }
            return p+1; //the pivot is second one in this case   
        }
        else  { 

            for (int j=p+1;j<p+length;j++) {  //ending should be p+length, speed two hour on finding this !!
                 if (a[j] <=pivot) {
                    
                    i++;
                    
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
               
            }
            
            int tmp = a[p];
            a[p]=a[i];
            a[i]=tmp;
            
            //a[p]=a[i];
            //a[i]=pivot;
            
            
        }

        return i;
    }
    
}
