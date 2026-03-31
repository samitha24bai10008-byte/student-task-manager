import java.io.*;
import java.util.*;

class Task {
    String title;
    String deadline;
    boolean isDone;

    Task(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
        this.isDone = false;
    }

    public String toString() {
        return title + "," + deadline + "," + isDone;
    }
}

public class taskmanager {
    static List<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {
        loadTasks();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Task\n2. View Tasks\n3. Mark Done\n4. Delete Task\n5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String title = sc.nextLine();
                    System.out.print("Enter deadline: ");
                    String deadline = sc.nextLine();
                    tasks.add(new Task(title, deadline));
                    saveTasks();
                    break;

                case 2:
                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);
                        System.out.println(i + ". " + t.title + " | " + t.deadline + " | " + (t.isDone ? "Done" : "Pending"));
                    }
                    break;

                case 3:
                    System.out.print("Enter index: ");
                    int idx = sc.nextInt();
                    tasks.get(idx).isDone = true;
                    saveTasks();
                    break;

                case 4:
                    System.out.print("Enter index: ");
                    int del = sc.nextInt();
                    tasks.remove(del);
                    saveTasks();
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }

    static void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                pw.println(t);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    static void loadTasks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Task t = new Task(parts[0], parts[1]);
                t.isDone = Boolean.parseBoolean(parts[2]);
                tasks.add(t);
            }
        } catch (IOException e) {
            // file may not exist initially
        }
    }
}