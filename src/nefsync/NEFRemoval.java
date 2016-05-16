package nefsync;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class NEFRemoval {

    private final String filename;

    private NEFRemoval(String f) {
        filename = f;
    }

    public void deleteFiles() throws IOException {
        HashSet<File> nefFiles = new HashSet<>();
        HashSet<String> jpgFiles = new HashSet<>();

        try {
            final Stream<Path> stream = Files.list(Paths.get(filename));
            stream.forEach(path -> {
                File file = path.toFile();
                if (file.isFile()) {
                    //System.out.println("File " + listOfFiles[i].getName());
                    if (file.getName().contains(".NEF")) {
                        nefFiles.add(file);
                    } else {
                        jpgFiles.add(getFileName(file));
                    }
                } else if (file.isDirectory()) {
                    System.out.println("Directory " + file.getName());
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        for (File nefFile : nefFiles) {
            String temp = getFileName(nefFile);
            if (!jpgFiles.contains(temp)) {
                nefFile.delete();
                System.out.println("Deleted " + nefFile.getName());
            }
        }
    }

    private String getFileName(File fileToConvert) {
        return fileToConvert.getName().substring(0, 8);
    }

    public static void main(String[] args) throws IOException {
        NEFRemoval attempt = new NEFRemoval("/Users/Oksana/Documents/Photos/2016.05.14ToreyPines/");
        attempt.deleteFiles();
    }
}
