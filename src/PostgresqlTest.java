import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class PostgresqlTest {
    private static Connection con = null;
    private static PreparedStatement stmtDanmu = null;
    private static String host = "localhost";
    private static String dbname = "project1";
    private static String user = "postgres";
    private static String pwd = "h143693216";
    private static String port = "5432";
    private static void preStatementDanmu() {
        try {
            stmtDanmu = con.prepareStatement("insert into danmu(bv, mid, time, content)"
                    + " values(?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }
    private static void loadDataDanmu(String bv, String mid, String time, String content)
            throws SQLException {
        // preStatementFollowings();
        if (con != null) {
            stmtDanmu.setString(1, bv);
            stmtDanmu.setString(2, mid);
            stmtDanmu.setString(3, time);
            stmtDanmu.setString(4, content);
            stmtDanmu.addBatch();
            // stmtFollowings.executeUpdate();
        }
    }
    public static void Ldanmu() {
        String fliename = "src//danmu.csv";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fliename));
            getConnection();
            long start=System.currentTimeMillis();
            Statement statement = null;
            if (con != null) {
                try {
                    statement = con.createStatement();
                    statement.execute("truncate table danmu");
                    statement.close();
                } catch (SQLException sq) {
                    System.err.println("SQL error: " + sq.getMessage());
                    try {
                        con.rollback();
                        statement.close();
                    } catch (Exception e2) {
                    }
                    closeConnection();
                    System.exit(1);
                }
            }
            closeConnection();
            getConnection();
            con.setAutoCommit(false);
            preStatementDanmu();
            long cnt=0;
            String line;
            String[] danmuInfo;
            bf.readLine();
            String ans = bf.readLine();
            while ((line = bf.readLine()) != null) {
                if (line.startsWith("BV")) {
                    //todo
                    danmuInfo = ans.split(",");
                    if (danmuInfo.length<4){
                        loadDataDanmu(danmuInfo[0],danmuInfo[1],danmuInfo[2],"");
                        cnt++;
                        System.out.println(ans);
                    }else if (danmuInfo.length>4){
                        for (int i = 4; i < danmuInfo.length; i++) {
                            danmuInfo[3] = danmuInfo[3] + "," + danmuInfo[i];
                        }
                        loadDataDanmu(danmuInfo[0], danmuInfo[1], danmuInfo[2], danmuInfo[3]);
                        System.out.println(ans);
                        cnt++;
                    }else {
                        loadDataDanmu(danmuInfo[0], danmuInfo[1], danmuInfo[2], danmuInfo[3]);
                        System.out.println(ans);
                        cnt++;
                    }

                    ans = line;
                } else {
                    ans = ans + "\n" + line;
                    continue;
                }

                if (cnt%500==0){
                    stmtDanmu.executeBatch();
                    stmtDanmu.clearBatch();
                }
            }
            String[] fin=ans.split(",");
            loadDataDanmu(fin[0],fin[1],fin[2],fin[3]);
            stmtDanmu.executeBatch();
            stmtDanmu.clearBatch();
            stmtDanmu.close();
            con.commit();
            closeConnection();
            System.out.println(cnt);
            long end=System.currentTimeMillis();
            System.out.println(end-start);

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmtDanmu.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtDanmu.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        }
    }
    private static void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname+"?reWriteBatchedInserts=true";
            con = DriverManager.getConnection(url, user, pwd);


        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
