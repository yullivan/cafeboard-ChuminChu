package cafeboard.post;

import cafeboard.AcceptanceTest;
import cafeboard.board.BoardRequest;
import cafeboard.board.BoardResponse;
import cafeboard.comment.CommentRequest;
import cafeboard.comment.CommentResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTest extends AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 게시글생성테스트() {
        BoardResponse 게시판1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 게시판1.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        assertThat(게시글.content()).isNotNull();
        assertThat(게시글.title()).isNotNull();
    }

    @Test
    void 게시글전체조회테스트() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        assertThat(게시글).isNotNull();
    }

    @Test
    void 특정게시글조회테스트() {

        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract().as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        CommentResponse 댓글1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        PostCommentDetailResponse 특정게시글조회 = RestAssured
                .given().log().all()
                .pathParam("postId", 게시글.id())
                .when()
                .get("/posts/{postId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostCommentDetailResponse.class);

        assertThat(특정게시글조회).isNotNull();

    }

    @Test
    void 게시글수정() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 수정전게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("인사", "안녕하세요", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        PostResponse 수정후게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("제목이다", "본문이다.", 수정전게시글.id()))
                .pathParam("postId", 수정전게시글.id())
                .when()
                .put("/posts/{postId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        List<PostResponse> 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("." , PostResponse.class);

        assertThat(게시글).allMatch(post -> !post.title().equals(수정전게시글.title()));
        assertThat(게시글).allMatch(post -> post.title().equals(수정후게시글.title()));

    }

    @Test
    void 게시글삭제테스트() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract().as(BoardResponse.class);

        PostResponse 게시글1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        PostResponse 삭제전게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("postId", 삭제전게시글.id())
                .when()
                .delete("/posts/{postId}")
                .then().log().all()
                .statusCode(200);

        List<PostResponse> posts = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", PostResponse.class);

        assertThat(posts).allMatch(post -> !post.id().equals(삭제전게시글));
    }

    @Test
    void 게시글_댓글개수_포함_조회() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        CommentResponse 댓글1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        CommentResponse 댓글2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        List<PostCommentResponse> 댓글개수포함조회 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .queryParam("boardId", 자유게시판.id())
                .when()
                .get("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", PostCommentResponse.class);

        assertThat(댓글개수포함조회.get(0).commentCount()).isEqualTo(2);
    }

    @Test
    void 게시글_댓글내용_포함_조회() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        PostResponse 게시글 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", 자유게시판.id()))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        CommentResponse 댓글1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        CommentResponse 댓글2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CommentRequest("댓글내용", "이름", 게시글.id()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        PostCommentDetailResponse 게시글상세조회 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("postId", 게시글.id())
                .when()
                .get("/posts/{postId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostCommentDetailResponse.class);

        assertThat(게시글상세조회.comments()).isNotNull();
    }
}