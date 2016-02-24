package com.rise.shop.domain.query;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-9.
 */
public class ArtistQuery extends DefaultBaseQuery {
    @FieldMeta(name = "姓名", order = 1)
    private String name;    //姓名
    private Long ageIntervalGt;
    private Long ageIntervalGte;
    private Long ageIntervalLt;
    private Long ageIntervalLte;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAgeIntervalGt() {
        return ageIntervalGt;
    }

    public void setAgeIntervalGt(Long ageIntervalGt) {
        this.ageIntervalGt = ageIntervalGt;
    }

    public Long getAgeIntervalGte() {
        return ageIntervalGte;
    }

    public void setAgeIntervalGte(Long ageIntervalGte) {
        this.ageIntervalGte = ageIntervalGte;
    }

    public Long getAgeIntervalLt() {
        return ageIntervalLt;
    }

    public void setAgeIntervalLt(Long ageIntervalLt) {
        this.ageIntervalLt = ageIntervalLt;
    }

    public Long getAgeIntervalLte() {
        return ageIntervalLte;
    }

    public void setAgeIntervalLte(Long ageIntervalLte) {
        this.ageIntervalLte = ageIntervalLte;
    }
}
