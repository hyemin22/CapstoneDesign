package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_name);

        EditText editName = findViewById(R.id.editName);
        Button okBtn = findViewById(R.id.okBtn1);

        okBtn.setEnabled(false);
        okBtn.setAlpha(0.5f);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                okBtn.setEnabled(charSequence.length() > 0);
                okBtn.setAlpha(1.0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                Intent intent = new Intent(SignupNameActivity.this, SignupIconActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

    }
}
