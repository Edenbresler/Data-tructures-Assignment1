

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        int index=0;
        int res=0;
        int stepInd=1;
        boolean isFound=false;
        while (!isFound&index<arr.length){
            if (arr[index]==x){
                res= index;
                isFound=true;
            }
            else if (stepInd%forward!=0){
                myStack.push(arr[index]);
                index=index+1;
                stepInd=stepInd+1;
            }
            else{    //doing backtrack
                while (!isFound&stepInd>=forward-back){
                    int check=(int)myStack.pop();
                    index=index-1;
                    stepInd=stepInd-1;
                    if (check==x){
                        res= index;
                        isFound=true;
                    }
                }
                stepInd=1;
            }
        }
        if (!isFound){
            return -1;
        }
    	return res ;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        if (arr.length==0){
            return -1;
        }
        int left=0;
        int right=arr.length-1;
        int res=0;
        boolean isFound=false;
        int mid=0;
        int inconsistencies = 0;
        while (left<=right&!isFound) {
            mid=(left+right)/2;
            if (arr[mid] == x) {
                res = mid;
                isFound = true;
            }
            else {
                if (inconsistencies == 0) {
                    myStack.push(mid);
                    myStack.push(left);
                    myStack.push(right);
                }
                else {
                    while (inconsistencies > 0) {
                        if (!myStack.isEmpty()){
                            right = (int) myStack.pop();
                            left = (int) myStack.pop();
                            mid = (int) myStack.pop();
                        }
                        inconsistencies = inconsistencies - 1;
                    }
                }
                if (arr[mid] < x) {
                    inconsistencies = Consistency.isConsistent(arr);
                    left = mid + 1;
                }
                else if (arr[mid] > x) {
                    right = mid - 1;
                }
            }
        }
        if (!isFound){
            return -1;
        }
    	return res;
    }
    
}
