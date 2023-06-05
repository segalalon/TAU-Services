package com.gs.infra.server;

import org.springframework.beans.factory.InitializingBean;

public class SecurityUtils implements InitializingBean {

    public static void setpassphrase(){
        System.setProperty("com.gs.property-storage.aes.passphrase","0b05308c9a00f07044416bad7a51bacd282fc5c0c999551a4ff15c302b268b204df875993770411044fb35953166ee7833c32ca0741e9fec091dfa10138039e8");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        setpassphrase();
    }
}
