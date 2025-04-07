import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    public void testAddAndGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(10);    // [10]
        deque.addLast(20);    // [10, 20]
        deque.addFirst(5);    // [5, 10, 20]


        // 测试 get()
        assertThat(deque.get(0)).isEqualTo(5);
        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(20);
        assertThat(deque.get(3)).isNull();  // 越界应该返回 null

        // 测试 size()
        assertThat(deque.size()).isEqualTo(3);

        // 测试 toList()
        List<Integer> expected = List.of(5, 10, 20);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void testMore() {
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addLast(10);
         deque.addLast(20);
         deque.addLast(5);
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(5);
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(5); // [10, 20, 5, 10, 20, 5, 10, 20, 5]
        assertThat(deque.get(8)).isEqualTo(5);
    }

    @Test
    public void testRemove() {
         ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
         deque.addLast(10);
         deque.addLast(20); // [10, 20]
        deque.addLast(5); // [10, 20, 5]
        deque.removeFirst();
        deque.removeLast(); // [20]
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(20);
    }

}
