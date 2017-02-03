package com.example.dell.t6tgal.Fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Created by dell on 2017/2/3.
 */

public class CursorLoaderListFragment1 extends ListFragment implements SearchViewCompat.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnQueryTextListener {
    //这就是用来展示列表信息的Adapter
    SimpleCursorAdapter mAdapter;
    //如果不是null，这就是当前的搜索过滤器。
    String mCurFilter;

    //我们想获取的联系人中的行数据。
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts.LOOKUP_KEY,
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //如果没有数据，就给控件一些文本去显示。
        //在真正的应用中，信息来自应用资源。
        setEmptyText("No phone numbers");
        //我们在action bar中显示一个菜单项。
        setHasOptionsMenu(true);

        //创建一个新的adapter，我们将用它来显示加载的数据。
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[] { ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.CONTACT_STATUS },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);
        setListAdapter(mAdapter);
        //准备loader， 重连到一个已存在的loader，
        //或者启动一个新的loader。
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //放置一个action bar用于搜索。
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());
        sv.setOnQueryTextListener(this);
        item.setActionView(sv);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //把新的cursor换进来。  (框架将会在我们返回的时候
        //管理旧cursor的关闭事宜。)
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //当最后一个Cursor进入onLoadFinished()的时候被调用。
        //cursor将要被关闭， 我们应该确保
        //不再使用它。
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //action bar上的搜索文本改变的时候被调用。
        //更新搜索过滤器，并且重启loader用当前的过滤器
        //来做新的查询。
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }
    @Override public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        Log.e("FragmentComplexLists", "Items-------------- clicked: " + id);
    }
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //当需要创建一个新的loader时被调用。
        //本例中仅有一个loader，所以我们不必关心ID的问题。
        //首先，根据我们当前是否正在过滤，
        //选择base URI来使用。
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }
        //现在创建并返回一个CursorLoader，
        //它将会为被显示的数据创建一个Cursor。
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(getActivity(), baseUri,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }
}
