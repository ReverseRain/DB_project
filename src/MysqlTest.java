import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class MysqlTest {
    static Connection con;
    public static String user;
    public static  String password;
    private static PreparedStatement stmtDanmu = null;
    private static PreparedStatement stmtUsers = null;
    public static void  getConnection () { // 建立返回值为Connection的方法
        try { // 加载数据库驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        user = "root";//数据库登录名
        password = "h143693216";//密码
        try { // 通过访问数据库的URL获取数据库连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true", user, password);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
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
    private static void loadDataUsers(String[] userInfo)
            throws SQLException {
        // preStatementUsers();
        if (con != null) {
            stmtUsers.setString(1, userInfo[0]);
            stmtUsers.setString(2, userInfo[1]);
            stmtUsers.setString(3, userInfo[2]);
            stmtUsers.setString(4, userInfo[3]);
            stmtUsers.setString(5, userInfo[4]);
            stmtUsers.setString(6, userInfo[5]);
            stmtUsers.setString(7, userInfo[6]);
            stmtUsers.addBatch();
            // stmtUsers.executeUpdate();
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
    private static void preStatementDanmu() throws SQLException {
        try {
            stmtDanmu = con.prepareStatement("insert into danmu(bv, mid, time, content)"
                    + " values(?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            con.close();
            System.exit(1);
        }
    }
    private static void preStatementUsers() {
        try {
            stmtUsers = con.prepareStatement("insert into user_(mid, name, sex, birthday, level, sign, identity)"
                    + " values(?,?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws SQLException {
        getConnection();
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
                    con.close();
                    System.exit(1);
                }
            }
            con.close();
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
            con.close();
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
            con.close();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtDanmu.close();
            } catch (Exception e2) {
            }
            con.close();
            System.exit(1);
        }

 //Lusers1();





    }
    public static void Lusers1(){
        String filename="src//users.csv";
        try {
            BufferedReader bf=new BufferedReader(new FileReader(filename));
            getConnection();
            con.setAutoCommit(false);
            preStatementUsers();
            String[]userinfo=new String[8];
            String[] users=new String[7];
            long count=0;
            bf.readLine();
            int qcount=0;
            int ccount=0;
            int temp ;
            char single;
            StringBuilder sb=new StringBuilder();
            while ((temp=bf.read())!=-1){
                single=(char) temp;
                if (single=='"'){
                    qcount++;
                }
                if (qcount%2==0&&(single==','||single=='\n')){
                    userinfo[ccount]=sb.toString();
                    sb.setLength(0);
                    ccount++;
                }else {
                    sb.append(single);
                }
                if (ccount==8){
                    for (int i = 0; i < 6; i++) {
                        users[i]=userinfo[i];
                        //System.out.println(videos[i]);
                    }
                    users[6]=userinfo[7];
                    loadDataUsers(users);




                    count++;
                    System.out.println(count);
                    sb.setLength(0);
                    qcount=0;
                    ccount=0;
                }
            }


            stmtUsers.executeBatch();
            stmtUsers.clearBatch();
            stmtUsers.close();
            con.commit();
            closeConnection();


        }catch (IOException e){
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtUsers.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            try {
                con.rollback();
                stmtUsers.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        }

    }

}
