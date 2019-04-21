package fakenewsagency.service;

import fakenewsagency.domain.entites.PollAnswer;
import fakenewsagency.domain.models.binding.PollAnswerBindingModel;
import fakenewsagency.domain.models.service.PollAnswerServiceModel;
import fakenewsagency.domain.models.service.PollServiceModel;
import fakenewsagency.error.PollAnswerNotFoundException;
import fakenewsagency.repository.PollAnswerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollAnswerServiceImpl implements PollAnswerService {
    private final ModelMapper modelMapper;
    private final PollAnswerRepository pollAnswerRepository;

    @Autowired
    public PollAnswerServiceImpl(ModelMapper modelMapper, PollAnswerRepository pollAnswerRepository) {
        this.modelMapper = modelMapper;
        this.pollAnswerRepository = pollAnswerRepository;
    }

    @Override
    public PollAnswerServiceModel addPollAnswer(PollAnswerServiceModel pollAnswerServiceModel) {
        PollAnswer pollAnswer = this.modelMapper.map(pollAnswerServiceModel, PollAnswer.class);
        try{
            this.pollAnswerRepository.saveAndFlush(pollAnswer);
            return this.modelMapper.map(pollAnswer, PollAnswerServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PollAnswerServiceModel editPollAnswer(String id, PollAnswerServiceModel pollAnswerServiceModel) {
        PollAnswer pollAnswer = this.pollAnswerRepository.findById(id)
                .orElseThrow(() -> new PollAnswerNotFoundException("PollAnswer with the given id was not found!"));
        //todo
        return this.modelMapper.map(this.pollAnswerRepository.saveAndFlush(pollAnswer), PollAnswerServiceModel.class);
    }

    @Override
    public List<PollAnswerServiceModel> findAllPollAnswers() {
        return this.pollAnswerRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PollAnswerServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PollAnswerServiceModel findPollAnswersById(String id) {
        return this.pollAnswerRepository.findById(id)
                .map(p -> {
                    PollAnswerServiceModel pollAnswerServiceModel = this.modelMapper.map(p, PollAnswerServiceModel.class);
                    this.pollAnswerRepository.findById(pollAnswerServiceModel.getId());

                    return pollAnswerServiceModel;
                })
                .orElseThrow(() -> new PollAnswerNotFoundException("Poll Answer with the given id was not found!"));
    }

    @Override
    public PollAnswerBindingModel extractPollAnswerByIdForEditOrDelete(String id) {
        PollAnswer pollAnswer = this.pollAnswerRepository.findById(id).orElse(null);

        if (pollAnswer == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        PollAnswerBindingModel pollAnswerBindingModel = this.modelMapper.map(pollAnswer, PollAnswerBindingModel.class);
        return pollAnswerBindingModel;
    }

    @Override
    public boolean deletePollAnswer(String id) {
        try{
            this.pollAnswerRepository.deleteById(id);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
