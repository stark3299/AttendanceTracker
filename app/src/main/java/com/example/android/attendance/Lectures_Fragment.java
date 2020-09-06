package com.example.android.attendance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Lectures_Fragment extends Fragment implements View.OnClickListener {
    TextInputEditText sub_name, sub_code, percentage, text_date;
    MaterialTextView mon, tue, wed, thu, fri, sat;
    FloatingActionButton add_recycler;
    Drawable selected, unselected;
    ArrayList<Integer> days_selected = new ArrayList<>();
    ArrayList<Integer> days_name = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button start_date;
    String current_date;
    int mYear, mMonth, mDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        add_recycler = Objects.requireNonNull(getView()).findViewById(R.id.fab_lectures);
        days_name.add(0, Calendar.MONDAY);
        days_name.add(1, Calendar.TUESDAY);
        days_name.add(2, Calendar.WEDNESDAY);
        days_name.add(3, Calendar.THURSDAY);
        days_name.add(4, Calendar.FRIDAY);
        days_name.add(5, Calendar.SATURDAY);
        return inflater.inflate(R.layout.fragment_lectures,container,false);
    }
    void dialog(){
        LayoutInflater inflator = getLayoutInflater();
        View dialog_layout = inflator.inflate(R.layout.add_dialogbox, null);
        sub_name = dialog_layout.findViewById(R.id.sub_name);
        sub_code = dialog_layout.findViewById(R.id.sub_code);
        start_date = dialog_layout.findViewById(R.id.btn_date);
        text_date = dialog_layout.findViewById(R.id.text_date);
        percentage = dialog_layout.findViewById(R.id.percentage);
        unselected = getResources().getDrawable(R.drawable.rounded_days);
        selected = getResources().getDrawable(R.drawable.selected_days);
        mon = dialog_layout.findViewById(R.id.mon);
        tue = dialog_layout.findViewById(R.id.tue);
        wed = dialog_layout.findViewById(R.id.wed);
        thu = dialog_layout.findViewById(R.id.thu);
        fri = dialog_layout.findViewById(R.id.fri);
        sat = dialog_layout.findViewById(R.id.sat);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userEmail = mUser.getEmail();
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        current_date = format.format(today);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("ADD SUBJECT");
        dialog.setView(dialog_layout);
        dialog.setCancelable(false);
        dialog.setNegativeButton("CANCEL", (dialogInterface, i) -> Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show());
        dialog.setPositiveButton("ADD", (dialogInterface, i) -> {
            if(check_field()){
                days(mon,0); days(tue, 1); days(wed, 2); days(thu, 3); days(fri ,4); days(sat, 5);
                int total_class = Calculate_days.calculate_classes(text_date.toString(), current_date, days_selected);
                if(total_class == -1){
                    Toast.makeText(getContext(), "Error in start date", Toast.LENGTH_SHORT).show();
                }
                else{
                    Dialog_FireStore.dialog_data(getContext(), sub_name.toString(), sub_code.toString(), percentage.toString(),
                            text_date.toString(), userEmail, current_date, total_class, days_selected);
                }
            }
            else{
                Toast.makeText(getContext(), "Fill up the details", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog1 =  dialog.create();
        dialog1.show();
    }
    Boolean check_field(){
        if (isEmpty(sub_name)) {
            sub_name.setError("Last name is required!");
            return false;
        }
        if (isEmpty(percentage)) {
            sub_name.setError("Last name is required!");
            return false;
        }
        if (isEmpty(text_date)) {
            sub_name.setError("Last name is required!");
            return false;
        }
        else{
            return true;
        }
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void days(MaterialTextView day, int no) {
        if(day.getBackground() == selected){
            days_selected.add(days_name.get(no));
        }
    }

    void changeStyle(MaterialTextView id){
        if(id.getBackground() ==  selected){
            id.setBackgroundDrawable(unselected);
        }
        else{
            id.setBackgroundDrawable(selected);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_date:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),
                        (view1, year, monthOfYear, dayOfMonth) -> text_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.fab_lectures:
                dialog();
                break;

            case R.id.mon:
                changeStyle(mon);
                break;

            case R.id.tue:
                changeStyle(tue);
                break;

            case R.id.wed:
                changeStyle(wed);
                break;

            case R.id.thu:
                changeStyle(thu);
                break;

            case R.id.fri:
                changeStyle(fri);
                break;

            case R.id.sat:
                changeStyle(sat);
                break;
        }
    }
}

