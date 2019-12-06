package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

    private String regex;
    private String rootPath;
    private String outFile;

    //Main method to run our program
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect Usage, please use: regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Top level search workflow
    @Override
    public void process() throws IOException {

        //First lets create an array of Strings to store the result (matched lines) in
        List<String> resultLines = new ArrayList<>();

        //Now lets loop through all lines in all files to see if they contain the regex pattern
        for (File file : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    resultLines.add(line);
                }
            }
        }

        //Finally lets call method to write the result (all matched lines) to specified output file
        writeToFile(resultLines);
    }

    //Traverse a given directory and return all files
    @Override
    public List<File> listFiles(String rootDir) {
        //First lets get the directory using File package
        File directory = new File(rootDir);

        //Get all files and directories from our main directory using listFiles() method
        File[] allFiles = directory.listFiles();

        //create Arraylist to store a list of files
        List<File> listOfFiles = new ArrayList<>();

        //Before iterating lets check if our given directory is empty
        if (allFiles == null) {
            return null;
        }

        //iterate through all files since we know it isnt null
        for (File file : allFiles) {
            //check if file is directory, then make a recursive call on it if it is
            if (file.isDirectory()) {
                List<File> insideDirectory = listFiles(file.getAbsolutePath());
                listOfFiles.addAll(insideDirectory);
            }
            else {
                //if its just a file then add it to the list
                listOfFiles.add(file);
            }
        }
        return listOfFiles;
    }

    //Read a file and return all the lines
    @Override
    public List<String> readLines(File inputFile) {

        //Create an array list of lines to return
        List<String> resultLines = new ArrayList<>();
        try {
            //Create new buffered reader
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));

            //Save each line as a string
            String line = bReader.readLine();

            //Loop through all lines, add each line to array and move to next
            while (line != null) {
                resultLines.add(line);
                line = bReader.readLine();
            }

            //When we get to null, we can close our buffered reader
            bReader.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        //Return the array of lines
        return resultLines;
    }

    //Check to see if line contains the regex pattern
    @Override
    public boolean containsPattern(String line) {
        //returns true if line matches the specified regex pattern and vice versa
        return line.matches(regex);
    }

    //Write lines to a file
    @Override
    public void writeToFile(List<String> lines) throws IOException {
        //Create new File writer for the output file specified
        FileWriter fWriter = new FileWriter(getOutFile());

        //Create new buffered writer by passing the file writer
        BufferedWriter bWriter = new BufferedWriter(fWriter);

        //Go through all the lines in given list and write using the buffered writer
        for (String line : lines) {
            bWriter.write(line + "\n");
        }

        //When we are finished writing to file, we can close our buffered writer
        bWriter.close();
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
