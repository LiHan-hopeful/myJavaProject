package util;

import org.junit.Assert;
import org.junit.Test;

public class DBUtilTest {

    @Test
    public void test(){
        Assert.assertNotNull(DBUtil.getConnection());
    }
}
