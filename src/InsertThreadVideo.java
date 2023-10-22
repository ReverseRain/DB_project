import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertThreadVideo implements Runnable {
    private Connection connection;

    public InsertThreadVideo(Connection connection, PreparedStatement stmt, PreparedStatement stmtLike,
                             PreparedStatement stmtCoin, PreparedStatement stmtFar,
                             PreparedStatement stmtView, List<String[]> dataList) {
        this.connection = connection;
        this.stmt = stmt;
        this.stmtLike = stmtLike;
        this.stmtCoin = stmtCoin;
        this.stmtFar = stmtFar;
        this.stmtView = stmtView;
        this.dataList = dataList;
    }

    private PreparedStatement stmt, stmtLike, stmtCoin, stmtFar, stmtView;
    private List<String[]> dataList;

    @Override
    public void run() {
        String[] lineList;
        for (int j = 0; j < dataList.size(); j++) {
            lineList = dataList.get(j);
            long num = 0;
            try {
                loadDataVideo(lineList[0], lineList[1], lineList[2], lineList[3], lineList[4], lineList[5], lineList[6], lineList[7], lineList[8], lineList[9]);
                lineList[10] = changeFollowing(lineList[10]);
                lineList[11] = changeFollowing(lineList[11]);
                lineList[12] = changeFollowing(lineList[12]);
                String[] like = lineList[10].split(",");
                for (int i = 0; i < like.length; i++) {
                    loadDataLike(lineList[0], like[i]);
                }
                String[] coin = lineList[11].split(",");
                for (int i = 0; i < coin.length; i++) {
                    loadDataCoin(lineList[0], coin[i]);

                }
                String[] far = lineList[12].split(",");
                for (int i = 0; i < far.length; i++) {
                    loadDataFavorite(lineList[0], far[i]);

                }
                lineList[13] = changeView(lineList[13]);
                String[] view = lineList[13].split(",");
                for (int i = 0; i < view.length; i++) {
                    loadDataView(lineList[0], view[i], view[++i]);
                }
                num++;
                if (num % 300 == 0) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("finish");
    }

    private void loadDataCoin(String BV, String user_mid) throws SQLException {
        if (connection != null) {
            stmtCoin.setString(1, BV);
            stmtCoin.setString(2, user_mid);
            stmtCoin.addBatch();
        }
    }

    private void loadDataLike(String BV, String user_mid) throws SQLException {
        if (connection != null) {
            stmtLike.setString(1, BV);
            stmtLike.setString(2, user_mid);
            stmtLike.addBatch();
        }
    }

    private void loadDataFavorite(String BV, String user_mid) throws SQLException {
        if (connection != null) {
            stmtFar.setString(1, BV);
            stmtFar.setString(2, user_mid);
            stmtFar.addBatch();
        }
    }

    private void loadDataView(String BV, String user_mid, String watch_time) throws SQLException {
        if (connection != null) {
            stmtView.setString(1, BV);
            stmtView.setString(2, user_mid);
            stmtView.setString(3, watch_time);
            stmtView.addBatch();
        }
    }

    private void loadDataVideo(String BV, String title, String owner_mid, String owner_name,
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

    public static String changeFollowing(String following) {  //与changecoin等的方法相同
        following = following.replace("'", "");
        following = following.replace("[", "");
        following = following.replace("]", "");
        following = following.replace("\"", "");
        following = following.replace(" ", "");
        return following;
    }

    public static String changeView(String view) {
        view = view.replace(" ", "");
        view = view.replace("'", "");
        view = view.replace("(", "");
        view = view.replace(")", "");
        view = view.replace("[", "");
        view = view.replace("]", "");
        view = view.replace("\"", "");
        return view;
    }
}
