import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testYearsAndData() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));
        List<Double> expectedDta = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0));
        assertThat(catPopulation.years()).isEqualTo(expectedYears);
        assertThat(catPopulation.data()).isEqualTo(expectedDta);
    }

    @Test
    public void testDividedByIgnore() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1992, 100.0);
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> catPopulation.dividedBy(dogPopulation)
        );
        assertThat(thrown).hasMessageThat().contains("Missing year in the provided TimeSeries");
    }

    @Test
    public void testDividedByBasic() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 400.0);
        dogPopulation.put(1992, 500.0);
        dogPopulation.put(1994, 800.0);
        TimeSeries dividedBy = catPopulation.dividedBy(dogPopulation);
        // excepted: (1991, 0.0), (1992, 0.5), (1994, 0.25)

        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1991, 1992, 1994));
        assertThat(dividedBy.years()).isEqualTo(expectedYears);

        List<Double> expectedData = new ArrayList<>(Arrays.asList(0.0, 0.2, 0.25));
        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(dividedBy.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }
} 