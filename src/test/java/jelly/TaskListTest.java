// Used copilot to write TaskListTest.java
package jelly;

import static org.junit.jupiter.api.Assertions.*;

import jelly.task.Deadline;
import jelly.task.Event;
import jelly.task.Task;
import jelly.task.TaskList;
import jelly.task.Todo;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

public class TaskListTest {
    private TaskList taskList;
    private LocalDate testDate1;
    private LocalDate testDate2;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        testDate1 = LocalDate.of(2026, 3, 15);
        testDate2 = LocalDate.of(2026, 4, 20);
    }

    @Test
    public void addTodo_success() {
        Todo todo = taskList.addTodo("Buy groceries");
        
        assertEquals(1, taskList.getSize());
        assertInstanceOf(Todo.class, todo);
        assertEquals("Buy groceries", todo.getDescription());
    }

    @Test
    public void addDeadline_success() {
        Deadline deadline = taskList.addDeadline("Submit assignment", testDate1);
        
        assertEquals(1, taskList.getSize());
        assertInstanceOf(Deadline.class, deadline);
        assertEquals("Submit assignment", deadline.getDescription());
    }

    @Test
    public void addEvent_success() {
        Event event = taskList.addEvent("Project meeting", testDate1, testDate2);
        
        assertEquals(1, taskList.getSize());
        assertInstanceOf(Event.class, event);
        assertEquals("Project meeting", event.getDescription());
    }

    @Test
    public void addTask_multipleTasks_success() {
        taskList.addTodo("Task 1");
        taskList.addDeadline("Task 2", testDate1);
        taskList.addEvent("Task 3", testDate1, testDate2);
        
        assertEquals(3, taskList.getSize());
    }

    @Test
    public void removeTask_success() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        
        Task removedTask = taskList.removeTask(0);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Task 1", removedTask.getDescription());
    }

    @Test
    public void markTask_success() {
        taskList.addTodo("Task 1");
        
        Task markedTask = taskList.markTask(0);
        markedTask.markDone();
        assertEquals("1", markedTask.getSaveStatusIcon());
    }

    @Test
    public void unmarkTask_success() {
        taskList.addTodo("Task 1");
        taskList.markTask(0);
        
        Task unmarkedTask = taskList.unmarkTask(0);
        unmarkedTask.unmarkDone();
        assertEquals("0", unmarkedTask.getSaveStatusIcon());
    }

    @Test
    public void updateTask_success() {
        taskList.addTodo("Original description");
        
        Task updatedTask = taskList.updateTask(0, "New description");
        
        assertEquals("New description", updatedTask.getDescription());
    }

    @Test
    public void isEmpty_emptyTaskList_success() {
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyTaskList_success() {
        taskList.addTodo("Task");
        
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void getSize_multipleTasksAdded_success() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        taskList.addTodo("Task 3");
        
        assertEquals(3, taskList.getSize());
    }

    @Test
    public void findTasks_matchingDescription_success() {
        taskList.addTodo("Read book");
        taskList.addTodo("Buy book");
        taskList.addTodo("Write code");
        
        TaskList foundTasks = taskList.findTasks("book");
        
        assertEquals(2, foundTasks.getSize());
    }

    @Test
    public void findTasks_noMatch_success() {
        taskList.addTodo("Read book");
        taskList.addTodo("Write code");
        
        TaskList foundTasks = taskList.findTasks("xyz");
        
        assertTrue(foundTasks.isEmpty());
    }

    @Test
    public void findTasks_partialMatch_success() {
        taskList.addTodo("Reading book");
        taskList.addTodo("Books collection");
        
        TaskList foundTasks = taskList.findTasks("ook");
        
        assertEquals(2, foundTasks.getSize());
    }

    @Test
    public void toSaveString_multipleTasksAdded_success() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        
        String saveString = taskList.toSaveString();
        
        assertNotNull(saveString);
        assertTrue(saveString.contains("T |"));
    }

    @Test
    public void toString_multipleTasksAdded_success() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        
        String output = taskList.toString();
        
        assertNotNull(output);
        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
    }
}
