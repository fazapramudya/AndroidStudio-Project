package com.example.contact.repository;

import android.app.Application;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contact.database.DAOContact;
import com.example.contact.database.Contact;
import com.example.contact.database.ContactRoomDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private final DAOContact mDaoContact;
    private final ExecutorService executorService;

    public ContactRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ContactRoomDB db = ContactRoomDB.getDatabase(application);
        mDaoContact = db.daoContact();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mDaoContact.getAllNotes();
    }

    public void insert(final Contact contact) {
        executorService.execute(() -> mDaoContact.insert(contact));
    }

    public void delete(final Contact contact) {
        executorService.execute(() -> mDaoContact.delete(contact));
    }

    public void update(final Contact contact) {
        executorService.execute(() -> mDaoContact.update(contact));
    }
}