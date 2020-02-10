package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Stack is LIFO data structure but Queue is FIFO, this class implements a Stack using a Queue
 * @param <T> generic type T, most common type would be Integer however
 */
public class StackUsingQueues<T> {
    Queue<T> queue = new LinkedList<>();

    /**
     * push operation will be O(n) time complexity as we need to go through the entire
     * queue at least once, we remove each element and re-add it to the queue in order to
     * implement stack using queue.
     * @param t the element (of type T) that you want to push
     */
    public void push(T t) {
        queue.add(t);
        int size = queue.size();

        while (size > 1) {
            queue.add(queue.remove());
            size--;
        }
    }

    /**
     * pop operation is O(1) time complexity as it is a simple remove operation using
     * LinkedList api and returns the last item which was removed in the queue
     * @return the last item of the queue/stack (top most element)
     */
    public T pop() {
        return queue.remove();
    }

    /**
     * top operation is also O(1) and just returns the top most element
     * @return the top element in the stack
     */
    public T top() {
        return queue.peek();
    }

    /**
     * Also O(1) time complexity and just returns if the stack is empty or not
     * @return
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}
