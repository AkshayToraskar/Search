package com.ak.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ak.search.app.Validate;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;
import com.ak.search.model.Survey;
import com.ak.search.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.txt_username)
    EditText txt_username;

    @BindView(R.id.txt_password)
    EditText txt_password;

    @BindView(R.id.rg_admin)
    RadioButton rb_admin;

    @BindView(R.id.btnLoginSubmit)
    Button btn_login;

    Validate validate;


    String userId;
    User user;

    boolean update=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            userId = String.valueOf(getIntent().getExtras().getLong("userId"));
            user = User.findById(User.class, Long.parseLong(userId));

            txt_username.setText(user.getName());
            txt_password.setText(user.getPassword());

            if (user.getType().equals("admin"))
                rb_admin.setChecked(true);

            btn_login.setText("Update");
            update=true;

        }

        validate = new Validate();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        if (!update) {
            menu.getItem(0).setVisible(false);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;


            case R.id.action_delete_user:
                new AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setMessage("Would you like to delete this User ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                User user = User.findById(User.class, Long.parseLong(userId));
                                user.delete();

                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBtnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnLoginSubmit:
                if(btn_login.getText().toString().equalsIgnoreCase("ADD")) {

                    if (validate.validateString(txt_username.getText().toString())) {
                        txt_username.setError("Enter Username");
                        return;
                    } else {
                        txt_username.setError(null);
                    }
                    if (validate.validateString(txt_password.getText().toString())) {
                        txt_password.setError("Enter Password");
                        return;
                    } else {
                        txt_username.setError(null);
                    }

                    String type;

                    if (rb_admin.isChecked()) {
                        type = "admin";

                    } else {
                        type = "user";
                    }

                    User user = new User();
                    user.setName(txt_username.getText().toString());
                    user.setPassword(txt_password.getText().toString());
                    user.setType(type);
                    user.save();

                    Toast.makeText(getApplicationContext(), "User Added Successfully !", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else{

                    String type;
                    if (rb_admin.isChecked()) {
                        type = "admin";

                    } else {
                        type = "user";
                    }
                    this.user.setName(txt_username.getText().toString());
                    this.user.setPassword(txt_password.getText().toString());
                    this.user.setType(type);
                    this.user.save();

                    finish();
                }

                break;
        }
    }
}
