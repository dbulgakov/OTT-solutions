package com.dbulgakov.task2.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    // Вообще хранить фрагменты в чистом виде - не очень хорошо.
    // Для этого есть fragment manager, который регулирует этот процесс
    // В случае с 2мя фрагментами он будет вести себя аналогично, но если фрагментов станет больше,
    // он может себе позволить неиспользуемые в данный момент выгружать из памяти,
    // а потом восстанавливать.
    // Соответственно, при этом фрагмент можно получить по тегу и с ним работать
    // В данном случае можно не исправлять, но на будущее стоит иметь ввиду
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
