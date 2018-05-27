/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author Pepe15224
 */
public class JDBCConnectionPoolTest extends TestCase {
    
    public JDBCConnectionPoolTest(String testName) {
        super(testName);
    }

    /**
     * Test of create method, of class JDBCConnectionPool.
     */
    public void testCreate() {
        System.out.println("JDBCConnectionPoolTest : testCreate");
        JDBCConnectionPool instance = new JDBCConnectionPool("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
            "CS2017B_27_java", "javajava");
        
        Connection result = instance.create();
        assertTrue(!result.equals(null));
    }

    /**
     * Test of expire method, of class JDBCConnectionPool.
     */
    public void testExpire() {
        try {
            System.out.println("JDBCConnectionPoolTest : testExpire");
            
            JDBCConnectionPool instance = new JDBCConnectionPool("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
                    "CS2017B_27_java", "javajava");
            Connection o = instance.create();
            instance.expire(o);
            assertTrue(o.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnectionPoolTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Test of validate method, of class JDBCConnectionPool.
     */
    public void testValidate() {
        System.out.println("JDBCConnectionPoolTest : testValidate");       
        JDBCConnectionPool instance = new JDBCConnectionPool("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://10.176.111.31;databaseName=JSONConverter",
                    "CS2017B_27_java", "javajava");
        Connection o = instance.create();
        boolean expResult = true;
        boolean result = instance.validate(o);
        assertEquals(expResult, result);

    }
    
}
