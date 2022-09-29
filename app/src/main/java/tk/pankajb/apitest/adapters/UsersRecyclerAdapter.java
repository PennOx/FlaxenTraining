package tk.pankajb.apitest.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tk.pankajb.apitest.R;
import tk.pankajb.apitest.databinding.SingleUserLayoutBinding;
import tk.pankajb.apitest.models.User;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UsersViewHolder> {

    private final List<User> users;

    public UsersRecyclerAdapter(List<User> users) {
        this.users = users;
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
    }
}
