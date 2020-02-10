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

    /**
     * push operation will be in O(n) time as we need to move all of the elements from stack1 to stack2
     * and then push t and finally move stack2 ontop of stack1
     * @param t t the element (of type T) that you want to push
     */
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

    /**
     * pop operation is O(1) time complexity as it is a simple remove operation using pop
     * method in Stack, returns the item that was popped
     * The top most item is the item that has been in the stack the longest, which is what we
     * need as we are doing FIFO
     * @return the top element of the stack which was removed
     */
    public T pop() {
        return stack1.pop();
    }

    /**
     * top operation is also O(1) and just returns the top most element which was the item that
     * has been in the stack the longest
     * @return the top most item in stack
     */
    public T peek() {
        return stack1.peek();
    }

    /**
     * Also O(1) time complexity and just returns if the stack is empty or not
     * @return boolean value if empty or not
     */
    public boolean empty() {
        return stack1.isEmpty();
    }
}
