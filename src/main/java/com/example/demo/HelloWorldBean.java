package com.example.demo;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

@Named("helloWorldBean") // Nome usado no XHTML
@RequestScoped
public class HelloWorldBean {

    private String sayHello = "Olá, Jakarta Faces no Tomcat 11 :=0 !";

    // O JSF usa o getter para acessar a propriedade
    public String getSayHello() {
        return sayHello;
    }

    public void setSayHello(String sayHello) {
        this.sayHello = sayHello;
    }
}
