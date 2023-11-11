import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test1 {//在此通过java程序对danmu表格进行四种操作并计时
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement stmt;

    public static void main(String[] args) throws IOException {
        if (getConnection("localhost", "5432", "postgres",
                "postgres", "4165202Lyf")) {
            long start = System.currentTimeMillis();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //测试select语句，共十组

//            String string = getAllContentDB("BV1gr4y1Y7nD");
//            System.out.println(string);
//            getALLContentIO("BV1gr4y1Y7nD");
//            //写入要加的内容
//            try {
//                CSVReader reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv");
//                String[] line;int cnt=0;
//                StringBuilder sb=new StringBuilder();
//                while ((line= reader.readLine())!=null){
//                    if (line[0].equals("BV1gr4y1Y7nD")){
//                        FileWriter writer=new FileWriter("C:\\Users\\DELL\\IdeaProjects\\DB_project\\tem.csv",true);
//                        writer.write(line[0]+","+line[1]+","+line[2]+","+line[3]+"\n");
//                        System.out.println(line[0]+","+line[1]+","+line[2]+","+line[3]);
//                        cnt++;
//                    }
//                }
//                System.out.println("数量"+cnt);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }


//            for (int i = 0; i < 10; i++) {
//                stmt.setString(1, );
//                stmt.setString(2, );
//                stmt.setString(3, );
//                stmt.setString(4, );
//            }
//            deleteSomeThingIO("C:\\Users\\DELL\\IdeaProjects\\DB_project\\Danmu2.csv","BV1gr4y1Y7nD");

            //读出删除的数据


//            try {
//                CSVReader   reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\tem.csv");
//                String[]line;int cnt=0;
//                StringBuilder sb=new StringBuilder();
//                while ((line= reader.readLine())!=null){
//                    sb.append(line[0]+","+line[1]+","+line[2]+","+line[3]+"\n");
//                    cnt++;
//                }
//                System.out.println("数量"+cnt);
//                addSomeThingIO(sb.toString());
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            addSomeThingDB("C:\\Users\\DELL\\IdeaProjects\\DB_project\\tem.csv");
            ExecutorService executorService = Executors.newFixedThreadPool(100);

            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\select.txt"));
            String line;
            List<String>datalist=new ArrayList<>();
            while ((line = br.readLine()) != null) {
                datalist.add(line);
            }
            long cnt=0;
            for (int i = 0; i < datalist.size(); i++) {
                cnt=cnt+Integer.valueOf(getAllContentDB(datalist.get(i)));
            }
//            executorService.shutdown();
            long t = System.currentTimeMillis() - start;
            System.out.println("数量"+cnt);
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

    public static String getAllContentDB(String BV) {
        StringBuilder sb = new StringBuilder();int cnt = 0;
        try {

            stmt = connection.prepareStatement("select content from danmu where BV='" + BV + "'");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("content") + "\n");
                cnt++;
            }
            connection.commit();
            System.out.println("数量" + cnt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(cnt);
//        return sb.toString();
    }

    public static void updateIO(String target, String BV) {
        String[] line;
        ArrayList<String[]> list = new ArrayList<>();
        int cnt = 0;
        try {
            CSVReader reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv");
            while ((line = reader.readLine()) != null) {
                if (line[0].equals(target)) {
                    line[0] = BV;
                    cnt++;
                }
                list.add(line);
            }
            FileWriter writer = new FileWriter("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv");
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i)[0] + "," + list.get(i)[1] + "," + list.get(i)[2] + "," + list.get(i)[3] + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数量" + cnt);
    }

    public static void updateDB(String target, String BV) {
        try {
            stmt = connection.prepareStatement("update danmu set BV=" + BV + "where BV=target");
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getALLContentIO(String BV) {//可以直接用CSVReader进行实现
        String[] line;
        int cnt = 0;
        try {
            CSVReader reader = new CSVReader("C:\\Users\\DELL\\IdeaProjects\\DB_project\\danmu.csv");
            while ((line = reader.readLine()) != null) {
                if (line[0].equals(BV)) {
                    System.out.println(line[line.length - 1]);
                    cnt++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("数量" + cnt);
    }

    public static void addSomeThingDB(String path) {
        try {
            stmt = connection.prepareStatement("insert into danmu(BV,mid,time,content)" + "values(?,?,?,?)");
            CSVReader reader = new CSVReader(path);//先把要写的写成一个文件？会不会因为读文件而增加时间
            String[] line;
            while ((line = reader.readLine()) != null) {
                stmt.setString(1, line[0]);
                stmt.setString(2, line[1]);
                stmt.setString(3, line[2]);
                stmt.setString(4, line[3]);
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addSomeThingIO(String str) {
        try {
            FileWriter writer = new FileWriter("danmuTest", true);
            writer.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //怎么通过io流实现对文件的删减？
    //？将文件读出来在写一遍？
    public static void deleteSomeThingIO(String path, String BV) {
        ArrayList<String[]> list = new ArrayList<>();
        String[] line;
        int cnt = 0;
        try {
            CSVReader reader = new CSVReader(path);
            while ((line = reader.readLine()) != null) {
                if (!line[0].equals(BV)) {
                    list.add(line);
                } else cnt++;
            }
            FileWriter writer = new FileWriter(path);//怎么重写正个文件？
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i)[0] + "," + list.get(i)[1] + "," + list.get(i)[2] + "," + list.get(i)[3] + "\n");
            }
            System.out.println("数量" + cnt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    //sql的删除操作？
    public static void deleteSomeThingDB(String BV) {
        try {
            stmt = connection.prepareStatement("delete from danmu where BV='" + BV + "'");
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
