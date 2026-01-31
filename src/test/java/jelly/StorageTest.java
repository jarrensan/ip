package jelly;

import jelly.exception.CreateFileExceptionTest;
import jelly.exception.JellyExceptionTest;
import jelly.exception.LoadFileExceptionTest;
import jelly.exception.WriteFileExceptionTest;
import jelly.task.TaskTest;
import jelly.task.TaskListTest;
import jelly.task.DeadlineTest;
import jelly.task.EventTest;
import jelly.task.TodoTest;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StorageTest {
    private File jellyFile;
    String filePath;

    public StorageTest(String filePath) {
        this.filePath = filePath;
    }

    public void create() throws CreateFileExceptionTest {
        try {
            this.jellyFile = new File(filePath);
            File parent = jellyFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdir();
            }
            if (!jellyFile.exists()) {
                jellyFile.createNewFile();
            }
        } catch (IOException e) {
            throw new CreateFileExceptionTest();
        }
    }

    public void write(TaskListTest taskList) throws WriteFileExceptionTest {
        String response = taskList.toSaveString();

        try {
            FileWriter fw = new FileWriter(jellyFile);
            fw.write(response);
            fw.close();
        } catch (IOException e) {
            throw new WriteFileExceptionTest();
        }
    }

    public ArrayList<TaskTest> load() throws JellyExceptionTest {
        try {
            create();
            BufferedReader br = new BufferedReader(new FileReader(jellyFile));
            ArrayList<TaskTest> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                TaskTest task = getTask(line);
                tasks.add(task);
            }
            return tasks;
        } catch (IOException e) {
            throw new LoadFileExceptionTest();
        }
    }

    public TaskTest getTask(String line) throws JellyExceptionTest {
        TaskTest task;
        String[] s = line.split(" \\| ");
        String type = s[0];
        boolean isMark = s[1].equals("1");
        String desc = s[2];
        switch (type) {
            case "D" -> {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate by = LocalDate.parse(s[3].substring(3).trim(), format);
                task = new DeadlineTest(desc, by);
            }
            case "E" -> {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate from = LocalDate.parse(s[3].substring(5).trim(), format);
                LocalDate to = LocalDate.parse(s[4].substring(3).trim(), format);
                task = new EventTest(desc, from, to);
            }
            case "T" -> task = new TodoTest(desc);
            default -> throw new JellyExceptionTest("Error Occurred!");
        }

        if (isMark) {
            task.markDone();
        }
        return task;
    }
}
