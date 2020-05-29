package com.example.restservice;


import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

//import org.junit.Assert;
import java.util.Scanner;

class Directory {
    static final int DIR_MAX_ELEMS = 20;

    static String path;

    public static void create(String pathToCreate, String name) {
        path = pathToCreate + '\\' + name;
        File dir = new File(pathToCreate);


        File file = new File(path);

        int numberOfSubfolders = 0;
        File listDir[] = dir.listFiles();
        if (listDir != null) {
            for (int i = 0; i < listDir.length; i++) {
                if (listDir[i].isDirectory()) {
                    numberOfSubfolders++;
                }
            }
        }

        //Creating a File object

        //Creating the directory
        ;
        if (!(numberOfSubfolders < DIR_MAX_ELEMS)) {
            System.out.println("Sorry couldn’t create specified directory");
            return;
        }
        if (file.mkdir()) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }

    }

    public static void delete(String path) {
        File dir = new File(path);
        if (!(dir.exists())) {
            System.out.println("Не існує");
            return;
        }
        if (!(dir.isDirectory())) {
            System.out.println("Не директорія");
            return;
        }
        dir.delete();
        System.out.println("Директорія видалена.");
    }

    public static void content(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            for (int i = 0; i < dir.listFiles().length; i++) {
                System.out.println(dir.listFiles()[i].getName());
            }
            return;
        }

    }

    public static void move(String path, String newPath) throws IOException, ClassNotFoundException {
        if (new File(path).isDirectory()) {
            {
                File dir[] = new File(path).listFiles();
                File newDir = new File(newPath);
                create(newDir.getPath(), new File(path).getName());
                for (int i = 0; i < dir.length; i++) {
                    //newDir = new File

                    move(dir[i].getPath(), newDir.getPath() + '\\' + new File(path).getName());
                    dir[i].delete();
                }
                return;
            }
        }
        if (!TextFile.check(path)) {
            Object data = BinaryFile.read(path);


            BinaryFile.create(newPath + '\\' + new File(path).getName(), data);
            //System.out.println(new File(path).delete());
            BinaryFile.delete(path);
        } else {
            String content = TextFile.read(path);
            TextFile.create(newPath + '\\' + new File(path).getName(), content);
            TextFile.delete(path);
        }
    }


}

class BinaryFile<T> {
    public static void create(String path, Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        fos.close();
        oos.close();

    }

    public static Object read(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object val = ois.readObject();
        fis.close();
        ois.close();

        return val;
    }

    public static void delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

    }

    public static void move(String path, String newPath) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (!file.exists() || file.isDirectory() || !new File(newPath).isDirectory()) {
            System.out.println("file does not exist");
            return;
        }
        newPath += '\\' + file.getName();
        Object obj = read(path);
        File newFile = new File(newPath);
        newFile.createNewFile();
        create(newPath, obj);
        file.delete();
    }
}

class TextFile {
    public static void create(String path, String info) throws IOException {
        if (!check(path)) {
            System.out.println('n');
            return;
        }
        if (new File(path).exists()) {
        } else {
            boolean b = new File(path).createNewFile();
        }
        FileWriter writer = new FileWriter(path, true);
        PrintWriter printer = new PrintWriter(writer);
        printer.println(info);
        printer.close();
        writer.close();
    }

    public static void move(String path, String newPath) throws IOException, ClassNotFoundException {
        if (!check(path)) {
            System.out.println("error");
            return;
        }
        File file = new File(path);
        if (!file.exists() || file.isDirectory() || !new File(newPath).isDirectory()) {
            System.out.println("error");
            return;
        }
        newPath += '\\' + file.getName();
        String str = read(path);
        File newFile = new File(newPath);
        newFile.createNewFile();
        create(newPath, str);
        boolean b = file.delete();
    }

