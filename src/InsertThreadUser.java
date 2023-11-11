import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertThreadUser implements Runnable {
    private Connection connection;
    private PreparedStatement stmt;
    private List<String[]> dataList;

    public InsertThreadUser(Connection connection, List<String[]> dataList) {
        this.connection = connection;
        try {
            stmt = connection.prepareStatement("insert into user_(mid,name,sex,birthday,level,sign,identity)" + "values(?,?,?,?,?,?,?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.dataList = dataList;
    }

    @Override
    public void run() {
        String[] lineList;
        long num = 0;
        for (int i = 0; i < dataList.size(); i++) {
            lineList = dataList.get(i);
            try {
                loadDataUser(lineList[0], lineList[1], lineList[2], lineList[3], lineList[4], lineList[5], lineList[7]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            num++;
            if (num % 30 == 0) {
                try {
                    stmt.executeBatch();
                    stmt.clearBatch();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
//            System.out.println(num+"用户");
        }
        try {
            stmt.executeBatch();
            stmt.clearBatch();
            mycommit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private void loadDataUser(String mid, String name, String sex, String birthday, String level, String sign, String identity) throws SQLException {
        if (connection != null) {
            stmt.setString(1, mid);
            stmt.setString(2, name);
            stmt.setString(3, sex);
            stmt.setString(4, birthday);
            stmt.setString(5, level);
            stmt.setString(6, sign);
            stmt.setString(7, identity);
            stmt.addBatch();
        }
    }
    private synchronized void mycommit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
