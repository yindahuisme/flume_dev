public class test {

    public static void main(String[] args) {

        String str=System.getenv("AWS_ACCESS_KEY_ID");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(str);

    }
}
