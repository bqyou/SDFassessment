package mailmerge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MailMerger implements Runnable {

    private String fileToRead;
    private String fileToWrite;
    private List<Map<String, String>> data = new LinkedList<Map<String, String>>();
    private String templateString;

    // constructor for mailmerger using args from command line
    public MailMerger(String[] args) {
        this.fileToRead = args[0];
        this.fileToWrite = args[1];
    }

    @Override
    public void run() {
        readFile1();
        readFile2();
        outputFiles();
    }

    // method to read csv file
    public void readFile1() {
        Path p = Paths.get(fileToRead);
        File file = p.toFile();
        List<Map<String, String>> data = new LinkedList<Map<String, String>>();
        try {

            // read file
            Reader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            String firstLine = br.readLine();
            String[] categories = firstLine.split(",");
            String line;

            // adding each row of data as a map into data linkedlist
            while (null != (line = br.readLine())) {
                Map<String, String> newEntry = new HashMap<String, String>();
                String[] entryArray = line.split(",");
                for (Integer i = 0; i < categories.length; i++) {
                    newEntry.put(categories[i], entryArray[i]);
                }
                data.add(newEntry);
            }
            br.close();
            r.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IO Ex");
        } finally {
            System.out.println("Attempted to read csv file\n");
        }
        this.data = data;
    }

    // method to read template file to obtain the template string
    public void readFile2() {
        Path p = Paths.get(fileToWrite);
        File file = p.toFile();

        // using stringbuilder to build the string
        StringBuilder sb = new StringBuilder();
        try {
            Reader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            String line;
            while (null != (line = br.readLine())) {
                if (line.trim().equals("")) {
                    sb.append("\n");
                } else {

                    sb.append(line + "\n");
                }
            }
            br.close();
            r.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IO Ex");
        }
        System.out.println("Attempted to read template\n");
        this.templateString = sb.toString();

    }

    // method to outputfiles (replace placeholder with actual values and output)
    public void outputFiles() {
        // loop through the list for each rows of data
        for (Integer i = 0; i < data.size(); i++) {
            Map<String, String> first = data.get(i);
            String finalString = templateString;
            // loop through each keyset to replace all placeholders with value
            for (String variable : first.keySet()) {
                String toReplace = "<<%s>>".formatted(variable);
                finalString = finalString.replace(toReplace, first.get(variable));
            }
            // replacing \n to form newlines
            finalString = finalString.replaceAll("\\\\n", "\n");
            // output file name
            String outputFilename = "%s-%d.txt".formatted(fileToRead.replaceAll(".csv", ""), i + 1);
            // write file
            writeFiles(outputFilename, finalString);
        }
    }

    // method to write file
    public void writeFiles(String fileName, String content) {
        try {
            Writer w = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(w);
            bw.write(content);
            bw.flush();
            bw.close();
            System.out.println("%s written".formatted(fileName));
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

}
