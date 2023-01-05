package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.domain.services.CustomerService;
import org.acme.domain.services.EventService;
import org.acme.domain.services.ResourceService;
import org.acme.domain.services.TeacherService;

import javax.inject.Inject;

@QuarkusMain
public class Main {


    public static void main(String ... args) {
        Quarkus.run(CommandApp.class, args);
    }
    public static class CommandApp implements QuarkusApplication {
        @Inject CustomerService customerService;
        @Inject EventService eventService;
        @Inject TeacherService teacherService;
        @Inject ResourceService resourceService;
        @Override
        public int run(String... args) throws Exception {
            initDevEnv();
            Quarkus.waitForExit();
            return 0;
        }
        private void initDevEnv(){
            System.out.println("-------------> TEST <-------------");
        }
    }

}
