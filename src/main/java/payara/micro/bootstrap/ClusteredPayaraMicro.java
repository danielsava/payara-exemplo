package payara.micro.bootstrap;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.data.InstanceDescriptor;

public class ClusteredPayaraMicro // implements PayaraClusterListener
{

    public static void main(String[] args) {

        try {
            PayaraMicroRuntime payaraRuntime = PayaraMicro.bootstrap();

            InstanceDescriptor descriptor = payaraRuntime.getLocalDescriptor();

            System.out.println(" Payara started at " + descriptor.getHttpPorts());
        } catch (BootstrapException e) {
            e.printStackTrace();
        }

    }


    /*
    @Override
    public void memberAdded(InstanceDescriptor instanceDescriptor) {
        System.out.println("Payara Micro Instance " + instanceDescriptor.getMemberUUID() + " has Appeared on Host and Http Port " + instanceDescriptor.getHostName());
    }

    @Override
    public void memberRemoved(InstanceDescriptor instanceDescriptor) {
        System.out.println("Payara Micro Instance " + instanceDescriptor.getMemberUUID() + " has Disappeared on Host and Http Port " + instanceDescriptor.getHostName());
    }
    */

}
