package fakenewsagency.domain.models.service;

import fakenewsagency.domain.entites.User;

import java.time.LocalDate;
import java.util.Set;

public class PollServiceModel {
    private String id;
    private User author;
    private String content;
    private LocalDate createdOn;
    private Set<String> possibleAnswers;

    public PollServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Set<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(Set<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
