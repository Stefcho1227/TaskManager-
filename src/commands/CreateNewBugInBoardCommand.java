package commands;

import core.contracts.TaskRepository;
import models.contracts.Board;
import models.contracts.Member;
import models.tasks.Contracts.Bug;
import models.tasks.Contracts.Feedback;
import models.tasks.enums.Priority;
import models.tasks.enums.Severity;
import utilities.ParsingHelpers;
import utilities.ValidationHelper;

import java.util.List;


public class CreateNewBugInBoardCommand extends BaseCommand {

    public static final String BUG_CREATED = "Bug {%s} created in board {%s}";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;
    public static final String PRIORITY_ERR = "Priority {%s} does not exist";
    public static final String SEVERITY_ERR = "Severity {%s} does not exist";

    public CreateNewBugInBoardCommand(TaskRepository repository) {
        super(repository);
    }

    @Override
    protected String executeCommand(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(2), Priority.class, PRIORITY_ERR.formatted(parameters.get(2)));
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(3), Severity.class, SEVERITY_ERR.formatted(parameters.get(3)));
        Member assignee = getRepository().findMemberByName(parameters.get(4));
        String board = parameters.get(5);

        return createBugInBoard(title, description, priority, severity, assignee, board);
    }


    private String createBugInBoard(String title, String description, Priority priority, Severity severity, Member assignee, String boardName) {
        Board board = getRepository().findBoardByName(boardName);
        Bug bug = getRepository().createBug(title, description, priority, severity, assignee);
        AddToBoard(bug, board);
        return String.format(BUG_CREATED, title, boardName);


    }

    private void AddToBoard(Bug bug, Board board) {
        getRepository().addTaskToBoard(bug, board);
    }


}
