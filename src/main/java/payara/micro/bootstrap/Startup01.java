package payara.micro.bootstrap;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Startup01 {

    public static void main(String[] args) throws BootstrapException {

        PayaraMicroRuntime instance = PayaraMicro.getInstance()
                .setHttpPort(8082)
                //.setDeploymentDir(new File("../../../../target"))
                //.setHttpAutoBind(true)
                //.setPrintLogo(true)
                .setInstanceName("Payara Server Spk")
                .bootStrap();

        Logger.getLogger(Startup01.class.getName()).log(Level.INFO, "Servidor Payara Instância - " + instance.getInstanceName());

    }

    public static void pararServidor() throws BootstrapException {
        PayaraMicro micro = PayaraMicro.getInstance();
        micro.shutdown();
    }

}
