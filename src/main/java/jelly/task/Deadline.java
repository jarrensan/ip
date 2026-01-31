package jelly.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toSave() {
        return "D | " + super.toSave() + " | " + "by: " + by.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")) + ")";
    }
}
