package com.example.rajatiit.admin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Intermediate extends AppCompatActivity {
    public static BatchDetail batchDetail;
    public static TeacherDetail teacherDetail;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        getAllUsersData();
        getInstituteData();
        getTimeTable();

        Intent intentBatch = new Intent(this, BatchMainActivity.class);
        Intent intentTeacher = new Intent(this, TeacherMainActivity.class);

        if(sp.getBoolean(Login.BATCH_LOGIN_CHECK, false)){
            startActivity(intentBatch);
            finish();
        }
        if(sp.getBoolean(Login.TEACHER_LOGIN_CHECK, false)){
            startActivity(intentTeacher);
            finish();
        }
    }

    private void getAllUsersData(){
        DatabaseReference reference = Database.database.getReference(UserStorage.USER_STORAGE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserStorage userStorage = dataSnapshot.getValue(UserStorage.class);

                String userName = sp.getString(Login.USERNAME,"Daku");
                String password = sp.getString(Login.PASSWORD,"DAku");
                ArrayList<BatchDetail> batchDetails = new UserStorage().getBatchDetails();
                int len = batchDetails.size();
                for (int i=0;i<len;i++){
                    if (userName.equals(batchDetails.get(i).getUserName()) && password.equals(batchDetails.get(i).getPassword())){
                        batchDetail = batchDetails.get(i);
                        break;
                    }
                }
                ArrayList<TeacherDetail> teacherDetails = new UserStorage().getTeacherDetails();
                int len2 = teacherDetails.size();
                for (int i=0;i<len2;i++){
                    if (userName.equals(teacherDetails.get(i).getFirstName()) && password.equals(teacherDetails.get(i).getPassword())){
                        teacherDetail = teacherDetails.get(i);
                        break;
                    }
                }
                //Toast.makeText(getBaseContext(),"Getting Users Data",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInstituteData(){
        DatabaseReference reference = Database.database.getReference(Institute.INSTITUTE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Institute institute = dataSnapshot.getValue(Institute.class);
                Toast.makeText(getBaseContext(),"Getting Institute Data",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTimeTable(){
        DatabaseReference reference = Database.database.getReference(TimeTable.TIME_TABLE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimeTable timeTable = dataSnapshot.getValue(TimeTable.class);
                Toast.makeText(getBaseContext(),"Getting Time Table",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
