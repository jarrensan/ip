package jelly;

import jelly.exception.CreateFileException;
import jelly.exception.JellyException;
import jelly.exception.LoadFileException;
import jelly.exception.WriteFileException;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.task.Deadline;
import jelly.task.Event;
import jelly.task.Todo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private File jellyFile;

    public void create(String filePath) throws CreateFileException {
        try {
            this.jellyFile = new File(filePath);
            File parent = jellyFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!jellyFile.exists()) {
                jellyFile.createNewFile();
            }
        } catch (IOException e) {
            throw new CreateFileException();
        }
    }

    public void write(TaskList taskList) throws WriteFileException {
        String response = taskList.toSaveString();
        try {
            FileWriter fw = new FileWriter(jellyFile);
            fw.write(response);
            fw.close();
        } catch (IOException e) {
            throw new WriteFileException();
        }
    }

    public TaskList load() throws JellyException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(jellyFile));
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                Task task = getTask(line);
                tasks.add(task);
            }
            return new TaskList(tasks);
        } catch (IOException e) {
            throw new LoadFileException();
        }
    }

    public Task getTask(String line) throws JellyException{
        Task task;
        String[] s = line.split(" \\| ");
        String type = s[0];
        boolean isMark = s[1].equals("1");
        String desc = s[2];
        switch (type) {
            case "D" -> {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate by = LocalDate.parse(s[3].substring(3).trim(), format);
                task = new Deadline(desc, by);
            }
            case "E" -> {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate from = LocalDate.parse(s[3].substring(5).trim(), format);
                LocalDate to = LocalDate.parse(s[4].substring(3).trim(), format);
                task = new Event(desc, from, to);
            }
            case "T" -> task = new Todo(desc);
            default -> throw new JellyException("Error Occurred!");
        }

        if (isMark) {
            task.markDone();
        }
        return task;
    }
}
