package org.fungover.storm.httphandler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ResponseTest {
    Response response = new Response();

    @Test
    void setStatusCodeShouldBeEqualToGetStatusCode(){
        response.setStatusCode(404);
        var result = response.getStatusCode();
        assertThat(result).isEqualTo(404);
    }
    @Test
    void setBodyShouldBeEqualToGetBody(){
        response.setBody(new byte[10]);
        var result = response.getBody().length;
        assertThat(result).isEqualTo(10);
    }



}
