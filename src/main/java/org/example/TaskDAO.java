package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public TaskDAO(){
        this.emf= Persistence.createEntityManagerFactory("taskPU");
        this.em=emf.createEntityManager();
    }

    public TaskDTO create(Task task){
        try{
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();

            System.out.println("Task created successfully id : "+task.getId());

            return convertToDTO(task);

        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }

    }


    public TaskDTO read(Long id){
        try{
            Task task=em.find(Task.class,id);
            if(task != null){
                System.out.println("Task found with id : "+id);
                return convertToDTO(task);
            }else{
                System.out.println("Task not found with id : "+id);
                return null;
            }
        }catch (Exception e){
            System.out.println("Error reading message : "+e.getMessage());
            return null;
        }
    }



    public TaskDTO update(Long id, String newTask, String newDescription) {
        try {
            em.getTransaction().begin();

            Task task = em.find(Task.class, id);
            if (task == null)
                return null;

            task.setTask(newTask);
            task.setDescription(newDescription);

            Task updateTask=em.merge(task);

            em.getTransaction().commit();
            return convertToDTO(updateTask);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return null;
        }
    }



    public boolean delete(Long id) {
        try {
            em.getTransaction().begin();

            Task task = em.find(Task.class, id);
            if (task == null)
                return false;

            em.remove(task);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }


    public List<TaskDTO> getAllTasks(){
        List<TaskDTO> taskList=new ArrayList<>();

        try{
            String jpql="SELECT t FROM Task t";
            List<Task> tasks=em.createQuery(jpql,Task.class).getResultList();

            for(Task t:tasks){
                taskList.add(convertToDTO(t));
            }

            System.out.println("Retrived "+tasks+" tasks from database");
            return taskList;
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            return null;
        }
    }



    public void close() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }



    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTask(),
                task.getDescription()
        );
    }






}

//    public TaskDTO findById(Long id) {
//        Task task = em.find(Task.class, id);
//        return task != null ? convertToDTO(task) : null;
//    }
//
//    public List<TaskDTO> findAll() {
//        List<Task> tasks = em.createQuery("FROM Task", Task.class)
//                .getResultList();
//
//        return tasks.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
