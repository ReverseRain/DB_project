import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
//        BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\users.csv"));
////
////        try {
////            String line=reader.readLine();
////            System.out.println(line);
////            line= reader.readLine();
////            String[] lines=line.split(",");
////            for (int i = 0; i < lines.length; i++) {
////                System.out.println(lines[i]);
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        System.setProperty("line separator","user/r/n");
//        reader=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\hah.csv"));
//        String linew;
//        while ((linew= reader.readLine())!=null){
//            String[] jai=linew.split("\"");
//            System.out.println(jai.length);
//            System.out.println(linew);
////            for (int i = 0; i < jai.length; i++) {
////                System.out.println(jai[i]);
////            }
//        }
////        String[] yu="BV1oZ4y1D7W7,1297219(,)2586.489,".split(",(?![\\)])");
////        System.out.println(yu.length);
////        String[] suo="1221212\"\"\"gouba".split("\"");
////        System.out.println(suo.length%2);//可不可以用这个判断呢？  ！！可
//        String[] why="\"\"\"".split("\"");
//        System.out.println(why.length);
        CSVReader reader=new CSVReader("C:\\Users\\DELL\\Desktop\\hah.csv");
        String[] line= null;
        BufferedReader reader2=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\hah.csv"));
        System.out.println(reader.length);
        while ((line= reader.readLine())!=null){
            System.out.println("true");
        }
//        String line2=null;
//        while ((line2=reader2.readLine())!=null){
//
//            System.out.println(line2);
//        }
//        String[] ko="".split(",")
        BufferedReader reader3=new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\huhu.txt"));
        String[] haha=reader3.readLine().split(",");
        for (int i = 0; i < haha.length; i++) {
            System.out.println(haha[i]+" "+haha[++i]);
        }
        System.out.println(haha.length);
    }
}