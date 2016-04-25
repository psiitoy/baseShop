package com.rise.shop.hbaseaccess.hbase;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Closeables;
import com.rise.shop.hbaseaccess.hbase.model.FieldSetting;
import com.rise.shop.hbaseaccess.hbase.model.ObjectMeta;
import com.rise.shop.hbaseaccess.hbase.model.RltState;
import com.rise.shop.hbaseaccess.hbase.reflect.HyBaseReflectUtil;
import com.rise.shop.hbaseaccess.util.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;

/**
 * 
 * like JdbcTemplate
 */
public class HyBaseOpsTemplate {
	
	private HConnection conn;
	
	private Configuration conf;
	
	public void close() {
		Closeables.closeQuietly(conn);
	}
	
	public void reConnect() {
		if (conn != null) {
			Closeables.closeQuietly(conn);
		}
		try {
			conn = HConnectionManager.createConnection(conf);
		} catch (IOException e) {
			throw new IllegalStateException("cannot create connection to hbase, please check your configuration.", e);
		}
	}
	
	public HyBaseOpsTemplate(Configuration conf) {
		this.conf = conf;
		if (conn == null) {
			try {
				conn = HConnectionManager.createConnection(conf);
			} catch (IOException e) {
				throw new IllegalStateException("cannot create connection to hbase, please check your configuration.", e);
			}
		}
	}
	
	private final static long MAX_TS = Long.MAX_VALUE;

	private HTableInterface getHTable(ObjectMeta objectMeta) {
		try {
			return conn.getTable(objectMeta.getTableName());
		} catch (IOException e) {
			throw new IllegalStateException("get HTableInterface failed.", e);
		}
	}
	
	public <T> void saveColumn(T t, String[] fieldNames) {
		Preconditions.checkNotNull(fieldNames);
		ObjectMeta objectMeta = HyBase.createIfAbsent(t.getClass());
		Preconditions.checkNotNull(objectMeta);

		byte[] rowkey = objectMeta.getRowKey(t);
		Put put = new Put(rowkey);
		
		HTableInterface htable = getHTable(objectMeta);
		
		for (String fieldName : fieldNames) {
			FieldSetting fieldSetting = objectMeta.getFieldSetting(fieldName);
			Preconditions.checkNotNull(fieldSetting);
			put.add(fieldSetting.resolveCF(), 
					Bytes.toBytes(fieldSetting.getColumn()), MAX_TS, fieldSetting.get(t));
			put.add(fieldSetting.resolveCF(), 
					Bytes.toBytes(fieldSetting.getColumn()), MAX_TS, fieldSetting.get(t));
		}
		try {
			htable.put(put);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			Closeables.closeQuietly(htable);
		}
	}
	
	public <T> void save(T t) {
		ObjectMeta objectMeta = HyBase.createIfAbsent(t.getClass());
		Preconditions.checkNotNull(objectMeta);
		
		HTableInterface htable = getHTable(objectMeta);
		
		List<FieldSetting> fieldSettings = objectMeta.toFieldSettingList();
		
		byte[] rowkey = objectMeta.getRowKey(t);
		Put put = new Put(rowkey);
		
		for (FieldSetting fieldSetting : fieldSettings) {
			put.add(fieldSetting.resolveCF(), 
					Bytes.toBytes(fieldSetting.getColumn()), MAX_TS, fieldSetting.get(t));
		}
		
		try {
			htable.put(put);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			Closeables.closeQuietly(htable);
		}
	}
	
