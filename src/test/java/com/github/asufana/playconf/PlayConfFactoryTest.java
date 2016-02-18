package com.github.asufana.playconf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.github.asufana.playconf.collections.*;
import com.github.asufana.playconf.vo.*;

public class PlayConfFactoryTest {
    
    //application.confサンプル
    private static final List<String> play1ConfSample = Arrays.asList("# This is the main configuration file for the application.",
                                                                      "# ~~~~~",
                                                                      "application.name=SampleProject",
                                                                      "",
                                                                      "# Application mode",
                                                                      "# ~~~~~",
                                                                      "# Set to dev to enable instant reloading and other development help.",
                                                                      "# Otherwise set to prod.",
                                                                      "      application.mode=dev",
                                                                      "%prod.application.mode=prod");
    
    @Test
    //不要行除去
    public void testFilterIgnoreLine() {
        final List<String> filtered = PlayConfFactory.filterIgnoreLine(play1ConfSample);
        //不要行が除去されていること
        assertThat(filtered, is(Arrays.asList("application.name=SampleProject",
                                              "application.mode=dev",
                                              "%prod.application.mode=prod")));
    }
    
    @Test
    @SuppressWarnings("serial")
    //PlayFrameworkIdでグルーピング
    public void testGroupByFrameworkId() throws Exception {
        final List<String> filtered = PlayConfFactory.filterIgnoreLine(play1ConfSample);
        final Map<FrameworkId, ConfigList> grouped = PlayConfFactory.groupByFrameworkId(filtered);
        //グルーピングされていること
        assertThat(grouped, is(new HashMap<FrameworkId, ConfigList>() {
            {
                put(new FrameworkId("%prod"),
                    new ConfigList(Arrays.asList(new Config("application.mode", "prod"))));
            }
            {
                put(new FrameworkId("%default"),
                    new ConfigList(Arrays.asList(new Config("application.name", "SampleProject"),
                                                 new Config("application.mode", "dev"))));
            }
        }));
        
    }
}
