package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentActivity extends AppCompatActivity
{
    public BookListFragment bookListFragment; //A reference to the book list fragment which is displayed

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Setting toolbar as action bar
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        //Initializing the bottom nav
        initializeBottomNav();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflating the toolbar menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        //Getting the search view
        SearchView searchView = (SearchView)menu.findItem(R.id.search_booklist).getActionView();

        //Setting query listener for the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchBookTitle(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        /*Handles clicks on the toolbar menu items*/

        Log.e("MITEM", "menu clicked");

        switch(menuItem.getItemId())
        {
            case R.id.sort_booklist : {
                //Checking if the book list fragment is currently being displayed
                if(bookListFragment != null)
                {
                    //Displaying the sorting bottom sheet
                    BookListSortBottomSheetDialogFragment bottomSheet = new BookListSortBottomSheetDialogFragment();
                    bottomSheet.show(getSupportFragmentManager(), "SORT_BSHEET");
                }

                return true;
            }

            case R.id.filter_booklist :
            {
                //Checking if the book list fragment is currently being displayed
                if(bookListFragment != null)
                {
                    //Displaying the Filters bottom sheet
                    BookListCategoryBottomSheetDialogFragment bottomSheet = new BookListCategoryBottomSheetDialogFragment();
                    bottomSheet.show(getSupportFragmentManager(), "SORT_BSHEET");
                }

                return true;
            }
        }

        return false;
    }

    private void initializeBottomNav()
    {
        /*Initializies the bottom navigation view*/

        BottomNavigationView bottomNav = findViewById(R.id.app_bottomnav); //Getting the bottom navigation view

        //Inflating the bottom nav menu
        bottomNav.inflateMenu(R.menu.bottomnav_menu);

        //Adding the nav controller
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment); //Getting the nav host fragment
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController()); //Adding the host fragment's nav controller to the bottom nav android
    }

    public void setListSort(BookListFragment.ListStates newState)
    {
        /*Sets the ordering of the books in the book list*/

        if(this.bookListFragment != null)
        {
            this.bookListFragment.displayBookList(newState, 1);
        }
    }

    private void searchBookTitle(String title)
    {
        /*Provides given search request to booklistfragment*/

        if(this.bookListFragment != null)
            this.bookListFragment.searchAndDisplayBook(title);
    }

}