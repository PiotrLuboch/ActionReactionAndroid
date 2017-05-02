package com.project.pluboch.actionreaction.reactions.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.pluboch.actionreaction.R;

public class SmsSendReaction extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 1000;

    private EditText edtNumber;
    private EditText edtSmsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send_reaction);

        edtNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtSmsMessage = (EditText) findViewById(R.id.edtSmsMessage);
    }

    public void selectNumberFromContactList(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    public void finishSmsSendReaction(View view) {
        Intent intent = getIntent();
        String number = edtNumber.getText().toString();
        String message = edtSmsMessage.getText().toString();
        intent.putExtra("phoneNumber", number);
        intent.putExtra("smsMessage", message);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_CONTACT_REQUEST:
                //code from http://stackoverflow.com/questions/8612531/how-can-i-choose-a-phone-number-with-androids-contacts-dialog
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                edtNumber.setText(number);
                break;
            default:
                break;
        }
    }
}
