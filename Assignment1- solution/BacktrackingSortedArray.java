import java.util.NoSuchElementException;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    int currentFree=0;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }
    
    @Override
    public Integer get(int index){
        if (index<0|index>=currentFree){
            throw new NoSuchElementException();
        }
        return arr[index];
    }
    @Override
    public Integer search(int k) {
        if (currentFree==0){
            return -1;
        }
        int left=0;
        int right=currentFree-1;
        int mid=0;
        int res=0;
        boolean isFound=false;

        while (left<=right&!isFound) {
            mid=(left+right)/2;
            if (arr[mid] == k) {
                res= mid;
                isFound= true;
            }
            else {
                if (arr[mid] < k) {
                    left=mid + 1;
                }
                else if (arr[mid] > k) {
                    right=mid - 1;
                }
            }
        }
        if (isFound){
            return res; 
        }
        return -1;

    }

    @Override
    public void insert(Integer x) {
        if (currentFree==arr.length){
            throw new IllegalArgumentException();
        }
        int left=0;
        int right=currentFree-1;
        int mid=0;
        int res=0;
        boolean isFound=false;
        if (currentFree==0){
            arr[currentFree]=x;
            stack.push(0);
            stack.push(1);
            currentFree=currentFree+1;
        }
        else if (x<arr[minimum()]){
            for (int i=currentFree;i>0;i=i-1){
                arr[i]=arr[i-1];
            }
            arr[0]=x;
            stack.push(0);
            stack.push(1);
            currentFree=currentFree+1;
        }
        else if (x>arr[maximum()]){
            arr[currentFree]=x;
            stack.push(currentFree);
            stack.push(1);
            currentFree=currentFree+1;
        }
        else {
            while (left<=right&!isFound) {
                mid=(left+right)/2;
                if (mid==0&currentFree==1){
                    arr[currentFree]=x;
                    stack.push(currentFree);
                    stack.push(1);
                    currentFree=currentFree+1;
                    isFound=true;
                }
                else if (arr[mid-1]< x&arr[mid]>x) {
                    currentFree=currentFree+1;
                    for (int i=currentFree-1;i>mid;i=i-1){
                        arr[i]=arr[i-1];
                    }
                    arr[mid]=x;
                    stack.push(mid);
                    stack.push(1);

                    isFound=true;
                }
                else {
                    if (arr[mid] <x ) {
                        left=mid+1;
                    }
                    else if (arr[mid] >x ) {
                        right=mid-1;
                    }
                }
            }
        }
    }

    @Override
    public void delete(Integer index) {
        if (index<0|index>=currentFree){
            throw new NoSuchElementException();
        }

        stack.push(index);
        stack.push(arr[index]);
        stack.push(2);
        for (int i=index;i<currentFree-1;i=i+1){
            arr[i]=arr[i+1];
        }

        currentFree=currentFree-1;

    }

    @Override
    public Integer minimum() {
        if (currentFree==0){
            throw new IllegalArgumentException();
        }
        return 0;
    }

    @Override
    public Integer maximum() {
        if (arr.length==0){
            throw new IllegalArgumentException();
        }
        return currentFree-1;
    }

    @Override
    public Integer successor(Integer index) {
        if (index>arr.length-2|index<0){
            throw new IllegalArgumentException();
        }
        return index+1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index==0|index>currentFree-1){  //Checking if index in bounds
            throw new IllegalArgumentException();
        }
        return index-1;
    }


    @Override
    public void backtrack() {
        if (stack.isEmpty()) {
            return;
        }
        int pointer = (int) stack.pop();
        if (pointer == 1) {    //Insert
            int insertIndex = (int) stack.pop();
            for (int i = insertIndex; i < currentFree - 1; i = i + 1) {
                arr[i] = arr[i + 1];
            }
            currentFree = currentFree - 1;
        }

        else if (pointer == 2) {     //Delete
            int insertVal = (int) stack.pop();
            int insertIndex = (int) stack.pop();
            currentFree = currentFree + 1;
            for (int i = currentFree - 1; i > insertIndex; i = i - 1) {
                arr[i] = arr[i - 1];
            }
            arr[insertIndex] = insertVal;
        }
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
    public void print() {
        if (currentFree!=0) {
            for (int i = 0; i < currentFree - 1; i = i + 1) {
                System.out.print(arr[i] + " ");
            }
            System.out.print(arr[currentFree - 1]);
        }
    }
}
