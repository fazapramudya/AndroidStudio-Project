package com.example.contact.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact.database.Contact;
import com.example.contact.databinding.ItemContactBinding;
import com.example.contact.helper.ContactDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final ArrayList<Contact> listContacts = new ArrayList<>();

    void setListContacts(List<Contact> listContacts) {
        final ContactDiffCallback diffCallback = new ContactDiffCallback(this.listContacts, listContacts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listContacts.clear();
        this.listContacts.addAll(listContacts);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(listContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder{
        final ItemContactBinding binding;

        ContactViewHolder(ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Contact contact) {
            binding.tvName.setText(contact.getName());
            binding.tvNumber.setText(contact.getNumber());
            binding.tvItemDate.setText(contact.getDate());
            binding.tvCall.setOnClickListener(v -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contact.getNumber()));
                v.getContext().startActivity(callIntent);
            });
            binding.tvMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:" + contact.getNumber()));
                v.getContext().startActivity(intent);
            });
            binding.tvWhatsapp.setOnClickListener(v -> {
                Intent waIntent = new Intent(Intent.ACTION_VIEW);
                waIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + contact.getNumber()));
                v.getContext().startActivity(waIntent);
            });
            binding.cvItemContact.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), InsertUpdateActivity.class);
                intent.putExtra(InsertUpdateActivity.EXTRA_CONTACT, contact);
                v.getContext().startActivity(intent);
            });
        }
    }
}
