package com.example.rakapermanaputra.moviewcatalog.activity;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.AppPreference;
import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;
import com.example.rakapermanaputra.moviewcatalog.reminder.DailyReminder;
import com.example.rakapermanaputra.moviewcatalog.reminder.UpcomingReminder;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.switch_dailyNotif)
    Switch dailySwitch;
    @BindView(R.id.switch_upcomingNotif)
    Switch upcomingSwitch;
    private List<Result> movieList;


    private DailyReminder dailyReminder;
    UpcomingReminder upcomingReminder;
    private boolean isUpcoming, isDaily;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);


        dailyReminder = new DailyReminder();
        upcomingReminder = new UpcomingReminder();

        appPreference = new AppPreference(this);

        setEnabledisableNotif();

    }

    void setEnabledisableNotif() {
        if (appPreference.isDaily()) {
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }
        if (appPreference.isUpcoming()) {
            upcomingSwitch.setChecked(true);
        } else {
            upcomingSwitch.setChecked(false);
        }


    }

    @OnClick({R.id.switch_dailyNotif, R.id.switch_upcomingNotif})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_dailyNotif:
                isDaily = dailySwitch.isChecked();
                if (isDaily) {
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    dailyReminder.setRepeatingAlarm(this);
                    Toast.makeText(this, "Daily Notification is ON", Toast.LENGTH_SHORT).show();

                } else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    dailyReminder.cancelAlarm(this);
                    Toast.makeText(this, "Daily Notification is OFF", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_upcomingNotif:
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming) {
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);
                    Toast.makeText(this, "Upcoming Notification is ON", Toast.LENGTH_SHORT).show();
                    releaseMovie();
                } else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    Toast.makeText(this, "Upcoming Notification is OFF", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void releaseMovie() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);


        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getNowPlaying();
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems movieItems = response.body();
                movieList = movieItems.getResults();

                for (int i = 0; i < movieList.size(); i++) {
                    Result result = movieList.get(i);
                    if (result.getReleaseDate().equals(now)) {
                        upcomingReminder.setReleaseReminderAlarm(getApplicationContext(), result.getTitle());

                    }

                }


            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });

    }

}
