package fakenewsagency.service;

import fakenewsagency.domain.entites.Poll;
import fakenewsagency.domain.models.binding.PollBindingModel;
import fakenewsagency.domain.models.service.PollServiceModel;
import fakenewsagency.error.PollNotFoundException;
import fakenewsagency.repository.PollRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {
    private final ModelMapper modelMapper;
    private final PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(ModelMapper modelMapper, PollRepository pollRepository) {
        this.modelMapper = modelMapper;
        this.pollRepository = pollRepository;
    }

    @Override
    public PollServiceModel addPoll(PollServiceModel pollServiceModel) {
        Poll poll = this.modelMapper.map(pollServiceModel, Poll.class);
        try{
            this.pollRepository.saveAndFlush(poll);
            return this.modelMapper.map(poll, PollServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PollServiceModel editPoll(String id, PollServiceModel pollServiceModel) {
        Poll poll = this.pollRepository.findById(id)
                .orElseThrow(() -> new PollNotFoundException("Poll with the given id was not found!"));
       //todo
        return this.modelMapper.map(this.pollRepository.saveAndFlush(poll), PollServiceModel.class);
    }

    @Override
    public List<PollServiceModel> findAllPolls() {
        return this.pollRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PollServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PollServiceModel findPollById(String id) {
        return this.pollRepository.findById(id)
                .map(p -> {
                    PollServiceModel pollServiceModel = this.modelMapper.map(p, PollServiceModel.class);
                    this.pollRepository.findById(pollServiceModel.getId());

                    return pollServiceModel;
                })
                .orElseThrow(() -> new PollNotFoundException("Poll with the given id was not found!"));
    }

    @Override
    public PollBindingModel extractPollByIdForEditOrDelete(String id) {
        Poll poll = this.pollRepository.findById(id).orElse(null);

        if (poll == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        PollBindingModel pollBindingModel = this.modelMapper.map(poll, PollBindingModel.class);
        return pollBindingModel;
    }

    @Override
    public boolean deletePoll(String id) {
        try{
            this.pollRepository.deleteById(id);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
