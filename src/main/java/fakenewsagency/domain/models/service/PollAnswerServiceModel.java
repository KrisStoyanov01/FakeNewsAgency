package fakenewsagency.domain.models.service;

import fakenewsagency.domain.entites.Poll;

public class PollAnswerServiceModel {
    private String id;
    private String message;
    private Integer timesChosen;
    private Poll poll;

    public PollAnswerServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTimesChosen() {
        return timesChosen;
    }

    public void setTimesChosen(Integer timesChosen) {
        this.timesChosen = timesChosen;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
