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
            return new Node<E>(data);
        } else if (root.data.compareTo(data) > 0) {
            root.left = insertRecursively(root.left, data);
        }else if (root.data.compareTo(data) <= 0) {
            root.right = insertRecursively(root.right, data);
        }
        return root;
    }

    @Override
    public void remove(E data) {
        root = removeRecursively(data, root);
    }
    private Node<E> removeRecursively(E data, Node<E> curr){
        if(curr == null){
            return null;
        }

        if(curr.data.compareTo(data) > 0){
            curr.left = removeRecursively(data, curr.left);
        } else if (curr.data.compareTo(data) < 0) {
            curr.right =removeRecursively(data, curr.right);
        } else{
            if(curr.left == null && curr.right == null){
                // leaf node
                return null;
            } else if (curr.left == null){
                // node with right child
                return curr.right;
            } else if (curr.right == null){
                // node with left child
                return curr.left;
            } else{
                // node with both children
                Node<E> iop = findIOP(curr);
            }
        }
        return curr;
    }

    private void removeOneChild(Node<E> curr, Node<E> parent){
        if(parent.right.data.compareTo(curr.data) == 0){
            parent.right = null;
            System.out.println("REMOVED right child");
        } else{
            parent.left = null;
            System.out.println("REMOVED left child");
        }
    }

    private Node<E>[] getNode(Node<E> curr, E data, Node<E> root) {
        if(curr.data.compareTo(data) == 0){
            return new Node[] {curr, root};
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