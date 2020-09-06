package com.example.android.attendance;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;

public class Dialog_FireStore {
    public Dialog_FireStore() {}
    static void dialog_data( Context context, String sub_name, String sun_code, String percentage,
                            String start_date, String userEmail, String current_date, int total_class, ArrayList<Integer> days ){
        FirebaseFirestore data = FirebaseFirestore.getInstance();
        Integer leaves_left = (total_class*75)/100;
        HashMap<String, Object> create_data =new HashMap<>();
        create_data.put("Subject Name", sub_name);
        create_data.put("Subject Code", sun_code);
        create_data.put("Percentage", percentage);
        create_data.put("Start Date", start_date);
        create_data.put("Class Days", FieldValue.arrayUnion(days));
        create_data.put("Leaves Left", leaves_left);
        create_data.put("Total Classes", total_class);
        create_data.put("Data Created On", current_date);
        create_data.put("Absents", 0);
        data.collection("Users").document(userEmail).collection("Subjects").add(create_data)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(context, "Subject Added Successfully", Toast.LENGTH_SHORT).show());
    }
}
