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

public class MailMerger {

    private String fileToRead;
    private String fileToWrite;
    private List<Map<String, String>> data = new LinkedList<Map<String, String>>();
    private String templateString;

    public MailMerger(String[] args) {
        this.fileToRead = args[0];
        this.fileToWrite = args[1];
    }

    public void readFile1() {
        Path p = Paths.get(fileToRead);
        File file = p.toFile();
        List<Map<String, String>> data = new LinkedList<Map<String, String>>();
        try {
            Reader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            String firstLine = br.readLine();
            String[] categories = firstLine.split(",");
            String line;
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

    public void readFile2() {
        Path p = Paths.get(fileToWrite);
        File file = p.toFile();
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

    public void outputFiles() {
        for (Integer i = 0; i < data.size(); i++) {
            Map<String, String> first = data.get(i);
            String finalString = templateString;
            for (String variable : first.keySet()) {
                String toReplace = "<<%s>>".formatted(variable);
                finalString = finalString.replace(toReplace, first.get(variable));
            }
            String outputFilename = "%s-%d.txt".formatted(fileToRead.replaceAll(".csv", ""), i + 1);
            writeFiles(outputFilename, finalString);
        }
    }

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
