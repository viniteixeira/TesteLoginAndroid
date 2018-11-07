package support.api.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MarioGuerra on 12/05/16.
 */

public class StableArrayAdapter extends ArrayAdapter<String> implements DynamicListView.SwappableListAdapter{

    final int INVALID_ID = -1;
    public static ArrayList<ObjectSeqVisita> mListaVisita;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    List<String> objects;
    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // This method is opposite of getItemId().
    public void addItem(String item) {
        // Add the string into the objects
        objects.add(item);

        // size of objects should be added by one after add() above
        int index = objects.size();
        mIdMap.put(item, index);
    }

    public void swap(ArrayList a,int index1, int index2) {

        Object temp = a.get(index1);
        a.set(index1, a.get(index2));
        a.set(index2, temp);

        ObjectSeqVisita temp_seq = mListaVisita.get(index1);
        mListaVisita.set(index1, mListaVisita.get(index2));
        mListaVisita.set(index2, temp_seq);



        notifyDataSetChanged();
    }


}
