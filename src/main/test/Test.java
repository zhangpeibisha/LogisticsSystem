import java.io.FileInputStream;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 */
public class Test {

    public static void main(String[] args) {

        String temp = "/order/viewOrderInfo";
        int first = temp.indexOf("/")+1;
        int two = temp.indexOf('/',first+1);

        System.out.println(two);

        String temp1 = temp.substring(first,two);
        String temp2 = temp.substring(two);
        System.out.println(temp1 + " * " + temp2);


    }
}
