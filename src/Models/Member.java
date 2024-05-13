package Models;

import Models.Tasks.Contracts.EventLog;
import Models.Tasks.Contracts.Task;
import Models.Tasks.EventLogImpl;
import Models.contracts.Loggable;
import Models.contracts.Taskable;
import utilities.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Member implements Loggable, Taskable {

    //TODO implement logging to all methods that change something.

    public static final String MEMBER_CONSTRUCTOR_LOG = "Member %s created";
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    public static final String NAME_LEN_ERR = "Member name must be between %d and %d";
    public static final String NON_EXISTENT_TASK_ERR = "Non-existent task";

    private String name;
    private final List<Task> tasks;
    private final List<EventLog> activityHistory;

    public Member(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
        addLog(MEMBER_CONSTRUCTOR_LOG.formatted(name));

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationHelper.validateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH,
                NAME_LEN_ERR.formatted(NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        this.name = name;
    }


    public void addTask(Task task) {
        tasks.add(task);
        String activity = "Task %s added to board %s".formatted(task.getTitle(), getName());
        addLog(activity);
    }


    public void removeTask(Task task) {
        if(!tasks.contains(task)){
            throw new IllegalArgumentException(NON_EXISTENT_TASK_ERR);
        }
        tasks.remove(task);
        String activity = "Task %s removed from board %s".formatted(task.getTitle(), getName());
        addLog(activity);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }


    @Override
    public void addLog(String description) {
        activityHistory.add(new EventLogImpl(description));

    }

    @Override
    public List<EventLog> getLogs() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
