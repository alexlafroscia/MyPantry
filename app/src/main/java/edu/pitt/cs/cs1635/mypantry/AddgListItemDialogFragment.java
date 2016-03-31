package edu.pitt.cs.cs1635.mypantry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Chris on 3/29/2016.
 */
public class AddgListItemDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater= getActivity().getLayoutInflater();

        //builder class shizz
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.dialog_additem,null));


        builder.setMessage(R.string.dialog_add_item).setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //accept
                mListener.onDialogPositiveClick(AddgListItemDialogFragment.this);
            }
        })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user cancled dialog
                        mListener.onDialogNegativeClick(AddgListItemDialogFragment.this);
                    }
                });
        //create the alertdialog object and return it
        return builder.create();
    }

    public interface AddListItemDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    AddListItemDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mListener= (AddListItemDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"implement the listener!");
        }
    }
}
