package fakenewsagency.service;

import fakenewsagency.domain.models.binding.GroupBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.domain.models.service.GroupServiceModel;

import java.util.List;

public interface GroupService {
    GroupServiceModel addGroup(GroupServiceModel groupServiceModel);

    GroupServiceModel findGroupById(String id);

    List<GroupServiceModel> findAllGroups();
}
