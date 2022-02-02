package org.fungover.storm.httphandler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestTest {
    Request request = new Request();
    @Test
    void clearingUrlShouldSetIsEmptyToTrue(){
        request.setUrl("Test");
        request.clearUrl();
        var result = request.getUrl().isEmpty();
        assertThat(result).isTrue();
    }

}
