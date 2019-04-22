package fakenewsagency.service;

import fakenewsagency.domain.entites.Request;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.RequestBindingModel;
import fakenewsagency.domain.models.service.RequestServiceModel;
import fakenewsagency.error.RequestNotFoundException;
import fakenewsagency.repository.RequestRepository;
import fakenewsagency.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RequestRepository requestRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public RequestServiceModel addRequest(RequestServiceModel requestServiceModel) {
        Request request = this.modelMapper.map(requestServiceModel, Request.class);
        try{
            this.requestRepository.saveAndFlush(request);
            return this.modelMapper.map(request, RequestServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RequestServiceModel> findAllRequests() {
        return this.requestRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RequestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestServiceModel> findAllRequestByUserId(String userId) {
        Optional<User> user = this.userRepository.findById(userId);
        return this.requestRepository.findRequestsByAuthor(user)
                .stream()
                .map(r -> this.modelMapper.map(r, RequestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RequestServiceModel findRequestById(String id) {
        return this.requestRepository.findById(id)
                .map(r -> {
                    RequestServiceModel requestServiceModel = this.modelMapper.map(r, RequestServiceModel.class);
                    this.requestRepository.findById(requestServiceModel.getId());

                    return requestServiceModel;
                })
                .orElseThrow(() -> new RequestNotFoundException("Request with the given id was not found!"));
    }

    @Override
    public RequestBindingModel extractRequestByIdForEditOrDelete(String id) {
        Request request = this.requestRepository.findById(id).orElse(null);

        if (request == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        RequestBindingModel requestBindingModel = this.modelMapper.map(request, RequestBindingModel.class);
        return requestBindingModel;
    }

    @Override
    public boolean deleteRequest(String id) {
        try{
            this.requestRepository.deleteById(id);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
