package ru.mirea.khudyakovma.mireaproject.ui.files;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.khudyakovma.mireaproject.databinding.ItemFileBinding;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.VH> {
    private final List<String> items;
    private final Callback callback;

    public interface Callback {
        void onClick(String filename);
    }

    public FileListAdapter(List<String> items, Callback callback) {
        this.items = items;
        this.callback = callback;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFileBinding b = ItemFileBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        String name = items.get(pos);
        holder.binding.tvName.setText(name);
        holder.binding.getRoot().setOnClickListener(v ->
                callback.onClick(name)
        );
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final ItemFileBinding binding;
        VH(@NonNull ItemFileBinding b) {
            super(b.getRoot());
            this.binding = b;
        }
    }
}
