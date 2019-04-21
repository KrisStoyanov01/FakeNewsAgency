package fakenewsagency.service;

import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.models.service.CommentServiceModel;
import fakenewsagency.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentServiceModel addComment(CommentServiceModel commentServiceModel) {
        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);
        try{
            this.commentRepository.saveAndFlush(comment);
            return this.modelMapper.map(comment, CommentServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CommentServiceModel editComment(String id, CommentServiceModel commentServiceModel) {
        return null;
    }

    @Override
    public List<CommentServiceModel> findAllComments() {
        return null;
    }

    @Override
    public CommentServiceModel findCommentById(String id) {
        return null;
    }

    @Override
    public CommentServiceModel extractCommentByIdForEditOrDelete(String id) {
        return null;
    }

    @Override
    public boolean deleteComment(String id) {
        return false;
    }
}
