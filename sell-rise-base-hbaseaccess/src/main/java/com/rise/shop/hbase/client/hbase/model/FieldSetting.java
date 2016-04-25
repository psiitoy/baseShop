package com.rise.shop.hbase.client.hbase.model;

import com.rise.shop.hbase.client.hbase.serial.ByteFieldConverter;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.util.List;

public class FieldSetting {
    public Field field;
    public String column;
    public FamilySetting familySetting;
    public boolean rowkey = false;

    ByteFieldConverter<Object> fieldConverter;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public FamilySetting getFamilySetting() {
        return familySetting;
    }

    public void setFamilySetting(FamilySetting familySetting) {
        this.familySetting = familySetting;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ByteFieldConverter<Object> getFieldConverter() {
        return fieldConverter;
    }

    public void setFieldConverter(ByteFieldConverter<Object> fieldConverter) {
        this.fieldConverter = fieldConverter;
    }

    public byte[] resolveCF() {
        return Bytes.toBytes(familySetting.name);
    }

    public byte[] get(Object t) throws RuntimeException {
        Object value;
        try {
            value = field.get(t);
            return fieldConverter.convert(value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

    }

    public void set(Object t, Result rs) {
        //TODO batch version support
        List<Cell> cell = rs.getColumnCells(this.resolveCF(), Bytes.toBytes(column));
        if (cell == null || cell.size() == 0) {
            return;
        } else {
            byte[] value = CellUtil.cloneValue(cell.get(0));
            try {
                field.set(t, fieldConverter.reverse(value));
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        }
    }
}