package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();

        if(ts == null) {
            throw new NullPointerException();
        }
        if(startYear > endYear) {
            throw new IllegalArgumentException();
        }

        for(int i = startYear; i <= endYear; i++) {
            if(containsKey(i)) {
                this.put(i, ts.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for(int i = MIN_YEAR; i <= MAX_YEAR; i++) {
            if(containsKey(i)) {
                years.add(i);
            }
        }

        return years;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> data = new ArrayList<>();
        List<Integer> years = years();
        for(int i: years) {
            data.add(this.get(i));
        }
        return data;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for(int i=MIN_YEAR; i <= MAX_YEAR; i++) {
            if(ifBothHave(ts,this,i)){
                result.put(i, ts.get(i) + this.get(i));
            }else if(this.containsKey(i)) {
                result.put(i,this.get(i));
            }else if(ts.containsKey(i)) {
                result.put(i,ts.get(i));
            }

        }
        return result;
    }

    private boolean ifBothHave(TimeSeries ts1, TimeSeries ts2,int i) {
        if(ts1.containsKey(i) && ts2.containsKey(i)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for(int i=MIN_YEAR; i <= MAX_YEAR; i++) {
            if(ifBothHave(ts,this,i)){
                result.put(i, this.get(i) / ts.get(i));
            }else if(this.containsKey(i)) {
                throw new IllegalArgumentException();
            }
        }

        return result;
    }

}
