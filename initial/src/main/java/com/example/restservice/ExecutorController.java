package com.example.restservice;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExecutorController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    static int n = 100;

    @GetMapping("/greeting")
    public Executor greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Executor ex = new Executor(n, String.format(template, name));

        return ex;
    }
    @Test
    @GetMapping("/count/{pattern}")
    public Executor count(@RequestParam(value = "pattern", defaultValue = "Main.java") String pattern) {
        int expected = 1;
        int actual = (int)new ForkJoinPool().invoke(new FS(new File("C:\\Users\\Гість\\IdeaProjects\\untitled1\\gs-rest-service\\initial"), 2, pattern));
        Assert.assertEquals(expected, actual);
        return new Executor(counter.incrementAndGet(), new ForkJoinPool().invoke(new FS(new File("C:\\Users\\Гість\\IdeaProjects\\untitled1\\gs-rest-service\\initial"), 2, pattern)) + " - " + pattern);

    }


    @PostMapping(value = "/greetings")
    public String greetingSubmit(@RequestParam(value = "name", defaultValue = "World") String greeting) {
        n = n * 100;
        return "nice";
    }
    @Test
    @DeleteMapping("/files/delete/{id}")
    String deleteEmployee(@PathVariable String id) {

        BinaryFile.delete(id);
        if(new File(id).exists()){
            Assert.fail();
        }
        return id;
    }
    @PutMapping(value = "/files/create/{content}")
    public String Create(@RequestParam(value = "content", defaultValue = "")String content) throws IOException {
        try {
            BinaryFile.create("new file", content);
        }
        catch(Exception e) {
            return e.getMessage();
        }

        return "Файл створено";

    }

    @GetMapping("/tree/{path}")
    public Executor tree(@RequestParam(value = "path", defaultValue = "") String path) {
        return new Executor(n, (String)new ForkJoinPool().invoke( new FS(new File(path), 3)));
    }
    @Test
    public void TestExecutorController(){
        deleteEmployee("new file");
    }
}
