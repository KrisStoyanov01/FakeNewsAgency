package fakenewsagency.domain.models.view;

import fakenewsagency.domain.entites.User;

import java.util.Set;

public class GroupDetailsViewModel {
    private String id;
    private String name;
    private String description;
    private Set<User> users;

    public GroupDetailsViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
