package com.rise.shop.persistence.inverted;

import com.rise.shop.persistence.beans.BasePersistenceBean;
import com.rise.shop.persistence.dao.EntityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi on 16-2-22.
 */
public abstract class AbstractInvertedDbService<F extends BasePersistenceBean, T extends BasePersistenceBean> implements InvertedDbApi {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected EntityDao<F> fromDao;
    protected EntityDao<T> toDao;

    public abstract long getNowCount();

    public abstract long getTotalCount();

    public void invertedDistance(InvertedDistanceEnum invertedDistanceEnum) {
    }

}
