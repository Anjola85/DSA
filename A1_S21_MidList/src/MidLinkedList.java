import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MidLinkedList <E> implements List <E>{
	private int size;
	private Node <E> head, tail, mid;
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	@Override
	public boolean add(E e) {
		add(size, e);
		return true;
	}

	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size()) throw new IndexOutOfBoundsException();
		if (size == 0) { //the list is empty
			head = new Node<E>(tail, e, null); 
			mid = head; tail = head;
		} else if (index == size){ //last position
			tail = new Node<E>(tail, e, null); 
			tail.prev.next = tail;
			mid = size % 2 != 0 ? mid.next : mid;
		} else if (index == 0) { //first position
			head = new Node <E> (null, e, head);
			head.next.prev = head;
			mid = size % 2 == 0 ? mid.prev : mid;
		} else { //neither first nor the last
			// *****YOU ARE HERE******
			Node <E> curNode = findPosition(index);
			curNode.prev.next = new Node <E> (curNode.prev, e, curNode);
			curNode.prev = curNode.prev.next;
			mid = index <= size / 2 && size % 2 == 0 ? mid.prev :
				index > size / 2 && size % 2 != 0  ? mid.next : mid; 
		}
		size ++;
	}

	private Node <E> findPosition (int index){
		// using ternary operator for conditional statements.

		// get what position the index is close to(either head, tail or mid)
		Node <E> curNode = index < size/4 ? head:
			index < 3*size/4 ? mid : tail;

		// depending on what position the index is close to, set index for head(0), mid(size/2) or tail(size - 1)
		int initialIndex = curNode == head ? 0:
			curNode == mid ? size/2 : size - 1 ;

		// Get the direction(up or down) on how to traverse the list. 
		/*  I don't understand the math. why 3/4? */
		int direction = index < size/4 ? 1:
			index < size/2 ? -1 : index < 3*size/4 ? 1 : -1;
			
		for (int i = initialIndex; i != index; 
				curNode = direction > 0 ? curNode.next : curNode.prev, i+= direction);
		return curNode;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		E elementToReturn;
		if (index == 0) { //first position or if the list becomes empty
			elementToReturn = head.item;
			head = head.next;
			if (head == null) mid = tail = null;
			else {
				head.prev = null;
				mid = size % 2 != 0 ? mid.next : mid;
			}
		} else if (index == size - 1) { //last position
			elementToReturn = tail.item;
			tail = tail.prev;
			tail.next = null;
			mid = size % 2 == 0 ? mid.prev : mid;
		} else { //neither first or the last; find the el. before
			Node <E> curNode = findPosition(index);
			elementToReturn = curNode.item;
			curNode.prev.next = curNode.next;
			curNode.next.prev = curNode.prev;
			mid = index <= size / 2 && size % 2 != 0 ? mid.next :
				index >= size / 2 && size % 2 == 0  ? mid.prev : mid; 
		}
		size --;
		return elementToReturn;
	}
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
		return findPosition(index).item;
	}
	@Override
	public int size() {
		return this.size;
	}
	@Override
	public void clear() {
		this.head = null;
		this.mid = null;
		this.tail = null;
		this.size = 0;
	}
	@Override
	public String toString() {
		if (size == 0) return "[]";
		StringBuilder sb = new StringBuilder();
		sb.append('[');

		for (Node <E> node = head; node != null; node = node.next) {
			sb.append(node.item.toString());
//			sb.append(node.prev != null?node.prev.item.toString():"null")
//				.append("<-(" + node.item.toString() + ")->")
//				.append(node.next != null?node.next.item.toString():"null");
			if (node.next == null)
				return sb.append(']').toString();
//			return sb.append(']').append(" Size: " + size + "; MID.item = " + mid.item.toString()).toString();
			sb.append(',').append(' ');
		}
		return null;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}


}
