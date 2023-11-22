package com.moralok.bean;

public class ProxyCircularA {

    private ProxyCircularB proxyCircularB;

    public ProxyCircularB getProxyCircularB() {
        return proxyCircularB;
    }

    public void setProxyCircularB(ProxyCircularB proxyCircularB) {
        this.proxyCircularB = proxyCircularB;
    }

    public void sayHello() {
        System.out.println("hello");
    }
}
