package fakenewsagency.service;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.models.binding.CommentBindingModel;
import fakenewsagency.domain.models.service.CommentServiceModel;
import fakenewsagency.error.CommentNotFoundException;
import fakenewsagency.repository.ArticleRepository;
import fakenewsagency.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
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
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with the given id was not found!"));
        comment.setContent(commentServiceModel.getContent());
        comment.setCreatedOn(commentServiceModel.getCreatedOn());
        return this.modelMapper.map(this.commentRepository.saveAndFlush(comment), CommentServiceModel.class);
    }

    @Override
    public List<CommentServiceModel> findAllCommentsByArticleId(String articleId) {
        Optional<Article> article = this.articleRepository.findById(articleId);
        return this.commentRepository.findCommentsByArticleOwner(article)
                .stream()
                .map(c -> this.modelMapper.map(c, CommentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentServiceModel findCommentById(String id) {
        return this.commentRepository.findById(id)
                .map(p -> {
                    CommentServiceModel commentServiceModel = this.modelMapper.map(p, CommentServiceModel.class);
                    this.commentRepository.findById(commentServiceModel.getId());

                    return commentServiceModel;
                })
                .orElseThrow(() -> new CommentNotFoundException("Comment with the given id was not found!"));
    }

    @Override
    public CommentBindingModel extractCommentByIdForEditOrDelete(String id) {
        Comment comment = this.commentRepository.findById(id).orElse(null);

        if (comment == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        CommentBindingModel commentBindingModel = this.modelMapper.map(comment, CommentBindingModel.class);
        return commentBindingModel;
    }

    @Override
    public boolean deleteComment(String id) {
        try{
            this.commentRepository.deleteById(id);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
