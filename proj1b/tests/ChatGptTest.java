import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ChatGptTest {

    @Test
    public void testAddLastAndGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        // 验证 size 和 get 方法
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(1)).isEqualTo(2);
        assertThat(deque.get(2)).isEqualTo(3);
        // 验证 toList 方法
        List<Integer> expected = List.of(1, 2, 3);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void testAddFirstAndGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        // 逻辑上 addFirst 顺序是：3, 2, 1
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.get(0)).isEqualTo(3);
        assertThat(deque.get(1)).isEqualTo(2);
        assertThat(deque.get(2)).isEqualTo(1);
        List<Integer> expected = List.of(3, 2, 1);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        // 删除队首元素
        int removed = deque.removeFirst();
        assertThat(removed).isEqualTo(10);
        assertThat(deque.size()).isEqualTo(2);
        List<Integer> expected = List.of(20, 30);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        // 删除队尾元素
        int removed = deque.removeLast();
        assertThat(removed).isEqualTo(30);
        assertThat(deque.size()).isEqualTo(2);
        List<Integer> expected = List.of(10, 20);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void testResizeUp() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // 添加足够的元素触发扩容（初始容量为8，添加超过8个元素）
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        assertThat(deque.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            assertThat(deque.get(i)).isEqualTo(i);
        }
    }

    @Test
    public void testResizeDown() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // 扩容
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }
        // 删除足够多的元素以使得使用率低于 0.25，从而触发缩容
        // 例如：删除前13个元素，剩下 3 个
        for (int i = 0; i < 13; i++) {
            deque.removeFirst();
        }
        assertThat(deque.size()).isEqualTo(3);
        // 剩下的元素应为 13, 14, 15
        List<Integer> expected = List.of(13, 14, 15);
        assertThat(deque.toList()).isEqualTo(expected);
    }
}
