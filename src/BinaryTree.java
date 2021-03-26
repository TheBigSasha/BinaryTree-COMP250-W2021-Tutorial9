import java.util.Stack;

public class BinaryTree<E extends Comparable<E>>{
    /*
    <E> means
    This tree contains elements of any type

    extends Comparable<E> means
    The type that this tree can contain must be comparable to itself

    (I specify that Comparable compares E to E, so the class E, which
    can be any (comparable) class must be comparable to itself
     */

    private Node<E> root = null; //Same as the head of a linkedlist
    private int size = 0;

    public BinaryTree(){
    }

    public BinaryTree(E... elements){
        //This is shorthand for:
        //A method which takes an array && a method that takes 1 item && a method that takes a comma separated bunch of stuff in 1!
        //Examples:
        /*
            new BinaryTree("Hello", "World, "Blah");

            new BinaryTree("Hello");

            new BinaryTree(new String[]{});
         */

        //E... elements == E[] elements

        for(E element : elements){
            add(element);
        }

    }

    public void remove(E item){
        remove(item,root);
    }

    private Node<E> remove(E data, Node<E> subTreeRoot){
        if(subTreeRoot == null){            //We pass in null if a leaf node, then it is ez
            size--;
            return subTreeRoot;
        }

        if(data.compareTo(subTreeRoot.element) > 0){ //since compareTo returns 0 if they are equal,
            //if we have a nonzero return we know which direction to go but not what is there
            subTreeRoot.right  = remove(data,subTreeRoot.right);
            //Remove is a destructive process that can affect a big subtree, so we have to change our pointers
            //when we recurse

        }else if (data.compareTo(subTreeRoot.element) < 0){

            //Still not the one we're removing, gotta find it!
            subTreeRoot.left  = remove(data,subTreeRoot.left);

        }else{

            //---------------------- SIMPLE: One child removes -------------------------
            //Here, we remove!
            if(subTreeRoot.right == null){
                //subTreeRoot has one child, so we can just replace them
                return subTreeRoot.left; //replace the node with its left child
            }else if(subTreeRoot.left == null){
                return subTreeRoot.right;
            }

            //-------------------- HARDER: Two child removes ------------------------

            //Common question:
            //Why not left and right? Why just right?
            //A: This case has 2 children for sure so it will be possible for the right in all cases.

            subTreeRoot.element = smallest(subTreeRoot.right).element;
            subTreeRoot.right = remove(subTreeRoot.element, subTreeRoot.right);

            //We are swapping this one with the biggest of the ones smaller than it (the closest one in value)
            //And then we are removing it (its easy to remove because its a leaf)
        }
        return subTreeRoot;
    }

    private Node<E> smallest(Node<E> root){
        Node<E> smallest= root;
        Node<E> temp = root;
        while(temp.left != null){
            smallest = temp;
            temp = temp.left;
        }
        return  smallest;

    }

    /**
     * This method adds something to our tree.
     *
     * This is a BINARY SEARCH TREE so:
     * We add an element which we insert to left or right
     * depending on its value .compareTo.
     *
     * In assignments, you will have to do some other rule too!
     *
     * For example: Sort based on 2 variables, or you also
     * make sure the biggest one is on the root always or
     * something else... You will do more rules than this in
     * your assignments.
     *
     * Rules:
     *          if element > root, element goes to root.left
     *          if element < root, element goes to root.right
     *
     *          if root.right || root.left != null, recurse to subtree
     *          at root.left or root.right
     *
     * @param element
     */
    public void add(E element) {
        if(root == null){
            root = new Node<>(element);
        }else {
            add(element, root);
        }
    }

    /**
     * Common mistake:
     * Mix up > and <
     * Mix up left and right
     *
     * Time complexity: not LogN, but O(N)
     *
     * No rule states our tree must be balanced (height is low), so
     * we have to assume the worst case where it is O(N).
     *
     * Really, it is O(height)
     *
     * @param element
     * @param subTreeRoot
     */
    private void add(E element, Node<E> subTreeRoot){
        if(element.compareTo(subTreeRoot.element) > 0){ //TODO: Check I put the < the right way
            //This means go right
            if(subTreeRoot.right == null){
                subTreeRoot.right = new Node<>(element);    //This spot is vacant, insert here!
                size++;
            }else{
                add(element,subTreeRoot.right);     //This spot is taken, go to the right subtree and try again
            }
        }else{  //If we wanna shorten code, we can use a variable and have this logic once, but its not readable!
            //This means go right
            if(subTreeRoot.left == null){
                subTreeRoot.left = new Node<>(element);    //This spot is vacant, insert here!
                size++;
            }else{
                add(element,subTreeRoot.left);     //This spot is taken, go to the right subtree and try again
            }
        }
    }

    public Stack<E> inOrder(){
        if(root == null) return null;
        return inOrder(new Stack<>(), root);    //Starts the recursion
    }

    /**
     * Returns a sorted stack in O(N)
     * @param list
     * @param subTreeRoot
     * @return
     */
    private Stack<E> inOrder(Stack<E> list, Node<E> subTreeRoot){
        if(subTreeRoot.left != null){
            inOrder(list, subTreeRoot.left);        //Left
        }
        list.push(subTreeRoot.element);             //Center

        if(subTreeRoot.right != null){
            inOrder(list,subTreeRoot.right);        //Right
        }
        return list;
    }

    private class Node<E extends Comparable<E>>{
        E element;

        public Node(E element) {
            this.element = element;
        }

        Node<E> left;
        Node<E> right;

        //In assignments, we do not usually use comparable for our tree assignment
    }

    public static void main(String[] args){
        BinaryTree<Integer> tree = new BinaryTree<>(6,324,23,1,2,3,89,346,443,3134,13,-33,4);
        int a= tree.size;
        for(Integer i : tree.inOrder()){
            System.out.println(i);
        }
        System.out.println("=================================================");
        tree.remove(3134);
        for(Integer i : tree.inOrder()){
            System.out.println(i);
        }
        System.out.println("=================================================");

        tree.remove(6);
        for(Integer i : tree.inOrder()){
            System.out.println(i);
        }
        System.out.println("=================================================");

        tree.remove(346);
        for(Integer i : tree.inOrder()){
            System.out.println(i);
        }
        System.out.println("=================================================");

    }
}