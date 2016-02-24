package com.rise.shop.persistence.inverted;

/**
 * Created by wangdi on 16-2-22.
 */
public interface InvertedDbApi {

    public long getNowCount();

    public long getTotalCount();

    public void invertedDistance(InvertedDistanceEnum invertedDistanceEnum);

}
