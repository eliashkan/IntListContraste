package be.intecbrussel;

import java.util.Arrays;

public class IntList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int[] EMPTY_ARRAY = {};
    private int[] dataArray;
    private int elementCount;

    public IntList() {
        this(DEFAULT_CAPACITY);
    }

    public IntList(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        this.dataArray = new int[initialCapacity];
    }

    /**
     * Return the number of elements in the list.
     */
    public int size() {
        return this.elementCount;
    }

    /**
     * Append element i to the end of the list.
     */
    public void add(int i) {
        if (elementCount == dataArray.length) {
            dataArray = grow();
        }
        dataArray[elementCount] = i;
        elementCount++;
    }

    /**
     * Helper method to grow the array buffer to at least the size
     * it needs to contain the elements.
     */
    private int[] grow() {
        return grow(elementCount + 1);
    }

    private int[] grow(int minCapacity) {
        dataArray = Arrays.copyOf(dataArray, newCapacity(minCapacity));
        return dataArray;
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = dataArray.length;
        int newCapacity = oldCapacity + (oldCapacity / 2);
        if (newCapacity <= minCapacity) {
            if (dataArray == EMPTY_ARRAY) {
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            }
            return minCapacity;
        }
        return newCapacity;
    }

    /**
     * Insert element i at position index.
     */
    public void insertElementAt(int i, int index) {
        if (index > elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " is larger than " + elementCount);
        }
        if (elementCount == dataArray.length) {
            dataArray = grow();
        }
        System.arraycopy(dataArray, index,
                dataArray, index + 1,
                elementCount - index);
        dataArray[index] = i;
        elementCount++;
    }

    /**
     * Empty the list, and thus set all elements to null.
     */
    public void clear() {
        for (int i = 0; i < elementCount; i++) {
            dataArray[i] = 0;
        }
    }

    /**
     * Remove the element at position index.
     *
     * @return value of element that has been removed.
     */
    public int removeElementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " is larger than" + elementCount);
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index can't be negative. Stay positive!");
        }
        int removedValue = dataArray[index];
        int rightSideLength = elementCount - index - 1;
        if (index + 1 == elementCount) {
            System.arraycopy(dataArray, index + 1, dataArray, index, rightSideLength);
        } else {
            dataArray[elementCount] = 0;
        }
        elementCount--;
        return removedValue;
    }

    /**
     * @return the position of element target in the list or -1.
     */
    public int indexOf(int target) {
        for (int counter = 0; counter < elementCount; counter++) {
            if (target == dataArray[counter]) {
                return counter;
            }
        }
        return -1;
    }

    /**
     * @return true if target is present in the list, otherwise false.
     */
    public boolean contains(int target) {
        return indexOf(target) >= 0;
    }

    /**
     * @return the element at position index.
     */
    public int get(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " is larger than " + elementCount);
        }
        return dataArray[index];
    }

    /**
     * @return a string which describes the contents of the list.
     */
    @Override
    public String toString() {
        this.trimToSize();
        return Arrays.toString(dataArray);
    }

    /**
     * Trim off trailing zero elements.
     * If a zero was deliberately added to the end of the list
     * this method (nor the toString method) will not be able to recognize it.
     */
    public void trimToSize() {
        int oldCapacity = dataArray.length;
        if (elementCount < oldCapacity) {
            dataArray = Arrays.copyOf(dataArray, elementCount);
        }
    }


    /**
     * Order the list ascending with the counting sort algorithm.
     */
    public void sort() {
        int[] negatives = new int[2];
        int[] positives = new int[1];
        for (int i = 0; i < elementCount; i++) {
            int currentValue = dataArray[i];
            if (currentValue == 0) {
                positives[0]++;
            } else if (currentValue < 0) {
                negatives = populateCountArray(negatives, -currentValue);
            } else {
                positives = populateCountArray(positives, currentValue);
            }
        }
        int replaceCounter = 0;
        for (int currentValue = negatives.length - 1; currentValue > 0; currentValue--) {
            int amount = negatives[currentValue];
            if (amount > 0) {
                for (int i = 0; i < amount; i++) {
                    dataArray[replaceCounter] = -currentValue;
                    replaceCounter++;
                }
            }
        }
        for (int currentValue = 0; currentValue < positives.length; currentValue++) {
            int amount = positives[currentValue];
            if (amount > 0) {
                for (int i = 0; i < amount; i++) {
                    dataArray[replaceCounter] = currentValue;
                    replaceCounter++;
                }
            }
        }
    }

    private int[] populateCountArray(int[] countArray, int currentValue) {
        if (currentValue + 1 > countArray.length) {
            countArray = Arrays.copyOf(countArray, currentValue + 1);
        }
        countArray[currentValue]++;
        return countArray;
    }
}
