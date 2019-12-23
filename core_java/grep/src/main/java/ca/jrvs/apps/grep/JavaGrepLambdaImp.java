package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect Usage, please use: regex rootPath outFile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            //we are calling a parent method but that will call overriden methods in this class
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //listFiles method re-implemented using lambda and stream Api
    @Override
    public List<File> listFiles(String rootDir) {

        //create Arraylist to store a list of files
        List<File> listOfFiles = new ArrayList<>();

        try {
            //walk returns a Stream that is populated with Path by walking the file tree rooted at rootDir
            //next we map Path to file (toFile returns a File object representing this path)
            // and filter files with the isFile check
            //finally we convert stream to list and save it in listOfFiles
            listOfFiles = Files.walk(Paths.get(rootDir))
                    .map(Path :: toFile)
                    .filter(File :: isFile)
                    .collect(Collectors.toList());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return listOfFiles;
    }

    //readLines method re-implemented using lambda and stream Api
    @Override
    public List<String> readLines(File inputFile) {

        //Create an array list of lines to return
        List<String> resultLines = new ArrayList<>();

        try {
            //Create new buffered reader
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));

            //Store all lines in resultLines after converting to list
            //lines() returns a Stream of lines read from this BufferedReader
            resultLines = bReader.lines().collect(Collectors.toList());

            //When we are done reading lines, we can close our buffered reader
            bReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return resultLines;
    }
}
