package commands;

import commands.BaseCommand;
import core.contracts.TaskRepository;
import models.tasks.Contracts.Feedback;
import models.tasks.enums.StatusFeedback;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListFeedbackCommand extends BaseCommand {
    public static final String INVALID_SORTING_CRITERIA = "Invalid sorting criteria";
    public ListFeedbackCommand(TaskRepository repository){
        super(repository);
    }
    @Override
    protected String executeCommand(List<String> parameters) {
        List<Feedback> feedbacks = getRepository().getFeedbacks();
        StatusFeedback statusFeedback = null;
        String sortCriteria = null;
        for (String param : parameters){
            if (isStatus(param)){
                statusFeedback = StatusFeedback.valueOf(param.toUpperCase());
            }
            else if (isSortCriteria(param)){
                sortCriteria = param.toLowerCase();
            }
        }
        if (statusFeedback != null){
            StatusFeedback result = statusFeedback;
            feedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getStatus() == result)
                    .collect(Collectors.toList());
        }
        if (sortCriteria != null){
            switch (sortCriteria){
                case "title":
                    feedbacks.sort(Comparator.comparing(Feedback::getTitle));
                    break;
                case "rating":
                    feedbacks.sort(Comparator.comparing(Feedback::getRating));
                    break;
                default:
                    throw new IllegalArgumentException(INVALID_SORTING_CRITERIA);
            }
        }
        return formatFeedbacks(feedbacks);
    }

    private boolean isStatus(String param){
        try {
            StatusFeedback.valueOf(param.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    private boolean isSortCriteria(String param){
        if (param == null){
            return false;
        }
        return param.equalsIgnoreCase("title") || param.equalsIgnoreCase("rating");
    }

    private String formatFeedbacks(List<Feedback> feedbacks){
        StringBuilder result = new StringBuilder();
        for (Feedback feedback : feedbacks){
            result.append("Feedback ID: ").append(feedback.getId())
                    .append(", Title: ").append(feedback.getTitle())
                    .append(", Status: ").append(feedback.getStatus())
                    .append("\n");
        }
        return result.toString();
    }
}