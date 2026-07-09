package com.parking.parking_slot_booking_system.constant;



import java.util.Arrays;

class SegmentTree{
    int arr[];
    int n;
    int tree[];
    static final int mod = (int) (1e9+7);
    SegmentTree(int arr[]){
        this.arr = arr;
        n = arr.length;
        tree = new int[4*n];
    }

    void build(int root,int low,int high){
        if(low == high){
            tree[root] = arr[low];
            return;
        }

        int mid = low+(high-low)/2;
        build(2*root+1,low,mid);
        build(2*root+2,mid+1,high);
        int left = tree[2*root+1];
        int right = tree[2*root+2];
        long temp = 0;
        int t = right;
        if(left != 0) temp += left;
        if(right != 0){
            while(right > 0){
                temp = temp*10;
                right /= 10;
            }
        }
        if(t!=0) temp += t;
        tree[root] = (int) (temp%mod);
    }

    int search(int root,int low,int high,int l,int r){
        if(l<=low && high<= r) return tree[root];
        if(high < l || low > r) return 0;
        int mid = low+(high-low)/2;
        int left = search(2*root+1,low,mid,l,r);
        int right = search(2*root+2,mid+1,high,l,r);

        long temp = 0;
        int t = right;
        if(left != 0) temp += left;
        if(right !=0){
            while(right > 0){
                temp = temp*10;
                right /= 10;
            }
        }
        if(t != 0) temp += t;
        return (int) (temp%mod);
    }
}
public class Main {

    public static void main(String[] args) {
        int arr[] = {8,3,6,5,3,3,5,5,9,7,9,8,8,9,1,7,5,5,8,8};
        int []pref = new int[arr.length];
        pref[0] = arr[0];
        for(int i=1;i<arr.length;i++){
            pref[i] = arr[i]+pref[i-1];
        }
        SegmentTree st = new SegmentTree(arr);
        st.build(0, 0, arr.length-1);
        System.out.println(Arrays.toString(st.tree));
        int queries[][] = {{0,7},{1,3},{4,6}};
        int ans[] = new int[queries.length];
        int ind = 0;
        for(int[] query:queries){
            long t = st.search(0,0,arr.length-1,query[0],query[1]);
            int mul = pref[query[1]]-((query[0] > 0)?pref[query[0]-1]:0);
            System.out.println(t*mul);
        }
    }
}
