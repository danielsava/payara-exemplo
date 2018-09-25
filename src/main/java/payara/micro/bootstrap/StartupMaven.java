package payara.micro.bootstrap;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;

/**
 *
 *  https://docs.payara.fish/documentation/payara-micro/deploying/deploy-program-maven.html
 *
 *
 */

public class StartupMaven {


    public static void main(String[] args) throws BootstrapException {

        PayaraMicro.getInstance().addDeployFromGAV("br.com.savasoft.exemplos,payara-micro,1.0-SNAPSHOT").bootStrap();

    }




}
