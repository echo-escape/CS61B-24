import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

import org.junit.Test;

public class UnionTest {

    /**
     * 测试 union 操作和 connected 方法，同时验证 sizeOf 的返回值是否正确。
     */
    @Test
    public void testUnionAndConnected() {
        UnionFind uf = new UnionFind(10);
        // 初始状态下，每个元素均为独立集合，调用 union 操作合并部分节点
        uf.union(1, 2);
        uf.union(2, 3);

        // 1、2、3 应在同一集合中
        int root1 = uf.find(1);
        int root2 = uf.find(3);
        assertThat(root1).isEqualTo(root2);

        // 检查集合大小是否正确
        int size = uf.sizeOf(1);
        assertWithMessage("合并后的集合大小应为3").that(size).isEqualTo(3);

        // 验证未合并的节点不相连
        assertWithMessage("节点 1 和 4 不应连接").that(uf.connected(1, 4)).isFalse();
    }

    /**
     * 测试路径压缩功能。
     * 构造链式结构后调用 find，检查所有中间节点的 parent 应该直接指向根节点。
     */
    @Test
    public void testPathCompression() {
        UnionFind uf = new UnionFind(10);
        // 构造一条链式结构：1-2-3-4
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(3, 4);

        // 调用 find 触发路径压缩
        int root = uf.find(1);
        // 断言 1, 2, 3, 4 这几个节点的直接父节点均为根节点（除根节点本身）
        for (int i = 1; i <= 4; i++) {
            // 注意：直接访问 parent 数组，所以需确保测试类与实现类在同一包下或 parent 属性为包内可见
            if (i != root) {
                assertWithMessage("节点 " + i + " 的 parent 应该直接指向根节点")
                        .that(uf.parent[i]).isEqualTo(root);
            }
        }
    }

    /**
     * 测试 find 方法在遇到非法索引时是否抛出异常
     */
    @Test
    public void testFindWithInvalidIndex() {
        UnionFind uf = new UnionFind(5);
        try {
            uf.find(5);
            fail("调用 find(5) 未抛出 IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // 预期抛出异常
            assertWithMessage("异常信息应包含描述").that(e.getMessage()).contains("Some comment");
        }
    }

    /**
     * 测试对自身进行 union 不改变并查集结构（即集合大小不变）。
     */
    @Test
    public void testUnionWithSelf() {
        UnionFind uf = new UnionFind(10);
        uf.union(3, 3);
        // 调用 union 后，集合大小仍应为1
        assertWithMessage("单个节点集合的大小应为1").that(uf.sizeOf(3)).isEqualTo(1);
    }

    /**
     * 测试多次 union 操作后的加权合并特性以及集合根的一致性。
     */
    @Test
    public void testWeightedUnion() {
        UnionFind uf = new UnionFind(10);
        uf.union(1, 2);  // 合并 [1,2]
        uf.union(3, 4);  // 合并 [3,4]
        uf.union(1, 3);  // 合并成 [1,2,3,4]

        // 验证集合大小
        int size = uf.sizeOf(1);
        assertWithMessage("合并后的集合大小应为4").that(size).isEqualTo(4);

        // 检查所有节点的根节点相同
        int root = uf.find(1);
        assertWithMessage("节点2的根节点应与节点1相同").that(uf.find(2)).isEqualTo(root);
        assertWithMessage("节点3的根节点应与节点1相同").that(uf.find(3)).isEqualTo(root);
        assertWithMessage("节点4的根节点应与节点1相同").that(uf.find(4)).isEqualTo(root);
    }
}
