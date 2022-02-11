package org.fungover.storm.httphandler;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


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
        request.putHeaders("1", "2");
        request.clearHeaders();
        var result = request.getParameters();
        assertThat(result).isEmpty();
    }

    @Test
    void clearParametersShouldSetIsEmptyToTrue() {
        request.putParameters("1", "2");
        request.clearParameters();
        var result = request.getHeaders();
        assertThat(result).isEmpty();
    }


}
