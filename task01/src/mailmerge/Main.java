package mailmerge;

public class Main {

    public static void main(String[] args) {

        MailMerger merger = new MailMerger(args);
        merger.readFile1();
        merger.readFile2();
        merger.outputFiles();

    }
}