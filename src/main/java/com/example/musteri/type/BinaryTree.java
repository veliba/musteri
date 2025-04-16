package com.example.musteri.type;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinaryTree<T extends Comparable<T>> {

	private Node<T> root;

	public void add(T value) {
		root = addRecursive(root, value);
	}

	private Node<T> addRecursive(Node<T> current, T value) {
		if (current == null) {
			return new Node<T>(value);
		}

		if (value.compareTo(current.getValue()) < 0) {
			current.setLeft(addRecursive(current.getLeft(), value));
		} else if (value.compareTo(value) > 0) {
			current.setRight(addRecursive(current.getRight(), value));
		} else {
			// value already exists
			return current;
		}

		return current;
	}

	public List<T> toSortedList() {
		List<T> sortedList = new ArrayList<>();
		inOrderTraversal(root, sortedList);
		return sortedList;
	}

	private void inOrderTraversal(Node<T> node, List<T> sortedList) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), sortedList);
			sortedList.add(node.getValue());
			inOrderTraversal(node.getRight(), sortedList);
		}
	}

}