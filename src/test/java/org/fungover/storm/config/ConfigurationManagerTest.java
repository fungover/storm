package org.fungover.storm.config;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationManagerTest {

    @Test
    void readConfigShouldConvertJsonFileToConfigurationValues(){
        ConfigurationManager.readConfigurationFile("src/test/java/org/fungover/storm/config/config.json");
        Configuration conf = ConfigurationManager.getCurrentConfiguration();

        var portResult = conf.getPort();
        var webrootResult = conf.getWebroot();

        assertThat(portResult).isEqualTo(8080);
        assertThat(webrootResult).isEqualTo("/Test");
    }

    @Test
    void getCurrentConfigurationShouldShowMessageWhenExceptionEmerged(){
        assertThatThrownBy(() -> ConfigurationManager.getCurrentConfiguration())
                .hasMessage("No Current Configuration Set.");
    }
}
