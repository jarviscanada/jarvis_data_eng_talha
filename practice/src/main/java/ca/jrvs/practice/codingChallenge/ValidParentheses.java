package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses {
    public boolean parenthesesWithMap(String s) {
        Map<Character, Character> map = new HashMap<>();
        Stack<Character> stack = new Stack<>();

        map.put('}', '{');
        map.put(']', '[');
        map.put(')', '(');

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            //If char is opening bracket, just push to stack
            if (map.containsValue(c)) {
                stack.push(c);
            }
            //char is closing bracket, pop from stack
            else {
                if (stack.empty()) {
                    return false;
                }

                Character top = stack.pop();
                if (map.get(c) != top) {
                    //if char is not closing bracket return false
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
