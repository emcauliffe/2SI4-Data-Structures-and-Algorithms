import java.util.EmptyStackException;

public class Stack<E> { //stack the same as discussed in class
	private SNode<E> head;

	public Stack(){
		head = null;
	}

	public boolean isEmpty() { //O(1)
		if (head==null){
			return true;
		} else {
			return false;
		}
	}

	public void push(E e){ //O(n)
		head = new SNode(e, head);
	}

	public E pop() throws EmptyStackException{ //O(1)
		if (isEmpty() == true){
			throw new EmptyStackException();
		}
		E value = head.element;
		head = head.next;
		return value;
	}
}

class SNode<E> {
	E element;
	SNode<E> next;

	public SNode(E e, SNode<E> n){
		element = e;
		next = n;
	}
}