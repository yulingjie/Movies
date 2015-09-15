package com.parser;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by ylj on 15-9-15.
 */
public class TrailerParserTest extends TestCase {

    @Test
    public void testParse() throws Exception {
        String jsonStr = "{\"id\":\"5576eac192514111e4001b03\",\"iso_639_1\":\"en\",\"key\":\"lP-sUUUfamw\",\"name\":\"Official Trailer 3\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Trailer\"}";
        TrailerParser parser = new TrailerParser();
        Trailer trailer = parser.Parse(jsonStr);
        assertEquals(trailer.getId(), "5576eac192514111e4001b03");
    }
}