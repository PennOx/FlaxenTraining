package tk.pankajb.apitest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.SingleUserLayoutBinding;
import tk.pankajb.apitest.interfaces.OnClickListener;
import tk.pankajb.apitest.models.User;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UsersViewHolder> {

    private final List<User> users;
    private final OnClickListener<Integer> listener;

    public UsersRecyclerAdapter(List<User> users, OnClickListener<Integer> listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SingleUserLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.single_user_layout,
                parent,
                false);

        return new UsersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.setUser(users.get(position));
        holder.setOnClick(listener, position);
    }

    @Override
    public int getItemCount() {

        if (users == null) {
            return 0;
        }

        return users.size();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {

        private final SingleUserLayoutBinding binding;

        public UsersViewHolder(SingleUserLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setUser(User user) {
            binding.setUser(user);
        }

        public void setOnClick(OnClickListener<Integer> listener, int position) {
           if (listener == null){
               binding.singleDeleteButton.setVisibility(View.GONE);
           }else {
               binding.singleDeleteButton.setVisibility(View.VISIBLE);
               binding.singleDeleteButton.setOnClickListener(view -> listener.onClick(position));
           }
        }
    }
}
