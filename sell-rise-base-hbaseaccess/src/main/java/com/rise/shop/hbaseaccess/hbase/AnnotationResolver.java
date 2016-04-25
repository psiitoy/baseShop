package com.rise.shop.hbaseaccess.hbase;

import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;

/**
 * Global setting, no class setting.
 *
 * @author nazario.wang
 */
public interface AnnotationResolver {

    public void resolve(ObjectMeta objectMeta, ResolverContext rCtx);

}
