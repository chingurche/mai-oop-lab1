package structs;
import structs.utils.*;

import java.util.Objects;

public class Structure {
    private final Vector2 position;
    private String name;
    private String description;

    public Structure(Builder<?> b) {
        this.position = Objects.requireNonNull(b.position, "Местоположение должно быть указано");
        setName(b.name);
        setDescription(b.description);
    }

    public Vector2 getPosition() { return position; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не должно быть пустым");
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Описание не должно быть пустым");
        this.description = description;
    }

    public static class Builder<T extends Builder<T>> {
        protected final Vector2 position;

        protected String name = "Неизвестная постройка";
        protected String description = "Про нее нечего сказать";

        public Builder(Vector2 position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        protected T self() { return (T) this; }

        public T name(String v) { this.name = v; return self(); }
        public T description(String v)  { this.description = v;  return self(); }

        public Structure build() {
            return new Structure(this);
        }
    }
}