package com.example.securitydemo.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Runner {
    public static void main(String[] args) throws IOException {
//        System.out.println("this is start "+Thread.currentThread());
//        var myThreadWithClass = new MyThread();
////        myThread.start();
//        var myThread = new MyThreadWithInterface();
//        var threadder = new Thread(myThread);
//        myThreadWithClass.setName("asdfsdf");
//        threadder.start();
//        System.out.println("this is end "+Thread.currentThread());
//        BufferedWriter writer = new BufferedWriter("","");
//        FileHandler fileHandler = new FileHandler();
//        fileHandler.binaryFileWrite();
//        fileHandler.binaryFileRead();


        // Creating a new file
//        File file = new File("filename3.txt");
//        if (!file.exists()) {
//            try {
//                System.out.println("Creating new file " + file.getName());
//                file.createNewFile();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


//// Deleting a file
//        file.delete();
//
// Renaming a file
//        File newFile = new File("newFilename.txt");
////        newFile.createNewFile();
//        file.renameTo(newFile);
//

// Creating a new directory
//        URI uri = file.toURI();
//        URI uri1 = new File("filename3.txt").toURI();
//        System.out.println("this is uri path " + uri);
//        System.out.println("this is uri1 path " + uri1);
//        System.out.println("this is abs ",);
//        Files.move(Paths.get(uri), Path.of("/home/bjit/IdeaProjects/securityDemo-master/newDirectory/filename4.txt"));
//        File dir = new File("newDirectory2");
//        dir.mkdir();
//
//// Deleting a directory
//        dir.mkdir();
//        dir.delete();
//        URI abs1 = (new File("/home/bjit/IdeaProjects/securityDemo-master/newDirectory/filename3.txt")).toURI();
//        URI abs2 = (new File("/home/bjit/IdeaProjects/securityDemo-master/filename2.txt")).toURI();
//        System.out.println(abs1.relativize(abs2).getPath());
        Path pathOne = Paths.get("/baeldung/bar/one.txt");
        Path pathTwo = Paths.get("/baeldung/bar/two.txt");
        Path pathThree = Paths.get("/baeldung/foo/three.txt");
        URI pathOneUri = pathOne.toUri();
        URI pathTwoUri = pathTwo.toUri();
        URI pathThreeUri = pathThree.toUri();
//        System.out.println(pathOne + " " + pathTwo + " " + pathThree);
//        System.out.println(pathOne.relativize(pathThree));
//        System.out.println(pathOne.getParent().relativize(pathTwo));
        System.out.println(pathOne.getParent().toUri().relativize(pathTwoUri));
    }
}
