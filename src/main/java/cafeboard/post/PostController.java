package cafeboard.post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public PostResponse save(@Valid @RequestBody PostRequest postRequest){
        return postService.save(postRequest);
    }

    @GetMapping("/posts")
    public List<PostCommentResponse> findByBoardId(@RequestParam(required = false) Long boardId){
        return postService.findAllByBoardId(boardId);
    }

    @GetMapping("/posts/{postId}")
    public PostCommentDetailResponse findByPostId(@PathVariable Long postId){
        return postService.findByPostId(postId);
    }

    @PutMapping("/posts/{postId}")
    public PostResponse putPost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest){
        return postService.update(postId,postRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public void deleteById(@PathVariable Long postId){
        postService.deleteById(postId);
    }
}
