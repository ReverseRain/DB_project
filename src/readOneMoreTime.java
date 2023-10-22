import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class readOneMoreTime {
    private static final int size = 600;
    private static PreparedStatement stmt = null, stmtLike = null, stmtCoin = null, stmtFar = null, stmtView = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;

    public static void main(String[] args) throws IOException {
        if (getConnection("localhost", "5432", "postgres",
                "postgres", "4165202Lyf")) {
            CSVReader reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\videos.csv");
            try {
                stmt = connection.prepareStatement("insert into videos(BV,title,owner_mid,owner_name,commit_time,review_time,public_time,duration,description,reviewer)" + "values(?,?,?,?,?,?,?,?,?,?)");
//                stmt=connection.prepareStatement("insert into user_(mid,name,sex,birthday,level,sign,identity)"+"values(?,?,?,?,?,?,?)");
//                stmt2=connection.prepareStatement("insert into following(user_mid,following_mid)"+"values(?,?)");
                stmtLike = connection.prepareStatement("insert into like_(BV,user_mid)" + "values(?,?)");
                stmtCoin = connection.prepareStatement("insert into coin(BV,user_mid)" + "values(?,?)");
                stmtFar = connection.prepareStatement("insert into favorite(BV,user_mid)" + "values(?,?)");
                stmtView = connection.prepareStatement("insert into view_time(BV,user_mid,watch_time)" + "values(?,?,?)");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            String[] lineList = null;
            List<String[]> dataList = new ArrayList<>();
            while ((lineList = reader.readLine()) != null) {
                dataList.add(lineList);
                System.out.println(lineList[0]);
            }
            for (int i = 0; i < dataList.size(); i += 1000) {
                List<String[]> subString = dataList.subList(i, Math.min(i + 1000, dataList.size()));
                InsertThreadVideo insertThreadVideo = new InsertThreadVideo(connection, stmt, stmtLike, stmtCoin, stmtFar, stmtView, subString);
                executorService.execute(insertThreadVideo);
            }
            System.out.println("congratulation");
            executorService.shutdown();
        }
    }

    private static boolean getConnection(String host, String port, String dbname, String user, String pwd) {
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
}
