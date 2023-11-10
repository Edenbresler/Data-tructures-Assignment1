import java.util.NoSuchElementException;

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    int minIndex;
    int maxIndex;
    int currentFree=0;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
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
        for (int i=1;i<currentFree;i=i+1){
            if (arr[i]==k){
                return i;
            }
        }
    	return -1;
    }

    @Override
    public void insert(Integer x) {
        if (currentFree==arr.length){
            throw new IllegalArgumentException();
        }
        arr[currentFree]=x;
        if (x>arr[maxIndex]){
            maxIndex=currentFree;
        }
        else if (x<arr[minIndex]){
            minIndex=currentFree;
        }

        stack.push(1);
        currentFree=currentFree+1;
    }

    @Override
    public void delete(Integer index) {
        if (index<0|index>=currentFree){
            throw new NoSuchElementException();
        }

        stack.push(arr[index]);
        stack.push(index);
        stack.push(2);

        if (index==maxIndex){
            maxIndex=predecessor(maxIndex);
        }
        else if (index==minIndex){
            minIndex=successor(minIndex);
        }
        if (minIndex==currentFree-1){
            minIndex=index;
        }
        else if (maxIndex==currentFree-1){
            maxIndex=index;
        }
        arr[index]=arr[currentFree-1];
        currentFree=currentFree-1;

    }

    @Override
    public Integer minimum() {
        if (currentFree==0){
            throw new NoSuchElementException();
        }
        return minIndex;

    }

    @Override
    public Integer maximum() {
        if (currentFree==0){
            throw new NoSuchElementException();
        }
        return maxIndex;
    }

    @Override
    public Integer successor(Integer index) {
        if (index==maximum()){  //Checking if index in bounds
            throw new IllegalArgumentException();
        }
        int temp=maximum();
        for (int i=0;i<currentFree;i=i+1){
            if (arr[i]>arr[index]&arr[i]<arr[temp]){
                temp=i;
            }
        }
        return temp;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (index==minimum()){  //Checking if index in bounds
            throw new IllegalArgumentException();
        }
        int temp=minimum();
        for (int i=0;i<currentFree;i=i+1){
            if (arr[i]<arr[index]&arr[i]>arr[temp]){
                temp=i;
            }
        }
        return temp;
    }

    @Override
    public void backtrack() {
        if (stack.isEmpty()){
            return;
        }
        int pointer=(int)stack.pop();
        if (pointer==1){    //Insert
            currentFree=currentFree-1;
            if (currentFree==minIndex){
                minIndex=successor(minIndex);
            }
            if (currentFree==maxIndex){
                minIndex=predecessor(maxIndex);
            }
        }
        else if (pointer==2){    //Delete
            int insertIndex=(int)stack.pop();
            int insertVal=(int)stack.pop();
            if (minIndex==insertIndex){
                minIndex=currentFree;
            }
            else if (maxIndex==insertIndex){
                maxIndex=currentFree;
            }
            arr[insertIndex]=insertVal;
            currentFree=currentFree+1;
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
        for (int i=0;i<currentFree-1;i=i+1){
            System.out.print(arr[i]+" ");
        }
        System.out.print(arr[currentFree-1]);
    }
    
}
