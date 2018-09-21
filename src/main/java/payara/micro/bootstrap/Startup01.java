package payara.micro.bootstrap;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;

public class Startup01 {

    public static void main(String[] args) throws BootstrapException {
        PayaraMicro.getInstance().setHttpPort(8080).bootStrap();
    }

}
