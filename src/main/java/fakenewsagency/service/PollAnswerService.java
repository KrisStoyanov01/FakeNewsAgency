package fakenewsagency.service;


import fakenewsagency.domain.models.binding.PollAnswerBindingModel;
import fakenewsagency.domain.models.service.PollAnswerServiceModel;

import java.util.List;

public interface PollAnswerService {
    PollAnswerServiceModel addPollAnswer(PollAnswerServiceModel pollAnswerServiceModel);

    PollAnswerServiceModel editPollAnswer(String id, PollAnswerServiceModel pollAnswerServiceModel);

    List<PollAnswerServiceModel> findAllPollAnswers();

    PollAnswerServiceModel findPollAnswersById(String id);

    PollAnswerBindingModel extractPollAnswerByIdForEditOrDelete(String id);

    boolean deletePollAnswer(String id);
}
