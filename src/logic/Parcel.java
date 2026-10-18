package logic;

public class Parcel {
    private String name;
    private float weight;

    public Parcel(String name, float weight) {
        setName(name);
        setWeight(weight);
    }

    public String getName() { return name; }

    public float getWeight() { return weight; }

    private void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не должно быть пустым");
        this.name = name;
    }

    private void setWeight(float weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("Груз должен иметь положительный вес");
        this.weight = weight;
    }
}
