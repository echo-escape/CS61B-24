package hashmap;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
public class SimpleTest {

    @Test
    public void simpleTest() {
        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put(1, 1);
        int value = myHashMap.get(1);
        assertThat(value).isEqualTo(1);
    }

    @Test
    public void simpleTest2() {
        MyHashMap<String, Integer> b = new MyHashMap<>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, i);
            assertThat(b.get("hi" + i)).isEqualTo(i);
            assertThat(b.containsKey("hi" + i)).isTrue();
        }
    }
}
