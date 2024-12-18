package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.text.LineBreakConfig;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    int found = 0;
    TextView sd_num_view;
    TextView food_sum_view;
    TextView week_view;
    TextView snooze_btn;
    TextView plus_btn;
    LinearLayout list_view;
    LinearLayout buy_list;
    TextView one_btn;
    TextView two_btn;
    TextView three_btn;
    TextView four_btn;
    TextView five_btn;
    TextView six_btn;
    TextView seven_btn;
    TextView eight_btn;
    TextView nine_btn;
    Handler handler ;
    TextView day_select;
    public DatabaseHelper sdb;
    boolean sorting = true;
    EditText search_item;
    ScrollView sc_view;
    int num_of_days = 7;
    private StringBuilder list_of_ingredients;
    private TextView global_food_view;
    private TextView search_btn;
    private List<String> day_food_to_show;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_main_layout);
        initialize();
        snooze_btn.setOnClickListener(this);
        day_select.setOnClickListener(this);
        food_sum_view.setOnClickListener(this);
        plus_btn.setOnLongClickListener(this);
        search_btn.setOnLongClickListener(this);
        search_btn.setOnClickListener(this);
        sd_num_view.setOnClickListener(this);
        List<NameStatusPair> items = sdb.getItemsSortedByUsage();
        add_items_to_view(items);
        sort_list_view("-1");
        startForegroundService(new Intent(this, CheckDataBase.class));
    }
    private void sort_list_view(String pos) {
        buy_list.removeAllViews();
        List<NameStatusPair> list = sdb.sort_buy_list(pos);
        add_items_to_view(list);
    }
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    private void add_items_to_view(List <NameStatusPair> item_to_show) {
        for(NameStatusPair item : item_to_show){
            View itemView = getLayoutInflater().inflate(R.layout.new_item_layout, null, false);
            addItemToLayout(itemView, item);
        }
    }    //----------------------------------------------------------------------------------------------------------
    private void add_food_to_view(List <String> item_to_show) {
        for(int i = 0; i < item_to_show.size(); i++){
            View food_view = getLayoutInflater().inflate(R.layout.food_ingredients_layout, null, false);
            add_food_name_to_layout(food_view, item_to_show.get(i), i);
        }
    }
    public void show_days_meal(){
        for(int i = 0; i < day_food_to_show.size(); i++){
            View food_view = getLayoutInflater().inflate(R.layout.food_ingredients_layout, null, false);
            TextView item_name = food_view.findViewById(R.id.food_name_id);
            TextView day_view = food_view.findViewById(R.id.day_view_id);
            TextView add_ingredient = food_view.findViewById(R.id.food_ingredients_id);
            List<String> get_days_food = sdb.get_days_food(day_food_to_show.get(i));
            day_view.setText(day_food_to_show.get(i));
            item_name.setText(get_days_food.get(0));
            String[]ingredients = get_days_food.get(1).split("-");
            for (int k = 0; k < ingredients.length; k++) {
                if(!ingredients[k].isEmpty()){
                    if(k != (ingredients.length - 1)){
                        add_ingredient.append(ingredients[k] +"\n");
                    }
                    else{
                        add_ingredient.append(ingredients[k]);
                    }
                }
            }
            if(day_food_to_show.get(i).equals("Mo") || day_food_to_show.get(i).equals("Fr") || day_food_to_show.get(i).equals("We") || day_food_to_show.get(i).equals("Su")){
                list_view.addView(food_view);
            }
            else {
                buy_list.addView(food_view);
            }
        }
    }

    private void add_food_name_to_layout(View foodView, String item, int i) {
        TextView item_name = foodView.findViewById(R.id.food_name_id);
        TextView add_ingredient = foodView.findViewById(R.id.food_ingredients_id);
        add_ingredient.setOnLongClickListener(v -> {
            List<NameStatusPair> items_to_check_status = sdb.getItemsSortedByPosition();
            StringBuilder new_ingredients = new StringBuilder();
            for(NameStatusPair item1 : items_to_check_status){
                if(item1.getStatus().equals("1")){
                    if(new_ingredients.length() == 0){
                        new_ingredients.append(item1.getName());
                    }
                    else{
                        new_ingredients.append("-").append(item1.getName());
                    }
                }
            }
            sdb.add_ingredients_to_food(item_name.getText().toString(), new_ingredients.toString());
            refresh();
            List<FoodDay> food_list = sdb.get_food_names();
            List<String> food_name_list = new ArrayList<>();
            for(FoodDay food : food_list) food_name_list.add(food.getFoodName());
            add_food_to_view(food_name_list);
            Toast.makeText(MainActivity.this, "New ingredients added!", Toast.LENGTH_LONG).show();
            return false;
        });
        item_name.setText(item);
        item_name.setOnClickListener(v -> {
            item_name.setBackgroundResource(R.drawable.unchecked);
            sdb.set_food_to_day(item_name.getText().toString(), day_select.getText().toString());
            Toast.makeText(this, "Food added to day!", Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    list_view.removeAllViews();
                    buy_list.removeAllViews();
                    show_days_meal();
                }
            },800);

        });
        item_name.setOnLongClickListener(v -> {
            String day_food = sdb.get_ingred_for_food(item_name.getText().toString());
            String[]ingredients = day_food.split("-");
            for (String ingredient : ingredients) {
                if(!ingredient.isEmpty()){
                   sdb.update_status(1, ingredient);
                }
            }
            Toast.makeText(MainActivity.this, "Ingredients are unchecked!", Toast.LENGTH_LONG).show();
            return false;
        });
        String day_food = sdb.get_ingred_for_food(item_name.getText().toString());
            String[]ingredients = day_food.split("-");
            for (int k = 0; k < ingredients.length; k++) {
                if(!ingredients[k].isEmpty()){
                    if(k != (ingredients.length - 1)){
                        add_ingredient.append(ingredients[k] +"\n");
                    }
                    else{
                        add_ingredient.append(ingredients[k]);
                    }
                }
            }
            if((i + 1) % 2 == 0){
                list_view.addView(foodView);
            }
            else {
                buy_list.addView(foodView);
            }

    }

    private void refresh() {
        list_view.removeAllViews();
        buy_list.removeAllViews();
    }
