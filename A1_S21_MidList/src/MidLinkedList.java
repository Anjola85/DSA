import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MidLinkedList<E> implements List<E>{
	private int size;
	private Node<E> head, mid, tail;
	
	private static class Node<E> {
		E item;
		Node<E> prev;
		Node<E> next;
		
		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}
	
	
//helper method to reduce time complexity and get position of an item.
//fraction explanation
//	HEAD 		|		MID			| 	TAIL
//	25%(size/4)	|	50%(size/2)		|  	75%(size * 3/4)
	private Node<E> findPosition(int index) {
		
//		get what position the index is closer to, be it head, mid or tail.
		Node<E> curNode;
		
		if(index < size/4)   //less than 25% means closer to the head
			curNode = head;    
		else if(index < (3*size/4))   //less than 75% means closer to the middle
			curNode = mid;
		else
			curNode = tail;				//else closer to the tail
		
//		set the index depending on what position the current node is close to.
		int initialIndex;
		
		if(curNode == head)
			initialIndex = 0;
		else if(curNode == mid)
			initialIndex = size/2;
		else
			initialIndex = size -1;
		
// Get the direction(forward(1) or backward(-1)) on how to traverse the list. 
// mid/4 is 25%, mid being 50% and tail being mid*3/4 75%.
		int direction;
		
		if(index < size/4)
			direction = 1;
		else if(index < size/2)
			direction = -1;     	//size/2 means not up to mid so start at mid and traverse backwards
		else if(index < 3*size/4)
			direction = 1;			//less than 75% means around mid region so start at mid and traverse forward.
		else
			direction = -1;			//means >= 75% so start at tail and traverse backwards
		
//		iterate until you index(position given)
//		there are only three initial index
//		iteration starts at initialIndex(where the current node is close to) and then start traversing from there.	
//		Set i to initial index and start traversing by adding or subtracting 1 till you find the ACTUAL POSITION.
		for(int i = initialIndex; i != index; i += direction)
		{
			if(direction > 0)
				curNode = curNode.next;
			else
				curNode = curNode.prev;
		}
		
		return curNode;	
	}

	
//	default constructor
	MidLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	

	@Override
	public boolean add(E e) {
		add(size, e);
		return true;
	}
	
	@Override
	public void add(int index, E e) {
//		catch exceptions
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		
		if(size == 0) 	
		{
			//if list is empty
			head = new Node<E>(tail, e, null);  //TODO: why did he set tail as previous, although tail is null but head.prev is not null.
			mid = head;
			tail = head;
		} 
		else if(index == size)   
		{
			//last position
			tail = new Node<E>(tail, e, null);    //sets previous as the previousTail and sets next to null
			tail.prev.next = tail;
			
			if(size%2 != 0)         //reset the middle element
				mid = mid.next;
		}
		else if(index == 0)
		{
//			first position
			head = new Node<E>(null, e, head);
			head.next.prev = head;
			
			if(size%2 == 0)         //reset the middle element
				mid = mid.prev;
		}
		else
		{
//			neither first or last
			Node<E> curNode = findPosition(index);
			curNode.prev.next = new Node<E>(curNode.prev, e, curNode); 
			curNode.prev = curNode.prev.next;
			//reset the value of curNode.prev to the new Node
			//which is already the reference of curNode.prev.next
			
//			TODO: TRACE ALGORITHM
//			reset mid value
			if(index <= size/2 && size%2 == 0)
				mid = mid.prev;
			else if(index > size/2 && size%2 != 0)
				mid = mid.next;
		}
		size++;
	}
	
	
	@Override
	public E remove(int index) {
		
//		check exceptions
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		E elementToReturn;
		
		if(index==0) {//first position
			elementToReturn = head.item;
			head = head.next;    //reset head to next element
			
			if( head == null)    //if thats the only element
				mid = tail = null;
			else {				
				head.prev = null;	//make sure head.prev points to nothing
				
//				reset mid value
				if(size%2 != 0)
					mid = mid.next; 
			}
		}
		else if(index == size-1) {//last position
			elementToReturn = tail.item;
			tail = tail.prev;	//reset tail to prev element
			tail.next = null;
			
//			reset mid value
			if(size%2 == 0)
				mid = mid.prev;
		}
		else
		{  //neither first or the last; find the element before
			Node <E> curNode = findPosition(index);
			elementToReturn = curNode.item;
			curNode.prev.next = curNode.next;
			curNode.next.prev = curNode.prev;
			
//			reset mid value
			if(index <= size/2 && size%2 != 0)
				mid = mid.next;
			else if(index >= size/2 && size%2==0)
				mid = mid.prev;
		}
		size--;
		return elementToReturn;
	}
	

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		boolean flag = false;
		if(size == 0) {
			flag = true;
		}
		return flag;
	}
	

	@Override
	public void clear() {
		this.head = null;
		this.mid = null;
		this.tail = null;
		this.size = 0;
	}

	@Override
	public E get(int index) {
		
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		return findPosition(index).item;
	}

//	toString implementation
	@Override
	public String toString() {
		
		if(this.size == 0)
			return "[]";
		
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		
		Node<E> curr = this.head;
		
		while(curr != null && curr != this.tail) {
			
			sb.append(curr.item.toString());
			sb.append(",");
			sb.append(" ");
			curr = curr.next;

		}
		
		sb.append(this.tail.item.toString());
		
		sb.append("]");
		
		return sb.toString();
	}
	
	
//	***********************UNIMPLEMENTED METHODS******************************

	
	@Override
	public boolean remove(Object o) {
		
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
