package com.github.filonovsv.artix_test_tusk;

import com.github.filonovsv.artix_test_tusk.dto.Operation;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtixTestTuskApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnBalanceWhenBonusCardIsExist() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/balance/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        Number amount = documentContext.read("$.amount");
        assertThat(amount).isEqualTo(123);
    }

    @Test
    void shouldNotReturnABonusCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/balance/-1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    @DirtiesContext
    void shouldDoOperation() {
        ResponseEntity<String> responseChangeBalance = restTemplate
                .postForEntity("/change_balance", new Operation(null, 1L, 1L), String.class);
        assertThat(responseChangeBalance.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(responseChangeBalance.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        Number amount = documentContext.read("$.amount");
        assertThat(amount).isEqualTo(124);

        ResponseEntity<String> responseGetHistory = restTemplate
                .getForEntity("/history/1", String.class);
        assertThat(responseGetHistory.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContextHistory = JsonPath.parse(responseGetHistory.getBody());
        JSONArray page = documentContextHistory.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }

    @Test
    @DirtiesContext
    void shouldCancelOperation() {

        ResponseEntity<Void> responseChangeBalance = restTemplate
                .postForEntity("/change_balance", new Operation(null, 1L, 1L), Void.class);
        assertThat(responseChangeBalance.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Void> responseCancel = restTemplate
                .postForEntity("/cancel_operation/1", null, Void.class);
        assertThat(responseChangeBalance.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> responseGetHistory = restTemplate
                .getForEntity("/history/1", String.class);
        assertThat(responseGetHistory.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContextHistory = JsonPath.parse(responseGetHistory.getBody());
        JSONArray page = documentContextHistory.read("$[*]");
        assertThat(page.size()).isEqualTo(0);

        ResponseEntity<String> responseGetBalance = restTemplate
                .getForEntity("/balance/1", String.class);
        assertThat(responseGetBalance.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContextGetBalance = JsonPath.parse(responseGetBalance.getBody());
        Number id = documentContextGetBalance.read("$.id");
        assertThat(id).isEqualTo(1);

        Number amount = documentContextGetBalance.read("$.amount");
        assertThat(amount).isEqualTo(123);
    }
}
