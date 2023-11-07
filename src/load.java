import java.sql.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataLoader {
    private static final int BATCH_SIZE = 500;
    private static Connection con = null;
    private static PreparedStatement stmtUsers = null;
    private static PreparedStatement stmtFollowings = null;
    private static PreparedStatement stmtDanmu = null;
    private static PreparedStatement stmtVideos=null;
    private static PreparedStatement stmtLike=null;
    private static PreparedStatement stmtCoin=null;
    private static PreparedStatement stmtFavorite=null;
    private static PreparedStatement stmtViewtime=null;

    private static String host = "localhost";
    private static String dbname = "project1";
    private static String user = "postgres";
    private static String pwd = "h143693216";
    private static String port = "5432";

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

    private static void preStatementFollowings() {
        try {
            stmtFollowings = con.prepareStatement("insert into following(user_mid, following_mid)"
                    + " values(?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }

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
    private static void preStatementVideos() {
        try {
            stmtVideos = con.prepareStatement("insert into videos(bv, title, owner_mid, owner_name, commit_time, review_time, public_time, duration, description, reviewer)"
                    +"values(?,?,?,?,?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }
    private static void preStatementLike(){
            try {
                stmtLike = con.prepareStatement("insert into like_(bv, user_mid)"
                        + " values(?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeConnection();
                System.exit(1);
            }
    }
    private static void preStatementCoin(){
        try {
            stmtCoin = con.prepareStatement("insert into coin(bv, user_mid)"
                    + " values(?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }
    private static void preStatementFavorite(){
        try {
            stmtFavorite = con.prepareStatement("insert into favorite(bv, user_mid)"
                    + " values(?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
        }
    }
    private static void preStatementview(){
        try {
            stmtViewtime = con.prepareStatement("insert into view_time(bv, user_mid, watch_time)"
                    + " values(?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeConnection();
            System.exit(1);
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

    private static void loadDataFollowings(String usermid, String followmid)
            throws SQLException {
        if (con != null) {
            stmtFollowings.setString(1, usermid);
            stmtFollowings.setString(2, followmid);
            stmtFollowings.addBatch();;
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
    private static void loadDataVideos(String[] videoinfo)
            throws SQLException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ctstamp=null;
        Timestamp rtstamp=null;
        Timestamp ptstamp=null;
        java.util.Date ct=null;
        java.util.Date rt=null;
        java.util.Date pt=null;
        try {
            ct=simpleDateFormat.parse(videoinfo[4]);
            rt=simpleDateFormat.parse(videoinfo[5]);
            pt=simpleDateFormat.parse(videoinfo[6]);
            ctstamp=new Timestamp(ct.getTime());
            rtstamp=new Timestamp(rt.getTime());
            ptstamp=new Timestamp(pt.getTime());

        }catch (ParseException e){
            e.printStackTrace();
        }

        if (con != null) {
            stmtVideos.setString(1, videoinfo[0]);
            stmtVideos.setString(2, videoinfo[1]);
            stmtVideos.setString(3, videoinfo[2]);
            stmtVideos.setString(4, videoinfo[3]);
            stmtVideos.setTimestamp(5, ctstamp);
            stmtVideos.setTimestamp(6, rtstamp);
            stmtVideos.setTimestamp(7, ptstamp);
            stmtVideos.setInt(8,Integer.parseInt(videoinfo[7]));
            stmtVideos.setString(9,videoinfo[8]);
            stmtVideos.setString(10,videoinfo[9]);
            stmtVideos.addBatch();

        }
    }
    private static void loadDataLike(String bv, String usermid)
            throws SQLException {
        if (con != null) {
            stmtLike.setString(1, bv);
            stmtLike.setString(2, usermid);
            stmtLike.addBatch();
        }
    }
    private static void loadDataCoin(String bv, String usermid)
            throws SQLException {
        if (con != null) {
            stmtCoin.setString(1, bv);
            stmtCoin.setString(2, usermid);
            stmtCoin.addBatch();
        }
    }
    private static void loadDataFavorite(String bv, String usermid)
            throws SQLException {
        if (con != null) {
            stmtFavorite.setString(1, bv);
            stmtFavorite.setString(2, usermid);
            stmtFavorite.addBatch();
        }
    }
    private static void loadDataview(String bv, String usermid,String time)
            throws SQLException {
        if (con != null) {
            stmtViewtime.setString(1, bv);
            stmtViewtime.setString(2, usermid);
            stmtViewtime.setInt(3,Integer.parseInt(time));
            stmtViewtime.addBatch();
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
    public static void Lusers(){
        String filename="src//users.csv";
        try{
            getConnection();
            Statement stmt0=null;
            Statement stmt1=null;
            if (con != null) {
                try {
                    stmt1=con.createStatement();
                    stmt1.execute("truncate table following");
                    stmt1.close();

                }catch (SQLException se1 ) {
                    System.err.println("SQL error: " + se1.getMessage());
                    try {
                        con.rollback();
                        stmt1.close();
                    } catch (Exception e2) {
                    }
                    closeConnection();
                    System.exit(1);
                }
                try {
                    stmt0 = con.createStatement();
                    stmt0.execute("delete from user_");
                    stmt0.close();
                }catch (SQLException se1 ) {
                    System.err.println("SQL error: " + se1.getMessage());
                    try {
                        con.rollback();
                        stmt0.close();
                    } catch (Exception e2) {
                    }
                    closeConnection();
                    System.exit(1);
                }

            }
            closeConnection();
            getConnection();
            con.setAutoCommit(false);



            preStatementUsers();
            preStatementFollowings();
            CsvReader reader=new CsvReader(filename);
            String[]userinfo;
            String[] users=new String[7];
            long countuser=0;
            while ((userinfo=reader.read())!=null){
                users[0]=userinfo[0];
                users[1]=userinfo[1];
                users[2]=userinfo[2];
                users[3]=userinfo[3];
                users[4]=userinfo[4];
                users[5]=userinfo[5];
                users[6]=userinfo[7];
                loadDataUsers(users);
                countuser++;
                System.out.print("第"+countuser+"个user"+" ");
                String fol=userinfo[6];
                if (!fol.equals("[]")) {
                    fol = fol.replaceAll("\\[|\\]|'|\s|\"", "");
                    String[] follows = fol.split(",");
                    for (int i = 0; i < follows.length; i++) {

                        loadDataFollowings(userinfo[0], follows[i]);
                        System.out.println("第"+(i+1)+"个following");
                    }
                }
            }

            stmtUsers.executeBatch();
            stmtUsers.clearBatch();
            stmtFollowings.executeBatch();
            stmtFollowings.clearBatch();
            stmtFollowings.close();
            stmtUsers.close();
            con.commit();
            closeConnection();



        }catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmtUsers.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtUsers.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        }
        closeConnection();


    }
    public static void Lvideos(){
        String filename="src//videos.csv";
        try{
            getConnection();
            con.setAutoCommit(false);
            preStatementVideos();
            CsvReader reader=new CsvReader(filename);
            String[]videosinfo;
            String[] videos=new String[10];
            long count=0;


            while ((videosinfo=reader.read())!=null){
                for (int i = 0; i < 10; i++) {
                    videos[i]=videosinfo[i];
                    System.out.println(videos[i]+" ");
                }
                loadDataVideos(videos);
                count++;
                System.out.println(count);

            }

                stmtVideos.executeBatch();
                stmtVideos.clearBatch();
                stmtVideos.close();
                con.commit();
                closeConnection();
        }catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmtVideos.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtVideos.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        }
    }
    public static void Lvideos1()  {
        String filename="src//videos.csv";
        try {


            BufferedReader bf=new BufferedReader(new FileReader(filename));
            getConnection();
            con.setAutoCommit(false);
            preStatementVideos();
            preStatementLike();
            preStatementCoin();
            preStatementFavorite();
            preStatementview();
            String[]videosinfo=new String[14];
           // String[]linshi;
            String[] videos=new String[10];
            int count=0;
            bf.readLine();
//            String line;
            int qcount=0;
           int ccount=0;
            int temp ;
            char single;
//            int cntvideo=0;
//            int cntlike=0;
//            int cntcoin=0;
//            int cntfavorite=0;
//            int cntview=0;
            StringBuilder sb=new StringBuilder();
           // List<String[]> list=new ArrayList<>();
            long begin=System.currentTimeMillis();
            long end;
            while ((temp=bf.read())!=-1){
                single=(char) temp;
                if (single=='"'){
                    qcount++;
                }
                if (qcount%2==0&&(single==','||single=='\n')){
                       videosinfo[ccount]=sb.toString();
                       sb.setLength(0);
                   // sb=new StringBuilder();
                        ccount++;
                }else {
                    sb.append(single);
                }
               if (ccount==14){

//                 linshi=new String[14];
//                   for (int i = 0; i < 14; i++) {
//                       linshi[i]=videosinfo[i];
//                   }
//
//                   System.out.println(list.size());
//                   list.add(linshi);

                   for (int i = 0; i < 10; i++) {
                       videos[i]=videosinfo[i];

                   }
                  if (videos[8].startsWith("\"")){
                      videos[8]=videos[8].substring(1,videos[8].length());
                  }
                   loadDataVideos(videos);


                  videosinfo[10]=videosinfo[10].replaceAll("(\")|\\[|\\]|'|\s","");
                  String[]likes=videosinfo[10].split(",");
                   for (int i = 0; i < likes.length; i++) {
                       loadDataLike(videos[0],likes[i]);

                 }

                   videosinfo[11]=videosinfo[11].replaceAll("(\")|\\[|\\]|'|\s","");
                   String[]coins=videosinfo[11].split(",");
                   for (int i = 0; i < coins.length; i++) {
                       loadDataCoin(videos[0],coins[i]);

                   }
                   videosinfo[12]=videosinfo[12].replaceAll("(\")|\\[|\\]|'|\s","");
                   String[]favorites=videosinfo[12].split(",");
                   for (int i = 0; i < favorites.length; i++) {
                       loadDataFavorite(videos[0],favorites[i]);

                   }
                   videosinfo[13]=videosinfo[13].replaceAll("(\")|\\[|\\]|'|\s|\\(|\\)|\r","");
                   String[]views=videosinfo[13].split(",");
                   for (int i = 0; i < views.length-1; i+=2) {
                       loadDataview(videos[0],views[i],views[i+1]);
                   }
                   count++;
                   if ((count%300)==0){
                       stmtVideos.executeBatch();
                       stmtVideos.clearBatch();
                       stmtLike.executeBatch();
                       stmtLike.clearBatch();
                       stmtCoin.executeBatch();
                       stmtCoin.clearBatch();
                       stmtFavorite.executeBatch();
                       stmtFavorite.clearBatch();
                       stmtViewtime.executeBatch();
                       stmtViewtime.clearBatch();
                   }
                  System.out.println(count);
                   sb.setLength(0);
                  // sb=new StringBuilder();
                   qcount=0;
                   ccount=0;
               }
            }
            stmtVideos.executeBatch();
            stmtVideos.clearBatch();
            stmtLike.executeBatch();
            stmtLike.clearBatch();
            stmtCoin.executeBatch();
            stmtCoin.clearBatch();
            stmtFavorite.executeBatch();
            stmtFavorite.clearBatch();
            stmtViewtime.executeBatch();
            stmtViewtime.clearBatch();
            con.commit();
            con.close();
            end=System.currentTimeMillis();
            System.out.println("time"+ (end-begin) );



//            ExecutorService executorService = Executors.newFixedThreadPool(10);
//            // 将插入任务提交到线程池
//            for (int i = 0; i < list.size(); i += 1000) {
//                List<String[]> subList = list.subList(i, Math.min(i + 1000, list.size()));
//                InsertThread insertThread = new InsertThread(subList,con);
//                executorService.execute(insertThread);
//            }
//            // 关闭线程池
//            executorService.shutdown();





//这是老方法
//            String ans = bf.readLine();
//            while ((line = bf.readLine()) != null){
//                if (line.startsWith("BV")){
////                    videosinfo=ans.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);//slow
////                    for (int i = 0; i < 10; i++) {
////                        videos[i]=videosinfo[i];
////                        System.out.print(videos[i]+" ");
////                    }
////                    loadDataVideos(videos);
////                    count++;
////                    System.out.println(count);
//                    long qcount=0;
//                    int ccount=0;
//                    char[]chars=ans.toCharArray();
//                    int start=0;
//                    int end=0;
//                    for (int i = 0; i < chars.length; i++) {
//                        if (chars[i]=='"'){
//                            qcount++;
//                        }
//                        if (qcount%2==0){
//                            if (chars[i]==','){
//                                end=i;
//                                videosinfo[ccount]=ans.substring(start,end);
//                                start=i+1;
//                                ccount++;
//
//                            }
//                        }
//                    }
//                    for (int i = 0; i < 10; i++) {
//                        videos[i]=videosinfo[i];
//                        System.out.print(videos[i]+" ");
//                    }
//                    loadDataVideos(videos);
//                    count++;
//                    System.out.println(count);
//
//
//
//                    ans=line;
//                }else {
//                    ans = ans + "\n" + line;
//                }
//
//            }
//这是老方法

//            stmtVideos.executeBatch();
//            stmtVideos.clearBatch();
//            stmtVideos.close();
//            stmtLike.executeBatch();
//            stmtLike.clearBatch();
//            stmtLike.close();
//            stmtCoin.executeBatch();
//            stmtCoin.clearBatch();
//            stmtCoin.close();
//            stmtFavorite.executeBatch();
//            stmtFavorite.clearBatch();
//            stmtFavorite.close();
//            stmtViewtime.executeBatch();
//            stmtViewtime.clearBatch();
//            stmtViewtime.close();
//            con.commit();
//            closeConnection();
//            end=System.currentTimeMillis();
//            System.out.println("time"+(end-begin));


        }catch (IOException e){
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmtVideos.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            try {
                con.rollback();
                stmtVideos.close();
            } catch (Exception e2) {
            }
            closeConnection();
            System.exit(1);
        }
    }
    public static void Lusers1(){
        String filename="src//users.csv";
        try {
            BufferedReader bf=new BufferedReader(new FileReader(filename));
            getConnection();
            con.setAutoCommit(false);
            preStatementUsers();
            preStatementFollowings();
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
                    String fol=userinfo[6];
                    if (!fol.equals("[]")) {
                        fol = fol.replaceAll("\\[|\\]|'|\s|\"", "");
                        String[] follows = fol.split(",");
                        for (int i = 0; i < follows.length; i++) {

                            loadDataFollowings(userinfo[0], follows[i]);
                        }
                    }



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
            stmtFollowings.executeBatch();
            stmtFollowings.clearBatch();
            stmtFollowings.close();
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


    public static void main(String[] args) {
          Ldanmu();
        //Lusers();
        //Lvideos();
        //Lusers1();
        //Lvideos1();



    }

    }








