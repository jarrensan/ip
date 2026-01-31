package jelly;

import jelly.exception.CreateFileException;
import jelly.exception.JellyException;
import jelly.exception.LoadFileException;
import jelly.exception.WriteFileException;
import jelly.task.Deadline;
import jelly.task.Event;
import jelly.task.Task;
import jelly.task.Todo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private File jellyFile;
    String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void create() throws CreateFileException {
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
            throw new CreateFileException();
        }
    }

    public void write(ArrayList<Task> taskList) throws WriteFileException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            sb.append(t.toSave()).append("\n");
        }

        try {
            FileWriter fw = new FileWriter(jellyFile);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            throw new WriteFileException();
        }
    }

    public ArrayList<Task> load() throws JellyException {
        try {
            create();
            BufferedReader br = new BufferedReader(new FileReader(jellyFile));
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                Task task = getTask(line);
                tasks.add(task);
            }
            return tasks;
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
        if (type.equals("D")) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            LocalDate by = LocalDate.parse(s[3].substring(3).trim(), format);
            task = new Deadline(desc, by);
        } else if (type.equals("E")) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            LocalDate from = LocalDate.parse(s[3].substring(5).trim(), format);
            LocalDate to = LocalDate.parse(s[4].substring(3).trim(), format);
            task = new Event(desc, from, to);
        } else if (type.equals("T")) {
            task = new Todo(desc);
        } else {
            throw new JellyException("Error Occurred!");
        }

        if (isMark) {
            task.markDone();
        }
        return task;
    }
}
