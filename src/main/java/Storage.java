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
            sb.append(i + 1).append(". ").append(t.toSave()).append("\n");
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
        }

        return taskList;
    }

    public Task getTask(String line) {

    }
}
