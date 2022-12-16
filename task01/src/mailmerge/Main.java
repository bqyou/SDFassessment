package mailmerge;

public class Main {

    public static void main(String[] args) {

        MailMerger merger = new MailMerger(args);
        merger.run();
    }
}