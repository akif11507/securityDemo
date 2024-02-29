package com.example.securitydemo.Utils.LogicalHierarchy;


public class HierarchicalModelling {


    public static void main(String[] args) {
        Task parentTask = Task.builder()
                .title("parent")
                .description("this is parent")
                .build();
        Task childTask = Task.builder()
                .title("child")
                .description("this is child")
                .parentTask(parentTask)
                .build();
        System.out.println(childTask.getParentTask().getTitle());
    }
}
