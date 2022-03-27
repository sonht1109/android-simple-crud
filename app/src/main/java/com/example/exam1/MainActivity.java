package com.example.exam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.exam1.adapter.CatItemAdapter;
import com.example.exam1.adapter.ImgSpinnerAdapter;
import com.example.exam1.model.Cat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerImg;
    private CatItemAdapter catItemAdapter;
    private RecyclerView rcvList;
    private EditText eName, ePrice, eDate, eTime;
    private Button btnAdd, btnEdit;
    private int editingPosition = -1;
    private Calendar c = Calendar.getInstance();

    private int[] listImg = {R.drawable.lem1,
            R.drawable.lem2, R.drawable.lem3,
            R.drawable.lem4, R.drawable.lem5, R.drawable.lem6};
    private ImgSpinnerAdapter imgSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        spinnerImg = findViewById(R.id.spinner_img);
        imgSpinnerAdapter = new ImgSpinnerAdapter(listImg, this);
        spinnerImg.setAdapter(imgSpinnerAdapter);
        btnAdd = findViewById(R.id.btn_add);
        btnEdit = findViewById(R.id.btn_edit);
        eName = findViewById(R.id.e_name);
        ePrice = findViewById(R.id.e_price);
        rcvList = findViewById(R.id.rcv_list);
        eDate = findViewById(R.id.e_date);
        eTime = findViewById(R.id.e_time);

        resetForm();

        catItemAdapter = new CatItemAdapter(this, new CatItemAdapter.CatItemListener() {
            @Override
            public void edit(int position) {
                handleChooseEditingCat(position);
            }

            @Override
            public void remove(int position) {
                if (position == editingPosition) {
                    resetForm();
                }
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvList.setLayoutManager(manager);
        rcvList.setAdapter(catItemAdapter);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        eDate.setOnClickListener(this);
        eTime.setOnClickListener(this);
    }

    public void handleChooseEditingCat(int position) {

        btnEdit.setEnabled(true);
        btnAdd.setEnabled(false);

        editingPosition = position;
        Cat editingCat = catItemAdapter.getListCat().get(editingPosition);
        eName.setText(editingCat.getName());
        ePrice.setText(String.valueOf(editingCat.getPrice()));
        eDate.setText(editingCat.getDate());
        eTime.setText(editingCat.getTime());

        int i = 0;
        for (; i < listImg.length; i++) {
            if (editingCat.getImg() == listImg[i]) {
                break;
            }
        }
        spinnerImg.setSelection(i);
    }

    public void handleEditCat() {
        Cat cat = returnCatFromForm();
        if (cat != null && editingPosition != -1) {
            catItemAdapter.editCat(cat, editingPosition);
            resetForm();
        }
    }

    public void handleAddCat() {
        Cat cat = returnCatFromForm();
        if (cat != null) {
            catItemAdapter.addCat(cat);
            resetForm();
        }
    }

    private Cat returnCatFromForm() {
        String name = eName.getText().toString();
        String price = ePrice.getText().toString();
        String img = spinnerImg.getSelectedItem().toString();
        String date = eDate.getText().toString();
        String time = eTime.getText().toString();

        try {
            double toDoublePrice = Double.parseDouble(price);
            int toIntImg = Integer.parseInt(img);
            if (name.equals("") || toDoublePrice < 0 || date.equals("") || time.equals("")) {
                throw new Exception();
            }
            Cat cat = new Cat(name, listImg[toIntImg], toDoublePrice, date, time);
            return cat;
        } catch (Exception e) {
            showToast("Invalid value");
            return null;
        }
    }

    private void resetForm() {
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        eName.setText("");
        ePrice.setText("");
        spinnerImg.setSelection(0);
        editingPosition = -1;
        eDate.setText("");
        eTime.setText("");
    }

    private void showToast(String mess) {
        Toast.makeText(MainActivity.this, mess, Toast.LENGTH_SHORT).show();
    }

    private void handleOpenDate() {
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        DatePickerDialog dateDialog = new DatePickerDialog(this, (view, year, month, day) -> {
            eDate.setText(day + "/" + month + "/" + year);
        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    private void handleOpenTime() {
        int currentHour = c.get(Calendar.HOUR);
        int currentMin = c.get(Calendar.MINUTE);
        TimePickerDialog timeDialog = new TimePickerDialog(this, (view, hour, min) -> {
            eTime.setText(hour + ":" + min);
        }, currentHour, currentMin, true);
        timeDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                handleAddCat();
                break;
            case R.id.btn_edit:
                handleEditCat();
                break;
            case R.id.e_date:
                handleOpenDate();
                break;
            case R.id.e_time:
                handleOpenTime();
                break;
        }
    }
}