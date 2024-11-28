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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewMainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    public static final String FIRST_TIME = "first_time";
    public static final String FIRST_ENTRY = "first_entry";;
    int found = 0;
    boolean need_to_be_incremented = false;
    TextView sd_num_view;
    TextView food_sum_view;
    TextView monday_view;
    TextView tuesday_view;
    TextView wednesday_view;
    TextView thursday_view;
    TextView friday_view;
    TextView saturday_view;
    TextView sunday_view;

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

    TextView day_select;
    public DatabaseHelper sdb;
    boolean sorting = true;
    EditText search_item;
    ScrollView sc_view;
    int num_of_days = 7;
    boolean register_cond;
    boolean switch_to_food_list;
    private StringBuilder list_of_ingredients;
    private TextView global_food_view;

    private List<String> day_food_to_show;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_main_layout);
        register_cond = false;
        switch_to_food_list = true;
        list_view = findViewById(R.id.con_item_view);
        food_sum_view = findViewById(R.id.food_list_id);
        sdb = new DatabaseHelper(NewMainActivity.this);
        SharedPreferences myPre = getSharedPreferences(FIRST_TIME, MODE_PRIVATE);
        search_item = findViewById(R.id.con_search_id);
        sc_view = findViewById(R.id.con_sc_id);
        TextView search_btn = findViewById(R.id.con_search_btn);
        day_select = findViewById(R.id.con_day_id);

        sd_num_view = findViewById(R.id.con_sort);
        minus_btn = findViewById(R.id.con_min_id);
        plus_btn = findViewById(R.id.con_plus_id);
        monday_view = findViewById(R.id.con_mon_id);
        tuesday_view = findViewById(R.id.con_tus_id);
        wednesday_view = findViewById(R.id.con_wed_id);
        thursday_view = findViewById(R.id.con_thu_id);
        friday_view = findViewById(R.id.con_fri_id);
        saturday_view = findViewById(R.id.con_sat_id);
        sunday_view = findViewById(R.id.con_sun_id);
        monday_view.setOnLongClickListener(this);
        tuesday_view.setOnLongClickListener(this);
        wednesday_view.setOnLongClickListener(this);
        thursday_view.setOnLongClickListener(this);
        friday_view.setOnLongClickListener(this);
        saturday_view.setOnLongClickListener(this);
        sunday_view.setOnLongClickListener(this);


        monday_view.setOnClickListener(this);
        tuesday_view.setOnClickListener(this);
        wednesday_view.setOnClickListener(this);
        thursday_view.setOnClickListener(this);
        friday_view.setOnClickListener(this);
        saturday_view.setOnClickListener(this);
        sunday_view.setOnClickListener(this);


        day_food_to_show = new ArrayList<>();
        one_btn = findViewById(R.id.one_con_id);
        two_btn = findViewById(R.id.two_con_id);
        three_btn = findViewById(R.id.three_con_id);
        four_btn = findViewById(R.id.four_con_id);
        five_btn = findViewById(R.id.five_con_id);
        six_btn = findViewById(R.id.six_con_id);
        seven_btn = findViewById(R.id.seven_con_id);
        eight_btn = findViewById(R.id.eight_con_id);
        list_of_ingredients = new StringBuilder();
        nine_btn = findViewById(R.id.nine_con_id);
        one_btn.setOnClickListener(this);
        two_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.two_id);
        three_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.three_id);
        four_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.four_id);
        five_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.five_id);
        six_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.six_id);
        seven_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.seven_id);
        eight_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.eight_id);
        nine_btn.setOnClickListener(this);// = fast_search_layout.findViewById(R.id.nine_id);

        day_select.setOnClickListener(v -> {
            String selected_day_string = day_select.getText().toString();
            switch (selected_day_string) {
                case "Day":
                    day_select.setText(R.string.monday);
                    break;
                case "Mo":
                    day_select.setText(R.string.tuesday);
                    break;
                case "Tu":
                    day_select.setText(R.string.wednesday);
                    break;
                case "We":
                    day_select.setText(R.string.thursday);
                    break;
                case "Th":
                    day_select.setText(R.string.friday);
                    break;
                case "Fr":
                    day_select.setText(R.string.saturday);
                    break;
                case "Sa":
                    day_select.setText(R.string.sunday);
                    break;
                case "Su":
                    day_select.setText(R.string.day);
                    break;
            }

        });


        food_sum_view.setText(sdb.get_number_of_foods() + "");
        food_sum_view.setOnClickListener(v -> {
            reset_btn_color();
            day_food_to_show.clear();
            list_view.removeAllViews();
            List<FoodDay> food_list = sdb.get_food_names();
            List<String> food_name_list = new ArrayList<>();
            for(FoodDay food : food_list) food_name_list.add(food.getFoodName());
            add_food_to_view(food_name_list);
        });// = fast_search_layout.findViewById(R.id.nine_id);
        //This part is for recipe registration
        plus_btn.setOnLongClickListener(v -> {
            list_of_ingredients = new StringBuilder();
            if(search_item.getText().toString().isEmpty())
            {
                Toast.makeText(NewMainActivity.this, "Please enter food name!", Toast.LENGTH_SHORT).show();
            }
            else if(is_a_number(search_item.getText().toString()))
            {
                Toast.makeText(NewMainActivity.this, "Please enter food name!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                List<NameStatusPair> food_items;
                try {
                    food_items = sdb.getItemsSortedByPosition();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                for(NameStatusPair item: food_items)
                {
                    if(item.getStatus().equals("1"))
                    {
                        list_of_ingredients.append("-").append(item.getName());
                    }
                }
                //Here to add food name and ingredients to database
                sdb.add_food(search_item.getText().toString(), list_of_ingredients.toString(), day_select.getText().toString());
                food_sum_view.setText(sdb.get_food_names().size()+"");
                search_item.setText("");
                Toast.makeText(NewMainActivity.this, "New food added!", Toast.LENGTH_SHORT).show();
            }

            return false;
        });

        minus_btn.setOnClickListener(v -> {
            num_of_days = (num_of_days > 7)?(num_of_days - 7):90;
            search_item.setText(num_of_days + "");
        });
        minus_btn.setOnLongClickListener(v -> {
            get_random_day_food();
            return false;
        });
        plus_btn.setOnClickListener(v -> {
            num_of_days = (num_of_days < 90)?(num_of_days + 7):7;
            search_item.setText(num_of_days + "");

        });
        search_btn.setOnLongClickListener(v -> {
            reset_btn_color();
            String search_text = search_item.getText().toString();
            String[] search_name_pos_duration = search_text.split("\\s+");
            list_view.removeAllViews();
            //Reset all status if nothing entered
            if (search_text.isEmpty()) {
                sdb.reset_status();
                Toast.makeText(NewMainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            }// If user entered just a number then an item interval should be updated
            else if (is_a_number(search_text))
            {
                Toast.makeText(this, "Press the view", Toast.LENGTH_LONG).show();
                search_item.setText("");
            } else
            {
                int pos = 0;
                int duration = 0;
                //If user entered 3 separated input in search box and two of them are numbers then it must be name, position and duration
                if ((search_name_pos_duration.length == 3) && (is_a_number(search_name_pos_duration[1])))
                {
                    try
                    {
                        duration = Integer.parseInt(search_name_pos_duration[2]);
                        pos = Integer.parseInt(search_name_pos_duration[1]);
                    } catch (Resources.NotFoundException e)
                    {
                        Toast.makeText(this, "Enter position or duration", Toast.LENGTH_LONG).show();
                    }
                    sdb.add_item(search_name_pos_duration[0], pos, duration);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
                    //If user entered 3 separated input in search box and the second one is also a text then it must be item name and position, duration must be 1
                } else if ((search_name_pos_duration.length == 3) && !(is_a_number(search_name_pos_duration[1]) && is_a_number(search_name_pos_duration[2])))
                {
                    try
                    {

                        pos = Integer.parseInt(search_name_pos_duration[2]);
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }

                    sdb.add_item(search_name_pos_duration[0] + " " + search_name_pos_duration[1], pos, 0);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();


                    //If user entered 3 separated input in search box and two of them are numbers then it must be name, position and duration
                } else if (search_name_pos_duration.length == 4)
                {
                    try {
                        duration = Integer.parseInt(search_name_pos_duration[3]);
                        pos = Integer.parseInt(search_name_pos_duration[2]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    sdb.add_item(search_name_pos_duration[0] + " " + search_name_pos_duration[1], pos, duration);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();

                }else if (search_name_pos_duration.length == 2)
                {
                    try
                    {
                        pos = Integer.parseInt(search_name_pos_duration[1]);
                    } catch (Resources.NotFoundException e)
                    {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    sdb.add_item(search_name_pos_duration[0], pos, 1);
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
            day_food_to_show.clear();
            reset_btn_color();
            String search_text = search_item.getText().toString();
            List<NameStatusPair> search_items = new ArrayList<>();
            List<NameStatusPair> items;
            try {
                items = sdb.getItemsSortedByUsage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            list_view.removeAllViews();
            if (search_text.isEmpty()) {

                for (NameStatusPair item : items) {
                    if (item.getStatus().equals("2")) {
                        sdb.update_status(0, item.getName());
                        sdb.set_priority(item.getName(), 0);
                        item.set_prio("0");
                    }
                }
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
            } else if (search_text.equals("r")) {
                sdb.reset_status();
                search_item.setText("");
                try {
                    items = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                add_items_to_view(items);
                Toast.makeText(NewMainActivity.this, "Usage reset!", Toast.LENGTH_LONG).show();
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
                InputMethodManager imm = (InputMethodManager) NewMainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                InputMethodManager imm = (InputMethodManager) NewMainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);
                sd_num_view.setText(String.format("%d", search_items.size()));
                add_items_to_view(search_items);
            }
            if (found == 0) {
                search_item.setText("");
                search_item.setHint("Not found!");
            }
        });
        List<NameStatusPair> items;
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
            List<NameStatusPair> items1 = null;
            if (sorting) {
                try
                {
                    items1 = sdb.getItemsSortedByPosition();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                v.setBackgroundResource(R.drawable.position);
            } else {

                try {
                    items1 = sdb.getItemsSortedByUsage();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                v.setBackgroundResource(R.drawable.usage);
            }
            add_items_to_view(items1);
            sorting = !sorting;
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
    }    //----------------------------------------------------------------------------------------------------------
    private void add_food_to_view(List <String> item_to_show) {
        for(String item : item_to_show){
            View food_view = getLayoutInflater().inflate(R.layout.food_view, null, false);
            add_food_name_to_layout(food_view, item);
        }
    }
    private void add_one_food_to_view(String item_to_show) {

        View food_view = getLayoutInflater().inflate(R.layout.food_view, null, false);
        add_food_name_to_layout(food_view, item_to_show);

    }

    private void add_food_name_to_layout(View foodView, String item) {
        TextView item_name = foodView.findViewById(R.id.food_name_view_id);
        TextView add_ingredient = foodView.findViewById(R.id.add_ingredient_to_food_id);
        TextView delete_food_view = foodView.findViewById(R.id.remove_food_id);
        item_name.setText(item);
        add_ingredient.setOnLongClickListener(v -> {
            sdb.add_ingredients_to_food(item_name.getText().toString(), day_select.getText().toString());
            Toast.makeText(NewMainActivity.this, "Ingredient added!", Toast.LENGTH_SHORT).show();
            return false;
        });
        item_name.setOnLongClickListener(v -> {

            list_view.removeView(foodView);
            sdb.remove_from_food_table(item_name.getText());
            return false;
        });
        delete_food_view.setOnClickListener(v -> {
            list_view.removeView(foodView);
            sdb.remove_from_food_table(item_name.getText().toString());
            Toast.makeText(NewMainActivity.this, "Food removed from the list!",Toast.LENGTH_LONG).show();
        });

        item_name.setOnClickListener(v -> {
            for (int j = 0; j < list_view.getChildCount(); j++) {
                list_view.getChildAt(j).findViewById(R.id.food_name_view_id).setBackgroundResource(R.drawable.rounded_corner);
            }
            v.findViewById(R.id.food_name_view_id).setBackgroundResource(R.drawable.transparent);
            global_food_view = v.findViewById(R.id.food_name_view_id);


        });
        item_name.setOnLongClickListener(v -> {
            day_food_to_show.clear();
            reset_btn_color();
            if(!search_item.getText().toString().isEmpty() && (!is_a_number(search_item.getText().toString())))
            {
                sdb.update_food_name(search_item.getText().toString(), item_name.getText().toString());
                item_name.setText(search_item.getText());
            }

            List<NameStatusPair> search_items = new ArrayList<>();
            list_view.removeAllViews();
            String day_food = sdb.get_ingred_for_food(item_name.getText().toString());
            List<NameStatusPair> items;
            try {
                items = sdb.getItemsSortedByUsage();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String[]ingredients = day_food.split("-");
            for (String ingredient : ingredients) {
                for (NameStatusPair item1 : items) {
                    if (item1.getName().equals(ingredient)) {
                        search_items.add(item1);
                    }
                }
            }
            add_one_food_to_view(item_name.getText().toString());
            add_items_to_view(search_items);
            return false;
        });
        list_view.addView(foodView);

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
        item_used.setOnLongClickListener(v -> {
            item.setUsage("0");
            item_used.setText(item.getUsage());
            sdb.reset_usage(item.getName(), "0");
            Toast.makeText(NewMainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            need_to_be_incremented = true;
            return false;
        });
        item_pos.setOnClickListener(v -> change_view_status(itemView,  item, 0));
        item_pos.setOnLongClickListener(v -> {
            int pos = Integer.parseInt(item.getPos());
            String new_pos = ((pos + 1) > 9 ) ? "1" : (pos + 1)+ "";
            item.setPos(new_pos);
            sdb.update_position(item.getPos(), item.getName());
            item_pos.setText(item.getPos());
            return true;
        });
        item_interval.setOnClickListener(v -> {
            if(!search_item.getText().toString().isEmpty())
            {
                if(is_a_number(search_item.getText().toString()))
                {
                    sdb.update_interval( Integer.parseInt(search_item.getText().toString()), item.getName());
                    item.set_interval(search_item.getText().toString());
                    item_interval.setText(search_item.getText().toString());
                    num_of_days = 7;
                    Toast.makeText(NewMainActivity.this, "Interval updated", Toast.LENGTH_LONG).show();
                    search_item.setText("");
                }
            }
        });

        item_name.setOnLongClickListener(v -> {
            if(!search_item.getText().toString().isEmpty())
            {
                String new_name = search_item.getText().toString();
                sdb.update_item_name(new_name , item.getName());
                item.set_name(new_name);
                Toast.makeText(NewMainActivity.this, "Item name updated!", Toast.LENGTH_SHORT).show();
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

        item_used.setText(item.getUsage());
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

        TextView item_interval = itemView.findViewById(R.id.interval_id);//.setText(search_item.getText().toString() + " sek");
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
                        item_interval.setText(item.get_interval());
                        item_used.setText(item.getUsage());
                        item.setStatus("1");
                        sdb.update_status(unchecked_status, item.getName());

                    }
                    break;
                case "1":
                    item_name.setTextColor(Color.WHITE);
                    if(h_v_view == 1)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        item_name.setBackgroundResource(R.drawable.checked);
                        if(item.getDuration().equals("0"))
                        {
                            sdb.removeItem(item.getName());
                            Toast.makeText(NewMainActivity.this, "Item will be removed", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            sdb.increment_usage_count(item.getName());
                            int checked_status = 2;
                            sdb.update_status(checked_status, item.getName());
                            sdb.update_date(date, item.getName());
                            item.setStatus("2");
                            item_pos.setText(item.getPos());
                            item_name.setText(item.getName());
                            item_interval.setText(item.get_interval());
                            item_used.setText(item.getUsage());
                        }
                    }
                    else
                    {
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item.setStatus("0");
                        item_pos.setText(item.getPos());
                        item_name.setText(item.getName());
                        item_interval.setText(item.get_interval());
                        item_used.setText(item.getUsage());
                        sdb.update_status(no_status, item.getName());
                        sdb.set_priority(item.getName(), 0);
                    }
                    break;
                case "2":
                    if(h_v_view == 0)
                    {
                        item_name.setTextColor(Color.BLACK);
                        item_name.setBackgroundResource(R.drawable.unchecked);
                        sdb.update_status(unchecked_status, item.getName());
                        item.setStatus("1");
                        item_name.setText(item.getName());
                        item_interval.setText(item.get_interval());
                        item_pos.setText(item.getPos());
                        item_used.setText(item.getUsage());
                    }
                    else
                    {

                        item_name.setTextColor(Color.WHITE);
                        item_name.setBackgroundResource(R.drawable.transparent);
                        item_name.setText(item.getName());
                        item_interval.setText(item.get_interval());
                        item.setStatus("0");
                        item_pos.setText(item.getPos());
                        item_used.setText(item.getUsage());
                        sdb.update_status(no_status, item.getName());
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
    //----------------------------------------------------- OnClick --------------------------------------
    @Override
    public void onClick(View v) {


        List<NameStatusPair> search_items = new ArrayList<>();
        List<NameStatusPair >items;
        String pos = getString(v);
        list_view.removeAllViews();
        try {
            items = sdb.getItemsSortedByUsage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(isWeekday(pos))
        {
            if (search_for_day_in_list(day_food_to_show, pos)){ day_food_to_show.remove(pos); }
            else{day_food_to_show.add(pos);}
            search_items =  add_food_to_the_list(items);
            sd_num_view.setText(String.format("%d",search_items.size()));
            sd_num_view.setBackgroundResource(R.drawable.checked);
        }
        else
        {
            day_food_to_show.clear();
            reset_btn_color();
            search_item.setText("");
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
            v.setBackgroundResource(R.drawable.checked);
            sd_num_view.setText(String.format("%d",search_items.size()));
            sd_num_view.setBackgroundResource(R.drawable.checked);
            add_items_to_view(search_items);
        }

        sc_view.fullScroll(ScrollView.FOCUS_UP);
        InputMethodManager imm = (InputMethodManager) NewMainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);

    }

    private List<NameStatusPair> add_food_to_the_list(List<NameStatusPair >items) {
        List<String> day_food;
        String[] ingredients;
        List<NameStatusPair> search_items = new ArrayList<>();
        for(String day: day_food_to_show)
        {
            day_food = sdb.get_days_food(day);
            if(day_food != null) {
                ingredients = day_food.get(1).split("-");
                for (String ingredient : ingredients) {
                    for (NameStatusPair item : items) {
                        if (item.getName().equals(ingredient)) {
                            search_items.add(item);
                        }
                    }
                }
                add_one_food_to_view(day + ": " + day_food.get(0));
                add_items_to_view(search_items);
            }
            search_items.clear();
        }
        return search_items;
    }


    private boolean search_for_day_in_list(List<String> dayFoodToShow, String pos) {
        for(int i = 0; i < dayFoodToShow.size(); i++)
        {
            if(dayFoodToShow.get(i).equals(pos))
            {
                return true;
            }
        }
        return false;
    }

    private static final Set<String> WEEKDAYS = new HashSet<>(Arrays.asList(
            "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"
    ));

    // Method to check if a string is a weekday name
    public static boolean isWeekday(String day) {
        if (day == null || day.trim().isEmpty()) {
            return false;
        }
        // Check if the trimmed, case-normalized string is in the WEEKDAYS set
        return WEEKDAYS.contains(day.trim().toLowerCase().substring(0, 1).toUpperCase() + day.trim().toLowerCase().substring(1));
    }

    private @NonNull String getString(View v) {
        int i = v.getId();
        String pos = "";
        if(i == R.id.one_con_id)
        {
            pos = "1";
        }
        else if(i == R.id.two_con_id)
        {

            pos = "2";
        }
        else if(i == R.id.three_con_id)
        {

            pos = "3";
        }
        else if(i == R.id.four_con_id)
        {

            pos = "4";
        }
        else if(i == R.id.five_con_id)
        {
            pos = "5";
        }
        else if(i == R.id.six_con_id)
        {
            pos = "6";
        }
        else if(i == R.id.seven_con_id)
        {

            pos = "7";
        }
        else if(i == R.id.eight_con_id)
        {

            pos = "8";
        }
        else if(i == R.id.nine_con_id)
        {

            pos = "9";
        }
        else if(i == R.id.con_mon_id)
        {
            pos = "Mo";
        }
        else if(i == R.id.con_tus_id)
        {

            pos = "Tu";
        }
        else if(i == R.id.con_wed_id)
        {

            pos = "We";
        }
        else if(i == R.id.con_thu_id)
        {

            pos = "Th";
        }
        else if(i == R.id.con_fri_id)
        {
            pos = "Fr";
        }
        else if(i == R.id.con_sat_id)
        {
            pos = "Sa";
        }
        else if(i == R.id.con_sun_id)
        {

            pos = "Su";
        }
        if (v.getBackground().getConstantState() == getResources().getDrawable(R.drawable.checked).getConstantState())
        {
            v.setBackgroundResource(R.drawable.transparent);
        }
        else {

            v.setBackgroundResource(R.drawable.checked);
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
        monday_view.setBackgroundResource(R.drawable.transparent);
        tuesday_view.setBackgroundResource(R.drawable.transparent);
        wednesday_view.setBackgroundResource(R.drawable.transparent);
        thursday_view.setBackgroundResource(R.drawable.transparent);
        friday_view.setBackgroundResource(R.drawable.transparent);
        saturday_view.setBackgroundResource(R.drawable.transparent);
        sunday_view.setBackgroundResource(R.drawable.transparent);
    }

    @Override
    public boolean onLongClick(View v) {
        String day = getString(v);
        sdb.set_food_to_day(global_food_view.getText().toString(), day);
        Toast.makeText(NewMainActivity.this, "Food saved on "+ day + "! ", Toast.LENGTH_LONG).show();

        return false;
    }

    private void get_random_day_food()
    {
        day_food_to_show.clear();
        day_food_to_show.add("Mo");
        day_food_to_show.add("Tu");
        day_food_to_show.add("We");
        day_food_to_show.add("Th");
        day_food_to_show.add("Fr");
        day_food_to_show.add("Sa");
        day_food_to_show.add("Su");
        List<FoodDay> food_list = sdb.get_food_names();
        for (String s : day_food_to_show)
        {
            Collections.shuffle(food_list);
            for(FoodDay food_day: food_list)
            {

                //FoodDay food_name = food_list.get(rand.nextInt(food_list.size()));
                if((food_day.getDay().equals("Day") || food_day.getDay().equals(s)) && (food_day.getUsage() == 0))
                {
                    sdb.set_food_to_day(food_day.getFoodName(), s);
                    sdb.update_last_week_usage(food_day.getFoodName(), 1);
                    food_list.remove(food_day);
                    break;
                }

            }
        }
        for(FoodDay food : food_list)
        {
            sdb.update_last_week_usage(food.getFoodName(), 0);
        }
        List<NameStatusPair> items;
        try {
            items = sdb.getItemsSortedByUsage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        list_view.removeAllViews();
        add_food_to_the_list(items);
        Toast.makeText(NewMainActivity.this, "Random food is chosen!", Toast.LENGTH_LONG).show();


    }





}
