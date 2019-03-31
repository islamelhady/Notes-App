package com.example.el_hady.viewmodel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private Button buttonColor;
    private CardView cardViewBackground;
    private int defaultColor;
    public static final String EXTRA_ID = "com.example.el_hady.viewmodel.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.el_hady.viewmodel.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.el_hady.viewmodel.EXTRA_DESCRIPTION";
    //public static final String EXTRA_PRIORITY = "com.example.el_hady.viewmodel.EXTRA_PRIORITY";
    public static final String EXTRA_COLOR = "com.example.el_hady.viewmodel.EXTRA_COLOR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        //numberPickerPriority = findViewById(R.id.number_picker_priority);
        buttonColor = findViewById(R.id.button_color);
        cardViewBackground = findViewById(R.id.card_view);
        defaultColor = getResources().getColor(R.color.white);

        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        //numberPickerPriority.setMinValue(1);
        //numberPickerPriority.setMaxValue(10);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            //cardViewBackground.setBackgroundColor(intent.getIntExtra(EXTRA_COLOR,1));

        } else {
            setTitle("Add Note");
        }


    }

    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                buttonColor.setBackgroundColor(color);
            }
        });
        ambilWarnaDialog.show();
    }


    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int color = this.defaultColor;


        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "PLZ insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_COLOR, color);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
