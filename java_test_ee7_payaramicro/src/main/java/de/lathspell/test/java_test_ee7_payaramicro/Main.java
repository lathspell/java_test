package de.lathspell.test.java_test_ee7_payaramicro;

import java.io.File;

import fish.payara.micro.PayaraMicro;

public class Main {

    public static void main(String[] args) throws Exception {

        PayaraMicro.getInstance()
                .setNoCluster(true)
                .setDeploymentDir(new File("target/"))
                //   .addDeployment("test.war")
                .bootStrap();
    }
}
