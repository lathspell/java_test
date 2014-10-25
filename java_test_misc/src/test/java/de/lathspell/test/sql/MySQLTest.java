package de.lathspell.test.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.junit.*;
import static org.junit.Assert.*;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

// import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
public class MySQLTest {

    Connection conn;

    @Before
    public void setUp() throws Exception {
        /*
         * old style:
         *
         * new com.mysql.jdbc.Driver(); Connection conn =
         * DriverManager.getConnection("jdbc:mysql://localhost/test", "root",
         * "");
         */
        /*
         * new style mit ApplicationContext:
         *
         * Context ctx = new InitialContext(); DataSource ds =
         * (DataSource)ctx.lookup("jdbc/MySQL"); conn = ds.getConnection("root",
         * "");
         */
        /*
         * new style
         */
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(Credentials.DBURL);
        ds.setUser(Credentials.DBUSER);
        ds.setPassword(Credentials.DBPASS);
        conn = ds.getConnection();
        Assert.assertTrue("conn ist keine Verbindugn", conn instanceof Connection);

        conn.createStatement().execute("DROP TABLE IF EXISTS junit");
        conn.createStatement().execute("CREATE TABLE junit (i int, s varchar(255)) ENGINE=InnoDB");
    }

    /**
     * Diese Funktion wird nach jedem einzelnen Test aufgerufen!
     */
    @After
    public void tearDown() throws Exception {
        conn.createStatement().execute("DROP TABLE junit");
    }

    @Test
    public void select() throws Exception {
        Statement stmt = conn.createStatement();
        stmt.execute("SELECT version() as v");
        ResultSet rs = stmt.getResultSet();
        rs.next();
        String version = rs.getString("v");
        Assert.assertTrue("Kein MySQL 5.x?", version.startsWith("5.")); // "5.0.51a-12"
    }

    @Test
    public void insert_update_delete() throws Exception {
        // INSERT
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO junit SET i=?, s=?");
        pstmt.setInt(1, 42);
        pstmt.setString(2, "hello world");
        pstmt.execute();
        Assert.assertTrue("INSERT wirkungslos", pstmt.getUpdateCount() == 1);

        // INSERT verify
        ResultSet rs = conn.createStatement().executeQuery("SELECT i FROM junit");
        rs.first();
        Integer i = rs.getInt("i");
        Assert.assertTrue("INSERT fehlgeschlagen", i == 42);

        // DELETE
        int count = conn.createStatement().executeUpdate("DELETE FROM junit WHERE i=42");
        Assert.assertTrue("DELETE hat " + count + " gefunden!", count == 1);

        // DELETE verify
        rs = conn.createStatement().executeQuery("SELECT count(i) as c FROM junit WHERE i=42");
        rs.first();
        count = rs.getInt("c");
        Assert.assertTrue("DELETE fehlgeschlagen", count == 0);
    }

    @Test
    public void multipleRows() throws Exception {
        // INSERTs
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO junit SET i=?");
        for (int i = 0; i < 50; i++) {
            pstmt.setInt(1, i);
            pstmt.execute();
        }

        // SELECTs
        int i = 0;
        ResultSet rs = conn.createStatement().executeQuery("SELECT i FROM junit");
        while (rs.next()) {
            // do something
            i++;
        }
        Assert.assertTrue(i == 50);
    }

    @Test
    public void wasNull() throws Exception {
        // INSERT
        conn.createStatement().execute("INSERT INTO junit SET i=0, s=NULL");
        conn.createStatement().execute("INSERT INTO junit SET i=NULL, s='0'");

        // SELECT mit int==null => 0
        ResultSet rs = conn.createStatement().executeQuery("SELECT i FROM junit WHERE s='0'");
        rs.first();
        int i = rs.getInt("i");
        boolean was_null = rs.wasNull();
        Assert.assertTrue(was_null);
        Assert.assertTrue(i == 0);


        // SELECT mit varchar==null => null
        rs = conn.createStatement().executeQuery("SELECT s FROM junit WHERE i=0");
        rs.first();
        String s = rs.getString("s");
        was_null = rs.wasNull();
        Assert.assertTrue(was_null);
        Assert.assertTrue(s == null);
    }

    @Test
    public void resultMetaData() throws Exception {
        // INSERT
        conn.createStatement().execute("INSERT INTO junit SET i=1, s='test'");

        ResultSet rs = conn.createStatement().executeQuery("SELECT *, i as renamed_i FROM junit");
        ResultSetMetaData rsm = rs.getMetaData();

        Assert.assertTrue(rsm.getCatalogName(1).equals(Credentials.DBNAME));
        Assert.assertTrue(rsm.getTableName(1).equals("junit"));
        Assert.assertTrue(rsm.getColumnCount() == 3);
        Assert.assertTrue(rsm.getColumnName(3).equals("i"));
        Assert.assertTrue(rsm.getColumnLabel(3).equals("renamed_i"));
        Assert.assertTrue(rsm.getColumnClassName(1).equals("java.lang.Integer"));
        Assert.assertTrue(rsm.getColumnType(1) == java.sql.Types.INTEGER);
        Assert.assertTrue(rsm.isNullable(1) == ResultSetMetaData.columnNullable);
    }

    @Test
    public void batch() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO junit SET i=?");

        // INSERT
        conn.setAutoCommit(false);
        for (int i = 0; i < 10; i++) {
            pstmt.setInt(1, i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        conn.commit();

        // INSERT verify
        ResultSet rs = conn.createStatement().executeQuery("SELECT count(*) as c FROM junit");
        rs.next();
        Assert.assertTrue(rs.getInt("c") == 10);


        // SELECT
        Statement stmt = conn.createStatement();
        stmt.setFetchSize(100);
        rs = stmt.executeQuery("SELECT * FROM junit");
        rs.last();
        Assert.assertTrue(rs.getRow() == 10);
    }

    @Test
    public void databaseMetaData() throws Exception {
        java.sql.DatabaseMetaData dmd = conn.getMetaData();

        // VERSION
        assertTrue(dmd.getDatabaseProductName().equals("MySQL"));
        assertTrue(dmd.getDatabaseProductVersion().compareTo("5.0") > 0);

        // FEATURES
        Assert.assertTrue(dmd.supportsResultSetConcurrency(
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE));

        // SHOW TABLES
        String[] types = {"TABLE"};
        ResultSet rs = dmd.getTables(null, null, "junit%", types);
        rs.next();
        assertTrue(rs.getString("TABLE_NAME").equals("junit"));

        // DESC
        rs = dmd.getColumns(Credentials.DBNAME, null, "junit", "%");
        rs.next();
        assertTrue(rs.getString("COLUMN_NAME").equals("i"));
        assertTrue(rs.getInt("DATA_TYPE") == java.sql.Types.INTEGER);
    }

    @Test
    public void testTransactions() throws Exception {
        // Supported?
        java.sql.DatabaseMetaData dmd = conn.getMetaData();
        Assert.assertTrue(dmd.supportsTransactions());

        conn.setAutoCommit(false);

        // attempt
        conn.createStatement().execute("INSERT INTO junit SET i=0");
        conn.createStatement().execute("INSERT INTO junit SET i=1");
        conn.createStatement().execute("INSERT INTO junit SET i=2");
        conn.rollback();

        // and again
        conn.createStatement().execute("INSERT INTO junit SET i=3");
        conn.commit();

        ResultSet rs = conn.createStatement().executeQuery("SELECT count(*) FROM junit");
        Assert.assertTrue(rs.next());
        Assert.assertTrue(rs.getInt(1) == 1);
    }
}