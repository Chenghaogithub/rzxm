import com.alibaba.fastjson.JSON;
import com.utils.Pager;

public class Test01 {

    public static void main(String[] args) {
        Pager p = new Pager();
        String s = JSON.toJSONString(p);
        System.out.println(s);
    }
}
