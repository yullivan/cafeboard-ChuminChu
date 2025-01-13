package cafeboard.post;

import cafeboard.board.Board;
import cafeboard.board.BoardRepository;
import cafeboard.comment.CommentResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;


    public PostService(PostRepository postRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
    }

    public PostResponse save(PostRequest postRequest) {
        Board boardId = boardRepository.findById(postRequest.boardId()).orElseThrow(() ->
                new NoSuchElementException("요청하신 게시판이 없습니다." + postRequest.boardId()));
        Post post = postRepository.save(new Post(postRequest.title(), postRequest.content(), boardId));
        return new PostResponse(post.getId(), post.getTitle(), post.getContent());
    }

    public PostCommentDetailResponse findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new NoSuchElementException("찾는 글이 없습니다." + postId));

        return new PostCommentDetailResponse(
                postId,
                post.getTitle(),
                post.getContent(),
                post.getComments()
                        .stream()
                        .map(comment -> new CommentResponse(comment))
                        .toList());
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("찾는 글이 없습니다." + postId));
        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());
        return new PostResponse(postId, post.getTitle(), post.getContent());
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new NoSuchElementException("찾는 글이 없습니다." + postId));
        postRepository.delete(post);
    }

    public List<PostCommentResponse> findByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId)
               .stream()
               .map( post-> new PostCommentResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getComments().size()
                        )).toList();
    }
}
