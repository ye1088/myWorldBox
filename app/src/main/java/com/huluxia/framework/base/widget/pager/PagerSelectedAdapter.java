package com.huluxia.framework.base.widget.pager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public abstract class PagerSelectedAdapter<T extends PagerFragment> extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<T> mFragments = new ArrayList();
    private ArrayList<SavedState> mSavedState = new ArrayList();
    private boolean mSelectedInitialize;

    public abstract T getItem(int i);

    public PagerSelectedAdapter(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    public T getPosFragment(int position) {
        if (position < this.mFragments.size()) {
            return (PagerFragment) this.mFragments.get(position);
        }
        return null;
    }

    public List<PagerFragment> excludePosFragment(int position) {
        List<PagerFragment> fragments = new ArrayList();
        PagerFragment fragment = getPosFragment(position);
        if (fragment == null) {
            return null;
        }
        fragments.addAll(this.mFragments);
        fragments.remove(fragment);
        return fragments;
    }

    public int indexOfFragment(PagerFragment fragment) {
        if (fragment != null && this.mFragments.contains(fragment)) {
            return this.mFragments.indexOf(fragment);
        }
        return -1;
    }

    public void startUpdate(ViewGroup container) {
    }

    @SuppressLint({"LongLogTag"})
    public Object instantiateItem(ViewGroup container, int position) {
        if (this.mFragments.size() > position) {
            Fragment f = (Fragment) this.mFragments.get(position);
            if (f != null) {
                return f;
            }
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment = getItem(position);
        if (this.mSavedState.size() > position) {
            SavedState fss = (SavedState) this.mSavedState.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }
        while (this.mFragments.size() <= position) {
            this.mFragments.add(null);
        }
        fragment.setPosition(position);
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        this.mFragments.set(position, fragment);
        this.mCurTransaction.add(container.getId(), fragment);
        return fragment;
    }

    @SuppressLint({"LongLogTag"})
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= position) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set(position, this.mFragmentManager.saveFragmentInstanceState(fragment));
        this.mFragments.set(position, null);
        this.mCurTransaction.remove(fragment);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment;
        }
    }

    public void finishUpdate(ViewGroup container) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
            if (this.mCurrentPrimaryItem != null && (this.mCurrentPrimaryItem instanceof PagerFragment) && this.mSelectedInitialize) {
                ((PagerFragment) this.mCurrentPrimaryItem).onSelected(this.mFragments.indexOf(this.mCurrentPrimaryItem));
                ((PagerFragment) this.mCurrentPrimaryItem).onPageScrollComplete(0);
                this.mSelectedInitialize = false;
            }
        }
    }

    public void setSelectedInitialize(boolean selectedInitialize) {
        this.mSelectedInitialize = selectedInitialize;
    }

    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    public Parcelable saveState() {
        Bundle bundle = null;
        if (this.mSavedState.size() > 0) {
            bundle = new Bundle();
            SavedState[] fss = new SavedState[this.mSavedState.size()];
            this.mSavedState.toArray(fss);
            bundle.putParcelableArray("states", fss);
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment f = (Fragment) this.mFragments.get(i);
            if (f != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                this.mFragmentManager.putFragment(bundle, "f" + i, f);
            }
        }
        return bundle;
    }

    @SuppressLint({"LongLogTag"})
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (fss != null) {
                for (Parcelable parcelable : fss) {
                    this.mSavedState.add((SavedState) parcelable);
                }
            }
            for (String key : bundle.keySet()) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    if (this.mFragmentManager == null) {
                        Log.w(TAG, "Null mFragmentManager at key " + key);
                        return;
                    }
                    Fragment f = this.mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (this.mFragments.size() <= index) {
                            this.mFragments.add(null);
                        }
                        f.setMenuVisibility(false);
                        this.mFragments.set(index, (PagerFragment) f);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }
}
