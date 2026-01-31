package jelly.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTest extends TaskTest {
    private LocalDate from, to;

    public EventTest(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSave() {
        return "E | " + super.toSave()
                + " | from: " + from.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"))
                + " | to: " + to.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")) + ")";
    }
}
