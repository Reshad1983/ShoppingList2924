package com.example.myapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String FIRST_TIME = "first_time";
    public static final String FIRST_ENTRY = "first_entry";

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
    TextView sum_view;
    LinearLayout list_view;
    LinearLayout search_layout;
    public DatabaseHelper sdb;
    int total_to_buy = 0;
boolean sorting = true;
    EditText search_item;
    ScrollView sc_view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        list_view = findViewById(R.id.layout_list);
        sdb = new DatabaseHelper(MainActivity.this);
        SharedPreferences myPre = getSharedPreferences(FIRST_TIME, MODE_PRIVATE);
        search_layout = findViewById(R.id.searsh_id);
        search_item = search_layout.findViewById(R.id.search_edt);
        String first_time_to_insert = myPre.getString(FIRST_ENTRY, "first_time_to_insert_items");
        sc_view = findViewById(R.id.scroll_view_id);
        TextView search_btn = search_layout.findViewById(R.id.search_btn);
        sd_num_view = search_layout.findViewById(R.id.sdb_num_id);
        sum_view = search_layout.findViewById(R.id.sum_id);
        if(first_time_to_insert.equals("first_time_to_insert_items"))
        {
            for(NamePosPair item : items){
                sdb.addItem(item.getName(), item.getPos());
            }
            SharedPreferences.Editor editor = myPre.edit();
            editor.putString(FIRST_ENTRY, "not_first_time");
            editor.apply();
        }
        search_btn.setOnLongClickListener(v -> {

            String search_text = search_item.getText().toString();
            String []search_name_pos = search_text.split("\\s+");
            list_view.removeAllViews();
            if(search_text.isEmpty())
            {
                sdb.reset_status();
                Toast.makeText(MainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            }
            else if(is_a_number(search_text)) {
                Toast.makeText(this, "Press te view", Toast.LENGTH_LONG).show();
                search_item.setText("");
            }
            else
            {
                int pos = 0;
                int price = 0 ;
                if(search_name_pos.length > 2)
                {
                    try
                    {
                        price = Integer.parseInt(search_name_pos[2]);
                    }
                    catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    if (price > 0) {

                        sdb.updatePrice(price, search_name_pos[0]);
                        Toast.makeText(this, "Item updated!!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if (Integer.parseInt(search_name_pos[1]) > 9)
                    {
                        try
                        {
                            price = Integer.parseInt(search_name_pos[1]);
                        }
                        catch (Resources.NotFoundException e) {
                            Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                        }
                        sdb.updatePrice(price, search_name_pos[0]);
                        Toast.makeText(this, "Item updated!!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        try
                        {
                            pos = Integer.parseInt(search_name_pos[1]);
                        }
                        catch (Resources.NotFoundException e) {
                            Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                        }

                        sdb.addItem(search_name_pos[0], pos);
                        Toast.makeText(this, "New Item added!", Toast.LENGTH_LONG).show();
                    }
                }
                search_item.setText("");
            }
            List<NameStatusPair> items = sdb.getItemsSortedByUsageAndStatus();

            sd_num_view.setText(String.format("%d", items.size()));
            add_items_to_view(items);
            return true;
        });
        search_btn.setOnClickListener(v -> {

            EditText search_item = search_layout.findViewById(R.id.search_edt);
            String search_text = search_item.getText().toString();
            List<NameStatusPair> search_items = new ArrayList<>();
            List<NameStatusPair> items = sdb.getItemsSortedByUsageAndStatus();
            boolean reset_sum = true;
            list_view.removeAllViews();
            if(search_text.isEmpty())
                {

                    for(NameStatusPair item : items)
                    {
                        if(item.getStatus().equals("2") )
                        {
                            sdb.updateStatus(0, item.getName());
                        }
                        else if(item.getStatus().equals("1"))
                        {
                            reset_sum = false;
                            total_to_buy -= Integer.parseInt(item.getPrice());
                        }
                    }
                    search_layout.setBackgroundResource(R.drawable.hole_view);
                    search_item.setBackgroundResource(R.drawable.hole_view);
                    sc_view.fullScroll(ScrollView.FOCUS_UP);
                    items = sdb.getItemsSortedByUsageAndStatus();
                    sd_num_view.setBackgroundResource(R.drawable.hole_view);
                    sd_num_view.setText(String.format("%d",items.size()));
                    if(reset_sum)
                    {
                        total_to_buy = 0;
                    }
                    add_items_to_view(items);
                    search_item.setHint("SAR");
                    sum_view.setText(total_to_buy + " sek");
                    found = 1;
                }
                else if(search_text.equals("0"))
                {

                    sdb.reset_usage();
                    search_item.setText("");
                    items = sdb.getItemsSortedByUsageAndStatus();
                    add_items_to_view(items);
                    Toast.makeText(MainActivity.this, "Usage reset!", Toast.LENGTH_LONG).show();
                    found = 1;
                }
                else if ((search_text.trim().length() == 1) && (Integer.parseInt(search_text) < 10) && (Integer.parseInt(search_text) > 0) )
                {
                    items = sdb.getItemsSortedByUsageAndStatus();
                    for (NameStatusPair item : items) {
                        if (item.getPos().equals(search_text)) {
                            found = 1;
                            search_items.add(item);
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
               else
               {
                    items = sdb.getItemsSortedByUsageAndStatus();
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
                sd_num_view.setText(String.format("%d",search_items.size()));
                add_items_to_view(search_items);
            }
            if(found == 0)
            {
                search_item.setText("");
                search_item.setHint("Not found!");
            }
        });
        List<NameStatusPair> items = sdb.getItemsSortedByUsage();
        sd_num_view.setText(String.format("%d",items.size()));
        sd_num_view.setBackgroundResource(R.drawable.usage);
        sd_num_view.setOnClickListener(v -> {
            list_view.removeAllViews();
            if(sorting)
            {
                List<NameStatusPair> items1 = sdb.getItemsSortedByUsage();
                v.setBackgroundResource(R.drawable.usage);
                add_items_to_view(items1);
                sorting = !sorting;
            }
            else
            {

                List<NameStatusPair> items1 = sdb.getItemsSortedByUsageAndStatus();
                v.setBackgroundResource(R.drawable.position);
                add_items_to_view(items1);
                sorting = !sorting;
            }
        });
       add_items_to_view(items);
    }
    //----------------------------------------------------------------------------------------------------------
    private void add_items_to_view(List <NameStatusPair> item_to_show) {
        for(NameStatusPair item : item_to_show){
            View itemView = getLayoutInflater().inflate(R.layout.item_view, null, false);
            addItemToLayout(itemView, item);
        }
    }
    public void addItemToLayout(View itemView, NameStatusPair item){
        TextView item_name = itemView.findViewById(R.id.textView);
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
                item_name.setBackgroundResource(R.drawable.unchecked);
                total_to_buy += Integer.parseInt(item.getPrice());
                sum_view.setText(total_to_buy + " sek");

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
            item_pos.setText("pos: " + item.getPos());
            return true;
        });
        item_name.setOnLongClickListener(v -> {
            if(!search_item.getText().toString().isEmpty())
            {
                if(is_a_number(search_item.getText().toString()))
                {
                    sdb.updatePrice( Integer.parseInt(search_item.getText().toString()), item.getName());
                    item_name.setText(item.getName());
                    item_name.append("\n" + search_item.getText().toString() + " sek");
                }
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
                total_to_buy += Integer.parseInt(item.getPrice());
                sum_view.setText(total_to_buy + " sek");
            }
        });
        item_pos.setText("pos: " + item.getPos() );
        item_name.setText(item.getName());
        item_name.append("\n" + item.getPrice() + " sek");

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
                        item_name.setTextColor(Color.BLACK);
                        item_name.setBackgroundResource(R.drawable.unchecked);
                        item_pos.setText("pos: " + item.getPos());
                        item_name.setText(item.getName());
                        item_name.append("\n" + item.getPrice() + " sek");
                        item_used.setText(item.getUsage()+ " ggr");
                        item.setStatus("1");
                        sdb.updateStatus(unchecked_status, item.getName());
                        total_to_buy += Integer.parseInt(item.getPrice());
                        sum_view.setText(total_to_buy + " sek");
                    }
                    break;
                case "1":
                    item_name.setTextColor(Color.WHITE);
                    if(h_v_view == 1)
                    {
                        item_name.setBackgroundResource(R.drawable.checked);
                        sdb.incrementUsageCount(item.getName());
                        int checked_status = 2;
                        sdb.updateStatus(checked_status, item.getName());
                        item.setStatus("2");
                        item_pos.setText("pos: " + item.getPos());
                        item_name.setText(item.getName());
                        item_name.append("\n" + item.getPrice() + " sek");
                        item_used.setText(item.getUsage()+ " ggr");
                    }
                    else
                    {
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item.setStatus("0");
                        item_pos.setText("pos: " + item.getPos());
                        item_name.setText(item.getName());
                        item_name.append("\n" + item.getPrice() + " sek");
                        item_used.setText(item.getUsage()+ " ggr");
                        sdb.updateStatus(no_status, item.getName());
                    }
                    total_to_buy -= Integer.parseInt(item.getPrice());
                    sum_view.setText(Math.max(total_to_buy, 0) + " sek");
                    break;
                case "2":
                    if(h_v_view == 0)
                    {
                        item_name.setTextColor(Color.BLACK);
                        item_name.setBackgroundResource(R.drawable.unchecked);
                        sdb.updateStatus(unchecked_status, item.getName());
                        item.setStatus("1");
                        item_name.setText(item.getName());
                        item_name.append("\n" + item.getPrice() + " sek");
                        item_pos.setText("pos: " + item.getPos());
                        item_used.setText(item.getUsage()+ " ggr");
                        total_to_buy += Integer.parseInt(item.getPrice());
                        sum_view.setText(total_to_buy + " sek");
                    }
                    else
                    {

                        item_name.setTextColor(Color.WHITE);
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item_name.setText(item.getName());
                        item_name.append("\n" + item.getPrice() + " sek");
                        item.setStatus("0");
                        item_pos.setText("pos: " + item.getPos());
                        item_used.setText(item.getUsage()+ " ggr");
                        sdb.updateStatus(no_status, item.getName());
                        total_to_buy -= Integer.parseInt(item.getPrice());
                        sum_view.setText(Math.max(total_to_buy, 0) + " sek");
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

}
