package util;

import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JSONUtilTest {

    @Test
    public void t1(){
        Map<String, String> map = new HashMap<>();
        map.put("1", "A");
        map.put("2", "B");
        map.put("3", "C");
        map.put("4", "D");
        System.out.println(JSONUtil.write(map));
    }

    @Test
    public void t2(){
        InputStream is = getClass().getClassLoader().getResourceAsStream("t2.json");
        HashMap map = JSONUtil.read(is, HashMap.class);
        System.out.println(map);
    }
}
