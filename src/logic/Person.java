package logic;

public class Person {
    private String name;
    private Parcel parcel;

    public Person(String name) {
        setName(name);
    }

    public String getName() { return name; }
    public Parcel getParcel() { return parcel; }

    private void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не должно быть пустым");
        this.name = name;
    }

    public void setParcel(Parcel parcel) {
        if (parcel == null)
            throw new IllegalArgumentException("Посылка получателя не должна быть пустой");
        this.parcel = parcel;
    }
}
