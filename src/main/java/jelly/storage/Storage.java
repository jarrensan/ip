package jelly.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import jelly.exception.CreateFileException;
import jelly.exception.InvalidFileCommandException;
import jelly.exception.JellyException;
import jelly.exception.LoadFileException;
import jelly.exception.WriteFileException;
import jelly.task.Deadline;
import jelly.task.Event;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.task.Todo;

public class Storage {
    private File jellyFile;

    /**
     * Adds new file in the file path specified in the directory if it does not exist, else it does nothing.
     *
     * @param filePath File Path of the specified data file.
     * @throws CreateFileException If unable to create file in the specified path provided.
     */
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

    /**
     * Adds new tasks in taskList into data file.
     *
     * @param taskList TaskList of all existing tasks.
     * @throws WriteFileException If unable to write into file.
     */
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

    /**
     * Returns tasklist based on the raw file data of tasks in the data file.
     *
     * @return TaskList generated from the data file.
     * @throws JellyException If line format is invalid, or file is corrupted or missing.
     */
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

    /**
     * Returns subclass of Task based on the raw string line specified in the data file.
     *
     * @param line The raw string line read from the data file.
     * @return Task with saved state.
     * @throws JellyException If line format is invalid, task type is unknown, or dates are not properly formatted.
     */
    public Task getTask(String line) throws JellyException {
        Task task;
        String[] s = line.split(" \\| ");
        if (s.length < 3) {
            throw new InvalidFileCommandException("Corrupted line: " + line);
        }
        String type = s[0];
        boolean isMark = s[1].equals("1");
        String desc = s[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        try {
            switch (type) {
            case "D":
                LocalDate by = LocalDate.parse(s[3].substring(3).trim(), formatter);
                task = new Deadline(desc, by);
                break;
            case "E":
                LocalDate from = LocalDate.parse(s[3].substring(5).trim(), formatter);
                LocalDate to = LocalDate.parse(s[4].substring(3).trim(), formatter);
                task = new Event(desc, from, to);
                break;
            case "T":
                task = new Todo(desc);
                break;
            default:
                throw new InvalidFileCommandException("Unknown task type: " + type);
            }
        } catch (JellyException e) {
            throw new InvalidFileCommandException("Error parsing task: " + line);
        }
        if (isMark) {
            task.markDone();
        }
        return task;
    }
}
