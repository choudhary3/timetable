package com.example.rajatiit.admin_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rajatiit.admin_app.dataclasses.Classroom;
import com.example.rajatiit.admin_app.timetablehandler.SlotDetails;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;

import java.util.ArrayList;


public class TeacherClassroomFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_data_interface, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        ListView listview = (ListView) view.findViewById(R.id.displayDataList);

        CustomTeacherClassroomAdapter customTeacherClassroomAdapter = new CustomTeacherClassroomAdapter(getContext(),
                R.layout.activity_display_data_interface, generateClassroomList());

        listview.setAdapter(customTeacherClassroomAdapter);

        return view;
    }

    private ArrayList<Classroom> generateClassroomList(){
        ArrayList<Classroom> classrooms = new ArrayList<>();
        int teacherId = Intermediate.teacherDetail.getTeacherId();
        ArrayList<SlotDetails> slotDetails = new TimeTable().getTotalSlots();
        int len = slotDetails.size();
        for (int i=0;i<len;i++){
            int len1 = slotDetails.get(i).totalClassrooms();
            for (int j=0;j<len1;j++){
                if (teacherId == slotDetails.get(i).getClassroomDetail(j).getTeacherId()) {
                    classrooms.add(slotDetails.get(i).getClassroomDetail(j));
                    break;
                }
            }
            if (classrooms.size() != i+1){
                classrooms.add(new Classroom());
            }
        }
        return classrooms;
    }
}
