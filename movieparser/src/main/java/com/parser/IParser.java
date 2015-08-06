package com.parser;

import java.util.List;

/**
 * Created by ylj on 7/29/15.
 */
public interface IParser<E>{
    E Parse(String jsonStr);
}
