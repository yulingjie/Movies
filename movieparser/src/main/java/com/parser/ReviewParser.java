package com.parser;

import org.json.JSONObject;

/**
 * Created by ylj on 15-9-26.
 */
public class ReviewParser implements IParser<Review> {

    @Override
    public Review Parse(String jsonStr) {
        JSONObject json = new JSONObject(jsonStr);
        Review review = new Review();
        review.setId(json.getString("id"));
        review.setAuthor(json.getString("author"));
        review.setContent(json.getString("content"));
        review.setUrl(json.getString("url"));
        return review;
    }
}
