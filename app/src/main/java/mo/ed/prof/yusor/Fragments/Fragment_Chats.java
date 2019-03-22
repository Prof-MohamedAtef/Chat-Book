package mo.ed.prof.yusor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Prof-Mohamed Atef on 3/21/2019.
 */

public class Fragment_Chats extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment_Chats newInstance(int sectionNum) {
        Fragment_Chats fragment_chats=new Fragment_Chats();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNum);
        fragment_chats.setArguments(args);
        return fragment_chats;
    }
}
