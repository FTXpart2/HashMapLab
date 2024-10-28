import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashSet<E> extends AbstractSet<E> {
    private Object[] elements;
    private int size;
    private static final int capacity = 16;

    public MyHashSet() {
        elements = new Object[capacity];
        size = 0;
    }

    @Override
    public boolean add(E e) {
        if (size >= elements.length) {
            resize();
        }
        if (contains(e)) {
            return false; // Element already exists
        }
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                elements[i] = elements[size - 1]; // Replace with last element
                elements[size - 1] = null; // Avoid loitering
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }
 // Used for for each loop
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < size && elements[index] == null) {
                    index++; // Skip null elements
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[index++];
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}



// old code
/*
 * 

public class MyHashSet {
    private String[] set;
    private int size;

    public MyHashSet() {
        set = new String[10]; // Initial capacity
        size = 0;
    }

    public void add(String value) {
        if (!contains(value)) {
            if (size == set.length) {
                resize(); // Resize if array is full
            }
            set[size] = value; // Add new element
            size++;
        }
    }

    public boolean contains(String value) {
        return containsRecursive(value, 0);
    }

    private boolean containsRecursive(String value, int index) {
        if (index >= size) {
            return false; // Reached the end
        }
        if (set[index].equals(value)) {
            return true; // Found the element
        }
        return containsRecursive(value, index + 1); // Check next element
    }

    private void resize() {
        set = Arrays.copyOf(set, set.length * 2); // Double the size
    }

    public void remove(String value) {
        int index = indexOf(value, 0);
        if (index != -1) {
            set[index] = set[size - 1]; // Replace with the last element
            set[size - 1] = null; // Remove reference
            size--;
        }
    }

    private int indexOf(String value, int index) {
        if (index >= size) {
            return -1; // Not found
        }
        if (set[index].equals(value)) {
            return index; // Found the element
        }
        return indexOf(value, index + 1); // Check next element
    }

    public int size() {
        return size;
    }
 */