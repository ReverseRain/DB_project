import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SelectThread implements Runnable{
    private Connection connection;
    private PreparedStatement stmt;
    private static ResultSet resultSet;
    private List<String> dataList;

    public SelectThread(Connection connection,
                        List<String> dataList) {
        this.connection = connection;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        long t=System.currentTimeMillis();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                stmt = connection.prepareStatement("select content from danmu where BV='" + dataList.get(i) + "'");
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    sb.append(resultSet.getString("content") + "\n");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        mycommit();
        System.out.println(sb.toString());
        t=System.currentTimeMillis()-t;
        System.out.println("时间"+t);
    }
    private synchronized void mycommit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
