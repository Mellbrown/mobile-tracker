package me.myds.g2u.mobiletracker.viewholder;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.R;

public class TransactionViewHolder extends BaseRecyclerViewHolder<Transaction> {

    public HashImage hashImage;
    public CardView cardView;
    public HashImage imgFrom;
    public HashImage imgTo;
    public TextView txtHash;
    public TextView txtFrom;
    public TextView txtTo;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtHash = itemView.findViewById(R.id.txtHash);
        this.txtFrom = itemView.findViewById(R.id.txtFrom);
        this.txtTo = itemView.findViewById(R.id.txtTo);
        this.hashImage = itemView.findViewById(R.id.imgHash);
        this.imgFrom = itemView.findViewById(R.id.imgFrom);
        this.imgTo = itemView.findViewById(R.id.imgTo);
        this.cardView = itemView.findViewById(R.id.card);
    }

    @Override
    public void bindData(Transaction transaction) {
        this.txtHash.setText(transaction.getTxHash());
        this.txtFrom.setText(transaction.getFrom());
        this.txtTo.setText(transaction.getTo());
        this.hashImage.setHashString(transaction.getTxHash().substring(2));
        this.imgFrom.setHashString(transaction.getFrom().substring(2));
        this.imgTo.setHashString(transaction.getTo().substring(2));

    }
}
