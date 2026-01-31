package jelly.task;

public class TodoTest extends TaskTest {
    public TodoTest(String description) {
        super(description);
    }

    @Override
    public String toSave() {
        return "T | " + super.toSave();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }



}