	public <T> RltState fetch(T t) {
		return fetch(t, null);
	}
	@SuppressWarnings("unchecked")
	public <T> RltState fetch(T t, Get condition) {
		return batchFetchWithCondition(Lists.newArrayList(t), condition).get(0);
	}
	/**
	 * batch Get request.
	 */
	@SuppressWarnings({ "unchecked"})
	public <T> List<RltState> batchFetchWithCondition(List<T> tList, Get condition) {
		Class<T> clazz = (Class<T>) tList.get(0).getClass();
		ObjectMeta meta = HyBase.createIfAbsent(clazz);
		HTableInterface htable = getHTable(meta);
		List<Get> getBatch = Lists.newArrayList();
		Map<byte[], List<Integer>> tIndex = Maps.newHashMap();
		int index = 0;
		for (T t : tList) {
			byte[] rowkey = meta.getRowKey(t);
			Preconditions.checkNotNull(rowkey, "rowkey should not be null");
			Get get = copyGet(condition, rowkey);
			getBatch.add(get);
			if (tIndex.containsKey(rowkey)) {
				tIndex.get(rowkey).add(index);
			} else {
				tIndex.put(rowkey, Lists.newArrayList(index));
			}
			index++;
		}
		Object[] results = new Object[tList.size()];
		try {
			htable.batch(getBatch, results);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			Closeables.closeQuietly(htable);
		}
		List<FieldSetting> fieldSettings = meta.toFieldSettingList();
		fieldSettings = filterWithCondition(fieldSettings, condition);
		index = 0;
		List<RltState> rlt = new ArrayList<RltState>();
		for (Object objRlt : results) {
			Result result = (Result) objRlt;
			RltState rState = new RltState(!result.isEmpty());
			rlt.add(rState);
			if (rState.isExist()) {
				for (FieldSetting fieldSetting : fieldSettings) {
					fieldSetting.set(tList.get(0), result);
				}
			}
		}
		return rlt;
	}
	//TODO add Hbase Filter function, such as Prefix Filter.
	@SuppressWarnings("unchecked")
	private List<FieldSetting> filterWithCondition(
			List<FieldSetting> fieldSettings, Query condition) {
		List<FieldSetting> filterFieldSettings = Lists.newArrayList();
		if (condition == null) {
			filterFieldSettings.addAll(fieldSettings);
		} else {
			Method gfm = ClassUtils.getMethod(condition.getClass(), "getFamilyMap");
			Map<byte [], NavigableSet<byte []>> familyMap = null;
			try {
				familyMap = (Map<byte[], NavigableSet<byte[]>>) gfm.invoke(condition);
			} catch (Exception e) {
				throw new IllegalStateException("it should not happen this problem.");
			}
			if (familyMap.isEmpty()) {
				filterFieldSettings.addAll(fieldSettings);
			} else {
				for (FieldSetting fieldSetting : fieldSettings) {
					byte[] cf = fieldSetting.resolveCF();
					if (familyMap.containsKey(cf)) {
						NavigableSet<byte []> set = familyMap.get(cf);
						if (set.contains(Bytes.toBytes(fieldSetting.column))) {
							filterFieldSettings.add(fieldSetting);
						}
					}
				}
			}
		
		}
		return filterFieldSettings;
	}

	private Get copyGet(Get get, byte[] row) {
		if (get == null) {
			return new Get(row);
		} else {
			Get copy = new Get(get);
			HyBaseReflectUtil.setRow(copy, row);
			return copy;
		}
		
	}
	
	/**
	 * With start row and end row query.
	 * @param tList
	 * @param qScan
	 */
	public <T> List<T> batchFetch(Scan qScan, Class<T> clazz) {
		ObjectMeta meta = HyBase.createIfAbsent(clazz);
		HTableInterface htable = getHTable(meta);
		
		Scan scan = null;
		if (qScan == null) {
			scan = new Scan();
		} else {
			try {
				scan = new Scan(qScan);
			} catch (IOException e) {
				throw new IllegalArgumentException();
			}
		}
		
		List<FieldSetting> fieldSettings = meta.toFieldSettingList();
		fieldSettings = filterWithCondition(fieldSettings, qScan);
		List<T> tRlt = Lists.newArrayList();
		
		try {
			ResultScanner scanner = htable.getScanner(scan);
			Result rs = scanner.next();
			while(rs != null) {
				T t = ClassUtils.newInstance(clazz);
				for (FieldSetting fieldSetting : fieldSettings) {
					fieldSetting.set(t, rs);
				}
				tRlt.add(t);
				rs = scanner.next();
			}
			return tRlt;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			Closeables.closeQuietly(htable);
		}
	}
}
