package br.com.savasoft.payara.micro.autobind.cluster;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.data.InstanceDescriptor;
import fish.payara.micro.event.PayaraClusterListener;

public class ClusteredPayaraMicro implements PayaraClusterListener {

    public static void main(String[] args) throws BootstrapException {

        PayaraMicroRuntime runtime = PayaraMicro.getInstance()
                .setHttpAutoBind(true)
                .setInstanceName("Cluster PayaraMicro Teste")
                .bootStrap();

        runtime.addClusterListener(new ClusteredPayaraMicro());

    }

    @Override
    public void memberAdded(InstanceDescriptor instanceDescriptor) {
        System.out.println("Payara Micro Instance " + instanceDescriptor.getMemberUUID() + " has Appeared on Host and Http Port " + instanceDescriptor.getHostName());
    }

    @Override
    public void memberRemoved(InstanceDescriptor instanceDescriptor) {
        System.out.println("Payara Micro Instance " + instanceDescriptor.getMemberUUID() + " has Disappeared on Host and Http Port " + instanceDescriptor.getHostName());
    }

}
