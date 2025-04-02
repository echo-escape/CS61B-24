import static com.google.common.truth.Truth.assertThat;
import org.junit.Test;

public class LinkedListDeque61BTestDiy {

    @Test
    public void testEmptyDeque() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        // 初始状态为空
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void testAddFirstAndAddLast() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();

        // 测试添加操作
        deque.addFirst(10); // deque: [10]
        deque.addLast(20);  // deque: [10, 20]
        deque.addFirst(5);  // deque: [5, 10, 20]

        // 验证链表内容和大小
        assertThat(deque.toList()).containsExactly(5, 10, 20).inOrder();
        assertThat(deque.size()).isEqualTo(3);
    }

    @Test
    public void testGetAndGetRecursive() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(10); // deque: [10]
        deque.addLast(20);  // deque: [10, 20]
        deque.addFirst(5);  // deque: [5, 10, 20]

        // 测试迭代获取
        assertThat(deque.get(0)).isEqualTo(5);
        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(20);

        // 测试递归获取
        assertThat(deque.getRecursive(0)).isEqualTo(5);
        assertThat(deque.getRecursive(1)).isEqualTo(10);
        assertThat(deque.getRecursive(2)).isEqualTo(20);
    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(10); // deque: [10]
        deque.addLast(20);  // deque: [10, 20]
        deque.addFirst(5);  // deque: [5, 10, 20]

        // 测试 removeFirst
        Integer removedFirst = deque.removeFirst(); // 移除 5, deque: [10, 20]
        assertThat(removedFirst).isEqualTo(5);

        // 测试 removeLast
        Integer removedLast = deque.removeLast();   // 移除 20, deque: [10]
        assertThat(removedLast).isEqualTo(20);

        // 验证链表内容和大小
        assertThat(deque.toList()).containsExactly(10).inOrder();
        assertThat(deque.size()).isEqualTo(1);

        // 移除剩余的元素
        Integer removed = deque.removeFirst();       // 移除 10, deque: []
        assertThat(removed).isEqualTo(10);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void testRemoveOnEmptyDeque() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        // 当 deque 为空时，移除操作应返回 null
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
    }
}
