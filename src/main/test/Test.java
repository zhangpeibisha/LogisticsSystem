/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 */
public class Test {

    public static void main(String[] args) {
        double[] value = {600,1200,1900,2300,3000,4000};
        for (double v:value) {
            System.out.println(v + " : " + jisuan(v));
        }
    }

    public static double jisuan(double n){
        return (-19.313+295.27*Math.pow(n/1000,1)-165.44*Math.pow(n/1000,2)+40.874*Math.pow(n/1000,3)
        -3.8445*Math.pow(n/1000,4));
    }
}
