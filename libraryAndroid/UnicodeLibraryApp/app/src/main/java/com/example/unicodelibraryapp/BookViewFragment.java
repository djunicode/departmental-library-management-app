package com.example.unicodelibraryapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookViewFragment extends Fragment {

    View rootView;
    Button secondButton;
    private Context context;
    ExpandableListView lv;
    List<String> listDataParent;
    HashMap<String, List<ListData>> listDataChild;

    public BookViewFragment() {
        // Required empty public constructor

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<>();

        listDataParent.add("IDs of Copies");
        listDataParent.add("Waiting List");

        List<ListData> idOfCopies = new ArrayList<>();
        idOfCopies.add(new ListData("888", null));
        idOfCopies.add(new ListData("333", null));
        List<ListData> waitingList  = new ArrayList<>();
        waitingList.add(new ListData("Arvind", "100"));
        waitingList.add(new ListData("Tanmay", "10jj0"));

        listDataChild.put(listDataParent.get(0), idOfCopies); // Header, Child data
        listDataChild.put(listDataParent.get(1), waitingList); // Header, Child data
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.list_view);
        lv.setAdapter(new ExpandableListAdapter(context, listDataParent, listDataChild));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private LayoutInflater inf;
        private List<String> groups;
        private HashMap<String, List<ListData>> child;
        private Context context;

        public ExpandableListAdapter(Context context, List<String> groups, HashMap<String, List<ListData>> child) {
            this.groups = groups;
            this.child = child;
            this.context = context;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            //return groups.length;
            return this.groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.child.get(this.groups.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.child.get(this.groups.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder;
            holder = new ViewHolder();
            if (convertView==null) {
                if (groups.get(groupPosition).equals("Waiting List")){

                    convertView = inf.inflate(R.layout.waiting_list_item, parent, false);

                    TextView tvItem = (TextView) convertView.findViewById(R.id.student_name);
                    TextView tvRealAmount = (TextView)convertView.findViewById(R.id.book_id);

                    ListData investment = (ListData) getChild(groupPosition, childPosition);
                    tvItem.setText(investment.getName());
                    tvRealAmount.setText(investment.getBookId());

                }

                if (groups.get(groupPosition).equals("IDs of Copies")){

                    convertView = inf.inflate(R.layout.list_id_copies_item, parent, false);

                    TextView tvItem = (TextView) convertView.findViewById(R.id.idCopiesListItem);


                    ListData investment = (ListData) getChild(groupPosition, childPosition);
                    tvItem.setText(investment.getName());

                }

            }
            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.adapter_list_group, parent, false);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.expandable_list_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class ViewHolder {
            TextView text;
        }

    }

}