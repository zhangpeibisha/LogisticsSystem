/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 */
public class Test {

    public static void main(String[] args) {
//        double[] value = {600,1200,900,2700,3400,700,
//        1300,2000,2800,3500,800,1400,2100,2900,3700,
//        900,1500,200,3000,3900,1000,1600,2300,3100,4000};
        double[] value = {3600,400};
        for (double v : value) {
            System.out.println(v + " : " + jisuan8(v));
        }
    }

    public static double jisuan(double n) {
        return (-19.313 + 295.27 * Math.pow(n / 1000, 1) - 165.44 * Math.pow(n / 1000, 2) + 40.874 * Math.pow(n / 1000, 3)
                - 3.8445 * Math.pow(n / 1000, 4));
    }

    public static double jisuan1(double t) {
        return ((5.56 * 5.83 * 0.85) / 0.367) * t;
    }


    public static double jisuan2(double t) {
        return ((0.793 * 5.38 * 0.85) / 0.367) * t;
    }

    public static double jisuan3(double t) {
        return ((2.769 * 5.83 * 0.85) / 0.367) * t;
    }

    public static double jisuan4(double n) {
        return 0.377*(0.367*n/(5.56*5.83));
    }

    public static double jisuan5(double n) {
        return 0.377*(0.367*n/(2.769*5.83));
    }

    public static double jisuan6(double n) {
        return 0.377*(0.367*n/(1.644*5.83));
    }

    public static double jisuan7(double n) {
        return 0.377*(0.367*n/5.38);
    }

    public static double jisuan8(double n) {
        return 0.377*(0.367*n/(5.38*0.793));
    }
}
