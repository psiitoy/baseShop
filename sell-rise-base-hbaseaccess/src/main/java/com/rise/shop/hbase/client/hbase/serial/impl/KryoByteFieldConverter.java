package com.rise.shop.hbase.client.hbase.serial.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.reflect.TypeToken;
import com.rise.shop.hbase.client.hbase.serial.ByteFieldConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

/**
 * Created by asus on 2015/9/26.
 *
 * @author nazario.wang
 */
public class KryoByteFieldConverter<F> implements ByteFieldConverter<F> {

    private Type fieldType;

    private final static KryoFactory factory = new KryoFactory() {
        @Override
        public Kryo create() {
            return new Kryo();
        }
    };
    private final static KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    @Override
    public byte[] convert(F f) {
        byte[] rlt;
        if (f == null) {
            rlt = new byte[0];
        } else {
            Kryo kryo = pool.borrow();
            try {
                Output output = new Output(new ByteArrayOutputStream());
                kryo.writeObject(output, f);
                rlt = output.toBytes();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            } finally {
                pool.release(kryo);
            }
        }
        return rlt;
    }

    @Override
    public F reverse(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        } else {
            Kryo kryo = pool.borrow();
            try {
                Input input = new Input(new ByteArrayInputStream(bytes));

                Class<?> c = TypeToken.of(this.fieldType).getRawType();

                return (F) kryo.readObject(input, c);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            } finally {
                pool.release(kryo);
            }
        }
    }

    @Override
    public void setType(Type type) {
        this.fieldType = type;
    }

}
