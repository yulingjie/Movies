package com.parser;

import org.json.JSONObject;

/**
 * Created by ylj on 15-9-15.
 */
public class TrailerParser implements IParser<Trailer> {

    @Override
    public Trailer Parse(String jsonStr) {
        Trailer trailer = new Trailer();
        JSONObject jsonObject =new JSONObject(jsonStr);
        trailer.setId(jsonObject.getString("id"));
        trailer.setLang(jsonObject.getString("iso_639_1"));
        trailer.setKey(jsonObject.getString("key"));
        trailer.setName(jsonObject.getString("name"));
        trailer.setSite(jsonObject.getString("site"));
        trailer.setSize(jsonObject.getInt("size"));
        return trailer;

    }
}
