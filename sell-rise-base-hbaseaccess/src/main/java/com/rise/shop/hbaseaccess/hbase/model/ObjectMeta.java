package com.rise.shop.hbaseaccess.hbase.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rise.shop.hbaseaccess.hbase.RowkeyExtractor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * describe the object meta, singleton, <br/>
 * 1. 支持exclude field 功能.
 * 2. 支持复杂对象序列化功能
 * 3. 支持MaxVersion功能
 * 4. 使用注解进行支持
 * 5. 支持基本类型
 * 6. 使用泛型进行适配转换. 
 */
public class ObjectMeta {

    private HTableDescriptor htd;

    private Class<?> modelType;

    private FamilySetting defaultFamilySetting;

    private Set<String> excludeFieldNames = Sets.newHashSet();

    public boolean contains(String fieldName) {
        return excludeFieldNames.contains(fieldName);
    }

    private Map<String, FamilySetting> familySettings = Maps.newHashMap();

    private Map<String, FieldSetting> fieldSettings = Maps.newHashMap();

    private RowkeyExtractor rowkeyExtractor;

    private String tableName;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRowkeyExtractor(RowkeyExtractor rowkeyExtractor) {
        this.rowkeyExtractor = rowkeyExtractor;
    }

    public ObjectMeta(Class<?> modelType) {
        this.modelType = modelType;
    }

    public byte[] getRowKey(Object object) {
        return this.rowkeyExtractor.getRowkey(object);
    }

    public HTableDescriptor getHtd() {
        return htd;
    }

    public List<FieldSetting> toFieldSettingList() {
        return Lists.newArrayList(fieldSettings.values());
    }


    static Logger logger = Logger.getLogger(ObjectMeta.class);

//	private Map<String, Object> extAttrs = Maps.newHashMap();

    public void addExcludeFieldName(String name) {
        this.excludeFieldNames.add(name);
    }

    public Class<?> getModelType() {
        return modelType;
    }

    public FamilySetting getDefaultFamilySetting() {
        return defaultFamilySetting;
    }

    public FamilySetting putIfAbsentFamilySetting(String familyName, int version) {
        FamilySetting familySetting = familySettings.get(familyName);
        if (familySetting == null) {
            familySetting = new FamilySetting();
            familySetting.name = familyName;
            familySetting.version = version;

            familySettings.put(familyName, familySetting);

            HColumnDescriptor hcd = new HColumnDescriptor(familyName);
            hcd.setMaxVersions(version);

            htd.addFamily(hcd);
        } else {
            familySetting = familySettings.get(familyName);
        }
        return familySetting;
    }

    public FieldSetting getFieldSetting(String fieldName) {
        return fieldSettings.get(fieldName);
    }

    public void setDefaultFamilySetting(FamilySetting defaultFamilySetting) {
        this.defaultFamilySetting = defaultFamilySetting;
    }

    public FieldSetting addFieldSetting(String arg0, FieldSetting arg1) {
        return fieldSettings.put(arg0, arg1);
    }

}
