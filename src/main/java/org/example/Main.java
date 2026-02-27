package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {


//        System.out.println("Starting JPA ........");
//        EntityManagerFactory emf= Persistence.createEntityManagerFactory("taskPU");
//        System.out.println("EntityManagerFactory called successfully......");
//        emf.close();
//        System.out.println("Closed successfully....");

        System.out.println("Starting JPA ........");

        //===== Create ========
        TaskDAO taskDAO=new TaskDAO();
        Task task1=new Task();
        task1.setTask("Learn Hibernate");
        task1.setDescription("Complete Hibernate tutorial from basic");
        taskDAO.create(task1);

        Task task2=new Task();
        task2.setTask("Build REST API");
        task2.setDescription("Building REST API using Hibernate");
        taskDAO.create(task2);


        Task task3=new Task();
        task3.setTask("Database");
        task3.setDescription("Learn Relational Database");
        taskDAO.create(task3);

        // ===== READ =====
        TaskDTO readTask = taskDAO.read(1L);
        System.out.println("Read -> " + readTask);

        // ===== GET ALL =====
        List<TaskDTO> allTasks = taskDAO.getAllTasks();
        System.out.println("All Tasks:");
        for (TaskDTO t : allTasks) {
            System.out.println(t);
        }


        // ===== UPDATE =====
        TaskDTO updated = taskDAO.update(
                task1.getId(),
                "Learn Hibernate Deeply",
                "Study entity lifecycle and dirty checking"
        );
        System.out.println("Updated -> " + updated);


        // ===== DELETE =====
        taskDAO.delete(task1.getId());
        System.out.println("After delete:");
        System.out.println(taskDAO.read(task1.getId()));


        // ===== CLOSE CONNECTION =====
        taskDAO.close();

        System.out.println("Finished Successfully");
    }
}