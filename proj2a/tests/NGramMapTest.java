import ngrams.NGramMap;
import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Utils.*;
import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the NGramMap class.
 *  @author Josh Hug
 */
public class NGramMapTest {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(2005, 2006, 2007, 2008));
        List<Double> expectedCounts = new ArrayList<>
                (Arrays.asList(646179.0, 677820.0, 697645.0, 795265.0));

        TimeSeries request2005to2008 = ngm.countHistory("request");
        assertThat(request2005to2008.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2005to2008.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }

        expectedYears = new ArrayList<>
                (Arrays.asList(2006, 2007));
        expectedCounts = new ArrayList<>
                (Arrays.asList(677820.0, 697645.0));

        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertThat(request2006to2007.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2006to2007.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }
    }

    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(TOP_14337_WORDS_FILE,
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertThat(fishCount.get(1865)).isWithin(1E-10).of(136497.0);
        assertThat(fishCount.get(1922)).isWithin(1E-10).of(444924.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0/2563919231.0);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertThat(dogCount.get(1865)).isWithin(1E-10).of(75819.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
    }

    @Test
    public void testDefensiveCopyInCountHistory() {
        // 构造 NGramMap 对象，使用较短的测试文件
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        // 获取 "request" 单词在 2005~2008 年间的历史记录
        TimeSeries tsCopy = ngm.countHistory("request", 2005, 2008);

        // 修改拷贝中的数据
        tsCopy.put(2005, 0.0);

        // 重新获取同一单词相同时间段的记录
        TimeSeries tsOriginal = ngm.countHistory("request", 2005, 2008);

        // 验证原始数据中的 2005 年数据不受之前修改的影响
        // 预期值为 646179.0（参照给出的测试数据）
        assertThat(tsOriginal.get(2005)).isWithin(1E-10).of(646179.0);
    }

    /**
     * 测试对不存在单词的请求：
     * 请求一个数据中不存在的单词，应该返回一个空的 TimeSeries。
     */
    @Test
    public void testNonExistentWord() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        TimeSeries ts = ngm.countHistory("thisWordDoesNotExist", 2005, 2008);
        // 验证返回的 TimeSeries 为空
        assertThat(ts.years()).isEmpty();
        assertThat(ts.data()).isEmpty();
    }

}  