package fakenewsagency.service;

import fakenewsagency.domain.models.binding.CommentBindingModel;
import fakenewsagency.domain.models.service.CommentServiceModel;

import java.util.List;

public interface CommentService {
    CommentServiceModel addComment(CommentServiceModel commentServiceModel);

    CommentServiceModel editComment(String id, CommentServiceModel commentServiceModel);

    List<CommentServiceModel> findAllCommentsByArticleId(String articleId);

    List<CommentServiceModel> findAllCommentsByUserId(String userId);

    CommentServiceModel findCommentById(String id);

    CommentBindingModel extractCommentByIdForEditOrDelete(String id);

    boolean deleteComment(String id);
}
