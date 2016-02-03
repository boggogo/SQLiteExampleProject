package georgikoemdzhiev.sqliteexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBHandler db = new DBHandler(this);

// Inserting Shop/Rows
//        Log.d("Insert: ", "Inserting ..");
//        db.addShop(new Shop(0,"My Shop","Union Street"));
//        db.addShop(new Shop(1,"Mamut","Budapest Street"));
//        db.addShop(new Shop(2,"Fox","Fox Street"));

//        db.deleteShop(new Shop(2,"",""));
        db.updateShop(new Shop(0,"THE BEST STORE EVER","Best Street 101"));

// Reading all shops
        Log.d("Reading: ", "Reading all shops..");
        List<Shop> shops = db.getAllShops();

        for (Shop shop : shops) {
            String log = "Id: " + shop.getId() + " ,Name: " + shop.getName() + " ,Address: " + shop.getAddress();
// Writing shops to log
            Log.d("Shop: : ", log);
        }

    }
}
