package com.example.musteri.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node<T extends Comparable<T>> {
	T value;
	Node<T> left;
	Node<T> right;

	public Node(T value) {
		this.value = value;
		right = null;
		left = null;
	}

}