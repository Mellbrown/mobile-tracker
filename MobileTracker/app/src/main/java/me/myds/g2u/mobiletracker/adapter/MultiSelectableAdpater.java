package me.myds.g2u.mobiletracker.adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import androidx.recyclerview.widget.RecyclerView;

abstract public class MultiSelectableAdpater<T, VH extends RecyclerView.ViewHolder> extends BaseRecyclerAdapter<T, VH>{

    private boolean selectable = false;

    public Set<T> disabled = new HashSet<>();
    public Set<T> selected = new HashSet<>();

    private OnChangeSelectableListener changeSelectableListener;

    public interface OnChangeSelectableListener {
        void onChanageSelectable (boolean selectable);
    }

    public MultiSelectableAdpater(int modelLayout, Class<VH> viewHolderClass) {
        super(modelLayout, viewHolderClass);
    }

    public void setOnChangeSelectableListener(OnChangeSelectableListener listener) {
        this.changeSelectableListener = listener;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        if (this.selectable == selectable) return;
        this.selectable = selectable;
        if (selectable) {

        } else {
//            disabled = new HashSet<>();
            selected = new HashSet<>();
        }
        if (changeSelectableListener != null)
            changeSelectableListener.onChanageSelectable(selectable);
    }


    // Selection
    public void setSelect (Set<Integer> selected) {
        HashSet<T> newSelected = new HashSet<>();
        for (int select : selected) {
            newSelected.add(list.get(select));
        }
        this.selected = newSelected;
    }

    public void setSelect (int position, boolean select) {
        if (select) {
            selected.add(list.get(position));
        } else {
            if (selected.contains(list.get(position)))
                selected.remove(list.get(position));
        }
    }


    public void setSelect (T select) {
        selected.add(select);
    }

    public Set<T> getSelected () {
        return new HashSet<>(selected);
    }

    public boolean isSelected (T data) {
        return selected.contains(data);
    }

    // Disable
    public void setDisabled (Collection<T> disable) {
        disabled.addAll(disable);
    }

    public boolean isDisabled (T data) {
        return disabled.contains(data);
    }

    public void notifyDataChaged2Selection (){
        if (selectable) {
            HashSet<T> newSelected = new HashSet<>();
            for (T select : selected) {
                if(list.contains(select))
                    newSelected.add(select);
            }
            this.selected = newSelected;
        }
    }

    public void notifyDataIO (Collection<T> data) {

    }


}
