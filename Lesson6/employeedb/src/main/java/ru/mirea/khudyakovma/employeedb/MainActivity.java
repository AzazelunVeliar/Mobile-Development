package ru.mirea.khudyakovma.employeedb;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HeroDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        HeroDao dao = db.heroDao();

        Hero h1 = new Hero("Железный человек", "Миллиардер, плейбой, филантроп");
        Hero h2 = new Hero("Росомаха", "Усиленная регенерация и когти из адамантия");
        Hero h3 = new Hero("Человек-паук", "Паучьи способности и паутина");
        Hero h4 = new Hero("Дэдпул", "Болтливый наёмник с невероятной регенерацией");

        dao.insert(h1);
        dao.insert(h2);
        dao.insert(h3);
        dao.insert(h4);

        List<Hero> all = dao.getAll();
        for (Hero h : all) {
            Log.d(TAG, "Loaded: " + h.id + " | " + h.name + " — " + h.description);
        }

        if (!all.isEmpty()) {
            Hero first = all.get(0);
            first.description = "Гениальный изобретатель и филантроп";
            dao.update(first);
            Log.d(TAG, "Updated: " + first.name + " -> " + first.description);
        }

        Hero byId = dao.getById(1);
        if (byId != null) {
            dao.delete(byId);
            Log.d(TAG, "Deleted: " + byId.id);
        }
    }
}
