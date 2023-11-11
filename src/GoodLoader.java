import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GoodLoader {
    private static final int size = 600;
    private static PreparedStatement stmt = null, stmtLike = null, stmtCoin = null, stmtFar = null, stmtView = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;

    public static void main(String[] args) throws IOException {
        if (getConnection("localhost", "5432", "postgres",
                "postgres", "4165202Lyf")) {
            long start=System.currentTimeMillis();
            CSVReader reader=new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\users.csv");
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

//            ExecutorService executorService1 = Executors.newFixedThreadPool(10);
//            String[] lineList1=null;
//            List<String[]>dataList1=new ArrayList<>();
//            List<String[]>dataListFollowing=new ArrayList<>();
//            while ((lineList1=reader.readLine())!=null){
//                dataList1.add(lineList1);
//                String[] following=new String[2];
//                following[0]=lineList1[0];following[1]=lineList1[6];
//                dataListFollowing.add(following);
//            }
//            for (int i = 0; i < dataList1.size(); i+=3800) {
//                List<String[]> subString=dataList1.subList(i,Math.min(i+3800,dataList1.size()));
//                InsertThreadUser insertThreadUser=new InsertThreadUser(connection,subString);
//                executorService1.execute(insertThreadUser);
//            }
//
//            ExecutorService executorServiceFollowing=Executors.newFixedThreadPool(10);
//            for (int i = 0; i < dataListFollowing.size(); i+=100000) {
//                List<String[]> subString =dataListFollowing.subList(i,Math.min(i+100000,dataListFollowing.size()));
//                InsertThreadFollowing insertThreadFollowing=new InsertThreadFollowing(connection,subString,start);
//                executorServiceFollowing.execute(insertThreadFollowing);
//            }
//            executorService1.shutdown();
//            executorServiceFollowing.shutdown();
//            System.out.println(dataListFollowing.size());
////            video part
//            reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\videos.csv");
//            ExecutorService executorService2 = Executors.newFixedThreadPool(10);
//            String[] lineList2 = null;
//            List<String[]> dataList2 = new ArrayList<>();
//            while ((lineList2 = reader.readLine()) != null) {
//                dataList2.add(lineList2);
//                System.out.println(lineList2[0]+"视频");
//            }
//            for (int i = 0; i < dataList2.size(); i += 1000) {
//                List<String[]> subString = dataList2.subList(i, Math.min(i + 1000, dataList2.size()));
//                InsertThreadVideo insertThreadVideo = new InsertThreadVideo(connection,subString,start);
//                executorService2.execute(insertThreadVideo);
//            }
//            System.out.println("congratulation");
//            executorService2.shutdown();
            //danmu part
            reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv");
            ExecutorService executorService3 = Executors.newFixedThreadPool(10000);
            String[] lineList3 = null;
            List<String[]> dataList3 = new ArrayList<>();
            while ((lineList3 = reader.readLine()) != null) {
                dataList3.add(lineList3);
//                System.out.println(lineList3[0]+"弹幕");
            }
            for (int i = 0; i < dataList3.size(); i += 1300) {
                List<String[]> subString = dataList3.subList(i, Math.min(i + 1300, dataList3.size()));
                InsertThreadDanmu insertThreadDanmu = new InsertThreadDanmu(connection,subString,start);
                executorService3.execute(insertThreadDanmu);
            }
            executorService3.shutdown();
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