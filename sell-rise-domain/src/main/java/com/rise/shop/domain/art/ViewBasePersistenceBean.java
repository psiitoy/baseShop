package com.rise.shop.domain.art;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.persistence.beans.BasePersistenceBean;

/**
 * mysql基本字段
 * Created by wangdi on 15-1-18.
 */
public class ViewBasePersistenceBean extends BasePersistenceBean {
    @FieldMeta(id = true, name = "序列号", order = 100, summary = false, editable = false)
    private Long id;//id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
