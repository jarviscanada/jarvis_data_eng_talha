package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Queue is FIDO data structure but Stack is LIFO, this class implements a Queue using two Stacks
 * @param <T> generic type T, most common type would be Integer however
 */
public class QueueUsingStacks<T> {
    Stack<T> stack1 = new Stack<>();
    Stack<T> stack2 = new Stack<>();

    public void push(T t) {
        if (stack1.isEmpty()) {
            stack1.push(t);
        }
        else {
            while (stack1.isEmpty() == false) {
                stack2.push(stack1.pop());
            }

            //Now we can push t to the empty stack1
            stack1.push(t);

            //finally move all of stack2 back to stack1 in proper order
            while (stack2.isEmpty() == false) {
                stack1.push(stack2.pop());
            }
        }
    }

    public T pop() {
        return stack1.pop();
    }

    public T peek() {
        return stack1.peek();
    }

    public boolean empty() {
        return stack1.isEmpty();
    }
}
