
import java.sql.*;

public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "project1";
    private String user = "postgres";
    private String pwd = "000000";
    private String port = "5432";


    private void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    private void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public int addOneUser(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into user_(mid, name, sex, birthday, level, sign, identity) " +
                "values (?,?,?,?,?,?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(userInfo[0]));
            preparedStatement.setString(2, userInfo[1]);
            preparedStatement.setString(3, userInfo[2]);
            preparedStatement.setString(4, userInfo[3]);
            preparedStatement.setInt(5, Integer.parseInt(userInfo[4]));
            preparedStatement.setString(6, userInfo[5]);
            preparedStatement.setString(7, userInfo[6]);
            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneFllowing(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into following(user_mid, following_mid) " +
                "values (?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(userInfo[0]));
            preparedStatement.setInt(2, Integer.parseInt(userInfo[1]));
            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneVideos(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into videos(bv, title, owner_mid, commit_time, review_time, public_time, duration, description, reviewer)" +
                "values (?,?,?,?,?,?,?,?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setString(2, userInfo[1]);
            preparedStatement.setInt(3,Integer.parseInt(userInfo[2]));
            preparedStatement.setString(4, userInfo[3]);
            preparedStatement.setString(5, userInfo[4]);
            preparedStatement.setString(6, userInfo[5]);
            preparedStatement.setInt(7, Integer.parseInt(userInfo[6]));
            preparedStatement.setString(8, userInfo[7]);
            preparedStatement.setInt(9, Integer.parseInt(userInfo[8]));
            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneLike(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into like_(bv, user_mid)" +
                "values (?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setInt(2,Integer.parseInt(userInfo[1]));
            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneCoin(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into coin(bv, user_mid)" +
                "values (?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setInt(2,Integer.parseInt(userInfo[1]));
            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneFavorite(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into favorite(bv, user_mid)" +
                "values (?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setInt(2,Integer.parseInt(userInfo[1]));
            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneView_time(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into view_time(bv, user_mid, watch_time)" +
                "values (?,?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setInt(2,Integer.parseInt(userInfo[1]));
            preparedStatement.setInt(3,Integer.parseInt(userInfo[2]));
            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public int addOneDanmu(String str) {
        getConnection();
        int result = 0;
        String sql = "insert into danmu(bv, mid, time, content)" +
                "values (?,?,?,?)";
        String userInfo[] = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userInfo[0]);
            preparedStatement.setInt(2,Integer.parseInt(userInfo[1]));
            preparedStatement.setInt(3,Integer.parseInt(userInfo[2]));
            preparedStatement.setString(4, userInfo[3]);
            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

}
