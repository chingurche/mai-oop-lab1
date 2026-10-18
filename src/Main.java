import logic.Parcel;
import logic.Person;
import structs.*;
import structs.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        PostOffice postOffice = new PostOffice.Builder(Vector2.zero(), 780067)
                .name("Почтовое отделение имени Морно")
                .description("Пункт был основан в 1904 году")
                .rooms(3)
                .capacity(10)
                .storageCapacity(100)
                .queueCapacity(5)
                .build();
        System.out.println(postOffice);

        Parcel[] parcels = {
                new Parcel("Туфлеботинкокроссовки", 2.000f),
                new Parcel("Письмо", 0.050f),
                new Parcel("Гири", 6.000f),
                new Parcel("Ростмастер", 0.300f),
                new Parcel("Кости", 0.100f)
        };

        postOffice.dropOffParcel(parcels);

        Person[] persons = {
                new Person("Артемий"),
                new Person("Федя"),
                new Person("Дима"),
                new Person("Ярик"),
                new Person("Влад")
        };

        for (int i = 0; i < persons.length; i++) {
            persons[i].setParcel(parcels[i]);
        }

        for (Person person : persons) {
            postOffice.personEnter(person);
        }

        List<CompletableFuture<Person>> futures = new ArrayList<>();

        for (Person person : persons) {
            CompletableFuture<Person> served = postOffice.joinQueue(person);

            served.thenAccept(p -> {
                postOffice.personExit(p);
                System.out.println(p.getName() + " ушел");
            });

            futures.add(served);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        postOffice.shutdown();
    }
}