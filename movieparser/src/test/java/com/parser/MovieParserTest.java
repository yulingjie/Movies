package com.parser;

import junit.framework.TestCase;

import org.junit.Test;

import java.text.DateFormat;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by ylj on 8/5/15.
 */
public class MovieParserTest extends TestCase {

    @Test
    public void testParse() throws Exception {
 String str = "{\"adult\":false, " +
                "\"backdrop_path\":\"/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\"," +
                " \"genre_ids\":[28,12,878,53]," +
                "\"id\":135397," +
                "\"original_language\":\"en\"," +
                "\"original_title\":\"Jurassic World\"," +
                "\"overview\":\"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\"," +
                "\"release_date\":\"2015-06-12\"," +
                "\"poster_path\":\"/uXZYawqUsChGSj54wcuBtEdUJbh.jpg\"," +
                "\"popularity\":85.59593," +
                "\"title\":\"Jurassic World\"," +
                "\"video\":false,\"vote_average\":7.0,\"vote_count\":1538}";

        MovieParser movieParser = new MovieParser();
        Movie movie = movieParser.Parse(str);
        assertEquals(135397, movie.getId());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(movie.getReleaseDate());
        System.out.printf(calendar.getTimeZone().toString());
        int year = calendar.get(GregorianCalendar.YEAR);
        assertEquals(2015, year);
    }
}