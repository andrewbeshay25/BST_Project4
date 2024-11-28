/*
 *
 *  BinarySearchTree.java
 *
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<? super E>> extends BinaryTree<E> {

    @Override
    public void insert(E data) {
        root = insertRecursively(root, data);
    }

    private Node<E> insertRecursively(Node<E> root, E data){
        if(root == null){
            Node<E> newNode = new Node<E>(data);
            return newNode;
        } else if (root.data.compareTo(data) > 0) {
            root.left = insertRecursively(root.left, data);
        }else if (root.data.compareTo(data) <= 0) {
            root.right = insertRecursively(root.right, data);
        }
        return root;
    }

    @Override
    public void remove(E data) {
     if (search(data)){
         Node<E>[] arr = getNode(root, data, root);
         Node<E> curr = arr[0];
         Node<E> parent = arr[1];

         System.out.println(curr.data + " CHILD OF " + parent.data);

         if(curr.right == null && curr.left==null){
             // leaf node
             removeLeaf(curr);
         } else if(curr.right == null || curr.left==null){
             // one child node
             removeOneChild(curr, parent);
         }
     }
    }

    private void removeLeaf(Node<E> curr){
        curr = null;
    }

    private void removeOneChild(Node<E> curr, Node<E> parent){

    }

    private Node<E>[] getNode(Node<E> curr, E data, Node<E> parent) {
        if(curr.data.compareTo(data) == 0){
            return new Node[] {curr, parent};
        }
        if(curr.data.compareTo(data) > 0){
            return getNode(curr.left, data, curr);
        } else{
            return getNode(curr.right, data, curr);
        }
    }


    private Node<E> findIOP(Node<E> curr) {
        curr = curr.left;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    @Override
    public boolean search(E data) {
       return searchRecursively(root, data);
    }

    private boolean searchRecursively(Node<E> root, E data){
        if (data == null || root == null){
            return false;
        }

        if(root.data.compareTo(data) == 0){
            return true;
        } else if (data.compareTo(root.data) > 0) {
            return searchRecursively(root.right, data);
        } else{
            return searchRecursively(root.left, data);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<E> {
        private Stack<Node<E>> stack;

        public InOrderIterator() {
            stack = new Stack<>();
            pushLeft(root);
        }

        private void pushLeft(Node<E> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Node<E> node = stack.pop();
            E result = node.data;

            if (node.right != null) {
                pushLeft(node.right);
            }

            return result;
        }
    }
}