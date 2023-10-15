import java.io.*;
import java.sql.*;

public class tryRead {

    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;
    public static void main(String[] args) throws IOException {
        if (getConnection("localhost","5432","postgres",
                "postgres","4165202Lyf")){
            BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\users.csv"));
            String line= reader.readLine();String ans="";long num=0;int start=0;
            try {
                stmt=connection.prepareStatement("insert into user_(mid,name,sex,birthday,level,sign,identity)"+"values(?,?,?,?,?,?,?)");
                stmt2=connection.prepareStatement("insert into following(user_mid,following_mid)"+"values(?,?)");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            while ((line = reader.readLine())!=null){
//                if (line.endsWith("user")){
//                ans=ans+line;
//                String[] UserInform=ans.split(",");
//                    try {
//                        for (int i = 6; i <UserInform.length-1 ; i++) {
//                            if (UserInform[i].equals("[]")||UserInform[i].startsWith("\"[")||UserInform[i].endsWith("']")){
//                                start=i;
//                                break;
//                            }
//                            UserInform[5]=UserInform[5]+","+UserInform[i];
//                        }
//                        loadDataUser(UserInform[0],UserInform[1], UserInform[2],UserInform[3],UserInform[4],UserInform[5],UserInform[UserInform.length-1]);
//                        for (int i = start; i < UserInform.length-1 ; i++) {
//                            if (UserInform[i].equals("[]")){
//                                break;
//                            }
//                            if (i==start&&i+1==UserInform.length-1){
//                                UserInform[i]=UserInform[i].replace("[","");
//                                UserInform[i]=UserInform[i].replace("]","");
//                            }
//                            else if (i==start){
//                                UserInform[i]=UserInform[i].replace("\"[","");
//                            }
//                            else if (i+1==UserInform.length-1){
//                                UserInform[i]=UserInform[i].replace("]\"","");
//                            }
//                            UserInform[i]=UserInform[i].replace(" '","");
//                            UserInform[i]=UserInform[i].replace("'","");
//                            loadDataFollowing(UserInform[0],UserInform[i] );
//                        }
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println(ans);
//                    ans="";
//                    num++;}else {
//                    ans=ans+line+"\n";
//                }
//            }
//            System.out.println("finish");
//            try {
//                stmt.executeBatch();
//                stmt.clearBatch();
//                stmt2.executeBatch();
//                stmt2.clearBatch();
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(num);
//            System.out.println("final of users");


            reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\danmu.csv"));
            line= reader.readLine();
            num=0;
            try {
                stmt=connection.prepareStatement("insert into danmu(BV,mid,time,content)"+"values(?,?,?,?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String[] inform=reader.readLine().split(",");
            while ((line = reader.readLine())!=null){
                if (line.startsWith("BV")){
                    System.out.println(line);
                    try {
                        loadDataDanmu(inform[0],inform[1],inform[2],inform[3]);
                        num++;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    inform=line.split(",");
                }else inform[3]=inform[3]+"\n"+line;
            }
            System.out.println("finishII");
            try {
                stmt.executeBatch();
                stmt.clearBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("final of danmu");
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
