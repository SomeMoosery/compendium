import java.util.*;

// NOTE YOU LITERALLY DO THE SAME THING FOR IMPLEMENTING A STACK WITH TWO QUEUES!!! JUST REVERSE THE DATA STRUCTURES
class QueueWithStacks {
    
    Stack<Integer> s1;
    Stack<Integer> s2;

    /** Initialize your data structure here. */
    public QueueWithStacks() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        if (s1.isEmpty()) {
            s1.push(x);
        } else {
            while(!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            s1.push(x);
            while(!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (s1.isEmpty()) {
            throw new StackEmptyException("Stack is empty");
        }
        return s1.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if (s1.isEmpty()) {
            throw new StackEmptyException("Stack is empty");
        }
        return s1.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty();
    }

    public static void main(String[] args) {
        QueueWithStacks obj = new QueueWithStacks();
        obj.push(2);
        System.out.println("Pushed 2");
        System.out.println(obj.pop());
        System.out.println(obj.peek());
        System.out.println(obj.empty());
    }
}

class StackEmptyException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public StackEmptyException(){
        super();
    }
    
    public StackEmptyException(String message) {
        super(message);
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */