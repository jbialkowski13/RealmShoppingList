package pl.whiter.realmio.model;

import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.internal.ColumnType;
import io.realm.internal.Table;

/**
 * Created by whiter
 */
public class Migration implements RealmMigration {


    @Override
    public long execute(Realm realm, long version) {
        Table shoppingItem = realm.getTable(ShoppingItem.class);
        shoppingItem.addColumn(ColumnType.INTEGER, "count");
        return 1;
    }
}
