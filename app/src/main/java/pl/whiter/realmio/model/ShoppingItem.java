package pl.whiter.realmio.model;


import io.realm.RealmObject;

/**
 * Created by whiter
 */
public class ShoppingItem extends RealmObject {

    private String id;
    private String name;

    public ShoppingItem() {

    }

    public ShoppingItem(String name, String id) {
        this.name = name;
        this.id = id;
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
}
