package fakenewsagency.service;

import fakenewsagency.domain.models.binding.PollBindingModel;
import fakenewsagency.domain.models.service.PollServiceModel;

import java.util.List;

public interface PollService {
    PollServiceModel addPoll(PollServiceModel pollServiceModel);

    PollServiceModel editPoll(String id, PollServiceModel pollServiceModel);

    List<PollServiceModel> findAllPolls();

    PollServiceModel findPollById(String id);

    PollBindingModel extractPollByIdForEditOrDelete(String id);

    boolean deletePoll(String id);
}
