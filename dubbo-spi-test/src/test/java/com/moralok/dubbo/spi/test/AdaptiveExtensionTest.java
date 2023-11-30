package com.moralok.dubbo.spi.test;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.junit.jupiter.api.Test;

public class AdaptiveExtensionTest {

    @Test
    void protocol() {
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = extensionLoader.getAdaptiveExtension();
    }

    @Test
    void loadBalance() {
        ExtensionLoader<LoadBalance> extensionLoader = ExtensionLoader.getExtensionLoader(LoadBalance.class);
        LoadBalance loadBalance = extensionLoader.getAdaptiveExtension();
    }

    @Test
    void camelToSplitName() {
        String ret = StringUtils.camelToSplitName("LoadBalance", ".");
        System.out.println(ret);
        ret = StringUtils.camelToSplitName("loadBalance", ".");
        System.out.println(ret);
    }
}
