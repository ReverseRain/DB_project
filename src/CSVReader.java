import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVReader {
    BufferedReader reader;
    int length;

    public CSVReader(String path) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(path));
        try {
            this.length = reader.readLine().split(",").length;
            System.out.println(length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] read() throws IOException {
        String oldLine = "";
        String[] ans = oldLine.split("");
        Boolean complete = true;
        String line;
        while ((!complete)||ans.length!=length) {
            if ((line = reader.readLine())==null){
                break;
            }
            oldLine = oldLine + line;
            complete = isComplete(complete, line);
            ans=oldLine.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
            System.out.println(complete+" "+ans.length);
        }
        return ans;
    }
    public String[] readLine() throws IOException {
        String[] ans=new String[length];
        char line;long cnt=0;int t=0;int tem;
        StringBuilder sb=new StringBuilder();
        while ((tem =  reader.read())!=-1){
            line=(char) tem;
            if (line=='\"'){
                cnt++;
            } else if (line==','&&cnt%2==0) {
                ans[t]=sb.toString();
                sb=new StringBuilder();
                t++;
            } else if (line=='\n'&&cnt%2==0) {
                ans[t]=sb.toString();
                sb=new StringBuilder();
                break;
            }else {
                sb.append(line);
            }
        }
        if (ans[length-1]!=null){
            return ans;
        }else {
            return null;
        }
    }
    public String[] readAllVideo() throws IOException {
        String[] ans=new String[length];
        char line;long cnt=0;int t=0;
        StringBuilder sb=new StringBuilder();
        while ((line = (char) reader.read())!=-1){
            if (line=='\"'){
                cnt++;
            } else if (line==','&&cnt%2==0) {
                ans[t]=sb.toString();
                sb=new StringBuilder();
                t++;
            } else if (line=='\n'&&cnt%2==0) {
                ans[t]=sb.toString();
                sb=new StringBuilder();
                break;
            }else {
                sb.append(line);
            }
        }
        if (ans[length-1]!=null){
            return ans;
        }else {
            return null;
        }
    }

    public boolean isComplete(Boolean complete, String line) {
        if (line==null){
            return true;
        }
        char[] chars = line.toCharArray();
        int cnt = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\"') {
                if (!(i + 1 < chars.length && chars[i + 1] == '\"')) {
                    cnt++;
                } else {
                    i = i + 1;
                }
            }
        }
        if (cnt % 2 == 1) {
            if (complete){
                complete=false;
            }else {
                complete=true;
            }
        }
        return complete;

    }
}

