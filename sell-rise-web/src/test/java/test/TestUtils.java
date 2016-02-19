package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by wangdi on 15-2-28.
 */
public class TestUtils {

    public static void main(String[] args) {
        JSONArray jsonArray = JSON.parseArray("[ 1425108174546]");
        System.out.printf(jsonArray.toJSONString());
    }
}
