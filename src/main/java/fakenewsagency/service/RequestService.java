package fakenewsagency.service;

import fakenewsagency.domain.models.binding.CommentBindingModel;
import fakenewsagency.domain.models.binding.RequestBindingModel;
import fakenewsagency.domain.models.service.CommentServiceModel;
import fakenewsagency.domain.models.service.RequestServiceModel;
import fakenewsagency.domain.models.service.UserServiceModel;

import java.util.List;

public interface RequestService {
    RequestServiceModel addRequest(RequestServiceModel requestServiceModel);

    List<RequestServiceModel> findAllRequests();

    List<RequestServiceModel> findAllRequestByUserId(String userId);

    RequestServiceModel findRequestById(String id);

    RequestBindingModel extractRequestByIdForEditOrDelete(String id);

    boolean deleteRequest(String id);
}
