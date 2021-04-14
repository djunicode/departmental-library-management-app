package com.example.unicodelibraryapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBookFragment extends Fragment
{

    private View.OnClickListener scanBarcodeClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //Checking for permission
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                ((LibrarianActivity)getActivity()).scanBarcode();
            else
                handleCameraPermission();
        }
    }; //Click listener for the scan barcode button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_book, container, false);

        //Setting click listener for isbn submit button
        AppCompatButton isbnSubmitBtn = fragmentView.findViewById(R.id.isbn_submit_btn);
        isbnSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addIsbn();
            }
        });

        //Setting click listener for scan barcode button
        fragmentView.findViewById(R.id.scan_barcode_btn).setOnClickListener(scanBarcodeClickListener);

        return fragmentView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        ((LibrarianActivity)getActivity()).addBookFragment = this;
    }

    @Override
    public void onStop()
    {
        super.onStop();

        ((LibrarianActivity)getActivity()).addBookFragment = null;
    }


    private void addIsbn()
    {
        /*Opens form for book with the entered ISBN*/

        //Getting the entered ISBN
        String isbn = ((EditText)getView().findViewById(R.id.isbn_textbox)).getText().toString();

        //ISBN Regex: ^(?=.{14,17}$)(97(8|9))?(\-)(\d){1,5}(\-)(\d){0,7}(\-)(\d){0,6}(\-)(\d|X)$

        //Checking if isbn is of valid format
        Pattern isbnRegexPattern = Pattern.compile("^(?=.{14,17}$)(97(8|9))?(\\-)(\\d){1,5}(\\-)(\\d){0,7}(\\-)(\\d){0,6}(\\-)(\\d|X)$");
        if(!isbnRegexPattern.matcher(isbn).matches())
        {
            //Invalid Isbn format
            Toast.makeText(getContext(), "Invalid ISBN Format", Toast.LENGTH_SHORT).show(); //Showing error message
            return;
        }
        else
        {
            //Creating bundle conaining the book isbn
            Bundle bundle = new Bundle();
            bundle.putString("ISBN", isbn);

            //Switching to book form fragment
            NavHostFragment navHostFragment = (NavHostFragment)(getActivity().getSupportFragmentManager().findFragmentById(R.id.librarian_nav_host_fragment));
            navHostFragment.getNavController().navigate(R.id.add_book_form_fragment, bundle);
        }

    }

    public void sendBarcode(String barcode)
    {
        /*Sends the barcode to the backend via api request*/

        Call<SuccessResponse> sendBarcodeCall = AuthActivity.retrofitApiInterface.addBookByBarcode(SessionInfo.loggedUser.getToken(), barcode);
        sendBarcodeCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response)
            {
                if(response.isSuccessful() && response.body().getSuccess())
                    Toast.makeText(getActivity(), "Book added successfully", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(getActivity(), "Failed to register barcode. Try again", Toast.LENGTH_SHORT).show();
                    Log.e("BARCODE_ERROR", response.message());
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(), "Failed to register barcode. Try again", Toast.LENGTH_SHORT).show();
                Log.e("BARCODE_ERROR", t.getMessage());
            }
        });
    }

    private void handleCameraPermission()
    {
        /*Handles the camera permission*/

        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Camera Permission")
                    .setMessage("The app requires your permission to access the camera in order to scan barcode")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //Requesting Permission
                            ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                                @Override
                                public void onActivityResult(Boolean result)
                                {
                                    if(result)
                                        ((LibrarianActivity)getActivity()).scanBarcode();
                                    else
                                        Toast.makeText(getActivity(), "Unable to scan barcode as camera permission denied", Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                        }
                    });
            builder.show();
        }
        else
        {
            //Requesting Permission
            ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result)
                {
                    if(result)
                        ((LibrarianActivity)getActivity()).scanBarcode();
                    else
                        Toast.makeText(getActivity(), "Unable to scan barcode as camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

}