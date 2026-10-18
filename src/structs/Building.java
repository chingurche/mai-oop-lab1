package structs;

import logic.Person;
import structs.utils.*;

import java.util.ArrayList;
import java.util.List;


public class Building extends Structure {
    private final int floors;
    private final int rooms;
    private final List<Person> personList;

    public Building(Builder<?> b) {
        super(b);
        this.floors = b.floors;
        this.rooms = b.rooms;

        personList = new ArrayList<>(b.capacity);
    }

    public int getFloors() { return floors; }
    public int getRooms() { return rooms; }

    public void personEnter(Person person) {
        if (person == null)
            throw new IllegalArgumentException("Человек должен существовать (быть инициализированным)");
        if (personList.contains(person))
            throw new IllegalArgumentException("Человек уже в здании");
        personList.add(person);
    }
    public void personExit(Person person) {
        int index = personList.indexOf(person);
        if (index == -1)
            throw new IllegalArgumentException("Человека не было в здании");
        personList.remove(index);
    }

    public static class Builder<T extends Builder<T>> extends Structure.Builder<T> {
        protected int floors = 1;
        protected int rooms = 1;
        protected int capacity = 1;

        public Builder(Vector2 position) { super(position); }

        public T floors(int v) { this.floors = v; return self(); }
        public T rooms(int v) { this.rooms = v; return self(); }
        public T capacity(int v) { this.capacity = v; return self(); }

        @Override
        public Building build() {
            if (floors < 1)
                throw new IllegalArgumentException("Должен быть минимум 1 этаж");
            if (rooms < 1)
                throw new IllegalArgumentException("Должна быть минимум 1 комната");
            if (capacity < 1) {
                throw new IllegalArgumentException("Должна быть возможность " +
                        "для входа хотя бы для 1 человека. Иначе какое это здание?");
            }
            return new Building(this);
        }
    }
}
