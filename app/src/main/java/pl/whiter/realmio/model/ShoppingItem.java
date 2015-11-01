package pl.whiter.realmio.model;


import io.realm.RealmObject;

/**
 * Created by whiter
 */
public class ShoppingItem extends RealmObject {

    private String id;
    private String name;
    private int count;

    public ShoppingItem() {

    }


    public ShoppingItem(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
