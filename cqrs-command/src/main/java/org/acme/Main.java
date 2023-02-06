package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Customer;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.domain.services.CustomerService;
import org.acme.domain.services.EventService;
import org.acme.domain.services.ResourceService;
import org.acme.domain.services.TeacherService;
import org.acme.utils.JsonUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
            try {
                initDevEnv();
            } catch (InconsistentDomainDataException e) {
                throw new RuntimeException(e);
            }
            Quarkus.waitForExit();
            return 0;
        }
        private void initDevEnv() throws IOException, ClassNotFoundException, InconsistentDomainDataException {
            List<UUID> resourceIdList = new ArrayList<>();
            List<UUID> teacherIdList = new ArrayList<>();
            List<UUID> customerIdList = new ArrayList<>();
            List<String> typeList = new ArrayList<>();
            typeList.add("Fitness");
            typeList.add("Musculation");
            typeList.add("Zumba");
            typeList.add("Abdos/Fessiers");
            typeList.add("Steps");
            typeList.add("Cardio I");
            typeList.add("Cardio II");
            Arrays.stream(JsonUtils.getValue("/init/01-init-data-resources.json", Resource[].class)).forEach(
                    resource -> {
                        resource = resourceService.addResource(resource);
                        resourceIdList.add(resource.getId());
                    }
            );
            Arrays.stream(JsonUtils.getValue("/init/02-init-data-teachers.json", Teacher[].class)).forEach(
                    teacher -> {
                        teacher= teacherService.addTeacher(teacher);
                        teacherIdList.add(teacher.getId());
                    }
            );
            Arrays.stream(JsonUtils.getValue("/init/03-init-data-customers.json", Customer[].class)).forEach(
                    customer -> {
                        customer = customerService.addCustomer(customer);
                        customerIdList.add(customer.getId());
                    }
            );
            for (int i = 0; i < 30; i++) {
                LocalDateTime startDateTime = getStartDateTimeOutOfWE();
                Collections.shuffle(resourceIdList);
                Collections.shuffle(customerIdList);
                Teacher teacher = new Teacher();
                teacher.setId(teacherIdList.get(getRandom(0,teacherIdList.size())));
                List<Customer> customersToEnroll = customerIdList.subList(0, getRandom(5, 10)).stream().map(uuid -> {
                    Customer customer = new Customer();
                    customer.setId(uuid);
                    return customer;
                }).collect(Collectors.toList());
                eventService.addEvent(
                        Event.builder()
                                .startDateTime(startDateTime)
                                .endDateTime(getEndDateTime(startDateTime))
                                .participants(customersToEnroll)
                                .teacher(teacher)
                                .reservedResources(resourceIdList.subList(0,getRandom(1,2)).stream().map(uuid ->
                                     Resource.builder().id(uuid).build()
                                ).collect(Collectors.toList()))
                                .nbMaxParticipant(customersToEnroll.size())
                                .type(typeList.get(new Random().nextInt(typeList.size())))
                                .build()
                );

            }
            customerService.deleteCustomer(customerIdList.get(0));
        }
        private int getRandom(int min, int max){
            return new Random().ints(min,max).findFirst().getAsInt();
        }
        private LocalDateTime getStartDateTimeOutOfWE(){
            boolean sign = new Random().nextBoolean();
            LocalDateTime localDateTime = LocalDateTime.now().withHour(getRandom(8,18)).withMinute(0).withSecond(0).withNano(0);
            if(sign){
                localDateTime=localDateTime.plusDays(getRandom(0,7));

            }else{
                localDateTime=localDateTime.minusDays(getRandom(0,7));
            }
            if(localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                localDateTime=localDateTime.minusDays(1);
            } else if(localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                localDateTime=localDateTime.plusDays(1);
            }
            return localDateTime;
        }
        private LocalDateTime getEndDateTime(LocalDateTime startDateTime){
            List<Integer> slotTimeList = Arrays.asList(30, 60, 90);
            Random rand = new Random();
            startDateTime = startDateTime.plusMinutes(slotTimeList.get(rand.nextInt(slotTimeList.size())));
            return startDateTime;
        }
    }

}
