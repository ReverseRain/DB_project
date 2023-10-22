import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class readAgain {
    private static final int size=600;
    private static PreparedStatement stmt = null,stmtLike=null,stmtCoin=null,stmtFar=null,stmtView=null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;
    public static void main(String[] args) throws IOException {
        if (getConnection("localhost","5432","postgres",
                "postgres","4165202Lyf")){
            CSVReader reader=new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\videos.csv");
            try {
                stmt=connection.prepareStatement("insert into videos(BV,title,owner_mid,owner_name,commit_time,review_time,public_time,duration,description,reviewer)"+"values(?,?,?,?,?,?,?,?,?,?)");
//                stmt=connection.prepareStatement("insert into user_(mid,name,sex,birthday,level,sign,identity)"+"values(?,?,?,?,?,?,?)");
//                stmt2=connection.prepareStatement("insert into following(user_mid,following_mid)"+"values(?,?)");
                stmtLike=connection.prepareStatement("insert into like_(BV,user_mid)"+"values(?,?)");
                stmtCoin=connection.prepareStatement("insert into coin(BV,user_mid)"+"values(?,?)");
                stmtFar=connection.prepareStatement("insert into favorite(BV,user_mid)"+"values(?,?)");
                stmtView=connection.prepareStatement("insert into view_time(BV,user_mid,watch_time)"+"values(?,?,?)");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String[] lineList=null;int num=0,num2=0,num3=0,num4=0,num5=0;
            while ((lineList= reader.readLine())!=null){
                try {
                    System.out.println(lineList[0]);
                    loadDataVideo(lineList[0],lineList[1],lineList[2],lineList[3],lineList[4],lineList[5],lineList[6],lineList[7],lineList[8],lineList[9]);
                    lineList[10]=changeFollowing(lineList[10]);
                    lineList[11]=changeFollowing(lineList[11]);
                    lineList[12]=changeFollowing(lineList[12]);
                    String[] like=lineList[10].split(",");
                    for (int i = 0; i < like.length; i++) {
                        loadDataLike(lineList[0],like[i]);
//                        num2++;
//                        if (num2%size==0){
//                            stmtLike.executeBatch();
//                            stmtLike.clearBatch();
//                        }
                    }
                    String[] coin=lineList[11].split(",");
                    for (int i = 0; i < coin.length; i++) {
                        loadDataCoin(lineList[0],coin[i]);
//                        num3++;
//                        if (num3%size==0){
//                            stmtCoin.executeBatch();
//                            stmtCoin.clearBatch();
//                        }
                    }
                    String[] far=lineList[12].split(",");
                    for (int i = 0; i < far.length; i++) {
                        loadDataFavorite(lineList[0], far[i]);
//                        num4++;
//                        if (num4%size==0){
//                            stmtFar.executeBatch();
//                            stmtFar.clearBatch();
//                        }
                    }
                    lineList[13]=changeView(lineList[13]);
                    String[] view=lineList[13].split(",");
                    for (int i = 0; i < view.length; i++) {
                        loadDataView(lineList[0],view[i],view[++i]);
                    }
                    num++;
                    if (num%300==0){
                        try {
                            stmt.executeBatch();
                            stmt.clearBatch();
                            stmtCoin.executeBatch();
                            stmtCoin.clearBatch();
                            stmtLike.executeBatch();
                            stmtLike.clearBatch();
                            stmtFar.executeBatch();
                            stmtFar.clearBatch();
                            stmtView.executeBatch();
                            stmtView.clearBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(num);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            try {
                stmt.executeBatch();
                stmt.clearBatch();
                stmtCoin.executeBatch();
                stmtCoin.clearBatch();
                stmtLike.executeBatch();
                stmtLike.clearBatch();
                stmtFar.executeBatch();
                stmtFar.clearBatch();
                stmtView.executeBatch();
                stmtView.clearBatch();
//                stmt2.executeBatch();
//                stmt2.clearBatch();

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
    private static void loadDataCoin(String BV,String user_mid) throws SQLException {
        if (connection != null) {
            stmtCoin.setString(1, BV);
            stmtCoin.setString(2,user_mid);
            stmtCoin.addBatch();
        }
    }
    private static void loadDataLike(String BV,String user_mid) throws SQLException {
        if (connection != null) {
            stmtLike.setString(1, BV);
            stmtLike.setString(2,user_mid);
            stmtLike.addBatch();
        }
    }
    private static void loadDataFavorite(String BV,String user_mid) throws SQLException {
        if (connection != null) {
            stmtFar.setString(1, BV);
            stmtFar.setString(2,user_mid);
            stmtFar.addBatch();
        }
    }
    private static void loadDataView(String BV,String user_mid,String watch_time) throws SQLException {
        if (connection != null) {
            stmtView.setString(1, BV);
            stmtView.setString(2,user_mid);
            stmtView.setString(3,watch_time);
            stmtView.addBatch();
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
    public static String changeFollowing(String following){  //与changecoin等的方法相同
        following=following.replace("'","");
        following=following.replace("[","");
        following=following.replace("]","");
        following=following.replace("\"","");
        following=following.replace(" ","");
        return following;
    }
    public static String changeView(String view){
        view=view.replace(" ","");
        view=view.replace("'","");
        view=view.replace("(","");
        view=view.replace(")","");
        view=view.replace("[","");
        view=view.replace("]","");
        view=view.replace("\"","");
        return view;
    }

//    public static void loadOfFollowing(String following,String mid) {
//        int start=0;
//        for (int i = 0; i < following.length(); i++) {
//            if (following.charAt(i)==','){
//                loadDataFollowing(mid,following.substring(start,i));
//                start=i;
//            }
//        }
//    }
}
