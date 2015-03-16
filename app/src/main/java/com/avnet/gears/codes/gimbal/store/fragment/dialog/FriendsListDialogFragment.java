package com.avnet.gears.codes.gimbal.store.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.bean.FriendDataBean;
import com.avnet.gears.codes.gimbal.store.bean.response.FriendListResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p/>
 * to handle interaction events.
 * Use the {@link FriendsListDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsListDialogFragment extends DialogFragment {
    private static int TAG_CHECKBOX_IDENTIFIER_INT = 23453;
    private List<String> selectedFriendsId = new ArrayList<String>();

    public static FriendsListDialogFragment newInstance(FriendListResponseBean responseBean) {
        Bundle args = new Bundle();
        args.putSerializable(
                GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_FRIEND_LIST_RESPONSE.toString(),
                responseBean);
        FriendsListDialogFragment fragment = new FriendsListDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_friends_list_selection, null);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.friend_list_layout);
        FriendListResponseBean responseBean = (FriendListResponseBean) getArguments().
                getSerializable(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_FRIEND_LIST_RESPONSE.toString());
        final FriendListSelectListener friendListSelectListener = (FriendListSelectListener) getActivity();
        // TODO caution here
        if (responseBean != null) {
            FriendDataBean[] friendsList = responseBean.getCatalogEntryView();
            if (friendsList != null && friendsList.length > 0) {
                for (FriendDataBean friend : friendsList) {
                    if (friend.getFriendUserId() != null) {
                        CheckBox ch = new CheckBox(getActivity());

                        ch.setText(friend.getFriendContactName()
                                + "["
                                + friend.getFriendPhoneNumber()
                                + "]");
                        ch.setHint(friend.getFriendUserId());
                        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                CheckBox checkBox = (CheckBox) buttonView;
                                String selectedFriendId = (String) checkBox.getHint();
                                if (isChecked) {
                                    if (!selectedFriendsId.contains(selectedFriendId)) {
                                        selectedFriendsId.add(selectedFriendId);
                                        Log.d("DEBUG", "Adding friend" + selectedFriendId);
                                    }
                                } else {
                                    selectedFriendsId.remove(selectedFriendId);
                                }
                            }
                        });
                        linearLayout.addView(ch);
                    }
                }
            }
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.friends_list_fragment_title)
                .setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do send selected response to the parent activity
                                friendListSelectListener.onFinishedSelectDialog(selectedFriendsId);
                                dialog.dismiss();
                            }
                        })
                .create();
    }

    public interface FriendListSelectListener {
        public void onFinishedSelectDialog(List<String> friendIdList);
    }

}
