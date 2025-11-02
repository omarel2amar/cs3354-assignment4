import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatGPTTest {

    // 1. brand new stack should be empty
    @Test
    void testInitialState() {
        Stack s = new Stack(3);
        assertTrue(s.isEmpty(), "New stack should be empty");
        assertFalse(s.isFull(), "New stack should not be full");
        assertEquals(0, s.size(), "New stack size should be 0");
        // peek/pop on empty should throw
        assertThrows(IllegalStateException.class, s::peek);
        assertThrows(IllegalStateException.class, s::pop);
    }

    // 2. push within capacity + peek
    @Test
    void testPushAndPeek() {
        Stack s = new Stack(5);
        s.push(10);
        s.push(20);
        s.push(30);
        assertEquals(3, s.size());
        assertFalse(s.isEmpty());
        assertFalse(s.isFull());
        assertEquals(30, s.peek(), "Peek should return last pushed element");
        // peek should NOT change size
        assertEquals(3, s.size());
    }

    // 3. push up to capacity (near-invalid: last slot) and then one extra
    @Test
    void testPushToFullAndOverflow() {
        Stack s = new Stack(2);
        s.push(100);
        s.push(200);
        assertTrue(s.isFull(), "Stack should be full after 2 pushes");
        assertEquals(2, s.size());
        // now invalid push
        assertThrows(IllegalStateException.class, () -> s.push(300));
    }

    // 4. pop order (LIFO) + popping last element (edge)
    @Test
    void testPopLifoAndEmptying() {
        Stack s = new Stack(3);
        s.push(1);
        s.push(2);
        s.push(3);

        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());  // popped last element
        assertTrue(s.isEmpty(), "Stack should be empty after popping all elements");
        assertEquals(0, s.size());

        // now invalid: pop from empty
        assertThrows(IllegalStateException.class, s::pop);
    }

    // 5. interleaved operations (push/pop/peek) to make sure top tracking is correct
    @Test
    void testInterleavedOps() {
        Stack s = new Stack(4);
        s.push(5);
        s.push(10);
        assertEquals(10, s.peek());
        assertEquals(2, s.size());

        int popped = s.pop();
        assertEquals(10, popped);
        assertEquals(1, s.size());
        assertEquals(5, s.peek());

        s.push(20);
        s.push(30);
        s.push(40); // now should be full (4 elements total)
        assertTrue(s.isFull());
        assertEquals(4, s.size());
        assertEquals(40, s.peek());
    }
}
