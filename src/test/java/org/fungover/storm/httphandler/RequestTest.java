package org.fungover.storm.httphandler;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestTest {
    Request request = new Request();

    @Test
    void clearingUrlShouldSetIsEmptyToTrue() {
        request.setUrl("Test");
        request.clearUrl();
        var result = request.getUrl().isEmpty();
        assertThat(result).isTrue();
    }


    @Test
    void clearingBodyShouldReturnZero() {
        request.setBody(new byte[10]);
        request.clearBody();
        var result = request.getBody().length;
        assertThat(result).isEqualTo(0);
    }

    @Test
    void clearHeadersShouldSetIsEmptyToTrue() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        request.setHeaders(map);
        request.clearHeaders();
        var result = map.isEmpty();
        assertThat(result).isTrue();
    }




}
