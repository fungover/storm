package org.fungover.storm.httphandler;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    void resettingResponseShouldSetHeadersToNull(){
        Map<String,String> map = new HashMap<>();
        map.put("1","3");
        response.setHeaders(map);
        response.reset();
        var result = response.getHeaders();
        assertThat(result).isEqualTo(Map.of());
    }

    @Test
    void setStatusCodeInOtherConstructorShouldBeEqualTo404(){
        Map<String,String> map = new HashMap<>();
        Response response1 = new Response(map,404,new byte[100]);
        assertThat(response1.getStatusCode()).isEqualTo(404);
    }

}
