import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;



public class TestDeque61B {
    @Test
    public void testIteratorLinkedListDeque61B() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertThat(lld).containsExactly(3, 2, 1);
    }

    @Test
    public void testIteratorArrayListDeque61B() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        assertThat(lld).containsExactly(3, 2, 1);
    }

    @Test
    public void testEqualLinkedListDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testEqualArrayDeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testToStringLinkedListDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testToStringArrayListDeques61B() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toString()).isEqualTo("[front, middle, back]");
    }

}
