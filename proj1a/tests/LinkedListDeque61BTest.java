import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testIsEmptyIsTrue() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         assertThat(list.isEmpty()).isTrue();
    }

    @Test
    public void testIsEmptyIsFalse() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         assertThat(list.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         assertThat(list.size()).isEqualTo(0);
         list.addFirst(0);
         assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void getWithValidIndex() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         list.addFirst(1);
         list.addFirst(2);
         assertThat(list.get(0)).isEqualTo(2);
         assertThat(list.get(1)).isEqualTo(1);
         assertThat(list.get(2)).isEqualTo(0);
    }

    @Test
    public void getWithInvalidIndex() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         assertThat(list.get(-1)).isNull();
         assertThat(list.get(1)).isNull();
    }

    @Test
    public void getRecursivelyWithValidIndex() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         list.addFirst(1);
         list.addFirst(2);
         assertThat(list.getRecursive(0)).isEqualTo(2);
         assertThat(list.getRecursive(1)).isEqualTo(1);
         assertThat(list.getRecursive(2)).isEqualTo(0);
    }

    @Test
    public void getRecursivelyWithInvalidIndex() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         assertThat(list.getRecursive(-1)).isNull();
         assertThat(list.getRecursive(1)).isNull();
    }

    @Test
    public void removeFirstTestBasic() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         list.addFirst(1);
         list.addFirst(2); // [2, 1, 0]
        list.removeFirst();
        assertThat(list.toList()).containsExactly(1, 0).inOrder();
        list.removeFirst();
        assertThat(list.toList()).containsExactly(0).inOrder();
    }

    @Test
    public void removeLastInvalid() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         assertThat(list.removeLast()).isNull();
    }

    @Test
    public void removeFirstInvalid() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         assertThat(list.removeFirst()).isNull();
    }

    @Test
    public void removeLastTestBasic() {
         Deque61B<Integer> list = new LinkedListDeque61B<>();
         list.addFirst(0);
         list.addFirst(1);
         list.addFirst(2); // [2, 1, 0]
        list.removeLast();
        assertThat(list.toList()).containsExactly(2, 1).inOrder();

    }

}