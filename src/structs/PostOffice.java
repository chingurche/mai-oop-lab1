package structs;

import logic.Parcel;
import logic.Person;
import logic.Storage;
import structs.utils.Vector2;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class PostOffice extends Building {
    private int postalCode;
    private final Storage storage;
    private final BlockingQueue<Request> queue;

    private final Thread operator;
    private volatile boolean running = true;

    public PostOffice(PostOffice.Builder b) {
        super(b);

        setPostalCode(b.postalCode);
        storage = new Storage(b.storageCapacity);
        queue = new ArrayBlockingQueue<>(b.queueCapacity);

        operator = new Thread(this::serveLoop, "Почтовое отделение " + b.postalCode);
        operator.start();
    }

    public int getPostalCode() { return postalCode; }
    private void setPostalCode(int postalCode) {
        if (postalCode < 100000 || postalCode > 999999)
            throw new IllegalArgumentException("Почтовый индекс это шестизначное число");
        this.postalCode = postalCode;
    }

    public void dropOffParcel(Parcel parcel) {
        storage.AddParcel(parcel);
    }

    public void dropOffParcel(Parcel[] parcels) {
        for (Parcel parcel : parcels) {
            storage.AddParcel(parcel);
        }
    }

    private void pickUpParcel(Parcel parcel) {
        storage.RemoveParcel(parcel);
    }

    public CompletableFuture<Person> joinQueue(Person person) {
        Objects.requireNonNull(person, "Человек должен существовать");
        CompletableFuture<Person> served = new CompletableFuture<>();
        try {
            queue.put(new Request(person, served));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            served.completeExceptionally(e);
        }
        return served;
    }

    private void serveLoop() {
        try {
            while (running) {
                Request req = queue.take();
                try {
                    Parcel parcel = req.person.getParcel();
                    pickUpParcel(parcel);
                    Thread.sleep(500);
                    System.out.println(req.person.getName() + " получил посылку " +
                            parcel.getName() + " весом " + parcel.getWeight() + "кг");
                    req.served.complete(req.person);
                } catch (Exception e) {
                    req.served.completeExceptionally(e);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        operator.interrupt();
    }

    private record Request(Person person, CompletableFuture<Person> served) {}

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getName()).append('\n')
                .append(getDescription()).append('\n')
                .append("Этажей: ").append(getFloors())
                .append("        Комнат: ").append(getRooms()).append('\n')
                .append("Местоположение ").append(getPosition())
                .toString();
    }

    public static class Builder extends Building.Builder<Builder> {
        protected final int postalCode;

        protected int storageCapacity = 100;
        protected int queueCapacity = 5;

        public Builder(Vector2 position, int postalCode) {
            super(position);
            this.postalCode = postalCode;
        }

        public Builder storageCapacity(int v) { storageCapacity = v; return this; }
        public Builder queueCapacity(int v) { queueCapacity = v; return this; }

        @Override
        public PostOffice build() {
            if (storageCapacity < 1)
                throw new IllegalArgumentException("Какой смысл от очереди если ее нет?");
            if (storageCapacity < 20) {
                throw new IllegalArgumentException("Какой смысл от " +
                        "склада на котором не поместится даже 20 посылок?");
            }
            return new PostOffice(this);
        }
    }
}
