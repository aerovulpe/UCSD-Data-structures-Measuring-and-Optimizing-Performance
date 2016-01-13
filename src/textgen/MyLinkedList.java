package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// Implement this method
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) {
		// Implement this method
		if (element == null)
			throw new NullPointerException("Element cannot be null!");

		new LLNode<>(element, tail);
		size++;
		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) {
		// Implement this method.
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Check index!");

		return getNode(index).data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The
	 *            index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element) {
		// Implement this method
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Check index!");

		if (element == null)
			throw new NullPointerException("Element cannot be null!");

		new LLNode<>(element, getNode(index));
		size++;
	}

	/** Return the size of the list */
	public int size() {
		// Implement this method
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// Implement this method.
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Check index!");

		LLNode<E> del = getNode(index);
		del.prev.next = del.next;
		del.next.prev = del.prev;
		del.prev = null;
		del.next = null;
		size--;
		return del.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// Implement this method.
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Check index!");

		if (element == null)
			throw new NullPointerException("Element cannot be null!");

		LLNode<E> delNode = getNode(index);
		E del = delNode.data;
		delNode.data = element;
		return del;
	}
	
	private LLNode<E> getNode(int index) {
		LLNode<E> currentNode = head;

		for (int i = -1; i < index; i++)
			currentNode = currentNode.next;

		return currentNode;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		data = e;
	}

	public LLNode(E e, LLNode<E> next) {
		this(e);
		this.prev = next.prev;
		this.next = next;
		next.prev.next = this;
		next.prev = this;
	}
}
