package org.example;

public class TaskDTO {
    private long id;
    private String task;
    private String description;

    public TaskDTO(){

    }

    public TaskDTO(long id, String task, String description) {
        this.id = id;
        this.task = task;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
