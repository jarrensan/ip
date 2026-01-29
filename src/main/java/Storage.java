import exception.CreateFileException;
import exception.JellyException;
import exception.WriteFileException;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private File jellyFile;

    public void createFile(String path) throws CreateFileException {
        try {
            this.jellyFile = new File(path);
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

    public void writeFile(ArrayList<Task> taskList) throws WriteFileException {
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

    public ArrayList<Task> loadTasks() throws JellyException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(jellyFile));
        ArrayList<Task> taskList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            Task task = getTask(line);
            taskList.add(task);
        }

        return taskList;
    }

    public Task getTask(String line) throws JellyException{
        Task task;
        String[] s = line.split(" \\| ");
        String type = s[0];
        boolean isMark = s[1].equals("1");
        String desc = s[2];
        if (type.equals("D")) {
            String by = s[3].substring(3);
            task = new Deadline(desc, by);
        } else if (type.equals("E")) {
            String from = s[3].substring(5).trim();
            String to = s[4].substring(3);
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
