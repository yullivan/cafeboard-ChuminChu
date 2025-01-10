package cafeboard;

import cafeboard.board.BoardRequest;
import cafeboard.post.PostRequest;
import cafeboard.post.PostResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 게시글생성테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", (long) 1))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200);
    }
    @Test
    void 게시글전체조회테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", (long) 1))
                .when()
                .get("/posts")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 특정게시글조회테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", (long) 1))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .pathParam("postId", "1")
                .when()
                .get("/posts/{postId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시글수정() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", (long) 1))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("아효 힘들어", "제목이다",(long) 1))
                .pathParam("postId", "2")
                .when()
                .put("/posts/{postId}")
                .then().log().all()
                .statusCode(200);

    }

    @Test
    void 게시글삭제테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequest("안녕하세요", "인사", (long) 1))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("postId", "1")
                .when()
                .delete("/posts/{postId}")
                .then().log().all()
                .statusCode(200);
    }
}
