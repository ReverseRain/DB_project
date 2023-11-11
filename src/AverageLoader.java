import java.io.*;
import java.sql.*;

public class AverageLoader {

    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;
    public static void main(String[] args) throws IOException {
        if (getConnection("localhost","5432","postgres",
                "postgres","4165202Lyf")){
            long t=System.currentTimeMillis();
            BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\users.csv"));
            reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv"));
            try {
                stmt=connection.prepareStatement("insert into danmu(BV,mid,time,content)"+"values(?,?,?,?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String line= reader.readLine();Boolean haveComplete=true,isFirst=false;
            long num=0;String[] inform,following,note;String temp="";
            while ((line= reader.readLine())!=null){
                note=line.split("\"");
                System.out.println(line);
                if (note.length%2!=1){
                    if (haveComplete){
                        haveComplete=false;
                        temp=temp+line;
                    }else {
                        temp=temp+"\n"+line;
                        haveComplete=true;
                    }
                }
                if (temp.equals("")){
                    inform=line.split("," +
                            "(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1 );
                }else {
                inform=temp.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1 );}

                if (haveComplete&inform.length==4){
                    try {
                        System.out.println(inform[0]+inform[1]+inform[2]+inform[3]);
                        loadDataDanmu(inform[0],inform[1],inform[2],inform[3]);
                        num++;
                        temp="";
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }//没有一种方法可以计算字符串中相应子串的个数？

            System.out.println("finishII");
            try {
                stmt.executeBatch();
                stmt.clearBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            t=System.currentTimeMillis()-t;
            System.out.println("时间"+t);

//            reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\users.csv"))



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


    private static void loadData(String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, content);
            stmt.addBatch();
        }
    }

    private static void loadDataVideo(String BV, String title, Integer owner_mid,
                                 Date commit_time,Date review_time,Date public_time,
                                 Integer duration,String description,Integer reviewer_mid) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, title);
            stmt.setInt(3, owner_mid);
            stmt.setDate(4, commit_time);
            stmt.setDate(5, review_time);
            stmt.setDate(6, public_time);
            stmt.setInt(7, duration);
            stmt.setString(8, description);
            stmt.setInt(9, reviewer_mid);
            stmt.addBatch();
        }
    }
    private static void loadDataDanmu(String BV,String mid,String time,String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, mid);
            stmt.setString(3, time);
            stmt.setString(4, content);
            stmt.addBatch();
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
}
