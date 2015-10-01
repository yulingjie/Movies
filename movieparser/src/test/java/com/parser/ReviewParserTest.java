package com.parser;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by ylj on 15-9-26.
 */
public class ReviewParserTest extends TestCase {

    @Test
    public void testParse() throws Exception {
        ReviewParser parser = new ReviewParser();
        String jsonStr = " {\n" +
                "      \"id\": \"5013bc76760ee372cb00253e\",\n" +
                "      \"author\": \"Chris\",\n" +
                "      \"content\": \"I personally thought this film is on par if not better than the Dark Knight.\\r\\n\\r\\nWhilst some think the film is too long for the story I didn't find this. The length of the film is longer than some (but doesn't feel it), I liked that the film took it's time rather than shoving more elements in it - I think this contributed to the dramatic ending (much like a classical piece of music will be relaxed and calm before the final crescendo).\\r\\n\\r\\nAt the end of the day whether you like this film will boil down to if you like films Christopher Nolan has directed and/or you like the Christopher Nolan Batman series so far.\\r\\n\\r\\nStupendously good film in my opinion.\",\n" +
                "      \"url\": \"http://j.mp/P18dg1\"\n" +
                "    }";
        Review review = parser.Parse(jsonStr);
        assertEquals(review.getAuthor(), "Chris");
    }
}