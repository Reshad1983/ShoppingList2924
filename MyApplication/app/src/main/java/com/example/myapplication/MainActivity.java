package com.example.myapplication;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.text.LineBreakConfig;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    int found = 0;
    TextView food_sum_view;
    TextView week_view;
    TextView snooze_btn;
    Spinner spinner;
    String position_of_item;
    ArrayAdapter<CharSequence> adapter;
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
    ScrollView sc_view;
    int num_of_days = 7;
    private StringBuilder list_of_ingredients;
    private TextView global_food_view;
    private TextView search_btn;
    boolean at_item;
    private List<String> day_food_to_show;
    String []item_names;
    ArrayAdapter<String> at_adapter;
    AutoCompleteTextView at_view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_layout);
        initialize();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position_of_item = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                position_of_item = "1";
            }
        });
        List<NameStatusPair> items = sdb.getItemsSortedByUsage();

        item_names = new String[items.size()];
        for(int i = 0; i < item_names.length; i++){
            item_names[i] = items.get(i).getName();
        }
        at_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item_names);
        at_view = findViewById(R.id.search_id);
        at_view.setAdapter(at_adapter);
        at_view.setOnItemClickListener((parent, view, position, id) -> {
            List<NameStatusPair> search_items = new ArrayList<>();
            for (NameStatusPair item : items) {
                if (item.getName().trim().toLowerCase().contains(parent.getItemAtPosition(position).toString().trim().toLowerCase())) {
                    found = 1;
                    search_items.add(item);
                }
            }
            refresh();
            at_view.setText("");
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(at_view.getWindowToken(), 0);
            add_items_to_view(search_items);
            if (found == 0) {
                at_view.setHint("Not found!");
            }else {
                at_view.setHint("Found!");
                found = 0;
            }
            at_item = true;
            search_items.clear();
        });
        add_items_to_view(items);
        sort_list_view("-1");
        if(!isMyServiceRunning(CheckDataBase.class)){
            startForegroundService(new Intent(this, CheckDataBase.class));
        }
    }
    @SuppressWarnings("deprecation")
    private boolean isMyServiceRunning(Class<?>serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
    private void sort_list_view(String pos) {
     List<NameStatusPair> items = sdb.sort_buy_list(pos);
        buy_list.removeAllViews();
        handler.postDelayed(() -> {
            buy_list.removeAllViews();
            List<NameStatusPair> list1 = sdb.sort_buy_list("-1");
            add_items_to_view(list1);
        },300);
    }
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    private void add_items_to_view(List <NameStatusPair> item_to_show) {
        for(NameStatusPair item : item_to_show){
            View itemView = getLayoutInflater().inflate(R.layout.item_layout, null, false);
            addItemToLayout(itemView, item, -1);
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
        List<NameStatusPair> items_to_check_status = sdb.getItemsSortedByPosition();
        add_ingredient.setOnLongClickListener(v -> {
            if(at_view.getText().toString().isEmpty()){
                String ingredients = add_ingredient.getText().toString();
                String [] sep_ingredients = ingredients.split("\n");
                for (String sepIngredient : sep_ingredients) {
                    for (NameStatusPair item_to_check : items_to_check_status) {
                        if (item_to_check.getName().equals(sepIngredient.trim())) {
                            sdb.update_status(1, item_to_check.getName());
                        }
                    }
                }
            }
            else{
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
            }
            return false;
        });
        item_name.setText(item);
        item_name.setOnLongClickListener(v -> {
            item_name.setBackgroundResource(R.drawable.unchecked);
            sdb.set_food_to_day(item_name.getText().toString(), day_select.getText().toString());
            Toast.makeText(this, "Food added to day!", Toast.LENGTH_LONG).show();
            handler.postDelayed(() -> {
                refresh();
                show_days_meal();
            },800);
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
    public void addItemToLayout(View itemView, NameStatusPair item, int pos){
        TextView item_name = itemView.findViewById(R.id.con_item_id);
        TextView item_interval = itemView.findViewById(R.id.con_interval_id);
        TextView item_use = itemView.findViewById(R.id.usa_id);
        CheckBox cb = itemView.findViewById(R.id.checkBox_id);
        TextView int_view = itemView.findViewById(R.id.interval_id);
        TextView pos_view = itemView.findViewById(R.id.pos_id);
        int_view.setText(item.get_interval());
        int_view.setOnClickListener(v -> {
            item_interval.setTextColor(Color.BLACK);
            item_interval.setText("Interval -->>" );
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item_interval.setTextColor(Color.BLACK);
                    item_interval.setText(item.get_date());
                }
            },1000);
        });
        pos_view.setText(item.getPos());
        item_interval.setOnClickListener(v -> {
            if(!at_view.getText().toString().isEmpty()){
                if(is_a_number(at_view.getText().toString())) {
                    sdb.update_interval( Integer.parseInt(at_view.getText().toString()), item.getName());
                    item.set_interval(at_view.getText().toString());
                    item_interval.setText(item.get_date()+ " ");
                    num_of_days = 7;
                    Toast.makeText(MainActivity.this, "Interval updated", Toast.LENGTH_LONG).show();
                    at_view.setText("");
                }
            }
        });
        item_name.setOnLongClickListener(v -> {
            if(!at_view.getText().toString().isEmpty()){
                String new_name = at_view.getText().toString();
                sdb.update_item_name(new_name , item.getName());
                item.set_name(new_name);
                Toast.makeText(MainActivity.this, "Item name updated!", Toast.LENGTH_SHORT).show();
                item_name.setText(item.getName());
            }
            else{
                if(cb.isChecked()){
                    sdb.removeItem(item_name.getText().toString());
                    list_view.removeView(itemView);
                }else {
                    sdb.update_position(position_of_item,item_name.getText().toString());
                    item.setPos(position_of_item);
                    Toast.makeText(MainActivity.this, "Position updated!", Toast.LENGTH_LONG).show();
                    List<NameStatusPair> item_pos_update = new ArrayList<>();
                    if(item.getStatus().equals("1")){
                        buy_list.removeView(itemView);
                    }else{
                        list_view.removeView(itemView);
                    }
                        item_pos_update.add(item);
                        add_items_to_view(item_pos_update);
                        item_pos_update.clear();
                }
            }
            at_view.setText("");
            return true;
        });
        item_name.setOnClickListener(v -> {
            if(item.getStatus().equals("0")){
                itemView.setBackgroundResource(R.drawable.unchecked);
                list_view.removeView(itemView);
                item.setStatus("1");
                buy_list.removeAllViews();
                sdb.update_status(1, item.getName());
                View vItemView = getLayoutInflater().inflate(R.layout.item_layout, null, false);
                addItemToLayout(vItemView, item, 0);
               handler.postDelayed(() -> {
               List<NameStatusPair> at_items_to_insert = sdb.getItemsSortedByUsage();
               if(at_item){
                   at_item = false;
                   add_items_to_view(at_items_to_insert);
                   sort_list_view("-1");
               }
               else{
                   sort_list_view( "-1");
               }
               }, 400);

            }
            else if(item.getStatus().equals("1")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Object[] setting = get_correct_date();
                if((boolean)setting[0]){
                    Date lDate = new Date();
                    boolean null_date = false;
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
                    long difference = Math.abs(((Date)setting[1]).getTime() - lDate.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    if((int)differenceDates >= 7 || null_date){
                        sdb.update_interval((int)differenceDates, item.getName());
                        item.set_interval((int)differenceDates + "");
                        Toast.makeText(MainActivity.this, "Interval: " + (int)differenceDates, Toast.LENGTH_LONG).show();
                    }
                    sdb.update_date((String)setting[2], item_name.getText().toString());
                    item.set_date((String)setting[2]);

                    }
                else{
                        Toast.makeText(MainActivity.this, "Usage incremented!", Toast.LENGTH_LONG).show();
                    }
                    item.setStatus("0");
                    item.setUsage(Integer.parseInt(item.getUsage()) + 1 + "");
                    sdb.update_status(0, item.getName());
                    sdb.increment_usage_count(item.getName());
                    if(!(boolean)setting[0]){
                        int_view.setBackgroundResource(R.drawable.checked);
                    }else {
                        itemView.setBackgroundResource(R.drawable.checked);
                    }
                    handler = new Handler();
                boolean finalUpdate_last_buy = (boolean)setting[0];
                handler.postDelayed(() -> {
                        if(!finalUpdate_last_buy){
                            int_view.setBackgroundResource(R.drawable.rounded_corner2);
                        }else {
                            itemView.setBackgroundResource(R.drawable.bordered_transparent);
                        }
                        if(itemView.getParent() != null){
                            ((ViewGroup)itemView.getParent()).removeView(itemView);
                        }
                        buy_list.removeView(itemView);
                        View mitemView = getLayoutInflater().inflate(R.layout.item_layout, null, false);
                        addItemToLayout(mitemView, item, -1);
                    }, 500);
            }
        });
        item_name.setText(item.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            item_name.setLineBreakStyle(LineBreakConfig.LINE_BREAK_STYLE_LOOSE);
        }
        item_interval.setText(item.get_date());
        item_interval.setOnClickListener(v -> {
            if (item.getStatus().equals("1")) {
                sdb.update_status(0, item.getName());
                item.setStatus("0");
                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                buy_list.removeView(itemView);
                View mitemView = getLayoutInflater().inflate(R.layout.item_layout, null, false);
                addItemToLayout(mitemView, item, -1);
            }

        });
            item_interval.setOnLongClickListener(v -> {
                Object[] objects = get_correct_date();
                String date_string = (String)objects[2];
                sdb.update_status(0, item.getName());
                sdb.update_date(date_string, item_name.getText().toString());
                item.setStatus("0");
                item.set_date(date_string);
                buy_list.removeView(itemView);
                View mitemView = getLayoutInflater().inflate(R.layout.item_layout, null, false);
                addItemToLayout(mitemView, item, -1);
                Toast.makeText(MainActivity.this, "Item snoozed", Toast.LENGTH_LONG).show();
                return false;
            });
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
                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                list_view.addView(itemView, Integer.parseInt(item.getPrio()));
                item.set_prio("0");
                break;
            case "1":

                itemView.setBackgroundResource(R.drawable.bordered_transparent);
                item_interval.setBackgroundResource(R.drawable.unchecked);
                if(pos == -1){
                    buy_list.addView(itemView);
                }
                else {
                    buy_list.addView(itemView, 0);
                }
                break;
        }
    }

    private Object[] get_correct_date() {
        Object [] return_array = new Object[3];
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        Date date = new Date();
        String date_string = sdf.format(date);
        long l;
        Calendar c = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (date_string) {
            case "fre":
            case "Fri":
                return_array[0] = true;
                break;
            case "Sun":
            case "sön": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, -2); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = true;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }
            case "Thu":
            case "tors": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, 1); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = true;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }

            case "Sat":
            case "lör": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, -1); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = true;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }
            case "Wed":
            case "ons": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, 2); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = false;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }
            case "tis":
            case "Tue": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, 3); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = false;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }
            case "Mon":
            case "mån": {
                c.setTime(date); // Using today's date
                c.add(Calendar.DATE, -3); // Adding 5 days
                l = c.getTimeInMillis();
                return_array[0] = false;
                return_array[1] = new Date(l);
                return_array[2] = sdf.format(c.getTime());
                break;
            }
        }
        return return_array;
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
            snooze_btn.setBackgroundResource(R.drawable.checked);
            handler.postDelayed(() -> snooze_btn.setBackgroundResource(R.drawable.hole_view), 500);
            for(int i = 0; i < buy_list.getChildCount(); i++){
                View item_view = buy_list.getChildAt(i);
                CheckBox cb = item_view.findViewById(R.id.checkBox_id);
                TextView item_name = item_view.findViewById(R.id.con_item_id);
                if(cb.isChecked()){
                    String date_string = (String)get_correct_date()[2];
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
        else if(btn_id == R.id.food_list_id){
            food_sum_view.setText(sdb.get_number_of_foods() + "");
            food_sum_view.setBackgroundResource(R.drawable.checked);
            refresh();
            List<FoodDay> food_list = sdb.get_food_names();
            List<String> food_name_list = new ArrayList<>();
            for(FoodDay food : food_list) food_name_list.add(food.getFoodName());
            add_food_to_view(food_name_list);
            handler.postDelayed(() -> food_sum_view.setBackgroundResource(R.drawable.hole_view), 300);
        } else if (btn_id == R.id.day_id) {
            set_text_to_day_textview(day_select);

        } else if (btn_id == R.id.search_btn) {
            search_btn.setBackgroundResource(R.drawable.checked);
            handler.postDelayed(() -> search_btn.setBackgroundResource(R.drawable.hole_view), 500);
            String search_text = at_view.getText().toString();
            List<NameStatusPair> items;
            refresh();
            if (search_text.isEmpty()) {
                sc_view.fullScroll(ScrollView.FOCUS_UP);
                if (sorting) {
                    items = sdb.getItemsSortedByPosition();
                } else {
                    items = sdb.getItemsSortedByUsage();
                }
                add_items_to_view(items);
                sort_list_view("-1");
                sorting = !sorting;
                at_view.setHint("SAR");
                items.clear();
            } else {
                    items = sdb.getItemsSortedByUsage();
                for (NameStatusPair item : items) {
                    if (item.getName().trim().toLowerCase().contains(search_text.trim().toLowerCase())) {
                        found = 1;
                        search_items.add(item);
                    }
                }
                at_view.setText("");
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(at_view.getWindowToken(), 0);
                add_items_to_view(search_items);
                if (found == 0) {
                    at_view.setHint("Not found!");
                }else {
                    at_view.setHint("Found!");
                    found = 0;
                }
                search_items.clear();
            }

        }
        else if(btn_id == R.id.con_sun_id){
            refresh();
            show_days_meal();
        }
        else{
            List<NameStatusPair >items;
            String pos = getString(v);
            refresh();
            at_view.setText("");
                items = sdb.getItemsSortedByUsage();
            for (NameStatusPair item_pos_search : items) {
                if (item_pos_search.getPos().equals(pos)) {
                    search_items.add(item_pos_search);
                }
            }
            v.setBackgroundResource(R.drawable.checked);
            add_items_to_view(search_items);
            sc_view.fullScroll(ScrollView.FOCUS_UP);
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(at_view.getWindowToken(), 0);

        }
    }

    private void set_text_to_day_textview(TextView daySelect) {
        String selected_day_string = daySelect.getText().toString();
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
            if(btn_id == R.id.con_sun_id){
            refresh();
            list_of_ingredients = new StringBuilder();
            if(at_view.getText().toString().isEmpty()){
                get_random_day_food();
                Toast.makeText(MainActivity.this, "Food for week added!", Toast.LENGTH_SHORT).show();
            }
            else if(is_a_number(at_view.getText().toString()))            {
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
                sdb.add_food(at_view.getText().toString(), list_of_ingredients.toString(), day_select.getText().toString());
                food_sum_view.setText(sdb.get_food_names().size()+"");
                at_view.setText("");
                Toast.makeText(MainActivity.this, "New food added!", Toast.LENGTH_SHORT).show();
            }
        }else if (btn_id == R.id.search_btn) {
            //Reset all status if nothing enter
            String search_text = at_view.getText().toString();
            if (search_text.isEmpty()) {
                sdb.reset_status();
                Toast.makeText(MainActivity.this, "Status reset!", Toast.LENGTH_LONG).show();
            } else{
                reset_btn_color();
                sdb.add_item(search_text, Integer.parseInt(position_of_item), 1, (String)get_correct_date()[2]);
                Toast.makeText(this, "Item added!!", Toast.LENGTH_LONG).show();
            }
            refresh();
            List<NameStatusPair> items = sdb.getItemsSortedByUsage();
            add_items_to_view(items);
                at_view.setText("");
                at_view.setHint("SAR");
                List<String> names = get_item_as_string(items);


        }
        else{
            String day = day_select.getText().toString();
            reset_btn_color();
            sdb.set_food_to_day(global_food_view.getText().toString(), day);
            Toast.makeText(MainActivity.this, "Food saved on "+ day + "! ", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    List<String> get_item_as_string(List<NameStatusPair> item_list){
        List<String> return_array = new ArrayList<>();
        for(NameStatusPair item : item_list){
            return_array.add(item.getName());
        }
        return return_array;
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
    public void update_data(List<String> item_array){
        at_adapter.clear();
        if(item_array != null){
            for(String item_name : item_array){
                at_adapter.insert(item_name, at_adapter.getCount());
            }
        }
        at_adapter.notifyDataSetChanged();
    }
    private void initialize(){
        handler = new Handler();
        list_view = findViewById(R.id.con_item_view);
        snooze_btn = findViewById(R.id.snooze_id);
        buy_list = findViewById(R.id.con_item_view2);
        food_sum_view = findViewById(R.id.food_list_id);
        sdb = new DatabaseHelper(MainActivity.this);
        at_view = findViewById(R.id.search_id);
        sc_view = findViewById(R.id.con_sc_id);
        spinner = findViewById(R.id.spinner_id);
        search_btn = findViewById(R.id.search_btn);
        day_select = findViewById(R.id.day_id);
        week_view = findViewById(R.id.con_sun_id);
        day_select.setOnLongClickListener(this);
        week_view.setOnClickListener(this);
        week_view.setOnLongClickListener(this);
        day_food_to_show = new ArrayList<>();
        day_food_to_show.add("Mo");
        day_food_to_show.add("Tu");
        day_food_to_show.add("We");
        day_food_to_show.add("Th");
        day_food_to_show.add("Fr");
        day_food_to_show.add("Sa");
        day_food_to_show.add("Su");
        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.number_array , R.layout.spinner_list );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
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
        two_btn.setOnClickListener(this);
        three_btn.setOnClickListener(this);
        four_btn.setOnClickListener(this);
        five_btn.setOnClickListener(this);
        six_btn.setOnClickListener(this);
        seven_btn.setOnClickListener(this);
        eight_btn.setOnClickListener(this);
        nine_btn.setOnClickListener(this);
        snooze_btn.setOnClickListener(this);
        snooze_btn.setOnLongClickListener(this);
        day_select.setOnClickListener(this);
        food_sum_view.setOnClickListener(this);
        search_btn.setOnLongClickListener(this);
        search_btn.setOnClickListener(this);
        position_of_item = "1";
        at_item = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
