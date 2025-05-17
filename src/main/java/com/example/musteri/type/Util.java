package com.example.musteri.type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Util {

	private Util() {
		// Prevent instantiation
	}

	/**
	 * Sorts an array using the bubble sort algorithm.
	 *
	 * @param arr the array to be sorted
	 * @return the sorted array
	 */
	public static int[] bubleSort(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					// swap arr[j] and arr[j+1]
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		return arr;
	}

	/**
	 * Sorts an array using the tree sort algorithm.
	 *
	 * @param arr the array to be sorted
	 * @return a list containing the sorted elements
	 */
	public static List<Integer> treeSort(int[] arr) {
		BinaryTree<Integer> tree = new BinaryTree<>();
		for (int i : arr) {
			tree.add(i);
		}

		return tree.toSortedList();
	}

	/**
	 * Gets the value of a property from an object using reflection.
	 *
	 * @param o    the object from which to get the property
	 * @param prop the name of the property
	 * @return the value of the property
	 */
	public static Object getProp(Object o, String prop) {
		if (o == null) {
			throw new IllegalArgumentException("Target object cannot be null");
		}
		if (prop == null || prop.trim().isEmpty()) {
			throw new IllegalArgumentException("Property name cannot be null or empty");
		}

		try {
			Field field = o.getClass().getDeclaredField(prop);
			field.setAccessible(true);
			return field.get(o);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			throw new IllegalArgumentException(
					"Failed to access field '" + prop + "' on object of type " + o.getClass().getName(), e);
		}
	}

	/**
	 * Gets a sorted list from a list of comparable elements.
	 *
	 * @param l the list to be sorted
	 * @return a sorted list
	 */
	public static <T extends Comparable<T>> List<T> getSortedList(List<T> l) {
		Set<T> s = new TreeSet<>(new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});

		for (T t : l) {
			s.add(t);
		}

		return new ArrayList<>(s);
	}

	/**
	 * Gets a sorted list from a list of elements based on a specified property.
	 *
	 * @param l               the list to be sorted
	 * @param comparableField the name of the property to sort by
	 * @return a sorted list
	 */
	public static <K, V extends Comparable<V>> List<K> getSortedList(List<K> l, final String comparableField) {
		Set<K> s = new TreeSet<>(new Comparator<K>() {

			@SuppressWarnings("unchecked")
			@Override
			public int compare(K o1, K o2) {
				V c1 = (V) getProp(o1, comparableField);
				V c2 = (V) getProp(o2, comparableField);
				return c1.compareTo(c2);
			}
		});

		for (K k : l) {
			s.add(k);
		}

		return new ArrayList<>(s);
	}

}