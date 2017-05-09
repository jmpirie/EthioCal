package com.hundaol.ethiopic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.domain.DateModel;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    Display display;

    @Inject
    DateModel dateModel;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.today)
    FloatingActionButton today;

    MainPagerAdapter pagerAdapter;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pagerAdapter = new MainPagerAdapter(this, display, dateModel);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        disposables.add(RxView.clicks(today).subscribe(v -> {
            dateModel.setJdv(GregorianCal.INSTANCE.today());
        }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposables.dispose();
    }
}
