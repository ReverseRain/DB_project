import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertThreadFollowing implements Runnable{
    private Connection connection;
    private PreparedStatement stmt;
    long start=0;
    private List<String[]> dataList;
    public InsertThreadFollowing(Connection connection, List<String[]> dataList,long start) {
        this.connection = connection;
        try {
            stmt = connection.prepareStatement("insert into following(user_mid,following_mid)" + "values(?,?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.dataList = dataList;
        this.start=start;
    }

    private void loadDataFollowing(String mid, String following_mid) throws SQLException {
        if (connection != null) {
            stmt.setString(1, mid);
            stmt.setString(2, following_mid);
            stmt.addBatch();
        }
    }
    public static String changeFollowing(String following) {  //与changecoin等的方法相同
        following = following.replace("'", "");
        following = following.replace("[", "");
        following = following.replace("]", "");
        following = following.replace("\"", "");
        following = following.replace(" ", "");
        return following;
    }

    @Override
    public void run() {
        String[] lineList;
        long num=0;
        for (int i = 0; i < dataList.size(); i++) {
            lineList=dataList.get(i);
            lineList[1]=changeFollowing(lineList[1]);
            String[] following=lineList[1].split(",");
            for (int j = 0; j < following.length; j++) {
                System.out.println(following[j]);
                try {if (following[j]!=""){
                    loadDataFollowing(lineList[0],following[j]);}
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            num++;
            System.out.println(num+"粉丝");
        }
        try {
            stmt.executeBatch();
            stmt.clearBatch();
            mycommit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        long cur=System.currentTimeMillis()-start;
        System.out.println("时间"+cur);
    }
    private synchronized void mycommit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
