package logic;

import structs.Building;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final List<Parcel> parcelList;

    public Storage() {
        parcelList = new ArrayList<>(100);
    }

    public Storage(int capacity) {
        parcelList = new ArrayList<>(capacity);
    }

    public void AddParcel(Parcel parcel) {
        if (parcel == null)
            throw new IllegalArgumentException("Посылка не должна быть пустой");
        parcelList.add(parcel);
    }

    public void RemoveParcel(Parcel parcel) {
        if (parcel == null)
            throw new IllegalArgumentException("Запрашиваемая посылка не должна быть пустой");
        parcelList.remove(parcel);
    }
}
