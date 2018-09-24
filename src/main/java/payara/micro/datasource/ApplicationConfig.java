package payara.micro.datasource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

    /**
     *
     *  OBS: Em outro exemplo do próprio payara, estes métodos abaixo não foram utilizados
     *
     *
     */

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourcesClasses(resources);
        return resources;
    }

    private void addRestResourcesClasses(Set<Class<?>> resources) {
        resources.add(SimpleKeyValueFacadeREST.class);
    }

}
