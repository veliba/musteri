package com.example.musteri;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.musteri.type.Util;

class UtilTest {

	@Test
	void bubleSortSortsArrayInAscendingOrder() {
		int[] input = { 5, 3, 8, 1, 2 };
		int[] expected = { 1, 2, 3, 5, 8 };
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void bubleSortHandlesEmptyArray() {
		int[] input = {};
		int[] expected = {};
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void bubleSortHandlesSingleElementArray() {
		int[] input = { 42 };
		int[] expected = { 42 };
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void bubleSortSortsArrayWithPositiveNumbers() {
		int[] input = { 5, 3, 8, 6, 2 };
		int[] expected = { 2, 3, 5, 6, 8 };
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void bubleSortHandlesArrayWithNegativeNumbers() {
		int[] input = { -3, -1, -7, -4 };
		int[] expected = { -7, -4, -3, -1 };
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void bubleSortHandlesArrayWithDuplicates() {
		int[] input = { 4, 2, 4, 2, 1 };
		int[] expected = { 1, 2, 2, 4, 4 };
		assertArrayEquals(expected, Util.bubleSort(input));
	}

	@Test
	void treeSortSortsArrayInAscendingOrder() {
		int[] input = { 7, 3, 9, 1 };
		List<Integer> expected = Arrays.asList(1, 3, 7, 9);
		assertEquals(expected, Util.treeSort(input));
	}

	@Test
	void treeSortHandlesEmptyArray() {
		int[] input = {};
		List<Integer> expected = Arrays.asList();
		assertEquals(expected, Util.treeSort(input));
	}

	@Test
	void treeSortHandlesArrayWithNegativeNumbers() {
		int[] input = { -3, -1, -7, -4 };
		List<Integer> expected = List.of(-7, -4, -3, -1);
		assertEquals(expected, Util.treeSort(input));
	}

	@Test
	void treeSortHandlesArrayWithDuplicates() {
		int[] input = { 4, 2, 4, 2, 1 };
		List<Integer> expected = List.of(1, 2, 4);
		assertEquals(expected, Util.treeSort(input));
	}

	@Test
	void getPropReturnsFieldValue() {
		class Sample {
			private String name = "test";
		}
		Sample sample = new Sample();
		assertEquals("test", Util.getProp(sample, "name"));
	}

	@org.junit.jupiter.api.Test
	void getPropHandlesPrivateFieldAccess() {
		class TestObject {
			private final String secret = "hidden";
		}

		TestObject obj = new TestObject();
		assertEquals("hidden", Util.getProp(obj, "secret"));
	}

	@org.junit.jupiter.api.Test
	void getPropHandlesInheritedField() {
		class Parent {
			protected String inheritedField = "parentValue";
		}
		class Child extends Parent {
		}

		Child child = new Child();
		assertEquals("parentValue", Util.getProp(child, "inheritedField"));
	}

	@org.junit.jupiter.api.Test
	void getPropThrowsExceptionForStaticField() {
		class TestObject {
			public static final String STATIC_FIELD = "staticValue";
		}

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> Util.getProp(new TestObject(), "STATIC_FIELD"));
		assertTrue(exception.getMessage().contains("Failed to access field"));
	}

	@org.junit.jupiter.api.Test
	void getPropHandlesFieldWithNullValue() {
		class TestObject {
			private final String nullableField = null;
		}

		TestObject obj = new TestObject();
		assertNull(Util.getProp(obj, "nullableField"));
	}

	@Test
	void getPropThrowsExceptionForInvalidField() {
		class Sample {
			private String name = "test";
		}
		Sample sample = new Sample();
		assertThrows(IllegalArgumentException.class, () -> Util.getProp(sample, "invalidField"));
	}

	@Test
	void getSortedListSortsListOfComparableElements() {
		List<Integer> input = Arrays.asList(5, 3, 8, 1);
		List<Integer> expected = Arrays.asList(1, 3, 5, 8);
		assertEquals(expected, Util.getSortedList(input));
	}

	@Test
	void getSortedListHandlesEmptyList() {
		List<Integer> input = Arrays.asList();
		List<Integer> expected = Arrays.asList();
		assertEquals(expected, Util.getSortedList(input));
	}

	@Test
	void getSortedListSortsListByComparableField() {
		class Sample {
			private final int value;

			Sample(int value) {
				this.value = value;
			}

			public int getValue() {
				return value;
			}
		}
		List<Sample> input = Arrays.asList(new Sample(5), new Sample(3), new Sample(8), new Sample(1));
		List<Sample> sorted = Util.getSortedList(input, "value");
		assertEquals(1, sorted.get(0).getValue());
		assertEquals(8, sorted.get(3).getValue());
	}
}