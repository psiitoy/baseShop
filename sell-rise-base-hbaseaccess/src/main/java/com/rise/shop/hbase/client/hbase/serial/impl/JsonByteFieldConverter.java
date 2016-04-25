package com.rise.shop.hbase.client.hbase.serial.impl;

import com.rise.shop.hbase.client.hbase.serial.ByteFieldConverter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.lang.reflect.Type;

public class JsonByteFieldConverter<F> implements ByteFieldConverter<F> {

    private ObjectMapper mapper = new ObjectMapper();

    private Type fieldType;

    public byte[] convert(F f) {
        byte[] rlt = null;
        if (f == null) {
            rlt = new byte[0];
        } else {
            try {
                rlt = mapper.writeValueAsBytes(f);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return rlt;
    }

    @SuppressWarnings({"unchecked"})
    public F reverse(byte[] t) {
        if (t == null || t.length == 0) {
            return null;
        } else {
            try {
                return (F) mapper.readValue(t, new TypeReference<F>() {

                    @Override
                    public Type getType() {
                        return JsonByteFieldConverter.this.fieldType;
                    }

                });
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void setType(Type type) {
        this.fieldType = type;
    }

}
