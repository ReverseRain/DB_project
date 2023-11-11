import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertThreadDanmu implements Runnable{
    private Connection connection;
    private PreparedStatement stmt;
    private List<String[]> dataList;
    private long start;

    public InsertThreadDanmu(Connection connection, List<String[]> dataList,long start) {
        this.connection = connection;
        try {
            stmt = connection.prepareStatement("insert into danmu(BV,mid,time,content)" + "values(?,?,?,?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.dataList = dataList;
        this.start=start;
    }

    @Override
    public void run() {
        String[] lineList;
        long num=0;
        for (int i = 0; i < dataList.size(); i++) {
            lineList= dataList.get(i);
            try {
                loadDataDanmu(lineList[0],lineList[1],lineList[2],lineList[3]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            num++;
//            System.out.println(num+"弹幕");
            if (num%500==0){
                try {
                    stmt.executeBatch();
                    stmt.clearBatch();
                    mycommit();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
    private void loadDataDanmu(String BV,String mid,String time,String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, mid);
            stmt.setString(3, time);
            stmt.setString(4, content);
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
