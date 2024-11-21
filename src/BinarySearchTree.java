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
        }
        if(root.data.compareTo(data) > 1){
            return insertRecursively(root.left, data);
        } else {
            return insertRecursively(root.right, data);
        }
    }
    @Override
    public void remove(E data) {

    }

    @Override
    public boolean search(E data) {
        return false;
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