public class Scannerr {
 public static    java.util.Scanner scanner=new java.util.Scanner(System.in);
    public static String printString(String s) {
        System.out.print(s);
        return scanner.nextLine();
    }
    public static int printint(String s) {
        System.out.print(s);
        return scanner.nextInt();
    }
}
