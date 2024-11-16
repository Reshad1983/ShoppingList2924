package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.text.LineBreakConfig;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FIRST_TIME = "first_time";
    public static final String FIRST_ENTRY = "first_entry";;
    NamePosPair[] items = {
            new NamePosPair("Avfallspåse", 4), new NamePosPair("Kyckling", 5) , new NamePosPair("Honung", 4), new NamePosPair("Blöja", 4),
            new NamePosPair("Torrschampo", 7), new NamePosPair("Toalettpapper", 4), new NamePosPair("Tandkräm", 7), new NamePosPair("Smoothie", 6),
            new NamePosPair("Gröna linser", 3), new NamePosPair("Deodorant", 7), new NamePosPair("Torkad mango", 2), new NamePosPair("Toscatårta mix", 3),
            new NamePosPair("Babyshampoo", 7), new NamePosPair("Smörgåsgurka", 3), new NamePosPair("Citronjuice", 6), new NamePosPair("Majsstärkelse", 3),
            new NamePosPair("Salsa", 3), new NamePosPair("Diskduk", 4), new NamePosPair("Gurka", 1), new NamePosPair("Chili con carne krydda", 3),
            new NamePosPair("Mjölk", 6), new NamePosPair("Tacokrydda", 3), new NamePosPair("Proteinbar", 2), new NamePosPair("Ayran", 6),
            new NamePosPair("Muffin", 9), new NamePosPair("Toscabulle", 9), new NamePosPair("Kaffe", 2), new NamePosPair("Glass", 2),
            new NamePosPair("Mascarpone", 6), new NamePosPair("Tandkräm (sensitive)", 7), new NamePosPair("Potatischips", 3), new NamePosPair("Bladspenat", 5),
            new NamePosPair("Mikropopcorn", 3), new NamePosPair("Läsk", 3), new NamePosPair("Tortilla", 3), new NamePosPair("Fryspåse", 4),
                new NamePosPair("Digestivekex", 9), new NamePosPair("Lion choklad", 3), new NamePosPair("Laxfilé", 5), new NamePosPair("Högrev", 1),
            new NamePosPair("Blommor", 9), new NamePosPair("Hushållsost", 6), new NamePosPair("Munskölj", 7), new NamePosPair("Välling", 4),
            new NamePosPair("Pizzamix", 6), new NamePosPair("Grönsaker", 8), new NamePosPair("Toningsspray", 7), new NamePosPair("Goudaost", 6),
            new NamePosPair("Vitaminer", 2), new NamePosPair("Rakhyvelset", 7), new NamePosPair("Sandwichglass", 2),
            new NamePosPair("Kanelbullar", 9), new NamePosPair("Eltandborsthuvud", 7), new NamePosPair("Granola", 6), new NamePosPair("Snickersglass", 2),
            new NamePosPair("Toapapper", 4), new NamePosPair("Vetemjöl", 3), new NamePosPair("Goudaost (skivad)", 6), new NamePosPair("Tvättlappar", 4),

            new NamePosPair("Våtservetter", 4), new NamePosPair("Pannkakor", 5), new NamePosPair("Sesamfrön", 3), new NamePosPair("Hamburgare bröd", 8),
            new NamePosPair("Glass", 2), new NamePosPair("Crunchy russin", 2), new NamePosPair("Hudkräm", 7),
            new NamePosPair("Majskorn", 3), new NamePosPair("Dipp", 3), new NamePosPair("Schampo", 7), new NamePosPair("Socker", 4),
            new NamePosPair("Folie", 4), new NamePosPair("Grädde (laktosfri)", 6), new NamePosPair("Avokado", 1), new NamePosPair("Bröd", 8),
            new NamePosPair("Kycklingvingar", 8),  new NamePosPair("Bärmix", 2), new NamePosPair("Smör & rapsolja", 6),
            new NamePosPair("Tandborste", 7), new NamePosPair("Rengöringsspray", 4), new NamePosPair("Sojasås", 6), new NamePosPair("Cheez Cruncherz", 3),
            new NamePosPair("Chips", 3), new NamePosPair("Torkad lime", 1), new NamePosPair("Potatisgratäng", 6), new NamePosPair("Russin", 2),
            new NamePosPair("Pågenlimpa", 8), new NamePosPair("Mandeldryck", 5), new NamePosPair("Vitlöksbaguetter", 5),
            new NamePosPair("Apelsinjuice", 6), new NamePosPair("Choklad", 2), new NamePosPair("Turkisk yoghurt", 6), new NamePosPair("Aluminiumfolie", 4),
            new NamePosPair("Avkalkningspulver", 4), new NamePosPair("Vetemjöl (siktat)", 3), new NamePosPair("Chokladkaka", 3), new NamePosPair("Binda", 7),
            new NamePosPair("Jalapeños", 3), new NamePosPair("Muffins", 8), new NamePosPair("Iskaffe", 5), new NamePosPair("Kanelbullar", 8),
            new NamePosPair("Klorin", 4), new NamePosPair("Chokladmix", 2), new NamePosPair("Kvarg", 6), new NamePosPair("Müslibar", 2),
            new NamePosPair("Fullkornsbröd", 8), new NamePosPair("Kidneybönor", 3), new NamePosPair("Mjölkdryck", 6), new NamePosPair("Liba bröd", 8),
            new NamePosPair("Tresor", 4), new NamePosPair("Snabbnudlar", 3), new NamePosPair("Risifrutti", 6), new NamePosPair("Kikärter", 3),
            new NamePosPair("Grötris", 3), new NamePosPair("Pasta", 3), new NamePosPair("Popcorn", 3), new NamePosPair("Halstablett", 2),
            new NamePosPair("Deospray", 7), new NamePosPair("Dressing", 3),

            new NamePosPair("Engångshyvel", 7), new NamePosPair("Svartpeppar", 3),
            new NamePosPair("Nutella", 4), new NamePosPair("Kaffe", 2), new NamePosPair("Hushållspapper", 4),
            new NamePosPair("Spraygrädde", 6), new NamePosPair("Purjolök", 1),new NamePosPair("Coca-Cola", 3),
            new NamePosPair("Salt", 3), new NamePosPair("Tonfisk", 3), new NamePosPair("Super Cheez Doodles", 3), new NamePosPair("Luftfräschare", 4),
            new NamePosPair("Butterkaka", 3), new NamePosPair("Yoghurt", 6), new NamePosPair("Tuggummi", 2), new NamePosPair("Fönsterputs", 4),
            new NamePosPair("Chokladmousse", 3), new NamePosPair("Tandkräm (Sandra)", 7), new NamePosPair("Tacokyckling", 3),
            new NamePosPair("Wok", 5), new NamePosPair("Ägg", 5), new NamePosPair("Blåbär", 2), new NamePosPair("Broccoli", 5),
            new NamePosPair("Philadelphia", 6), new NamePosPair("Havregryn", 4), new NamePosPair("Paprikapulver", 3), new NamePosPair("Sirap", 4),
            new NamePosPair("Bomullspinnar", 7), new NamePosPair("Vaniljbulle", 8), new NamePosPair("Krossade tomater", 3), new NamePosPair("Köttbullar", 5),
            new NamePosPair("Olivolja", 3), new NamePosPair("Rostad lök", 3), new NamePosPair("Start granola", 4), new NamePosPair("Smoothie", 6),
            new NamePosPair("Mjölkchoklad", 6), new NamePosPair("Potatis", 1), new NamePosPair("Hallon", 2), new NamePosPair("Sköljmedel", 4),
            new NamePosPair("Tranbär", 2), new NamePosPair("Lingongrova", 8), new NamePosPair("Baby wash", 4), new NamePosPair("Grekisk yoghurt", 6),
            new NamePosPair("Iskaffe", 5), new NamePosPair("Geisha", 2), new NamePosPair("Sockerkaka", 3),
            new NamePosPair("Tvålull", 4), new NamePosPair("Cookies", 8), new NamePosPair("Kryddmix", 3), new NamePosPair("Paprika chili", 3)
    };
    int found = 0;
    boolean need_to_be_incremented = false;
    TextView sd_num_view;
    TextView minus_btn;
    TextView plus_btn;
    LinearLayout list_view;
    TextView one_btn;
    TextView two_btn;
    TextView three_btn;
    TextView four_btn;
    TextView five_btn;
    TextView six_btn;
    TextView seven_btn;
    TextView eight_btn;
    TextView nine_btn;
    LinearLayout search_layout;
    LinearLayout fast_search_layout;
    public DatabaseHelper sdb;
    boolean sorting = true;
    EditText search_item;
    ScrollView sc_view;
    int num_of_days = 7;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        list_view = findViewById(R.id.layout_list);
        sdb = new DatabaseHelper(MainActivity.this);
        SharedPreferences myPre = getSharedPreferences(FIRST_TIME, MODE_PRIVATE);
        search_layout = findViewById(R.id.searsh_id);
        fast_search_layout = findViewById(R.id.btn_search_id);
        search_item = search_layout.findViewById(R.id.search_edt);
        String first_time_to_insert = myPre.getString(FIRST_ENTRY, "first_time_to_insert_items");
        sc_view = findViewById(R.id.scroll_view_id);
        TextView search_btn = search_layout.findViewById(R.id.search_btn);
        sd_num_view = fast_search_layout.findViewById(R.id.sdb_num_id);
        minus_btn = search_layout.findViewById(R.id.minus_btn_id);
        plus_btn = search_layout.findViewById(R.id.plus_btn_id);
        one_btn = fast_search_layout.findViewById(R.id.one_id);
        two_btn = fast_search_layout.findViewById(R.id.two_id);
        three_btn = fast_search_layout.findViewById(R.id.three_id);
        four_btn = fast_search_layout.findViewById(R.id.four_id);
        five_btn = fast_search_layout.findViewById(R.id.five_id);
        six_btn = fast_search_layout.findViewById(R.id.six_id);
        seven_btn = fast_search_layout.findViewById(R.id.seven_id);
        eight_btn = fast_search_layout.findViewById(R.id.eight_id);
        nine_btn = fast_search_layout.findViewById(R.id.nine_id);
        one_btn.setOnClickListener(this);
        two_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.two_id);
        three_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.three_id);
        four_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.four_id);
        five_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.five_id);
        six_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.six_id);
        seven_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.seven_id);
        eight_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.eight_id);
        nine_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.nine_id);
        if (first_time_to_insert.equals("first_time_to_insert_items")) {
            for (NamePosPair item : items) {
                sdb.addItem(item.getName(), item.getPos());
            }

            SharedPreferences.Editor editor = myPre.edit();
            editor.putString(FIRST_ENTRY, "not_first_time");
            editor.apply();
        }

        minus_btn.setOnClickListener(v -> {
            num_of_days = (num_of_days > 7)?(num_of_days - 7):90;
            search_item.setText(num_of_days + "");
        });
        plus_btn.setOnClickListener(v -> {
            num_of_days = (num_of_days < 90)?(num_of_days + 7):7;
            search_item.setText(num_of_days + "");

        });
        search_btn.setOnLongClickListener(v -> {
            reset_btn_color();
            String search_text = search_item.getText().toString();
            String[] search_name_pos = search_text.split("\\s+");
            list_view.removeAllViews();
            if (search_text.isEmpty()) {
                sdb.reset_status();
                Toast.makeText(MainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            } else if (is_a_number(search_text))
            {
                Toast.makeText(this, "Press the view", Toast.LENGTH_LONG).show();
                search_item.setText("");
            } else
            {
                int pos = 0;
                int price = 0;
                if ((search_name_pos.length == 3) && (is_a_number(search_name_pos[1]))) {
                    try {
                        price = Integer.parseInt(search_name_pos[2]);
                        pos = Integer.parseInt(search_name_pos[1]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    if (price > 0) {
                        sdb.add_item_price(search_name_pos[0], pos, price);
                        Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
                    }
                } else if ((search_name_pos.length == 3) && !(is_a_number(search_name_pos[1]) && is_a_number(search_name_pos[2]))) {
                    try {

                        pos = Integer.parseInt(search_name_pos[2]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }

                    sdb.add_item_price(search_name_pos[0] + " " + search_name_pos[1], pos, 0);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();


                } else if (search_name_pos.length == 4) {
                    try {
                        price = Integer.parseInt(search_name_pos[3]);
                        pos = Integer.parseInt(search_name_pos[2]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    if (price > 0) {
                        sdb.add_item_price(search_name_pos[0] + " " + search_name_pos[1], pos, price);
                        Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
                    }

                } else if (search_name_pos.length == 1) {
                    if (is_a_number(search_name_pos[0])) {
                        try {
                            price = Integer.parseInt(search_name_pos[0]);
                        } catch (Resources.NotFoundException e) {
                            Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                        }
                        sdb.updatePrice(price, search_name_pos[0]);
                        Toast.makeText(this, "Item updated!!", Toast.LENGTH_LONG).show();
                    }
                } else if (search_name_pos.length == 2) {
                    try {
                        pos = Integer.parseInt(search_name_pos[1]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }

                    sdb.addItem(search_name_pos[0], pos);
                    Toast.makeText(this, "New Item added!", Toast.LENGTH_LONG).show();
                }

                search_item.setText("");
            }
            List<NameStatusPair> items = null;
            try {
                items = sdb.getItemsSortedByUsage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            sd_num_view.setText(String.format("%d", items.size()));
            add_items_to_view(items);
            return true;
        });
        search_btn.setOnClickListener(v -> {
            reset_btn_color();
            EditText search_item = search_layout.findViewById(R.id.search_edt);
            String search_text = search_item.getText().toString();
            List<NameStatusPair> search_items = new ArrayList<>();
            List<NameStatusPair> items = null;
            try {
                items = sdb.getItemsSortedByUsage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            list_view.removeAllViews();
            if (search_text.isEmpty()) {

                for (NameStatusPair item : items) {
                    if (item.getStatus().equals("2")) {
                        sdb.updateStatus(0, item.getName());
                        sdb.set_priority(item.getName(), 0);
                        item.set_prio("0");
                    } else if (item.getStatus().equals("1")) {
                    }
                }
                search_layout.setBackgroundResource(R.drawable.hole_view);
                search_item.setBackgroundResource(R.drawable.hole_view);
                sc_view.fullScroll(ScrollView.FOCUS_UP);
                try {
                    items = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                sd_num_view.setBackgroundResource(R.drawable.hole_view);
                sd_num_view.setText(String.format("%d", items.size()));

                add_items_to_view(items);
                search_item.setHint("SAR");
                found = 1;
            } else if (search_text.equals("r")) {
                sdb.reset_status();
                search_item.setText("");
                try {
                    items = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                add_items_to_view(items);
                Toast.makeText(MainActivity.this, "Usage reset!", Toast.LENGTH_LONG).show();
                found = 1;
            } else if ((search_text.trim().length() == 1) && (Integer.parseInt(search_text) < 10) && (Integer.parseInt(search_text) > 0)) {
                try {
                    items = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                for (NameStatusPair item : items) {
                    if (item.getPos().equals(search_text)) {
                        found = 1;
                        search_items.add(item);
                    }
                }
                sd_num_view.setText(String.format("%d", search_items.size()));
                sd_num_view.setBackgroundResource(R.drawable.checked);
                add_items_to_view(search_items);

                sc_view.fullScroll(ScrollView.FOCUS_UP);
                search_item.setText("");
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);
            } else {
                try {
                    items = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                for (NameStatusPair item : items) {
                    if (item.getName().trim().toLowerCase().contains(search_text.trim().toLowerCase())) {
                        search_item.setHint("Found!");
                        found = 1;
                        search_items.add(item);
                    }
                }
                search_item.setText("");
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);
                search_layout.setBackgroundResource(R.drawable.checked);
                sd_num_view.setText(String.format("%d", search_items.size()));
                add_items_to_view(search_items);
            }
            if (found == 0) {
                search_item.setText("");
                search_item.setHint("Not found!");
            }
        });
        List<NameStatusPair> items = null;
        try {
            items = sdb.getItemsSortedByUsage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        sd_num_view.setText(String.format("%d", items.size()));
        sd_num_view.setBackgroundResource(R.drawable.usage);
        sd_num_view.setOnClickListener(v -> {
            reset_btn_color();
            list_view.removeAllViews();
            if (sorting) {
                List<NameStatusPair> items1 = null;
                try
                {
                    items1 = sdb.getItemsSortedByPosition();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                v.setBackgroundResource(R.drawable.position);
                add_items_to_view(items1);
                sorting = !sorting;
            } else {

                List<NameStatusPair> items1 = null;
                try {
                    items1 = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                v.setBackgroundResource(R.drawable.usage);
                add_items_to_view(items1);
                sorting = !sorting;
            }
        });
        add_items_to_view(items);
        startForegroundService(new Intent(this, CheckDataBase.class));
    }
    //----------------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------------------
    private void add_items_to_view(List <NameStatusPair> item_to_show) {
        for(NameStatusPair item : item_to_show){
            View itemView = getLayoutInflater().inflate(R.layout.item_view, null, false);
            addItemToLayout(itemView, item);
        }
    }
    public void addItemToLayout(View itemView, NameStatusPair item){
        TextView item_name = itemView.findViewById(R.id.textView);
        TextView item_interval = itemView.findViewById(R.id.interval_id);
        TextView item_pos = itemView.findViewById(R.id.pos_view);
        TextView item_used = itemView.findViewById(R.id.used_view_id);
        itemView.setBackgroundResource(R.drawable.hole_view);
        item_pos.setTextColor(Color.WHITE);
        item_used.setTextColor(Color.WHITE);
        item_used.setBackgroundResource(R.drawable.usage);
        item_pos.setBackgroundResource(R.drawable.position);
        switch (item.getStatus()) {
            case "0":
                item_name.setTextColor(Color.WHITE);
                item_name.setBackgroundResource(R.drawable.transparent);
                break;
            case "1":
                item_name.setTextColor(Color.BLACK);
                if(item.getPrio().equals("1"))
                {

                    item_name.setBackgroundResource(R.drawable.prio);
                }
                else
                {
                    item_name.setBackgroundResource(R.drawable.unchecked);

                }

                break;
        }
        item_used.setOnClickListener(v -> change_view_status(itemView,  item, 1));
        item_used.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                item.setUsage("0");
                item_used.setText(item.getUsage()+ " ggr");
                sdb.reset_usage(item.getName(), "0");
                Toast.makeText(MainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
                need_to_be_incremented = true;
                return false;
            }
        });
        item_pos.setOnClickListener(v -> change_view_status(itemView,  item, 0));
        item_pos.setOnLongClickListener(v -> {
            int pos = Integer.parseInt(item.getPos());
            String new_pos = ((pos + 1) > 9 ) ? "1" : (pos + 1)+ "";
            item.setPos(new_pos);
            sdb.updatePos(item.getPos(), item.getName());
            item_pos.setText(item.getPos());
            return true;
        });
        item_interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!search_item.getText().toString().isEmpty())
                {
                    if(is_a_number(search_item.getText().toString()))
                    {
                        sdb.update_interval( Integer.parseInt(search_item.getText().toString()), item.getName());
                        item.set_interval(search_item.getText().toString());
                        item_interval.setText(search_item.getText().toString());
                        num_of_days = 7;
                        Toast.makeText(MainActivity.this, "Interval updated", Toast.LENGTH_LONG).show();
                        search_item.setText("");
                    }
                }
            }
        });

        item_name.setOnLongClickListener(v -> {
            if(!search_item.getText().toString().isEmpty())
            {
                String new_name = search_item.getText().toString();
                sdb.update_item_name(new_name , item.getName());
                item.set_name(new_name);
                Toast.makeText(MainActivity.this, "Item name updated!", Toast.LENGTH_SHORT).show();
                item_name.setText(item.getName());
                item_interval.setText(item.get_interval());
            }
            else
            {

                sdb.removeItem(item_name.getText().toString());
                list_view.removeView(itemView);
            }
            search_item.setText("");
            return true;
        });
        item_name.setOnClickListener(v -> {
            if(item.getStatus().equals("1"))
            {
                if(item.getPrio().equals("0"))
                {

                    sdb.set_priority(item.getName(), 1);
                    item.set_prio("1");
                    item_name.setBackgroundResource(R.drawable.prio);
                }
                else
                {
                    sdb.set_priority(item.getName(), 0);
                    item.set_prio("0");
                    item_name.setBackgroundResource(R.drawable.unchecked);
                }
            }
        });
        item_pos.setText(item.getPos() );
        item_name.setText(item.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            item_name.setLineBreakStyle(LineBreakConfig.LINE_BREAK_STYLE_LOOSE);
        }
        String item_interval_num = item.get_interval();
        item_interval.setText(item_interval_num);

        item_used.setText(item.getUsage()+ " ggr");
        list_view.addView(itemView);
    }

    private boolean is_a_number(String string)
    {
        char [] num = string.toCharArray();
        for(char c : num)
        {
            if(!Character.isDigit(c))
            {
                return false;
            }

        }
        return true;
    }

    private void change_view_status(View itemView, NameStatusPair item, int h_v_view)
    {
        TextView item_name = itemView.findViewById(R.id.textView);
        TextView item_pos = itemView.findViewById(R.id.pos_view);

        TextView item_price = itemView.findViewById(R.id.interval_id);//.setText(search_item.getText().toString() + " sek");
        TextView item_used = itemView.findViewById(R.id.used_view_id);

        String status = item.getStatus();
        int unchecked_status = 1;
        int no_status = 0;
        if(!need_to_be_incremented)
        {
            switch(status)
            {
                case "0":
                    if(h_v_view == 1)
                    {
                        item_name.setTextColor(R.drawable.usage);
                        item_name.setBackgroundResource(R.drawable.unchecked);
                        item_pos.setText(item.getPos());
                        item_name.setText(item.getName());
                        item_price.setText(item.get_interval());
                        item_used.setText(item.getUsage()+ " ggr");
                        item.setStatus("1");
                        sdb.updateStatus(unchecked_status, item.getName());

                    }
                    break;
                case "1":
                    item_name.setTextColor(Color.WHITE);
                    if(h_v_view == 1)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        item_name.setBackgroundResource(R.drawable.checked);
                        sdb.incrementUsageCount(item.getName());
                        int checked_status = 2;
                        sdb.updateStatus(checked_status, item.getName());
                        sdb.update_date(date, item.getName());
                        item.setStatus("2");
                        item_pos.setText(item.getPos());
                        item_name.setText(item.getName());
                        item_price.setText(item.get_interval());
                        item_used.setText(item.getUsage()+ " ggr");
                    }
                    else
                    {
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item.setStatus("0");
                        item_pos.setText(item.getPos());
                        item_name.setText(item.getName());
                        item_price.setText(item.get_interval());
                        item_used.setText(item.getUsage()+ " ggr");
                        sdb.updateStatus(no_status, item.getName());
                        sdb.set_priority(item.getName(), 0);
                    }
                    break;
                case "2":
                    if(h_v_view == 0)
                    {
                        item_name.setTextColor(Color.BLACK);
                        item_name.setBackgroundResource(R.drawable.unchecked);
                        sdb.updateStatus(unchecked_status, item.getName());
                        item.setStatus("1");
                        item_name.setText(item.getName());
                        item_price.setText(item.get_interval());
                        item_pos.setText(item.getPos());
                        item_used.setText(item.getUsage()+ " ggr");
                    }
                    else
                    {

                        item_name.setTextColor(Color.WHITE);
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item_name.setText(item.getName());
                        item_price.setText(item.get_interval());
                        item.setStatus("0");
                        item_pos.setText(item.getPos());
                        item_used.setText(item.getUsage()+ " ggr");
                        sdb.updateStatus(no_status, item.getName());
                    }
                    break;

            }
        }
        need_to_be_incremented = false;

    }
    @Override
    protected void onUserLeaveHint() {
        this.finish();
        super.onUserLeaveHint();
    }

    @Override
    public void onClick(View v) {
        reset_btn_color();
        String pos = getString(v);
        list_view.removeAllViews();

        List<NameStatusPair> search_items = new ArrayList<>();
        List<NameStatusPair >items = null;
        try {
            items = sdb.getItemsSortedByUsage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (NameStatusPair item_pos_search : items) {
            if (item_pos_search.getPos().equals(pos)) {
                search_items.add(item_pos_search);
            }
        }
        sd_num_view.setText(String.format("%d",search_items.size()));
        sd_num_view.setBackgroundResource(R.drawable.checked);
        add_items_to_view(search_items);

        sc_view.fullScroll(ScrollView.FOCUS_UP);
        search_item.setText("");
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);
    }

    private static @NonNull String getString(View v) {
        int i = v.getId();
        String pos = "";
        if(i == R.id.one_id)
        {
            v.setBackgroundResource(R.drawable.checked);
            pos = "1";
        }
        else if(i == R.id.two_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "2";
        }
        else if(i == R.id.three_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "3";
        }
        else if(i == R.id.four_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "4";
        }
        else if(i == R.id.five_id)
        {
            v.setBackgroundResource(R.drawable.checked);
            pos = "5";
        }
        else if(i == R.id.six_id)
        {
            v.setBackgroundResource(R.drawable.checked);
            pos = "6";
        }
        else if(i == R.id.seven_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "7";
        }
        else if(i == R.id.eight_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "8";
        }
        else if(i == R.id.nine_id)
        {

            v.setBackgroundResource(R.drawable.checked);
            pos = "9";
        }
        return pos;
    }
    private void reset_btn_color()
    {
        one_btn.setBackgroundResource(R.drawable.transparent);
        two_btn.setBackgroundResource(R.drawable.transparent);
        three_btn.setBackgroundResource(R.drawable.transparent);
        four_btn.setBackgroundResource(R.drawable.transparent);
        five_btn.setBackgroundResource(R.drawable.transparent);
        six_btn.setBackgroundResource(R.drawable.transparent);
        seven_btn.setBackgroundResource(R.drawable.transparent);
        eight_btn.setBackgroundResource(R.drawable.transparent);
        nine_btn.setBackgroundResource(R.drawable.transparent);
    }

}
