package cafeboard.board;

import cafeboard.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardTest extends AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 게시판생성테스트() {
        BoardResponse 자유게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        assertThat(자유게시판.boardName()).isNotNull();
    }


    @Test
    void 게시판생성_제한테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("adlkfaksldnkfnasdnfalskdn"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 게시판생성_중복테스트() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("게시판1"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    void 게시판조회테스트() {
        RestAssured
                .given().log().all()
                .when()
                .get("/boards")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시판수정테스트() {
        BoardResponse 게시판 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("지유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(BoardResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .pathParam("boardId", 게시판.id())
                .when()
                .put("/boards/{boardId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 게시판삭제테스트() {
        BoardResponse 공지사항 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new BoardRequest("자유게시판"))
                .when()
                .post("/boards")
                .then().log().all()
                .statusCode(200)
                .extract().as(BoardResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("boardId", 공지사항.id())
                .when()
                .delete("/boards/{boardId}")
                .then().log().all()
                .statusCode(200);
    }
}
