import java.util.Stack;

public class SpringArray {
    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        for (char c : springExpr.toCharArray()) {
            if (c == '[') {
                stack.push(new Spring(1));
            } else if (c == '{') {
                stack.push(new Spring());
            } else if (c == ']') {
                Spring s = new Spring(1);
                while (!stack.isEmpty() && !(stack.peek() instanceof SpringArray)) {
                    s = s.inParallel(stack.pop());
                }
                stack.pop(); // remove the SpringArray from the stack
                stack.push(s);
            } else if (c == '}') {
                Spring s = new Spring();
                while (!stack.isEmpty() && !(stack.peek() instanceof SpringArray)) {
                    s = s.inSeries(stack.pop());
                }
                stack.pop(); // remove the SpringArray from the stack
                stack.push(s);
            }
        }
        return stack.pop();
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        Stack<Spring> stack = new Stack<>();
        for (char c : springExpr.toCharArray()) {
            if (c == '[') {
                stack.push(new Spring(1));
            } else if (c == '{') {
                stack.push(new Spring());
            } else if (c == ']') {
                SpringArray sa = new SpringArray(stack.toArray(new Spring[0]));
                stack.clear();
                stack.push(sa.equivalentSpring(springs));
            } else if (c == '}') {
                SpringArray sa = new SpringArray(stack.toArray(new Spring[0]));
                stack.clear();
                stack.push(sa.equivalentSpring(springs));
            }
        }
        return stack.pop();
    }
}
