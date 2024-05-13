package Models.Tasks;

import Models.Tasks.Contracts.Bug;
import Models.Tasks.Contracts.Comment;
import Models.Tasks.enums.Priority;
import Models.Tasks.enums.Severity;
import Models.Tasks.enums.StatusBug;
import Models.contracts.Member;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Models.Tasks.Contracts.Bug {
    public static final String BUG_CONSTRUCTOR_LOG = "Bug %s created";

    //Constants

    //Attrbitutes
    private final List<String> stepsToReproduce;
    private Priority priority;
    private Severity severity;
    private StatusBug status;
    private Member assignee;
    private boolean initializing = true;

    //TODO /*Need to add the member class for assignee:*/
    //Constructor

    public BugImpl(int id, String title, String description, Priority priority, Severity severity, Member assignee) {

        super(id, title, description);
        this.priority = priority;
        this.severity = severity;
        this.status = StatusBug.ACTIVE;
        stepsToReproduce = new ArrayList<>();
        initializing = false;
        addLog(BUG_CONSTRUCTOR_LOG.formatted(title));

    }

    //Methods


    @Override
    public Priority getPriority() {
        return priority;
    }


    public void setPriority(Priority priority) {
        if(!initializing){
            addLog("Priority changed from %s to %s".formatted(this.priority,priority));

        }
        this.priority = priority;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        if(!initializing){
            addLog("Severity changed from %s to %s".formatted(this.severity,severity));

        }
        this.severity = severity;
    }

    @Override
    public StatusBug getStatus() {
        return status;
    }

    public void setStatus(StatusBug status) {
        if(!initializing){
            addLog("Status changed from %s to %s".formatted(this.status,status));

        }
        this.status = status;
    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
    }


    @Override
    public void addStepToReproduce(String step) {
        addLog("""
                New step to reproduce added
                Step: %s
                """.formatted(step));
        stepsToReproduce.add(step);
    }


    @Override
    public void removeStepToReproduce(String step) {
        addLog("""
                New step to reproduce removed
                Step: %s
                """.formatted(step));
        stepsToReproduce.remove(step);
    }


    @Override
    public void clearStepsToReproduce() {
        addLog("""
                Steps to reduce cleared
                """);
        stepsToReproduce.clear();
    }

    @Override
    public Member getAssignee() {
        return assignee;
    }

    /*public void setAssignee(Member assignee) {
        for (Member member : )
        this.assignee = assignee;
    }*/
}
