package cafeboard.comment;

import cafeboard.post.Post;
import cafeboard.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentResponse save(CommentRequest commentRequest) {
        Post postId = postRepository.findById(commentRequest.postId())
                .orElseThrow(() -> new NoSuchElementException("해당 게시글은 존재하지 않습니다."));
        Comment comment = commentRepository.save(new Comment(
                commentRequest.content(),
                commentRequest.name(),
                postId));

        return new CommentResponse(comment.getContent(), comment.getName(), comment.getId());

    }

    @Transactional
    public CommentResponse update(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("찾는 댓글이 없습니다."));
        comment.setContent(commentRequest.content());
        return new CommentResponse(comment.getContent(), comment.getName(), commentId);
    }

    public CommentResponse deleteById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("찾는 댓글이 없습니다."));
        commentRepository.delete(comment);
        return new CommentResponse(comment.getContent(), comment.getName(), commentId);
    }
}
