package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    public wordsfileMap map1;
    public TimeSeries map2;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     * wordsfilename countsfilename
     */

    public TimeSeries timeseriesforcountsfile;
    public wordsfileMap wordsfilemap;

    private class wordsfileMap extends TreeMap<String,TimeSeries>{
        public wordsfileMap(){
            super();
        }
    }
    public NGramMap(String wordsFilename, String countsFilename) {
            if(wordsFilename != null){
                In in = new In(wordsFilename);
                map1 = new wordsfileMap();

                while(in.hasNextLine()){
                    String[]  splitLine = in.readLine().split("\t");
                    String word = splitLine[0];
                    int year = Integer.parseInt(splitLine[1]);
                    double value = Double.parseDouble(splitLine[2]);

                    if(map1.containsKey(word)){
                        map1.get(word).put(year, value);
                    }else{
                        TimeSeries p = new TimeSeries();
                        p.put(year, value);
                        map1.put(word, p);
                    }

                }
            }

            if(countsFilename != null){
                In in = new In(countsFilename);
                map2 = new TimeSeries();
                while(in.hasNextLine()){
                    String[] splitLine = in.readLine().split(",");
                    int year = Integer.parseInt(splitLine[0]);
                    double value = Double.parseDouble(splitLine[1]);

                    map2.put(year, value);
                }
            }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        TimeSeries series1 = map1.get(word);
        for(int i = startYear; i <= endYear; i++){
            if(series1.containsKey(i)){
                ts.put(i, series1.get(i));
            }
        }
        return ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries();
        for(int i = MIN_YEAR; i <= MAX_YEAR; i++){
            ts.put(i,map2.get(i));
        }
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = this.countHistory(word, startYear, endYear);
        TimeSeries p = this.totalCountHistory();
        ts = ts.dividedBy(p);
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for(String word : words){
            TimeSeries p = this.weightHistory(word, startYear, endYear);
            ts = ts.plus(p);
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
