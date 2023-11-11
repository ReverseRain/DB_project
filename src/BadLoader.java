import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class BadLoader {
    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt2 = null;
    private static Connection connection = null;

    public static void main(String[] args) throws IOException {
        if (getConnection("localhost", "5432", "postgres",
                "postgres", "4165202Lyf")) {
            long t = System.currentTimeMillis();
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv"));
            String[] lineList = null;
            int num = 0;
            String line = reader.readLine();
            try {
                connection.setAutoCommit(false);
                stmt = connection.prepareStatement("insert into danmu(BV,mid,time,content)" + "values(?,?,?,?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String ans= reader.readLine();
            String[] inform=new String[4];
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BV")) {
                    inform=ans.split(",");
                    System.out.println(line);
                    if (inform.length >= 4) {
                        for (int i = 4; i < inform.length; i++) {
                            inform[3] = inform[3] + inform[i];
                        }
                        try {
                            loadDataDanmu(inform[0], inform[1], inform[2], inform[3]);
                            num++;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            loadDataDanmu(inform[0], inform[1], inform[2], "");
                            num++;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    ans=line;
                } else {
                    ans = ans + "\n" + line;
                    continue;
                }
                if (num % 500 == 0) {
                    try {
                        stmt.executeBatch();
                        stmt.clearBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                loadDataDanmu(inform[0], inform[1], inform[2], inform[3]);
                num++;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                stmt.executeBatch();
                stmt.clearBatch();
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("finish");
            t = System.currentTimeMillis() - t;
            System.out.println("时间" + t);
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

    private static void loadDataDanmu(String BV, String mid, String time, String content) throws SQLException {
        if (connection != null) {
            stmt.setString(1, BV);
            stmt.setString(2, mid);
            stmt.setString(3, time);
            stmt.setString(4, content);
            stmt.addBatch();
        }
    }

    private static void loadDataUser(String mid, String name, String sex, String birthday, String level, String sign, String identity) throws SQLException {
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

    private static void loadDataFollowing(String mid, String following_mid) throws SQLException {
        if (connection != null) {
            stmt2.setString(1, mid);
            stmt2.setString(2, following_mid);
            stmt2.addBatch();
        }
    }

    private static void loadDataVideo(String BV, String title, String owner_mid, String owner_name,
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

    public static String changeFollowing(String following) {
        following = following.replace("'", "");
        following = following.replace("[", "");
        following = following.replace("]", "");
        following = following.replace("\"", "");
        following = following.replace(" ", "");
        return following;
    }
}
