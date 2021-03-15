package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.di.DI;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailNeighbourActivity extends AppCompatActivity {
    @BindView(R.id.activity_neighbour_details_toolbar)
    public android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.activity_neighbour_details_toolbar_btn)
    public ImageButton mToolbarButton;
    @BindView(R.id.activity_neighbour_details_toolbar_img)
    public ImageView mNeighbourAvatar;
    @BindView(R.id.activity_neighbour_details_toolbar_txt)
    public TextView mToolbarTextView;
    @BindView(R.id.infos_card_title_txt)
    public TextView mInfosCardTitleTextView;
    @BindView(R.id.infos_card_address_txt)
    public TextView mAdresse;
    @BindView(R.id.infos_card_phone_txt)
    public TextView mCardPhoneTxt;
    @BindView(R.id.infos_card_website_txt)
    public TextView mCardWebsiteTxt;
    @BindView(R.id.desc_card_title_txt)
    public TextView mCardTitleTxt;
    @BindView(R.id.des_card_txt)
    public TextView mCardDescTxt;
    @BindView(R.id.activity_neighbour_details_fav_fab)
    public android.support.design.widget.FloatingActionButton mFavFab;
    @BindDrawable(R.drawable.ic_star_border_white_24dp)
    public Drawable mStarBorderWhite;
    @BindDrawable(R.drawable.ic_star_white_24dp)
    public Drawable mStarWhite;


    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_neighbour);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        getNeighbour();
        configureToolbar();
        comfigureInfosCard();
        configureFavFab();
    }

    private void getNeighbour() {
        mNeighbour = getIntent().getParcelableExtra("Neighbour");
    }

    private void configureToolbar() {
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mNeighbourAvatar);
        mToolbarTextView.setText(mNeighbour.getName());
        mToolbarButton.setOnClickListener(v -> finish());
    }

    private void comfigureInfosCard() {
        mInfosCardTitleTextView.setText(mNeighbour.getName());
        mAdresse.setText(mNeighbour.getAddress());
        mCardPhoneTxt.setText(mNeighbour.getPhoneNumber());
        mCardWebsiteTxt.setText("www.facebook.com/" + mNeighbour.getName().toLowerCase());
        mCardTitleTxt.setText("About Me");
        mCardDescTxt.setText(mNeighbour.getAboutMe());
    }

        private void configureFavFab() {
            if (mNeighbour.getIsFavourite() == true) {
                mFavFab.setImageDrawable(mStarWhite);
            } else {
                mFavFab.setImageDrawable(mStarBorderWhite);
            }

            mFavFab.setOnClickListener(v -> {
                mApiService.changeNeighbourFavoriteStatus(mNeighbour);
                mNeighbour.setIsFavourite(!mNeighbour.getIsFavourite());
                configureFavFab();
            });

    }
}