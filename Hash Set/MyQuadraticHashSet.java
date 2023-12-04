//package hash;
package com.company;
import java.util.Arrays;
// MyQuadraticHashSet.java
// Implements a hash table of objects, using open addressing with quadratic probing.
// - Jeff Ward

public class MyQuadraticHashSet<E> implements MySet<E> {
	private int size = 0; // The number of elements in the set

	private Object[] table; // The table elements. Initially all null.

	private double maxLoadFactor; // size should not exceed (table.length *
									// maxLoadFactor)

	private int thresholdSize; // (int) (table.length * maxLoadFactor)
								// When size + numRemoved exceeds this value,
								// then resize and rehash.

	private int[] tableSizes; // A set of prime numbers that will be used as the
								// hash table sizes.

	private int nextTableSizeIndex = 0; // Index into tableSizes.

	/*
	 * REMOVED is stored in the hashTable in place of a removed element. This
	 * lets the quadratic probing process know that it may need to continue
	 * probing.
	 */
	private final static Object REMOVED = new Object();

	private int numRemoved = 0; // The number of times that REMOVED occurs in the table.

	private int probeIndex(int hashCode, long probeCount, int tableLength) {
		return (int) ((hashCode % tableLength + tableLength + probeCount * probeCount) % tableLength);
	}


	public MyQuadraticHashSet(double maxLoadFactor, int[] tableSizes) {
		this.maxLoadFactor = maxLoadFactor;
		this.tableSizes = tableSizes;
		this.thresholdSize = (int) (tableSizes[nextTableSizeIndex] * maxLoadFactor);
		this.table = new Object[tableSizes[nextTableSizeIndex]];
	}

	public void clear() {
		Arrays.fill(table, null);
		size = 0;
		numRemoved = 0;
	}

	public boolean contains(E e) {
		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, table.length);

		while (table[index] != null) {
			if (table[index] != REMOVED && e.equals(table[index])) {
				return true;
			}
			probeCount++;
			index = probeIndex(hashCode, probeCount, table.length);
		}

		return false;
	}


	public boolean add(E e) {
		if (size + numRemoved >= thresholdSize) {
			rehash();
		}

		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, table.length);

		while (table[index] != null && table[index] != REMOVED) {
			if (e.equals(table[index])) {
				return false; // Element already exists
			}
			probeCount++;
			index = probeIndex(hashCode, probeCount, table.length);
		}

		table[index] = e;
		size++;
		return true;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		nextTableSizeIndex++;
		Object[] oldTable = table;
		table = new Object[tableSizes[nextTableSizeIndex]];
		thresholdSize = (int) (tableSizes[nextTableSizeIndex] * maxLoadFactor);
		size = 0;
		numRemoved = 0;

		for (Object element : oldTable) {
			if (element != null && element != REMOVED) {
				add((E) element);
			}
		}
	}

	public boolean remove(E e) {
		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, table.length);

		while (table[index] != null) {
			if (table[index] != REMOVED && e.equals(table[index])) {
				table[index] = REMOVED;
				size--;
				numRemoved++;
				return true;
			}
			probeCount++;
			index = probeIndex(hashCode, probeCount, table.length);
		}

		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public java.util.Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}
}
