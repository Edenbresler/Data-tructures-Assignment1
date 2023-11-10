

import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
    	if (root == null) {
    		throw new NoSuchElementException("empty tree has no root");
    	}
        return root;
    }
	
    public Node search(int k) {
        Node temp=root;
        while (temp!=null){
            if (k==temp.getKey()){
                return temp;
            }
            else if (k<temp.getKey()){
                temp=temp.left;
            }
            else if (k>temp.getKey()){
                temp=temp.right;
            }
        }
    	return null;
    }



    public void insert(Node node) {
        redoStack.clear(); // We can use retrack only after backtrack.
        insertReal(node);
    }
    public void insertReal(Node node) {
        Node parent=null;
        Node temp=root;
        Node[] nodeArr=new Node[2];
        Node indNode= new Node(2,null);   //We are in INSERT
        nodeArr[0]=indNode;
        nodeArr[1]=node;
        stack.push(nodeArr);
        while (temp!=null){
            parent=temp;
            if (node.getKey()<temp.getKey()){
                temp=temp.left;
            }
            else{
                temp=temp.right;
            }
        }
        node.parent=parent;
        if (parent==null){
            root=node;
        }
        else if (node.getKey()< parent.getKey()){
            parent.left=node;
        }
        else{
            parent.right=node;
        }
    }

    public void delete(Node node) {
        redoStack.clear();   // We can use retrack only after backtrack.
        deleteReal(node);
    }
    public void deleteReal(Node node) {
        Node indNode= new Node(1,null);      //We are in DELETE
        if (node.left==null&node.right==null){
            if (node==root){
                Node[] nodeArr=new Node[3];
                nodeArr[0]=indNode;
                Node caseNode= new Node(1,null);
                nodeArr[1]=caseNode;
                nodeArr[2]=root;
                root=null;
            }
            else {
                Node parent=node.parent;
                Node[] nodeArr=new Node[4];
                nodeArr[0]=indNode;
                Node caseNode= new Node(2,null);
                nodeArr[1]=caseNode;
                nodeArr[2]=node;
                if (node==parent.right){
                    parent.right=null;
                    nodeArr[3]=new Node(1,null);;
                }
                else{
                    parent.left=null;
                    nodeArr[3]=new Node(2,null);;
                }
                stack.push(nodeArr);
            }
        }

        else if (node.left!=null&node.right!=null){         //Two sons
            Node[] nodeArr=new Node[6];
            nodeArr[0]=indNode;
            Node caseNode= new Node(5,null);
            Node successor=successor(node);
            nodeArr[1]=caseNode;
            nodeArr[2]=node;
            Node succCopy=new Node(successor.getKey(), successor.getValue());
            succCopy.parent=successor.parent;
            succCopy.left=successor.left;
            succCopy.right=successor.right;
            nodeArr[4]=succCopy;
            Node rootRight=root;
            Node rootLeft=root.left;
            if (successor.parent.right!=null&&successor.parent.right.equals(successor)){
                nodeArr[5]=new Node(1,null);   //Direct son of node
            }
            else{
                nodeArr[5]=new Node(2,null);   //offspring of node
            }
            //delete(successor);
            if (successor.parent.right!=null&&successor.parent.right.equals(successor)){
                successor.parent.right=successor.right;
                if (successor.right!=null){
                    rootRight=successor.right;
                }
            }
            else{
                successor.parent.left=successor.right;
                if (successor.right!=null){
                    rootRight=successor.right;
                }

            }
            Node parent=node.parent;
            if (parent==null){           // root
                nodeArr[3]=new Node(1,null);
                successor.left=root.left;
                successor.right=root.right;
                root=successor;
                successor.parent=null;
                rootLeft.parent=root;
                rootRight.parent=root;
            }
            else if (node==parent.left){        //The node is left son
                nodeArr[3]=new Node(2,null);;
                parent.left=successor;
                successor.left=node.left;
                successor.right=node.right;
                successor.parent=parent;
            }
            else if (node==parent.right){    //The node is right son
                nodeArr[3]=new Node(3,null);
                parent.right=successor;
                successor.left=node.left;
                successor.right=node.right;
                successor.parent=parent;
            }
            stack.push(nodeArr);
        }
        else if (node.right==null){    //Only left son
            Node parent=node.parent;
            Node[] nodeArr=new Node[4];
            Node caseNode= new Node(3,null);
            nodeArr[0]=indNode;
            nodeArr[1]=caseNode;
            nodeArr[2]=node;
            if (parent==null){
                nodeArr[3]= new Node(1,null);  // The node is root
                root=node.left;
                root.parent=null;
                }
            else {
                if (node==parent.right){
                    nodeArr[3]= new Node(2,null);  // The node is right son
                    parent.right=node.right;
                    parent.right.parent=parent;
                }
                else
                    nodeArr[3]= new Node(3,null);  // The node is left son
                    parent.left=node.left;
                    parent.left.parent=parent;
            }
            stack.push(nodeArr);
        }
        else {                              //Only right son
            Node parent=node.parent;
            Node[] nodeArr=new Node[4];
            Node caseNode= new Node(4,null);
            nodeArr[0]=indNode;
            nodeArr[1]=caseNode;
            nodeArr[2]=node;
            if (parent==null) {
                root = node.right;
                root.parent = null;
                nodeArr[3]=new Node(1,null);
            }
            else {
                if (node==parent.right){
                    parent.right=node.right;
                    parent.right.parent=parent;
                    nodeArr[3]=new Node(2,null);
                }
                else {
                    parent.left = node.right;
                    parent.left.parent = parent;
                    nodeArr[3]=new Node(3,null);
                }
            }
            stack.push(nodeArr);
        }
    }

    public Node minimum() {
        Node temp=root;
        if (temp==null){
            throw new IllegalArgumentException();
        }
        while (temp.left!=null){
            temp=temp.left;
        }
    	return temp;
    }

    public Node maximum() {
        Node temp=root;
        if (temp==null){
            throw new IllegalArgumentException();
        }
        while (temp.right!=null){
            temp=temp.right;
        }
        return temp;
    }

    public Node successor(Node node) {
        Node temp=node;
        if (temp.right!=null){
            temp=temp.right;
            while (temp.left!=null){
                temp=temp.left;
            }
            return temp;
        }
        Node parent=node.parent;
        while (parent!=null && temp==parent.right){
            temp=parent;
            parent=temp.parent;
        }
    	return parent;
    }

    public Node predecessor(Node node) {
        Node temp=node;
        if (temp.left!=null){
            temp=temp.left;
            while (temp.right!=null){
                temp=temp.right;
            }
            return temp;
        }
        Node parent=node.parent;
        while (parent!=null && temp==parent.left){
            temp=parent;
            parent=temp.parent;
        }
        return parent;
    }

    @Override
    public void backtrack() {
        if (stack.isEmpty()){
            return;
        }
        Node[] arr=(Node[])stack.pop();
        redoStack.push(arr);
        if (arr[0].getKey()==1){    // We are in DELETE
            if (arr[1].getKey()==1){   // Lonely root
                root=arr[3];
            }
            else if (arr[1].getKey()==2){  // Leaf
                if (arr[3].getKey()==1){
                    arr[2].parent.right=arr[2];
                }
                else{
                    arr[2].parent.left=arr[2];
                }
            }
            else if (arr[1].getKey()==3){    //One left son
                if (arr[3].getKey()==1){         //we are the root
                    root=arr[2];
                    root.left.parent=root;
                }
                else if (arr[3].getKey()==2){    //we are the right son
                    arr[2].parent.right=arr[2];
                    arr[2].left.parent=arr[2];
                }
                else{                            //we are the left son
                    arr[2].parent.left=arr[2];
                    arr[2].left.parent=arr[2];
                }
            }
            else if (arr[1].getKey()==4){     //One right son
                if (arr[3].getKey()==1){         //we are the root
                    root=arr[2];
                    root.right.parent=root;
                }
                else if (arr[3].getKey()==2){    //we are the right son
                    arr[2].parent.right=arr[2];
                    arr[2].right.parent=arr[2];
                }
                else{                            //we are the left son
                    arr[2].parent.left=arr[2];
                    arr[2].right.parent=arr[2];
                }
            }
            else if (arr[1].getKey()==5){      //Two sons
                if (arr[3].getKey()==1){     //the node is root
                    if (arr[5].getKey()==1){   //Succs is direct son
                        root=arr[2];
                        root.right=arr[4];
                        root.left.parent=root;
                        root.right.parent=root;
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                    else{                      //Succs is offspring of node
                        root=arr[2];
                        arr[4].parent.left=arr[4];
                        root.left.parent=root;
                        root.right.parent=root;
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                }
                else if (arr[3].getKey()==2){     //the node is left son
                    if (arr[5].getKey()==1){     //Succs is direct son
                        arr[2].parent.left=arr[2];
                        arr[4].parent.right=arr[4];
                        arr[2].left.parent=arr[2];
                        arr[2].right.parent=arr[2];
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                    else{                       //Succs is offspring of node
                        arr[2].parent.left=arr[2];
                        arr[4].parent.left=arr[4];
                        arr[2].left.parent=arr[2];
                        arr[2].right.parent=arr[2];
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                }
                else if (arr[3].getKey()==3){     //the node is right son
                    if (arr[5].getKey()==1){     //Succs is direct son
                        arr[2].parent.right=arr[2];
                        arr[4].parent.right=arr[4];
                        arr[2].left.parent=arr[2];
                        arr[2].right.parent=arr[2];
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                    else{                       //Succs is offspring of node
                        arr[2].parent.right=arr[2];
                        arr[4].parent.left=arr[4];
                        arr[2].left.parent=arr[2];
                        arr[2].right.parent=arr[2];
                        if (arr[4].left!=null){
                            arr[4].left.parent=arr[4];
                        }
                        if (arr[4].right!=null){
                            arr[4].right.parent=arr[4];
                        }
                    }
                }
            }

        }
        else if (arr[0].getKey()==2){      //We are in INSERT
            if(arr[1].equals(arr[1].parent.left) ){
                arr[1].parent.left=null;
            }
            else{
                arr[1].parent.right=null;
            }

        }
    }

    @Override
    public void retrack() {
        if (redoStack.isEmpty()){
            return;
        }
        Node[] arr= (Node[])redoStack.pop();
        if(arr[0].getKey()==1){
            deleteReal(arr[2]);
        }
        else{
            insertReal(arr[2]);
        }
    }



    public void printPreOrder2(Node node) {
        System.out.print(node.getKey()+" ");
        if (node.left!=null){
            printPreOrder2(node.left);
        }
        if (node.right!=null){
            printPreOrder2(node.right);
        }
    }
    public void printPreOrder(){
        printPreOrder2(root);
    }

    @Override
    public void print() {
    	printPreOrder();
    }
    public static class Node {
    	// These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        
        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }



    }

}
