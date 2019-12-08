
# Java Grep Application

## Introduction

This application is essentially a Java implementation of the linux command "Grep". It recursively searches all files in the specified directory and goes line by line to find matches with the given regex pattern. It saves those matched lines and writes them to a new file which is also specified by the user. The purpose of this project was to master Core Java as it is the foundation of Data Engineering. This project utilizes Java I/O classes as well as Lambda expressions and the Stream API.

## Usage

In order to use this application, we just need to specify these three arguments: `regex rootPath outFile` when running the program. Here is an example: 
```
.*sampleRegexPattern*$ /home/dev/ /tmp/grep.out
```
So our specified regex pattern here is `.\*sampleRegexPattern\*$` and we recursively searching in the `/home/dev/` folder. Our program will go through all the lines in that directory recursively and search for lines that contain our regex pattern, and finally will output those matched lines to `/tmp/grep.out `


## Pseudocode

Here is the `process` method's pseudocode which shows how the program works:

``` Java 
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issues

Our solution is fine for us as it works on a small scale but if we need to work with large folders/files (a 50gb file described in the user story) it would take too long and we would run into a shortage of memory. We need a different solution/implementation to work with larger files since we wont have enough memory to read those larger files. This is because every line being read is stored into memory with our current implementation. 
This is why we created another implementation utilizing Streams and Lambda expressions (`JavaGrepLambdaImp.java`), there we shouldn't run into this memory issue. 

## Improvements

1. Have an option to output the number of matched lines to our terminal/IDE. This could be really useful if the user just wants to get a quick idea of how successful the search was.

2. Have an easier method to run the program where the user can get results faster (as it would be displayed right when they run it) instead of having to manually open the output file.

3. Implement a check to make sure we don't run into a memory issue described above. Instead, just alert user to use the other implementation to avoid any problems.
