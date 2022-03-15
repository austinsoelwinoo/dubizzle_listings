package com.dubizzle.listings.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dubizzle.listings.R;
import com.dubizzle.listings.core.domain.Listing;
import com.dubizzle.listings.databinding.AcitivityDetailsBinding;
import timber.log.Timber;

public class DDetailsActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_PARAM_LISTING = "INTENT_EXTRA_PARAM_LISTING";

    public static Intent getCallingIntent(Context context, Listing listing) {
        Intent callingIntent = new Intent(context, DDetailsActivity.class);
        Timber.d("getCallingIntent %s", listing.getName());
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LISTING, listing);
        return callingIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcitivityDetailsBinding binding = AcitivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        Listing parcelableListing = getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_LISTING);
        Timber.d("onCreate %s", parcelableListing.getName());
        Glide.with(this)
                .load(parcelableListing.retrieveFirstImageUrl())
                .placeholder(R.drawable.listing_item_placeholder)
                .into(binding.ivListingImage);

        binding.tvListingName.setText(parcelableListing.getName());
        binding.tvListingPrice.setText(parcelableListing.getPrice());
        binding.tvListingCreated.setText(getString(R.string.created_date_text, parcelableListing.prettifiedCreatedAt()));
    }
}
