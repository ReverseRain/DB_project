import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class tryRead {

    private static PreparedStatement stmt = null;
    private static Connection connection = null;
    public static void main(String[] args) throws IOException {
        BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\danmu.csv"));
        String line;
        if (getConnection("localhost","5432","postgres",
                "postgres","4165202Lyf")){
            try {
                stmt=connection.prepareStatement("insert into video_1(BV,mid,time,content)"+"values(?,?,?,?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            while ((line = reader.readLine())!=null){
                String[] inform=line.split(",");
                if (inform.length==4){
                try {
                    loadData(inform[0],inform[1],inform[2],inform[3]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(line);}
            }
            System.out.println("finish");
            try {
                stmt.executeBatch();
                stmt.clearBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("final");
        };
    }

    private static boolean getConnection(String host,String port,String dbname,String user,String pwd) {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            connection = DriverManager.getConnection(url, user, pwd);
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
            return false;
        }
    }


    private static void loadData(String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, content);
            stmt.addBatch();
        }
    }

    private static void loadData(String BV, String title, Integer owner_mid,
                                 Date commit_time,Date review_time,Date public_time,
                                 Integer duration,String description,Integer reviewer) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, title);
            stmt.setInt(3, owner_mid);
            stmt.setDate(4, commit_time);
            stmt.setDate(5, review_time);
            stmt.setDate(6, public_time);
            stmt.setInt(7, duration);
            stmt.setString(8, description);
            stmt.setInt(9, reviewer);
            stmt.addBatch();
        }
    }
    private static void loadData(String BV,String mid,String time,String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, mid);
            stmt.setString(3, time);
            stmt.setString(4, content);
            stmt.addBatch();
        }
    }
}