    public static void append(String path, String str) throws IOException {
        if (!check(path)) {
            System.out.println('n');
            return;
        }
        if (new File(path).exists()) {
        } else {
            System.out.println("No file exists");
        }
        FileWriter writer = new FileWriter(path, true);
        PrintWriter printer = new PrintWriter(writer);
        printer.println(str);
        printer.close();
        writer.close();
    }

    public static boolean check(String txt) {
        if (txt.charAt(txt.length() - 1) == 't') {
            if (txt.charAt(txt.length() - 2) == 'x') {
                if (txt.charAt(txt.length() - 3) == 't') {
                    if (txt.charAt(txt.length() - 4) == '.') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String read(String path) throws IOException {
        FileReader reader = new FileReader(path);
        BufferedReader bRreader = new BufferedReader(reader);
        String res = "";
        String temp = bRreader.readLine();
        while (temp != null) {
            res += temp + "\n";
            temp = bRreader.readLine();
        }
        bRreader.close();
        reader.close();
        return res;
    }

    public static void delete(String path) {
        if (check(path)) {
            new File(path).delete();
        }
    }
}

class Buffer<T> {
    final int MAX_BUF_FILE_SIZE = 25;
    String path;
    int count = 0;

    public Buffer(String Path) {
        this.path = Path;
        File buffer = new File(path);
        try {
            buffer.createNewFile();
            ArrayList<Object> list = new ArrayList<Object>();
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream((fos));
            oos.writeObject(list);
            oos.close();
            ;
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    public boolean appendData(T obj) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object list = ois.readObject();
        ArrayList data = (ArrayList) list;
        data.add(obj);
        if (count < MAX_BUF_FILE_SIZE) {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            count++;
            oos.close();
            fos.close();
            return true;
        } else {

            return false;
        }
    }

    public Object getData() throws IOException, ClassNotFoundException {
        if (count != 0) {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<Object> val = (ArrayList<Object>) ois.readObject();
            Object data = val.get(0);
            val.remove(0);
            fis.close();
            //ois.close();
            count--;
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(val);
            count++;
            oos.close();
            fos.close();
            return data;
        } else {
            return null;
        }
    }

    public void delete() {
        new File(path).delete();
    }

    public void move(String newPath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<Object> val = (ArrayList<Object>) ois.readObject();
        fis.close();
        BinaryFile.create(newPath, val);
        BinaryFile.delete(path);
    }
}

class DCCD {
    String path;

    public void create(String info, String path) throws IOException {
        String str = "";
        for (int i = 0; i < info.length(); i++) {
            int num = (int) info.charAt(i);
            num = num - 2;
            str += (char) num;
        }
        TextFile.create(path, str);
    }

    public String open() throws IOException {
        String content = TextFile.read(path);
        String str = "";
        for (int i = 0; i < content.length(); i++) {
            int num = (int) content.charAt(i);
            num = num + 2;
            str += (char) num;
        }
        return str;
    }

}

interface Future<T> {

    void Get(T action);
}

class Counter<T> implements Future {

    Long number;

    public Counter(Object action) {
        Get(action);
    }

    @Override
    public void Get(Object action) {
        number = (Long) action;
    }
}

class GetByName<T> implements Future {

    ArrayList<File> result;

    public GetByName(Object object) {
        Get(object);
    }

    @Override
    public void Get(Object action) {
        result = (ArrayList<File>) action;
    }
}

class GetTree<T> implements Future {

    String tree;

    public GetTree(Object object) {
        Get(object);
    }

    @Override
    public void Get(Object action) {
        tree = (String) action;
    }
}

class EmptyCount implements Future {
    int count;

    EmptyCount(Object object) {
        Get(object);
    }

    @Override
    public void Get(Object action) {
        count = (int) (action);
    }
}

class FS extends RecursiveTask {
    File file;
    int umova = 2;
    long count = 0;
    static int processes = Runtime.getRuntime().availableProcessors();
    String pattern = "Main.java";

    public long count() {
        if (file.listFiles() != null) {
            long sum = file.listFiles().length;
            for (File f : file.listFiles()) {
                sum += new FS(f, 1).count();
            }
            return sum;
        }
        return 0;
    }

    public long count(boolean bool) {
        if (bool == true && processes > 0) {
            umova = 1;
            return (long) compute();
        } else if (bool) {
            return count();
        }
        return file.listFiles().length;
    }

    public ArrayList<Object> search(String name) {
        pattern = name;
        if (processes > 0) {
            umova = 2;
            return (ArrayList<Object>) compute();
        }
        ArrayList<Object> res = new ArrayList<Object>();
        if (file.getName().equals(name)) {
            res.add(file);
        }
        if (file.listFiles() != null) {
ArrayList<ArrayList<Object>> lists = new ArrayList<ArrayList<Object>>();
            for (int i = 0; i < file.listFiles().length; i++) {
                lists.add(new FS(file.listFiles()[i], 2, this.pattern).search(name));
            }
for(ArrayList<Object> list : lists){
    for(Object o : list){
        res.add(o);
    }
}
        }
        return res;
    }

    public int EmptyDirectory(boolean done) {
        int empty = 0;
        if (processes > 0) {

            umova = 4;
            empty += (int) compute();
            return empty;
        }
        if (!done) {
            if (file.isDirectory()) {
                if (file.listFiles() != null) {
                    if (file.listFiles().length == 0) {
                        empty += 1;
                    }
                }
            }
        }
        if (file.listFiles() != null) {
            for (int i = 0; i < file.listFiles().length; i++) {
                empty += new FS(file.listFiles()[i], 4).EmptyDirectory(false);
            }
        }
        return empty;
    }

    public String Tree() {
        String res = new String();
        if (file.isDirectory()) {
            res += "{type: directory, name: " + file.getName();
        } else {
            res += "{type: file, name: " + file.getName() + "}";
            return res;
        }
        if (processes > 0) {

            umova = 3;
            res += (String) compute();
            res += "}";
            return res;
        } else {

            if (file.listFiles() != null) {

                for (File f : file.listFiles()) {
                    res += new FS(f, 3).Tree();
                }
                res += "}";
                return res;
            }
        }
        return "";
    }

    public FS(File target, int umova) {
        this.umova = umova;
        file = target;
    }

    public FS(File target, int umova, int available) {
        this.umova = umova;
        file = target;
        processes = available;
    }

    public FS(File target, int umova, String pattern) {
        this.umova = umova;
        file = target;
        this.pattern = pattern;
    }

    public FS(File target, int umova, int available, String pattern) {
        this.umova = umova;
        file = target;
        processes = available;
        this.pattern = pattern;
    }

    @Override
    protected Object compute() {
        if (umova == 1) {

            if (processes > 0) {
                processes -= 1;
                if (file.listFiles() != null) {
                    count = file.listFiles().length;
                    ArrayList<FS> tasks = new ArrayList<FS>();
                    for (File f : file.listFiles()) {
                        FS next = new FS(f, 1);
                        next.fork();
                        tasks.add(next);
                    }
                    for (FS next : tasks) {
                        count += (long) next.join();
                    }
                }
                processes += 1;
                return count;
            } else {
                return count(true);
            }
        } else if (umova == 2) {

            if (processes > 0) {
                processes -= 1;
                ArrayList<Object> res = new ArrayList<>();
                if (file.getName().equals(pattern)) {
                    res.add(file);
                }


                ArrayList<FS> tasks = new ArrayList<FS>();
                if (file.listFiles() != null) {

                    for (File f : file.listFiles()) {

                        FS next = new FS(f, 2, this.pattern);
                        next.fork();
                        tasks.add(next);
                    }
                    for (FS next : tasks) {
                        if (next.join() != null) {
                            for (Object list : (ArrayList<Object>) (next.join())) {
                                res.add(list);
                            }
                        }
                    }
                }
                processes += 1;
                return res;
            } else {
                return search(this.pattern);
            }
        } else if (umova == 3) {
            String res = new String();


            if (processes > 0) {
                processes -= 1;
                if (file.isDirectory()) {
                    res += "{type: directory, name: " + file.getName();
                } else {
                    res += "{type: file, name: " + file.getName() + "}";
                    return res;
                }
                ArrayList<FS> tasks = new ArrayList<FS>();
                if (file.listFiles() != null) {

                    for (File f : file.listFiles()) {

                        FS next = new FS(f, 3);
                        next.fork();
                        tasks.add(next);
                    }
                    for (FS next : tasks) {
                        if (next.join() != null) {

                            res += (String) next.join();

                        }
                    }
                }
                processes += 1;
                res += "}";
                return res;
            } else {
                res += Tree();
                return res;
            }
        } else if (umova == 4) {
            int empty = 0;
            if (file.isDirectory()) {

                if (file.listFiles() != null) {
                    if (file.listFiles().length == 0) {
                        empty += 1;
                    }
                }
            }
            if (processes > 0) {
                processes -= 1;

                ArrayList<FS> tasks = new ArrayList<FS>();
                if (file.listFiles() != null) {

                    for (File f : file.listFiles()) {

                        FS next = new FS(f, 4);
                        next.fork();
                        tasks.add(next);
                    }
                    for (FS next : tasks) {
                        if (next.join() != null) {

                            empty += (Integer) next.join();

                        }
                    }
                }
                processes += 1;

                return empty;
            } else {
                empty += (Integer) EmptyDirectory(true);
                return empty;
            }
        }
        return (long) 0;
    }
}


interface FSAction<T> {
    Future<T> Do() throws IOException;
}

class FileCounter implements FSAction {
    String path;
    boolean umova;

    public FileCounter(String path, boolean umova) {
        this.path = path;
        this.umova = umova;
    }

    @Override
    public Future<Long> Do() throws IOException {
        return new Counter(new FS(new File(path), 1).count(true));
    }
}

class FileSearcher implements FSAction {
    String path;
    String pattern;

    public FileSearcher(String path, String pattern) {
        this.path = path;
        this.pattern = pattern;
    }

    @Override
    public Future<ArrayList<File>> Do() throws IOException {
        return new GetByName(new FS(new File(path), 2).search(pattern));
    }
}

class TreeCreater implements FSAction {
    String path;
    String Tree;

    public TreeCreater(String path) {
        this.path = path;

    }

    @Override
    public Future<String> Do() throws IOException {
        return new GetTree(new FS(new File(path), 2).Tree());
    }
}/**/

class EmptyCounter implements FSAction {
    String path;

    public EmptyCounter(String path) {
        this.path = path;

    }

    @Override
    public Future<Integer> Do() throws IOException {
        return new EmptyCount(new FS(new File(path), 4).EmptyDirectory(true));
    }
}

class Controller {
    ExecutorService worker = Executors.newFixedThreadPool(100);
    ArrayList<Future<Object>> res;

    public Future<Object> submitAction(final FSAction action) throws IOException {

        worker.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    res.add(action.Do());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return action.Do();

    }

    public void Go() throws IOException {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0)
                submitAction(new TreeCreater("C:\\Users\\К\\Downloads\\lab3_CG.rar\\Lab"));
            if (i % 3 == 0)
                submitAction(new FileCounter("C:\\Users\\К\\Downloads\\lab3_CG.rar\\Lab", true));
            if (i % 4 == 0)
                submitAction(new FileSearcher("C:\\Users\\К\\Downloads\\lab3_CG.rar\\Lab", "Lab.iml"));
            if (i % 5 == 0)
                submitAction(new EmptyCounter("C:\\Users\\К\\Downloads\\lab3_CG.rar\\Lab"));
        }
    }


}

public class Main {
    public static void main(String[] args) {

        FS F = new FS(new File("C:\\Users\\Гість\\IdeaProjects\\untitled1\\gs-rest-service\\initial"), 4);
        System.out.println(new ForkJoinPool().invoke(F));
    }
}
