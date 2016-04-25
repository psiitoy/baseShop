package com.rise.shop.hbase.client.hbase;

import com.rise.shop.hbase.client.hbase.model.ObjectMeta;

/**
 * Global setting, no class setting.
 *
 * @author nazario.wang
 */
public interface AnnotationResolver {

    public void resolve(ObjectMeta objectMeta, ResolverContext rCtx);

}
