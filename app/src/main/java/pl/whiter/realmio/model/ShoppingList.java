package pl.whiter.realmio.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by whiter
 */
public class ShoppingList extends RealmObject {

    private String id;
    private RealmList<ShoppingItem> items;
    private ShoppingListDetails details;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public RealmList<ShoppingItem> getItems() {
        return items;
    }

    public void setItems(RealmList<ShoppingItem> items) {
        this.items = items;
    }

    public ShoppingListDetails getDetails() {
        return details;
    }

    public void setDetails(ShoppingListDetails details) {
        this.details = details;
    }
}