/*.........................................................................................................................
...........................................................................................................................
 */
    public void addItemToLayout(View itemView, NameStatusPair item){
        TextView item_name = itemView.findViewById(R.id.con_item_id);
        TextView item_interval = itemView.findViewById(R.id.con_interval_id);
        TextView item_use = itemView.findViewById(R.id.con_usa_id);
        item_interval.setOnClickListener(v -> {
            if(!search_item.getText().toString().isEmpty()){
                if(is_a_number(search_item.getText().toString())) {
                    sdb.update_interval( Integer.parseInt(search_item.getText().toString()), item.getName());
                    item.set_interval(search_item.getText().toString());
                    item_interval.setTextColor(Color.BLACK);
                    item_interval.setText(item.get_date()+ " ");
                    num_of_days = 7;
                    Toast.makeText(MainActivity.this, "Interval updated", Toast.LENGTH_LONG).show();
                    search_item.setText("");
                }
            }
        });
        item_name.setOnLongClickListener(v -> {
            if(!search_item.getText().toString().isEmpty()){
                String new_name = search_item.getText().toString();
                sdb.update_item_name(new_name , item.getName());
                item.set_name(new_name);
                Toast.makeText(MainActivity.this, "Item name updated!", Toast.LENGTH_SHORT).show();
                item_name.setText(item.getName());
            }
            else if(item.getStatus().equals("1")){
                sdb.increment_usage_count(item.getName());
                sdb.update_status(0, item.getName());
                item.setStatus("0");
                int usage = Integer.parseInt(item.getUsage()) + 1;
                item.setUsage(usage + "" );
                handler = new Handler();
                handler.postDelayed(() -> {
                    itemView.setBackgroundResource(R.drawable.bordered_transparent);
                    if(itemView.getParent() != null){
                        ((ViewGroup)itemView.getParent()).removeView(itemView);
                    }
                    buy_list.removeView(itemView);
                    View mitemView = getLayoutInflater().inflate(R.layout.new_item_layout, null, false);
                    addItemToLayout(mitemView, item);
                }, 500);
                Toast.makeText(MainActivity.this, "Usage updated!", Toast.LENGTH_SHORT).show();
            }
            else{

                sdb.removeItem(item_name.getText().toString());
                list_view.removeView(itemView);
            }
            search_item.setText("");
            return true;
        });
        item_name.setOnClickListener(v -> {
            if(item.getStatus().equals("0")){
                item_interval.setBackgroundResource(R.drawable.unchecked);
                sdb.update_status(1, item.getName());
                item.setStatus("1");
                item.set_prio(""+ list_view.indexOfChild(itemView));
                list_view.removeView(itemView);
                sort_list_view(item.getPos());
            }
            else if(item.getStatus().equals("1")){
                Date date = new Date();
                Date  lDate = new Date();
                boolean null_date = false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date_string = sdf.format(date);
                itemView.setBackgroundResource(R.drawable.checked);
                sdb.increment_usage_count(item.getName());
                sdb.update_status(0, item.getName());
                if(item.get_date() == null){
                    null_date = true;
                }
                else{
                    try {
                        lDate = sdf.parse(item.get_date());
                    } catch (ParseException e) {
                        lDate = new Date();
                    }
                }
                assert lDate != null;
                long difference = Math.abs(date.getTime() - lDate.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                if((int)differenceDates >= 7 || (null_date == true)){
                    sdb.update_interval((int)differenceDates, item.getName());
                    sdb.update_date(date_string, item_name.getText().toString());
                    item.set_interval((int)differenceDates + "");
                    Toast.makeText(MainActivity.this, "Interval: " + (int)differenceDates, Toast.LENGTH_LONG).show();
                    item_interval.setText(date_string);
                    item.set_date(date_string);
                }
                item.setStatus("0");
                handler = new Handler();
                handler.postDelayed(() -> {
                    itemView.setBackgroundResource(R.drawable.bordered_transparent);
                    if(itemView.getParent() != null){
                        ((ViewGroup)itemView.getParent()).removeView(itemView);
                    }
                    buy_list.removeView(itemView);
                    View mitemView = getLayoutInflater().inflate(R.layout.new_item_layout, null, false);
                    addItemToLayout(mitemView, item);
                }, 500);
            }
        });
        item_name.setText(item.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            item_name.setLineBreakStyle(LineBreakConfig.LINE_BREAK_STYLE_LOOSE);
        }
        item_interval.setTextColor(Color.BLACK);
        item_interval.setText(item.get_date());
        item_interval.setOnClickListener(v -> {
            if (item.getStatus().equals("1")) {
                sdb.update_status(0, item.getName());
                item.setStatus("0");
                item_name.setTextColor(Color.BLACK);
                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                buy_list.removeView(itemView);
                View mitemView = getLayoutInflater().inflate(R.layout.new_item_layout, null, false);
                addItemToLayout(mitemView, item);
            }

        });
            item_interval.setOnLongClickListener(v -> {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date_string = sdf.format(date);
                sdb.update_status(0, item.getName());
                sdb.update_date(date_string, item_name.getText().toString());
                Toast.makeText(MainActivity.this, "Item snoozed", Toast.LENGTH_LONG).show();
                item.setStatus("0");
                item.set_date(date_string);
                buy_list.removeView(itemView);
                View mitemView = getLayoutInflater().inflate(R.layout.new_item_layout, null, false);
                addItemToLayout(mitemView, item);
                return false;
            });
        item_use.setTextColor(Color.BLACK);
        item_use.setText(item.getUsage());
        String rare_buy_item = item.get_rare_item();
        if(rare_buy_item == null){
            item.set_as_rare("0");
            item_use.setBackgroundResource(R.drawable.bordered_transparent);
        }
        else if(item.get_rare_item().equals("1")){
            item_use.setBackgroundResource(R.drawable.unchecked);
        }
        item_use.setOnClickListener(v -> {
            if(item.get_rare_item().equals("0") ){
                item_use.setBackgroundResource(R.drawable.unchecked);
                sdb.set_as_rare_item(item.getName(), "1");
                item.set_as_rare("1");
                Toast.makeText(MainActivity.this, "Rare item", Toast.LENGTH_SHORT).show();
            }
            else{
                item_use.setBackgroundResource(R.drawable.bordered_transparent);
                sdb.set_as_rare_item(item.getName(), "0");
                item.set_as_rare("0");
                Toast.makeText(MainActivity.this, "Removed rare item", Toast.LENGTH_SHORT).show();
            }
        });
        switch (item.getStatus()) {
            case "0":
                item_name.setTextColor(Color.BLACK);
                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                list_view.addView(itemView, Integer.parseInt(item.getPrio()));
                item.set_prio("0");
                break;
            case "1":
                item_name.setTextColor(Color.BLACK);
                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                item_interval.setBackgroundResource(R.drawable.unchecked);
                buy_list.addView(itemView);
                break;
        }
    }

    private boolean is_a_number(String string) {
        char [] num = string.toCharArray();
        for(char c : num){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    //----------------------------------------------------- OnClick --------------------------------------
    @Override
    public void onClick(View v) {
        List<NameStatusPair> search_items = new ArrayList<>();
        reset_btn_color();
        int btn_id = v.getId();
        if(btn_id == R.id.snooze_id){
            for(int i = 0; i < buy_list.getChildCount(); i++){
                View item_view = buy_list.getChildAt(i);
                CheckBox cb = item_view.findViewById(R.id.checkBox_id);
                TextView item_name = item_view.findViewById(R.id.con_item_id);
                if(cb.isChecked()){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date_string = sdf.format(date);
                    sdb.update_status(0, item_name.getText().toString());
                    sdb.update_date(date_string, item_name.getText().toString());
                    NameStatusPair item = sdb.find_item_from_db(item_name.getText().toString());
                    search_items.add(item);
                    buy_list.removeView(item_view);
                    i--;
                }
            }
            if(!search_items.isEmpty()){
                add_items_to_view(search_items);
                Toast.makeText(MainActivity.this, "Item snoozed", Toast.LENGTH_LONG).show();
                search_items.clear();
            }
            else{
                Toast.makeText(MainActivity.this, "No item selected to snooze!", Toast.LENGTH_LONG).show();
            }
        }
        else if(btn_id == R.id.con_sort){
            refresh();
            List<NameStatusPair> items1;
            if (sorting) {
                    items1 = sdb.getItemsSortedByPosition();
            } else {
                    items1 = sdb.getItemsSortedByUsage();
            }
            add_items_to_view(items1);
            sorting = !sorting;
        }
        else if(btn_id == R.id.food_list_id){
            food_sum_view.setText(sdb.get_number_of_foods() + "");
            food_sum_view.setBackgroundResource(R.drawable.checked);
            refresh();
            List<FoodDay> food_list = sdb.get_food_names();
            List<String> food_name_list = new ArrayList<>();
            for(FoodDay food : food_list) food_name_list.add(food.getFoodName());
            add_food_to_view(food_name_list);
            handler.postDelayed(() -> food_sum_view.setBackgroundResource(R.drawable.hole_view), 300);
        } else if (btn_id == R.id.con_day_id) {
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
        } else if (btn_id == R.id.con_search_btn) {
            search_btn.setBackgroundResource(R.drawable.checked);
            handler.postDelayed(() -> search_btn.setBackgroundResource(R.drawable.hole_view), 300);
            String search_text = search_item.getText().toString();
            List<NameStatusPair> items;
            list_view.removeAllViews();
            buy_list.removeAllViews();
            if (search_text.isEmpty()) {
                sc_view.fullScroll(ScrollView.FOCUS_UP);
                    items = sdb.getItemsSortedByUsage();
                add_items_to_view(items);
                sort_list_view("-1");
                search_item.setHint("SAR");
                found = 1;
            }  else if ((search_text.trim().length() == 1) && (Integer.parseInt(search_text) < 10) && (Integer.parseInt(search_text) > 0)) {

                    items = sdb.getItemsSortedByUsage();
                for (NameStatusPair item : items) {
                    if (item.getPos().equals(search_text)) {
                        found = 1;
                        search_items.add(item);
                    }
                }
                add_items_to_view(search_items);

                sc_view.fullScroll(ScrollView.FOCUS_UP);
                search_item.setText("");
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);
            } else {
                    items = sdb.getItemsSortedByUsage();
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
                add_items_to_view(search_items);
            }
            if (found == 0) {
                search_item.setText("");
                search_item.setHint("Not found!");
            }
            search_items.clear();
        } else{
            List<NameStatusPair >items;
            String pos = getString(v);
            list_view.removeAllViews();
            buy_list.removeAllViews();
            if(pos.equals("week")){
                show_days_meal();
            }
            else{
                search_item.setText("");
                    items = sdb.getItemsSortedByUsage();
                for (NameStatusPair item_pos_search : items) {
                    if (item_pos_search.getPos().equals(pos)) {
                        search_items.add(item_pos_search);
                    }
                }
                v.setBackgroundResource(R.drawable.checked);
                add_items_to_view(search_items);
            }
            sc_view.fullScroll(ScrollView.FOCUS_UP);
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search_item.getWindowToken(), 0);

        }
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
        if(i == R.id.one_con_id){pos = "1";}
        else if(i == R.id.two_con_id){pos = "2";}
        else if(i == R.id.three_con_id){pos = "3";}
        else if(i == R.id.four_con_id){pos = "4";}
        else if(i == R.id.five_con_id){pos = "5";}
        else if(i == R.id.six_con_id){pos = "6";}
        else if(i == R.id.seven_con_id){pos = "7";}
        else if(i == R.id.eight_con_id){pos = "8";}
        else if(i == R.id.nine_con_id){pos = "9";}
        else if(i == R.id.con_sun_id){pos = "week";}
        if (v.getBackground().getConstantState() == getResources().getDrawable(R.drawable.checked).getConstantState()){
            v.setBackgroundResource(R.drawable.hole_view);}
        else {v.setBackgroundResource(R.drawable.checked);}
        return pos;
    }
    private void reset_btn_color()
    {
        one_btn.setBackgroundResource(R.drawable.hole_view);
        two_btn.setBackgroundResource(R.drawable.hole_view);
        three_btn.setBackgroundResource(R.drawable.hole_view);
        four_btn.setBackgroundResource(R.drawable.hole_view);
        five_btn.setBackgroundResource(R.drawable.hole_view);
        six_btn.setBackgroundResource(R.drawable.hole_view);
        seven_btn.setBackgroundResource(R.drawable.hole_view);
        eight_btn.setBackgroundResource(R.drawable.hole_view);
        nine_btn.setBackgroundResource(R.drawable.hole_view);
        week_view.setBackgroundResource(R.drawable.hole_view);
    }

    @Override
    public boolean onLongClick(View v) {
        int btn_id = v.getId();
        if(btn_id  == R.id.con_plus_id){
            list_of_ingredients = new StringBuilder();
            if(search_item.getText().toString().isEmpty()){
                list_view.removeAllViews();
                buy_list.removeAllViews();
                get_random_day_food();
                Toast.makeText(MainActivity.this, "Food for week added!", Toast.LENGTH_SHORT).show();
            }
            else if(is_a_number(search_item.getText().toString()))            {
                Toast.makeText(MainActivity.this, "Please enter food name!", Toast.LENGTH_SHORT).show();
            }
            else{
                List<NameStatusPair> food_items;
                    food_items = sdb.getItemsSortedByPosition();
                for(NameStatusPair item: food_items){
                    if(item.getStatus().equals("1")){
                        list_of_ingredients.append("-").append(item.getName());
                    }
                }
                //Here to add food name and ingredients to database
                sdb.add_food(search_item.getText().toString(), list_of_ingredients.toString(), day_select.getText().toString());
                food_sum_view.setText(sdb.get_food_names().size()+"");
                search_item.setText("");
                Toast.makeText(MainActivity.this, "New food added!", Toast.LENGTH_SHORT).show();
            }
        } else if (btn_id == R.id.con_search_id) {

            reset_btn_color();
            String search_text = search_item.getText().toString();
            String[] search_name_pos_duration = search_text.split("\\s+");
            list_view.removeAllViews();
            buy_list.removeAllViews();
            //Reset all status if nothing entered
            if (search_text.isEmpty()) {
                sdb.reset_status();
                Toast.makeText(MainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            }// If user entered just a number then an item interval should be updated
            else if (is_a_number(search_text)) {
                Toast.makeText(this, "Press the view", Toast.LENGTH_LONG).show();
                search_item.setText("");
            } else{
                int pos = 0;
                int duration = 0;
                //If user entered 3 separated input in search box and two of them are numbers then it must be name, position and duration
                if ((search_name_pos_duration.length == 3) && (is_a_number(search_name_pos_duration[1]))){
                    try{
                        duration = Integer.parseInt(search_name_pos_duration[2]);
                        pos = Integer.parseInt(search_name_pos_duration[1]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Enter position or duration", Toast.LENGTH_LONG).show();
                    }
                    sdb.add_item(search_name_pos_duration[0], pos, duration);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
                    //If user entered 3 separated input in search box and the second one is also a text then it must be item name and position, duration must be 1
                } else if ((search_name_pos_duration.length == 3) && !(is_a_number(search_name_pos_duration[1]) && is_a_number(search_name_pos_duration[2]))){
                    try{
                        pos = Integer.parseInt(search_name_pos_duration[2]);
                    }
                    catch (Resources.NotFoundException e){
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    sdb.add_item(search_name_pos_duration[0] + " " + search_name_pos_duration[1], pos, 0);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
                    //If user entered 3 separated input in search box and two of them are numbers then it must be name, position and duration
                } else if (search_name_pos_duration.length == 4) {
                    try {
                        duration = Integer.parseInt(search_name_pos_duration[3]);
                        pos = Integer.parseInt(search_name_pos_duration[2]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    sdb.add_item(search_name_pos_duration[0] + " " + search_name_pos_duration[1], pos, duration);
                    Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();

                }else if (search_name_pos_duration.length == 2){
                    try{
                        pos = Integer.parseInt(search_name_pos_duration[1]);
                    } catch (Resources.NotFoundException e) {
                        Toast.makeText(this, "Position error", Toast.LENGTH_SHORT).show();
                    }
                    sdb.add_item(search_name_pos_duration[0], pos, 1);
                    Toast.makeText(this, "New Item added!", Toast.LENGTH_LONG).show();
                }
                search_item.setText("");
            }
            List<NameStatusPair> items = sdb.getItemsSortedByUsage();
            add_items_to_view(items);
        } else{
            String day = day_select.getText().toString();
            reset_btn_color();
            sdb.set_food_to_day(global_food_view.getText().toString(), day);
            Toast.makeText(MainActivity.this, "Food saved on "+ day + "! ", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    private void get_random_day_food(){

        List<FoodDay> food_list = sdb.get_food_names();
        for (String s : day_food_to_show){
            Collections.shuffle(food_list);
            for(FoodDay food_day: food_list){
                if((food_day.getDay().equals("Day") || food_day.getDay().equals(s)) && (food_day.getUsage() == 0)) {
                    sdb.set_food_to_day(food_day.getFoodName(), s);
                    sdb.update_last_week_usage(food_day.getFoodName(), 1);
                    food_list.remove(food_day);
                    break;
                }
            }
        }
        for(FoodDay food : food_list){
            sdb.update_last_week_usage(food.getFoodName(), 0);
        }
        list_view.removeAllViews();
        show_days_meal();
        Toast.makeText(MainActivity.this, "Random food is chosen!", Toast.LENGTH_LONG).show();
    }
    private void initialize(){
        handler = new Handler();
        list_view = findViewById(R.id.con_item_view);
        snooze_btn = findViewById(R.id.snooze_id);
        buy_list = findViewById(R.id.con_item_view2);
        food_sum_view = findViewById(R.id.food_list_id);
        sdb = new DatabaseHelper(MainActivity.this);
        search_item = findViewById(R.id.con_search_id);
        sc_view = findViewById(R.id.con_sc_id);
        search_btn = findViewById(R.id.con_search_btn);
        day_select = findViewById(R.id.con_day_id);
        sd_num_view = findViewById(R.id.con_sort);
        plus_btn = findViewById(R.id.con_plus_id);
        week_view = findViewById(R.id.con_sun_id);
        day_select.setOnLongClickListener(this);
        week_view.setOnClickListener(this);
        day_food_to_show = new ArrayList<>();
        day_food_to_show.add("Mo");
        day_food_to_show.add("Tu");
        day_food_to_show.add("We");
        day_food_to_show.add("Th");
        day_food_to_show.add("Fr");
        day_food_to_show.add("Sa");
        day_food_to_show.add("Su");
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
    }
}
