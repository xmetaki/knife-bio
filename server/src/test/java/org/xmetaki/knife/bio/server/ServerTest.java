package org.xmetaki.knife.bio.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void configFileParse() {
        BaseInfo baseInfo = Server.configFileParse("/config.server.yaml");
        assertEquals("token equal", baseInfo.token, "9999");
    }
}