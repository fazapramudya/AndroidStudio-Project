package com.example.contact.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contact.R;
import com.example.contact.database.Contact;
import com.example.contact.databinding.ActivityInsertUpdateBinding;
import com.example.contact.helper.DateHelper;
import com.example.contact.helper.ViewModelFactory;

public class InsertUpdateActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "extra_contact";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private boolean isEdit = false;
    private Contact contact;
    private ContactInsertUpdateViewModel contactInsertUpdateViewModel;
    private ActivityInsertUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactInsertUpdateViewModel = obtainViewModel(InsertUpdateActivity.this);
        contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contact != null) {
            isEdit = true;
        } else {
            contact = new Contact();
        }
        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            if (contact != null) {
                binding.etName.setText(contact.getName());
                binding.etNumber.setText(contact.getNumber());
                binding.etGroup.setText(contact.getGroup());
                binding.etInstagram.setText(contact.getInstagram());
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.btnSubmit.setText(btnTitle);
        binding.btnSubmit.setOnClickListener(view -> {
            String name = binding.etName.getText().toString().trim();
            String number = binding.etNumber.getText().toString().trim();
            String group = binding.etGroup.getText().toString().trim();
            String instagram = binding.etInstagram.getText().toString().trim();
            if (name.isEmpty()) {
                binding.etName.setError(getString(R.string.empty));
            } else if (number.isEmpty()) {
                binding.etNumber.setError(getString(R.string.empty));
            } else if (group.isEmpty()) {
                binding.etGroup.setError(getString(R.string.empty));
            } else if (instagram.isEmpty()) {
                binding.etInstagram.setError(getString(R.string.empty));
            } else {
                contact.setName(name);
                contact.setNumber(number);
                contact.setGroup(group);
                contact.setInstagram(instagram);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONTACT, contact);
                if (isEdit) {
                    contactInsertUpdateViewModel.update(contact);
                    showToast(getString(R.string.changed));
                } else {
                    contact.setDate(DateHelper.getCurrentDate());
                    contactInsertUpdateViewModel.insert(contact);
                    showToast(getString(R.string.added));
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogMessage = getString(R.string.message_delete);
            dialogTitle = getString(R.string.delete);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        (dialog, id) -> {
                            if (!isDialogClose) {
                                contactInsertUpdateViewModel.delete(contact);
                                showToast(getString(R.string.deleted));
                            }
                            finish();
                        })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ContactInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactInsertUpdateViewModel.class);
    }
}