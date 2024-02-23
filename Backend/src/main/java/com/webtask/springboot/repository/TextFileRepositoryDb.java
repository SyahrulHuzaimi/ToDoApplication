//package com.webtask.springboot.repository;
//
//import com.webtask.springboot.domain.Task;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TextFileRepositoryDb implements DbTaskRepository {
//
//    private final String FILENAME = "tasks.txt";
//
//    public TextFileRepositoryDb(){
//        File textfile = new File(FILENAME);
//        try {
//            textfile.createNewFile();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Override
//    public List<Task> findAll() {
//        List<Task> tasks = new ArrayList<>();
//        try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
//            String line;
//            while ((line=br.readLine()) != null){
//                tasks.add(new Task(line));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return tasks;
//    }
//
//    @Override
//    public void saveAll(List<Task> tasks) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
//            for (Task t: tasks){
//                String task = t.getTask();
//                bw.write(task);
//                bw.newLine();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
