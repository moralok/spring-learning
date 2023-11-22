package com.moralok.bean;

public class ProxyCircularB {

    private ProxyCircularA proxyCircularA;

    public ProxyCircularA getProxyCircularA() {
        return proxyCircularA;
    }

    public void setProxyCircularA(ProxyCircularA proxyCircularA) {
        this.proxyCircularA = proxyCircularA;
    }
}
