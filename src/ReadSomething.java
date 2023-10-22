import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ReadSomething {
    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;
    public static void main(String[] args) throws IOException {
        if (getConnection("localhost","5432","postgres",
                "postgres","4165202Lyf")){
            CSVReader reader=new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\videos.csv");
//            CSVReader reader=new CSVReader("C:\\Users\\DELL\\Desktop\\users.csv");
            try {
                stmt=connection.prepareStatement("insert into videos(BV,title,,owner_mid,owner_name,commit_time,review_time,public_time,duration,description,reviewer)"+"values(?,?,?,?,?,?,?,?,?,?)");
//                stmt=connection.prepareStatement("insert into user_(mid,name,sex,birthday,level,sign,identity)"+"values(?,?,?,?,?,?,?)");
                stmt2=connection.prepareStatement("insert into following(user_mid,following_mid)"+"values(?,?)");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String[] lineList=null;int num=0;
            while ((lineList= reader.read()).length!=1){
                try {
                    System.out.println(lineList[0]+" "+num);
                    loadDataVideo(lineList[0],lineList[1],lineList[2],lineList[3],lineList[4],lineList[5],lineList[6],lineList[7],lineList[8],lineList[9]);
//                    loadDataUser(lineList[0],lineList[1],lineList[2],lineList[3],lineList[4], lineList[5],lineList[7]);
//                    lineList[6]=changeFollowing(lineList[6]);
//                    if (!lineList[6].equals("")){
//                    String[] followings=lineList[6].split(",");
//                    for (int i = 0; i < followings.length; i++) {
//                        loadDataFollowing(lineList[0],followings[i]);
//                    }}
                    num++;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            try {
                stmt.executeBatch();
                stmt.clearBatch();
                stmt2.executeBatch();
                stmt2.clearBatch();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("finish");

    }
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
    private static void loadDataUser(String mid,String name,String sex,String birthday,String level,String sign,String identity) throws SQLException {
        if (connection != null) {
            stmt.setString(1, mid);
            stmt.setString(2,name);
            stmt.setString(3, sex);
            stmt.setString(4,birthday);
            stmt.setString(5,level);
            stmt.setString(6,sign);
            stmt.setString(7,identity);
            stmt.addBatch();
        }
    }
    private static void loadDataFollowing(String mid,String following_mid) throws SQLException {
        if (connection != null) {
            stmt2.setString(1, mid);
            stmt2.setString(2,following_mid);
            stmt2.addBatch();
        }
    }
    private static void loadDataVideo(String BV, String title, String owner_mid,String owner_name,
                                      String commit_time, String review_time, String public_time,
                                      String duration, String description, String reviewer_mid) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, title);
            stmt.setString(3, owner_mid);
            stmt.setString(4, owner_name);
            stmt.setString(5, commit_time);
            stmt.setString(6, review_time);
            stmt.setString(7, public_time);
            stmt.setString(8, duration);
            stmt.setString(9, description);
            stmt.setString(10, reviewer_mid);
            stmt.addBatch();
        }
    }
    public static String changeFollowing(String following){
        following=following.replace("'","");
        following=following.replace("[","");
        following=following.replace("]","");
        following=following.replace("\"","");
        following=following.replace(" ","");
        return following;
    }
}
